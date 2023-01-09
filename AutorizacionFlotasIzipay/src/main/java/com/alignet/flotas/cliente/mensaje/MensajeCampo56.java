package com.alignet.flotas.cliente.mensaje;

public class MensajeCampo56 {
	
	private String tipoMensajeTxOriginal;
	
	private String idTxOriginal;
	
	private String fechaTxOriginal;
	
	private String codigoAdquirente;
	
	public MensajeCampo56() {
		this.tipoMensajeTxOriginal = "1100";
	}
	
	public String getTipoMensajeTxOriginal() {
		return tipoMensajeTxOriginal;
	}

	public String getIdTxOriginal() {
		return idTxOriginal;
	}

	public String getFechaTxOriginal() {
		return fechaTxOriginal;
	}

	public String getCodigoAdquirente() {
		return codigoAdquirente;
	}

	public void setTipoMensajeTxOriginal(String tipoMensajeTxOriginal) {
		this.tipoMensajeTxOriginal = tipoMensajeTxOriginal;
	}

	public void setIdTxOriginal(String idTxOriginal) {
		this.idTxOriginal = idTxOriginal;
	}

	public void setFechaTxOriginal(String fechaTxOriginal) {
		this.fechaTxOriginal = fechaTxOriginal;
	}

	public void setCodigoAdquirente(String codigoAdquirente) {
		this.codigoAdquirente = codigoAdquirente;
	}
}
