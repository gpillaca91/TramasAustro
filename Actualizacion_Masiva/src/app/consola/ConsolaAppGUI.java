/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.consola;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

public class ConsolaAppGUI extends JFrame {

    private JButton btNoDeclared;
    private JButton btStart;
    private JButton btStop;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JSeparator jSeparator1;
    private JPanelListLog listLog;
    private JProgressBar pgBarTrama;
    private File fLog = new File("log", "consola.log");
    private Thread tLog;
    private ConsolaApp oApp = new ConsolaApp();
    private Ejecutor oEje = new Ejecutor();
    public static ConsolaAppGUI frmApp1;

    public ConsolaAppGUI() {
        initComponents();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.btStart = new JButton();
        this.btStop = new JButton();
        this.btNoDeclared = new JButton();
        this.jSeparator1 = new JSeparator();
        this.jLabel1 = new JLabel();
        this.pgBarTrama = new JProgressBar();
        this.listLog = new JPanelListLog();
        this.listLog.setOwner(this);
        try {
            this.listLog.setLogFile(this.fLog);
        } catch (IOException e) {
            System.out.println(e);
        }

        setDefaultCloseOperation(3);
        setTitle("CONSOLA");
        setBackground(new Color(204, 204, 255));
        setCursor(new Cursor(0));
        setFont(new Font("Arial", 0, 11));
        setForeground(Color.white);
        setName("frmConsolaApp");
        setResizable(false);
        this.btStart.setFont(new Font("MS Sans Serif", 1, 11));
        this.btStart.setText("INICIAR");
        this.btStart.setToolTipText("Iniciar Servicio");
        this.btStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ConsolaAppGUI.this.btStartActionPerformed(evt);
            }

        });
        this.jPanel1.add(this.btStart);

        this.btStop.setText("DETENER");
        this.btStop.setEnabled(false);
        this.btStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ConsolaAppGUI.this.btStopActionPerformed(evt);
            }

        });
        this.jPanel1.add(this.btStop);

        this.jSeparator1.setOrientation(1);
        this.jSeparator1.setPreferredSize(new Dimension(2, 20));
        this.jPanel1.add(this.jSeparator1);

        getContentPane().add(this.jPanel1, "South");

        getContentPane().add(this.listLog, "Center");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 700) / 2, (screenSize.height - 400) / 2, 700, 400);
    }

    private void btStartActionPerformed(ActionEvent evt) {
        startApp();
        this.btStart.setEnabled(false);
        this.btStop.setEnabled(true);
        this.btNoDeclared.setEnabled(true);
    }

    private void btStopActionPerformed(ActionEvent evt) {
        stopApp();
        this.btStop.setEnabled(false);
        this.btNoDeclared.setEnabled(false);
        this.btStart.setEnabled(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                ConsolaAppGUI frmApp = new ConsolaAppGUI();
                frmApp.setVisible(true);
                frmApp1 = frmApp;
            }
        });
    }

    private void startApp() {
        this.listLog.setRunning(true);
        Thread tLog = new Thread(this.listLog);
        tLog.start();

        this.oApp.loadConfiguration();
        this.oEje.setRunning(true);
        this.oEje.setLogObject(this.oApp.getLog());
        this.oEje.IniciarEjecutor(this.oApp.getPuertoCanal(),this.oApp.getTimeOutCanal());
    }

    private void stopApp() {
        this.oEje.setRunning(false);
        this.oApp.closeEverything();
        try {
            Thread.sleep (5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.listLog.setRunning(false);
        frmApp1.setVisible(false);
        frmApp1.dispose();
    }
}
