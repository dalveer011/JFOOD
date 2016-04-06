
package jfood;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeCustomer extends MenuCustomer {
    //Declaration
    //private JLabel lblCopyRight, lblHeading;
    private JComboBox cmbState,cmbCity,cmbRestau;
    private JLabel lblState,lblCity,lblRestau;
    private JPanel panelCenter;
    private DBConnection conn;  
    private ResultSet rs,rs2,rs3;
    String id;
    
    public HomeCustomer (final String id){
        
        this.id = id;
        this.initComponents();
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.setTitle("Home | Welcome to JFood");
        this.setJMenuBar(customerMenu());
        this.add(this.createMyToolBar(), BorderLayout.EAST);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        
        //Adding actionListeners
        cmbState.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                try { 
                   conn = new DBConnection();        
                    rs = conn.getInfo("select city from jfood_cities where state = '"+cmbState.getSelectedItem().toString()+"'");
                    cmbCity.removeAllItems();
                    //cmbCity.addItem("select city");
                    while(rs.next()){
                        cmbCity.addItem(rs.getString(1));
                    }
                  conn.closeConnection();
                            }  catch (SQLException ex) {
                    System.out.println("Error in item listener of cmState "+ex.getMessage());
                }
            }
        });
 
          cmbCity.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                   conn = new DBConnection();        
                   rs = conn.getInfo("select LoginId,Name from RestaurantOwners_Jfood  where province = '"+cmbState.getSelectedItem().toString()+"' and city='"+cmbCity.getSelectedItem().toString()+"'");
                    int count = 0;
                    cmbRestau.removeAllItems();
                    cmbRestau.addItem("Select Restaurant");
                    while(rs.next()) {
                cmbRestau.addItem(rs.getString(1)+"("+rs.getString(2)+")");
                count++;
                }
                    if(count == 0) {
                    JOptionPane.showMessageDialog(null, "Sorry! No restaurant in your selected area", "sorry", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                   System.out.println("Error in item listener of cmbcity");
                }
       }
//        });
                });
        miAccountDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            Customer_UpdateDetails c1 = new Customer_UpdateDetails(LoginForm.customer.getLoginId());
            HomeCustomer.this.dispose();
         }
     });
        miAddBalance.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AddBalance ab = new AddBalance(LoginForm.customer.getLoginId());
            HomeCustomer.this.dispose();
         }
     });
        
        miLogout.addActionListener
        (
                new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l1 = new LogOut();
                HomeCustomer.this.dispose();
            }
        }
        );
        
        //Adding actionlistener to the toolbar!
        tbBtnUpdate.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Customer_UpdateDetails c2 = new Customer_UpdateDetails(LoginForm.customer.getLoginId());
                HomeCustomer.this.dispose();
            }                
            }
        );
        
        tbBtnAddBalance.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddBalance a2 = new AddBalance(LoginForm.customer.getLoginId());
                HomeCustomer.this.dispose();
            }                
            }
        );
        
        tbBtnLogout.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l2 = new LogOut();
                HomeCustomer.this.dispose();
            }                
            }
        );
    }
    private void initComponents (){       
conn = new DBConnection();        
//creating the combo boxes with image icon
       
        lblState = new JLabel("Select State");
        lblCity = new JLabel("select City");
        lblRestau = new JLabel("Select Restaurant");
        cmbState = new JComboBox();
         cmbCity = new JComboBox();
         cmbRestau = new JComboBox();
          try {
            
              rs = conn.getInfo("select distinct state from jfood_cities");
              cmbState.addItem("Select State");   
              cmbCity.addItem("Select City");  
              cmbRestau.addItem("Select Restaurant");  
            while(rs.next()){
            cmbState.addItem(rs.getString(1));
            }
            cmbState.setSelectedIndex(0);
           conn.closeConnection();
        } catch (SQLException ex) {
            System.out.println("Error in Home Customer "+ex.getMessage());
        }
        //Adding the panel
        
        panelCenter = new JPanel ();
        panelCenter.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(10, 5, 10, 5);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        panelCenter.add(lblState, g);
        g.gridx = 1;
        g.gridy = 0;
       
        panelCenter.add(cmbState, g);
        g.gridx = 0;
        g.gridy = 1;
        panelCenter.add(lblCity, g);
        g.gridx = 1;
        g.gridy = 1;
        panelCenter.add(cmbCity, g);
        g.insets= new Insets(10, 5, 0, 5);
        g.gridx = 0;
        g.gridy = 2;
        panelCenter.add(lblRestau, g);
        g.gridx = 1;
        g.gridy = 2;
        panelCenter.add(cmbRestau, g);
  
    }
    
    
}
