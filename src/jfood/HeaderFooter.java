
package jfood;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderFooter {
private static JPanel header,footer,westPanel;
private static JLabel head,foot,west;
public static final int HEIGHT = 700;
public static final int WIDTH = 800;

public static JPanel getHeader(JLabel  head) {
    header = new JPanel(new FlowLayout());
    header.add(head);
    return header;
}
public static JPanel getFooter(JLabel foot) {
        footer = new JPanel(new FlowLayout());
        footer.add(foot);
        return footer;
}
public static JPanel getWest(JLabel west) {
    westPanel = new JPanel();
    westPanel.add(west);
return westPanel;

}
}
