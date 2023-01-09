package com.alignet.flotas.cliente.mensaje;

public class MensajeReporteTxs {
	
	private String codigoProceso;

	private String fechaTxTerminal;
	
	private String MCC;
	
	private String datosPtoServicio;
	
	private String codigoFuncion;
	
	private String MCCInternacional;
	
	private String idTerminal;
	
	public MensajeReporteTxs() {
		this.codigoProceso    = "203000";
		this.MCC              = "5540";
		this.datosPtoServicio = "101130113134";
		this.codigoFuncion    = "301";
		this.MCCInternacional = "5541";
		this.idTerminal       = "99999997";
	}
	
	public String getCodigoProceso() {
		return codigoProceso;
	}

	public String getFechaTxTerminal() {
		return fechaTxTerminal;
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

	public void setCodigoProceso(String codigoProceso) {
		this.codigoProceso = codigoProceso;
	}

	public void setFechaTxTerminal(String fechaTxTerminal) {
		this.fechaTxTerminal = fechaTxTerminal;
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

	public String getMCCInternacional() {
		return MCCInternacional;
	}

	public String getIdTerminal() {
		return idTerminal;
	}

	public void setMCCInternacional(String mCCInternacional) {
		MCCInternacional = mCCInternacional;
	}

	public void setIdTerminal(String idTerminal) {
		this.idTerminal = idTerminal;
	}
}
