/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author jbueno
 */
public class Provider {
    private String name;
    private String desc;
    private Databases basesDatos;
    private ConfigManager configuracion;


    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Databases getBasesDatos() {
        return basesDatos;
    }

    public void setBasesDatos(Databases basesDatos) {
        this.basesDatos = basesDatos;
    }

    public ConfigManager getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfigManager configuracion) {
        this.configuracion = configuracion;
    }
    
    
    
}
