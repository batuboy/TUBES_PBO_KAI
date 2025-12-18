package kai.view;

import javax.swing.*;
import java.awt.*;

import kai.controller.UserController;
import kai.models.user.Passenger;

public class RegisterView extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, backButton;

    private UserController userController;

    public RegisterView() {
        super("KAI App - Register");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userController = new UserController();

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        // Add labels and fields
        panel.add(new JLabel("Full Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);

        panel.add(registerButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        // Register button action
        registerButton.addActionListener(e -> registerAction());

        // Back button action
        backButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
    }

    private void registerAction() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = userController.register(name, phone, email, password);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginView().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email may already be registered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
