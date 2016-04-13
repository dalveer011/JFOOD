/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.DBConnection;
import jfood.HeaderFooter;
import jfood.LogOut;
import loginAndRegistration.LoginForm;
import jfood.ThankUForUpdatingDetails;

public class Customer_UpdateDetails extends MenuCustomer {
    //Declaration for Main Frame! 
    private JPanel panelCenter;
    
    //Declaration for Panel Inside
    private JLabel lblHeadingInside,lblId;
    
    private JLabel lblFirstName, lblLastName, lblCity, 
            lblStreetName, lblProvince, lblPostalCode, lblEmail, lblPhone,lblPass;

    private JTextField txtFirstName, txtLastName, txtCity, 
            txtStreetName, txtProvince, txtPostalCode, txtEmail, txtPhone,txtPass,txtId;
    
    private JButton btnUpdate;
    private JPanel panelCenterInside1,panelCenterInside2 ;
    String id;
    private JLabel lblRole;
    
    String [] sq1 = {"What is your mother's maiden name?", "Which city you were born in?", 
        "What is your favourite holiday destination?"};
    
    String [] sq2 = {"Which is your dream car?", "Which city was your mother born in?", 
        "Who is your favourite school teacher?"};
    
    private JComboBox comboBoxSq1; //should we give them option to change the security qs?
    private JComboBox comboBoxSq2;
    private JLabel lblSq1;
    private JLabel lblSq2;
    private JLabel lblAns1;
    private JLabel lblAns2;
    private JTextField txtAns1;
    private JTextField txtAns2;
    private JTextField txtSq1;
    private JTextField txtSq2;
    private JTextField txtRole;
    
    private DataInputStream dIn;
    private DataOutputStream dOut;
    
    private DBConnection db = null;
    private ResultSet rs = null;
    private String querySecQuestions = "SELECT LOGINID, SECURITY_QUESTION1, ANSWER_1, SECURITY_QUESTION2, ANSWER_2 FROM SECURITY_QUESTIONS_JFOOD";

    
    public Customer_UpdateDetails (final String id){
        this.id = id;
        this.initComponents();
        this.setTitle("Customer Update Details | " + LoginForm.customer.getFirstName() + " " + LoginForm.customer.getLastName());
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        //Creating the menubar using MenuCustomer class!
        this.setJMenuBar(customerMenu()); 
        this.add(this.createMyToolBar(), BorderLayout.EAST);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        miAccountDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            Customer_UpdateDetails c = new Customer_UpdateDetails(id);
         }
        });
        miAddBalance.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AddBalance ab = new AddBalance(id);
            Customer_UpdateDetails.this.dispose();
         }
        });
        miLogout.addActionListener
        (
                new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l1 = new LogOut();
                Customer_UpdateDetails.this.dispose();
            }
        }
        );
        
        tbBtnUpdate.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Customer_UpdateDetails c1 = new Customer_UpdateDetails(id);
                Customer_UpdateDetails.this.dispose();
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
                AddBalance a1 = new AddBalance(id);
                Customer_UpdateDetails.this.dispose();
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
                LogOut l1 = new LogOut();
                Customer_UpdateDetails.this.dispose();
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
                Customer_UpdateDetails.this.dispose();
            }                
            }
        );
    }
    
    private void initComponents (){
     //Create the inside details! 
        
        //Labels
        lblHeadingInside = new JLabel ("Update your contact information");
        
        lblId = new JLabel("Id");
        lblPass = new JLabel("Password");
        lblRole = new JLabel ("Role");
        lblFirstName = new JLabel ("Enter First Name");
        lblLastName = new JLabel ("Enter Last Name");
        lblStreetName = new JLabel ("Enter Street Address");
        lblCity = new JLabel ("Enter City");
        lblProvince = new JLabel ("Enter Province");
        lblPostalCode = new JLabel ("Enter Postal Code");
        lblPhone = new JLabel ("Enter Phone");
        lblEmail = new JLabel ("Enter Email");
        lblSq1 = new JLabel ("Security Question 1");
        lblSq2 = new JLabel ("Security Question 2");
        lblAns1 = new JLabel ("Answer*");
        lblAns2 = new JLabel ("Answer*");
        
        
        comboBoxSq1 = new JComboBox(sq1);
        comboBoxSq2 = new JComboBox(sq2);
        
        txtAns1 = new JTextField ();
        txtAns2 = new JTextField ();
        
        
        txtId = new JTextField(id);
        txtId.setPreferredSize(new Dimension (280, 28));
        txtId.setEditable(false);
        txtPass = new JTextField(LoginForm.customer.getPassword());
        
        txtRole = new JTextField(LoginForm.customer.getRole()); 
        txtRole.setEditable(false);
        
        txtFirstName = new JTextField (LoginForm.customer.getFirstName());
        txtLastName = new JTextField (LoginForm.customer.getLastName());
        txtStreetName = new JTextField (LoginForm.customer.getStreetAddress());
        txtCity = new JTextField (LoginForm.customer.getCity());
        txtProvince = new JTextField (LoginForm.customer.getProvince());
        txtPostalCode = new JTextField (LoginForm.customer.getPostalCode());
        txtPhone = new JTextField (LoginForm.customer.getPhone());
        txtEmail = new JTextField (LoginForm.customer.getEmail());
        
        
        db = new DBConnection();
        rs = db.getInfo(querySecQuestions);
        
        try {
            while (rs.next()){
                if (txtId.getText().equals(rs.getString(1))){
                    txtAns1.setText(rs.getString(3));
                    txtAns2.setText(rs.getString(5));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Customer_UpdateDetails", JOptionPane.ERROR_MESSAGE);
        }
        
        //Update button! 
        btnUpdate = new JButton ("Update");
        btnUpdate.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
                
                String password = txtPass.getText();
                String fName = txtFirstName.getText();
                String lName = txtLastName.getText();
                String streetAdd = txtStreetName.getText();
                String city = txtCity.getText();
                String province = txtProvince.getText();
                String postalCode = txtPostalCode.getText();
                String email = txtEmail.getText();
                String phone = txtPhone.getText();
                String sq1 = (String) comboBoxSq1.getSelectedItem();
                String ans1 = txtAns1.getText();
                String sq2 = (String) comboBoxSq2.getSelectedItem();;
                String ans2 = txtAns2.getText();
                if(password.trim().isEmpty() ||fName.trim().isEmpty() ||lName.trim().isEmpty() ||streetAdd.trim().isEmpty() ||
                        city.trim().isEmpty() ||province.trim().isEmpty() ||postalCode.trim().isEmpty() ||email.trim().isEmpty() ||
                        phone.trim().isEmpty() ||ans1.trim().isEmpty() ||ans2.trim().isEmpty() )
                     JOptionPane.showMessageDialog(null,"All fields here are Mandatory","Fields Empty",JOptionPane.ERROR_MESSAGE);
                else 
                {
                    LoginForm.customer.setPassword(password);
                    LoginForm.customer.setFirstName(fName);
                    LoginForm.customer.setLastName(lName);
                    LoginForm.customer.setStreetAddress(streetAdd);
                    LoginForm.customer.setCity(city);
                    LoginForm.customer.setProvince(province);
                    LoginForm.customer.setPostalCode(postalCode);
                    LoginForm.customer.setEmail(email);
                    LoginForm.customer.setPhone(phone);
                    db.UpdateCustomerInfo(id, password, fName, lName, streetAdd, city, province, postalCode, email, phone);
                    db.UpdateSecurityInfo(id, password, sq1, ans1, sq2, ans2);
                    System.out.println("Update Complete");
                    System.out.println(password);
                    System.out.println(email);
                    db.closeConnection();
                    new ThankUForUpdatingDetails(id);
                    Customer_UpdateDetails.this.dispose();
            }             
        }
    });
        
        //PanelCenterInside to hold the Labels and TextFields
        panelCenterInside1 = new JPanel ();
        panelCenterInside1.setLayout(new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(4, 12, 4, 12);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.BOTH;
         
        g.gridx = 0; g.gridy = 0;        
        panelCenterInside1.add(lblId, g);
        g.gridx = 1; g.gridy = 0; 
        panelCenterInside1.add(txtId, g);
        g.gridx = 0; g.gridy = 1; 
        panelCenterInside1.add(lblPass, g);
        g.gridx = 1; g.gridy = 1; 
        panelCenterInside1.add(txtPass, g);
        g.gridx = 0; g.gridy = 2; 
        panelCenterInside1.add(lblRole, g);
        g.gridx = 1; g.gridy = 2; 
        panelCenterInside1.add(txtRole, g);
        g.gridx = 0; g.gridy = 3; 
        panelCenterInside1.add(lblFirstName, g);
        g.gridx = 1; g.gridy = 3; 
        panelCenterInside1.add(txtFirstName, g);
        g.gridx = 0; g.gridy = 4; 
        panelCenterInside1.add(lblLastName, g);
        g.gridx = 1; g.gridy = 4; 
        panelCenterInside1.add(txtLastName, g);
        g.gridx = 0; g.gridy = 5; 
        panelCenterInside1.add(lblStreetName, g);
        g.gridx = 1; g.gridy = 5; 
        panelCenterInside1.add(txtStreetName, g);
        g.gridx = 0; g.gridy = 6; 
        panelCenterInside1.add(lblCity, g);
        g.gridx = 1; g.gridy = 6; 
        panelCenterInside1.add(txtCity, g);
        g.gridx = 0; g.gridy = 7; 
        panelCenterInside1.add(lblProvince, g);
        g.gridx = 1; g.gridy = 7; 
        panelCenterInside1.add(txtProvince, g);
        g.gridx = 0; g.gridy = 8; 
        panelCenterInside1.add(lblPostalCode, g);
        g.gridx = 1; g.gridy = 8; 
        panelCenterInside1.add(txtPostalCode, g);
        g.gridx = 0; g.gridy = 9; 
        panelCenterInside1.add(lblEmail, g);
        g.gridx = 1; g.gridy = 9; 
        panelCenterInside1.add(txtEmail, g);
        g.gridx = 0; g.gridy = 10; 
        panelCenterInside1.add(lblPhone, g);
        g.gridx = 1; g.gridy = 10; 
        panelCenterInside1.add(txtPhone, g);
        g.gridx = 0; g.gridy = 11; 
        panelCenterInside1.add(lblSq1, g);
        g.gridx = 1; g.gridy = 11; 
        panelCenterInside1.add(comboBoxSq1, g);
        g.gridx = 0; g.gridy = 12; 
        panelCenterInside1.add(lblAns1, g);
        g.gridx = 1; g.gridy = 12; 
        panelCenterInside1.add(txtAns1, g);
        g.gridx = 0; g.gridy = 13; 
        panelCenterInside1.add(lblSq2, g);
        g.gridx = 1; g.gridy = 13; 
        panelCenterInside1.add(comboBoxSq2, g);
        g.gridx = 0; g.gridy = 14; 
        panelCenterInside1.add(lblAns2, g);
        g.gridx = 1; g.gridy = 14; 
        panelCenterInside1.add(txtAns2, g);
        
        panelCenterInside2 = new JPanel();
        panelCenterInside2.setLayout(new FlowLayout());
        panelCenterInside2.add(btnUpdate);
        
        panelCenter = new JPanel ();
        panelCenter.setLayout(new BorderLayout ());
        panelCenter.add(panelCenterInside1, BorderLayout.CENTER);
        panelCenter.add(panelCenterInside2, BorderLayout.SOUTH);
        
    }
}
