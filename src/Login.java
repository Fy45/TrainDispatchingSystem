import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

public class Login extends JFrame {
    private JTextField userName;
    private JLabel userLabel;
    private JLabel pwdLabel;
    private JPasswordField userNamePasswordField;
    private JPanel loginPanel;
    private JRadioButton adminRadioButton;
    private JRadioButton driverRadioButton;
    private JRadioButton passengerRadioButton;
    private JButton loginButton;
    private Users user;

    public Login(String title) {
        setTitle(title);

        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        pwdLabel = new JLabel();
        pwdLabel.setFont(new Font("Microsoft YaHei UI", pwdLabel.getFont().getStyle(), pwdLabel.getFont().getSize()));
        pwdLabel.setRequestFocusEnabled(false);
        pwdLabel.setText("Password : ");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        loginPanel.add(pwdLabel, gbc);

        userNamePasswordField = new JPasswordField();
        userNamePasswordField.setPreferredSize(new Dimension(190, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 2, 5);
        loginPanel.add(userNamePasswordField, gbc);

        adminRadioButton = new JRadioButton();
        adminRadioButton.setFont(new Font("Microsoft YaHei UI", adminRadioButton.getFont().getStyle(), adminRadioButton.getFont().getSize()));
        adminRadioButton.setText("Admin");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        loginPanel.add(adminRadioButton, gbc);

        driverRadioButton = new JRadioButton();
        driverRadioButton.setFont(new Font("Microsoft YaHei UI", driverRadioButton.getFont().getStyle(), driverRadioButton.getFont().getSize()));
        driverRadioButton.setText("Driver");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        loginPanel.add(driverRadioButton, gbc);

        passengerRadioButton = new JRadioButton();
        passengerRadioButton.setActionCommand("TouristFrame");
        passengerRadioButton.setFont(new Font("Microsoft YaHei UI", passengerRadioButton.getFont().getStyle(), passengerRadioButton.getFont().getSize()));
        passengerRadioButton.setSelected(true);
        passengerRadioButton.setText("Passenger");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 5);
        loginPanel.add(passengerRadioButton, gbc);

        userLabel = new JLabel();
        userLabel.setFont(new Font("Microsoft YaHei UI", userLabel.getFont().getStyle(), userLabel.getFont().getSize()));
        userLabel.setText("UserName : ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 2, 5);
        loginPanel.add(userLabel, gbc);

        userName = new JTextField();
        userName.setFont(new Font("Microsoft YaHei UI", userName.getFont().getStyle(), userName.getFont().getSize()));
        userName.setPreferredSize(new Dimension(190, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 2, 5);
        loginPanel.add(userName, gbc);

        loginButton = new JButton();
        loginButton.setFont(new Font("Microsoft YaHei UI", loginButton.getFont().getStyle(), loginButton.getFont().getSize()));
        loginButton.setText("Login");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 5, 5);
        loginPanel.add(loginButton, gbc);
        userLabel.setLabelFor(userNamePasswordField);

        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(adminRadioButton);
        buttonGroup.add(driverRadioButton);
        buttonGroup.add(passengerRadioButton);

        setContentPane(loginPanel);

        loginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //check user's type
                //获取用户名和密码
                String userStr = userName.getText().trim();
                String pwdStr = userNamePasswordField.getText().trim();
                if(adminRadioButton.isSelected()) {
                    //检查用户名和密码
                    if(Objects.equals(Users.admins.get(userStr), pwdStr)) {
                        AdminFrame admin = new AdminFrame("Admin Train Journey Management System");
                        admin.setContentPane(admin.getContentPane());
                        admin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        admin.pack();
                        admin.setVisible(true);
                        //hide current window
                        setVisible(false);
                    }
                } else if(driverRadioButton.isSelected()) {
                    //检查用户名和密码
                    if(Objects.equals(Users.drivers.get(userStr), pwdStr)) {
                        DriverFrame driver = new DriverFrame("Driver Train Journey Management System", userStr);
                        driver.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        driver.pack();
                        driver.setVisible(true);
                        //hide current window
                        setVisible(false);
                    }
                } else if(passengerRadioButton.isSelected()) {
                    TouristFrame tr = new TouristFrame("Tourist Train Journey Management System");
                    tr.setContentPane(tr.getContentPane());
                    tr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    tr.pack();
                    tr.setVisible(true);
                    //hide current window
                    setVisible(false);
                }


            }
        });

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login logFrame = new Login("Login");
                logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                logFrame.setResizable(false);
                logFrame.pack();
                logFrame.setVisible(true);
            }
        });
    }
}
