package kai.view.adminView;

import javax.swing.*;

import kai.controller.ReportController;

import java.awt.*;

public class ReportPanel extends JPanel {

    private JLabel ticketLbl;
    private JLabel revenueLbl;
    private JLabel scheduleLbl;

    private ReportController reportController;

    public ReportPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize controller
        reportController = new ReportController();

        // ===== Title =====
        JLabel title = new JLabel("System Report", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // ===== Center Panel =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        ticketLbl = new JLabel("Total Tickets Sold : 0");
        revenueLbl = new JLabel("Total Revenue     : Rp 0");
        scheduleLbl = new JLabel("Total Schedules   : 0");

        ticketLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        revenueLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        scheduleLbl.setFont(new Font("Arial", Font.PLAIN, 16));

        centerPanel.add(ticketLbl);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(revenueLbl);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(scheduleLbl);

        add(centerPanel, BorderLayout.CENTER);

        // ===== Back Button =====
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        backBtn.setPreferredSize(new Dimension(80, 35));
        add(backBtn, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout cl = (CardLayout) parent.getLayout();
                cl.show(parent, "MENU");
            }
        });

        // ===== Fetch and set real data =====
        updateReportData();
    }

    // ===============================
    // Fetch data from controller and update labels
    // ===============================
    private void updateReportData() {
        int tickets = reportController.getTotalTicketsSold();
        double revenue = reportController.getTotalRevenue();
        int schedules = reportController.getTotalSchedules();

        setReportData(tickets, revenue, schedules);
    }

    // ===============================
    // Method to update report data
    // ===============================
    public void setReportData(int tickets, double revenue, int totalSchedules) {
        ticketLbl.setText("Total Tickets Sold : " + tickets);
        revenueLbl.setText("Total Revenue     : Rp " + revenue);
        scheduleLbl.setText("Total Schedules   : " + totalSchedules);
    }
}
