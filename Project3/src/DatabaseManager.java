import com.google.gson.Gson;

import javax.annotation.processing.Processor;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Random;
import java.time.LocalDate;
import java.lang.*;



public class DatabaseManager {

    private static DatabaseManager databaseManager;
    public static DatabaseManager getInstance() {
        if (databaseManager == null) databaseManager = new DatabaseManager();
        return databaseManager;
    }

    private Connection connection;

    private DatabaseManager() {

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:data/store.db");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Message process(String requestString) {

        Gson gson = new Gson();
        Message message = gson.fromJson(requestString, Message.class);

        switch (message.getId()) {
            case Message.LOAD_PRODUCT: {
                Product product = loadProduct(Integer.parseInt(message.getContent()));
                Message replyMessage = new Message(Message.LOAD_PRODUCT_REPLY, gson.toJson(product));
                return replyMessage;
            }

            case Message.SAVE_PRODUCT: {
                Product product = gson.fromJson(message.getContent(), Product.class);
                boolean result = saveProduct(product);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Product saved");
                    return new Message(Message.SUCCESS, "Product saved");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot save the product");
                    return new Message(Message.FAIL, "Cannot save the product");
                }
            }

            case Message.LOAD_CUSTOMER: {
                Customer customer = loadCustomer(Integer.parseInt(message.getContent()));
                Message replyMessage = new Message(Message.LOAD_CUSTOMER_REPLY, gson.toJson(customer));
                return replyMessage;
            }

            case Message.SAVE_CUSTOMER: {
                Customer customer = gson.fromJson(message.getContent(), Customer.class);
                boolean result = saveCustomer(customer);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Customer saved");
                    return new Message(Message.SUCCESS, "Customer saved");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot save the customer");
                    return new Message(Message.FAIL, "Cannot save the customer");
                }
            }
            case Message.LOAD_ORDER: {
                Order order = loadOrder(Integer.parseInt(message.getContent()));
                Message replyMessage = new Message(Message.LOAD_ORDER_REPLY, gson.toJson(order));
                return replyMessage;
            }
            case Message.SAVE_ORDER: {
                Order order = gson.fromJson(message.getContent(), Order.class);
                boolean result = saveOrder(order);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Order saved");
                    return new Message(Message.SUCCESS, "Order saved");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot save the order");
                    return new Message(Message.FAIL, "Cannot save the order");
                }
            }
            case Message.SAVE_NEW_USER: {
                User user = gson.fromJson(message.getContent(), User.class);
                boolean result = saveNewUser(user);
                if (result) {
                    JOptionPane.showMessageDialog(null, "New user saved");
                    return new Message(Message.SUCCESS, "New user saved");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot save the new user");
                    return new Message(Message.FAIL, "Cannot save the new user");
                }
            }
            case Message.LOGIN: {
                User user = gson.fromJson(message.getContent(), User.class);
                User user1 = loginUser(user);
                if (user1 != null) return new Message(Message.LOGIN_REPLY, gson.toJson(user1));
                else {
                    JOptionPane.showMessageDialog(null, "Invalid login");
                    return new Message(Message.FAIL, "Invalid login");
                }
            }
            case Message.SAVE_USER: {
                User user = gson.fromJson(message.getContent(), User.class);
                boolean result = saveUser(user);
                if (result) {
                    JOptionPane.showMessageDialog(null, "User information saved");
                    return new Message(Message.SUCCESS, "User Information saved");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot save the user info");
                    return new Message(Message.FAIL, "Cannot save the user info");
                }
            }
            case Message.SAVE_PASSWORD: {
                User user = gson.fromJson(message.getContent(), User.class);
                boolean result = savePassword(user);
                if (result) {
                    JOptionPane.showMessageDialog(null, "User Password saved");
                    return new Message(Message.SUCCESS, "User Password saved");
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot save the user password");
                    return new Message(Message.FAIL, "Cannot save the user password");
                }
            }
            case Message.FIND_PRODUCT: {
                String query = gson.fromJson(message.getContent(), String.class);
                ProductListModel productList = searchProduct(query);
                Message replyMessage = new Message(Message.FIND_PRODUCT_REPLY, gson.toJson(productList));
                return replyMessage;
            }
            case Message.MAKE_ORDER: {
                OrderLine orderLine = gson.fromJson(message.getContent(), OrderLine.class);
                OrderLine result = makeOrder(orderLine);
                if (result != null) {
                    JOptionPane.showMessageDialog(null, "Order made");
                    return new Message(Message.SUCCESS, "Order made");
                }
                else {
                    return new Message(Message.FAIL, "Cannot make order");
                }
            }
            case Message.LOAD_ORDER_HISTORY: {
                int id = gson.fromJson(message.getContent(), int.class);
                OrderListModel orderList = searchOrder(id);
                Message replyMessage = new Message(Message.LOAD_ORDER_HISTORY_REPLY, gson.toJson(orderList));
                return replyMessage;

            }
            case Message.LOAD_ORDERLINE: {
                OrderLine orderLine = gson.fromJson(message.getContent(), OrderLine.class);
                OrderLine result = loadOrderLine(orderLine);
                Message replyMessage = new Message(Message.LOAD_ORDERLINE_REPLY, gson.toJson(result));
                return replyMessage;
            }
            case Message.CHANGE_ORDER: {
                OrderLine orderLine = gson.fromJson(message.getContent(), OrderLine.class);
                boolean result = changeOrder(orderLine);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Order changed");
                    return new Message(Message.SUCCESS, "Order changed");
                }
                else return new Message(Message.FAIL, "Cannot changed order");
            }
            case Message.CANCEL_ORDER: {
                OrderLine orderLine = gson.fromJson(message.getContent(), OrderLine.class);
                boolean result = cancelOrder(orderLine);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Order cancelled");
                    return new Message(Message.SUCCESS, "Order cancelled");
                }
                else return new Message(Message.FAIL, "Cannot cancelled order");
            }
            default:
                return new Message(Message.FAIL, "Cannot process the message");
        }
    }

    public Product loadProduct(int id) {
        try {
            String query = "SELECT * FROM Products WHERE ProductID = " + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Product product = new Product();
                product.setProductID(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setQuantity(resultSet.getDouble(4));
                resultSet.close();
                statement.close();

                return product;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveProduct(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = ?");
            statement.setInt(1, product.getProductID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // this product exists, update its fields
                statement = connection.prepareStatement("UPDATE Products SET Name = ?, Price = ?, Quantity = ? WHERE ProductID = ?");
                statement.setString(1, product.getName());
                statement.setDouble(2, product.getPrice());
                statement.setDouble(3, product.getQuantity());
                statement.setInt(4, product.getProductID());
            }
            else { // this product does not exist, use insert into
                statement = connection.prepareStatement("INSERT INTO Products VALUES (?, ?, ?, ?)");
                statement.setString(2, product.getName());
                statement.setDouble(3, product.getPrice());
                statement.setDouble(4, product.getQuantity());
                statement.setInt(1, product.getProductID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;        // save successfully

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false; // cannot save!
        }
    }

    public Customer loadCustomer(int id) {
        try {
            String query = "SELECT * FROM Customer WHERE CustomerID = " + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setPhoneNumber(resultSet.getString(4));
                customer.setAddress(resultSet.getString(5));
                resultSet.close();
                statement.close();

                return customer;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveCustomer(Customer customer) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer WHERE CustomerID = ?");
            statement.setInt(1, customer.getCustomerID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                statement = connection.prepareStatement("UPDATE Customer SET FirstName = ?, LastName = ?, PhoneNumber = ?, Address = ?  WHERE CustomerID = ?");
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getPhoneNumber());
                statement.setString(4, customer.getAddress());
                statement.setInt(5, customer.getCustomerID());
            } else {
                statement = connection.prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?, ?)");
                statement.setString(2, customer.getFirstName());
                statement.setString(3, customer.getLastName());
                statement.setString(4, customer.getPhoneNumber());
                statement.setString(5, customer.getAddress());
                statement.setInt(1, customer.getCustomerID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }

    public Order loadOrder(int id) {
        try {
            String query = "SELECT * FROM 'Order' WHERE OrderID = " + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Order order = new Order();
                order.setOrderID(resultSet.getInt(1));
                order.setCustomerID(resultSet.getInt(2));
                order.setTotalCost(resultSet.getDouble(3));
                order.setTotalTax(resultSet.getDouble(4));
                order.setDate(resultSet.getString(5));
                resultSet.close();
                statement.close();

                return order;
            }

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }


    public boolean saveOrder(Order order) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM 'Order' WHERE OrderID = ?");
            statement.setInt(1, order.getOrderID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                statement = connection.prepareStatement("UPDATE 'Order' SET CustomerID = ?, TotalCost = ?, TotalTax = ?, Date = ? WHERE OrderID = ?");
                statement.setInt(1, order.getCustomerID());
                statement.setDouble(2, order.getTotalCost());
                statement.setDouble(3, order.getTotalTax());
                statement.setString(4, order.getDate());
                statement.setInt(5, order.getOrderID());
            } else {
                statement = connection.prepareStatement("INSERT INTO 'Order' VALUES (?, ?, ?, ?, ?)");
                statement.setInt(2, order.getCustomerID());
                statement.setDouble(3, order.getTotalCost());
                statement.setDouble(4, order.getTotalTax());
                statement.setString(5, order.getDate());
                statement.setInt(1, order.getOrderID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }


    public boolean saveNewUser(User user) {
        try {
            Random random = new Random();
            Statement statement = connection.createStatement();
            int upperbound = 1000;
            user.setUserID(random.nextInt(upperbound));
            ResultSet resultSet;
            String query = "SELECT * FROM 'Users' WHERE UserID = " + user.getUserID();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                user.setUserID(random.nextInt(upperbound));
                resultSet = statement.executeQuery(query);
            }

            resultSet = statement.executeQuery("SELECT * FROM 'Users' WHERE UserName = \"" + user.getUsername() + "\"");
            if(resultSet.next()) {
                JOptionPane.showMessageDialog(null, "This username is already taken!");
                throw new Exception("This username is already taken!");
            }

            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO 'Users' VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement1.setString(2, user.getUsername());
            statement1.setString(3, user.getPassword());
            statement1.setString(4, user.getFullName());
            statement1.setString(5, user.getAddress());
            statement1.setString(6, user.getPayment());
            statement1.setString(7, user.getPhoneNumber());
            statement1.setBoolean(8, user.isManager());
            statement1.setInt(1, user.getUserID());
            statement1.execute();
            resultSet.close();
            statement.close();
            statement1.close();
            return true;
        } catch (Exception e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(User user) {
        try {
            String query = "SELECT * FROM 'Users' WHERE UserName = \"" + user.getUsername() + "\"";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                if (user.getPassword().equals(resultSet.getString(3))) {
                    user.setUserID(resultSet.getInt(1));
                    user.setFullName(resultSet.getString(4));
                    user.setAddress(resultSet.getString(5));
                    user.setPayment(resultSet.getString(6));
                    user.setPhoneNumber(resultSet.getString(7));
                    user.setIsManager(resultSet.getBoolean(8));
                    resultSet.close();
                    statement.close();
                    return user;
                }
            }
        } catch (Exception e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM 'Users' WHERE UserID = " + user.getUserID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                statement = connection.prepareStatement("UPDATE 'Users' SET FullName = ?, Address = ?, Payment = ?, PhoneNumber = ? WHERE UserID = ?");
                statement.setString(1, user.getFullName());
                statement.setString(2, user.getAddress());
                statement.setString(3, user.getPayment());
                statement.setString(4, user.getPhoneNumber());
                statement.setInt(5, user.getUserID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;
        } catch (Exception e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }
    public boolean savePassword(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM 'Users' WHERE UserID = ?");
            statement.setInt(1, user.getUserID());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                statement = connection.prepareStatement("UPDATE 'Users' SET Password = ? WHERE UserID = ?");
                statement.setString(1, user.getPassword());
                statement.setInt(2, user.getUserID());
            }
            statement.execute();
            resultSet.close();
            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
            return false;
        }
    }

    public ProductListModel searchProduct(String keyword) {
        Product product = null;
        ProductListModel listModel = new ProductListModel();
        try {
            Statement stmt = connection.createStatement();

            String sql = "SELECT * FROM Products WHERE Name LIKE \'%" +  keyword + "%\'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                product = new Product();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getDouble(3));
                product.setQuantity(rs.getDouble(4));

                listModel.list.add(product); // make a new NoteModel and add
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listModel;
    }

    public OrderLine makeOrder(OrderLine orderLine) {
        try {
            String query = "SELECT * FROM Products WHERE ProductID = " + orderLine.getProductID();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                double productQuantity = resultSet.getDouble(4);
                if (productQuantity >= orderLine.getQuantity()) {
                    productQuantity = productQuantity - orderLine.getQuantity();
                    double cost = resultSet.getDouble(3);
                    cost = cost * orderLine.getQuantity();
                    double totalTax = cost * 0.09;
                    cost = cost + totalTax;
                    totalTax = Math.round(totalTax*100.0)/100.0;
                    cost = Math.round(cost*100.0)/100.0;
                    orderLine.setCost(cost);
                    Random random = new Random();
                    int upperbound = 1000;
                    orderLine.setOrderID(random.nextInt(upperbound));
                    query = "SELECT * FROM 'OrderLine' WHERE OrderID = " + orderLine.getOrderID();
                    resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        orderLine.setOrderID(random.nextInt(upperbound));
                        resultSet = statement.executeQuery(query);
                    }
                    PreparedStatement statement1 = connection.prepareStatement("INSERT INTO 'OrderLine' VALUES (?, ?, ?, ?)");
                    statement1.setInt(2, orderLine.getProductID());
                    statement1.setDouble(3, orderLine.getQuantity());
                    statement1.setDouble(4, orderLine.getCost());
                    statement1.setInt(1, orderLine.getOrderID());
                    statement1.execute();
                    PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = " + orderLine.getProductID());
                    ResultSet resultSet1 = statement2.executeQuery();
                    if (resultSet1.next()) {
                        statement2 = connection.prepareStatement("UPDATE Products SET Quantity = ? WHERE ProductID = " + orderLine.getProductID());
                        statement2.setDouble(1, productQuantity);
                    }
                    statement2.execute();
                    LocalDate localDate = LocalDate.now();
                    String date = String.valueOf(localDate);
                    PreparedStatement statement3 = connection.prepareStatement("INSERT INTO 'Order' VALUES (?, ?, ?, ?, ?)");
                    statement3.setInt(2, orderLine.getUserID());
                    statement3.setDouble(3, cost);
                    statement3.setDouble(4, totalTax);
                    statement3.setString(5, date);
                    statement3.setInt(1, orderLine.getOrderID());
                    statement3.execute();
                    resultSet.close();
                    resultSet1.close();
                    statement.close();
                    statement1.close();
                    statement2.close();
                    statement3.close();
                    return orderLine;
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity is too large, not enough product in stock");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No product with this ID");
            }
        } catch (SQLException e){
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public OrderListModel searchOrder(int id) {
        Order order = null;
        OrderListModel listModel = new OrderListModel();
        try {
            Statement stmt = connection.createStatement();

            String sql = "SELECT * FROM 'Order' WHERE CustomerID LIKE '%" +  id + "%'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                order = new Order();
                order.setOrderID(rs.getInt(1));
                order.setCustomerID(rs.getInt(2));
                order.setTotalCost(rs.getDouble(3));
                order.setTotalTax(rs.getDouble(4));
                order.setDate(rs.getString(5));

                listModel.orderList.add(order);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listModel;
    }

    public OrderLine loadOrderLine(OrderLine orderLine) {
        try {
            String query = "SELECT * FROM 'Order' WHERE OrderID = " + orderLine.getOrderID();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt(2);
                rs.close();
                stmt.close();
                if (id == orderLine.getUserID()) {
                    query = "SELECT * FROM 'OrderLine' WHERE OrderID = " + orderLine.getOrderID();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if(resultSet.next()) {
                        OrderLine order = new OrderLine();
                        order.setUserID(orderLine.getUserID());
                        order.setOrderID(resultSet.getInt(1));
                        order.setProductID(resultSet.getInt(2));
                        order.setQuantity(resultSet.getDouble(3));
                        order.setCost(resultSet.getDouble(4));
                        resultSet.close();
                        statement.close();
                        return order;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "You do not have an order with this ID");
            }
        } catch (SQLException e){
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeOrder(OrderLine orderLine) {
        try {
            String query = "SELECT * FROM 'Order' WHERE OrderID = " + orderLine.getOrderID();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt(2);
                rs.close();
                stmt.close();
                if (id == orderLine.getUserID()) {
                    query = "SELECT * FROM Products WHERE ProductID = " + orderLine.getProductID();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        double productQuantity = resultSet.getDouble(4);
                        if (productQuantity >= orderLine.getQuantity()) {
                            productQuantity = productQuantity - orderLine.getQuantity();
                            double cost = resultSet.getDouble(3);
                            cost = cost * orderLine.getQuantity();
                            double totalTax = cost * 0.09;
                            cost = cost + totalTax;
                            totalTax = Math.round(totalTax * 100.0) / 100.0;
                            cost = Math.round(cost * 100.0) / 100.0;
                            orderLine.setCost(cost);
                            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM OrderLine WHERE OrderID = " + orderLine.getOrderID());
                            ResultSet resultSet1 = statement1.executeQuery();
                            if (resultSet1.next()) {
                                statement1 = connection.prepareStatement("UPDATE OrderLine SET ProductID = ?, Quantity = ?, Cost = ? WHERE OrderID = " + orderLine.getOrderID());
                                statement1.setInt(1, orderLine.getProductID());
                                statement1.setDouble(2, orderLine.getQuantity());
                                statement1.setDouble(3, orderLine.getCost());
                                statement1.execute();
                            }
                            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = " + orderLine.getProductID());
                            ResultSet resultSet2 = statement2.executeQuery();
                            if (resultSet2.next()) {
                                statement2 = connection.prepareStatement("UPDATE Products SET Quantity = ? WHERE ProductID = " + orderLine.getProductID());
                                statement2.setDouble(1, productQuantity);
                            }
                            statement2.execute();
                            LocalDate localDate = LocalDate.now();
                            String date = String.valueOf(localDate);
                            PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM OrderLine WHERE OrderID = " + orderLine.getOrderID());
                            ResultSet resultSet3 = statement3.executeQuery();
                            if (resultSet3.next()) {
                                statement3 = connection.prepareStatement("UPDATE 'Order' SET TotalCost = ?, TotalTax = ?, Date = ? WHERE OrderID = " + orderLine.getOrderID());
                                statement3.setDouble(1, cost);
                                statement3.setDouble(2, totalTax);
                                statement3.setString(3, date);
                                statement3.execute();
                            }
                            resultSet.close();
                            resultSet1.close();
                            resultSet2.close();
                            resultSet3.close();
                            statement.close();
                            statement1.close();
                            statement2.close();
                            statement3.close();
                            return true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Quantity is too large, not enough product in stock");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No product with this ID");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You do not have an order with this ID");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You do not have an order with this ID");
            }
        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelOrder(OrderLine orderLine) {
        try {
            String query = "SELECT * FROM 'Order' WHERE OrderID = " + orderLine.getOrderID();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt(2);
                rs.close();
                stmt.close();
                if (id == orderLine.getUserID()) {
                    query = "SELECT * FROM 'OrderLine' WHERE OrderID = " + orderLine.getOrderID();
                    Statement stmt1 = connection.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(query);
                    if (rs1.next()) {
                        orderLine.setProductID(rs1.getInt(2));
                        orderLine.setQuantity(rs1.getDouble(3));
                    }
                    rs1.close();
                    stmt1.close();
                    query = "SELECT * FROM 'Products' WHERE ProductID = " + orderLine.getProductID();
                    Statement stmt2 = connection.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query);
                    double quantity = 0;
                    if (rs2.next()) {
                        quantity = rs2.getDouble(4);
                        quantity = quantity + orderLine.getQuantity();
                    }
                    rs2.close();
                    stmt2.close();
                    PreparedStatement stmt3 = connection.prepareStatement("SELECT * FROM Products WHERE ProductID = " + orderLine.getProductID());
                    ResultSet rs3 = stmt3.executeQuery();
                    if (rs3.next()) {
                        stmt3 = connection.prepareStatement("UPDATE 'Products' SET Quantity = ? WHERE ProductID = " + orderLine.getProductID());
                        stmt3.setDouble(1, quantity);
                        stmt3.executeUpdate();
                    }
                    rs3.close();
                    stmt3.close();
                    PreparedStatement stmt4 = connection.prepareStatement("DELETE FROM OrderLine WHERE OrderID = " + orderLine.getOrderID() + ";");
                    stmt4.executeUpdate();
                    PreparedStatement stmt5 = connection.prepareStatement("DELETE FROM 'Order' WHERE OrderID = " + orderLine.getOrderID() + ";");
                    stmt5.executeUpdate();
                    stmt4.close();
                    stmt5.close();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "You do not have an order with this ID");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You do not have an order with this ID");
            }
        } catch (SQLException e) {
            System.out.println("Database access error!");
            e.printStackTrace();
        }
        return false;
    }
}
