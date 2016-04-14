
package customer;
import customer.AddBalance;
import customer.AddtoCartRecord;
import customer.MenuCustomer;
import customer.HomeCustomer;
import customer.Customer_UpdateDetails;
import customer.Customer;
import customer.CheckOut;
import database.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import jfood.HeaderFooter;
import jfood.LogOut;
import loginAndRegistration.LoginForm;
/**
 *
 * @author Mazhar
 */
public class DisplayItems extends MenuCustomer{
    private JLabel lblLogo, lblCategory, lblQty, lblPrice, lblSlNum, lblDesc, lblAdd_Remove;
    private JButton btnDisplay, btnCheckOut, btnAddToCart;
    private JComboBox menuCategory;
    private JPanel panelCenter, panelCenterInside, panelCenterInsideBottom, panelCenterInsideMiddle, 
            panelCenterInsideTop;
    private String restId,cstId;
    private ArrayList recordList;
    
    private String [] menuCategories = {"Select Category", "Rice","Noodles","Chicken","Fish","Dessert","Entree","Soup","Salad", "Breakfast", "Dinner", "Mains", "Entree"};
    
    public DisplayItems(final String id, final String cstId) {
        this.restId = id;
        this.cstId = cstId;
        this.initComponents();
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.setTitle("Menu | "+id);
        this.setJMenuBar(customerMenu());
        this.add(this.createMyToolBar(), BorderLayout.EAST);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        //Adding actionListeners
        miAccountDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            Customer_UpdateDetails c1 = new Customer_UpdateDetails(cstId);
            DisplayItems.this.dispose();
         }
     });
        miAddBalance.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AddBalance ab = new AddBalance(cstId);
            DisplayItems.this.dispose();
         }
     });
        
        miLogout.addActionListener
        (new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l1 = new LogOut();
                DisplayItems.this.dispose();
            }
        }
        );
        
        //Adding actionlistener to the toolbar!
        tbBtnUpdate.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Customer_UpdateDetails c2 = new Customer_UpdateDetails(cstId);
                DisplayItems.this.dispose();
            }                
            }
        );
        
        tbBtnAddBalance.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddBalance a2 = new AddBalance(cstId);
                DisplayItems.this.dispose();
            }                
            }
        );
        
        tbBtnLogout.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l2 = new LogOut();
                DisplayItems.this.dispose();
            }                
            }
        );
        
        tbBtnHome.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new HomeCustomer(cstId);
                DisplayItems.this.dispose();
            }                
            }
        );
    }
    
    private void initComponents(){
        //panel
        panelCenter = new JPanel (new BorderLayout());
        
        String a = "images/logo.png";
        lblLogo = new JLabel(new ImageIcon(getClass().getResource(a)));
        panelCenter.add(lblLogo, BorderLayout.NORTH);
        
        lblCategory = new JLabel ("Category");
        menuCategory = new JComboBox(menuCategories);
        btnCheckOut = new JButton ("Check Out");
        btnAddToCart= new JButton ("Add to Cart");
        menuCategory.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                panelCenterInsideMiddle.removeAll();
                String category = (String) menuCategory.getSelectedItem();
                
                    int count = 0;
                    recordList = new ArrayList();
                    DBConnection db = new DBConnection();
                    ResultSet rs = db.getInfo("Select itemnum, item_description, category, price from menu_items_jfood where restaurantid = '" + restId + "'");
                    try{
                        
                    while (rs.next()){
                        
                        String itemNo = rs.getString(1);
                        String item_desc = rs.getString (2);
                        String cat = rs.getString(3);
                        double price = rs.getDouble(4);
                        
                    if(category.equals(cat)) {
                            JTextField  a = new JTextField();
                            a.setText(itemNo);
                            a.setEditable(false);
                            JTextField  b = new JTextField();
                            b.setText(item_desc);
                            b.setEditable(false);
                            JTextField  c = new JTextField();
                            c.setText(Double.toString(price));
                            c.setEditable(false);
                            JSpinner quantity = new JSpinner(new SpinnerNumberModel(1,1,25,1));
                            JCheckBox  d = new JCheckBox();
                            
                            AddtoCartRecord record = new AddtoCartRecord();
                            record.setTxtItemNo(a);
                            record.setTxtItemdesc(b);
                            record.setTxtItemPrice(c);
                            record.setQuantity(quantity);
                            record.setCheck(d);
                            recordList.add(record);
                                        panelCenterInsideMiddle.add(a);
                                        panelCenterInsideMiddle.add(b);
                                        panelCenterInsideMiddle.add(c);
                                        panelCenterInsideMiddle.add(quantity);
                                        panelCenterInsideMiddle.add(d);
                                        panelCenterInsideMiddle.validate();
                              count++;
                    }
                    }
                    
                    if(count == 0) {
                    JOptionPane.showMessageDialog(null,"No item in this category for choosen Restaurant", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error in DisplayItems.java" + ex.getMessage());
                } 
            }
        });
        //Adding action listener to add to cart
        btnAddToCart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{   
               
                    for(Object z : recordList) {
                    AddtoCartRecord x = (AddtoCartRecord)z;
                    if(x.isChecked()) {
                        LoginForm.customer.getShoppingList().add(x);
                    }
                    } 
                
                }catch(Exception ex) {
                JOptionPane.showMessageDialog(null,"No items selected from category","Error",JOptionPane.ERROR_MESSAGE);
                }
            }    
        });
        //Adding action listener to checkout
        btnCheckOut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if( LoginForm.customer.getShoppingList().isEmpty()){
                      JOptionPane.showMessageDialog(null,"No item choosen", "Choose an item", JOptionPane.INFORMATION_MESSAGE);
                }else{
                new CheckOut( LoginForm.customer.getShoppingList(),LoginForm.customer.getLoginId(),restId);
                DisplayItems.this.dispose();
            }
            }
        });
        //Creating the inside Panel
        panelCenterInside = new JPanel (new BorderLayout());
        
        //Creating the paneltop inside the Center
        panelCenterInsideTop = new JPanel(new GridLayout(1,5,5,10));
        panelCenterInsideTop.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));

        
        lblSlNum = new JLabel("Item Number");
        lblDesc = new JLabel ("Item Description");
        lblPrice = new JLabel("Price");
        lblQty = new JLabel ("Quantity");
        lblAdd_Remove = new JLabel("Add/Remove");
        
        panelCenterInsideTop.add(lblSlNum);
        panelCenterInsideTop.add(lblDesc);
        panelCenterInsideTop.add(lblPrice);
        panelCenterInsideTop.add(lblQty);
        panelCenterInsideTop.add(lblAdd_Remove);
        
        panelCenterInside.add(panelCenterInsideTop, BorderLayout.NORTH);
        
        //In the middle
        panelCenterInsideMiddle = new JPanel();
        panelCenterInsideMiddle.setLayout(new GridLayout(0,5,5,10));
        panelCenterInside.add(panelCenterInsideMiddle, BorderLayout.CENTER);
        
        //This is going to be south of inside panel
        panelCenterInsideBottom = new JPanel (new FlowLayout());
        panelCenterInsideBottom.add(lblCategory);
        panelCenterInsideBottom.add(menuCategory);
        panelCenterInsideBottom.add(btnAddToCart);
        panelCenterInsideBottom.add(btnCheckOut);
        
        panelCenterInside.add(panelCenterInsideBottom, BorderLayout.SOUTH);
        
        panelCenter.add(panelCenterInside, BorderLayout.CENTER);
    }
}
