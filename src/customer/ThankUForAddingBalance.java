    
package customer;
import customer.MenuCustomer;
import customer.HomeCustomer;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DBConnection;
import jfood.HeaderFooter;
import loginAndRegistration.LoginForm;

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
    
    private DBConnection db;
    private ResultSet rs;
    
    private String queryWallet = "SELECT LOGINID, CURRENTBAL FROM CUSTOMER_WALLET_JFOOD";
    
    
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
        
        
        db = new DBConnection();
        rs = db.getInfo(queryWallet);
        
        try {
            while (rs.next()){
                if (id.equals(rs.getString(1))){
                    txtCurBalance.setText(rs.getString(2));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "SQL Exception | Thank You for adding Balance", JOptionPane.ERROR_MESSAGE);
        }
        
        btnContinue = new JButton ("Continue");
        btnExit = new JButton ("Exit");
        //ActionListener for exit button!
        btnExit.addActionListener
        (
            new ActionListener () {

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
