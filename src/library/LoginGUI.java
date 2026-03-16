package library;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Library Login");
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginButton);

        frame.add(panel);

        loginButton.addActionListener(e -> {

            String username = userField.getText();
            String password = new String(passField.getPassword());

            if(username.equals("admin") && password.equals("1234")){

                JOptionPane.showMessageDialog(frame,"Login Successful");

                frame.dispose();
                LibraryGUI.main(null);

            }else{

                JOptionPane.showMessageDialog(frame,"Invalid Login");

            }

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}