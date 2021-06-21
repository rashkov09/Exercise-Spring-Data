import java.sql.ResultSet;
import java.util.Properties;

public interface Handle {
    void setProperties(String username, String password);
    void setConnection();
    void setStatement(String query);
    ResultSet execute();
}
