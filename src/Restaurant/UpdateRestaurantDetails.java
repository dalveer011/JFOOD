/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Restaurant;

import customer.AddBalance;
import customer.Customer_UpdateDetails;
import customer.HomeCustomer;
import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jfood.HeaderFooter;
import jfood.LogOut;
import jfood.ThankUForUpdatingDetails;
import loginAndRegistration.LoginForm;

/**
 *
 * @author Mazhar
 */
public class UpdateRestaurantDetails extends RestaurantMenuBar {
    //Declaration for Main Frame! 
    private JPanel panelCenter;
    
    //Declaration for Panel Inside
    private JLabel lblHeadingInside,lblId;
    
    private JLabel lblName, lblLastName, lblCity, 
            lblStreetName, lblProvince, lblPostalCode, lblEmail, lblPhone,lblPass;

    private JTextField txtName, txtLastName, txtCity, 
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

    
    public UpdateRestaurantDetails (final String id){
        this.id = id;
        this.initComponents();
        this.setTitle("Restaurant Update Details");
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        //Creating the menubar using MenuCustomer class!
        this.setJMenuBar(restMenuBar()); 
        this.add(this.getRestaurantToolBar(), BorderLayout.EAST);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
    
    private void initComponents (){
     //Create the inside details! 
        
        //Labels
        lblHeadingInside = new JLabel ("Update your contact information");
        
        lblId = new JLabel("Id");
        lblPass = new JLabel("Password");
        lblRole = new JLabel ("Role");
        lblName = new JLabel ("Enter First Name");
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
        txtPass = new JTextField();
        
        txtRole = new JTextField(); 
        txtRole.setEditable(false);
        
        txtName = new JTextField ();
        txtStreetName = new JTextField ();
        txtCity = new JTextField ();
        txtProvince = new JTextField ();
        txtPostalCode = new JTextField ();
        txtPhone = new JTextField ();
        txtEmail = new JTextField ();
        
        
        //Update button! 
        btnUpdate = new JButton ("Update");
        
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
        panelCenterInside1.add(lblName, g);
        g.gridx = 1; g.gridy = 3; 
        panelCenterInside1.add(txtName, g);
        g.gridx = 0; g.gridy = 4; 
        panelCenterInside1.add(lblStreetName, g);
        g.gridx = 1; g.gridy = 4; 
        panelCenterInside1.add(txtStreetName, g);
        g.gridx = 0; g.gridy = 5; 
        panelCenterInside1.add(lblCity, g);
        g.gridx = 1; g.gridy = 5; 
        panelCenterInside1.add(txtCity, g);
        g.gridx = 0; g.gridy = 6; 
        panelCenterInside1.add(lblProvince, g);
        g.gridx = 1; g.gridy = 6; 
        panelCenterInside1.add(txtProvince, g);
        g.gridx = 0; g.gridy = 7; 
        panelCenterInside1.add(lblPostalCode, g);
        g.gridx = 1; g.gridy = 7; 
        panelCenterInside1.add(txtPostalCode, g);
        g.gridx = 0; g.gridy = 8; 
        panelCenterInside1.add(lblEmail, g);
        g.gridx = 1; g.gridy = 8; 
        panelCenterInside1.add(txtEmail, g);
        g.gridx = 0; g.gridy = 9; 
        panelCenterInside1.add(lblPhone, g);
        g.gridx = 1; g.gridy = 9; 
        panelCenterInside1.add(txtPhone, g);
        g.gridx = 0; g.gridy = 10; 
        panelCenterInside1.add(lblSq1, g);
        g.gridx = 1; g.gridy = 10; 
        panelCenterInside1.add(comboBoxSq1, g);
        g.gridx = 0; g.gridy = 11; 
        panelCenterInside1.add(lblAns1, g);
        g.gridx = 1; g.gridy = 11; 
        panelCenterInside1.add(txtAns1, g);
        g.gridx = 0; g.gridy = 12; 
        panelCenterInside1.add(lblSq2, g);
        g.gridx = 1; g.gridy = 12; 
        panelCenterInside1.add(comboBoxSq2, g);
        g.gridx = 0; g.gridy = 13; 
        panelCenterInside1.add(lblAns2, g);
        g.gridx = 1; g.gridy = 13; 
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
