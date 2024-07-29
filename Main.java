import java.sql.*;
import org.checkerframework.checker.sqlquotes.qual.SqlEvenQuotes;

public class Main {

    private static String type = "wheels"; // or tires

    public static void main(String[] args) {

        String sql_query = ( type.equals("wheels") ) ? getWheelQuery() : getTireQuery() ;

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                ResultSet resultSet = statement.executeQuery( sql_query );
        ){

            Item.getItems( resultSet, type );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static @SqlEvenQuotes String getWheelQuery() {
        return "SELECT " +
                "wheels.id as id, " +
                "wheels.item_number as item_number, " +
                "brand.brand_name as brand, " +
                "model.model_name as model, " +
                "wheel_finish.finish as finish " +
                "FROM wheels " +
                "JOIN brand ON wheels.brand = brand.id " +
                "JOIN model ON wheels.model = model.id " +
                "JOIN wheel_finish ON wheels.full_finish = wheel_finish.id WHERE wheels.active = 1";
    }

    private static @SqlEvenQuotes String getTireQuery() {
        return "SELECT " +
                "tires.id as id, " +
                "tires.item_number as item_number, " +
                "brand.brand_name as brand, " +
                "model.model_name as model " +
                "FROM tires " +
                "JOIN brand ON tires.brand = brand.id " +
                "JOIN model ON tires.model = model.id WHERE tires.active = 1";
    }
}