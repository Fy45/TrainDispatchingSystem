import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by hhd09_000 on 2016/5/13.
 */
public class DriverInfo {
    private String[] columnNames = {
            "Name", "Password", "Status"
    };

    private JTable driversTable;
    private DriversTableModel driversTableModel;
    private JPanel driversPanel;

    public JPanel getDriversPanel() {
        return driversPanel;
    }

    public DriverInfo() {
        driversPanel = new JPanel(new BorderLayout());
        driversTableModel = new DriversTableModel();
        driversTable = new JTable(driversTableModel) {
            @Override
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }


            public boolean isCellEditable(int r, int c)
            {
                return false;
            }
        };
        driversTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        driversTable.setRowSelectionAllowed(false);

        final JScrollPane scrollPane = new JScrollPane(driversTable);
        driversPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Drivers Information");
        frame.setContentPane(new DriverInfo().getDriversPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * �ڲ��࣬���ڴ���ײ�����
     * �˴�������EditDrivers�й�ͨ
     */
    private class DriversTableModel extends AbstractTableModel {
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
            return EditDrivers.conductorsInfo.size();
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
            for (Map.Entry<String, ArrayList<String>> entry : EditDrivers.conductorsInfo.entrySet()) {
                if(count == rowIndex && columnIndex < entry.getValue().size())
                    //���ҵ���Ӧ����
                    return entry.getValue().get(columnIndex);
                ++count;
            }
            //û���ҵ������ؿ�ֵ
            return null;
        }

        /**
         * �ñ��Ϊֻ�������ɱ༭
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
         * @param columnIndex
         * @return
         */
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

    }
}
