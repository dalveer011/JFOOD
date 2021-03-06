/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Restaurant;

import customer.HomeCustomer;
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
import jfood.HeaderFooter;

/**
 *
 * @author Mazhar
 */
public class ThankUForUpdatingDetails_Rst extends JFrame {
    private JLabel lblThanks,img;
    private JButton btnContinue, btnExit;
    private JPanel panelCenterInside1, panelCenterInside2, panelCenterInside3,
            panelCenter;
    private String id;
    
    public ThankUForUpdatingDetails_Rst (String  id){
        this.id = id;
        this.initComponents();
        this.setTitle("Thank you for the update");
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void initComponents (){
        //Create the panels:
      
        panelCenterInside1 = new JPanel ();
        panelCenterInside2 = new JPanel ();
        panelCenterInside3 = new JPanel ();
        panelCenter = new JPanel();
    
        
        //Creating the labels and the text field
        lblThanks = new JLabel ("Thank you for updating your details "+ id +". Your "
                + "account is successfully updated.");
        lblThanks.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblThanks.setForeground(Color.BLUE);
        img = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
        

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
        (new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantHome(id);
                ThankUForUpdatingDetails_Rst.this.dispose();
            }
        });
       
        panelCenterInside1.setLayout(new GridLayout (1,1));
        panelCenterInside1.add(img);
        
        panelCenterInside2.add(lblThanks);
        
        panelCenterInside3.setLayout(new FlowLayout());
        panelCenterInside3.add(btnContinue);
        panelCenterInside3.add(btnExit);
        
        panelCenter.setLayout(new GridLayout (3,1, 20, 40));
        panelCenter.add(panelCenterInside1);
        panelCenter.add(panelCenterInside2);
        panelCenter.add(panelCenterInside3);
    }
}

