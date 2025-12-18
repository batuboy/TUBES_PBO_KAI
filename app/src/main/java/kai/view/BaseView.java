package kai.view;

import javax.swing.*;
import java.awt.*;

public class BaseView extends JFrame {

    protected HeaderPanel header;

    public BaseView(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center on screen
        setLayout(new BorderLayout());

        // Add header by default
        header = new HeaderPanel();
        add(header, BorderLayout.NORTH);
    }

    // Helper method to apply consistent theme
    protected void applyTheme(JComponent component) {
        component.setBackground(new Color(240, 240, 240));
        component.setFont(new Font("Arial", Font.PLAIN, 14));
    }
}

