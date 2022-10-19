import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeInfoViewController {
    private JPanel mainPanel;
    private JButton changePasswordButton;
    private JButton changeMyPersonalInfoButton;

    private Client client;
    private User user;
    private PersonalInfoViewController personalInfoViewController;
    private ChangePasswordViewController changePasswordViewController;

    public ChangeInfoViewController(Client client, User user) {
        this.client = client;
        this.user = user;
        this.personalInfoViewController = new PersonalInfoViewController(client, user);
        this.changePasswordViewController = new ChangePasswordViewController(client, user);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordViewController.setUser(user);
                JFrame frame = new JFrame("Customer Menu");
                frame.setContentPane(changePasswordViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        changeMyPersonalInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                personalInfoViewController.setUser(user);
                JFrame frame = new JFrame("Customer Menu");
                frame.setContentPane(personalInfoViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
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
