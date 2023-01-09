package com.alignet.flotas.cliente.mensaje;

/**
 * Created by Fabio Salas Millones on 17/06/2014.
 */
public class MensajeReimpresion {

    private String codigoProceso;

    private String idTxTerminal;

    private String fechaTxTerminal;

    private String MCC;

    private String codigoFuncion;

    private String MCCInternacional;

    private String idTerminal;

    public MensajeReimpresion() {
        this.codigoProceso    = "203000";
        this.MCC              = "5540";
        this.MCCInternacional = "5541";
        this.idTerminal       = "99999997";
    }

    public String getCodigoProceso() {
        return codigoProceso;
    }

    public void setCodigoProceso(String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    public String getIdTxTerminal() {
        return idTxTerminal;
    }

    public void setIdTxTerminal(String idTxTerminal) {
        this.idTxTerminal = idTxTerminal;
    }

    public String getFechaTxTerminal() {
        return fechaTxTerminal;
    }

    public void setFechaTxTerminal(String fechaTxTerminal) {
        this.fechaTxTerminal = fechaTxTerminal;
    }

    public String getMCC() {
        return MCC;
    }

    public void setMCC(String MCC) {
        this.MCC = MCC;
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public String getMCCInternacional() {
        return MCCInternacional;
    }

    public void setMCCInternacional(String MCCInternacional) {
        this.MCCInternacional = MCCInternacional;
    }

    public String getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(String idTerminal) {
        this.idTerminal = idTerminal;
    }
}
