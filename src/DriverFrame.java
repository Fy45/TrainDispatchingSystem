import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class DriverFrame extends JFrame{
    private JPanel driver;
    private JTextField driverStatus;
    private JTextField trainNumber;
    private JComboBox driverOperation;
    private JButton OKButton;
    private JLabel driveName;

    public DriverFrame(String title, final String name) {
        // do something different
        setTitle(title);

        driveName.setText(name);
        if(EditDrivers.conductorsInfo.containsKey(name)) {
            //"Name", "Password", "Status", "Train Number"
            driverStatus.setText(EditDrivers.conductorsInfo.get(name).get(2));
            trainNumber.setText(EditDrivers.conductorsInfo.get(name).get(3));
        }

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(trainNumber.getText());

                String trainStatus = driverOperation.getSelectedItem().toString();
                System.out.println(trainStatus);

                if(Objects.equals(trainStatus, "Start")) {
                    EditTrains.trainsInfo.get(trainNumber.getText()).set(1, "Run");
                    EditDrivers.conductorsInfo.get(driveName.getText()).set(2, "Run");
                }

                if(Objects.equals(trainStatus, "Stop")){
                    EditTrains.trainsInfo.get(trainNumber.getText()).set(1, "Stop");
                    EditDrivers.conductorsInfo.get(driveName.getText()).set(2, "Free");
                }


                driverStatus.setText(EditDrivers.conductorsInfo.get(name).get(2));
            }
        });

        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                Users.routesInformation = EditRoutes.routesInfo;
                Users.driversInformation = EditDrivers.conductorsInfo;
                Users.saveInformation();
                super.windowClosing(e);
            }
        });
    }

    {
        driver = new JPanel();
        driver.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setText("Status : ");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 2, 0, 0);
        driver.add(label1, gbc);
        driverStatus = new JTextField();
        driverStatus.setEditable(false);
        driverStatus.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 0, 2, 0);
        driver.add(driverStatus, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Train Number : ");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        driver.add(label2, gbc);
        trainNumber = new JTextField();
        trainNumber.setEditable(false);
        trainNumber.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 0, 2, 0);
        driver.add(trainNumber, gbc);
        driverOperation = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Start");
        defaultComboBoxModel1.addElement("Stop");
        driverOperation.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        driver.add(driverOperation, gbc);
        OKButton = new JButton();
        OKButton.setText("OK");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        driver.add(OKButton, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Name : ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 2, 0, 0);
        driver.add(label3, gbc);
        driveName = new JLabel();
        driveName.setPreferredSize(new Dimension(100, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        driver.add(driveName, gbc);

        setContentPane(driver);
    }

    public static void main(String[] args) {
        DriverFrame driver = new DriverFrame("DriverFrame Train Journey Management System", "Jack");
        driver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        driver.pack();
        driver.setVisible(true);
    }
}

