/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.consola;

import app.model.ConfigManager;
import app.model.Provider;
import app.simulador.parser.Parser;
import com.alignet.isoparser.bean.ISOFields;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;

import app.simulador.parser.Parser;
import app.simulador.util.ConfigSimulator;
import app.simulador.util.HexaHelper;
import app.simulador.util.Util;

import com.alignet.isoparser.bean.ISOFields;
import com.alignet.isoparser.impl.ISOParserUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.LogManager;

public class Ejecutor {

    private Logger log = null;

    public static final String SEPARATOR_PUNTOCOMA = ";";
    public static final String SEPARATOR_COMA = ",";
    public static final String QUOTE = "\"";

    private boolean _encendido = false;
    private boolean _connectado = false;
    private boolean _running = false;

    public void setLogObject(Logger log) {
        this.log = log;
    }

    public void setRunning(boolean run) {
        this._encendido = run;
    }

    private class MiHilo implements Runnable {

        private Socket conexion;
        InputStream flujoEntrada;
        OutputStream flujoSalida;
        String cabConst = "TEST";

        public MiHilo(Socket socket) {
            this.conexion = socket;
        }

        public void run() {
            try {
                while ((!this.conexion.isClosed()) && this.conexion.isConnected()) {

                    ObjectInputStream ois = new ObjectInputStream(this.conexion.getInputStream());
                    String mess = (String) ois.readObject();
                    log.info("Message Received: " + mess);
                    String cabMessage = mess.substring(0, 4);
                    if (cabConst.equals(cabMessage.toUpperCase())) {
                        String realMessage = mess.substring(4);
                        ObjectOutputStream oos = new ObjectOutputStream(this.conexion.getOutputStream());
                        String mesSend = "Hi Client " + realMessage;
                        oos.writeObject(mesSend);
                        log.info("Message Sent: " + mesSend);
                        ois.close();
                        oos.close();
                        this.conexion.close();

                    } else {
                        long timeToWait = 25L;
                        long timeout = 1000L;
                        Parser parser = new Parser();
                        ISOFields isoFields = new ISOFields();
                        this.flujoEntrada = this.conexion.getInputStream();
                        DataInputStream dataInputStream = new DataInputStream(
                                new BufferedInputStream(this.flujoEntrada));
                        this.flujoSalida = this.conexion.getOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(
                                new BufferedOutputStream(this.flujoSalida));
                        int lenghtMessage = 0;
                        int longitudCabecera = 22;
                        log.info("conexion.isConnected(): " + this.conexion.isConnected());
                        try {
                            log.info("dataInputStream.available: " + dataInputStream.available());
                            while (dataInputStream.available() == 0) {
                                Thread.sleep(timeToWait);
                            }
                            log.info("Obteniendo bytes: ");
                            short lengthFromBytes = dataInputStream.readShort();
                            log.info("El mensaje recibido tiene una longitud de :" + lengthFromBytes);
                            lenghtMessage = lengthFromBytes;
                            log.info("Longitud total de la trama (mas cabecera): " + lenghtMessage);
                            List<Byte> bArray = new ArrayList<Byte>();
                            long waitedTime = 0L;
                            while (true) {
                                int datosDisponibles = dataInputStream.available();
                                log.info("datosDisponibles: " + datosDisponibles);
                                if (datosDisponibles < lenghtMessage) {
                                    waitedTime += timeToWait;
                                    if (waitedTime == timeout) {
                                        throw new Exception("Se cumplio el timeout de espera de bytes");
                                    }
                                    Thread.sleep(timeToWait);
                                    continue;
                                }
                                break;
                            }
                            log.info("lengthFromBytes: " + lengthFromBytes);
                            log.info("lenghtMessage: " + lenghtMessage);
                            byte[] cabeceraBytes = new byte[longitudCabecera];
                            dataInputStream.read(cabeceraBytes);
                            log.info("Mostrando bytes de cabecera: ");
                            Util.mostrarBytes(cabeceraBytes);
                            String cabeceraString = ISOParserUtils.bytesToHex(cabeceraBytes);
                            log.info("Mostrando contenido de cabecera: " + cabeceraString);
                            boolean valido = Util.validarCabecera(cabeceraString, lengthFromBytes);
                            log.info("Resultado validacion de cabecera: " + valido);
                            if (valido) {
                                byte[] message = new byte[lengthFromBytes];
                                dataInputStream.read(message);
                                log.info("Mostrando bytes de trama: ");
                                Util.mostrarBytes(message);
                                isoFields = Ejecutor.this.converBytesAndGetISOFields(message, parser);
                                log.info("trama recibida: " + isoFields.toString());
                                Ejecutor.this.sendMessage(dataOutputStream, isoFields, parser);
                            } else {
                                log.info("ERROR: No paso validacion de cabecera");
                            }
                        } catch (EOFException e) {
                            this.flujoEntrada = null;
                            this.flujoEntrada = this.conexion.getInputStream();
                            log.info("error en el dataoutput o datainp " + e);
                            break;
                        } catch (SocketException e) {
                            log.info("error en el socket " + e);
                            break;
                        } catch (SocketTimeoutException e) {
                            log.info("Error en el proceso" + e);
                            continue;
                        }
                        log.info("------------Fin del procesamiento-------------");
                    }
                }

            } catch (Exception e) {
                log.info("Ocurrio una excepcion " + e.getCause() + " " + e.getMessage());
                // try {
                // this.flujoSalida.close();
                // } catch (IOException e1) {
                // log.info("Ocurrio una excepcion2 " + e1);
                // }
            }
        }

    }

    public Ejecutor() {
    }

    public void IniciarEjecutor(int puerto, int timeout) {
        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            serverSocket.setSoTimeout(timeout);
            this._encendido = true;
            log.info("Iniciando servidor en el puerto: " + puerto);
            log.info("-------------------------------");
            this._connectado = false;
            int count = 0;
            while (this._encendido) {
                Socket conexionEntrante = null;
                try {
                    conexionEntrante = serverSocket.accept();
                    conexionEntrante.setSoTimeout(timeout);
                } catch (SocketTimeoutException e) {
                    continue;
                }
                log.info("Se ha aceptado una conexion");
                log.info("..Desde: " + conexionEntrante.getInetAddress().getHostName());
                log.info("INICIANDO HILO");
                (new Thread(new MiHilo(conexionEntrante))).start();
                log.info("HILO INICIADO");
            }
            log.info("CERRANDO SERVER SOCKET");
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ISOFields converBytesAndGetISOFields(byte[] message, Parser parser) {
        ISOFields isoFields = new ISOFields();
        log.info("Array de bytes obtenidos : " + ISOParserUtils.bytesToHex(message));
        Util.mostrarBytes(message);
        isoFields = parser.consiguiendoISOFields(message);
        log.info("ISOFIELDS: " + isoFields);
        return isoFields;
    }

    private void sendMessage(DataOutputStream dataOutputStream, ISOFields isoFields, Parser parser) throws IOException {
        log.info("Inicio - sendMessage");
        Integer length = Integer.valueOf(0);
        if (isoFields != null) {
            byte[] response = parser.prepararMensaje(isoFields);
            isoFields = parser.consiguiendoISOFields(response);
            log.info("isoFields enviado: " + isoFields);
            byte[] temp = response;
            response = null;
            response = temp;
            int longitudCabecera = 22;
            length = Integer.valueOf(Integer.valueOf(response.length).intValue() + longitudCabecera);
            String cabecera = Util.generarCabeceraRespuesta(length.intValue());
            log.info("cabecera decimal " + cabecera);
            Util.mostrarBytes(cabecera.getBytes());
            log.info("bytesToHexString Cabeceras " + Util.bytesToHexString(cabecera.getBytes()));
            log.info("requestMessage.getTramaISOoutput() hexa " + Util.bytesToHexString(response));
            long inicio = System.currentTimeMillis();
            byte[] message = ISOParserUtils.addToFrame(HexaHelper.hex2Byte(cabecera), response);
            String responseString = ISOParserUtils.bytesToHex(message);
            log.info("Trama enviada: " + responseString);
            Util.mostrarBytes(message);
            byte[] respCode = isoFields.getField39().getBytes("cp1047");
            byte[] referenceNumber = null;
            byte[] authorizationCode = null;
            byte[] terminalCode = isoFields.getField41().getBytes("cp1047");
            if (isoFields.getFieldMTI().equals("0810")) {
                message[37] = respCode[0];
                message[38] = respCode[1];
            } else if (isoFields.getFieldMTI().equals("0110")) {
                referenceNumber = isoFields.getField37().getBytes("cp1047");
                Util.convertToEbcdic(message, referenceNumber, 73, 84);
                if (isoFields.getField39().equals("00")) {
                    authorizationCode = isoFields.getField38().getBytes("cp1047");
                    Util.convertToEbcdic(message, authorizationCode, 85, 90);
                    Util.convertToEbcdic(message, respCode, 91, 92);
                    Util.convertToEbcdic(message, terminalCode, 93, 100);
                } else {
                    Util.convertToEbcdic(message, respCode, 85, 86);
                    Util.convertToEbcdic(message, terminalCode, 87, 94);
                }
            } else if (isoFields.getFieldMTI().equals("0410")) {

            }
            dataOutputStream.write(message);
            log.info("Se finalizo el envio del mensaje");
            dataOutputStream.flush();
        }
        log.info("Fin - sendMessage");
    }

    public static void main(String[] args) {
        InputStream filePath = Util.getFilePath("configuration.properties", Main.class);
        try {
            Properties properties = Util.loadProperties(filePath);
            ConfigSimulator.setBinList(Util.getListFromPropertie("BIN", properties));
            ConfigSimulator.setCommerceList(Util.getListFromPropertie("COMMERCE", properties));
        } catch (IOException e) {
            System.out.println("Ocurrio un error al obtener los listados de configuracion" + e);
            e.printStackTrace();
            try {
                Thread.sleep(100000L);
            } catch (InterruptedException e1) {
                System.out.println("" + e);
                e1.printStackTrace();
            }
        }
        Ejecutor ejec = new Ejecutor();
        try {
            URL logURL = new File("config/logging.properties").toURL();
            LogManager.getLogManager().readConfiguration(new FileInputStream(logURL.getFile()));
            Logger log = Logger.getLogger(Ejecutor.class.getName());
            ejec.setLogObject(log);
            ejec.IniciarEjecutor(12164, 20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
