import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustMenuViewController {
    private JPanel mainPanel;
    private JButton changeMyInfoButton;
    private JButton searchProductsButton;
    private JButton myOrdersButton;

    private Client client;
    private User user;

    private ChangeInfoViewController changeInfoViewController;
    private OrderMenuViewController orderMenuViewController;
    private FindProductViewController findProductViewController;

    public CustMenuViewController(Client client, User user) {
        this.client = client;
        this.user = user;
        this.changeInfoViewController = new ChangeInfoViewController(client, user);
        this.orderMenuViewController = new OrderMenuViewController(client, user);
        this.findProductViewController = new FindProductViewController(client);

        changeMyInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeInfoViewController.setUser(user);
                JFrame frame = new JFrame("Customer Menu");
                frame.setContentPane(changeInfoViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        myOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderMenuViewController.setUser(user);
                client.setOrderMenuViewController(orderMenuViewController);
                JFrame frame = new JFrame("Order Menu");
                frame.setContentPane(orderMenuViewController.getMainPanel());
                frame.setMinimumSize(new Dimension(800, 400));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });

        searchProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Search Product Menu");
                frame.setContentPane(client.getFindProductViewController().getMainPanel());
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

}
