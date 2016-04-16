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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import jfood.ProcessID;
import jfood.ThankUForUpdatingDetails_Cus;
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
    
    private JLabel lblName, lblCity, 
            lblStreetName, lblProvince, lblPostalCode, lblEmail, lblPhone,lblPass;

    private JTextField txtName, txtCity, 
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
    private JTextField txtRole;
    
    private DBConnection db = null;
    private ResultSet rs = null;
    private JComboBox comboBoxProvince;
    private JComboBox comboBoxCity;
    private String ans1;
    private String ans2;

    
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

                //Adding action listener to the menubar!
        gotoHome.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new RestaurantHome (id);
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        addItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new AddNewItems(id);
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        deleteItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new DeleteItems(id);
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        updateItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new UpdateItems(id);
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        updateAcDetails.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateRestaurantDetails a = new UpdateRestaurantDetails(id);
                UpdateRestaurantDetails.this.dispose();
            }
        });
        
        logOut.addActionListener
        (new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new LogOut();
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        tbBtnAdd.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddNewItems a = new AddNewItems(id);
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );

        tbBtnDelete.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                DeleteItems a = new DeleteItems(id);
                UpdateRestaurantDetails.this.dispose();
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
                UpdateItems a = new UpdateItems(id);
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        tbBtnLogOut.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut a = new LogOut();
                UpdateRestaurantDetails.this.dispose();
            }                
            }
        );
        
        tbBtnResHome.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                RestaurantHome a = new RestaurantHome(id);
                UpdateRestaurantDetails.this.dispose();
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
        lblName = new JLabel ("Enter Restaurant Name");
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
        
        try {
            Socket socketGetRestaurantDetails = new Socket("localHost", 8000);
            DataInputStream dataFromServer = new DataInputStream(socketGetRestaurantDetails.getInputStream());
            DataOutputStream dataToServer = new DataOutputStream (socketGetRestaurantDetails.getOutputStream());

            dataToServer.writeInt(ProcessID.GET_SECURITY_ANSWERS); //Process Id for getting Sq Answers from Security_Answers Table
            dataToServer.writeUTF(id);
            ans1 = dataFromServer.readUTF();
            ans2 = dataFromServer.readUTF(); 
            socketGetRestaurantDetails.close();
        } catch (IOException ex) {
            Logger.getLogger(UpdateRestaurantDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        comboBoxSq1 = new JComboBox(sq1);
        comboBoxSq2 = new JComboBox(sq2);
        comboBoxProvince = new JComboBox();
        comboBoxCity = new JComboBox();
        //populating state combo box
         try {
            db = new DBConnection();
            System.out.println("hello");
            rs = db.getInfo("select distinct state from jfood_cities");
            System.out.println("ends");
            comboBoxProvince.addItem("Select State");   
            comboBoxCity.addItem("Select City");   
            while(rs.next()){
            comboBoxProvince.addItem(rs.getString(1));
            }
            comboBoxProvince.setSelectedIndex(0);
        } catch (SQLException ex) {
            System.out.println("Error in customerRegistration "+ex.getMessage());
        }
        
        comboBoxProvince.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                db = new DBConnection();  
                if(comboBoxProvince.getSelectedIndex() == 0){
                comboBoxCity.removeAllItems();
                comboBoxCity.addItem("Select City");
               }else{
                try {       
                    rs = db.getInfo("select city from jfood_cities where state = '"+comboBoxProvince.getSelectedItem().toString()+"'");
                    comboBoxCity.removeAllItems();   
                    //cmbCity.addItem("select city");
                    while(rs.next()){
                        comboBoxCity.addItem(rs.getString(1));
                    }
                    comboBoxCity.setSelectedIndex(0);
                    }  catch (SQLException ex) {
                    System.out.println("Error in item listener of cmState "+ex.getMessage());
                }
            }
            }
        }); 
         
        txtAns1 = new JTextField (ans1);
        txtAns2 = new JTextField (ans2);
        
        txtId = new JTextField(id);
        txtId.setPreferredSize(new Dimension (280, 28));
        txtId.setEditable(false);    
        txtPass = new JTextField(LoginForm.restaurantOwner.getPassword());      
        txtRole = new JTextField(LoginForm.restaurantOwner.getRole()); 
        txtRole.setEditable(false);
        txtName = new JTextField (LoginForm.restaurantOwner.getName());
        txtStreetName = new JTextField (LoginForm.restaurantOwner.getStreetAddress());
        txtPostalCode = new JTextField (LoginForm.restaurantOwner.getPostalCode());
        txtPhone = new JTextField (LoginForm.restaurantOwner.getPhone());
        txtEmail = new JTextField (LoginForm.restaurantOwner.getEmail());
        
        
        //Update button! 
        btnUpdate = new JButton ("Update");
        
        btnUpdate.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
                
                String password = txtPass.getText();
                String name = txtName.getText();
                String streetAdd = txtStreetName.getText();
                String province = (String)comboBoxProvince.getSelectedItem();
                String city = (String)comboBoxCity.getSelectedItem();
                String postalCode = txtPostalCode.getText().toUpperCase();
                String email = txtEmail.getText();
                String phone = txtPhone.getText();
                String sq1 = (String) comboBoxSq1.getSelectedItem();
                String updatedAns1 = txtAns1.getText();
                String sq2 = (String) comboBoxSq2.getSelectedItem();;
                String updatedAns2 = txtAns2.getText();
                if(password.trim().isEmpty() ||name.trim().isEmpty() ||streetAdd.trim().isEmpty() ||
                        city.trim().isEmpty() ||province.trim().isEmpty() ||postalCode.trim().isEmpty() ||email.trim().isEmpty() ||
                        phone.trim().isEmpty() ||ans1.trim().isEmpty() ||ans2.trim().isEmpty() )
                {
                    JOptionPane.showMessageDialog(null,"All fields here are Mandatory","Fields Empty",JOptionPane.ERROR_MESSAGE);
                }else if (password.length()>20)
                {
                    JOptionPane.showMessageDialog(null, "Password cannot exceed 20 characters"," Login Name | Over limit", JOptionPane.ERROR_MESSAGE);
                    txtPass.setText("");
                    txtPass.grabFocus();
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
                    LoginForm.restaurantOwner.setPassword(password);
                    LoginForm.restaurantOwner.setName(name);
                    LoginForm.restaurantOwner.setStreetAddress(streetAdd);
                    LoginForm.restaurantOwner.setCity(city);
                    LoginForm.restaurantOwner.setProvince(province);
                    LoginForm.restaurantOwner.setPostalCode(postalCode);
                    LoginForm.restaurantOwner.setEmail(email);
                    LoginForm.restaurantOwner.setPhone(phone);
                    
                    try {
                        Socket updateRstInfo = new Socket ("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(updateRstInfo.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream (updateRstInfo.getOutputStream());

                        dataToServer.writeInt(ProcessID.UPDATE_RESTAURANT_DETAILS); //ProcessId for Updating Restaurant Info.
                        
                        dataToServer.writeUTF(id);
                        dataToServer.writeUTF(password);
                        dataToServer.writeUTF(name);
                        dataToServer.writeUTF(streetAdd);
                        dataToServer.writeUTF(city);
                        dataToServer.writeUTF(province);
                        dataToServer.writeUTF(postalCode);
                        dataToServer.writeUTF(email);
                        dataToServer.writeUTF(phone);
                        dataToServer.writeUTF(sq1);
                        dataToServer.writeUTF(updatedAns1);
                        dataToServer.writeUTF(sq2);
                        dataToServer.writeUTF(updatedAns2);
                        
                        boolean checkRstUpdated = dataFromServer.readBoolean();
                        if (checkRstUpdated)
                        {
                            System.out.println("Update Complete");
                            System.out.println(password);
                            System.out.println(email);
                            JOptionPane.showMessageDialog(null, "Restaurant details updated", "Update Successful" , JOptionPane.INFORMATION_MESSAGE);
                            ThankUForUpdatingDetails_Rst a = new ThankUForUpdatingDetails_Rst(LoginForm.restaurantOwner.getLoginId());
                            UpdateRestaurantDetails.this.dispose();
                        }else {
                            JOptionPane.showMessageDialog(null, "Restaurant details could not be updated", "Update was unsuccessful" , JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(UpdateRestaurantDetails.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
        panelCenterInside1.add(lblName, g);
        g.gridx = 1; g.gridy = 3; 
        panelCenterInside1.add(txtName, g);
        g.gridx = 0; g.gridy = 4; 
        panelCenterInside1.add(lblStreetName, g);
        g.gridx = 1; g.gridy = 4; 
        panelCenterInside1.add(txtStreetName, g);
        g.gridx = 0; g.gridy = 5; 
        panelCenterInside1.add(lblProvince, g);
        g.gridx = 1; g.gridy = 5; 
        panelCenterInside1.add(comboBoxProvince, g);
        g.gridx = 0; g.gridy = 6; 
        panelCenterInside1.add(lblCity, g);
        g.gridx = 1; g.gridy = 6; 
        panelCenterInside1.add(comboBoxCity, g);
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
