import test.DbHandler;
import test.Handle;

import java.sql.SQLException;
import java.util.Scanner;

public class AddMinion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String minionName = "";
        int minionAge = 0;
        String minionTown = "";
        String villainName = "";
        for (int i = 0; i < 2; i++) {
            String[] input = sc.nextLine().split(" ");
            if (i == 0) {
                minionName = input[1];
                minionAge = Integer.parseInt(input[2]);
                minionTown = input[3];
            } else {
                villainName = input[1];
            }
        }
        Handle handler = new DbHandler("minions_db");
        try {
            addMinionIfNotExists(minionName, minionAge, handler);
            addTownIfNotExists(minionTown, handler);
            addVillainIfNotExists(villainName, handler);
            addMinionToVillain(minionName, villainName, handler);
            handler.commit();
        }catch (SQLException e){
            handler.rollback();
            System.out.println(e.getMessage());
        }
    }

    private static void addMinionToVillain(String minionName, String villainName, Handle handler) throws SQLException {
            handler.setStatement("INSERT INTO minions_villains(minion_id,villain_id)\n" +
                    "SELECT (\n" +
                    "SELECT m.id\n" +
                    "FROM minions AS m\n" +
                    "WHERE m.`name` = ?),(\n" +
                    "SELECT v.id\n" +
                    "FROM villains AS v\n" +
                    "WHERE v.`name` = ?);");
            handler.getStatement().setString(1, minionName);
            handler.getStatement().setString(2, villainName);
            int result = handler.executeUpdate();
            if (result > 0) {
                System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
            }
    }

    private static void addMinionIfNotExists(String minionName, int minionAge, Handle handler) throws SQLException {
        handler.setStatement("INSERT INTO minions (`name`,`age`)\n" +
                "SELECT ? , ?\n" +
                "WHERE NOT EXISTS (\n" +
                "SELECT minions.`name`\n" +
                "FROM minions\n" +
                "WHERE minions.`name` = ?);");

            handler.getStatement().setString(1, minionName);
            handler.getStatement().setInt(2, minionAge);
            handler.getStatement().setString(3, minionName);
    }

    private static void addVillainIfNotExists(String villainName, Handle handler) throws SQLException {
        handler.setStatement("INSERT INTO villains (`name`,`evilness_factor`)\n" +
                "SELECT (?), 'evil'\n" +
                "WHERE NOT EXISTS (\n" +
                "SELECT villains.`name`\n" +
                "FROM villains\n" +
                "WHERE villains.`name` = ?);");

            handler.getStatement().setString(1, villainName);
            handler.getStatement().setString(2, villainName);
            int result = handler.executeUpdate();
            if (result > 0) {
                System.out.printf("Villain %s was added to the database.%n", villainName);
            }
    }

    private static void addTownIfNotExists(String minionTown, Handle handler) throws SQLException {
        handler.setStatement("INSERT INTO towns (`name`)\n" +
                "SELECT (?)\n" +
                "WHERE NOT EXISTS (\n" +
                "SELECT towns.`name`\n" +
                "FROM towns\n" +
                "WHERE towns.`name` = ?);");

            handler.getStatement().setString(1, minionTown);
            handler.getStatement().setString(2, minionTown);
            int result = handler.executeUpdate();
            if (result > 0) {
                System.out.printf("Town %s was added to the database.%n", minionTown);
            }
    }
}
