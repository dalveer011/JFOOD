
package jfood;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckOut extends MenuCustomer {
private JPanel center,bottom,centerMain;
private JButton pay;
private String id;
    public CheckOut(ArrayList a,final String id) {
        this.id = id;
     this.setTitle("Customer Update Details");
     this.setVisible(true);
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        //Creating the menubar using MenuCustomer class!
        this.setJMenuBar(customerMenu()); 
        this.add(this.createMyToolBar(), BorderLayout.EAST);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        center = new JPanel(new GridLayout(0, 5));
        int tot = 0;
        for(Object c : a) {
    AddtoCartRecord d = (AddtoCartRecord)c;
     center.add(d.getTxtItemNo());
     center.add(d.getTxtItemdesc());
     center.add(new JTextField(Integer.toString(d.getQuantity())));
     center.add(d.getTxtItemPrice());
     center.add(d.getCheck());
    tot  +=  d.getQuantity() * (Double.parseDouble(d.getTxtItemPrice().getText()));
        }
    bottom = new JPanel(new FlowLayout());
    bottom.add(new JLabel("Total Amount To be Paid"));
    bottom.add(new JTextField(Integer.toString(tot)));
    pay = new JButton("Pay");
    bottom.add(pay);
    centerMain = new JPanel(new BorderLayout());
    centerMain.add(center);
    centerMain.add(bottom,BorderLayout.SOUTH);
    this.add(centerMain,BorderLayout.CENTER);
    
    //Adding menu and toolbarActionListener
    miAccountDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            Customer_UpdateDetails c1 = new Customer_UpdateDetails(id);
            
         }
     });
        miAddBalance.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AddBalance ab = new AddBalance(id);
            
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
                Customer_UpdateDetails c2 = new Customer_UpdateDetails(id);
                
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
                AddBalance a2 = new AddBalance(id);
                
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
                new HomeCustomer(id);
                CheckOut.this.dispose();
            }                
            }
        );
    }
    
    
    }

