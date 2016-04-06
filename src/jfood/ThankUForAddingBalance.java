    
package jfood;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mazhar
 */
public class ThankUForAddingBalance extends MenuCustomer {
    private JLabel lblThanks, lblCurBalance;
    private JButton btnContinue, btnExit;
    private JPanel panelCenter;
    private JTextField txtCurBalance;
    private String id;
    private JPanel panelCenterTop;
    private JPanel panelCenterBottom;
    private JPanel panelCenterMiddle;
    
    
    public ThankUForAddingBalance (String id){
        this.id = id;
        this.initComponents();
        this.setTitle("Thank You adding balance to your wallet | " + LoginForm.customer.getFirstName() + " " + LoginForm.customer.getLastName());
        this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
        this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
        this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    private void initComponents (){
        
        //Creating the labels and the text field
        lblCurBalance = new JLabel ("Current Balance");
        lblThanks = new JLabel ("\tThank you for adding balance to your wallet "+id
        +". Your account is successfully updated.");
        lblThanks.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        lblThanks.setForeground(Color.BLUE);
        
        txtCurBalance = new JTextField();
        txtCurBalance.setPreferredSize(new Dimension (180, 30));
        txtCurBalance.setEditable(false);
         DataInputStream dis = null;
                try {
                    dis = new DataInputStream(new FileInputStream("balance.txt"));
                    while(dis.available() > 0) {
                        String cid = dis.readUTF();
                    if(id.equals(cid)) {
                    txtCurBalance.setText(dis.readUTF());
                    break;
                    }else {
                    for(int i =0;i<4;i++) {
                    dis.readUTF();
                    }
                    }
                    }
                    dis.close();
                   } catch (FileNotFoundException ex) {
                    Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
            Logger.getLogger(AddBalance.class.getName()).log(Level.SEVERE, null, ex);
        } 
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
                HomeCustomer h2 = new HomeCustomer (id);
                ThankUForAddingBalance.this.dispose();
            }
        });
        
        
        panelCenterTop = new JPanel ();
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
        panelCenterTop.add(logo);
        
        panelCenterBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panelCenterBottom.add(btnContinue);
        panelCenterBottom.add(btnExit);
        
        panelCenterMiddle = new JPanel (new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets= new Insets(10, 5, 10, 5);
        
        g.anchor = GridBagConstraints.CENTER;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 6;
        panelCenterMiddle.add(lblThanks, g);
        
        g.insets = new Insets(10, 220, 10, 5);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 3;
        g.gridy = 1;
        g.gridwidth= 1;
        panelCenterMiddle.add(lblCurBalance, g);
        
        g.insets = new Insets(10, 5, 10, 150);
        g.anchor = GridBagConstraints.LINE_START;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 4;
        g.gridy = 1;
        g.gridwidth=1;
        panelCenterMiddle.add(txtCurBalance, g);
        
        
        
        panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(panelCenterTop, BorderLayout.NORTH);
        panelCenter.add(panelCenterMiddle, BorderLayout.CENTER);
        panelCenter.add(panelCenterBottom, BorderLayout.SOUTH);
        
    }
            
}
