/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfood;

import database.DBConnection;
import loginAndRegistration.LoginForm;
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
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author HARSH
 */
public class ForgotPassword extends JFrame {
    
    private JLabel lblques1;
    public JTextField txtEnterId,txtAns1,txtAns2,txtQues1,txtQues2;
    private JPanel panelCenter,panelCenterSouth,panelCenterMain,PanelCenterBottom,panelCenterNorth,Panel;
    private JLabel lblSq2,lblAns1,lblAns2,lblPassword;
    private JButton submit,reset,Login,Exit, btnBack;
    private JTextField txtPassword;
    private JLabel logo;
    private String id;
    private JLabel lblEnterId;
    private JLabel lblSq1;
    private String ans1,ans2,password;
    
    private DBConnection db = null; //Need to fix
    private ResultSet rs = null;
    private String query = "SELECT LOGINID, PASSWORD, SECURITY_QUESTION1, ANSWER_1, SECURITY_QUESTION2, ANSWER_2 FROM SECURITY_QUESTIONS_JFOOD";
            
    public ForgotPassword(final String id)
    {
        this.id=id;
        this.initComponents();
        this.add(panelCenter,BorderLayout.CENTER);
        this.setTitle("Forgot Password");
        this.setVisible(true);
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        txtEnterId.setText(id);
        txtEnterId.setEditable(false);

        
            try {
            Socket socketfbPassScreen = new Socket ("localHost", 8000);
            DataInputStream dataFromServer = new DataInputStream(socketfbPassScreen.getInputStream());
            DataOutputStream dataToServer = new DataOutputStream (socketfbPassScreen.getOutputStream());
            
            dataToServer.writeInt(ProcessID.GET_PASSWORD_SECURITY_ANSWERS_FGPASS); //Process Id for Retreiving secQues1 and answes along with passwrd | Forgot Password Screen
            
            System.out.println(id);
            dataToServer.writeUTF(id);
            
            password = dataFromServer.readUTF();
            String Ques1 = dataFromServer.readUTF();
            ans1 = dataFromServer.readUTF();
            String Ques2 = dataFromServer.readUTF();
            ans2 = dataFromServer.readUTF();
            txtQues1.setText(Ques1);
            txtQues1.setEditable(false);
            txtQues2.setText(Ques2);
            txtQues2.setEditable(false);
            
            socketfbPassScreen.close();
        } catch (IOException ex) {
            Logger.getLogger(ForgotPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
            
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userAns1 = txtAns1.getText();
                String userAns2 = txtAns2.getText();
                
                int count=0;

                        if(userAns1.equalsIgnoreCase(ans1) & userAns2.equalsIgnoreCase(ans2))
                        {
                            count++;
                            txtPassword.setText(password);
                        }
                        
                        if(count==0)
                        {
                            JOptionPane.showMessageDialog(null,"Answers dont match","Error",JOptionPane.ERROR_MESSAGE);
                            txtAns1.setText("");
                            txtAns2.setText("");
                        } 
            }              
        });
        
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtPassword.getText().equals(""))
                {
                    new LoginForm();
                    ForgotPassword.this.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Please answer your security questions","Generate Password",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtAns1.setText("");
                txtAns2.setText("");
                txtPassword.setText("");
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm();
                ForgotPassword.this.dispose();
            }
        });
        
        Exit.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?", 
                    "Exit Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (n == JOptionPane.YES_OPTION){
                if (db != null)
                db.closeConnection();
                System.exit(0);
            }
                    
            else if (n == JOptionPane.NO_OPTION)
            {}
        }
    });
    }
    
    private void initComponents()
    {
        
        lblEnterId = new JLabel("Login ID");
        lblSq1 = new JLabel("Security Question1");
        lblSq2 = new JLabel("Security Question2");
        lblAns1 = new JLabel("Answer");
        lblAns2 = new JLabel("Answer");
        lblPassword = new JLabel("Show Password");
        
        submit = new JButton("Submit");
        reset = new JButton("Reset");
        Exit = new JButton("Exit");
        Login = new JButton("Login");
        btnBack = new JButton ("Back");
        
        txtEnterId = new JTextField();
        txtQues1 = new JTextField();
        txtQues2 = new JTextField();
        txtAns1 = new JTextField();
        txtAns2 = new JTextField();
        txtPassword = new JTextField();
        txtPassword.setEditable(false);
        
        txtEnterId.setPreferredSize(new Dimension (280, 30));
        txtQues1.setPreferredSize(new Dimension (280, 30));
        txtQues2.setPreferredSize(new Dimension (280, 30));
        txtAns1.setPreferredSize(new Dimension (280, 30));
        txtAns2.setPreferredSize(new Dimension (280, 30));
        txtPassword.setPreferredSize(new Dimension (280, 30));
        
        panelCenterMain = new JPanel();
        panelCenterMain.setLayout(new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        panelCenterMain.add(lblEnterId, g);
        g.gridx = 1;
        g.gridy = 0;
        panelCenterMain.add(txtEnterId, g);
        g.gridx = 0;
        g.gridy = 1;
        panelCenterMain.add(lblSq1, g);
        g.gridx = 1;
        g.gridy = 1;
        panelCenterMain.add(txtQues1, g);
        g.gridx = 0;
        g.gridy = 2;
        panelCenterMain.add(lblAns1, g);
        g.gridx = 1;
        g.gridy = 2;
        panelCenterMain.add(txtAns1, g);
        g.gridx = 0;
        g.gridy = 3;
        panelCenterMain.add(lblSq2, g);
        g.gridx = 1;
        g.gridy = 3;
        panelCenterMain.add(txtQues2, g);
        g.gridx = 0;
        g.gridy = 4;
        panelCenterMain.add(lblAns2, g);
        g.gridx = 1;
        g.gridy = 4;
        panelCenterMain.add(txtAns2, g);
        g.gridx = 0;
        g.gridy = 5;
        panelCenterMain.add(lblPassword,g);
        g.gridx = 1;
        g.gridy = 5;
        panelCenterMain.add(txtPassword,g);
        
        panelCenterNorth = new JPanel(new FlowLayout ());
        logo = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
        panelCenterNorth.add(logo);
        
        panelCenter = new JPanel (new BorderLayout ());
        panelCenter.add(panelCenterNorth, BorderLayout.NORTH);
        
        panelCenter.add(panelCenterMain, BorderLayout.CENTER);
        
        panelCenterSouth = new JPanel (new FlowLayout());
        panelCenterSouth.add(reset);
        panelCenterSouth.add(submit);
        panelCenterSouth.add(Login);
        panelCenterSouth.add(Exit);
        panelCenterSouth.add(btnBack);
        
        
        panelCenter.add(panelCenterSouth, BorderLayout.SOUTH);

    }
}