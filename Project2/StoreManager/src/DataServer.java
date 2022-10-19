import com.google.gson.Gson;

import java.io.*;
import java.net.*;

public class DataServer
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(5056);
        System.out.println("Starting server program!");
        int nClients = 0;
        while (true)
        {
            Socket s = null;
            try
            {
                s = ss.accept();
                nClients++;
                System.out.println("A new client is connected  " + s + " client number: " + nClients);
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Assigning new thread for this client");
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    Gson gson = new Gson();
    DataAccess dao = new SQLiteDataAdapter();
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        dao.connect();
    }

    @Override
    public void run()
    {
        String received;
        while (true) {
            try {
                received = dis.readUTF();
                System.out.println("Message from client " + received);
                RequestModel req = gson.fromJson(received, RequestModel.class);
                ResponseModel res = new ResponseModel();
                if (req.code == RequestModel.EXIT_REQUEST) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                else
                if (req.code == RequestModel.LOAD_PRODUCT_REQUEST) {
                    int id = Integer.parseInt(req.body);
                    System.out.println("The client is asking for a product with ID: " + id);
                    ProductModel model = dao.loadProduct(id);
                    if (model != null) {
                        res.code = ResponseModel.OK;
                        res.body = gson.toJson(model);
                    }
                    else {
                        res.code = ResponseModel.DATA_NOT_FOUND;
                        res.body = "";
                    }
                }
                else
                if (req.code == RequestModel.SAVE_PRODUCT_REQUEST) {
                    ProductModel product = gson.fromJson(req.body, ProductModel.class);
                    System.out.println("The client is asking to save a product with ID: " + product.productID);
                    dao.saveProduct(product);
                    res.code = ResponseModel.OK;
                    res.body = gson.toJson(product);
                }
                else
                if (req.code == RequestModel.LOAD_ORDER_REQUEST) { // load a note from database
                    int id = Integer.parseInt(req.body);
                    System.out.println("The client is asking for an order with ID: " + id);
                    OrderModel order = dao.loadOrder(id);
                    if (order == null) {
                        res.code = ResponseModel.DATA_NOT_FOUND;
                        res.body = "";
                    }
                    else {
                        res.code = ResponseModel.OK;
                        res.body = gson.toJson(order);
                    }
                }
                else
                if (req.code == RequestModel.SAVE_ORDER_REQUEST) {
                    System.out.println(req.body);
                    OrderModel order = gson.fromJson(req.body, OrderModel.class);
                    System.out.println("The client is asking to save an order with ID: " + order.orderID);
                    dao.saveOrder(order);
                    res.code = ResponseModel.OK;
                    res.body = gson.toJson(order);
                }
                else
                if (req.code == RequestModel.LOAD_CUSTOMER_REQUEST) {
                    int id = Integer.parseInt(req.body);
                    System.out.println("The client is asking for a customer with ID: " + id);
                    CustomerModel customer = dao.loadCustomer(id);
                    if (customer == null) {
                        res.code = ResponseModel.DATA_NOT_FOUND;
                        res.body = "";
                    }
                    else {
                        res.code = ResponseModel.OK;
                        res.body = gson.toJson(customer);
                    }
                }
                else
                if (req.code == RequestModel.SAVE_CUSTOMER_REQUEST) {
                    System.out.println(req.body);
                    CustomerModel customer = gson.fromJson(req.body, CustomerModel.class);
                    System.out.println("The client is asking to save a customer with ID: " + customer.customerID);
                    dao.saveCustomer(customer);
                    res.code = ResponseModel.OK;
                    res.body = gson.toJson(customer);
                }
                else {
                    res.code = ResponseModel.UNKNOWN_REQUEST;
                    res.body = "";
                }
                String json = gson.toJson(res);
                System.out.println("JSON object of ResponseModel: " + json);
                dos.writeUTF(json);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            this.dis.close();
            this.dos.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
