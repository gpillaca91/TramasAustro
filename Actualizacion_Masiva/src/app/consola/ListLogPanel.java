/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.consola;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class ListLogPanel extends JScrollPane
{
    private JTextPane _textPane = new JTextPane(new DefaultStyledDocument());
  private SimpleAttributeSet _attributeSet = new SimpleAttributeSet();

  public ListLogPanel()
  {
    this._textPane.setEditable(false);
    this._textPane.setFont(new Font("Monospaced", 0, 11));
    setViewportView(this._textPane);
  }

  private void scrollToBottom() {
    this._textPane.setCaretPosition(this._textPane.getDocument().getLength());
  }

  public JTextPane getTextPane() {
    return this._textPane;
  }

  public void append(String str, int level)
    throws BadLocationException
  {
    if (level != 0)
      StyleConstants.setForeground(this._attributeSet, Color.RED);
    this._textPane.getDocument().insertString(this._textPane.getDocument().getLength(), str, this._attributeSet);
    StyleConstants.setForeground(this._attributeSet, Color.BLACK);
    scrollToBottom();
  }

  public void remove() throws BadLocationException {
    int len = this._textPane.getText().indexOf(10);
    getDocument().remove(0, len);
  }

  public Document getDocument() {
    return this._textPane.getDocument();
  }

  public SimpleAttributeSet getSimpleAttributeSet() {
    return this._attributeSet;
  }
}
