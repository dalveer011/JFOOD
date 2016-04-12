
package Restaurant;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class RestaurantMenuBar extends JFrame {
protected JMenu home,updateItems,updateDetails,logout;
protected JMenuItem gotoHome,updateAcDetails,addItem,deleteItem,updateItem,exit,logOut;
private JMenuBar restMenuBar;
private JToolBar restaurantToolBar;
protected JButton tbBtnAdd,tbBtnDelete,tbBtnUpdate,tbBtnLogOut, tbBtnResHome;
private JButton tbBtnExit;

private void initComponents() {
home = new JMenu("Home");
updateItems = new JMenu("Update");
updateDetails = new JMenu("Update Item");
logout = new JMenu("Exit");

gotoHome = new JMenuItem("Go to Home");
gotoHome.setMnemonic('H');
gotoHome.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK));
updateAcDetails = new JMenuItem("Update Account Details");
updateAcDetails.setMnemonic('U');
updateAcDetails.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,Event.CTRL_MASK));
addItem = new JMenuItem("Add Item");
addItem.setMnemonic('A');
addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK));
deleteItem = new JMenuItem("Delete Item");
deleteItem.setMnemonic('D');
deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,Event.CTRL_MASK));
updateItem = new JMenuItem("Update Item details");
updateItem.setMnemonic('I');
updateItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,Event.CTRL_MASK));
exit = new JMenuItem("Exit");
exit.setMnemonic('X');
exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK));
logOut = new JMenuItem("Log Out");
logOut.setMnemonic('L');
logOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,Event.CTRL_MASK));
home.add(gotoHome);
updateItems.add(addItem);
updateItems.add(deleteItem);
//Creating Sub Menu
updateItems.add(updateItem);
updateDetails.add(updateAcDetails);
updateDetails.add(updateItems);

logout.add(logOut);
logout.add(exit);

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

home.addMouseListener(new MouseListener() {
    

    @Override
    public void mouseEntered(MouseEvent e) {
        JMenu item = (JMenu)e.getSource();
        item.setSelected(true);
        item.doClick();
    }

    @Override
    public void mouseExited(MouseEvent e) {
         JMenu item = (JMenu)e.getSource();
        item.setSelected(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
});
}
public  JMenuBar restMenuBar() {
this.initComponents();
restMenuBar = new JMenuBar();
restMenuBar.add(home);
restMenuBar.add(updateDetails);
//restMenuBar.add(updateItems);
restMenuBar.add(logout);

return restMenuBar;
}
public JToolBar getRestaurantToolBar() {
tbBtnAdd = new JButton(new ImageIcon(getClass().getResource("images/Add.png")));
tbBtnDelete = new JButton(new ImageIcon(getClass().getResource("images/Delete.png")));
tbBtnUpdate = new JButton(new ImageIcon(getClass().getResource("images/Update.png")));
tbBtnLogOut = new JButton(new ImageIcon(getClass().getResource("resources/logout.jpg")));
tbBtnResHome = new JButton (new ImageIcon(getClass().getResource("images/home_restaurant.png")));
tbBtnExit = new JButton (new ImageIcon(getClass().getResource("resources/exit2.png")));

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

restaurantToolBar = new JToolBar();
restaurantToolBar.setFloatable(true);
restaurantToolBar.setOrientation(SwingConstants.VERTICAL);
restaurantToolBar.add(tbBtnResHome);
restaurantToolBar.add(tbBtnAdd);
restaurantToolBar.add(tbBtnDelete);
restaurantToolBar.add(tbBtnUpdate);
restaurantToolBar.add(tbBtnLogOut);
restaurantToolBar.add(tbBtnExit);


tbBtnAdd.setToolTipText("Add new Item");
tbBtnDelete.setToolTipText("Delete an Item");
tbBtnUpdate.setToolTipText("Update Item Details");
tbBtnLogOut.setToolTipText("Log Out");
tbBtnResHome.setToolTipText("Restaurant's Home Page");
tbBtnExit.setToolTipText("Exit From Application");


return restaurantToolBar;
}
}
