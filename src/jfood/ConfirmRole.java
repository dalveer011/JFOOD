/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author Mazhar
 */
public class ConfirmRole extends JFrame {
    private JButton btnRestaurantOwner, btnCustomer, btnCancel;
    private JLabel lblTitle;
    private JPanel panelCenter, panelSouth;
    
    
    public ConfirmRole (){
        this.initComponents();
        this.setTitle("User | Role");
        this.setSize(450, 120);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void initComponents (){
        btnRestaurantOwner = new JButton ("Restaurant Owner");
        btnCustomer = new JButton ("Customer");
        btnCancel = new JButton ("Cancel");
        
        lblTitle = new JLabel ("Are you a customer or a restaurant owner? Please confirm..", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Klee", Font.BOLD, 14));
        
        panelCenter = new JPanel ();
        panelCenter.setBorder(new EmptyBorder (15, 10, 15, 10));
        panelCenter.add(lblTitle);
        
        panelSouth = new JPanel ();
        panelSouth.add(btnRestaurantOwner);
        panelSouth.add(btnCustomer);
        panelSouth.add(btnCancel);
        
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmRole.this.dispose();
                new WelcomeScreen();
            }
        });
        
        btnCustomer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerRegistration();
                ConfirmRole.this.dispose();
            }
        });
        
        btnRestaurantOwner.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantRegistration();
                ConfirmRole.this.dispose();
            }
        });
    }
}
