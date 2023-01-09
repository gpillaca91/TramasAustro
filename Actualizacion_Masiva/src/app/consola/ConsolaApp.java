/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.consola;

import app.model.ConfigManager;
import app.model.Databases;
import app.model.Database;
import app.model.Master;
import app.model.Provider;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;

public class ConsolaApp {

    private static Logger log;
    private int timeInterval = 0;
    private int timeWait = 0;
    private String rutaLocal = "";
    private Socket conexion;

    
    
    Configuration config = null;
    Provider proveedor = new Provider();
    Master infoMaestra;
    Timer timer;

    

    public int getTimeInverval() {
        return (this.timeInterval * 1000);
    }

    public int getTimeWait() {
        return this.timeWait;
    }

    public ConsolaApp() {
    }

    public ConsolaApp(Socket socket) {
        this.conexion = socket;
    }

    public void loadConfiguration() {
        try {
            URL logURL = new File("config/logging.properties").toURL();
            LogManager.getLogManager().readConfiguration(new FileInputStream(logURL.getFile()));
            log = Logger.getLogger(ConsolaApp.class.getName());

            log.info("****Inicia carga de configuración****");

            ConfigurationFactory factory = new ConfigurationFactory();
            URL configURL = new File("config/config.xml").toURL();
            factory.setConfigurationURL(configURL);
            this.config = factory.getConfiguration();

            Master master = new Master();
            master.setName(this.config.getString("master[@name]"));
            master.setDesc(this.config.getString("master[@desc]"));
            master.setTimeinterval(this.config.getInt("master.timeinterval"));
            master.setTimewait(this.config.getInt("master.timewait"));
            master.setGmt(this.config.getString("master.gmt"));

            this.proveedor.setName(this.config.getString("provider[@name]"));

            Databases basesdedatos = new Databases();
            List<Database> listaBasesDatos = new ArrayList<Database>();
            String[] databaseNames = this.config.getStringArray("provider.databases.database[@name]");
            for (int x = 0; x < databaseNames.length; ++x) {
                Database db = new Database();
                if (this.config.getBoolean("provider.databases.database(" + x + ")[@turn]", true)) {
                    db.setTurn(true);
                    db.setName(this.config.getString("provider.databases.database(" + x + ")[@name]"));
                    db.setDriver(this.config.getString("provider.databases.database(" + x + ").driver"));
                    db.setServer(this.config.getString("provider.databases.database(" + x + ").server"));
                    db.setPort(this.config.getString("provider.databases.database(" + x + ").port"));
                    db.setSchema(this.config.getString("provider.databases.database(" + x + ").schema"));
                    db.setUser(this.config.getString("provider.databases.database(" + x + ").user"));
                    db.setPassword(this.config.getString("provider.databases.database(" + x + ").password"));

                } else {
                    db.setTurn(false);
                }
                listaBasesDatos.add(db);
            }
            basesdedatos.setListaDataBases(listaBasesDatos);
            this.proveedor.setBasesDatos(basesdedatos);

            ConfigManager confManager = new ConfigManager();
            confManager.setName(this.config.getString("provider.configManager[@name]"));

            confManager.setHostCanal(this.config.getString("provider.configManager.hostCanal"));
            confManager.setPortCanal(this.config.getInt("provider.configManager.portCanal"));
            confManager.setTimeout(this.config.getInt("provider.configManager.timeout"));

            this.proveedor.setConfiguracion(confManager);

            log.info("Info de estructura de datos cargada.");
            log.info("****Finalizó la carga de configuración****");
        } catch (ConfigurationException eConf) {
            System.out.println(eConf);
            log.warning(eConf.getMessage());
        } catch (MalformedURLException eURL) {
            System.out.println(eURL);
            log.warning(eURL.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            log.warning(e.getMessage());
        }
    }

    public void closeEverything() {
        log.info("****Iniciando proceso de apagado****");

        log.info("****Buscando conexiones de bases de datos para el proveedor " + this.proveedor.getName() + "****");
        List<Database> listaBDs = this.proveedor.getBasesDatos().getListaDataBases();
        for (Iterator<Database> iterator1 = listaBDs.iterator(); iterator1.hasNext();) {
            Database next1 = iterator1.next();
            log.info("****Validando la base de datos " + next1.getName() + "****");
            if (next1.isTurn()) {
                //espacio para apagado de conexiones
            } else {
                log.info("****La base de datos no está activa****");
            }
        }
        log.info("****Finalizó la inspección de base de datos.****");

        log.info("****Apagando...****");
    }
    
    public int getPuertoCanal(){
        return this.proveedor.getConfiguracion().getPortCanal();
    }
    
    public int getTimeOutCanal(){
        return this.proveedor.getConfiguracion().getTimeout();
    }
    
    public Logger getLog(){
        return this.log;
    }
}
