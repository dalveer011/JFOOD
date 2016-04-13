/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginAndRegistration;
import Restaurant.RestaurantOwner;
import Restaurant.RestaurantHome;
import customer.HomeCustomer;
import customer.Customer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.TitledBorder;
import database.DBConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import jfood.ForgotPassword;
import jfood.HeaderFooter;
import jfood.WelcomeScreen;


/**
 *
 * @author Mazhar
 */
public class LoginForm extends JFrame{
    //declaration
    private JButton btnLogin,btnForgotPass,btnHome;
    private JComboBox comboBoxRole;
    public  JLabel lblEnterId, lblEnterPassword, lblRole;
    public  JTextField txtEnterId;
    private JPanel panelCenterBottom,center,panelCenterTop;
    private JPasswordField txtEnterPassword;

    //Creating a separate toolbar for login screen!
    private JToolBar tbForLogin;
    private JButton tbBtnBack;
    private JPanel panelCenterMiddle;
    
    private DBConnection db = null;
    private ResultSet rs = null;
    public static Customer customer;
    public static RestaurantOwner restaurantOwner;
    
    private String queryCustomer = "select loginId, password, role, fname, lname, address, city, province, postalcode, email, phone from CUSTOMERS_JFOOD ";
    private String queryRestaurantOwner = "select restaurantId, password, role, name, address, city, province, postalcode, email, phone from RESTAURANTOWNERS_JFOOD ";

    public LoginForm ()
    {
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.initComponents();
        this.setTitle("Login Form");
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        
        this.add(center, BorderLayout.CENTER);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(tbForLogin, BorderLayout.EAST);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        db = new DBConnection();
        
    }
    
    private void initComponents (){
        //creating the labels
        lblEnterId = new JLabel ("Enter Login Name");        
        lblEnterPassword = new JLabel ("Enter Password");
        lblRole = new JLabel ("Role");
        
        //creating the button and adding action listener to this button
        //btnHome = new JButton("Home");
        TitledBorder leftBorder = BorderFactory.createTitledBorder("Login");
        leftBorder.setTitleJustification(TitledBorder.LEFT);
   
        btnLogin = new JButton ("Login");
        btnForgotPass = new JButton("Forgot Password ?");
        
        btnForgotPass.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (txtEnterId.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Provide login id","Attention",JOptionPane.ERROR_MESSAGE);
                    txtEnterId.setText("");
                    txtEnterId.grabFocus();

                }else
                {
                    try {
                        Socket socketfgPass = new Socket("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(socketfgPass.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream(socketfgPass.getOutputStream());
                       
                        String role = (String)comboBoxRole.getSelectedItem();
                        if (role == "Customer"){
                            dataToServer.writeInt(5); //Forgot password for Customers
                            dataToServer.writeUTF(txtEnterId.getText());
                            
                            boolean checkfgPass_cus = dataFromServer.readBoolean();
                            if (checkfgPass_cus){
                                new ForgotPassword(txtEnterId.getText());
                                socketfgPass.close();
                                LoginForm.this.dispose();
                            }else{
                                JOptionPane.showMessageDialog(null, "Customer Id doesn't exist ", "Login Incorrect", JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }else if (role == "Restaurant Owner")
                        {
                            dataToServer.writeInt(6); //Forgot password for Restaurant Owners
                            dataToServer.writeUTF(txtEnterId.getText());
                            
                            boolean checkfgPass_Rst = dataFromServer.readBoolean();
                            if (checkfgPass_Rst){
                                new ForgotPassword(txtEnterId.getText());
                                socketfgPass.close();
                                LoginForm.this.dispose();
                            }else{
                                JOptionPane.showMessageDialog(null, "Restaurant Id doesn't exist ", "Login Incorrect", JOptionPane.ERROR_MESSAGE);
                            }    
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                String pass = null;
                String myrole = null;
                String fName = null;
                String lName = null;
                String restaurantName = null;
                String address = null;
                String city = null;
                String province = null;
                String pc = null;
                String email = null;
                String phone = null;
                
                boolean checkCus = false;
                boolean checkRst = false;
                
                String entId = txtEnterId.getText();
                String entPass = String.valueOf(txtEnterPassword.getPassword());
                
                if (entId.trim().isEmpty() || entPass.trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Id and Password field can not be left empty", "Empty fields", JOptionPane.ERROR_MESSAGE);
                }else
                {
                    try {
                        Socket socketLogin = new Socket("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(socketLogin.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream (socketLogin.getOutputStream());
                        
                        String role = (String) comboBoxRole.getSelectedItem();
                            if (role == "Customer")
                            {
                                dataToServer.writeInt(3); //Process Id for Login Check | IF CUSTOMER COMBOBOX WAS CHECKED
                                dataToServer.writeUTF(entId);
                                dataToServer.writeUTF(entPass);
                                boolean logPassCheckCus = dataFromServer.readBoolean();
                                
                                if (logPassCheckCus){
                                    id = entId;
                                    pass = entPass;
                                    myrole = dataFromServer.readUTF();
                                    fName = dataFromServer.readUTF();
                                    lName = dataFromServer.readUTF();
                                    address = dataFromServer.readUTF();
                                    city = dataFromServer.readUTF();
                                    province = dataFromServer.readUTF();
                                    pc = dataFromServer.readUTF();
                                    email = dataFromServer.readUTF();
                                    phone = dataFromServer.readUTF();
                                    checkCus = true;
                                }else{
                                    JOptionPane.showMessageDialog(null, "LoginId and PassWord incorrect", "LoginID/Pass Mismatch", JOptionPane.ERROR_MESSAGE);
                                }
                            }else if (role == "Restaurant Owner")
                            {
                                dataToServer.writeInt(4); //Process Id for Login Check | IF RESTAURANT OWNER COMBOBOX WAS CHECKED
                                dataToServer.writeUTF(entId);
                                dataToServer.writeUTF(entPass);
                                boolean logPassCheckRst = dataFromServer.readBoolean();
                                
                                if (logPassCheckRst){
                                    id = entId;
                                    pass = entPass;
                                    myrole = dataFromServer.readUTF();
                                    restaurantName = dataFromServer.readUTF();
                                    address = dataFromServer.readUTF();
                                    city = dataFromServer.readUTF();
                                    province = dataFromServer.readUTF();
                                    pc = dataFromServer.readUTF();
                                    email = dataFromServer.readUTF();
                                    phone = dataFromServer.readUTF();
                                    checkRst = true;
                                }else{
                                    JOptionPane.showMessageDialog(null, "Loginid and Password incorrect", "LoginID & Password Mismatch", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        socketLogin.close();
                        } catch (IOException ex) {
                        Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if (checkCus){
                    customer = new Customer(entId, entPass, myrole, fName, lName, address, city, province, pc, email, phone);
                    new HomeCustomer(customer.getLoginId());
                    LoginForm.this.dispose();
                }else if (checkRst)
                {
                    restaurantOwner = new RestaurantOwner(entId, entPass, myrole, restaurantName, address, city, province, pc, email, phone);
                    new RestaurantHome(restaurantOwner.getLoginId());
                    LoginForm.this.dispose();
                }
            }
        });
        
//        btnLogin.addActionListener(new ActionListener() {
//            
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String id = null;
//                String pass = null;
//                String myrole = null;
//                String fName = null;
//                String lName = null;
//                String restaurantName = null;
//                String address = null;
//                String city = null;
//                String province = null;
//                String pc = null;
//                String email = null;
//                String phone = null;
//                
//                
//                String role = (String) comboBoxRole.getSelectedItem();
//                if (role == "Customer")
//                {
//                    rs = db.getInfo(queryCustomer);
//                }else if (role == "Restaurant Owner")
//                {
//                    rs = db.getInfo(queryRestaurantOwner);                    
//                }
//                
//                String entId = txtEnterId.getText();
//                String entPass = String.valueOf(txtEnterPassword.getPassword());
//                
//                if (entId.trim().isEmpty() || entPass.trim().isEmpty())
//                {
//                    JOptionPane.showMessageDialog(null, "Id and Password field can not be left empty", "Empty fields", JOptionPane.ERROR_MESSAGE);
//                }else 
//                {
//                    int count = 0;
//                    try {
//                        while (rs.next()){
//                            id = rs.getString(1);
//                            pass = rs.getString(2);
//                            myrole = rs.getString(3);
//                            if (id.equals(entId) & pass.equals(entPass) & myrole.equals(role))
//                                if (role.equals("Restaurant Owner"))
//                            {  
//                                System.out.println("Role is restaurant");
//                                restaurantName = rs.getString(4);
//                                address = rs.getString(5);
//                                city = rs.getString(6);
//                                province= rs.getString(7);
//                                pc = rs.getString(8);
//                                email = rs.getString(9);
//                                phone = rs.getString(10);
//                                count++;
//                            }
//                            else        
//                            {
//                                System.out.println("Role is customer");
//                                fName = rs.getString(4);
//                                lName = rs.getString(5);
//                                address = rs.getString(6);
//                                city = rs.getString(7);
//                                province= rs.getString(8);
//                                pc = rs.getString(9);
//                                email = rs.getString(10);
//                                phone = rs.getString(11);
//                                count++;
//                            }
//                            }
//                                                
//                        if (count == 0){
//                            JOptionPane.showMessageDialog(null,"Your credentials are not Valid.\n Try Again!", "invalid id//Password", JOptionPane.ERROR_MESSAGE);
//                            txtEnterId.setText("");
//                            txtEnterPassword.setText("");
//                            txtEnterId.grabFocus();    
//                        } 
//                        
//                        if (count > 0 ){
//                            if (role == "Customer"){
//                                customer = new Customer(entId, entPass, role, fName, lName, address, city, province, pc, email, phone);
//                                new HomeCustomer(customer.getLoginId());
//                                db.closeConnection();
//                                LoginForm.this.dispose();
//                            }else{
//                                restaurantOwner = new RestaurantOwner(entId, entPass, role, restaurantName, address, city, province, pc, email, phone);
//                                new RestaurantHome(restaurantOwner.getLoginId());
//                                db.closeConnection();
//                                LoginForm.this.dispose();
//                            }
//                        }
//                        } catch (SQLException ex) {
//                            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Login Form", JOptionPane.ERROR_MESSAGE);
//                        }
//                } 
//            }
//        });
        
        
        String [] role = {"Customer", "Restaurant Owner"};
        comboBoxRole = new JComboBox (role);
        
        //Creating the text box
        
        txtEnterId = new JTextField ();
        txtEnterId.setPreferredSize(new Dimension (300, 30));
        txtEnterPassword = new JPasswordField ();
        txtEnterPassword.setPreferredSize(new Dimension (300, 30));
        
        //Creating the panel
        
        panelCenterTop = new JPanel();
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
        panelCenterTop.add(logo);
        
        panelCenterMiddle = new JPanel();
        panelCenterMiddle.setLayout(new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(10, 5, 10, 5);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        panelCenterMiddle.add(lblEnterId, g);
        g.gridx = 1;
        g.gridy = 0;
        panelCenterMiddle.add(txtEnterId, g);
        g.gridx = 0;
        g.gridy = 1;
        panelCenterMiddle.add(lblEnterPassword, g);
        g.gridx = 1;
        g.gridy = 1;
        panelCenterMiddle.add(txtEnterPassword, g);
        g.insets= new Insets(10, 5, 0, 5);
        g.gridx = 0;
        g.gridy = 2;
        panelCenterMiddle.add(lblRole, g);
        g.gridx = 1;
        g.gridy = 2;
        panelCenterMiddle.add(comboBoxRole, g);
        
        panelCenterBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 60));
        panelCenterBottom.add(btnLogin);
        panelCenterBottom.add(btnForgotPass);
        
        
        //panelCenter.add(btnHome);
        
        center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createTitledBorder(leftBorder, "Login"));
        
        center.add(panelCenterTop,BorderLayout.NORTH);
        center.add(panelCenterMiddle,BorderLayout.CENTER);
        center.add(panelCenterBottom, BorderLayout.SOUTH);
        
        
        tbForLogin = new JToolBar ("Back to Home");
        tbForLogin.setOrientation(SwingConstants.VERTICAL);
        tbForLogin.setFloatable(true);
        
        tbBtnBack = new JButton (new ImageIcon(getClass().getResource("resources/back.png")));
        
        tbForLogin.add(tbBtnBack);
        
        tbBtnBack.addActionListener
        (
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        new WelcomeScreen();
                        LoginForm.this.dispose();
                    }
                }
        );
    }      
}
