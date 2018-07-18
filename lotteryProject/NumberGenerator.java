package lotteryProject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.Random;
import java.text.DecimalFormat;

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
    static DecimalFormat df = new DecimalFormat("#.#");

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

            // 3a. Execute SQL Query (to show table data)
            myRs = myStmt.executeQuery("select * from pb_quickpicks_table");

        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
    
    void storeMegaMillionsQuickPick(int[] arr, int num) {

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

            String sq1 = "INSERT INTO mm_quickpicks_table "
                    + "(id, date, num_1, num_2, num_3, num_4, num_5, money_ball)"
                    + " VALUES ('" + id + "', '" + sdf.format(date) + "', '" + num_1 + "', '" + num_2 + "', '" + num_3 + "', '" + num_4 + "', '"
                    + num_5 + "', '" + money_ball + "')";

            // 3. Execute SQL query
            myStmt = myConn.createStatement();
            myStmt.executeUpdate(sq1);

            // 3a. Execute SQL Query (to show table data)
            myRs = myStmt.executeQuery("select * from pb_quickpicks_table");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    void pbQuickPickReset() {
      //id = 0;
      try {
          Statement myStmt = myConn.createStatement();
          String sq1 = "TRUNCATE pb_quickpicks_table;";
          myStmt.executeUpdate(sq1);
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }
    
    void mmQuickPickReset() {
      //id = 0;
      try {
          Statement myStmt = myConn.createStatement();
          String sq1 = "TRUNCATE mm_quickpicks_table;";
          myStmt.executeUpdate(sq1);
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }

    int getmmLastId(){
        int currentid=0;
        
        try {
            Statement myStmt = myConn.createStatement();
            String sq1 = ("SELECT COUNT(*) FROM mm_quickpicks_table");
            myRs = myStmt.executeQuery(sq1);
            while (myRs.next()){
            int x = myRs.getInt("COUNT(*)");   
            currentid = x;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return currentid;    
    }
    
    int getpbLastId(){
        int currentid=0;
        
        try {
            Statement myStmt = myConn.createStatement();
            String sq1 = ("SELECT COUNT(*) FROM pb_quickpicks_table");
            myRs = myStmt.executeQuery(sq1);
            while (myRs.next()){
            int x = myRs.getInt("COUNT(*)");
            currentid = x;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return currentid;    
    }
    
    String mmNumberFrequency(int currentElement) {

        int x;
        int extra = currentElement;
        int many =0;
        for (int i = 0; i < 5; i++) {
            int times =i;
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM mm_table;");
                while (myRs.next()) {
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
        }
        double percent = (many/881.0)*100.0;
        return df.format(percent) +"%";
    }
    
    String pbNumberFrequency(int currentElement) {

        int x;
        int extra = currentElement;
        int many =0;
        for (int i = 0; i < 5; i++) {
            int times =i;
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM powerball_table;");
                while (myRs.next()) {
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
        }
        double percent = (many/880.0)*100.0;
        return df.format(percent) +"%";
    }
    
    String mmPowerFrequency(int currentElement) {
    	int x;
        int extra = currentElement;
        int many =0;
        for (int i = 0; i < 5; i++) {
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM mm_table;");
                while (myRs.next()) {
                	x = myRs.getInt("money_ball");
                	if (x == extra) {
                		many++;
                	}
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        double percent = (many/881.0)*100.0;
        return df.format(percent) +"%";
    }
    
    String pbPowerFrequency(int currentElement) {
    	int x;
        int extra = currentElement;
        int many =0;
        for (int i = 0; i < 5; i++) {
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("SELECT * FROM powerball_table;");
                while (myRs.next()) {
                	x = myRs.getInt("money_ball");
                	if (x == extra) {
                		many++;
                	}
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        double percent = (many/880.0)*100.0;
        return df.format(percent) +"%";
    }
    
//    String pbHistory(int id) {
//    	int x = id-1;
//    	String str = null;
//    	try{
//    		myStmt = myConn.createStatement(); 
//    		myRs = myStmt.executeQuery("SELECT 'num_1','num_2','num_3','num_4','num_5','money_ball' FROM pb_quickpicks_table LIMIT 0,1");
//    		if (myRs.next()) {
//    			str = myRs.getInt("num_1") +" "+ myRs.getInt("num_2") +" "+ myRs.getInt("num_3") +" "+ myRs.getInt("num_4") +
//    					" "+ myRs.getInt("num_5") +" "+ myRs.getInt("money_ball");
//    			return str;
//    		}
//    	} catch (SQLException exc) {
//    		exc.printStackTrace();
//    		return str;
//    	}
//    	return "NO";
//    }
//    
//    String mmHistory(int id) {
//    	int x = id-1;
//    	try{
//    		String str;
//    		myStmt = myConn.createStatement(); 
//    		myRs = myStmt.executeQuery("SELECT 'num_1','num_2','num_3','num_4','num_5','money_ball' FROM mm_quickpicks_table LIMIT '" +x+ "',1;");
//    		while (myRs.next()) {
//    			str = myRs.getInt("num_1") +" "+ myRs.getInt("num_2") +" "+ myRs.getInt("num_3") +" "+ myRs.getInt("num_4") +
//    					" "+ myRs.getInt("num_5") +" "+ myRs.getInt("money_ball");
//    		return str;
//    		}
//    	} catch (SQLException exc) {
//    		exc.printStackTrace();
//    		return "null";
//    	}
//    	return "NO";
//    }
}
