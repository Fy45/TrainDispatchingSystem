import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 管理所有的列车，包括站点删除和添加
 */
public class EditTrains {
    //列名
    private String[] columnNames = {
            "TrainNum", "Status", "Routes", "Start Station", "Arrive0",
            "Arrive1", "Arrive2", "Arrive3", "Arrive4", "Arrive5",
            "Arrive6", "Arrive7", "Arrive8", "Arrive9", "End Station"
    };
    //存储列车信息
    public static TreeMap<String, ArrayList<String>> trainsInfo = new TreeMap<>();
    static {
        trainsInfo = Users.trainsInformation;
    }

    private JTable trainsTable;
    private TrainsTableModel trainsTableModel;
    private JPanel trainsPanel;

    public JPanel getTrainsPanel() {
        return trainsPanel;
    }

    public EditTrains() {
        trainsPanel = new JPanel(new BorderLayout());
        trainsTableModel = new TrainsTableModel();
        trainsTable = new JTable(trainsTableModel) {
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        trainsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        final JScrollPane scrollPane = new JScrollPane(trainsTable);
        trainsPanel.add(scrollPane, BorderLayout.CENTER);

        trainsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JPanel panel = new JPanel();
        trainsPanel.add(panel, BorderLayout.SOUTH);

        final JButton addButton = new JButton("add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //添加新的一行
                final int rowCount = trainsInfo.size();
                trainsTableModel.addRow("T" + rowCount, new ArrayList<String>() {
                    {
                        add("T" + rowCount);
                        for(int i = 1; i < 14; ++i) {
                            add("");
                        }
                    }
                });
            }
        });
        panel.add(addButton);

        final JButton delButton = new JButton("delete");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = trainsTable.getSelectedRow();
                //存在选中行
                if(selectedRow != -1) {
                    trainsTableModel.removeRow((String) trainsTableModel.getValueAt(selectedRow, 0));
                }
            }
        });
        panel.add(delButton);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("EditTrains");
        frame.setSize(1000, 400);
        frame.setContentPane(new EditTrains().getTrainsPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 内部类，用于处理底层数据
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
            return trainsInfo.size();
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
            for (Map.Entry<String, ArrayList<String>> entry : trainsInfo.entrySet()) {
                if(count == rowIndex && columnIndex < entry.getValue().size())
                    //再找到对应的列
                    return entry.getValue().get(columnIndex);
                ++count;
            }
            //没有找到，返回空值
            return null;
        }

        /**
         * 实现表格的编辑
         * @param obj
         * @param rowIndex
         * @param columnIndex
         */
        @Override
        public void setValueAt(Object obj, int rowIndex, int columnIndex) {
            if(0 < columnIndex)
            {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : trainsInfo.entrySet()) {
                    if(count == rowIndex) {
                        entry.getValue().set(columnIndex, (String) obj);
                        return;
                    }
                    count = count + 1;

                }
            }
            //对于作为主键的第一列要特别处理
            else
            {
                String oldTrainNum = (String) getValueAt(rowIndex, 0);
                String newTrainNum = (String) obj;
                final ArrayList<String> remove = trainsInfo.remove(oldTrainNum);
                remove.set(0, newTrainNum);
                trainsInfo.put(newTrainNum, remove);
                //车次名修改，则分配到该车的Driver中的信息也相应改变
                for (Map.Entry<String, ArrayList<String>> entry : EditDrivers.conductorsInfo.entrySet()) {
                    if(Objects.equals(entry.getValue().get(3), oldTrainNum)) {
                        entry.getValue().set(3, newTrainNum);
                    }
                }

            }
        }

        /**
         * 允许编辑
         * @param rowIndex
         * @param columnIndex
         * @return
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
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

        /**
         * 添加行
         */
        public void addRow(String key, ArrayList<String> value) {
            trainsInfo.put(key ,value);
            fireTableRowsInserted(0, trainsInfo.size() - 1);
        }

        /**
         * 删除行
         */
        public void removeRow(String key) {
            trainsInfo.remove(key);
            fireTableRowsDeleted(0, trainsInfo.size() - 1);
            //车次删除，则分配到该车的Driver中的信息也相应改变
            for (Map.Entry<String, ArrayList<String>> entry : EditDrivers.conductorsInfo.entrySet()) {
                if(Objects.equals(entry.getValue().get(3), key)) {
                    entry.getValue().set(3, "");
                }
            }
        }

    }

}
