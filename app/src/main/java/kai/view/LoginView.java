package kai.view;

import javax.swing.*;

import kai.controller.LoginController;
import kai.models.user.Admin;
import kai.models.user.Employee;
import kai.models.user.User;
import kai.view.adminView.MenuAdminView;
import kai.view.userView.MenuUserView;
import java.awt.*;


public class LoginView extends BaseView {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    private LoginController controller;

    public LoginView() {
        super("KAI App - Login", 400, 500);

        controller = new LoginController();

        // form panel
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

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            User user = controller.login(email, password);

            if (user != null) {
                if (user instanceof Admin) {
                    new MenuAdminView().setVisible(true);
                } else {
                    new MenuUserView().setVisible(true);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterView().setVisible(true);
            dispose();
        });
    }
}
