package com.alignet.flotas.cliente.util;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.alignet.flotas.cliente.mensaje.MensajeReporteTxs;
import com.google.common.base.Function;

/**
 * Created by Fabio Salas Millones on 17/06/2014.
 */
public class ConvertidorMsgReporte {
	
	public static ISOMsg convertirMensajeReporte(MensajeReporteTxs mensaje) {
		
		Function<MensajeReporteTxs, ISOMsg> convertidor = new Function<MensajeReporteTxs, ISOMsg>() {

			public ISOMsg apply(MensajeReporteTxs mensaje) {
				ISOMsg mensajeISO = new ISOMsg();
				try {
	                mensajeISO.setMTI("1300");
	                mensajeISO.set(12, mensaje.getFechaTxTerminal());
	                mensajeISO.set(24, mensaje.getCodigoFuncion());
	                mensajeISO.set(41, mensaje.getIdTerminal());
	                
                } catch (ISOException e) {
	                e.printStackTrace();
                }
				
	            return mensajeISO;
            }
			
		};
		
		return convertidor.apply(mensaje);
	}
}
