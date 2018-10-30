import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * 管理车长
 */
public class EditDrivers {
    //列名
    private String[] columnNames = {
            "Name", "Password", "Status", "Train Number"
    };

    //存储车长信息
    public static TreeMap<String, ArrayList<String>> conductorsInfo = new TreeMap<>();
    static {
        conductorsInfo = Users.driversInformation;
    }
    private JTable conductorsTable;
    private ConductorsTableModel conductorsTableModel;
    private JPanel conductorsPanel;
    private Users users;

    public JPanel getConductorsPanel() {
        return conductorsPanel;
    }

    public EditDrivers() {
        conductorsPanel = new JPanel(new BorderLayout());
        conductorsTableModel = new ConductorsTableModel();
        conductorsTable = new JTable(conductorsTableModel) {
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        conductorsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        final JScrollPane scrollPane = new JScrollPane(conductorsTable);
        conductorsPanel.add(scrollPane, BorderLayout.CENTER);

        conductorsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JPanel panel = new JPanel();
        conductorsPanel.add(panel, BorderLayout.SOUTH);

        final JButton addButton = new JButton("add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int count = conductorsInfo.size();
                conductorsTableModel.addRow("Temp" + count, new ArrayList<String>() {
                    {
                        add("Temp" + count);
                        add("");
                        add("");
                        add("");
                    }
                });
            }
        });
        panel.add(addButton);

        final JButton delButton = new JButton("delete");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = conductorsTable.getSelectedRow();
                if(selectedRow != -1) //存在选中行
                    conductorsTableModel.removeRow((String) conductorsTableModel.getValueAt(selectedRow, 0));
            }
        });
        panel.add(delButton);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("EditDrivers");
        frame.setContentPane(new EditDrivers().getConductorsPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 内部类，用于处理底层数据
     */
    private class ConductorsTableModel extends AbstractTableModel {
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
            return conductorsInfo.size();
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
            for (Map.Entry<String, ArrayList<String>> entry : conductorsInfo.entrySet()) {
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
            if(!(columnIndex <= 0))
            {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : conductorsInfo.entrySet()) {
                    if(count == rowIndex) {
                        entry.getValue().set(columnIndex, (String) obj);
                        //如果修改的是密码字段，则需同步更新Users中的信息
                        if(columnIndex == 1) {
                            Users.drivers.put(entry.getKey(), (String) obj);
                        }
                        return;
                    }
                    final int i = ++count;

                }

            }
            //对于作为主键的第一列要特别处理
            else
            {
                final ArrayList<String> remove = conductorsInfo.remove(getValueAt(rowIndex, 0));
                remove.set(0, (String) obj);
                conductorsInfo.put((String) obj, remove);
                //同步修改Users中的车长名
                String password = Users.drivers.remove(getValueAt(rowIndex, 0));
                Users.drivers.put((String) obj, password);
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
            conductorsInfo.put(key ,value);
            fireTableRowsInserted(0, conductorsInfo.size() - 1);
            //即时更新用户信息
            Users.drivers.put(key, value.get(1));
        }

        /**
         * 删除行
         */
        public void removeRow(String key) {
            conductorsInfo.remove(key);
            fireTableRowsDeleted(0, conductorsInfo.size() - 1);
            //即时更新用户信息
            Users.drivers.remove(key);
        }

    }
}
