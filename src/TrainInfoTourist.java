import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * չʾ���ο͵�ʱ�̱�
 */
public class TrainInfoTourist {
    //����
    private String[] columnNames = {
            "TrainNum", "Status", "Routes", "Start Station", "Arrive0",
            "Arrive1", "Arrive2", "Arrive3", "Arrive4", "Arrive5",
            "Arrive6", "Arrive7", "Arrive8", "Arrive9", "End Station"
    };

    private JTable trainsTable;
    private TrainsTableModel trainsTableModel;
    private JPanel trainsPanel;

    public JPanel getTrainsPanel() {
        return trainsPanel;
    }

    public TrainInfoTourist() {
        trainsPanel = new JPanel(new BorderLayout());
        trainsTableModel = new TrainsTableModel();
        trainsTable = new JTable(trainsTableModel) {
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        trainsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        trainsTable.setRowSelectionAllowed(false);

        final JScrollPane scrollPane = new JScrollPane(trainsTable);
        trainsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("EditTrains Information");
        frame.setContentPane(new TrainInfoTourist().getTrainsPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * ���ģ�ͣ����ڲ��ݵײ����ݣ����ָ�JTable
     */
    private class TrainsTableModel extends AbstractTableModel {
        /**
         * Returns the number of rows in the model. A
         * <code>JTable</code> uses this method to determine how many rows it
         * should display.  This method should be quick, as it
         * is called frequently during rendering.
         *
         * @return the number of rows in the model
         * @see #getColumnCount
         */
        @Override
        public int getRowCount() {
            return EditTrains.trainsInfo.size();
        }

        /**
         * Returns the number of columns in the model. A
         * <code>JTable</code> uses this method to determine how many columns it
         * should create and display by default.
         *
         * @return the number of columns in the model
         * @see #getRowCount
         */
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * Returns the value for the cell at <code>columnIndex</code> and
         * <code>rowIndex</code>.
         *
         * @param rowIndex    the row whose value is to be queried
         * @param columnIndex the column whose value is to be queried
         * @return the value Object at the specified cell
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            //���ҵ���Ӧ����
            int count = 0;
            for (Map.Entry<String, ArrayList<String>> entry : EditTrains.trainsInfo.entrySet()) {
                if (count == rowIndex && columnIndex < entry.getValue().size())
                    //���ҵ���Ӧ����
                    return entry.getValue().get(columnIndex);
                ++count;
            }
            //û���ҵ������ؿ�ֵ
            return null;
        }

        /**
         * ��ֱֹ�ӱ༭ʱ�̱�
         *
         * @param rowIndex
         * @param columnIndex
         * @return
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        /**
         * ��������
         *
         * @param columnIndex
         * @return
         */
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
    }


}
