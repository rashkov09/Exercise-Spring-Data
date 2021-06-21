import java.sql.*;
import java.util.Properties;

public class DbHandler implements Handle{
    private Connection connection;
    private Properties properties;
    private PreparedStatement stmt;

    public DbHandler() {
    }


    @Override
    public void setProperties(String username, String password) {
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password",password);

    }

    @Override
    public void setConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://79.132.12.253:3306/soft_uni",this.properties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void setStatement(String query) {
        try {
            this.stmt = this.connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ResultSet execute() {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs ;
    }
}
