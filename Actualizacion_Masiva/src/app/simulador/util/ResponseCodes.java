/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.simulador.util;

/**
 *
 * @author jbueno
 */

public enum ResponseCodes {
	
	APROVED("00", "APROBADA", "Aprobado."), 
	INVALID_COMERCE("03", "COMERCIO INVALIDO", "Comercio invalido"), 
	EXPIRED_CARD("54", "TARJETA VENCIDA", "Tarjeta vencida."), 
	TRANSACTION_NOT_PERMITED("57", "TRANSACCION NO PERMITIDA", "Transaccion no permitida"), 
	INVALID_CARD("14", "TARJETA INVALIDA", "Tarjeta invalida o cedula no corresponde con titular."), 
	SYSTEM_ERROR("96", "ERROR EN SISTEMA", "Error en sistema.");
	  
	private final String code;

	private final String description;

	private final String detail;

	ResponseCodes(String code, String description, String detail) {
		this.code = code;
		this.description = description;
		this.detail = detail;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

	public String getDetail() {
		return this.detail;
	}

}
