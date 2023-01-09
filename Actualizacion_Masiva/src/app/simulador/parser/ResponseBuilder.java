/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.parser;

/**
 *
 * @author jbueno
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.alignet.isoparser.bean.ISOFields;

import app.simulador.util.ConfigSimulator;
import app.simulador.util.ResponseCodes;
import app.simulador.util.Util;

public class ResponseBuilder {

	public static ISOFields buildEchoResponse(ISOFields isoFields) {
		ISOFields fields = new ISOFields();
		fields.setFieldMTI(prepareCommonMtiResponse(isoFields.getFieldMTI()));
		fields.setField11(isoFields.getField11());
		fields.setField39(ResponseCodes.APROVED.getCode());
		fields.setField41(isoFields.getField41());
		return fields;
	}

	public static ISOFields buildSaleResponse(ISOFields isoFields) {
		ISOFields fields = new ISOFields();
		isoFields = valideSale(isoFields);
		String codeRes = isoFields.getField39();
		if (codeRes != null && !codeRes.equals("00")) {
			fields.setField39(codeRes);
		} else {
			fields.setField38(generateAutorizationCode());
			fields.setField39(String.valueOf(ResponseCodes.APROVED.getCode()));
		}
		fields.setField37(generateRRN());
		fields.setField12(getProccesingHour());
		fields.setField13(getProccesingDate());
		fields.setField2(isoFields.getField2());
		fields.setField3(isoFields.getField3());
		fields.setField4(isoFields.getField4());
		fields.setField7(isoFields.getField7());
		fields.setField11(isoFields.getField11());
		fields.setField19(isoFields.getField19());
		fields.setField25(isoFields.getField25());
		fields.setField32(isoFields.getField32());
		fields.setField41(isoFields.getField41());
		fields.setField42(isoFields.getField42());
		fields.setField49(isoFields.getField49());
		fields.setFieldMTI(prepareCommonMtiResponse(isoFields.getFieldMTI()));
		return fields;
	}

	public static ISOFields buildRefundResponse(ISOFields isoFields) {
		ISOFields fields = new ISOFields();
		isoFields = validRefund(isoFields);
		String codeRes = isoFields.getField39();
		if (codeRes != null && !codeRes.equals("00")) {
			fields.setField39(codeRes);
		} else {
			fields.setField39(String.valueOf(ResponseCodes.APROVED.getCode()));
		}
		fields.setField2(isoFields.getField2());
		fields.setField3(isoFields.getField3());
		fields.setField11(isoFields.getField11());
		fields.setField24(isoFields.getField24());
		fields.setField37(isoFields.getField37());
		fields.setField38(isoFields.getField38());
		fields.setField41(isoFields.getField41());
		fields.setField49(isoFields.getField49());
		fields.setFieldMTI(prepareCommonMtiResponse(isoFields.getFieldMTI()));
		fields.setField12(getProccesingHour());
		fields.setField13(getProccesingDate());
		return fields;
	}

	public static ISOFields buildReverseResponse(ISOFields isoFields) {
		ISOFields fields = new ISOFields();
		fields.setField2(isoFields.getField2());
		fields.setField3(isoFields.getField3());
		fields.setField11(isoFields.getField11());
		fields.setField24(isoFields.getField24());
		fields.setField41(isoFields.getField41());
		fields.setField49(isoFields.getField49());
		fields.setFieldMTI(prepareCommonMtiResponse(isoFields.getFieldMTI()));
		fields.setField12(getProccesingHour());
		fields.setField13(getProccesingDate());
		fields.setField37(generateRRN());
		fields.setField39(String.valueOf(ResponseCodes.APROVED.getCode()));
		return fields;
	}

	public static ISOFields buildReconciliationResponse(ISOFields isoFields) {
		ISOFields fields = new ISOFields();
		fields.setField2(isoFields.getField2());
		fields.setField3(isoFields.getField3());
		fields.setField11(isoFields.getField11());
		fields.setField24(isoFields.getField24());
		fields.setField41(isoFields.getField41());
		fields.setField42(isoFields.getField42());
		fields.setFieldMTI(prepareCommonMtiResponse(isoFields.getFieldMTI()));
		fields.setField7("0000000000");
		fields.setField12(getProccesingHour());
		fields.setField13(getProccesingDate());
		fields.setField37(generateRRN());
		fields.setField39(ResponseCodes.APROVED.getCode());
		return fields;
	}

	public static ISOFields buildDefault(ISOFields isoFields) {
		ISOFields fields = new ISOFields();
		fields = isoFields;
		fields.setFieldMTI(prepareCommonMtiResponse(isoFields.getFieldMTI()));
		fields.setField39(ResponseCodes.SYSTEM_ERROR.getCode());
		return fields;
	}

	private static String prepareCommonMtiResponse(String mti) {
		String commonResponseTermination = "10";
		String mtiResponse = null;
		mtiResponse = mti.substring(0, 2) + "10";
		return mtiResponse;
	}

	private static String getProccesingHour() {
		SimpleDateFormat sdformat = new SimpleDateFormat("HHmmss");
		Date hour = new Date();
		return sdformat.format(hour);
	}

	private static String getProccesingDate() {
		SimpleDateFormat sdformat = new SimpleDateFormat("MMdd");
		Date date = new Date();
		return sdformat.format(date);
	}

	private static String generateRRN() {
		long rrnLong = (new Long(System.currentTimeMillis())).longValue();
		String rrn = String.valueOf(rrnLong);
		return rrn.substring(0, 12);
	}

	private static String generateAutorizationCode() {
		long autorizationCodeLong = (new Long(System.currentTimeMillis())).longValue();
		String autorizationCode = String.valueOf(autorizationCodeLong);
		return autorizationCode.substring(0, 6);
	}

	private static ISOFields valideSale(ISOFields fields) {
		String bin = fields.getField2().substring(2, 8);
		String lastPan = fields.getField2();
		String pan = fields.getField2().substring(2);
		if (Util.compareWithValuesInList(ConfigSimulator.getBinList(), bin) || !StringUtils.isNumeric(lastPan)) {
			fields.setField39(ResponseCodes.INVALID_CARD.getCode());
			return fields;
		}
		if (pan.equals("4859510000000028")) {
			fields.setField39(ResponseCodes.TRANSACTION_NOT_PERMITED.getCode());
			return fields;
		}
		if (validateFechaExp(fields.getField14())) {
			fields.setField39(ResponseCodes.EXPIRED_CARD.getCode());
			return fields;
		}
		if (Util.compareWithValuesInList(ConfigSimulator.getCommerceList(), fields.getField42().trim())) {
			fields.setField39(ResponseCodes.INVALID_COMERCE.getCode());
			return fields;
		}
		return fields;
	}

	private static ISOFields validRefund(ISOFields fields) {
		if (!fields.getField42().equals("720000000043173")) {
			fields.setField39(ResponseCodes.INVALID_COMERCE.getCode());
			return fields;
		}
		return fields;
	}

	private static boolean validateFechaExp(String expDate) {
		String month = expDate.substring(2, 4);
		String year = "20" + expDate.substring(0, 2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(month);
		System.out.println(year);
		int currentYear = calendar.get(1);
		System.err.println(currentYear);
		boolean isValidYear = (Integer.parseInt(year) >= currentYear);
		if (isValidYear) {
			int currentMonth = calendar.get(2);
			System.err.println(currentMonth);
			if (Integer.parseInt(month) - 1 > currentMonth || Integer.parseInt(year) > currentYear) {
				System.out.println("ok");
				return false;
			}
			System.out.println("ka");
			return true;
		}
		System.out.println("ko");
		return true;
	}

	public static void main(String[] args) {
		System.out.println(validateFechaExp("1511"));
	}

}
