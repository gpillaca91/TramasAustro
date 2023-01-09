/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.util;

/**
 *
 * @author jbueno
 */

public enum Operations {

	ECHO("0800", "990000", "ECHO"), 
	SALE("0100", "000000", "Compra"), 
	REFUND("0200", "020000", "Anulacion"), 
	REFUND_B("0200", "220000", "Anulacion_B"), 
	REVERSE("0400", "000000", "Reverso"), 
	RECONCILIATION_A("0500", "920000", "Cierre Lote"), 
	RECONCILIATION_B("0500", "960000", "Cierre Lote");
	
	private final String mti;

	private final String prcode;

	private final String description;
	
	Operations(String mti, String prcode, String description) {
		this.mti = mti;
		this.prcode = prcode;
		this.description = description;
	}

	public String getMti() {
		return this.mti;
	}

	public String getPrcode() {
		return this.prcode;
	}

	public String getDescription() {
		return this.description;
	}

	public static Operations getOperation(String mti) {
		for (Operations operation : values()) {
			if (operation.getMti().equals(mti))
				return operation;
		}
		return null;
	}

}

