package kai.view.adminView;

import javax.swing.*;
import kai.view.BaseView;
import kai.view.LoginView;
import java.awt.*;

public class MenuAdminView extends BaseView {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MenuAdminView() {
        super("KAI App - Admin Menu", 600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Main Menu Panel ---
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton manageDashboardBtn = new JButton("Dashboard");
        JButton manageSchedulesBtn = new JButton("Schedule");
        JButton manageMaintenanceBtn = new JButton("Maintenance");
        JButton manageReportBtn = new JButton("Report");
        JButton logoutBtn = new JButton("Logout");

        menuPanel.add(manageDashboardBtn);
        menuPanel.add(manageSchedulesBtn);
        menuPanel.add(manageMaintenanceBtn);
        menuPanel.add(manageReportBtn);
        menuPanel.add(logoutBtn);

        // --- Sub-panels (templates) ---
        DashBoardPanel dashBoardPanel = new DashBoardPanel();
        SchedulesPanel schedulesPanel = new SchedulesPanel();
        MaintenancePanel maintenancePanel = new MaintenancePanel();
        ReportPanel reportPanel = new ReportPanel();

        // Add panels to CardLayout
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(dashBoardPanel, "DASHBOARD");
        mainPanel.add(schedulesPanel, "SCHEDULES");
        mainPanel.add(maintenancePanel, "MAINTENANCE");
        mainPanel.add(reportPanel, "REPORT");
        // mainPanel.add(manageUsersPanel, "USERS");

        add(mainPanel, BorderLayout.CENTER);

        // --- Button actions ---
        manageDashboardBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        manageSchedulesBtn.addActionListener(e -> cardLayout.show(mainPanel, "SCHEDULES"));
        manageMaintenanceBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAINTENANCE"));
        manageReportBtn.addActionListener(e -> cardLayout.show(mainPanel, "REPORT"));
        logoutBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }
}
