package kai.view.userView;

import javax.swing.*;
import kai.view.BaseView;
import kai.view.LoginView;
import java.awt.*;

public class MenuUserView extends BaseView {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MenuUserView() {
        super("KAI App - User Menu", 600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Main Menu Panel ---
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

        // --- Sub-panels ---
        BookTicketPanel bookPanel = new BookTicketPanel();
        HistoryTicketPanel historyPanel = new HistoryTicketPanel();
        CancelTicketPanel cancelPanel = new CancelTicketPanel();

        // Add panels to CardLayout
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(bookPanel, "BOOK");
        mainPanel.add(historyPanel, "VIEW");
        mainPanel.add(cancelPanel, "CANCEL");

        add(mainPanel, BorderLayout.CENTER);

        // --- Button actions ---
        bookBtn.addActionListener(e -> cardLayout.show(mainPanel, "BOOK"));
        viewBtn.addActionListener(e -> cardLayout.show(mainPanel, "VIEW"));
        cancelBtn.addActionListener(e -> cardLayout.show(mainPanel, "CANCEL"));

        logoutBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }
}
