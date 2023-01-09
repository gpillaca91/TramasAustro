/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.isoparser.impl;

/**
 *
 * @author jbueno
 */
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;

import com.alignet.isoparser.ISOParser;
import com.alignet.isoparser.bean.ISOFields;

public class ISOParserImpl2 implements ISOParser {
	private Properties properties;

	private static Logger log = Logger.getLogger("WS_GATEWAY_AUSTRO");

	public byte[] buidISOMessage(Object isoFields, String isoFileName) throws ISOException, NoSuchMethodException,
			InvocationTargetException, IllegalArgumentException, SecurityException, IllegalAccessException {
		ISOFields pureIsoFields = new ISOFields();
		if (isoFields instanceof ISOFields) {
			pureIsoFields = (ISOFields) isoFields;
			ISOMsg isoMsg = new ISOMsg();
			GenericPackager packager = new GenericPackager(isoFileName);
			isoMsg.setMTI(pureIsoFields.getFieldMTI());
			isoMsg.setPackager((ISOPackager) packager);
			int fieldMax = 128;
			for (int i = 0; i <= fieldMax; i++) {
				Object valorReservado = null;
				valorReservado = isoFields.getClass().getMethod("getField" + i, new Class[0]).invoke(isoFields,
						new Object[0]);
				if (valorReservado != null)
					log.info("valorReservado: " + valorReservado);
				if (valorReservado != null && valorReservado.toString().trim().length() != 0)
					isoMsg.set(i, valorReservado.toString());
			}
			byte[] data = null;
			data = isoMsg.pack();
			return data;
		}
		throw new ClassCastException("The object isn't an instance of " + pureIsoFields.getClass().getName());
	}

	public ISOFields buildMessageFrombyteArray(byte[] iso, String isoFileName)
			throws ISOException, IOException, Exception {
		log.info("inicio - buildMessageFrombyteArray");
		ISOFields isoFields = new ISOFields();
		GenericPackager packager = new GenericPackager(isoFileName);
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager((ISOPackager) packager);
		isoMsg.unpack(iso);
		log.info("unpack: " + ReflectionToStringBuilder.toString(isoMsg));
		ReflectionToStringBuilder.toString(isoMsg);
		for (int i = 0; i <= isoMsg.getMaxField(); i++) {
			if (isoMsg.hasField(i))
				if (i == 0) {
					isoFields.getClass().getMethod("setFieldMTI", new Class[] { String.class }).invoke(isoFields,
							new Object[] { isoMsg.getString(i) });
					log.info(isoMsg.getString(i));
				} else {
					isoFields.getClass().getMethod("setField" + String.valueOf(i), new Class[] { String.class })
							.invoke(isoFields, new Object[] { isoMsg.getString(i) });
					log.info(isoMsg.getString(i));
				}
		}
		log.info("fin - buildMessageFrombyteArray");
		return isoFields;
	}
	
	public void setConfiguration(Properties arg0) {}

}
