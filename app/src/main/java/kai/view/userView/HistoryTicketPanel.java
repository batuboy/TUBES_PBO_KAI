package kai.view.userView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import kai.controller.TicketController;
import kai.models.reservation.Ticket;

public class HistoryTicketPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public HistoryTicketPanel(String userId) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Riwayat Tiket", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        String[] kolom = { "ID Tiket", "Kereta", "Asal", "Tujuan", "Tanggal", "Jam Berangkat", "Harga", "Status" };
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            Container parent = getParent();
            if (parent != null && parent.getLayout() instanceof CardLayout) {
                ((CardLayout) parent.getLayout()).show(parent, "MENU");
            }
        });
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backBtn);
        add(backPanel, BorderLayout.SOUTH);

        loadHistory(userId);
    }

    public void refresh(String userId) {
        tableModel.setRowCount(0);
        loadHistory(userId);
    }

    private void loadHistory(String userId) {
        TicketController ticketController = new TicketController();
        List<Ticket> tickets = ticketController.viewReservationHistory(userId);

        for (Ticket t : tickets) {
            tableModel.addRow(new Object[] {
                    t.getTicketId(),
                    t.getSchedule().getServiceName(),
                    t.getSchedule().getRoute().getOrigin().getName(),
                    t.getSchedule().getRoute().getDestination().getName(),
                    t.getSchedule().getDepartureTime().toLocalDate(),
                    t.getSchedule().getDepartureTime().toLocalTime(),
                    t.getSchedule().getPrice(),
                    t.getStatus().name()
            });
        }
    }
}
