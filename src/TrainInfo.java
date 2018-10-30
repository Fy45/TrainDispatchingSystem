import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 展示列车信息
 */
public class TrainInfo {
    //列名
    private String[] columnNames = {
            "TrainNum", "TrainStatus"
    };

    private JTable trainsTable;
    private TrainsTableModel trainsTableModel;
    private JPanel trainsPanel;

    public JPanel getTrainsPanel() {
        return trainsPanel;
    }

    public TrainInfo() {
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
        JFrame frame = new JFrame("Trains Information");
        frame.setContentPane(new TrainInfo().getTrainsPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 用于操纵底层数据的内部类，数据与EditTrain中共通
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
            //先找到对应的行
            int count = 0;
            for (Map.Entry<String, ArrayList<String>> entry : EditTrains.trainsInfo.entrySet()) {
                if(count == rowIndex  && columnIndex < entry.getValue().size())
                    //再找到对应的列
                    return entry.getValue().get(columnIndex);
                ++count;
            }
            //没有找到，返回空值
            return null;
        }

        /**
         * 该表格为只读，不可编辑
         * @param rowIndex
         * @param columnIndex
         * @return
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        /**
         * 返回列名
         * @param columnIndex
         * @return
         */
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

    }
}
