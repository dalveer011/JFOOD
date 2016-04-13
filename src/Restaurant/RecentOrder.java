
package Restaurant;

import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jfood.HeaderFooter;
import jfood.LogOut;

/**
 *
 * @author Dalvir Sahota
 */
public class RecentOrder extends RestaurantMenuBar {
private DBConnection db;
private ResultSet rs;
private JPanel panelCentre;
private int orderNo;
private String restId;
private ArrayList showButtons,doneButtons,lblOrderNum;
public RecentOrder(String restauranttId) {
    restId = restauranttId;
        this.initComponents();
        for(int i=0;i<showButtons.size();i++){
        JButton sh = (JButton)showButtons.get(i);
        JButton done = (JButton)doneButtons.get(i);
        JLabel lbOrderNo = (JLabel)lblOrderNum.get(i);
        panelCentre.add(lbOrderNo);
         panelCentre.add(sh);
          panelCentre.add(done);
        }
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);

        this.setTitle("Update Food items");
        this.add(panelCentre,BorderLayout.CENTER);
        this.setJMenuBar(restMenuBar());
        this.setVisible(true);
         addItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddNewItems a = new AddNewItems(restId);
                RecentOrder.this.dispose();
            }                
            }
        );
        
        deleteItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                DeleteItems a = new DeleteItems(restId);
                  RecentOrder.this.dispose();
            }                
            }
        );
        
        updateItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                UpdateItems a = new UpdateItems(restId);
                  RecentOrder.this.dispose();
            }                
            }
        );
        
        logOut.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut a = new LogOut();
                  RecentOrder.this.dispose();
            }                
            }
        );
}
private void initComponents() {
    try {
        showButtons = new ArrayList();
        doneButtons = new ArrayList();
        lblOrderNum = new ArrayList();
        panelCentre = new JPanel(new GridLayout(0,3));
        db = new DBConnection();
        rs = db.getInfo("select orderNum from orders_jfood where status = 0 and restaurantId = '"+this.restId+"'");
        while(rs.next()) {
            orderNo = rs.getInt(1);
            final JLabel orderNum = new JLabel();
            orderNum.setText(Integer.toString(orderNo));
             lblOrderNum.add(orderNum);
            JButton orderShow = new JButton("Show Order");
             panelCentre.add(orderShow);
             orderShow.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("orderButton presses with order nu"+orderNo);
                    OrderInformationTwo a = new OrderInformationTwo(Integer.parseInt(orderNum.getText()));
                    a.setVisible(true);
                }
            });
             showButtons.add(orderShow);
             JButton orderDone = new JButton("Done with order");
               orderDone.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
               int dr = JOptionPane.showConfirmDialog(null,"Is order is ready to deliever to customer ? ", "order Done confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
               if(dr == JOptionPane.YES_OPTION){
                   try {
                       db = new DBConnection();
                       db.updateOrder(Integer.parseInt(orderNum.getText()));
                       panelCentre.validate();
                   } catch (SQLException ex) {
                               JOptionPane.showMessageDialog(null,"Error occured in recent orders in action listener of orderDone button"+ex.getMessage(), "null", JOptionPane.ERROR_MESSAGE);
                   }
               }
                }
            });
               doneButtons.add(orderDone);
            
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,"Error occured in recent orders init"+ex.getMessage(), "null", JOptionPane.INFORMATION_MESSAGE);
    }

}

}