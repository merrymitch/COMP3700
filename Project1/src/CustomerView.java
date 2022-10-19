import javax.swing.*;
import java.awt.*;

public class CustomerView extends JFrame {

    public JTextField txtCustomerID = new JTextField(30);
    public JTextField txtFirstName = new JTextField(30);
    public JTextField txtLastName = new JTextField(30);
    public JTextField txtAddress = new JTextField(30);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");

    public CustomerView() {

        this.setTitle("Customer View");
        this.setSize(new Dimension(600, 300));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));    // make this window with box layout

        JPanel line1 = new JPanel();
        line1.add(new JLabel("Customer ID"));
        line1.add(txtCustomerID);
        this.getContentPane().add(line1);

        JPanel line2 = new JPanel();
        line2.add(new JLabel("First Name"));
        line2.add(txtFirstName);
        this.getContentPane().add(line2);

        JPanel line3 = new JPanel();
        line3.add(new JLabel("Last Name"));
        line3.add(txtLastName);
        this.getContentPane().add(line3);

        JPanel line4 = new JPanel();
        line4.add(new JLabel("Address"));
        line4.add(txtAddress);
        this.getContentPane().add(line4);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnSave);

        this.getContentPane().add(buttonPanel);

    }

}
