import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashMap;

public class AdminFrame extends JFrame {
    private JMenuBar mb = new JMenuBar();
    private JMenu[] menus = {
            new JMenu("System"), new JMenu("View"),
            new JMenu("Edit"), new JMenu("Control")
    };

    private HashMap<String, JMenuItem> items = new HashMap<>();

    {
        items.put("Initialize the Timetable", new JMenuItem("Initialize the Timetable"));
        items.put("Train Information", new JMenuItem("Train Information"));
        items.put("Driver Information", new JMenuItem("Driver Information"));
        items.put("Edit Routes", new JMenuItem("Edit Routes"));
        items.put("Edit Trains", new JMenuItem("Edit Trains"));
        items.put("Edit Drivers", new JMenuItem("Edit Drivers"));
        items.put("Control Trains", new JMenuItem("Control Trains"));

    }
    public AdminFrame(String title) {
        setTitle(title);
        setPreferredSize(new Dimension(900, 400));
        menus[0].add(items.get("Initialize the Timetable"));
        menus[1].add(items.get("Train Information"));
        menus[1].add(items.get("Driver Information"));
        menus[2].add(items.get("Edit Routes"));
        menus[2].add(items.get("Edit Trains"));
        menus[2].add(items.get("Edit Drivers"));
        menus[3].add(items.get("Control Trains"));
        for(JMenu menu : menus) {
            mb.add(menu);
        }
        setJMenuBar(mb);

        //为每个菜单项添加事件
        items.get("Initialize the Timetable").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser("timeTable.csv");
                int rVal = c.showOpenDialog(AdminFrame.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    File file = c.getSelectedFile();
                    //解析时刻表文件
                    TimeTable.parse(file);

                    //打开成功则显示时刻表
                    JPanel contentPane = (JPanel) getContentPane();
                    contentPane.removeAll();
                    contentPane.add(new TrainInfoTourist().getTrainsPanel());
                    contentPane.revalidate();
                    contentPane.repaint();
                } else {
                	 //打开失败则什么都不显示
                    JPanel contentPane = (JPanel) getContentPane();
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                }

                System.out.println("Initialize the Timetable");
            }
        });
        items.get("Train Information").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new TrainInfo().getTrainsPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Train Information");
            }
        });
        items.get("Driver Information").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new DriverInfo().getDriversPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Driver Information");
            }
        });
        items.get("Edit Routes").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new EditRoutes().getRoutesPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Edit Routes");
            }
        });
        items.get("Edit Trains").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new EditTrains().getTrainsPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Edit Trains");
            }
        });
        items.get("Edit Drivers").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new EditDrivers().getConductorsPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Edit Drivers");
            }
        });
        items.get("Control Trains").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new ControlTrains().getTrainsPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Control Trains");
            }
        });

        //窗口关闭时保存修改后的车长信息
        this.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
            	  //将车长信息存入文件
                Users.save();
                Users.trainsInformation = EditTrains.trainsInfo;
                Users.driversInformation = EditDrivers.conductorsInfo;
                Users.routesInformation = EditRoutes.routesInfo;
                Users.saveInformation();
                super.windowClosing(e);
            }
        });


    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminFrame admin = new AdminFrame("Admin Train Journey Management System");
                admin.setContentPane(admin.getContentPane());
                admin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                admin.pack();
                admin.setVisible(true);
            }
        });
    }
}

