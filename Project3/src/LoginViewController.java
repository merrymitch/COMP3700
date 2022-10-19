import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginViewController {
    private JPanel mainPanel;
    private JTextField usernameTF;
    private JTextField passwordTF;
    private JButton newUserButton;
    private JButton loginButton;

    private Client client;

    private NewCustViewController newCustViewController;

    public LoginViewController(Client client) {
        this.client = client;
        this.newCustViewController = new NewCustViewController(client);

        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("New User");
                frame.setContentPane(newCustViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUsername(usernameTF.getText());
                user.setPassword(passwordTF.getText());

                Gson gson = new Gson();
                String userLogin = gson.toJson(user);

                Message message = new Message(Message.LOGIN, userLogin);
                client.sendMessage(message);

            }
        });

    }
    public void getManagerPanel() {
        JFrame frame = new JFrame("Manager Menu");
        frame.setContentPane(client.getProductViewController().getMainPanel());
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void getCustomerPanel() {
        JFrame frame = new JFrame("Customer Menu");
        frame.setContentPane(client.getCustMenuViewController().getMainPanel());
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }
    public Client getClient() {
        return client;
    }

}
