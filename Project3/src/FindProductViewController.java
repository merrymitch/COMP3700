import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class FindProductViewController {
    public JPanel getMainPanel() { return mainPanel; }
    private JPanel mainPanel;
    private JTextField queryTF;
    private JButton searchButton;
    private JTable productTable;
    private JScrollPane scrollPanel;
    private DefaultTableModel tableModel;

    private Client client;

    public FindProductViewController(Client client) {
        this.client = client;
        tableModel = new DefaultTableModel();
        productTable.setModel(tableModel);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = queryTF.getText();
                searchAndDisplayProducts(query);
            }
        });
    }

    private void searchAndDisplayProducts(String query) {
        tableModel.setRowCount(0);
        Gson gson = new Gson();
        String queryString = gson.toJson(query);
        Message message = new Message(Message.FIND_PRODUCT, queryString);
        client.sendMessage(message);
    }

    public void updateProductSearch(ProductListModel productList) {
        String[] columnNames = new String[] {"ID", "Name", "Price", "Quantity"};
        tableModel.setColumnIdentifiers(columnNames);
        int row = productList.getList().size();
        int col = 4;
        String[][] data = new String[row][col];
        for(int i = 0; i < row; i++) {
            data[i][0] = String.valueOf(productList.getList().get(i).getProductID());
            data[i][1] = productList.getList().get(i).getName();
            data[i][2] = String.valueOf(productList.getList().get(i).getPrice());
            data[i][3] = String.valueOf(productList.getList().get(i).getQuantity());
            tableModel.addRow(data[i]);
        }
    }

}
