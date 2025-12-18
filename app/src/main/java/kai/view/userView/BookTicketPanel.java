package kai.view.userView;

import javax.swing.*;

import kai.controller.ScheduleController;
import kai.controller.StationController;
import kai.controller.TicketController;
import kai.models.train.Station;
import kai.models.user.User;
import kai.models.reservation.Schedule;
import kai.models.reservation.ScheduleRailcar;

import java.awt.*;
import java.time.LocalDateTime;

import com.github.lgooddatepicker.components.DatePicker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookTicketPanel extends JPanel {

    private JComboBox<String> asalCombo;
    private JComboBox<String> tujuanCombo;
    private DatePicker tanggalPicker;
    private JPanel resultPanel;
    private Schedule selectedSchedule;
    private String selectedSeatNumber;
    private User user;

    public BookTicketPanel(User user) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        this.user = user;

        StationController stationController = new StationController();
        List<String> stations = new ArrayList<>();
        for (Station station : stationController.getAllStations()) {
            stations.add(station.getName());
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(formPanel(stations));

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(Color.WHITE);
        centerPanel.add(resultPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(backPanel(), BorderLayout.SOUTH);
    }

    private JPanel formPanel(List<String> stations) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        asalCombo = new JComboBox<>(stations.toArray(new String[0]));
        panel.add(createLabeledComponent("Stasiun Asal", asalCombo));
        panel.add(Box.createVerticalStrut(12));

        tujuanCombo = new JComboBox<>(stations.toArray(new String[0]));
        panel.add(createLabeledComponent("Stasiun Tujuan", tujuanCombo));
        panel.add(Box.createVerticalStrut(12));

        tanggalPicker = new DatePicker();
        panel.add(createLabeledComponent("Tanggal Berangkat", tanggalPicker));
        panel.add(Box.createVerticalStrut(20));

        JButton searchBtn = new JButton("Pesan & Cari Kereta");
        searchBtn.setBackground(new Color(255, 140, 0));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchBtn.addActionListener(e -> {
            resultPanel.removeAll();

            ScheduleController controller = new ScheduleController();
            List<Schedule> hasil = controller.searchSchedules(
                    getStasiunAsal(),
                    getStasiunTujuan(),
                    getTanggalBerangkat());

            if (hasil.isEmpty()) {
                resultPanel.add(new JLabel("Tidak ada jadwal ditemukan."));
            } else {
                for (Schedule s : hasil) {
                    if (!LocalDateTime.now().isAfter(s.getDepartureTime().minusMinutes(30))) {
                        JPanel card = new JPanel();
                        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        card.setBackground(new Color(250, 250, 250));

                        card.add(new JLabel("Kereta: " + s.getServiceName() + " (" + s.getServiceNo() + ")"));
                        card.add(new JLabel("Berangkat: " + s.getDepartureTime().toLocalTime()));
                        card.add(new JLabel("Tiba: " + s.getArrivalTime().toLocalTime()));
                        card.add(new JLabel("Harga: Rp" + s.getPrice()));
                        JButton pilihBtn = new JButton("Pilih Jadwal");
                        pilihBtn.addActionListener(ev -> {
                            selectedSchedule = s;
                            showRailcarPanel(selectedSchedule);
                        });
                        card.add(pilihBtn);
                        resultPanel.add(Box.createVerticalStrut(10));
                        resultPanel.add(card);
                        }
                }
            }

            resultPanel.revalidate();
            resultPanel.repaint();
        });

        panel.add(searchBtn);
        return panel;
    }

    private JPanel createLabeledComponent(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panel.setBackground(new Color(245, 245, 245));

        JLabel label = new JLabel("  " + labelText);
        label.setPreferredSize(new Dimension(120, 30));
        inputComponent.setBorder(null);

        panel.add(label, BorderLayout.WEST);
        panel.add(inputComponent, BorderLayout.CENTER);
        return panel;
    }

    private JPanel backPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            Container parent = getParent();
            if (parent != null && parent.getLayout() instanceof CardLayout) {
                ((CardLayout) parent.getLayout()).show(parent, "MENU");
            }
        });

        panel.add(backBtn);
        return panel;
    }

    private void showRailcarPanel(Schedule schedule) {
        resultPanel.removeAll();

        ScheduleController sc = new ScheduleController();
        List<ScheduleRailcar> railcars = sc.getRailcarsForSchedule(schedule.getScheduleId());

        for (ScheduleRailcar sr : railcars) {
            JButton railcarBtn = new JButton("Gerbong " + sr.getOrder());
            railcarBtn.addActionListener(e -> {
                showSeatPanel(schedule.getScheduleId(), sr.getRailcarId());
            });
            resultPanel.add(railcarBtn);
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private void showSeatPanel(String scheduleId, String railcarId) {
        resultPanel.removeAll();

        TicketController ticketController = new TicketController();
        List<Map<String, String>> availableSeats = ticketController.viewAvailableSeats(scheduleId, railcarId);

        JPanel seatPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        for (Map<String, String> seat : availableSeats) {
            String seatId = seat.get("seatId");
            String seatNo = seat.get("seatNo");

            JButton seatBtn = new JButton(seatNo); 
            seatBtn.setBackground(Color.GREEN);
            seatBtn.addActionListener(e -> {
                selectedSeatNumber = seatId;
                JOptionPane.showMessageDialog(this, "Kamu pilih kursi: " + seatNo);
            });
            seatPanel.add(seatBtn);
        }

        JButton confirmBtn = new JButton("Konfirmasi Booking");
        confirmBtn.addActionListener(e -> {
            if (selectedSeatNumber != null) {
                ticketController.bookingTicket(this.user, selectedSchedule, selectedSeatNumber);
                JOptionPane.showMessageDialog(this, "Tiket berhasil dipesan!");
            } else {
                JOptionPane.showMessageDialog(this, "Pilih kursi dulu!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        resultPanel.add(seatPanel);
        resultPanel.add(confirmBtn);
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public String getStasiunAsal() {
        return (String) asalCombo.getSelectedItem();
    }

    public String getStasiunTujuan() {
        return (String) tujuanCombo.getSelectedItem();
    }

    public String getTanggalBerangkat() {
        if (tanggalPicker.getDate() != null) {
            return tanggalPicker.getDate().toString();
        }
        return "";
    }

}
