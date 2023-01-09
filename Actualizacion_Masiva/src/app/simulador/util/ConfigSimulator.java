/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.util;

/**
 *
 * @author jbueno
 */
import java.util.List;

public class ConfigSimulator {

	private static List<String> commerceList;

	private static List<String> binList;

	public static List<String> getCommerceList() {
		return commerceList;
	}

	public static void setCommerceList(List<String> commerceList) {
		ConfigSimulator.commerceList = commerceList;
	}

	public static List<String> getBinList() {
		return binList;
	}

	public static void setBinList(List<String> binList) {
		ConfigSimulator.binList = binList;
	}

}