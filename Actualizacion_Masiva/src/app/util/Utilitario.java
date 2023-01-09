/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.util;

import static app.consola.Ejecutor.QUOTE;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author jbueno
 */
public class Utilitario {
    public String removeCaracteresRarosCadena (String campo) {
        campo = campo.replaceAll("^" + QUOTE, "").replaceAll(QUOTE + "$", "");
        return campo;
    }
}
