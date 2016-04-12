
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
        
//       try {
//                    DataInputStream dis = new DataInputStream(new FileInputStream("addItems.txt"));
//                   //If no items are there in choosen category to check that
//                    int count = 0;
//                     int i = 0; // to increament array
//                    while(dis.available() > 0) {
//                    //Getting data from file to view item no to restaurant owner to make delete more easy
//                    String restIdFromFile = dis.readUTF();
//                    String itemNoFromFile = dis.readUTF();
//                    String catFromFile = dis.readUTF();
//                    if(restId.equals(restIdFromFile) && category.equals(catFromFile)) {
//                        String desc = dis.readUTF();
//                        String price = dis.readUTF();
//                        allItems[i][0] = itemNoFromFile;
//                        allItems[i][1] = desc;
//                        allItems[i][2] = price;
//                         i++;
//                        count++;
//                    }else{
//                    dis.readUTF();
//                    dis.readUTF();
//                    }
//                    }
                  //String[] columns = {"Item no ","Item Description","Item Cost"};
//                    info = new JTable(allItems,columns);
//                    //info.setModel(new DefaultTableModel(allItems, columns));
//                    //Adding table to center of center main
//                    centerMain.add(info);
//                    //Adding center main to center of frame
//                    JLabel cat = new JLabel("**********These Items are for Category :    "+category+"   ************");
//                    cat.setFont(new Font("Times New Roman", 2, 14));
//                    centerMain.add(cat,BorderLayout.NORTH);
//                    this.add(centerMain,BorderLayout.CENTER);
//                    if(count > 0) {
//                    this.setVisible(true);
//                    }else {
//                       JOptionPane.showMessageDialog(null, "No items in choosen category for this restaurant", "Item Details", JOptionPane.ERROR_MESSAGE); 
//                        ViewInformation.this.dispose();
//                    }
//                    dis.close();
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(DeleteItems.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(DeleteItems.class.getName()).log(Level.SEVERE, null, ex);
                

      //Adding action listener to ok
      db = new DBConnection();
      String query = "select itemnum,restaurantid,category,item_description,price from menu_items_jfood";
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
