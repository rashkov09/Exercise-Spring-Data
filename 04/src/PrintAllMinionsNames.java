import test.DbHandler;
import test.Handle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class PrintAllMinionsNames {
    public static void main(String[] args) {
        try {
            ArrayDeque<String> minionNames = getMinionNames();
            while (!minionNames.isEmpty()){
                System.out.println(minionNames.pollFirst());
                System.out.println(minionNames.pollLast());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static ArrayDeque<String> getMinionNames() throws SQLException {
        Handle handler = new DbHandler("minions_db");
        handler.setStatement("SELECT m.`name` FROM minions AS m");
        ResultSet rs = handler.execute();
        ArrayDeque<String> names = new ArrayDeque<>();
        while (rs.next()){
            names.add(rs.getString("name"));
        }
        return names;
    }
}
