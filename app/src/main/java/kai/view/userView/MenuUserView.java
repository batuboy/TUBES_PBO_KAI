package kai.view.userView;

import javax.swing.*;

import kai.models.user.User;
import kai.view.BaseView;
import kai.view.LoginView;
import java.awt.*;

public class MenuUserView extends BaseView {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private User user;

    public MenuUserView(User user) {
        super("KAI App - User Menu", 600, 400);
        this.user = user;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton bookBtn = new JButton("Book Ticket");
        JButton viewBtn = new JButton("View Tickets");
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton logoutBtn = new JButton("Logout");

        menuPanel.add(bookBtn);
        menuPanel.add(viewBtn);
        menuPanel.add(cancelBtn);
        menuPanel.add(logoutBtn);

        BookTicketPanel bookPanel = new BookTicketPanel(user);
        HistoryTicketPanel historyPanel = new HistoryTicketPanel(this.user.getUserId());
        CancelTicketPanel cancelPanel = new CancelTicketPanel(user.getUserId());

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(bookPanel, "BOOK");
        mainPanel.add(historyPanel, "VIEW");
        mainPanel.add(cancelPanel, "CANCEL");

        add(mainPanel, BorderLayout.CENTER);

        bookBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK"));
        viewBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "VIEW");
            historyPanel.refresh(user.getUserId());
        });
        cancelBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "CANCEL");
            cancelPanel.refresh();
        });

        logoutBtn.addActionListener(e -> { 
            new LoginView().setVisible(true);
            dispose();
        });
    }
}
