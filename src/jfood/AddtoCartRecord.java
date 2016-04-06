
package jfood;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Dalvir Sahota
 */
public class AddtoCartRecord extends JFrame {
private JTextField txtItemNo,txtItemdesc,txtItemPrice;
private JSpinner quantity;
private JCheckBox chk;

    public JTextField getTxtItemNo() {
        return txtItemNo;
    }

    public void setTxtItemNo(JTextField txtItemNo) {
        this.txtItemNo = txtItemNo;
    }

    public JTextField getTxtItemdesc() {
        return txtItemdesc;
    }

    public void setTxtItemdesc(JTextField txtItemdesc) {
        this.txtItemdesc = txtItemdesc;
    }

    public JTextField getTxtItemPrice() {
        return txtItemPrice;
    }

    public void setTxtItemPrice(JTextField txtItemPrice) {
        this.txtItemPrice = txtItemPrice;
    }

    public int getQuantity() {
        return (int) quantity.getValue();
    }

    public void setQuantity(JSpinner quantity) {
        this.quantity = quantity;
    }
    public void setCheck(JCheckBox chk) {
        this.chk = chk;
    }
     public JCheckBox getCheck() {
       return  this.chk;
    }
    public  boolean isChecked() {
        return chk.isSelected();
    }

    
}
