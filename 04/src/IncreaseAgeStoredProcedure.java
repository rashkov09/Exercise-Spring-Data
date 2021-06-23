import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Handle handler = new DbHandler("minions_db");
        int minionId = Integer.parseInt(sc.nextLine());

        try {
            handler.setCallableStatement("CALL `usp_get_older`(?)");
            handler.getCallableStatement().setInt(1, minionId);
            ResultSet rs = handler.executeCall();
            handler.setStatement("SELECT m.`name`, m.age \n" +
                    "FROM\tminions AS m\n" +
                    "WHERE\tm.id =?; ");
            handler.getStatement().setInt(1,minionId);
            ResultSet rsToPrint = handler.execute();
            while (rsToPrint.next()){
                System.out.printf("%s %d%n",rsToPrint.getString("name"),rsToPrint.getInt("age"));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
