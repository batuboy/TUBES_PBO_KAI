package kai.view.userView;

import javax.swing.*;
import java.awt.*;

public class BookTicketPanel extends JPanel {

    public BookTicketPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // FORM di tengah
        add(formPanel(), BorderLayout.CENTER);

        // BACK di bawah
        add(backPanel(), BorderLayout.SOUTH);
    }

    // ================= FORM =================
    private JPanel formPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        panel.add(createField("Stasiun Asal"));
        panel.add(Box.createVerticalStrut(12));

        panel.add(createField("Stasiun Tujuan"));
        panel.add(Box.createVerticalStrut(12));

        panel.add(createTextField("Tanggal Berangkat"));
        panel.add(Box.createVerticalStrut(20));

        JButton searchBtn = new JButton("Pesan & Cari Kereta");
        searchBtn.setBackground(new Color(255, 140, 0));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(searchBtn);
        return panel;
    }

    // ================= TEXT FIELD =================
    private JPanel createTextField(String labelText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panel.setBackground(new Color(245, 245, 245));

        JLabel label = new JLabel("  " + labelText);
        JTextField field = new JTextField();
        field.setBorder(null);

        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    // ================= LABEL FIELD =================
    private JPanel createField(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        panel.setBackground(new Color(245, 245, 245));

        JLabel label = new JLabel("  " + text);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    // ================= BACK PANEL =================
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
}
