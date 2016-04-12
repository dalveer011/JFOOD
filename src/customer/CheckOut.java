
package customer;
import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jfood.HeaderFooter;
import jfood.LogOut;
import loginAndRegistration.LoginForm;

public class CheckOut extends MenuCustomer {
private JPanel center,bottom,centerMain;
private JTextField totalAmt,orderNum;
  private JCheckBox edit;
private JButton pay,cancel;
private double total,price,quantity;
private ArrayList myList;
private String cstid,restId;
    public CheckOut(ArrayList a,final String cstid,final String restId) {
        this.cstid = cstid;
        this.restId = restId;
        this.myList = a;
        System.out.println(restId);
        System.out.println(cstid);
     this.setTitle("Customer Update Details");
     this.setVisible(true);
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        //Creating the menubar using MenuCustomer class!
        this.setJMenuBar(customerMenu()); 
        this.add(this.createMyToolBar(), BorderLayout.EAST);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        center = new JPanel(new GridLayout(0, 5));
     
    for(Object c : a) {
    AddtoCartRecord d = (AddtoCartRecord)c;
     center.add(d.getTxtItemNo());
     center.add(d.getTxtItemdesc());
     center.add(new JTextField(Integer.toString(d.getQuantity())));
      price = Double.parseDouble(d.getTxtItemPrice().getText());
      quantity = d.getQuantity();
       center.add(d.getTxtItemPrice());
        edit = d.getCheck();
        center.add(edit);
        
        edit.addItemListener(new ItemListener() {
       
        @Override
        public void itemStateChanged(ItemEvent e) {
           if(edit.isSelected()){
            total = total + (price*quantity);
               System.out.println("Total"+total);  
           }
           else{
               total =total - (price*quantity);
                System.out.println("Total"+total);
               
           }
            totalAmt.setText(Double.toString(total));
        }
    });
      total =total + (price*quantity);
        }
    bottom = new JPanel(new FlowLayout());
    bottom.add(new JLabel("Total Amount To be Paid"));
    totalAmt = new JTextField(Double.toString(total));
    totalAmt.setEditable(false);
    orderNum = new JTextField();
    orderNum.setEditable(false);
    DBConnection db = new DBConnection();
    ResultSet rs = db.getInfo("select max(ordernum) from orders_jfood");
    try {
        if(rs.next()){
            int ordern = Integer.parseInt(rs.getString(1)) +1;
            orderNum.setText(Integer.toString(ordern));
        }
    } catch (SQLException ex) {
        System.out.println("Error in getting maximum value from orders_jfood");
    }
    bottom.add(totalAmt);
     bottom.add(orderNum);
    pay = new JButton("Pay");
    pay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnection db = new DBConnection();
                String query = "select currentbal from customers_jfood where loginid = '"+LoginForm.customer.getLoginId()+"' ";
                ResultSet rs = db.getInfo(query);
                try {
                    if(rs.next()){
                        double bal = rs.getDouble(1);
                        if(bal < total) {
                        JOptionPane.showMessageDialog(null,"Please Add money to your wallet","Add money",JOptionPane.INFORMATION_MESSAGE);
                        }else{
                           db.addOrder(Integer.parseInt(orderNum.getText()), LoginForm.customer.getLoginId(), restId, Customer.completeShoppingList);
                           rs.updateDouble(1, bal - total);
                           rs.updateRow();
                            Customer.completeShoppingList.clear();
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("Error occured in pay action listener");
                }
               
            }
        });
    cancel = new JButton("Remove Everything");
    cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Customer.completeShoppingList.clear();
                JOptionPane.showMessageDialog(null,"oh! we get it. Wanna add new items Enjoy!","",JOptionPane.INFORMATION_MESSAGE);
                new HomeCustomer(LoginForm.customer.getLoginId());
                CheckOut.this.dispose();
            }
        });
    
    bottom.add(pay);
    bottom.add(cancel);
    centerMain = new JPanel(new BorderLayout());
    centerMain.add(center);
    centerMain.add(bottom,BorderLayout.SOUTH);
    this.add(centerMain,BorderLayout.CENTER);
    
    //Adding menu and toolbarActionListener
    miAccountDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            Customer_UpdateDetails c1 = new Customer_UpdateDetails(LoginForm.customer.getLoginId());
            
         }
     });
        miAddBalance.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AddBalance ab = new AddBalance(LoginForm.customer.getLoginId());
            
         }
     });
        
        miLogout.addActionListener
        (
                new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l1 = new LogOut();
                CheckOut.this.dispose();
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
                CheckOut.this.dispose();
            }                
            }
        );
        
tbBtnHome.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new HomeCustomer(LoginForm.customer.getLoginId());
                CheckOut.this.dispose();
            }                
            }
        );
    }
    
    
    }

