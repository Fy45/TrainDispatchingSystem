import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 用于初始化时刻表
 */
public class TimeTable {

    public static void parse(File file) {
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {

                // 用逗号作为分隔符
                final String[] data = line.split(cvsSplitBy);
                // 提取每一行的信息
                final String trainNum = data[0];
                final String route = data[1];
                final String driver = data[2];
                // 初始化列车信息
                EditTrains.trainsInfo.put(trainNum, new ArrayList<String>() {
                    {
                        add(trainNum);
                        add(""); //列车初始状态为空
                        add(route);
                        for(int i = 3; i < data.length; ++i) {
                            add(data[i]);
                        }
                    }
                });
                // 初始化路线信息
                EditRoutes.routesInfo.put(route, new ArrayList<String>() {
                    {
                        add(route);
                        for(int i = 0; i < 10; ++i) {
                            add(""); //路线上所有10个站点初始值为空
                        }
                    }
                });
                // 初始化车长信息
                EditDrivers.conductorsInfo.put(driver, new ArrayList<String>() {
                    {
                        add(driver);
                        add(""); //初始状态下车长密码为空
                        add(""); //初始状态下车长状态为空
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
