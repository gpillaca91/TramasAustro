package com.alignet.flotas.cliente.util;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.alignet.flotas.cliente.mensaje.MensajeAnulacion;
import com.alignet.flotas.cliente.mensaje.MensajeCampo56;
import com.google.common.base.Function;
import com.google.common.base.Strings;


public class ConvertidorMsgAnulacion {

	public static ISOMsg convertirMensajeAnulacion(MensajeAnulacion mensaje) {
		
		Function<MensajeAnulacion, ISOMsg> convertidor = new Function<MensajeAnulacion, ISOMsg>() {

			public ISOMsg apply(MensajeAnulacion mensaje) {
				ISOMsg mensajeISO = new ISOMsg();
				
				try {
					mensajeISO.setMTI("1420");
					mensajeISO.set(2, mensaje.getPAN());
					mensajeISO.set(3, mensaje.getCodigoProceso());
					mensajeISO.set(4, Strings.padStart(String.valueOf(mensaje.getImporteTx()), 12, '0'));
					mensajeISO.set(11, mensaje.getIdTxTerminal());
					mensajeISO.set(12, mensaje.getFechaTxTerminal());
					mensajeISO.set(22, mensaje.getDatosPtoServicio());
					mensajeISO.set(24, mensaje.getCodigoFuncion());
					mensajeISO.set(25, mensaje.getCodigoRazon());
					mensajeISO.set(32, mensaje.getCodigoAdquirente());
					mensajeISO.set(37, mensaje.getNumeroReferencia());
					mensajeISO.set(41, mensaje.getIdTerminal());
					mensajeISO.set(42, Strings.padEnd(mensaje.getCodigoComercio(), 15, ' '));
					
					ISOMsg campo43 = ConvertidorMsgAutorizacion.convertirMensajeCampo43(mensaje.getEstablecimiento());
					StringBuffer sb = new StringBuffer();
					for (int i = 1; i <= campo43.getMaxField(); i++) {
						sb.append(campo43.getString(i));
					}
					mensajeISO.set(43, sb.toString());
					
					ISOMsg campo48 = ConvertidorMsgAutorizacion.convertirMensajeCampo48(mensaje.getDatosAdicionales());
					sb = new StringBuffer();
					for (int i = 1; i <= campo48.getMaxField(); i++) {
						sb.append(campo48.getString(i));
					}
					mensajeISO.set(48, sb.toString());
					
					mensajeISO.set(49, mensaje.getCodigoMoneda());
					
					ISOMsg campo56 = ConvertidorMsgAnulacion.convertirMensajeCampo56(mensaje.getDatosOriginalesTx());
					sb = new StringBuffer();
					for (int i = 1; i <= campo56.getMaxField(); i++) {
						sb.append(campo56.getString(i));
					}
					mensajeISO.set(56, sb.toString());
					
				} catch (ISOException e) {
					e.printStackTrace();
				}
	            return mensajeISO;
            }
			
		};
		
		return convertidor.apply(mensaje);
	}
	
	public static ISOMsg convertirMensajeCampo56(MensajeCampo56 campo56) {
		
		Function<MensajeCampo56, ISOMsg> convertidor = new Function<MensajeCampo56, ISOMsg>() {

			public ISOMsg apply(MensajeCampo56 campo56) {
	            ISOMsg mensajeISO = new ISOMsg(56);
	            try {
	            	mensajeISO.set(1, campo56.getTipoMensajeTxOriginal());
	            	mensajeISO.set(2, campo56.getIdTxOriginal());
	            	mensajeISO.set(3, campo56.getFechaTxOriginal());
	            	mensajeISO.set(4, campo56.getCodigoAdquirente());
	            	
	            } catch (ISOException e) {
	            	e.printStackTrace();
	            }
				return mensajeISO;
            }
			
		};
		
		return convertidor.apply(campo56);
	}
}
