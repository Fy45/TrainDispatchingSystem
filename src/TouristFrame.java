import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TouristFrame extends JFrame {
    private JMenu[] menus = {
            new JMenu("View")
    };
    private JMenuItem[] items = {
            new JMenuItem("Train Information")
    };

    public TouristFrame(String title) {
        setTitle(title);
        setPreferredSize(new Dimension(1000, 500));

        menus[0].add(items[0]);
        items[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPane = (JPanel) getContentPane();
                contentPane.removeAll();
                contentPane.add(new TrainInfoTourist().getTrainsPanel());
                contentPane.revalidate();
                contentPane.repaint();
                System.out.println("Tourist Information");
            }
        });

        JMenuBar mb = new JMenuBar();
        mb.add(menus[0]);
        setJMenuBar(mb);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TouristFrame tr = new TouristFrame("Tourist Train Journey Management System");
                tr.setContentPane(tr.getContentPane());
                tr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                tr.pack();
                tr.setVisible(true);
            }
        });

    }
}
