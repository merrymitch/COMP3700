/*
 * Controller class for Order.
 * Define actions for save and load.
 */

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
            orderModel.customerID = Integer.parseInt(myView.txtCustID.getText());
            orderModel.productID = Integer.parseInt(myView.txtProductID.getText());
            orderModel.totalCost = Double.parseDouble(myView.txtTotalCost.getText());
            orderModel.totalTax = Double.parseDouble(myView.txtTotalTax.getText());
            myDAO.saveOrder(orderModel);
            JOptionPane.showMessageDialog(null, "Order saved successfully!");
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid format for numbers!");
            ex.printStackTrace();
        }
    }

    private void loadOrderAndDisplay() {
        try {
            int orderID = Integer.parseInt(myView.txtOrderID.getText());
            OrderModel orderModel = myDAO.loadOrder(orderID);
            if (orderModel == null)
                JOptionPane.showMessageDialog(null, "No existing order with this ID " + orderID);
            else {
                myView.txtOrderDate.setText(orderModel.orderDate);
                myView.txtCustID.setText(String.valueOf(orderModel.customerID));
                myView.txtProductID.setText(String.valueOf(orderModel.productID));
                myView.txtTotalCost.setText(String.valueOf(orderModel.totalCost));
                myView.txtTotalTax.setText(String.valueOf(orderModel.totalTax));
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid format for Order ID");
            ex.printStackTrace();
        }
    }

}
