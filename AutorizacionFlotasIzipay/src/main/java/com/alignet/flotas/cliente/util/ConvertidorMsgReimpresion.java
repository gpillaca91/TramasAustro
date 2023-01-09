package com.alignet.flotas.cliente.util;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.alignet.flotas.cliente.mensaje.MensajeReimpresion;
import com.google.common.base.Function;

/**
 * Created by Fabio Salas Millones on 17/06/2014.
 */
public class ConvertidorMsgReimpresion {

	public static ISOMsg convertirMensajeReimpresion(MensajeReimpresion mensaje) {
		
		Function<MensajeReimpresion, ISOMsg> convertidor = new Function<MensajeReimpresion, ISOMsg>() {

			public ISOMsg apply(MensajeReimpresion mensaje) {
				ISOMsg mensajeISO = new ISOMsg();
				try {
	                mensajeISO.setMTI("1300");
	                mensajeISO.set(2, "");
	                mensajeISO.set(38, "");
	                //mensajeISO.set(3, mensaje.getCodigoProceso());
	                mensajeISO.set(11, mensaje.getIdTxTerminal());
	                //mensajeISO.set(12, mensaje.getFechaTxTerminal());
	                //mensajeISO.set(18, mensaje.getMCC());
	                mensajeISO.set(24, mensaje.getCodigoFuncion());
	                //mensajeISO.set(26, mensaje.getMCCInternacional());
	                mensajeISO.set(41, mensaje.getIdTerminal());
	                System.out.println("mensajeISO "+ mensajeISO);
                } catch (ISOException e) {
	                e.printStackTrace();
                }
	            return mensajeISO;
            }
			
		};
		
		return convertidor.apply(mensaje);
	}
}
