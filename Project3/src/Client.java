import com.google.gson.Gson;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;

public class Client {
    private JTextArea messageTextArea;
    private JPanel mainPanel;
    private JButton loginButton;

    private SecretKey secretKey;

    private byte[] initializationVector;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private Gson gson;

    private Worker worker;

    private User customer;

    private ProductViewController productViewController;
    private CustomerViewController customerViewController;
    private OrderViewController orderViewController;
    private LoginViewController loginViewController;
    private NewCustViewController newCustViewController;
    private CustMenuViewController custMenuViewController;
    private ChangeInfoViewController changeInfoViewController;
    private ChangePasswordViewController changePasswordViewController;
    private PersonalInfoViewController personalInfoViewController;
    private OrderMenuViewController orderMenuViewController;
    private FindProductViewController findProductViewController;


    public Client() {
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 12002);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // send the secret key
            secretKey = KeyService.createAESKey();

            String keyString = KeyService.convertSecretKeyToString(secretKey);

            dataOutputStream.writeUTF(keyString);

            // send the initialization vector

            initializationVector = KeyService.createInitializationVector();

            String vectorString = Base64.getEncoder().encodeToString(initializationVector);

            dataOutputStream.writeUTF(vectorString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        gson = new Gson();

        worker = new Worker();
        customer = new User();
        Thread workerThread = new Thread(worker);
        workerThread.start();

        this.productViewController = new ProductViewController(this);
        this.customerViewController = new CustomerViewController(this);
        this.orderViewController = new OrderViewController(this);
        this.loginViewController = new LoginViewController(this);
        this.newCustViewController = new NewCustViewController(this);
        this.custMenuViewController = new CustMenuViewController(this, customer);
        this.changeInfoViewController = new ChangeInfoViewController(this, customer);
        this.changePasswordViewController = new ChangePasswordViewController(this, customer);
        this.personalInfoViewController = new PersonalInfoViewController(this, customer);
        this.orderMenuViewController = new OrderMenuViewController(this, customer);
        this.findProductViewController = new FindProductViewController(this);
        this.orderMenuViewController = new OrderMenuViewController(this, customer);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Login");
                frame.setContentPane(loginViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public void sendMessage(Message message) {

        String str = gson.toJson(message);
        try {

            // Encrypting the message
            // using the symmetric key
            byte[] cipherText
                    = KeyService.do_AESEncryption(
                    str,
                    secretKey,
                    initializationVector);

            String cipherTextString = Base64.getEncoder().encodeToString(cipherText);

            dataOutputStream.writeUTF(cipherTextString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private class Worker implements Runnable {

        @Override
        public void run() {
            while (true) {
                String replyString = null;
                try {
                    replyString = dataInputStream.readUTF();

                    byte[] decode = Base64.getDecoder().decode(replyString);

                    replyString
                            = KeyService.do_AESDecryption(
                            decode,
                            secretKey,
                            initializationVector);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message message = gson.fromJson(replyString, Message.class);

                processMessage(message);

            }
        }
    }

    private void processMessage(Message message) {
        messageTextArea.append(gson.toJson(message) + "\n");
        switch (message.getId()) {
            case Message.LOAD_PRODUCT_REPLY: {
                Product product = gson.fromJson(message.getContent(), Product.class);
                productViewController.updateProductInfo(product);
                break;
            }
            case Message.LOAD_CUSTOMER_REPLY: {
                Customer customer = gson.fromJson(message.getContent(), Customer.class);
                customerViewController.updateCustomerInfo(customer);
                break;
            }
            case Message.LOAD_ORDER_REPLY: {
                Order order = gson.fromJson(message.getContent(), Order.class);
                orderViewController.updateOrderInfo(order);
                break;
            }
            case Message.LOGIN_REPLY: {
                customer = gson.fromJson(message.getContent(), User.class);
                if(customer.isManager()) {
                    loginViewController.getManagerPanel();
                    break;
                } else {
                    custMenuViewController = new CustMenuViewController(loginViewController.getClient(), customer);
                    loginViewController.getCustomerPanel();
                    break;
                }
            }
            case Message.FIND_PRODUCT_REPLY: {
                ProductListModel productList = gson.fromJson(message.getContent(), ProductListModel.class);
                findProductViewController.updateProductSearch(productList);
                break;
            }
            case Message.LOAD_ORDER_HISTORY_REPLY: {
                OrderListModel orderListModel = gson.fromJson(message.getContent(), OrderListModel.class);
                orderMenuViewController.updateOrderSearch(orderListModel);
                break;
            }
            case Message.LOAD_ORDERLINE_REPLY: {
                OrderLine orderLine = gson.fromJson(message.getContent(), OrderLine.class);
                orderMenuViewController.updateOrderLine(orderLine);
                break;
            }
            default:
                return;
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Client");
        frame.setContentPane(new Client().mainPanel);
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void setUser(User user) {
        this.customer = user;
    }
    public ProductViewController getProductViewController() {
        return productViewController;
    }
    public CustomerViewController getCustomerViewController() {
        return customerViewController;
    }
    public OrderViewController getOrderViewController() {
        return orderViewController;
    }
    public CustMenuViewController getCustMenuViewController() {
        return custMenuViewController;
    }
    public FindProductViewController getFindProductViewController() {
        return findProductViewController;
    }
    public OrderMenuViewController getOrderMenuViewController() {
        return orderMenuViewController;
    }
    public void setOrderMenuViewController(OrderMenuViewController orderMenuViewController) {
        this.orderMenuViewController = orderMenuViewController;
    }

}
