package kai.view;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 102, 204)); // KAI blue

        // KAI logo
        // ImageIcon logoIcon = new ImageIcon(getClass().getResource("/kai/view/resources/kai_logo.png"));

        // JLabel logoLabel = new JLabel(logoIcon);
        // logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // add(logoLabel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(800, 100));
    }
}

