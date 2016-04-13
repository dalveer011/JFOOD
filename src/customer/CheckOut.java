
package customer;
import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
private JPanel center,bottom,centerMain,centreBottom,centerFinal;
private JTextField totalAmt,orderNum,tax;
private JButton pay,cancel;
private double total,price,quantity,totalOrderAmount;
private ArrayList myList,myCheckBoxes,finalList;
private String cstid,restId;
    public CheckOut(ArrayList a,final String cstid,final String restId) {
        this.cstid = cstid;
        this.restId = restId;
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
        myList = new ArrayList();
        for(Object c : a) {
        AddtoCartRecord d = (AddtoCartRecord)c;
       final JCheckBox edit = d.getCheck();
      price = Double.parseDouble(d.getTxtItemPrice().getText());
      quantity = d.getQuantity();
      final JLabel priceNeeded = new JLabel(Double.toString(price));
      final JLabel quanNeeded = new JLabel(Double.toString(quantity));
       edit.addItemListener(new ItemListener() {
       
        @Override
        public void itemStateChanged(ItemEvent e) {
           if(edit.isSelected()){
            totalOrderAmount = totalOrderAmount + (Double.parseDouble(priceNeeded.getText())*Double.parseDouble(quanNeeded.getText()));
               System.out.println("Total"+totalOrderAmount);  
           }
           else{
               totalOrderAmount = totalOrderAmount - (Double.parseDouble(priceNeeded.getText())*Double.parseDouble(quanNeeded.getText()));
               
               
           }
             totalAmt.setText(String.format("%.2f",totalOrderAmount));
             Double totalTax = ((Double.parseDouble(totalAmt.getText()) * 13.5)/100);
             tax.setText(String.format("%.2f",totalTax));
        }
    });
       myList.add(edit);
      }
    for (int i=0;i<a.size();i++) {
    AddtoCartRecord d = (AddtoCartRecord)(a.get(i));
     center.add(d.getTxtItemNo());
     center.add(d.getTxtItemdesc());
     center.add(new JTextField(Integer.toString(d.getQuantity())));
      price = Double.parseDouble(d.getTxtItemPrice().getText());
      quantity = d.getQuantity();
      final JLabel priceNeeded = new JLabel(Double.toString(price));
      final JLabel quanNeeded = new JLabel(Double.toString(quantity));
      
       center.add(d.getTxtItemPrice());
       center.add((JCheckBox)myList.get(i));
      totalOrderAmount =totalOrderAmount + (price*quantity);
        }
     Double totalTax = ((totalOrderAmount * 13.5)/100);
     
        centreBottom = new JPanel();
        centreBottom.setLayout(new GridBagLayout());
        totalAmt = new JTextField(String.format("%.2f",totalOrderAmount));
        totalAmt.setEditable(false);
        orderNum = new JTextField();
        orderNum.setEditable(false);
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(10, 5, 10, 5);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        centreBottom.add(new JLabel("Order Num"), g);
        g.gridx = 1;
        g.gridy = 0;
        centreBottom.add(orderNum, g);
        g.gridx = 0;
        g.gridy = 1;
       tax = new JTextField();
       tax.setText(String.format("%.2f",totalTax));
        centreBottom.add(new JLabel("Tax"), g);
        g.gridx = 1;
        g.gridy = 1;
        centreBottom.add(tax, g);
        g.insets= new Insets(10, 5, 0, 5);
        g.gridx = 0;
        g.gridy = 2;
        centreBottom.add(new JLabel("Total order Amount to be paid"), g);
        g.gridx = 1;
        g.gridy = 2;
        centreBottom.add(totalAmt, g);
        
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
                       total = Double.parseDouble(totalAmt.getText())+Double.parseDouble(tax.getText());
                        if(bal < total) {
                        JOptionPane.showMessageDialog(null,"Please Add money to your wallet","Add money",JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            finalList = new ArrayList();
                            for(int i = 0;i<myList.size();i++) {
                            JCheckBox a = (JCheckBox)myList.get(i);
                            if(a.isSelected()) {
                            finalList.add(LoginForm.customer.getShoppingList().get(i));
                            }
                            }
                           db.addOrder(Integer.parseInt(orderNum.getText()), LoginForm.customer.getLoginId(), restId, finalList);
                           rs.updateDouble(1, bal - total);
                           rs.updateRow();
                            LoginForm.customer.getShoppingList().clear();
                            HomeCustomer a = new HomeCustomer(LoginForm.customer.getLoginId());
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
                LoginForm.customer.getShoppingList().clear();
                JOptionPane.showMessageDialog(null,"oh! we get it. Wanna add new items Enjoy!","",JOptionPane.INFORMATION_MESSAGE);
                new HomeCustomer(LoginForm.customer.getLoginId());
                CheckOut.this.dispose();
            }
        });
    bottom = new JPanel(new FlowLayout());
    bottom.add(pay);
    bottom.add(cancel);
    centerFinal = new JPanel(new BorderLayout());
    centerMain = new JPanel(new GridLayout(2,1));
    centerMain.add(center);
    centerMain.add(centreBottom);
    centerFinal.add(bottom,BorderLayout.SOUTH);
    centerFinal.add(centerMain,BorderLayout.CENTER);
    this.add(centerFinal,BorderLayout.CENTER);
    
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

