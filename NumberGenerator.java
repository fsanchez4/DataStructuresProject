package lotteryProject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.Random;

class NumberGenerator {

    private static String url = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9247157?allowPublicKeyRetrieval=true&useSSL=false&requireSSL=false";
    private static String user = "sql9247157";
    private static String password = "ZY9rN5phwb";
    private static Random rand = new Random();
    private static Connection myConn;
    private static Statement myStmt;
    private static ResultSet myRs;
    static int[] mainNumbers = new int[6];
    static int money_ball;

    /*
    // MySQL DB Info
    Mega Millions table:  mm_table
    Powerball table:  powerball_table
    MM user quick picks table:  mm_quickpicks
    PB user quick picks table:  powerball_quickpicks
    Primary key (column):  id
    Columns:  id, date, num_1, num_2, num_3, num_4, num_5, money_ball, multiplier
    */
    
    static {
        try {
            myConn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static int powerBall() {

        money_ball = rand.nextInt(26) + 1;
        return money_ball;

    }

    static int megaBall() {

        money_ball = rand.nextInt(25) + 1;
        return money_ball;

    }

    int[] powerballNumGenerator() {

        int rNum;
        for (int i = 0; i < 5; i++) {
            rNum = rand.nextInt(69) + 1;
            while (match(mainNumbers, rNum)) {
                rNum = rand.nextInt(69) + 1;
            }

            mainNumbers[i]=rNum;
        }
        sort(mainNumbers);
        mainNumbers[5] = powerBall();
        return mainNumbers;
    }

    int[] megaMillionsNumGenerator() {

        int rNum;
        for (int i = 0; i < 5; i++) {
            rNum = rand.nextInt(70) + 1;
            while (match(mainNumbers, rNum)) {
                rNum = rand.nextInt(70) + 1;
            }

            mainNumbers[i]=rNum;
        }
        sort(mainNumbers);
        mainNumbers[5] = megaBall();
        return mainNumbers;
    }


    static boolean match(int[] a, int num) {
        for (int i = 0; i < a.length; i++) {
            if (num == a[i]) {
                return true;
            }
        }
        return false;
    }

    static void sort(int[] aNum){
        for(int i =0; i<aNum.length-2; i++){
            for(int j=i+1; j<aNum.length-1; j++){
                if(aNum[i]>aNum[j]) {
                    int temp = aNum[i];
                    aNum[i] = aNum[j];
                    aNum[j] = temp;
                }
            }
        }
    }

    static void printPBQuickPick(int[] mainNumbers) {
        int x;
        int size = mainNumbers.length;
        for (int i = 0; i < size; i++) {
            x = mainNumbers[i];
            System.out.print(x + " ");
        }
        System.out.print(powerBall());
    }

    static void printMMQuickPick(int[] mainNumbers) {
        int x;
        int size = mainNumbers.length;
        for (int i = 0; i < size; i++) {
            x = mainNumbers[i];
            System.out.print(x + " ");
        }
        System.out.print(megaBall());
    }

    static void dbConnect() {
        try {

            // Get a connection to database
            Connection myConn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection complete.");

        } catch (SQLException exc) {
            exc.printStackTrace();

        }
    }
    
    void storePowerballQuickPick(int[] arr, int num) {

        Date date = new Date();
        String DATE_FORMAT = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        try {
        	int id = num;
            int num_1 = arr[0];
            int num_2 = arr[1];
            int num_3 = arr[2];
            int num_4 = arr[3];
            int num_5 = arr[4];
            money_ball = arr[5];

            String sq1 = "INSERT INTO pb_quickpicks_table "
                    + "(id, date, num_1, num_2, num_3, num_4, num_5, money_ball)"
                    + " VALUES ('" + id + "', '" + sdf.format(date) + "', '" + num_1 + "', '" + num_2 + "', '" + num_3 + "', '" + num_4 + "', '"
                    + num_5 + "', '" + money_ball + "')";

            // 3. Execute SQL query
            myStmt = myConn.createStatement();
            myStmt.executeUpdate(sq1);

            System.out.println("Quick pick has been stored.");
            System.out.println();

            // 3a. Execute SQL Query (to show table data)
            myRs = myStmt.executeQuery("select * from pb_quickpicks_table");


            // 4. Process the result set
            while (myRs.next()) {

                // Database output
                System.out.println(myRs.getString("id") + " || Date: " + myRs.getString("date") +
                        " || Quick Pick: " + myRs.getString("num_1") + ", " + myRs.getString("num_2") +
                        ", " + myRs.getString("num_3") + ", " + myRs.getString("num_4") + ", " +
                        myRs.getString("num_5") + " || Money Ball: " + myRs.getString("money_ball"));

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
    
    static void mmNumberFrequency(int currentElement) {

        int x;
        int extra = currentElement;
        int many =0;
        for (int i = 0; i < 5; i++) {
            int times =i;
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM mm_table;");
                while (myRs.next()) {
                    // System.out.println(myRs.getInt("where"));
                    // x = myRs.getInt("num_1");
                    if (times == 0) {
                        x = myRs.getInt("num_1");
                        if (x == extra) {
                            many++;
                        }
                    } else if (times == 1) {
                        x = myRs.getInt("num_2");
                        if (x == extra) {
                            many++;
                        }
                    } else if (times == 2) {
                        x = myRs.getInt("num_3");
                        if (x == extra) {
                            many++;
                        }
                    } else if (times == 3) {
                        x = myRs.getInt("num_4");
                        if (x == extra) {
                            many++;
                        }
                    } else if (times == 4) {
                        x = myRs.getInt("num_5");
                        if (x == extra) {
                            many++;
                        }
                    }
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

            // SELECT num_5, COUNT(*) `return_value` FROM mm_table GROUP BY num_5 HAVING `return_value` > 1;
        }

        System.out.println(extra+ " appears: " + many + " times in the database");
        double percent = (many/881.0)*100.0;
        System.out.println("the percent of "+extra+ " showing up Lottery history is: "+df.format(percent)+ "%");
        System.out.println();
    }

//    int getLastId(){
//        //String URL="localhost:3306";
//        //String USERNAME="root";
//        //String PASSWORD="root";
//        //String DATABASE="demo";
//        
//        int currentid=0;
//        
//        try {
//            //Class.forName("com.mysql.jdbc.Driver");
//            //Connection con=DriverManager.getConnection("jdbc:mysql://"+ URL + "/" + DATABASE,USERNAME,PASSWORD);
//            
//            //PreparedStatement ps=myConn.prepareStatement("insert into record (name) values(?)",Statement.RETURN_GENERATED_KEYS);
//            //ps.setString(1,"Neeraj");
//            
//            //ps.executeUpdate();
//            PreparedStatement ps = myConn.prepareStatement("insert into record pb_quickpicks_table values(id)",Statement.RETURN_GENERATED_KEYS);
//            //ps.setString(1);
//            ps.executeUpdate();
//            
//            ResultSet rs = ps.getGeneratedKeys();
//            if(rs.next()){
//                currentid=rs.getInt(1);
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return currentid;    
//    }
}
