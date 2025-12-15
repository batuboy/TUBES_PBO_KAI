package kai.view.adminView;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {

    // private ManageTrainsController controller;

    public ReportPanel() {
        // this.controller = controller;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Manage Trains Panel", JLabel.CENTER);
        add(title, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        add(backBtn, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            // Use CardLayout in parent to go back (will be handled by MenuAdminView)
            Container parent = getParent();
            if (parent instanceof JPanel) {
                CardLayout cl = (CardLayout) parent.getLayout();
                cl.show(parent, "MENU");
            }
        });
    }
}
