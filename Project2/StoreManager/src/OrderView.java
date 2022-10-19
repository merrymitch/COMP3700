/*
 * Class for the Order Menu User Interface
 */
import javax.swing.*;
import java.awt.*;

public class OrderView extends JFrame {

    public JTextField txtOrderID = new JTextField(30);
    public JTextField txtOrderDate = new JTextField(30);
    public JTextField txtCustID = new JTextField(30);
    public JTextField txtProductID = new JTextField(30);
    public JTextField txtTotalCost = new JTextField(30);
    public JTextField txtTotalTax = new JTextField(30);

    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");

    public OrderView() {

        this.setTitle("Order View");
        this.setSize(new Dimension(600, 300));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));    // make this window with box layout

        JPanel line1 = new JPanel();
        line1.add(new JLabel("Order ID"));
        line1.add(txtOrderID);
        this.getContentPane().add(line1);

        JPanel line2 = new JPanel();
        line2.add(new JLabel("Order Date"));
        line2.add(txtOrderDate);
        this.getContentPane().add(line2);

        JPanel line3 = new JPanel();
        line3.add(new JLabel("Customer ID"));
        line3.add(txtCustID);
        this.getContentPane().add(line3);

        JPanel line4 = new JPanel();
        line4.add(new JLabel("Product ID"));
        line4.add(txtProductID);
        this.getContentPane().add(line4);

        JPanel line5 = new JPanel();
        line5.add(new JLabel("TotalCost"));
        line5.add(txtTotalCost);
        this.getContentPane().add(line5);

        JPanel line6 = new JPanel();
        line6.add(new JLabel("TotalTax"));
        line6.add(txtTotalTax);
        this.getContentPane().add(line6);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLoad);
        buttonPanel.add(btnSave);

        this.getContentPane().add(buttonPanel);
    }
}
