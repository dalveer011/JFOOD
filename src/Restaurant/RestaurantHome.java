
package Restaurant;

import Restaurant.DeleteItems;
import Restaurant.AddNewItems;
import java.awt.BorderLayout;
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
import jfood.LogOut;
import loginAndRegistration.LoginForm;

/**
 *
 * @author Dalvir Sahota
 */
public class RestaurantHome  extends RestaurantMenuBar {
JPanel logo,buttons,content,center;
JButton add,delete,update,logout2;
JLabel image,contentBody;
String restId;

public RestaurantHome(final String restId) {
this.restId = restId;
this.initComponents();
this.setTitle("Home Page : Restaurant | " + LoginForm.restaurantOwner.getName());
this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);

this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
this.add(center,BorderLayout.CENTER);
//addign menuBar
this.setJMenuBar(restMenuBar());
//Adding action listener to the menubar!
        addItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddNewItems a = new AddNewItems(restId);
                RestaurantHome.this.dispose();
            }                
            }
        );
        
        deleteItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                DeleteItems a = new DeleteItems(restId);
                RestaurantHome.this.dispose();
            }                
            }
        );
        
        updateItem.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                UpdateItems a = new UpdateItems(restId);
                RestaurantHome.this.dispose();
            }                
            }
        );
        
        logOut.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut a = new LogOut();
                RestaurantHome.this.dispose();
            }                
            }
        );
//Adding Tool Bar
this.add(this.getRestaurantToolBar(),BorderLayout.EAST);
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
this.setVisible(true);

        tbBtnAdd.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                AddNewItems a = new AddNewItems(restId);
                RestaurantHome.this.dispose();
            }                
            }
        );

        tbBtnDelete.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                DeleteItems a = new DeleteItems(restId);
                RestaurantHome.this.dispose();
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
                UpdateItems a = new UpdateItems(restId);
                RestaurantHome.this.dispose();
            }                
            }
        );
        
        tbBtnLogOut.addActionListener
        (
                new ActionListener ()
            {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LogOut a = new LogOut();
                RestaurantHome.this.dispose();
            }                
            }
        );
}

private void initComponents() {
add = new JButton("Add item");
delete = new JButton("Delete Item");
update = new JButton("Update Item Details");
logout2 = new JButton("Log Out");

image = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
contentBody = new JLabel("Restaurant");
contentBody.setFont(new Font("Arial", 1, 46));

logo = new JPanel(new FlowLayout());
logo.add(image);

content = new JPanel();
content.add(contentBody);

buttons  = new JPanel(new FlowLayout());
buttons.add(add);
buttons.add(delete);
buttons.add(update);
buttons.add(logout2);

center = new JPanel(new GridLayout(3, 1));
center.add(logo);
center.add(content);
center.add(buttons);

add.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
       
            AddNewItems addItem = new AddNewItems(restId);
            RestaurantHome.this.dispose();
        
    }
});

update.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
       
             UpdateItems a = new UpdateItems(restId);
            RestaurantHome.this.dispose();
        
    }
});

delete.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      
            DeleteItems a = new DeleteItems(restId);
            RestaurantHome.this.dispose();
        
    }
});

logout2.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
       
            LogOut a = new LogOut();
            RestaurantHome.this.dispose();
        
    }
});

}
   
}
