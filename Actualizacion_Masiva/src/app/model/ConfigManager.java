/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

/**
 *
 * @author jbueno
 */
public class ConfigManager {
    private String name;
    private String desc;

    private String hostCanal;
    private int portCanal;
    private int timeout;

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


    public String getHostCanal() {
        return hostCanal;
    }

    public void setHostCanal(String hostCanal) {
        this.hostCanal = hostCanal;
    }

    public int getPortCanal() {
        return portCanal;
    }

    public void setPortCanal(int portCanal) {
        this.portCanal = portCanal;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
}
