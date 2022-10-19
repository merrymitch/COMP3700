import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalInfoViewController {
    private JPanel mainPanel;
    private JTextField fullNameTF;
    private JTextField addressTF;
    private JTextField paymentTF;
    private JTextField phoneNumberTF;
    private JButton saveInfoButton;
    private JButton loadInfoButton;

    private Client client;
    private User user;

    public PersonalInfoViewController(Client client, User user) {
        this.client = client;
        this.user = user;

        saveInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setFullName(fullNameTF.getText());
                user.setAddress(addressTF.getText());
                user.setPayment(paymentTF.getText());
                user.setPhoneNumber(phoneNumberTF.getText());
                Gson gson = new Gson();
                String userString = gson.toJson(user);
                Message message = new Message(Message.SAVE_USER, userString);
                client.sendMessage(message);
            }
        });

        loadInfoButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                fullNameTF.setText(user.getFullName());
                addressTF.setText(user.getAddress());
                paymentTF.setText(user.getPayment());
                phoneNumberTF.setText(user.getPhoneNumber());
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
