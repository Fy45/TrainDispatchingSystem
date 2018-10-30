import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class EditRoutes {
    //����
    private String[] columnNames =
            {"Name", "Start", "Stations1", "Stations2", "Stations3", "Stations4",
                    "Stations5", "Stations6", "Stations7", "Stations8", "End"};
    //����·����Ϣ
    public static TreeMap<String, ArrayList<String>> routesInfo = new TreeMap<>();
    static {
        routesInfo = Users.routesInformation;
    }
    private JTable routesTable;
    private RoutesTableModel routesTableModel; //·��ģ��

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

        routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //��ѡ
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
                if(selectedRow != -1) //����ѡ����
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
     * �ڲ��࣬���ڴ���ײ�����
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
            //���ҵ���Ӧ����
            int count = 0;
            for (Map.Entry<String, ArrayList<String>> entry : routesInfo.entrySet()) {
                if(count == rowIndex && columnIndex < entry.getValue().size())
                    //���ҵ���Ӧ����
                    return entry.getValue().get(columnIndex);
                ++count;
            }
            //û���ҵ������ؿ�ֵ
            return null;
        }

        /**
         * ʵ�ֱ��ı༭
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
            //������Ϊ�����ĵ�һ��Ҫ�ر���
            else
            {
                //�������е�·����
                String oldKey = (String) getValueAt(rowIndex, 0);
                String newKey = (String) obj;
                final ArrayList<String> remove = routesInfo.remove(oldKey);
                remove.set(0, newKey);
                routesInfo.put(newKey, remove);
                //Routes��������Ҫ��Trains�е��������һ������
                for (Map.Entry<String, ArrayList<String>> entry : EditTrains.trainsInfo.entrySet()) {
                    if(Objects.equals(entry.getValue().get(2), oldKey)) {
                        entry.getValue().set(2, newKey);
                    }
                }
            }
        }

        /**
         * ����༭
         * @param rowIndex
         * @param columnIndex
         * @return
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
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

        /**
         * �����
         */
        public void addRow(String key, ArrayList<String> value) {
            routesInfo.put(key ,value);
            fireTableRowsInserted(0, routesInfo.size() - 1);
        }

        /**
         * ɾ����
         */
        public void removeRow(String key) {
            routesInfo.remove(key);
            fireTableRowsDeleted(0, routesInfo.size() - 1);
            //Routesɾ������Ҫ��Trains�е�ͬ��Routes��Ϊ��ֵ
            for (Map.Entry<String, ArrayList<String>> entry : EditTrains.trainsInfo.entrySet()) {
                if(Objects.equals(entry.getValue().get(2), key)) {
                    entry.getValue().set(2, "");
                }
            }
        }

    }

}
