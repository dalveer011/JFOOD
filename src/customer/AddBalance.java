/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jfood.HeaderFooter;
import jfood.LogOut;
import jfood.ProcessID;
import loginAndRegistration.LoginForm;

/**
 *
 * @author Mazhar
 */
public class AddBalance extends MenuCustomer {
    private JLabel lblCurBalance, lblAddAmount, lblCard, lblExpiry, lblCCV, lblCopyRight, imgWest, lblHeading,lblId;
    private JTextField txtCurBalance, txtAddAmount, txtCard, txtExpiry, txtCCV,txtId;
    private JButton btnAdd, btnExit;
    private JPanel  panelCenterMiddle;
    private String id;
    private JPanel panelCenterBottom;
    private JPanel panelCenter;
    private JPanel panelCenterTop;
    
    private DBConnection db;
    private ResultSet rs;
    private String query = "SELECT LOGINID, CURRENTBAL, CARDINFO, EXPIRY, CCV FROM CUSTOMERs_JFOOD";

    
    public AddBalance (final String id){
    this.id = id;
    this.initComponents();

    this.setTitle("Add Balance to your Wallet | " + LoginForm.customer.getFirstName() + " " + LoginForm.customer.getLastName());
    this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
    this.setJMenuBar(customerMenu());
    this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
    //this.add(HeaderFooter.getHeader(),BorderLayout.NORTH);
    this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
    //this.add(panelNorth, BorderLayout.NORTH);
    this.add(panelCenter, BorderLayout.CENTER);
    //this.add(panelSouth, BorderLayout.SOUTH);
    this.add(createMyToolBar(), BorderLayout.EAST);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
        miAccountDetails.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            Customer_UpdateDetails c = new Customer_UpdateDetails(id);
            AddBalance.this.dispose();
         }
     });
        miLogout.addActionListener
        (
                new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut l2 = new LogOut();
                AddBalance.this.dispose();
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
                AddBalance.this.dispose();
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
                AddBalance.this.dispose();
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
                AddBalance.this.dispose();
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
                AddBalance.this.dispose();
            }                
            }
        );
    }
    
    private void initComponents (){
        //Create the labels
        lblCurBalance = new JLabel ("Current Balance");
        lblAddAmount = new JLabel ("Add Amount(without , or $)");
        lblCard = new JLabel ("Debit/Credit Card Number");
        lblExpiry = new JLabel ("Expiry");
        lblCCV = new JLabel ("Security Number");   
        lblId = new JLabel("Customer id");
        
        //Creating the textfields
        txtAddAmount = new JTextField();
        txtAddAmount.setPreferredSize(new Dimension (250, 30));
        txtCard = new JTextField();
        txtCard.setPreferredSize(new Dimension (250, 30));
        
        txtExpiry = new JTextField();
        txtExpiry.setPreferredSize(new Dimension (250, 30));
        txtCCV = new JTextField();
        txtCCV.setPreferredSize(new Dimension (250, 30));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setText(id);
        txtId.setPreferredSize(new Dimension (250, 30));
        txtCurBalance = new JTextField();
        txtCurBalance.setPreferredSize(new Dimension (250, 30));
        txtCurBalance.setEditable(false);
        
        try {
            Socket socketGetCard = new Socket ("localHost", 8000);
            DataInputStream dataFromServer = new DataInputStream(socketGetCard.getInputStream());
            DataOutputStream dataToServer = new DataOutputStream (socketGetCard.getOutputStream());

            dataToServer.writeInt(ProcessID.GET_CREDIT_CARD_DETAILS); //Process Id for getting Sq Answers from Security_Answers Table
            dataToServer.writeUTF(id);
            
            txtCurBalance.setText(dataFromServer.readUTF());
            txtCard.setText(dataFromServer.readUTF());
            txtExpiry.setText(dataFromServer.readUTF());
            txtCCV.setText(dataFromServer.readUTF());
            
            socketGetCard.close();
        } catch (IOException ex) {
            Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Creating the buttons
        btnAdd = new JButton ("Add");
        btnExit = new JButton ("Exit");
        
        //Adding Action listener to add Balance
        btnAdd.addActionListener
        (
            new ActionListener (){

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String customerId,balance, amt, cardInfo, expiry, ccv;
                db = new DBConnection();
                
                customerId = txtId.getText();
                balance = txtCurBalance.getText();
                amt = txtAddAmount.getText();
                cardInfo = txtCard.getText();
                expiry = txtExpiry.getText();
                ccv = txtCCV.getText();
                
                if (amt.trim().isEmpty() || cardInfo.trim().isEmpty() || expiry.trim().isEmpty() || ccv.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Fields marked with * cannot be empty" , "Empty Fields", JOptionPane.ERROR_MESSAGE);
                }else if (!cardInfo.matches("^([4-5][0-9]{3}\\-[0-9]{4}\\-[0-9]{4}\\-[0-9]{4})$")) 
                {
                    JOptionPane.showMessageDialog(null, "Credit Card must start with 4 or 5 and must be in 4|5###-####-####-#### format" , "Credit Card | Format mismatch", JOptionPane.ERROR_MESSAGE);
                    txtCard.setText("");
                    txtCard.grabFocus();
                }else if (!expiry.matches("^(0[1-9]|1[0-2])\\/[0-9]{2}$")) 
                {
                    JOptionPane.showMessageDialog(null, "Expiry date should match MM/YY" , "Expiry | Format mismatch", JOptionPane.ERROR_MESSAGE);
                    txtExpiry.setText("");
                    txtExpiry.grabFocus();
                }else if (!ccv.matches("^([0-9]{3})$")) 
                {
                    JOptionPane.showMessageDialog(null, "CCV should match ###" , "CCV | Format mismatch", JOptionPane.ERROR_MESSAGE);
                    txtCCV.setText("");
                    txtCCV.grabFocus();
                }else {
                    double b = Double.parseDouble(balance);
                    double a = Double.parseDouble(amt);
                    double c = b + a;
                    double addAmt = c;

                    try {
                        Socket socketUpdateCreditCardInfo = new Socket ("localHost", 8000);
                        DataInputStream dataFromServer = new DataInputStream(socketUpdateCreditCardInfo.getInputStream());
                        DataOutputStream dataToServer = new DataOutputStream (socketUpdateCreditCardInfo.getOutputStream());

                        dataToServer.writeInt(ProcessID.UPDATE_CREDITCARD_DETAILS);
                        dataToServer.writeUTF(customerId);
                        dataToServer.writeDouble(addAmt);
                        dataToServer.writeUTF(cardInfo);
                        dataToServer.writeUTF(expiry);
                        dataToServer.writeUTF(ccv);
                        
                        boolean checkCusUpdated = dataFromServer.readBoolean();
                        
                        if (checkCusUpdated)
                        {
                            JOptionPane.showMessageDialog(null,"Updated","Customer Wallet Updated",JOptionPane.INFORMATION_MESSAGE);
                            ThankUForAddingBalance tu = new ThankUForAddingBalance(id);
                            AddBalance.this.dispose();
                        }else
                        {
                            JOptionPane.showMessageDialog(null,"Update Failed","Update Unsuccessful",JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }               
        }               
    });
        
        //ActionListener for exit button!
        btnExit.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?", 
                    "Exit Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (n == JOptionPane.YES_OPTION){
                    if (db != null){
                        db.closeConnection();
                    }
                    System.exit(0);
            }
            else if (n == JOptionPane.NO_OPTION)
            {}
        }
    });
        
   //Creating the panels
       
        panelCenterMiddle = new JPanel (new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.BOTH;
         
        g.gridx = 0;
        g.gridy = 0;        
        panelCenterMiddle.add(lblId, g);
        g.gridx = 1;
        g.gridy = 0; 
        panelCenterMiddle.add(txtId, g);
        g.gridx = 0;
        g.gridy = 1; 
        panelCenterMiddle.add(lblCurBalance, g);
        g.gridx = 1;
        g.gridy = 1; 
        panelCenterMiddle.add(txtCurBalance, g);
        g.gridx = 0;
        g.gridy = 2; 
        panelCenterMiddle.add(lblAddAmount, g);
        g.gridx = 1;
        g.gridy = 2; 
        panelCenterMiddle.add(txtAddAmount, g);
        g.gridx = 0;
        g.gridy = 3; 
        panelCenterMiddle.add(lblCard, g);
        g.gridx = 1;
        g.gridy = 3; 
        panelCenterMiddle.add(txtCard, g);
        g.gridx = 0;
        g.gridy = 4; 
        panelCenterMiddle.add(lblExpiry, g);
        g.gridx = 1;
        g.gridy = 4; 
        panelCenterMiddle.add(txtExpiry, g);
        g.gridx = 0;
        g.gridy = 5; 
        panelCenterMiddle.add(lblCCV, g);
        g.gridx = 1;
        g.gridy = 5; 
        panelCenterMiddle.add(txtCCV, g);
        
        panelCenterBottom = new JPanel (new FlowLayout(FlowLayout.CENTER, 20, 30));
        panelCenterBottom.add(btnAdd);
        panelCenterBottom.add(btnExit);
        
        panelCenterTop = new JPanel();
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
        panelCenterTop.add(logo);
        
        
        panelCenter = new JPanel (new BorderLayout ());
        panelCenter.add(panelCenterTop, BorderLayout.NORTH);
        panelCenter.add (panelCenterMiddle, BorderLayout.CENTER);
        panelCenter.add(panelCenterBottom, BorderLayout.SOUTH);    
    }
}   