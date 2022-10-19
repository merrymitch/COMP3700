import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordViewController {
    private JPanel mainPanel;
    private JTextField passwordTF;
    private JButton changePasswordButton;

    private Client client;
    private User user;

    public ChangePasswordViewController(Client client, User user) {
        this.client = client;
        this.user = user;

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setPassword(passwordTF.getText());
                Gson gson = new Gson();

                String newUserPassword = gson.toJson(user);

                Message message = new Message(Message.SAVE_PASSWORD, newUserPassword);
                client.sendMessage(message);
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
