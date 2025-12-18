package kai.view;

import javax.swing.*;
import java.awt.*;
import kai.controller.UserController;
import kai.models.user.Admin;
import kai.models.user.Passenger;
import kai.models.user.User;
import kai.models.user.num.Position;
import kai.view.adminView.MenuAdminView;
import kai.view.userView.MenuUserView;

public class LoginView extends BaseView {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    private UserController userController;

    public LoginView() {
        super("KAI App - Login", 400, 500);
        userController = new UserController();

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(loginButton);
        formPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);

        // ===== Login action =====
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if(email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter email and password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = userController.login(email, password);
            if(user != null) {
                // Redirect based on email role
                if(user.getPosition() == Position.ADMIN) {
                    new MenuAdminView().setVisible(true);
                } else if(user.getPosition() == Position.PASSENGER) {
                    new MenuUserView().setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ===== Register action =====
        registerButton.addActionListener(e -> {
            new RegisterView().setVisible(true);
            dispose();
        });
    }

    // Optional: run LoginView directly
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
