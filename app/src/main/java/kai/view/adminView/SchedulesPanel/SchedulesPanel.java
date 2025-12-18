package kai.view.adminView.SchedulesPanel;

import kai.controller.ScheduleController;
import kai.models.reservation.Schedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SchedulesPanel extends JPanel {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ScheduleController controller;
    private JTable table;

    private EditSchedulePanel editSchedulePanel;

    public SchedulesPanel(JPanel mainPanel, CardLayout cardLayout, ScheduleController controller,
            EditSchedulePanel editSchedulePanel) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.controller = controller;
        this.editSchedulePanel = editSchedulePanel;

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Manage Schedules", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Schedule");
        JButton editBtn = new JButton("Edit Schedule");
        JButton backBtn = new JButton("Back");

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load data
        loadTableData();

        // BUTTON ACTIONS
        addBtn.addActionListener(e -> cardLayout.show(mainPanel, "SCHEDULE_ADD"));

        editBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a schedule first!");
                return;
            }

            String scheduleId = table.getValueAt(selectedRow, 0).toString();
            Schedule schedule = controller.getAllSchedules().stream()
                    .filter(s -> s.getScheduleId().equals(scheduleId))
                    .findFirst()
                    .orElse(null);

            if (schedule != null) {
                editSchedulePanel.setSchedule(schedule); // pass schedule directly
                cardLayout.show(mainPanel, "SCHEDULE_EDIT");
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
    }

    public void loadTableData() {
        List<Schedule> schedules = controller.getAllSchedules();
        String[] columns = { "ID", "Service No", "Route", "Departure", "Arrival", "Price" };
        Object[][] data = new Object[schedules.size()][columns.length];

        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            data[i][0] = s.getScheduleId();
            data[i][1] = s.getServiceNo();
            data[i][2] = s.getRoute().getOrigin().getName() + " → " + s.getRoute().getDestination().getName();
            data[i][3] = s.getDepartureTime().toString();
            data[i][4] = s.getArrivalTime().toString();
            data[i][5] = s.getPrice();
        }

        table.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
}
