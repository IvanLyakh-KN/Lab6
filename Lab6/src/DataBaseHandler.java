import java.sql.Connection;
import  java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DataBaseHandler extends Configs{
     Connection dbConnection;
     public Connection getDbConnection()
             throws ClassNotFoundException,SQLException {

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

         Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return  dbConnection;
     }
    public void signUpPersone(String FirstName, String LastName, String PhoneNumber){
        String insert = "INSERT INTO " + Const.NAME_TABLE + "(" + Const.PERSON_FIRST_NAME + "," +
                Const.PERSON_LAST_NAME + "," + Const.PERSON_PHONE_NUMBER + ")" +
                "VALUES(?,?,?)";

        try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setString(1, FirstName);
                prSt.setString(2, LastName);
                prSt.setString(3, PhoneNumber);

                prSt.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
}
