package kai.view.userView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import kai.controller.TicketController;
import kai.models.reservation.Ticket;

public class CancelTicketPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private TicketController ticketController;
    private String userId;

    public CancelTicketPanel(String userId) {
        this.userId = userId;
        this.ticketController = new TicketController();

        setLayout(new BorderLayout());

        JLabel title = new JLabel("Cancel / Print Ticket", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        String[] kolom = {"ID Tiket", "Kereta", "Asal", "Tujuan", "Tanggal", "Jam", "Seat", "Status"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelBtn = new JButton("Cancel Ticket");
        JButton printBtn = new JButton("Print Ticket");
        JButton backBtn = new JButton("Back");
        buttonPanel.add(cancelBtn);
        buttonPanel.add(printBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        cancelBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String ticketId = (String) tableModel.getValueAt(row, 0);
                if (ticketController.cancelReservation(ticketId)) {
                    refresh(); 
                    JOptionPane.showMessageDialog(this, "Ticket " + ticketId + " cancelled!");
                } else {
                    JOptionPane.showMessageDialog(this, "Tiket tidak bisa dibatalkan!");
                }
                    
            } else {
                JOptionPane.showMessageDialog(this, "Please select a ticket first.");
            }
        });

        printBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String ticketId = (String) tableModel.getValueAt(row, 0);
                Ticket t = ticketController.printTicket(ticketId);

                JOptionPane.showMessageDialog(this,
                    "Ticket ID: " + t.getTicketId() + "\n" +
                    "Kereta: " + t.getSchedule().getServiceName() + "\n" +
                    "Asal: " + t.getSchedule().getRoute().getOrigin().getName() + "\n" +
                    "Tujuan: " + t.getSchedule().getRoute().getDestination().getName() + "\n" +
                    "Tanggal: " + t.getSchedule().getDepartureTime().toLocalDate() + "\n" +
                    "Jam: " + t.getSchedule().getDepartureTime().toLocalTime() + "\n" +
                    "Seat: " + t.getSeatNumber() + "\n" +
                    "Status: " + t.getStatus().name(),
                    "Print Ticket",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this, "Please select a ticket first.");
            }
        });

        backBtn.addActionListener(e -> {
            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout cl = (CardLayout) parent.getLayout();
                cl.show(parent, "MENU");
            }
        });
        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        loadCancel();
    }

    private void loadCancel() {
        List<Ticket> tickets = ticketController.viewReservationHistory(userId);
        for (Ticket t : tickets) {
            if (t.getStatus().name().equals("BOOKED")) { 
                tableModel.addRow(new Object[]{
                    t.getTicketId(),
                    t.getSchedule().getServiceName(),
                    t.getSchedule().getRoute().getOrigin().getName(),
                    t.getSchedule().getRoute().getDestination().getName(),
                    t.getSchedule().getDepartureTime().toLocalDate(),
                    t.getSchedule().getDepartureTime().toLocalTime(),
                    t.getSeatNumber(),
                    t.getStatus().name()
                });
            }
        }
    }
}
