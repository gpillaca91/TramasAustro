/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.util;

/**
 *
 * @author jbueno
 */
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Util {

	private static Logger log = Logger.getLogger("WS_GATEWAY_AUSTRO");

	public static void mostrarBytes(byte[] array) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++)
			buffer.append(array[i] + " ");
		log.debug(buffer.toString());
	}

	public static InputStream getFilePath(String fileName, Class<?> classArg) {
		InputStream inputStream = classArg.getClassLoader().getResourceAsStream(fileName);
		return inputStream;
	}

	public static Properties loadProperties(InputStream filepath) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(filepath);
		DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
		Properties properties = new Properties();
		properties.load(dataInputStream);
		return properties;
	}

	public static List<String> getListFromPropertie(String propertieName, Properties properties) {
		List<String> values = new ArrayList<String>();
		System.out.println("Total de " + propertieName + " a cargar : "
				+ properties.getProperty(propertieName + ".LIST.SIZE").toString().trim());
		int size = Integer.valueOf(properties.getProperty(propertieName + ".LIST.SIZE").toString().trim()).intValue();
		for (int i = 0; i < size; i++)
			values.add(properties.getProperty(propertieName + "." + i));
		return values;
	}

	public static boolean compareWithValuesInList(List<String> list, String value) {
		for (String item : list) {
			if (item.equals(value))
				return false;
		}
		return true;
	}

	public static boolean validarCabecera(String cabecera, int lengthFromBytes) {
		boolean esValido = false;
		String valorFijo = cabecera.substring(0, 6);
		if (valorFijo.equals("160102")) {
			esValido = true;
			cabecera = cabecera.substring(6);
			int length = Integer.parseInt(cabecera.substring(0, 5));
			if (length == lengthFromBytes) {
				esValido = true;
				cabecera = cabecera.substring(5);
				if (cabecera.equals("000000000000000000000000000000000")) {
					esValido = true;
				} else {
					esValido = false;
				}
			} else {
				esValido = false;
			}
		} else {
			esValido = false;
		}
		return esValido;
	}

	public static String generarCabeceraRespuesta(int lengthFromBytes) {
		String cabecera = "";
		String lengthHex = decToHex(lengthFromBytes);
		cabecera = completando(String.valueOf(lengthHex), 4, true, "0") + "160102"
				+ completando(String.valueOf(lengthFromBytes), 4, true, "0") + "3000000000000000000000000000000000";
		return cabecera;
	}

	public static String decToHex(int num) {
		String str = Integer.toHexString(num);
		System.out.println("Method 1: Decimal to hexadecimal: " + str);
		return str;
	}

	public static String completando(String valor, int longitud, boolean esValorAlineadoDerecha,
			String caracterRelleno) {
		StringBuffer espacios = new StringBuffer("");
		if (valor == null)
			return "NULL";
		if (valor.length() <= longitud) {
			for (int i = 0; i < longitud - valor.length(); i++)
				espacios.append(caracterRelleno);
		} else {
			log.info("[*ERROR] El valor tiene una longitud mayor a " + cadena(longitud));
			return "ERROR";
		}
		if (esValorAlineadoDerecha)
			return espacios.toString() + valor;
		return valor + espacios.toString();
	}

	public static String cadena(int numero) {
		StringBuffer transformers = new StringBuffer();
		transformers.append(numero);
		return transformers.toString();
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		return sb.toString();
	}

	public static void convertToEbcdic(byte[] message, byte[] data, int ini, int end) {
		int j = 0;
		for (int i = ini; i <= end; i++) {
			message[i] = data[j];
			j++;
		}
	}

}