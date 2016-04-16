
package customer;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 *
 * @author Dalvir Sahota
 */
public class MenuCustomer extends JFrame {

    private JMenuBar mbCustomer;
    private JMenu menuUpdate, menuHelp, menuLogout, menuFile,checkOutMenu;
    protected JMenuItem miAccountDetails, miAddBalance, miHelpContents, miAbout, miExit, miLogout,checkOut;
    
    protected JToolBar myToolBar;
    protected JButton tbBtnExit, tbBtnUpdate, tbBtnAddBalance, tbBtnLogout, tbBtnHome;
    
   
    public  JMenuBar  customerMenu() { //this is a method which will create the menubar 
        mbCustomer = new JMenuBar ();
        
        //menu inside the menubar!
        
        menuUpdate = new JMenu ("Update");
        menuHelp = new JMenu ("Help");
        menuLogout = new JMenu ("Log out!");
        menuFile = new JMenu("File");
       
        // creating the menu items
        miLogout = new JMenuItem("Logout");
        miLogout.setMnemonic('L');
        miLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,Event.CTRL_MASK));
        miAccountDetails = new JMenuItem ("Account Details");
        miAccountDetails.setMnemonic('A');
        miAccountDetails.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK));
        miAddBalance = new JMenuItem ("Add Balance");
        miAddBalance.setMnemonic('B');
        miAddBalance.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,Event.CTRL_MASK));
        miAbout = new JMenuItem ("About");
        miHelpContents = new JMenuItem ("Help Contents");
        miHelpContents.setMnemonic('H');
        miHelpContents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK));
        miExit = new JMenuItem("Exit from Application");
        miExit.setMnemonic('E');
        miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK));
        checkOutMenu = new JMenu("Check Out");
        checkOut = new JMenuItem("Check Out");
        checkOut.setMnemonic('C');
        checkOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK));
        checkOutMenu.add(checkOut);
         mbCustomer.add(checkOutMenu);
        //Adding action listener to the Exit button
        
        miExit.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?", 
                    "Exit Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (n == JOptionPane.YES_OPTION)
                    System.exit(0);
           
        }
    });
        
        
        
        menuUpdate.add(miAccountDetails);
        menuUpdate.add(miAddBalance);
        menuLogout.add(miLogout);
        menuHelp.add(miAbout);
        menuHelp.add(miHelpContents);
        
        menuFile.add(miExit);
        
        //adding the menus to the menubar
        mbCustomer.add(menuFile);
        mbCustomer.add(menuUpdate);
        mbCustomer.add(menuHelp);
        mbCustomer.add(menuLogout);
        
        return mbCustomer;
    }
    
    public JToolBar createMyToolBar (){
        myToolBar = new JToolBar ("JFood | Toolbar");
        
        tbBtnExit = new JButton (new ImageIcon (getClass().getResource("resources/exit1.png")));
        tbBtnUpdate = new JButton (new ImageIcon (getClass().getResource("resources/update1.jpg")));
        tbBtnAddBalance = new JButton (new ImageIcon (getClass().getResource("resources/add1.png")));
        tbBtnLogout = new JButton (new ImageIcon (getClass().getResource("resources/logout1.jpg")));
        tbBtnHome = new JButton (new ImageIcon (getClass().getResource("resources/rsz_home.jpg")));
        
        tbBtnExit.addActionListener(new ActionListener() {

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
        
        
        
        
        //Adding buttons to the toolbar
        myToolBar.add(tbBtnExit);
        myToolBar.add(tbBtnUpdate);
        myToolBar.add(tbBtnAddBalance);
        myToolBar.add(tbBtnLogout);
        myToolBar.add(tbBtnHome);
        
        myToolBar.setFloatable(true);
        myToolBar.setOrientation(SwingConstants.VERTICAL);
        
        //Setting tool tip text!
        tbBtnExit.setToolTipText("Exit from application");
        tbBtnUpdate.setToolTipText("Update account details");
        tbBtnAddBalance.setToolTipText("Add Balance to your wallet");
        tbBtnLogout.setToolTipText("Logout");
        tbBtnHome.setToolTipText("Home Page");
        
        return myToolBar;
       
    }
}
