import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RemoteDataAdapter implements DataAccess {
    Gson gson = new Gson();
    Socket s = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    @Override
    public void connect() {
        try {
            s = new Socket("localhost", 5056);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void saveProduct(ProductModel product) {
        RequestModel req = new RequestModel();
        req.code = RequestModel.SAVE_PRODUCT_REQUEST;
        req.body = gson.toJson(product);
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response: " + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server!");
            }
            else
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not save the product!");
                }
                else {
                    System.out.println("Saving the following ProductModel object: ");
                    System.out.println("Product ID = " + product.productID);
                    System.out.println("Product Name = " + product.name);
                    System.out.println("Product Price = " + product.price);
                    System.out.println("Product Quantity = " + product.quantity);
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ProductModel loadProduct(int productID) {
        RequestModel req = new RequestModel();
        req.code = req.LOAD_PRODUCT_REQUEST;
        req.body = String.valueOf(productID);
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response: " + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server!");
                return null;
            }
            else
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a product with that ID!");
                    return null;
                }
                else {
                    ProductModel model = gson.fromJson(res.body, ProductModel.class);
                    System.out.println("Receiving the following ProductModel object: ");
                    System.out.println("ProductID = " + model.productID);
                    System.out.println("Product name = " + model.name);
                    System.out.println("Product price = " + model.price);
                    System.out.println("Product name = " + model.quantity);
                    return model;
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrder(OrderModel order) {
        RequestModel req = new RequestModel();
        req.code = RequestModel.SAVE_ORDER_REQUEST;
        req.body = gson.toJson(order);
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response: " + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server!");
            }
            else
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not save the order!");
                    return;
                }
                else {
                    System.out.println("Saving the following OrderModel object: ");
                    System.out.println("Order ID = " + order.orderID);
                    System.out.println("Order Date = " + order.orderDate);
                    System.out.println("Customer ID = " + order.customerID);
                    System.out.println("Product ID = " + order.productID);
                    System.out.println("Total Cost = " + order.totalCost);
                    System.out.println("Total Quantity = " + order.totalCost);
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public OrderModel loadOrder(int orderID) {
        RequestModel req = new RequestModel();
        req.code = req.LOAD_ORDER_REQUEST;
        req.body = String.valueOf(orderID);
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response:" + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server!");
                return null;
            }
            else
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find an order with that ID!");
                    return null;
                }
                else {
                    OrderModel model = gson.fromJson(res.body, OrderModel.class);
                    System.out.println("Receiving the following OrderModel object: ");
                    System.out.println("Order ID = " + model.orderID);
                    System.out.println("Order Date = " + model.orderDate);
                    System.out.println("Customer ID = " + model.customerID);
                    System.out.println("Product ID = " + model.productID);
                    System.out.println("Total Cost = " + model.totalCost);
                    System.out.println("Total Tax = " + model.totalTax);
                    return model;
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveCustomer(CustomerModel customer) {
        RequestModel req = new RequestModel();
        req.code = RequestModel.SAVE_CUSTOMER_REQUEST;
        req.body = gson.toJson(customer);
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response: " + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server!");
                return;
            }
            else
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not save the customer!");
                }
                else {
                    System.out.println("Saving the following CustomerModel object: ");
                    System.out.println("Customer ID = " + customer.customerID);
                    System.out.println("First Name = " + customer.firstName);
                    System.out.println("Last Name = " + customer.lastName);
                    System.out.println("Address = " + customer.address);
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public CustomerModel loadCustomer(int customerID) {
        RequestModel req = new RequestModel();
        req.code = req.LOAD_CUSTOMER_REQUEST;
        req.body = String.valueOf(customerID);
        String json = gson.toJson(req);
        try {
            dos.writeUTF(json);
            String received = dis.readUTF();
            System.out.println("Server response: " + received);
            ResponseModel res = gson.fromJson(received, ResponseModel.class);
            if (res.code == ResponseModel.UNKNOWN_REQUEST) {
                System.out.println("The request is not recognized by the Server!");
                return null;
            }
            else
                if (res.code == ResponseModel.DATA_NOT_FOUND) {
                    System.out.println("The Server could not find a customer with that ID!");
                    return null;
                }
                else {
                    CustomerModel model = gson.fromJson(res.body, CustomerModel.class);
                    System.out.println("Receiving the following CustomerModel object: ");
                    System.out.println("Customer ID = " + model.customerID);
                    System.out.println("First Name = " + model.firstName);
                    System.out.println("Last Name = " + model.lastName);
                    System.out.println("Address = " + model.address);
                    return model;
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
