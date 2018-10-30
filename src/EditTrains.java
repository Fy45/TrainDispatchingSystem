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
 * �������е��г�������վ��ɾ�������
 */
public class EditTrains {
    //����
    private String[] columnNames = {
            "TrainNum", "Status", "Routes", "Start Station", "Arrive0",
            "Arrive1", "Arrive2", "Arrive3", "Arrive4", "Arrive5",
            "Arrive6", "Arrive7", "Arrive8", "Arrive9", "End Station"
    };
    //�洢�г���Ϣ
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
                //����µ�һ��
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
                //����ѡ����
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
     * �ڲ��࣬���ڴ���ײ�����
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
            //���ҵ���Ӧ����
            int count = 0;
            for (Map.Entry<String, ArrayList<String>> entry : trainsInfo.entrySet()) {
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
            //������Ϊ�����ĵ�һ��Ҫ�ر���
            else
            {
                String oldTrainNum = (String) getValueAt(rowIndex, 0);
                String newTrainNum = (String) obj;
                final ArrayList<String> remove = trainsInfo.remove(oldTrainNum);
                remove.set(0, newTrainNum);
                trainsInfo.put(newTrainNum, remove);
                //�������޸ģ�����䵽�ó���Driver�е���ϢҲ��Ӧ�ı�
                for (Map.Entry<String, ArrayList<String>> entry : EditDrivers.conductorsInfo.entrySet()) {
                    if(Objects.equals(entry.getValue().get(3), oldTrainNum)) {
                        entry.getValue().set(3, newTrainNum);
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
            trainsInfo.put(key ,value);
            fireTableRowsInserted(0, trainsInfo.size() - 1);
        }

        /**
         * ɾ����
         */
        public void removeRow(String key) {
            trainsInfo.remove(key);
            fireTableRowsDeleted(0, trainsInfo.size() - 1);
            //����ɾ��������䵽�ó���Driver�е���ϢҲ��Ӧ�ı�
            for (Map.Entry<String, ArrayList<String>> entry : EditDrivers.conductorsInfo.entrySet()) {
                if(Objects.equals(entry.getValue().get(3), key)) {
                    entry.getValue().set(3, "");
                }
            }
        }

    }

}
