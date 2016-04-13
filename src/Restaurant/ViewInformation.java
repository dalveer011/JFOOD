
package Restaurant;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import database.DBConnection;
import jfood.HeaderFooter;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Dalvir Sahota
 */
public class ViewInformation extends JFrame{
private String[][] allItems;
private JTable info;
private JButton ok;
private JPanel bottom,center,centerMain;
private DBConnection db = null;
private ResultSet rsView = null;

public ViewInformation(String restName) {
this.initComponents();

//allItems = new String[10][3];
this.setTitle("Items Information for Restaurant ID "+restName);
       
        
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        
//      
      db = new DBConnection();
      String query = "select itemnum,restaurantid,category,item_description,price from menu_items_jfood where restaurantid='"+ restName+"'";
      rsView = db.getInfo(query);
      info = new JTable();
      
      info.setModel(DbUtils.resultSetToTableModel(rsView));
      centerMain.add(info);
      JLabel cat = new JLabel("**********Table for Restaurant "+restName+ "************");
                   cat.setFont(new Font("Times New Roman", 2, 14));
      centerMain.add(cat,BorderLayout.NORTH);
                    this.add(centerMain,BorderLayout.CENTER);
                     
       ok.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
     ViewInformation.this.dispose();
    }
    
        });
       this.setVisible(true);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}private void  initComponents() {
    ok = new JButton("OK");
    bottom = new JPanel(new FlowLayout());
    bottom.add(ok);
    centerMain = new JPanel(new BorderLayout());
    centerMain.add(bottom,BorderLayout.SOUTH);
}
}
