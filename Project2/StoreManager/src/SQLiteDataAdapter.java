import java.sql.*;

public class SQLiteDataAdapter implements DataAccess {
    Connection conn = null;

    @Override
    public void connect() {
        try {
            String url = "jdbc:sqlite:store.db";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            if (conn == null) {
                System.out.println("Cannot make the connection!");
            }
            else {
                System.out.println("The connection object is " + conn);
            }
            System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveProduct(ProductModel product) {
        try {
            Statement stmt = conn.createStatement();
            if (loadProduct(product.getProductID()) == null) {
                stmt.execute("INSERT INTO Product(productID, name, price, quantity) VALUES ("
                        + product.getProductID() + ","
                        + '\'' + product.getName() + '\'' + ","
                        + product.getPrice() + ","
                        + product.getQuantity() + ")"
                );
            }
            else {
                stmt.executeUpdate("UPDATE Product SET "
                        + "productID = " + product.getProductID() + ","
                        + "name = " + '\'' + product.getName() + '\'' + ","
                        + "price = " + product.getPrice() + ","
                        + "quantity = " + product.getQuantity() +
                        " WHERE productID = " + product.getProductID()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        ProductModel product = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE ProductID = " + productID);
            if (rs.next()) {
                product = new ProductModel();
                product.setProductID(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getDouble(3));
                product.setQuantity(rs.getDouble(4));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }

    @Override
    public void saveCustomer(CustomerModel customer) {
        try {
            Statement stmt = conn.createStatement();
            if (loadCustomer(customer.getCustomerID()) == null) {
                stmt.execute("INSERT INTO 'Customer'(CustomerID, FirstName, LastName, Address) VALUES ("
                        + customer.getCustomerID() + ","
                        + '\'' + customer.getFirstName() + '\'' + ","
                        + '\'' + customer.getLastName() + '\'' + ","
                        + '\'' + customer.getAddress() + '\'' + ")"
                );
            }
            else {
                stmt.executeUpdate("UPDATE 'Customer' SET "
                        + "CustomerID = " + customer.getCustomerID() + ","
                        + "FirstName = " + '\'' + customer.getFirstName() + '\'' + ","
                        + "LastName = " + '\'' + customer.getLastName() + '\'' + ","
                        + "Address = " + '\'' + customer.getAddress() + '\'' +
                        " WHERE CustomerID = " + customer.getCustomerID()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Customer WHERE CustomerID = " + customerID);
            if (rs.next()) {
                customer = new CustomerModel();
                customer.setCustomerID(rs.getInt(1));
                customer.setFirstName(rs.getString(2));
                customer.setLastName(rs.getString(3));
                customer.setAddress(rs.getString(4));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    @Override
    public void saveOrder(OrderModel order) {
        try {
            Statement stmt = conn.createStatement();
            if (loadOrder(order.getOrderID()) == null) {
                stmt.execute("INSERT INTO 'Orders'(OrderID, OrderDate, CustomerID, ProductID, TotalCost, TotalTax) VALUES ("
                        + order.getOrderID() + ","
                        + '\'' + order.getOrderDate() + '\'' + ","
                        + order.getCustomerID() + ","
                        + order.getProductID() + ","
                        + order.getTotalCost() + ","
                        + order.getTotalTax() + ")"
                );
            }
            else {
                stmt.executeUpdate("UPDATE 'Orders' SET "
                        + "OrderID = " + order.getOrderID() + ","
                        + "OrderDate = " + '\'' + order.getOrderDate() + '\'' + ","
                        + "CustomerID = " + order.getCustomerID() + ","
                        + "ProductID = " + order.getProductID() + ","
                        + "TotalCost = " + order.getTotalCost() + ","
                        + "TotalTax = " + order.getTotalTax() +
                        " WHERE OrderID = " + order.getOrderID()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public OrderModel loadOrder(int orderId) {
        OrderModel order = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM 'Orders' WHERE OrderID = " + orderId);
            if (rs.next()) {
                order = new OrderModel();
                order.setOrderID(rs.getInt(1));
                order.setOrderDate(rs.getString(2));
                order.setCustomerID(rs.getInt(3));
                order.setProductID(rs.getInt(4));
                order.setTotalCost(rs.getDouble(5));
                order.setTotalTax(rs.getDouble(6));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }

}
