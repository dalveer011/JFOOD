/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;

import customer.MenuCustomer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mazhar
 */
public class LogOut extends JFrame {
    private JLabel lblLogOut, lblLogo;
    private JButton btnContinue, btnExit;
    private JPanel panelCenter, panelCenterInside1, panelCenterInside2, panelCenterNorth, panelCenterMiddle, panelCenterSouth;
    private ImageIcon logo;
    public LogOut (){
        this.initComponents();
        this.setTitle("Logged out!");
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        //this.add(HeaderFooter.getHeader(),BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelCenterInside1, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void initComponents (){
        panelCenterInside1 = new JPanel ();
        panelCenterInside2 = new JPanel (new FlowLayout(1, 10, 0));
        //Create the logo:
        logo = new ImageIcon (getClass().getResource("images/logo.png"));
        
        //Creating the labels and the text field
        lblLogOut = new JLabel ("                        Thank you for using JFood. You have successfully logged out.");
        lblLogOut.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblLogOut.setForeground(Color.BLUE);
        
       
        btnContinue = new JButton ("Continue");
        btnExit = new JButton ("Exit");
        
        //ActionListener for exit button!
        btnExit.addActionListener
        (
            new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        btnContinue.addActionListener
        (
            new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomeScreen w2 = new WelcomeScreen();
                LogOut.this.dispose();
            }
        });
        
      
        panelCenterInside2.add(btnContinue);
        panelCenterInside2.add(btnExit);
        
        panelCenterInside1.setLayout(new GridLayout(3,1,0,30));
        panelCenterInside1.add(new JLabel(logo));
        panelCenterInside1.add(lblLogOut);
        panelCenterInside1.add(panelCenterInside2);
        
       
    }
}
