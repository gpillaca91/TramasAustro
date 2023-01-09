/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.util;

/**
 *
 * @author jbueno
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.util.encoders.Hex;

public class CryptoUtil {

	public static String getStringSHA(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("SHA-512");
			md5.reset();
			md5.update(str.getBytes());
			return new String(Hex.encode(md5.digest()));
		} catch (NoSuchAlgorithmException e) {
			System.err.println("ERROR SHA " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
