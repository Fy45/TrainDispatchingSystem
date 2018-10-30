import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class EditRoutes {
    //列名
    private String[] columnNames =
            {"Name", "Start", "Stations1", "Stations2", "Stations3", "Stations4",
                    "Stations5", "Stations6", "Stations7", "Stations8", "End"};
    //储存路线信息
    public static TreeMap<String, ArrayList<String>> routesInfo = new TreeMap<>();
    static {
        routesInfo = Users.routesInformation;
    }
    private JTable routesTable;
    private RoutesTableModel routesTableModel; //路线模型

    public JPanel getRoutesPanel() {
        return routesPanel;
    }

    private JPanel routesPanel;

    public EditRoutes() {
        routesPanel = new JPanel(new BorderLayout());
        routesTableModel = new RoutesTableModel();
        routesTable = new JTable(routesTableModel){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        routesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        final JScrollPane scrollPane = new JScrollPane(routesTable);
        routesPanel.add(scrollPane, BorderLayout.CENTER);

        routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
        final JPanel panel = new JPanel();
        routesPanel.add(panel, BorderLayout.SOUTH);

        final JButton addButton = new JButton("add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int rowCount = routesInfo.size();
                routesTableModel.addRow("Temp" + rowCount, new ArrayList<String>(){
                    {
                        add("Temp" + rowCount);
                        for(int i = 0; i < 10; ++i) {
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
                int selectedRow = routesTable.getSelectedRow();
                if(selectedRow != -1) //存在选中行
                    routesTableModel.removeRow((String) routesTableModel.getValueAt(selectedRow, 0));
            }
        });
        panel.add(delButton);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("EditRoutes");
        frame.setContentPane(new EditRoutes().getRoutesPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * 内部类，用于处理底层数据
     */
    private class RoutesTableModel extends AbstractTableModel {
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
            return routesInfo.size();
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
            for (Map.Entry<String, ArrayList<String>> entry : routesInfo.entrySet()) {
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
            if(columnIndex > 0)
            {
                int count = 0;
                for (Map.Entry<String, ArrayList<String>> entry : routesInfo.entrySet()) {
                    if(count == rowIndex) {
                        entry.getValue().set(columnIndex, (String) obj);
                        return;
                    }
                    ++count;

                }
            }
            //对于作为主键的第一列要特别处理
            else
            {
                //保存已有的路线名
                String oldKey = (String) getValueAt(rowIndex, 0);
                String newKey = (String) obj;
                final ArrayList<String> remove = routesInfo.remove(oldKey);
                remove.set(0, newKey);
                routesInfo.put(newKey, remove);
                //Routes更名，需要将Trains中的相关数据一并更改
                for (Map.Entry<String, ArrayList<String>> entry : EditTrains.trainsInfo.entrySet()) {
                    if(Objects.equals(entry.getValue().get(2), oldKey)) {
                        entry.getValue().set(2, newKey);
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
            routesInfo.put(key ,value);
            fireTableRowsInserted(0, routesInfo.size() - 1);
        }

        /**
         * 删除行
         */
        public void removeRow(String key) {
            routesInfo.remove(key);
            fireTableRowsDeleted(0, routesInfo.size() - 1);
            //Routes删除，需要将Trains中的同名Routes置为空值
            for (Map.Entry<String, ArrayList<String>> entry : EditTrains.trainsInfo.entrySet()) {
                if(Objects.equals(entry.getValue().get(2), key)) {
                    entry.getValue().set(2, "");
                }
            }
        }

    }

}
