import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderController implements ActionListener {

    OrderView myView;
    DataAccess myDAO;

    public OrderController(OrderView view, DataAccess dao) {
        myView = view;
        myDAO = dao;
        myView.btnLoad.addActionListener(this);
        myView.btnSave.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myView.btnLoad) {      // button Load is clicked
            loadOrderAndDisplay();
        }

        if (e.getSource() == myView.btnSave) {      // button Save is clicked
            saveOrder();
        }

    }

    private void saveOrder() {
        OrderModel orderModel = new OrderModel();

        try {
            int orderID = Integer.parseInt(myView.txtOrderID.getText());
            orderModel.orderID = orderID;
            orderModel.orderDate = myView.txtOrderDate.getText();
            orderModel.customerName = myView.txtCustomerName.getText();
            orderModel.totalCost = Double.parseDouble(myView.txtTotalCost.getText());
            orderModel.totalTax = Double.parseDouble(myView.txtTotalTax.getText());

            myDAO.saveOrder(orderModel);
            JOptionPane.showMessageDialog(null, "Order saved successfully!");


        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid format for OrderID");
            ex.printStackTrace();
        }    }

    private void loadOrderAndDisplay() {
        try {
            int orderID = Integer.parseInt(myView.txtOrderID.getText());
            OrderModel orderModel = myDAO.loadOrder(orderID);
            myView.txtOrderDate.setText(orderModel.orderDate);
            myView.txtCustomerName.setText(orderModel.customerName);
            myView.txtTotalCost.setText(String.valueOf(orderModel.totalCost));
            myView.txtTotalTax.setText(String.valueOf(orderModel.totalTax));

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid format for OrderID");
            ex.printStackTrace();
        }
    }
}
