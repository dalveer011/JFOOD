/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
/**
 *
 * @author Mazhar
 */
public class RestaurantRegistration extends JFrame {
    
    //Declaration!
    //private JLabel lblHeading;
    private JMenuBar back;
    private JMenuItem goback;
    private JMenu goToHome;
    public  JLabel lblId, lblPassword, lblName, lblCity, 
            lblStreetName, lblProvince, lblPostalCode, lblEmail, lblPhone, 
            lblRole, lblSq1, lblSq2, lblAns1, lblAns2;
    public  JTextField txtId, txtName, txtLastName, txtCity, 
            txtStreetName, txtPostalCode, txtEmail, txtPhone, txtAns1, txtAns2;
    private JPasswordField txtPassword; //used JPasswordField instead of JTextField
    public  JComboBox comboBoxRole, comboBoxSq1, comboBoxSq2, comboBoxProvince;  
    private JButton btnRegister,btnHome;   
    private JPanel panelRegistration, panelHeading, panelButton,center;
    
    private DBConnection db = null;
    private JTextField txtRole;
    private JComboBox comboBoxCity;
    
    
    //Constructors
    public RestaurantRegistration (){
        this.initComponents();
        this.setTitle("Registration Form | Restaurant Owner");
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.setJMenuBar(back);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelRegistration, BorderLayout.CENTER);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); //Terminate the application on pressing close button
        this.setVisible(true);
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
                RestaurantRegistration.this.dispose();
            }
        });
        back = new JMenuBar();
        goToHome.add(goback);
        back.add(goToHome);
    
        lblId = new JLabel ("Enter Login Name*");
        lblPassword = new JLabel ("Enter Password*");
        lblName = new JLabel ("Enter Resraurant's Name*");
        lblStreetName = new JLabel ("Enter Street Address*");
        lblCity = new JLabel ("Enter City*");
        lblProvince = new JLabel ("Enter Province*");
        lblPostalCode = new JLabel ("Enter Postal Code*");
        lblPhone = new JLabel ("Enter Phone*");
        lblEmail = new JLabel ("Enter Email");
        lblRole = new JLabel ("Role*");
        lblSq1 = new JLabel ("Security Question 1");
        lblSq2 = new JLabel ("Security Question 2");
        lblAns1 = new JLabel ("Answer*");
        lblAns2 = new JLabel ("Answer*");
        
        
        //TextFields created
        txtId = new JTextField ();
        txtPassword = new JPasswordField ();
        txtName = new JTextField ();
        txtStreetName = new JTextField ();
        comboBoxCity = new JComboBox ();
        txtPostalCode = new JTextField ();
        txtPhone = new JTextField ();
        txtEmail = new JTextField ();
        txtAns1 = new JTextField ();
        txtAns2 = new JTextField ();
        txtRole = new JTextField("Restaurant Owner");
        txtRole.setEditable(false);
        comboBoxProvince = new JComboBox();
        
        //Populating the ComboBox City and Province
        
        db = new DBConnection();
        ResultSet rsGetState = db.getInfo("Select distinct STATE from JFOOD_CITIES");
        ResultSet rsGetCity = db.getInfo("Select distinct city from JFOOD_CITIES ");
        
        try {
            while (rsGetState.next()){
                comboBoxProvince.addItem(rsGetState.getString(1));
            }
            rsGetState.close();
            
            while (rsGetCity.next()){
                comboBoxCity.addItem(rsGetCity.getString (1));
            }
            rsGetCity.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String [] sq1 = {"What is your mothers maiden name?", "Which city you were born in?", 
        "What is your favourite holiday destination?"};
        comboBoxSq1 = new JComboBox(sq1);
        
        String [] sq2 = {"Which is your dream car?", "Which city was your mother born in?", 
        "Who is your favourite school teacher?"};
        comboBoxSq2 = new JComboBox(sq2);
        
        //String [] province = {"Ontario", "British Columbia", "Quebec", "Prince Edward Islands", "New Brunswick" , "Manitoba" , "Nova Scotia", "Alberta", "Saskatchewan", "Newfoundland and Labrador"};
                
        //Register Button with actionlistener to Login form!
        
        btnRegister = new JButton ("Register");
        
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loginId = txtId.getText();
                char[] password = txtPassword.getPassword();
                String pass = String.valueOf(password);
                String role = txtRole.getText();
                String name = txtName.getText();
                String streetAdd = txtStreetName.getText();
                String city = (String)comboBoxCity.getSelectedItem();
                String province = (String)comboBoxProvince.getSelectedItem();
                String postalCode = txtPostalCode.getText();
                String email = txtEmail.getText();
                String phone = txtPhone.getText();
                String secQues1 = (String) comboBoxSq1.getSelectedItem();
                String ans1 = txtAns1.getText();
                String secQues2 = (String) comboBoxSq2.getSelectedItem();
                String ans2 = txtAns2.getText();
                
                if(loginId.trim().isEmpty() || pass.trim().isEmpty()||name.trim().isEmpty()||streetAdd.trim().isEmpty()
                        ||city.trim().isEmpty()||province.trim().isEmpty()||postalCode.trim().isEmpty()||phone.trim().isEmpty()||ans1.trim().isEmpty()||ans2.trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null, "Fields Marked as * can not be left Blank","Can not be Empty", JOptionPane.ERROR_MESSAGE);
                }else{
                    
                        db = new DBConnection();
                        db.addRestaurantInfo (loginId, pass, role, name, streetAdd, city, province, postalCode, email, phone);
                        db.addSecurityQuestions (loginId, pass, role, secQues1, ans1, secQues2, ans2);
                                          
                    JOptionPane.showMessageDialog(null, "Your Account Has been created. Please login..", "Sign Up Successful", JOptionPane.INFORMATION_MESSAGE);    
                    
                    //Closing connection
                    db.closeConnection();
                    
                    LoginForm l1 = new LoginForm();
                    RestaurantRegistration.this.dispose();
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
                RestaurantRegistration.this.dispose();
            
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
        panelRegistration.add(lblName);
        panelRegistration.add(txtName);
        panelRegistration.add(lblStreetName);
        panelRegistration.add(txtStreetName);
        panelRegistration.add(lblCity);
        panelRegistration.add(comboBoxCity);
        panelRegistration.add(lblProvince);
        panelRegistration.add(comboBoxProvince);
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
