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

public class AddSchedulePanel extends JPanel {

    private ScheduleController controller;
    private JPanel mainPanel;
    private CardLayout cardLayout;

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

    public AddSchedulePanel(ScheduleController controller, JPanel mainPanel, CardLayout cardLayout) {
        this.controller = controller;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Add Schedule", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));

        comboRoute = new JComboBox<>();
        comboLocomotive = new JComboBox<>();

        // Departure & Arrival spinners
        departureSpinner = new JSpinner(new SpinnerDateModel());
        departureSpinner.setEditor(new JSpinner.DateEditor(departureSpinner, "yyyy-MM-dd HH:mm"));
        arrivalSpinner = new JSpinner(new SpinnerDateModel());
        arrivalSpinner.setEditor(new JSpinner.DateEditor(arrivalSpinner, "yyyy-MM-dd HH:mm"));

        serviceNameField = new JTextField();
        serviceNoField = new JTextField();
        priceField = new JTextField();

        form.add(new JLabel("Route:"));
        form.add(comboRoute);

        form.add(new JLabel("Locomotive:"));
        form.add(comboLocomotive);

        form.add(new JLabel("Departure:"));
        form.add(departureSpinner);

        form.add(new JLabel("Arrival:"));
        form.add(arrivalSpinner);

        form.add(new JLabel("Service Name:"));
        form.add(serviceNameField);

        form.add(new JLabel("Service No:"));
        form.add(serviceNoField);

        form.add(new JLabel("Price:"));
        form.add(priceField);

        add(form, BorderLayout.CENTER);

        // Railcar table
        railcarTable = new JTable();
        JScrollPane scroll = new JScrollPane(railcarTable);
        scroll.setPreferredSize(new Dimension(400, 150));
        add(scroll, BorderLayout.EAST);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout());
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        buttons.add(saveBtn);
        buttons.add(cancelBtn);
        add(buttons, BorderLayout.SOUTH);

        loadFormData();

        cancelBtn.addActionListener(e -> cardLayout.show(mainPanel, "SCHEDULES"));
        saveBtn.addActionListener(e -> saveSchedule());
    
    }

    private void loadFormData() {
        comboRoute.setModel(new DefaultComboBoxModel<>(controller.getAllRoutes()));
        comboLocomotive.setModel(new DefaultComboBoxModel<>(controller.getAvailableLocomotives()));
        railcarTable.setModel(controller.getRailcarTableModel());
    }

    private List<ScheduleController.RailcarSelection> getSelectedRailcars() {
        List<ScheduleController.RailcarSelection> selected = new java.util.ArrayList<>();
        for (int i = 0; i < railcarTable.getRowCount(); i++) {
            Boolean checked = (Boolean) railcarTable.getValueAt(i, 0);
            if (checked != null && checked) {
                String railcarId = (String) railcarTable.getValueAt(i, 1);
                int order = (Integer) railcarTable.getValueAt(i, 4);
                selected.add(new ScheduleController.RailcarSelection(railcarId, order));
            }
        }
        return selected;
    }

    private boolean validateForm() {
        if (comboRoute.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a route!");
            return false;
        }
        if (comboLocomotive.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a locomotive!");
            return false;
        }
        if (serviceNameField.getText().isEmpty() ||
                serviceNoField.getText().isEmpty() ||
                priceField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
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
            Route route = (Route) comboRoute.getSelectedItem();
            Locomotive loco = (Locomotive) comboLocomotive.getSelectedItem();

            Date depDate = (Date) departureSpinner.getValue();
            Date arrDate = (Date) arrivalSpinner.getValue();

            LocalDateTime departure = depDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime arrival = arrDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            String serviceName = serviceNameField.getText();
            String serviceNo = serviceNoField.getText();
            double price = Double.parseDouble(priceField.getText());

            Schedule schedule = controller.createScheduleFromForm(
                    route, loco, departure, arrival, serviceName, serviceNo, price
            );

            List<ScheduleRailcar> railcars = controller.createScheduleRailcars(schedule.getScheduleId(), getSelectedRailcars());

            boolean success = controller.addSchedule(schedule, railcars);
            if (success) JOptionPane.showMessageDialog(this, "Schedule added successfully!");
            else JOptionPane.showMessageDialog(this, "Failed to add schedule!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

}
