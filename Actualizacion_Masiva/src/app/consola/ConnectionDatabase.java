/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.consola;

import COM.ibm.db2.jdbc.app.DB2Driver;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Logger;

public class ConnectionDatabase {

  String connType = null;
  String connURL = null;
  String connUser = null;
  String connPasswd = null;
  int connOpenCursors;
  Logger log = null;
  private Hashtable actionCode = new Hashtable();

  public ConnectionDatabase(String type, Logger log)
  {
    try {
      this.connType = type.toLowerCase();
      this.log = log;
      if(type.equalsIgnoreCase("microsoft")){
          DriverManager.registerDriver(new SQLServerDriver());
      }else if(type.equalsIgnoreCase("db2")){
          DriverManager.registerDriver(new DB2Driver());
      }
    } catch (Exception e) {
      log.warning("Error al cargar el driver JDBC/ODBC: " + e.getMessage());
    }
  }

  public boolean closeConnection(Connection con) {
    try {
      if (con != null)
        con.close();
      return true;
    } catch (SQLException e) {
      this.log.warning("Error al cerrar la conexión al Motor de Base de Datos: " + e.getMessage()); }
    return false;
  }

  public Connection openConnection(Connection con)
  {
    try {
      con = DriverManager.getConnection(this.connURL, this.connUser, this.connPasswd);
      
      con.setAutoCommit(false);
      return con;
    } catch (Exception e) {
      this.log.warning("Error al conectar al Motor de Base de Datos con los parametros proporcionados en el archivo de Configuración: " + e.getMessage()); }
    return null;
  }

  public boolean commitData(Connection con)
  {
    try {
      con.commit();
      return true;
    } catch (SQLException eSQL) {
      this.log.info("Error al tratar de hacer Commit al Motor de Base de Datos - " + eSQL.getMessage()); }
    return false;
  }

  public void setConnectionString(String server, String port, String dbname)
  {
    if (this.connType.equals("oracle")){
      //this.connURL = "jdbc:" + this.connType + ":thin:@" + server + ":" + port + ":" + dbname;
      //this.connURL = "jdbc:" + this.connType + ":thin:@(description=(address=(host=" + server + ")(protocol=tcp)(port=" + port + "))(connect_data=(service_name=sisesat.produce.gob.pe)(server="+ server + ")))";    
        this.connURL = "jdbc:" + this.connType + ":thin:@(description=(address=(host=" + server + ")(protocol=tcp)(port=" + port + "))(connect_data=(service_name=sisesat)(server="+ server + ")))";    
    }else if(this.connType.equals("microsoft")){
        this.connURL = "jdbc:sqlserver://" + server+";databaseName="+ dbname;    
    }else if(this.connType.equals("db2")){
        this.connURL = "jdbc:db2://" + server+":"+port+"/"+ dbname;    
    }
//    System.out.println("connURL:"+this.connURL);
    
  }

  public void setUser(String user) {
    this.connUser = user;
  }

  public void setPassword(String passwd) {
    this.connPasswd = passwd;
  }

  public void setOpenCursors(int oc) {
    this.connOpenCursors = oc;
  }

  public int getOpenCursors() {
    return this.connOpenCursors;
  }

  public boolean inActionCode(String type, int code) {
    if (this.actionCode.containsKey(type)) {
      int[] codes = (int[])this.actionCode.get(type);
      for (int i = 0; i < codes.length; ++i) {
        if (codes[i] == code) return true;
      }
    }
    return false;
  }

  public void setActionCode(String type, int[] codes) {
    this.actionCode.put(type, codes);
  }

  public int[] getActionCode(String type) {
    if (this.actionCode.containsKey(type)) {
      return ((int[])this.actionCode.get(type));
    }
    return null;
  }
}
