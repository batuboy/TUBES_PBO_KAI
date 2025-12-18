package kai.view.adminView.SchedulesPanel;

import kai.controller.ScheduleController;
import kai.models.reservation.Schedule;
import kai.models.reservation.ScheduleRailcar;
import kai.models.train.Locomotive;
import kai.models.train.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EditSchedulePanel extends JPanel {

    private ScheduleController controller;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Schedule schedule;

    private JComboBox<Route> comboRoute;
    private JComboBox<Locomotive> comboLocomotive;
    private JSpinner departureSpinner;
    private JSpinner arrivalSpinner;
    private JTextField serviceNameField;
    private JTextField serviceNoField;
    private JTextField priceField;
    private JTable railcarTable;
    private JButton saveBtn;
    private JButton cancelBtn;

    public EditSchedulePanel(ScheduleController controller, JPanel mainPanel, CardLayout cardLayout) {
        this.controller = controller;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Edit Schedule", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));

        comboRoute = new JComboBox<>(controller.getAllRoutes());
        comboLocomotive = new JComboBox<>(controller.getAvailableLocomotives());
        departureSpinner = new JSpinner(new SpinnerDateModel());
        departureSpinner.setEditor(new JSpinner.DateEditor(departureSpinner, "yyyy-MM-dd HH:mm"));
        arrivalSpinner = new JSpinner(new SpinnerDateModel());
        arrivalSpinner.setEditor(new JSpinner.DateEditor(arrivalSpinner, "yyyy-MM-dd HH:mm"));
        serviceNameField = new JTextField();
        serviceNoField = new JTextField();
        priceField = new JTextField();

        form.add(new JLabel("Route:")); form.add(comboRoute);
        form.add(new JLabel("Locomotive:")); form.add(comboLocomotive);
        form.add(new JLabel("Departure:")); form.add(departureSpinner);
        form.add(new JLabel("Arrival:")); form.add(arrivalSpinner);
        form.add(new JLabel("Service Name:")); form.add(serviceNameField);
        form.add(new JLabel("Service No:")); form.add(serviceNoField);
        form.add(new JLabel("Price:")); form.add(priceField);

        add(form, BorderLayout.CENTER);

        railcarTable = new JTable();
        railcarTable.setModel(controller.getRailcarTableModel());
        add(new JScrollPane(railcarTable), BorderLayout.EAST);

        JPanel buttons = new JPanel(new FlowLayout());
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        buttons.add(saveBtn);
        buttons.add(cancelBtn);
        add(buttons, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> cardLayout.show(mainPanel, "SCHEDULES"));
        saveBtn.addActionListener(e -> saveSchedule());
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;

        comboRoute.setSelectedItem(schedule.getRoute());
        comboLocomotive.setSelectedItem(schedule.getLocomotive());

        Date depDate = Date.from(schedule.getDepartureTime().atZone(ZoneId.systemDefault()).toInstant());
        Date arrDate = Date.from(schedule.getArrivalTime().atZone(ZoneId.systemDefault()).toInstant());
        departureSpinner.setValue(depDate);
        arrivalSpinner.setValue(arrDate);

        serviceNameField.setText(schedule.getServiceName());
        serviceNoField.setText(schedule.getServiceNo());
        priceField.setText(String.valueOf(schedule.getPrice()));

        // Select railcars
        List<ScheduleRailcar> railcars = controller.getRailcarsForSchedule(schedule.getScheduleId());
        DefaultTableModel model = (DefaultTableModel) railcarTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String railcarId = (String) model.getValueAt(i, 1);
            for (ScheduleRailcar sr : railcars) {
                if (sr.getRailcarId().equals(railcarId)) {
                    model.setValueAt(true, i, 0); // check
                    model.setValueAt(sr.getOrder(), i, 4);
                }
            }
        }
    }

    private List<ScheduleController.RailcarSelection> getSelectedRailcars() {
        List<ScheduleController.RailcarSelection> selected = new java.util.ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) railcarTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean checked = (Boolean) model.getValueAt(i, 0);
            if (checked != null && checked) {
                String railcarId = (String) model.getValueAt(i, 1);
                int order = (Integer) model.getValueAt(i, 4);
                selected.add(new ScheduleController.RailcarSelection(railcarId, order));
            }
        }
        return selected;
    }

    private boolean validateForm() {
        if (comboRoute.getSelectedItem() == null || comboLocomotive.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Select route and locomotive!");
            return false;
        }
        if (serviceNameField.getText().isEmpty() || serviceNoField.getText().isEmpty() || priceField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return false;
        }
        if (getSelectedRailcars().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one railcar!");
            return false;
        }
        return true;
    }

    private void saveSchedule() {
        if (!validateForm()) return;

        try {
            schedule.setRoute((Route) comboRoute.getSelectedItem());
            schedule.setLocomotive((Locomotive) comboLocomotive.getSelectedItem());
            schedule.setDepartureTime(((Date) departureSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            schedule.setArrivalTime(((Date) arrivalSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            schedule.setServiceName(serviceNameField.getText());
            schedule.setServiceNo(serviceNoField.getText());
            schedule.setPrice(Double.parseDouble(priceField.getText()));

            boolean success = controller.updateSchedule(schedule, controller.createScheduleRailcars(schedule.getScheduleId(), getSelectedRailcars()));

            if (success) JOptionPane.showMessageDialog(this, "Schedule updated!");
            else JOptionPane.showMessageDialog(this, "Failed to update schedule!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
