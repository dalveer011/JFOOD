/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginAndRegistration;
import loginAndRegistration.LoginForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.TitledBorder;
import database.DBConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jfood.HeaderFooter;
import jfood.ProcessID;
import jfood.WelcomeScreen;
/**
 *
 * @author Mazhar
 */
public class CustomerRegistration extends JFrame {
    
        //Declaration!
    
    //private JLabel lblHeading;
    private JMenuBar back;
    private JMenuItem goback;
    private JMenu goToHome;
    public  JLabel lblId, lblPassword, lblFirstName, lblLastName, lblCity, 
            lblStreetName, lblProvince, lblPostalCode, lblEmail, lblPhone, 
            lblRole, lblSq1, lblSq2, lblAns1, lblAns2;
    public  JTextField txtId, txtFirstName, txtLastName, txtCity, 
            txtStreetName, txtPostalCode, txtEmail, txtPhone, txtAns1, txtAns2;
    private JPasswordField txtPassword; //used JPasswordField instead of JTextField
    public  JComboBox comboBoxRole, comboBoxSq1, comboBoxSq2, comboBoxProvince,cmbCity;  
    private JButton btnRegister,btnHome;   
    private JPanel panelRegistration, panelHeading, panelButton,center;
    private ResultSet rs;
    private DBConnection db = null;
    private DBConnection conn = null;
    private JTextField txtRole;
    
    
    //Constructors
    public CustomerRegistration (){
        this.initComponents();
        this.setTitle("Registration Form | Customer");
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.setJMenuBar(back);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelRegistration, BorderLayout.CENTER);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //Terminate the application on pressing close button
        this.setVisible(true);
        comboBoxProvince.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                conn = new DBConnection();  
                if(comboBoxProvince.getSelectedIndex() == 0){
                cmbCity.removeAllItems();
                cmbCity.addItem("Select City");
                }else{
                try {       
                    rs = conn.getInfo("select city from jfood_cities where state = '"+comboBoxProvince.getSelectedItem().toString()+"'");
                    cmbCity.removeAllItems();   
                    //cmbCity.addItem("select city");
                    while(rs.next()){
                        cmbCity.addItem(rs.getString(1));
                    }
                    cmbCity.setSelectedIndex(0);
                            }  catch (SQLException ex) {
                    System.out.println("Error in item listener of cmState "+ex.getMessage());
                }
            }
            }
        });
    }
    
    //initComponents method
    private void initComponents (){
        //inializing menu
        goToHome = new JMenu("Home");
        goback = new JMenuItem("Go To Home");
        goback.setMnemonic('H');
        TitledBorder leftBorder = BorderFactory.createTitledBorder("Register");
         leftBorder.setTitleJustification(TitledBorder.LEFT);
        goback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeScreen ws = new WelcomeScreen();
                CustomerRegistration.this.dispose();
            }
        });
        back = new JMenuBar();
        goToHome.add(goback);
        back.add(goToHome);
    
        lblId = new JLabel ("Enter Login Name*");
        lblPassword = new JLabel ("Enter Password*");
        lblFirstName = new JLabel ("Enter First Name*");
        lblLastName = new JLabel ("Enter Last Name*");
        lblStreetName = new JLabel ("Enter Street Address*");
        lblCity = new JLabel ("Enter City*");
        lblProvince = new JLabel ("Enter Province*");
        lblPostalCode = new JLabel ("Enter Postal Code*");
        lblPhone = new JLabel ("Enter Phone*");
        lblEmail = new JLabel ("Enter Email*");
        lblRole = new JLabel ("Role*");
        lblSq1 = new JLabel ("Security Question 1");
        lblSq2 = new JLabel ("Security Question 2");
        lblAns1 = new JLabel ("Answer*");
        lblAns2 = new JLabel ("Answer*");
        
        
        //TextFields created
        txtId = new JTextField ();
        txtId.setColumns(1);
        txtPassword = new JPasswordField ();
        txtFirstName = new JTextField ();
        txtLastName = new JTextField ();
        txtStreetName = new JTextField ();
        txtCity = new JTextField ();
        txtPostalCode = new JTextField ();
        txtPhone = new JTextField ();
        txtEmail = new JTextField ();
        txtAns1 = new JTextField ();
        txtAns2 = new JTextField ();
        txtRole = new JTextField ("Customer");
        txtRole.setEditable(false);
        comboBoxProvince = new JComboBox();
        cmbCity = new JComboBox();
        
        String [] sq1 = {"What is your mothers maiden name?", "Which city you were born in?", 
        "What is your favourite holiday destination?"};
        comboBoxSq1 = new JComboBox(sq1);
        
        String [] sq2 = {"Which is your dream car?", "Which city was your mother born in?", 
        "Who is your favourite school teacher?"};
        comboBoxSq2 = new JComboBox(sq2);
        
        try {
            conn =new DBConnection();
            System.out.println("hello");
            rs = conn.getInfo("select distinct state from jfood_cities");
            System.out.println("ends");
            comboBoxProvince.addItem("Select State");   
            cmbCity.addItem("Select City");   
            while(rs.next()){
            comboBoxProvince.addItem(rs.getString(1));
            }
            comboBoxProvince.setSelectedIndex(0);  
        } catch (SQLException ex) {
            System.out.println("Error in customerRegistration "+ex.getMessage());
        }
                
        //Register Button with actionlistener to Login form!
        
        btnRegister = new JButton ("Register");
        
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loginId = txtId.getText();
                char[] password = txtPassword.getPassword();
                String pass = String.valueOf(password);
                String role = txtRole.getText();
                String fName = txtFirstName.getText();
                String lName = txtLastName.getText();
                String streetAdd = txtStreetName.getText();
                String city = cmbCity.getSelectedItem().toString();
                String province = (String)comboBoxProvince.getSelectedItem();
                String postalCode = txtPostalCode.getText().toUpperCase();
                String email = txtEmail.getText();
                String phone = txtPhone.getText();
                String secQues1 = (String) comboBoxSq1.getSelectedItem();
                String ans1 = txtAns1.getText();
                String secQues2 = (String) comboBoxSq2.getSelectedItem();
                String ans2 = txtAns2.getText();
                
                if(loginId.trim().isEmpty() || pass.trim().isEmpty()||fName.trim().isEmpty()||lName.trim().isEmpty()||streetAdd.trim().isEmpty()
                        ||city.trim().isEmpty()||province.trim().isEmpty()||postalCode.trim().isEmpty()||phone.trim().isEmpty()||email.trim().isEmpty()||ans1.trim().isEmpty()||ans2.trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null, "Fields Marked as * can not be left Blank","Can not be Empty", JOptionPane.ERROR_MESSAGE);

                }else if (loginId.length()>20)
                {
                    JOptionPane.showMessageDialog(null, "Login Name cannot exceed 20 characters"," Login Name | Over limit", JOptionPane.ERROR_MESSAGE);
                    txtId.setText("");
                    txtId.grabFocus();
                }else if (pass.length()>20)
                {
                    JOptionPane.showMessageDialog(null, "Password cannot exceed 20 characters"," Login Name | Over limit", JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText("");
                    txtPassword.grabFocus();
                }else if (!postalCode.matches("^(([A-Za-z][0-9]){3})$"))
                {
                    JOptionPane.showMessageDialog(null, "Postal Code format must be a#a#a#"," Postal Code | Format mismatch", JOptionPane.ERROR_MESSAGE);
                    txtPostalCode.setText("");
                    txtPostalCode.grabFocus();
                }else if (city.equals("Select City") || province.equals("Select Province"))
                {
                    JOptionPane.showMessageDialog(null, "Province and City must be selected "," Province & City | Must be Selected", JOptionPane.ERROR_MESSAGE);
                }else if (!phone.matches("^([0-9]{3}\\-[0-9]{3}\\-[0-9]{4})$"))
                {
                    JOptionPane.showMessageDialog(null, "Please enter a 10 digit phone number. Number format is ###-###-####", "Phone | Mismatch", JOptionPane.ERROR_MESSAGE);
                    txtPhone.setText("");
                    txtPhone.grabFocus();
                }
                else
                {
                    try {
                        Socket socketCusRegistration = new Socket("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(socketCusRegistration.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream(socketCusRegistration.getOutputStream());
                        
                        dataToServer.writeInt(ProcessID.CUSTOMER_REGISTRATION); //Process Id for Customer Registration!
                        dataToServer.writeUTF(loginId);
                        dataToServer.writeUTF(pass);
                        dataToServer.writeUTF(role);
                        dataToServer.writeUTF(fName);
                        dataToServer.writeUTF(lName);
                        dataToServer.writeUTF(streetAdd);
                        dataToServer.writeUTF(city);
                        dataToServer.writeUTF(province);
                        dataToServer.writeUTF(postalCode);
                        dataToServer.writeUTF(email);
                        dataToServer.writeUTF(phone);
                        dataToServer.writeUTF(secQues1);
                        dataToServer.writeUTF(ans1);
                        dataToServer.writeUTF(secQues2);
                        dataToServer.writeUTF(ans2);
                        
                        boolean checkRegistration = dataFromServer.readBoolean();
                        
                        if (checkRegistration){
                            JOptionPane.showMessageDialog(null, "Your Account Has been created. Please login..", "Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);    
                            LoginForm l1 = new LoginForm();
                            socketCusRegistration.close();
                            CustomerRegistration.this.dispose();
                        }else{
                            JOptionPane.showMessageDialog(null, "LoginId already exits. Please use a different Login name.", "Sign Up Unsuccessful", JOptionPane.ERROR_MESSAGE);   
                            txtId.setText("");
                            txtId.grabFocus();
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(RestaurantRegistration.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        
        btnHome = new JButton("Home");
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(e.getSource()== btnHome)
            {
                WelcomeScreen ws = new WelcomeScreen();
                CustomerRegistration.this.dispose();
            
            }
            }
        });
        //Creating the panel to hold all the labels
        
        panelRegistration = new JPanel ();
        panelRegistration.setBorder(leftBorder);
        panelRegistration.setLayout(new GridLayout (16,2));
        
        panelRegistration.add(lblId);
        panelRegistration.add(txtId);
        panelRegistration.add(lblPassword);
        panelRegistration.add(txtPassword);
        panelRegistration.add(lblRole);
        panelRegistration.add(txtRole);
        panelRegistration.add(lblFirstName);
        panelRegistration.add(txtFirstName);
        panelRegistration.add(lblLastName);
        panelRegistration.add(txtLastName);
        panelRegistration.add(lblStreetName);
        panelRegistration.add(txtStreetName);
        panelRegistration.add(lblProvince);
        panelRegistration.add(comboBoxProvince);
        panelRegistration.add(lblCity);
        panelRegistration.add(cmbCity);
        panelRegistration.add(lblPostalCode);
        panelRegistration.add(txtPostalCode);
        panelRegistration.add(lblEmail);
        panelRegistration.add(txtEmail);
        panelRegistration.add(lblPhone);
        panelRegistration.add(txtPhone);
        panelRegistration.add(lblSq1);
        panelRegistration.add(comboBoxSq1);
        panelRegistration.add(lblAns1);
        panelRegistration.add(txtAns1);
        panelRegistration.add(lblSq2);
        panelRegistration.add(comboBoxSq2);
        panelRegistration.add(lblAns2);
        panelRegistration.add(txtAns2);
        panelRegistration.add(btnRegister);
        panelRegistration.add(btnHome);
       
    }
}