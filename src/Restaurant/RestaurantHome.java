
package Restaurant;

import database.DBConnection;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
JButton add,delete,update,logout2,recentOrders;
JLabel image,contentBody;
String restId;

public RestaurantHome(final String restId) {
this.restId = restId;
this.initComponents();
this.setTitle("Home Page : Restaurant | " + LoginForm.restaurantOwner.getName());
this.setSize(HeaderFooter.WIDTH, HeaderFooter.HEIGHT);
this.add(this.getRestaurantToolBar(),BorderLayout.EAST);
this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
this.add(HeaderFooter.getHeader(new JLabel(new ImageIcon(getClass().getResource("resources/logo2.png")))), BorderLayout.NORTH);
this.add(HeaderFooter.getFooter(new JLabel(new ImageIcon(getClass().getResource("images/copyright.png")))), BorderLayout.SOUTH);
this.add(center,BorderLayout.CENTER);
//addign menuBar
this.setJMenuBar(restMenuBar());
this.setVisible(true);
DBConnection db = new DBConnection();
ResultSet rs = db.getInfo("select count(status) from orders_jfood where status = 0 and restaurantid = '"+restId+"' group by restaurantid");
    try {
        if(rs.next()) {
            int count = rs.getInt(1);
            if(count > 0) 
            {
                contentBody.setText("You have "+count+" recent orders");
            }
            else
            {
               contentBody.setText("You have no recent order");
            }
        }   
    } catch (SQLException ex) {
        System.out.println("Error in restaurant home constructor"+ex.getMessage());
    }
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
        
        updateAcDetails.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateRestaurantDetails a = new UpdateRestaurantDetails(restId);
                RestaurantHome.this.dispose();
            }
        });
        
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
recentOrders = new JButton("Recent Orders");
image = new JLabel(new ImageIcon(getClass().getResource("images/logo.png")));
contentBody = new JLabel();


logo = new JPanel(new FlowLayout());
logo.add(image);


content = new JPanel();
content.add(contentBody,BorderLayout.CENTER);


buttons  = new JPanel(new FlowLayout());
buttons.add(add);
buttons.add(delete);
buttons.add(update);
buttons.add(logout2);
buttons.add(recentOrders);
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
recentOrders.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
    new RecentOrder(restId);
    RestaurantHome.this.dispose();
    }
});

}
   
}
