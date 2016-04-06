/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private DataInputStream dIn;
    
    
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
            //To get Current Balance
            String cId = txtId.getText();
            dIn = new DataInputStream(new FileInputStream("balance.txt"));
            while (dIn.available()>0)
            {
                if (dIn.readUTF().equals(cId))
                {
                    txtCurBalance.setText(dIn.readUTF());
                    txtCard.setText(dIn.readUTF());
                    txtExpiry.setText(dIn.readUTF());
                    txtCCV.setText(dIn.readUTF());
                    break;
                }
            }
            dIn.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
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
            private DataOutputStream dOut;

            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                String customerId = txtId.getText();
                String balance = txtCurBalance.getText();
                String addAmount = txtAddAmount.getText();
                String cardInfo = txtCard.getText();
                String expiry = txtExpiry.getText();
                String ccv = txtCCV.getText(); 
                
                File n = new File ("balance.txt");
                File bal = new File ("balance.txt");
                File temp = new File ("tempBalance.txt");
                
                
                dIn = new DataInputStream( new FileInputStream(bal));
                dOut = new DataOutputStream(new FileOutputStream(temp, true));
                
                while(dIn.available()>0)
                {
                    String cId = dIn.readUTF();
                    
                    if (cId.equals(customerId))
                    {
                        for (int i = 1; i < 5; i++)
                        {
                            dIn.readUTF();
                        }
                        
                        dOut.writeUTF(customerId);
                        double amt = Double.parseDouble(balance) + Double.parseDouble((addAmount));
                        dOut.writeUTF(Double.toString(amt));
                        dOut.writeUTF(cardInfo);
                        dOut.writeUTF(expiry);
                        dOut.writeUTF(ccv);
                    }
                    else
                    {
                        dOut.writeUTF(cId);
                        for (int i = 1; i < 5; i++)
                        {
                            dOut.writeUTF(dIn.readUTF());
                        }
                    }
                }
                dIn.close();
                dOut.close();
                
                bal.delete();
                temp.renameTo(n);
                
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null,"Updated","Customer Wallet Updated",JOptionPane.INFORMATION_MESSAGE);
                ThankUForAddingBalance tu = new ThankUForAddingBalance(id);
                AddBalance.this.dispose();
            }
                   
            });
        
        //ActionListener for exit button!
        btnExit.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?", 
                    "Exit Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (n == JOptionPane.YES_OPTION)
                    System.exit(0);
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