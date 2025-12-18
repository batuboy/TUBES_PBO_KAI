package kai.view.adminView;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import kai.controller.ScheduleController;
import kai.view.BaseView;
import kai.view.LoginView;
import kai.view.adminView.SchedulesPanel.AddSchedulePanel;
import kai.view.adminView.SchedulesPanel.EditSchedulePanel;
import kai.view.adminView.SchedulesPanel.SchedulesPanel;

public class MenuAdminView extends BaseView {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ScheduleController scheduleController;

    public MenuAdminView() {
        super("KAI App - Admin Menu", 600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        scheduleController = new ScheduleController();

        // ===== MENU BUTTONS =====
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        JButton dashboardBtn = new JButton("Dashboard");
        JButton schedulesBtn = new JButton("Schedule");
        JButton maintenanceBtn = new JButton("Maintenance");
        JButton reportBtn = new JButton("Report");
        JButton logoutBtn = new JButton("Logout");
        menuPanel.add(dashboardBtn);
        menuPanel.add(schedulesBtn);
        menuPanel.add(maintenanceBtn);
        menuPanel.add(reportBtn);
        menuPanel.add(logoutBtn);

        // ===== SUB PANELS =====
        DashBoardPanel dashboardPanel = new DashBoardPanel();

        // Create EditSchedulePanel first
        EditSchedulePanel editSchedulePanel =
                new EditSchedulePanel(scheduleController, mainPanel, cardLayout);

        // Pass editSchedulePanel to SchedulesPanel
        SchedulesPanel schedulesPanel =
                new SchedulesPanel(mainPanel, cardLayout, scheduleController, editSchedulePanel);

        AddSchedulePanel addSchedulePanel =
                new AddSchedulePanel(scheduleController, mainPanel, cardLayout);

        MaintenancePanel maintenancePanel = new MaintenancePanel();
        ReportPanel reportPanel = new ReportPanel();

        // ===== ADD TO CARDLAYOUT =====
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        mainPanel.add(schedulesPanel, "SCHEDULES");
        mainPanel.add(addSchedulePanel, "SCHEDULE_ADD");
        mainPanel.add(editSchedulePanel, "SCHEDULE_EDIT");
        mainPanel.add(maintenancePanel, "MAINTENANCE");
        mainPanel.add(reportPanel, "REPORT");

        add(mainPanel, BorderLayout.CENTER);

        // ===== MENU BUTTON ACTIONS =====
        dashboardBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        schedulesBtn.addActionListener(e -> cardLayout.show(mainPanel, "SCHEDULES"));
        maintenanceBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAINTENANCE"));
        reportBtn.addActionListener(e -> cardLayout.show(mainPanel, "REPORT"));
        logoutBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }
}
