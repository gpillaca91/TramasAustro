package com.alignet.flotas.cliente.util;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import com.alignet.flotas.cliente.mensaje.MensajeAutorizacion;
import com.alignet.flotas.cliente.mensaje.MensajeCampo43;
import com.alignet.flotas.cliente.mensaje.MensajeCampo48;
import com.google.common.base.Function;
import com.google.common.base.Strings;

public class ConvertidorMsgAutorizacion {
	
	public static ISOMsg convertirMensajeAutorizacion(MensajeAutorizacion mensaje) {
		
		Function<MensajeAutorizacion, ISOMsg> convertidor = new Function<MensajeAutorizacion, ISOMsg>() {
			
			public ISOMsg apply(MensajeAutorizacion mensaje) {
				ISOMsg mensajeISO = new ISOMsg();
				try {
					mensajeISO.setMTI("1100");
					mensajeISO.set(2, mensaje.getPAN());
					mensajeISO.set(3, mensaje.getCodigoProceso());
					mensajeISO.set(4, Strings.padStart(String.valueOf(mensaje.getImporteTx()), 12, '0'));
					mensajeISO.set(11, mensaje.getIdTxTerminal());
					mensajeISO.set(12, mensaje.getFechaTxTerminal());
					mensajeISO.set(14, mensaje.getFechaExpTarjeta());
					mensajeISO.set(18, mensaje.getMCC());
					mensajeISO.set(22, mensaje.getDatosPtoServicio());
					mensajeISO.set(24, mensaje.getCodigoFuncion());
					mensajeISO.set(26, mensaje.getMCCInternacional());
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
					mensajeISO.set(53, mensaje.getControlSeguridad());
					
				} catch (ISOException e) {
					e.printStackTrace();
				}
				return mensajeISO;
			}
		};
		return convertidor.apply(mensaje);
	}

	public static ISOMsg convertirMensajeCampo43(MensajeCampo43 campo43) {
		
		Function<MensajeCampo43, ISOMsg> convertidor = new Function<MensajeCampo43, ISOMsg>() {
			
			public ISOMsg apply(MensajeCampo43 campo43) {
				ISOMsg mensajeISO = new ISOMsg(43);
				try {
					mensajeISO.set(1, Strings.padEnd(campo43.getRazonSocial(), 25, ' '));
					mensajeISO.set(2, Strings.padEnd(campo43.getCiudad(), 13, ' '));
					mensajeISO.set(3, campo43.getPais());
				} catch (ISOException e) {
					e.printStackTrace();
				}
				
				return mensajeISO;
			}
		};
		
		return convertidor.apply(campo43);
	}

	public static ISOMsg convertirMensajeCampo48(MensajeCampo48 campo48) {
		
		Function<MensajeCampo48, ISOMsg> convertidor = new Function<MensajeCampo48, ISOMsg>() {
			
			public ISOMsg apply(MensajeCampo48 campo48) {
				ISOMsg mensajeISO = new ISOMsg(48);
				try {
					mensajeISO.set(1, Strings.padEnd(campo48.getPlaca(), 9, ' '));
					mensajeISO.set(2, Strings.padStart(campo48.getKilometraje(), 6, '0'));
					mensajeISO.set(3, Strings.padEnd(campo48.getDocumento(), 18, ' '));
					mensajeISO.set(4, Strings.padEnd(campo48.getClave(), 12, ' '));
					mensajeISO.set(5, Strings.padEnd(campo48.getUnidadEjecutora(), 20, ' '));
					mensajeISO.set(6, Strings.padEnd(campo48.getSaldo(), 12, ' '));
	    			
	    			String nombreTitular = Strings.padEnd(campo48.getNombreTitular(), 20, ' ');
	    			mensajeISO.set(7, nombreTitular.substring(0, 20));
	    			
	    			mensajeISO.set(8, Strings.padStart(campo48.getCantidadGalones(), 6, '0'));
	    			mensajeISO.set(9, campo48.getClaseCombustible());
	    			mensajeISO.set(10, Strings.padEnd(campo48.getCodigoEmpresa(), 6, ' '));
	    			mensajeISO.set(11, Strings.padEnd(campo48.getCodigoEmisor(), 25, ' '));
	    			
				} catch (ISOException e) {
	                e.printStackTrace();
                }
				
				return mensajeISO;
			}
		};
		
		return convertidor.apply(campo48);
	}
}
