import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ���ڳ�ʼ��ʱ�̱�
 */
public class TimeTable {

    public static void parse(File file) {
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {

                // �ö�����Ϊ�ָ���
                final String[] data = line.split(cvsSplitBy);
                // ��ȡÿһ�е���Ϣ
                final String trainNum = data[0];
                final String route = data[1];
                final String driver = data[2];
                // ��ʼ���г���Ϣ
                EditTrains.trainsInfo.put(trainNum, new ArrayList<String>() {
                    {
                        add(trainNum);
                        add(""); //�г���ʼ״̬Ϊ��
                        add(route);
                        for(int i = 3; i < data.length; ++i) {
                            add(data[i]);
                        }
                    }
                });
                // ��ʼ��·����Ϣ
                EditRoutes.routesInfo.put(route, new ArrayList<String>() {
                    {
                        add(route);
                        for(int i = 0; i < 10; ++i) {
                            add(""); //·��������10��վ���ʼֵΪ��
                        }
                    }
                });
                // ��ʼ��������Ϣ
                EditDrivers.conductorsInfo.put(driver, new ArrayList<String>() {
                    {
                        add(driver);
                        add(""); //��ʼ״̬�³�������Ϊ��
                        add(""); //��ʼ״̬�³���״̬Ϊ��
                        add(trainNum);
                    }
                });

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("timeTable.csv");
        TimeTable.parse(file);
        System.out.println("Done");
    }
}
