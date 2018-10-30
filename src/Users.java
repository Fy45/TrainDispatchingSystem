import java.io.*;
import java.util.*;

/**
 * 将所有信息保存到文件中
 */
public class Users{
    public static HashMap<String, String> admins = new HashMap<>();
    public static HashMap<String, String> drivers = new HashMap<>();
    public static TreeMap<String, ArrayList<String>> routesInformation = new TreeMap<>();
    public static TreeMap<String, ArrayList<String>> trainsInformation = new TreeMap<>();
    public static TreeMap<String, ArrayList<String>> driversInformation = new TreeMap<>();

    static
    {
        run("admins");
        run("drivers");
        getInformation("routesInformation");
        getInformation("trainsInformation");
        getInformation("driversInformation");
    }

    /**
     * 初始化管理员和车长信息
      * @param option
     */
    private static void run(String option) {
        File file = new File(option);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedReader br = null;
        String splitBy = ",";
        String line;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                //用逗号作为分隔符
                String[] info = line.split(splitBy);

                if(Objects.equals(option, "admins") && info.length == 2) {
                    admins.put(info[0], info[1]);
                }

                if(Objects.equals(option, "drivers") && info.length == 2) {
                    drivers.put(info[0], info[1]);
                }


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

    /**
     * 从文件中获取路线、车次和车长的信息
     */
    private static void getInformation(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedReader br = null;
        String splitBy = ",";
        String line;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                //用逗号作为分隔符
                final String[] info = line.split(splitBy);

                if(Objects.equals(fileName, "routesInformation")) {
                    routesInformation.put(info[0], new ArrayList<String>(){
                        {
                            for (String anInfo : info) {
                                add(anInfo);
                            }
                        }
                    });
                }

                if(Objects.equals(fileName, "trainsInformation")) {
                    trainsInformation.put(info[0], new ArrayList<String>() {
                        {
                            for (String anInfo : info) {
                                add(anInfo);
                            }
                        }
                    });
                }

                if(Objects.equals(fileName, "driversInformation")) {
                    driversInformation.put(info[0], new ArrayList<String>(){
                        {
                            for (String anInfo : info) {
                                add(anInfo);
                            }
                        }
                    });
                }
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

    /**
     * 将管理员和车长信息保存到文件
     */
    public static void save(){
        try {
            PrintWriter adminOut = new PrintWriter(
                    new BufferedWriter(new FileWriter("admins"))
            );
            for (Map.Entry<String, String> entry : admins.entrySet()) {
                adminOut.println(entry.getKey() + "," + entry.getValue());
            }
            adminOut.close();

            PrintWriter driverOut = new PrintWriter(
                    new BufferedWriter(new FileWriter("drivers"))
            );
            for (Map.Entry<String, String> entry : drivers.entrySet()) {
                driverOut.println(entry.getKey() + "," + entry.getValue());
            }
            driverOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将路线、车长和列车信息保存到文件
     * @return
     */
    public static void saveInformation() {
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new FileWriter("routesInformation"))
            );
            for (Map.Entry<String, ArrayList<String>> entry : routesInformation.entrySet()) {
                //将ArrayList中的字符串用逗号连接，以便写入文件
                String line = "";
                for(String s : entry.getValue()) {
                    line += s + ",";
                }
                out.println(line);
            }
            out.close();

            out = new PrintWriter(
                    new BufferedWriter(new FileWriter("trainsInformation"))
            );
            for (Map.Entry<String, ArrayList<String>> entry : trainsInformation.entrySet()) {
                //将ArrayList中的字符串用逗号连接，以便写入文件
                String line;
                line = "";
                for(String s : entry.getValue()) {
                    line += s + ",";
                }
                out.println(line);
            }
            out.close();

            out = new PrintWriter(
                    new BufferedWriter(new FileWriter("driversInformation"))
            );
            for (Map.Entry<String, ArrayList<String>> entry : driversInformation.entrySet()) {
                String line = "";
                for(String s : entry.getValue()) {
                    line = line + (s + ",");
                }
                out.println(line);
            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return admins.toString() +"\n" + drivers.toString();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Users users = new Users();
        System.out.println(users);

        admins.put("AdminFrame", "123456");
        drivers.put("Jack", "123");

        //read the User Info from the file
        save();

    }
}
