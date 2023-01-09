/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.consola;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;

public class JPanelListLog extends JPanel
  implements Runnable{
     private int _maxLines = 10000;
  private int _linesShown = 0;

  private boolean _running = false;
  private int _updateInterval = 1000;
  private File _file;
  private long _filePointer;
  private ListLogPanel _asta = new ListLogPanel();

  private DateFormat frmDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  private ArrayList _rules = new ArrayList();

  private String fLine = null;
  private JFrame _owner;

  public JPanelListLog()
  {
    setLayout(new BorderLayout());
    add(this._asta, "Center");

    setVisible(true);
  }

  public void run() {
    try {
      while (this._running) {
        Thread.sleep(this._updateInterval);
        long len = this._file.length();
        if (len < this._filePointer)
        {
          appendMessage("Log file was reset. Restarting logging from start of file.", 1);
          this._filePointer = len;
        } else if (len > this._filePointer)
        {
          RandomAccessFile raf = new RandomAccessFile(this._file, "r");
          raf.seek(this._filePointer);
          String line = "";
          String prnLine = "";
          int typeMsg = 0;
          while ((line = raf.readLine()) != null)
          {
            line = line.replaceAll(String.valueOf('\r'), "");
            line = line.replaceAll(String.valueOf('\n'), "");

            if (line != "");
            int pAppStr = line.indexOf("app.sisesat");

            if (pAppStr != -1) {
              if (prnLine != "") {
                appendMessage("[" + this.fLine + "] " + prnLine, typeMsg);

                prnLine = "";
                typeMsg = 0;
              }
              this.fLine = line.substring(0, pAppStr - 1);
            }
            else if ((line.indexOf("WARNING:") != -1) || (line.indexOf("ADVERTENCIA:") != -1) || (line.indexOf("SEVERITY:") != -1) || (line.indexOf("GRAVE:") != -1))
            {
              prnLine = line.substring(line.indexOf(":") + 1).trim();
              typeMsg = 1;
            } else if (line.indexOf("INFO:") != -1) {
              prnLine = line.substring(line.indexOf(":") + 1).trim();
              typeMsg = 0;
            } else if (this.fLine != null) {
              prnLine = prnLine + line.trim();
              typeMsg = 2;
            } else {
              prnLine = prnLine + line.trim();
              typeMsg = 2;
            }

          }

          if (prnLine != null) {
            appendMessage("[" + this.fLine + "] " + prnLine, typeMsg);
          }
          this._filePointer = raf.getFilePointer();
          raf.close();
        }
      }
    }
    catch (Exception e) {
      appendMessage("Fatal error reading log file, log tailing has stopped.", 2);
    }
  }

  public void setOwner(JFrame owner) {
    this._owner = owner;
  }

  public void setLogFile(File file) throws IOException {
    this._file = file;

    if ((!(this._file.exists())) || (this._file.isDirectory()) || (!(this._file.canRead()))) {
      throw new IOException("Can't read this file.");
    }

    this._filePointer = this._file.length();
  }

  public void appendMessage(String message, int level) {
    try {
      this._asta.append(message + "\r\n", level);

      if (++this._linesShown > this._maxLines)
      {
        this._asta.remove();
        this._linesShown -= 1;
      }
    }
    catch (BadLocationException e) {
      throw new RuntimeException("Tried to add a new line to a bad place.");
    }
  }

  public void setRunning(boolean run) {
    this._running = run;
  }
}
