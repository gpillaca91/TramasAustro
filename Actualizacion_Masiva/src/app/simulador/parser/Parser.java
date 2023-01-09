/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.parser;

/**
 *
 * @author jbueno
 */

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;

import com.alignet.isoparser.bean.ISOFields;
import com.alignet.isoparser.impl.ISOParserImpl;

import app.simulador.util.CryptoUtil;
import app.simulador.util.Operations;
import app.simulador.util.Util;
import app.isoparser.impl.ISOParserImpl2;

public class Parser {
	private String fileName;

	private static Logger log = Logger.getLogger("WS_GATEWAY_AUSTRO");

	private void getFile() {
		URL url = Parser.class.getClassLoader().getResource("iso8583BCDMaster.xml");
		log.info("ruta: " + url.toString());
		this.fileName = url.toString();
	}

	public ISOFields consiguiendoISOFields(byte[] iso) {
		log.info("inicio - consiguiendoISOFields");
		getFile();
		ISOParserImpl2 iSOParserImpl2 = new ISOParserImpl2();
		ISOFields isoFields = null;
		try {
			log.info("ISO : ");
			Util.mostrarBytes(iso);
			isoFields = iSOParserImpl2.buildMessageFrombyteArray(iso, this.fileName);
		} catch (ISOException e) {
			e.printStackTrace();
			log.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		log.info("fin - consiguiendoISOFields");
		return isoFields;
	}

	public byte[] prepararMensaje(ISOFields isoFields) {
		log.info("Inicio - prepararMensaje");
		ISOParserImpl iSOParserImpl = new ISOParserImpl();
		byte[] respuesta = null;
		log.info("isoFields.getFieldMTI(): " + isoFields.getFieldMTI());
		log.info("isoFields.getFieldMTI().trim().isEmpty(): " + isoFields.getFieldMTI().trim().isEmpty());
		if (isoFields.getFieldMTI() != null && !isoFields.getFieldMTI().trim().isEmpty())
			try {
				String mti = isoFields.getFieldMTI();
				String prcode = isoFields.getField3();
				log.info("La operacion recibida tiene el mti : " + mti + " y el prcode : " + prcode);
				System.out.println("La operacion recibida tiene el mti : " + mti + " y el prcode : " + prcode);
				Operations operations = Operations.getOperation(mti);
				log.info("Se armara una trama de : " + operations.getDescription());
				System.out.println("Se armara una trama de : " + operations.getDescription());
				switch (operations) {
				case ECHO:
					respuesta = iSOParserImpl.buidISOMessage(ResponseBuilder.buildEchoResponse(isoFields),
							this.fileName);
					break;
				case SALE:
					respuesta = iSOParserImpl.buidISOMessage(ResponseBuilder.buildSaleResponse(isoFields),
							this.fileName);
					break;
				case REFUND:
					respuesta = iSOParserImpl.buidISOMessage(ResponseBuilder.buildRefundResponse(isoFields),
							this.fileName);
					break;
				case REVERSE:
					respuesta = iSOParserImpl.buidISOMessage(ResponseBuilder.buildReverseResponse(isoFields),
							this.fileName);
					break;
				case RECONCILIATION_A:
				case RECONCILIATION_B:
					respuesta = iSOParserImpl.buidISOMessage(ResponseBuilder.buildReconciliationResponse(isoFields),
							this.fileName);
					break;
				default:
					respuesta = iSOParserImpl.buidISOMessage(ResponseBuilder.buildDefault(isoFields), this.fileName);
					break;
				}
				if (operations == Operations.RECONCILIATION_A || operations == Operations.RECONCILIATION_B) {
					String correlationId = CryptoUtil.getStringSHA(prcode + isoFields.getField11());
					System.out.println("CORRELATION ID : " + correlationId);
					log.info("CORRELATION ID_LOG : " + correlationId);
				} else {
					String correlationId = CryptoUtil
							.getStringSHA(isoFields.getField2() + prcode + isoFields.getField11());
					System.out.println("CORRELATION ID : " + correlationId);
					log.info("CORRELATION ID_LOG : " + correlationId);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				log.error(e);
			} catch (SecurityException e) {
				e.printStackTrace();
				log.error(e);
			} catch (ISOException e) {
				e.printStackTrace();
				log.error(e);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				log.error(e);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				log.error(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				log.error(e);
			}
		log.info("Fin - prepararMensaje");
		return respuesta;
	}

}
