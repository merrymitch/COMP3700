import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCustViewController {
    private JPanel mainPanel;
    private JTextField usernameTF;
    private JTextField passwordTF;
    private JTextField fullNameTF;
    private JTextField addressTF;
    private JTextField paymentTF;
    private JTextField phoneNumberTF;
    private JButton saveButton;

    private Client client;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public NewCustViewController(Client client) {
        this.client = client;

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUsername(usernameTF.getText());
                user.setPassword(passwordTF.getText());
                user.setFullName(fullNameTF.getText());
                user.setAddress(addressTF.getText());
                user.setPayment(paymentTF.getText());
                user.setPhoneNumber(phoneNumberTF.getText());

                Gson gson = new Gson();

                String newUserString = gson.toJson(user);

                Message message = new Message(Message.SAVE_NEW_USER, newUserString);
                client.sendMessage(message);
            }

        });

    }
}
