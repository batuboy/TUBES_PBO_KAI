package kai.view.adminView;

import kai.controller.MaintenanceController;
import kai.models.train.Locomotive;
import kai.models.train.Railcar;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MaintenancePanel extends JPanel {

    private MaintenanceController controller;

    private JTable locoTable;
    private JTable railcarTable;

    private List<Locomotive> locomotives;
    private List<Railcar> railCars;

    public MaintenancePanel() {
        controller = new MaintenanceController();

        setLayout(new BorderLayout(10, 10));

        // ===== TITLE =====
        JLabel title = new JLabel("Maintenance", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // ===== LOAD DATA =====
        locomotives = controller.getAllLocomotives();
        railCars = controller.getAllRailCars();

        // ===== CENTER CONTENT =====
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // ---------- LOCOMOTIVES ----------
        JLabel locoLabel = new JLabel("Locomotives");
        locoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        locoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] locoCols = { "ID", "Name", "Status" };
        Object[][] locoData = new Object[locomotives.size()][3];

        for (int i = 0; i < locomotives.size(); i++) {
            Locomotive l = locomotives.get(i);
            locoData[i][0] = l.getLocomotiveId();
            locoData[i][1] = l.getName();
            locoData[i][2] = l.getStatus();
        }

        locoTable = new JTable(locoData, locoCols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make all cells non-editable
            }
        };
        locoTable.getTableHeader().setReorderingAllowed(false);
        locoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        locoTable.setRowSelectionAllowed(true);
        locoTable.setColumnSelectionAllowed(false);

        JScrollPane locoScroll = new JScrollPane(locoTable);
        locoScroll.setPreferredSize(new Dimension(600, 200));

        JButton toggleLocoBtn = new JButton("Toggle Locomotive Status");
        toggleLocoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleLocoBtn.addActionListener(e -> toggleSelectedLocomotive());

        // ---------- RAILCARS ----------
        JLabel railLabel = new JLabel("Railcars");
        railLabel.setFont(new Font("Arial", Font.BOLD, 14));
        railLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] railCols = { "ID", "Name", "Status" };
        Object[][] railData = new Object[railCars.size()][3];

        for (int i = 0; i < railCars.size(); i++) {
            Railcar r = railCars.get(i);
            railData[i][0] = r.getRailcarId();
            railData[i][1] = r.getName();
            railData[i][2] = r.getStatus();
        }

        railcarTable = new JTable(railData, railCols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        railcarTable.getTableHeader().setReorderingAllowed(false);
        railcarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        railcarTable.setRowSelectionAllowed(true);
        railcarTable.setColumnSelectionAllowed(false);

        JScrollPane railScroll = new JScrollPane(railcarTable);
        railScroll.setPreferredSize(new Dimension(600, 200));

        JButton toggleRailBtn = new JButton("Toggle Railcar Status");
        toggleRailBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleRailBtn.addActionListener(e -> toggleSelectedRailCar());

        // ---------- ADD TO CONTENT PANEL ----------
        contentPanel.add(locoLabel);
        contentPanel.add(locoScroll);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(toggleLocoBtn);
        contentPanel.add(Box.createVerticalStrut(15));

        contentPanel.add(railLabel);
        contentPanel.add(railScroll);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(toggleRailBtn);

        add(contentPanel, BorderLayout.CENTER);

        // ===== BACK BUTTON =====
        JButton backBtn = new JButton("Back");
        add(backBtn, BorderLayout.SOUTH);
        backBtn.addActionListener(e -> {
            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout cl = (CardLayout) parent.getLayout();
                cl.show(parent, "MENU");
            }
        });
    }

    // ===== HELPERS =====

    private void toggleSelectedLocomotive() {
        int row = locoTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a locomotive first");
            return;
        }

        Locomotive loco = locomotives.get(row);
        controller.toggleLocomotiveStatus(loco);
        locoTable.setValueAt(loco.getStatus(), row, 2); // update table
    }

    private void toggleSelectedRailCar() {
        int row = railcarTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a railcar first");
            return;
        }

        Railcar railCar = railCars.get(row);
        controller.toggleRailCarStatus(railCar);
        railcarTable.setValueAt(railCar.getStatus(), row, 2);
    }
}
