import javax.swing.*;
import java.util.ArrayList;

public class OrderListModel {
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public ArrayList<Order> orderList = new ArrayList<>();
}
