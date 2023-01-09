package com.alignet.flotas.cliente.mensaje;

public class MensajeAutorizacion {
	
	private String PAN;
	
	private String codigoProceso; // enviar 203000
	
	private int importeTx;
	
	private String idTxTerminal;
	
	private String fechaTxTerminal; // yyMMddHHmmss
	
	private String fechaExpTarjeta; // yyMM
	
	private String MCC; // Codigo Actividad Nacional del Establecimiento, enviar 5540
	
	private String datosPtoServicio; // Datos del punto de servicio, enviar 101130113134
	
	private String codigoFuncion; // Datos del codigo de funcion, enviar 100
	
	private String MCCInternacional; // Codigo Actividad Nacional del Establecimiento (formato internacional), enviar 5541
	
	private String codigoAdquirente; // Codigo del adquirente
	
	private String numeroReferencia;
	
	private String idTerminal;
	
	private String codigoComercio;
	
	private MensajeCampo43 establecimiento;
	
	private MensajeCampo48 datosAdicionales;
	
	private String codigoMoneda;
	
	private String controlSeguridad;

	public MensajeAutorizacion() {
		this.codigoProceso    = "203000";
		this.MCC              = "5540";
//		this.datosPtoServicio = "101130113134";
		this.datosPtoServicio = "200101254144";
		this.codigoFuncion    = "100";
		this.MCCInternacional = "5541";
//		this.idTerminal       = "99999997";
		this.idTerminal		  = "01336675";
		this.codigoMoneda	  = "604";
		this.controlSeguridad = "0000000000000000";
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

	public String getFechaExpTarjeta() {
		return fechaExpTarjeta;
	}

	public String getMCC() {
		return MCC;
	}

	public String getDatosPtoServicio() {
		return datosPtoServicio;
	}

	public String getCodigoFuncion() {
		return codigoFuncion;
	}

	public String getMCCInternacional() {
		return MCCInternacional;
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

	public String getControlSeguridad() {
		return controlSeguridad;
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

	public void setFechaExpTarjeta(String fechaExpTarjeta) {
		this.fechaExpTarjeta = fechaExpTarjeta;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public void setDatosPtoServicio(String datosPtoServicio) {
		this.datosPtoServicio = datosPtoServicio;
	}

	public void setCodigoFuncion(String codigoFuncion) {
		this.codigoFuncion = codigoFuncion;
	}

	public void setMCCInternacional(String mCCInternacional) {
		MCCInternacional = mCCInternacional;
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

	public void setControlSeguridad(String controlSeguridad) {
		this.controlSeguridad = controlSeguridad;
	}

}
