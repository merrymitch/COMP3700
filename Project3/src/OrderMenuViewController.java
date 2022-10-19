import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderMenuViewController {
    private JPanel mainPanel;
    private JButton makeOrderButton;
    private JTable orderTable;
    private JTextField quantityTF1;
    private JTextField productIDTF1;
    private JButton reviewOrderHistoryButton;
    private JButton reviewAnOrderButton;
    private JButton cancelOrderButton;
    private JTextField orderIDTF;
    private JTextField productIDTF2;
    private JTextField quantityTF2;
    private JScrollPane scrollPanel;
    private JButton saveOrderChangesButton;
    private DefaultTableModel tableModel;

    private Client client;
    private User user;
    public OrderMenuViewController(Client client, User user) {
        this.client = client;
        this.user = user;
        tableModel = new DefaultTableModel();
        orderTable.setModel(tableModel);


        makeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderLine orderLine = new OrderLine();
                orderLine.setUserID(user.getUserID());
                orderLine.setProductID(Integer.parseInt(productIDTF1.getText()));
                orderLine.setQuantity(Double.parseDouble(quantityTF1.getText()));
                Gson gson = new Gson();
                String orderLineString = gson.toJson(orderLine);
                Message message = new Message(Message.MAKE_ORDER, orderLineString);
                client.sendMessage(message);
            }
        });

        reviewOrderHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = user.getUserID();
                searchAndDisplayOrders(id);
            }
        });

        reviewAnOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderLine orderLine = new OrderLine();
                orderLine.setOrderID(Integer.parseInt(orderIDTF.getText()));
                orderLine.setUserID(user.getUserID());
                Gson gson = new Gson();
                String orderLineString = gson.toJson(orderLine);
                Message message = new Message(Message.LOAD_ORDERLINE, orderLineString);
                client.sendMessage(message);
            }
        });

        saveOrderChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderLine orderLine = new OrderLine();
                orderLine.setUserID(user.getUserID());
                orderLine.setOrderID(Integer.parseInt(orderIDTF.getText()));
                orderLine.setProductID(Integer.parseInt(productIDTF2.getText()));
                orderLine.setQuantity(Double.parseDouble(quantityTF2.getText()));
                Gson gson = new Gson();
                String orderLineString = gson.toJson(orderLine);
                Message message = new Message(Message.CHANGE_ORDER, orderLineString);
                client.sendMessage(message);
            }
        });

        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderLine orderLine = new OrderLine();
                orderLine.setUserID(user.getUserID());
                orderLine.setOrderID(Integer.parseInt(orderIDTF.getText()));
                Gson gson = new Gson();
                String orderLineString = gson.toJson(orderLine);
                Message message = new Message(Message.CANCEL_ORDER, orderLineString);
                client.sendMessage(message);
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    public void setUser(User user) { this.user = user; }

    private void searchAndDisplayOrders(int id) {
        tableModel.setRowCount(0);
        Gson gson = new Gson();
        String historyString = gson.toJson(id);
        Message message = new Message(Message.LOAD_ORDER_HISTORY, historyString);
        client.sendMessage(message);
    }

    public void updateOrderSearch(OrderListModel orderList) {
        String[] columnNames = new String[] {"OrderID", "UserID", "Total Cost", "Total Tax", "Date"};
        tableModel.setColumnIdentifiers(columnNames);
        int row = orderList.getOrderList().size();
        int col = 5;
        String[][] data = new String[row][col];
        for(int i = 0; i < row; i++) {
            data[i][0] = String.valueOf(orderList.getOrderList().get(i).getOrderID());
            data[i][1] = String.valueOf(orderList.getOrderList().get(i).getCustomerID());
            data[i][2] = String.valueOf(orderList.getOrderList().get(i).getTotalCost());
            data[i][3] = String.valueOf(orderList.getOrderList().get(i).getTotalTax());
            data[i][4] = orderList.getOrderList().get(i).getDate();
            tableModel.addRow(data[i]);
        }
    }

    public void updateOrderLine(OrderLine orderLine) {
        orderIDTF.setText(String.valueOf(orderLine.getOrderID()));
        productIDTF2.setText(String.valueOf(orderLine.getProductID()));
        quantityTF2.setText(String.valueOf(orderLine.getQuantity()));
    }

}
