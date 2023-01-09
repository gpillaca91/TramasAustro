/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alignet.flotas.cliente.launcher;

import com.alignet.flotas.cliente.mensaje.MensajeAutorizacion;
import com.alignet.flotas.cliente.mensaje.MensajeCampo43;
import com.alignet.flotas.cliente.mensaje.MensajeCampo48;
import com.alignet.flotas.cliente.servicio.ServicioMensaje;
import com.google.common.base.Strings;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

/**
 *
 * @author jbueno
 */
public class AuthorizationLauncher {

        // private static MensajeAutorizacion mensajeAutorizacion;
        //
        // private static ServicioMensaje servicioMensaje;
        // private static ISOMsg respuestaAutorizacion;
        public static MensajeAutorizacion setUp() {

                MensajeAutorizacion mensajeAutorizacion = new MensajeAutorizacion();
                mensajeAutorizacion.setPAN("480858******0142");
                mensajeAutorizacion.setImporteTx(2000);
                String idTx = Integer.toString(60000 + (new Random().nextInt(10000)));
                mensajeAutorizacion.setIdTxTerminal(idTx);
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
                String fechaTx = sdf.format(new Date());
                mensajeAutorizacion.setFechaTxTerminal(fechaTx);
                mensajeAutorizacion.setFechaExpTarjeta("2710");
                mensajeAutorizacion.setCodigoAdquirente("06028011");
                String numeroReferencia = Strings.padEnd(mensajeAutorizacion.getIdTxTerminal(), 12, ' ');
                // this.mensajeAutorizacion.setNumeroReferencia(numeroReferencia);
                mensajeAutorizacion.setNumeroReferencia("463872      ");
                mensajeAutorizacion.setCodigoComercio("666000002");
                MensajeCampo43 datosEstablecimiento = new MensajeCampo43();
                // datosEstablecimiento.setRazonSocial("REPSOL TIKI");
                datosEstablecimiento.setRazonSocial("PRUEBAS IZIPAY  ENV");
                datosEstablecimiento.setCiudad("LIMA         ");
                datosEstablecimiento.setPais("PE");
                mensajeAutorizacion.setEstablecimiento(datosEstablecimiento);
                MensajeCampo48 datosAdicionales = new MensajeCampo48();
                // datosAdicionales.setPlaca("VV-1111");
                datosAdicionales.setPlaca("123456789");
                // datosAdicionales.setKilometraje("1");
                datosAdicionales.setKilometraje("036839");
                // datosAdicionales.setDocumento("45876254");
                datosAdicionales.setDocumento("123456789         ");
                // datosAdicionales.setClave("0000");
                datosAdicionales.setClave("pinCond1234 ");
                // datosAdicionales.setUnidadEjecutora("");
                datosAdicionales.setUnidadEjecutora("UnidaEjecutora12345 ");
                // datosAdicionales.setSaldo("");
                datosAdicionales.setSaldo("12345678    ");
                // datosAdicionales.setNombreTitular("");
                datosAdicionales.setNombreTitular("NombredelConductor  ");
                // datosAdicionales.setCantidadGalones("1000");
                datosAdicionales.setCantidadGalones("001000");
                datosAdicionales.setClaseCombustible("90");
                datosAdicionales.setCodigoEmpresa("");
                datosAdicionales.setCodigoEmisor("");
                mensajeAutorizacion.setDatosAdicionales(datosAdicionales);
                return mensajeAutorizacion;
        }

        public static void main(String[] args) {
                ServicioMensaje servicioMensaje = new ServicioMensaje();
                MensajeAutorizacion mensajeAutorizacion = setUp();

                ISOMsg respuestaAutorizacion = servicioMensaje.procesarMensajeAutorizacion(mensajeAutorizacion);

                System.out.println("respuesta : " + respuestaAutorizacion.toString());

                System.out.println("children: " + respuestaAutorizacion.getChildren());
                try {
                        Map mapa43 = respuestaAutorizacion.getComponent(43).getChildren();
                        ISOField f43_1 = (ISOField) mapa43.get(1);
                        ISOField f43_2 = (ISOField) mapa43.get(2);
                        ISOField f43_3 = (ISOField) mapa43.get(3);
                        String cadenaCampo_43 = f43_1.getValue().toString() + "" + f43_2.getValue().toString() + ""
                                        + f43_3.getValue().toString();

                        Map mapa48 = respuestaAutorizacion.getComponent(48).getChildren();
                        String cadenaCampo_48 = "";
                        for (int i = 1; i <= 11; i++) {

                                ISOField val48 = (ISOField) mapa48.get(i);
                                cadenaCampo_48 = cadenaCampo_48 + val48.getValue().toString();
                        }

                        System.out.println("respuesta MTI: [" + respuestaAutorizacion.getMTI() + "]");
                        System.out.println("respuesta 3: ["
                                        + respuestaAutorizacion.getComponent(3).getValue().toString() + "]");
                        System.out.println("respuesta 43 : [" + cadenaCampo_43 + "]");
                        System.out.println("respuesta 48 : [" + cadenaCampo_48 + "]");
                } catch (ISOException ex) {
                        Logger.getLogger(AuthorizationLauncher.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

}
