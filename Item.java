import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

class Item {

    private static String[] match = {"/", "& ", "&", "-", ","};

    static void getItems(ResultSet resultSet, String type) throws SQLException {

        System.out.println("Displaying all items");

        if ( type.equals("wheels") ) {
            while( resultSet.next() )
                System.out.println( downloadWheels( resultSet ) );

        } else if ( type.equals("tires") ) {
            while( resultSet.next() )
                System.out.println( downloadTires( resultSet ) );
        }

        System.out.println("Finished");
    }

    private static String downloadWheels(ResultSet resultSet) throws SQLException {
        String item_number = resultSet.getString( "item_number");
        String brand       = resultSet.getString( "brand" );
        String model       = resultSet.getString( "model" );
        String finish      = resultSet.getString( "finish" );

        brand  = sanitizeData( brand );
        model  = sanitizeData( model );
        finish = sanitizeData( finish );

        String image_location = "http://website.com/version_2/img/wheels/" + brand + "/" + model + "_" + finish + ".jpg";

        try(InputStream in = new URL( image_location ).openStream()){
            Files.copy(in, Paths.get("D:/DB/FCWT/applications/eci-website-images/wheels/" + item_number + ".jpg"));
        } catch (IOException e) {
            //try with png
            try(InputStream in = new URL( image_location.replace( ".jpg", ".png" ) ).openStream()) {
                Files.copy(in, Paths.get("D:/DB/FCWT/applications/eci-website-images/wheels/" + item_number + ".jpg"));
            } catch ( IOException ignored ) {}
        }

        return image_location;
    }

    private static String downloadTires(ResultSet resultSet) throws SQLException {
        String item_number = resultSet.getString( "item_number");
        String brand       = resultSet.getString( "brand" );
        String model       = resultSet.getString( "model" );

        brand  = sanitizeData( brand );
        model  = sanitizeData( model );

        String image_location = "http://website.com/version_2/img/tires/" + brand + "/" + model + ".jpg";

        try(InputStream in = new URL( image_location ).openStream()){
            Files.copy(in, Paths.get("D:/DB/FCWT/applications/eci-website-images/tires/" + item_number + ".jpg"));
        } catch (IOException e) {
            //try with png
            try(InputStream in = new URL( image_location.replace( ".jpg", ".png" ) ).openStream()) {
                Files.copy(in, Paths.get("D:/DB/FCWT/applications/eci-website-images/tires/" + item_number + ".jpg"));
            } catch ( IOException ignored ) {}
        }

        return image_location;
    }

    private static String sanitizeData( String str ) {
        str = str.toLowerCase();

        for ( String replacement : match )
            str = str.replace( replacement, "" );

        str = str.replace(" ", "_");

        return str;
    }
}