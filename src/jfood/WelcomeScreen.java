
package jfood;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Dalvir Sahota
*/

public class WelcomeScreen extends JFrame {

    private JPanel chooseBtn,contentArea,addInCenter,addInCenterTop,addInCenterBottom,logo;
    private JButton login,register,exit;
    private JLabel center,lblLogo,lblicon1,lblicon2,lblicon3,lblProcess;
    
    private JPanel panelInCenterBottom1, panelInCenterBottom2;
    private ImageIcon backgroundImg;
    
    public WelcomeScreen() {
    this.initComponents();
    //this.setSize(600,800);
    this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
    this.setTitle("Welcome | JFood");
    this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
    this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(addInCenter,BorderLayout.CENTER);
    this.setVisible(true);

    
    }
    private void initComponents() {
    
    login = new JButton("Login");
    exit= new JButton("Exit");
    exit.addActionListener(new ActionListener() {

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
    //adding action listener to login button
    login.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginForm f = new LoginForm();
            WelcomeScreen.this.dispose();
        }
    });
    register = new JButton("Register");
    //Adding action listener to register button
    register.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            new ConfirmRole();
            //RegistrationForm r = new RegistrationForm();
            WelcomeScreen.this.dispose();
        }
    });
    
        
    chooseBtn = new JPanel();
    chooseBtn.setLayout(new FlowLayout());
    chooseBtn.add(login);
    chooseBtn.add(register);
    chooseBtn.add(exit);
    
    logo = new JPanel();
    lblLogo = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
    lblicon1 = new JLabel(new ImageIcon(getClass().getResource("images/restautarnt.png")));
    lblicon2 = new JLabel(new ImageIcon(getClass().getResource("images/pay.png")));
    lblicon3 = new JLabel(new ImageIcon(getClass().getResource("images/home.png")));
   
    
    logo.add(lblLogo);
    
    center = new JLabel("Feeling Hungry! Came to Right Place ");
    center.setFont(new Font("Tahoma", 10, 24));
    
    contentArea = new JPanel();
    contentArea.add(center);
    //contentArea.setBackground(Color.red);
    
    //Jpanel to add in center in welcome screen
    addInCenterTop = new JPanel(new BorderLayout()); //changed to borderlayout!
    addInCenterTop.add(logo, BorderLayout.NORTH);
    addInCenterTop.add(contentArea, BorderLayout.CENTER);
    addInCenterTop.add(chooseBtn, BorderLayout.SOUTH); 
    
    addInCenterBottom = new JPanel(new BorderLayout());
    addInCenterBottom.setBackground(Color.YELLOW);
    
    panelInCenterBottom1 = new JPanel();
    panelInCenterBottom2 = new JPanel();
    
    panelInCenterBottom1.setLayout(new GridLayout(1,3));
    panelInCenterBottom1.setBackground(Color.YELLOW);
    
    panelInCenterBottom1.add(lblicon1);
    panelInCenterBottom1.add(lblicon2);
    panelInCenterBottom1.add(lblicon3);
    
    backgroundImg = new ImageIcon (getClass().getResource("resources/bck_welcome_south.png"));
    lblProcess = new JLabel (backgroundImg);
    
    panelInCenterBottom2.setLayout (new GridLayout(1,1));
    panelInCenterBottom2.setBackground(Color.YELLOW);
    panelInCenterBottom2.add(lblProcess);
    
    addInCenterBottom.add(panelInCenterBottom1, BorderLayout.CENTER);
    addInCenterBottom.add(panelInCenterBottom2, BorderLayout.SOUTH);
    
    addInCenter = new JPanel(new GridLayout(2,1));
    addInCenter.add(addInCenterTop);
    addInCenter.add(addInCenterBottom);
    
    
    }
}