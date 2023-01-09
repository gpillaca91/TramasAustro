package com.alignet.flotas.cliente.mensaje;

public class MensajeAnulacion {
	
	private String PAN;
	
	private String codigoProceso;
	
	private int importeTx;
	
	private String idTxTerminal;
	
	private String fechaTxTerminal;

	private String datosPtoServicio;
	
	private String codigoFuncion;
	
	private String codigoRazon;
	
	private String codigoAdquirente;
	
	private String numeroReferencia;
	
	private String idTerminal;
	
	private String codigoComercio;
	
	private MensajeCampo43 establecimiento;
	
	private MensajeCampo48 datosAdicionales;
	
	private String codigoMoneda;
	
	private MensajeCampo56 datosOriginalesTx;
	
	public MensajeAnulacion() {
		this.codigoProceso    = "203000";
		this.datosPtoServicio = "101130113134";
		this.idTerminal       = "99999997";
		this.codigoMoneda     = "604";
	}
	
	public String getPAN() {
		return PAN;
	}

	public String getCodigoProceso() {
		return codigoProceso;
	}

	public int getImporteTx() {
		return importeTx;
	}

	public String getIdTxTerminal() {
		return idTxTerminal;
	}

	public String getFechaTxTerminal() {
		return fechaTxTerminal;
	}

	public String getDatosPtoServicio() {
		return datosPtoServicio;
	}

	public String getCodigoFuncion() {
		return codigoFuncion;
	}

	public String getCodigoRazon() {
		return codigoRazon;
	}

	public String getCodigoAdquirente() {
		return codigoAdquirente;
	}

	public String getNumeroReferencia() {
		return numeroReferencia;
	}

	public String getIdTerminal() {
		return idTerminal;
	}

	public String getCodigoComercio() {
		return codigoComercio;
	}

	public MensajeCampo43 getEstablecimiento() {
		return establecimiento;
	}

	public MensajeCampo48 getDatosAdicionales() {
		return datosAdicionales;
	}

	public String getCodigoMoneda() {
		return codigoMoneda;
	}

	public void setPAN(String pAN) {
		PAN = pAN;
	}

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public void setImporteTx(int importeTx) {
		this.importeTx = importeTx;
	}

	public void setIdTxTerminal(String idTxTerminal) {
		this.idTxTerminal = idTxTerminal;
	}

	public void setFechaTxTerminal(String fechaTxTerminal) {
		this.fechaTxTerminal = fechaTxTerminal;
	}


	public void setDatosPtoServicio(String datosPtoServicio) {
		this.datosPtoServicio = datosPtoServicio;
	}

	public void setCodigoFuncion(String codigoFuncion) {
		this.codigoFuncion = codigoFuncion;
	}

	public void setCodigoRazon(String codigoRazon) {
		this.codigoRazon = codigoRazon;
	}

	public void setCodigoAdquirente(String codigoAdquirente) {
		this.codigoAdquirente = codigoAdquirente;
	}

	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}

	public void setIdTerminal(String idTerminal) {
		this.idTerminal = idTerminal;
	}

	public void setCodigoComercio(String codigoComercio) {
		this.codigoComercio = codigoComercio;
	}

	public void setEstablecimiento(MensajeCampo43 establecimiento) {
		this.establecimiento = establecimiento;
	}

	public void setDatosAdicionales(MensajeCampo48 datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

	public void setCodigoMoneda(String codigoMoneda) {
		this.codigoMoneda = codigoMoneda;
	}

	public MensajeCampo56 getDatosOriginalesTx() {
		return datosOriginalesTx;
	}

	public void setDatosOriginalesTx(MensajeCampo56 datosOriginalesTx) {
		this.datosOriginalesTx = datosOriginalesTx;
	}
	
}
