package file.alkimi;

import java.io.IOException;
import java.sql.*;
public class CalculatePrice {
    public static void main(String[] args) throws SQLException, IOException {
        AlkimiDB alkimi=new AlkimiDB();
        alkimi.ConnectDb();
        alkimi.Calculate();    //Runs all the query and returns the total prices for the given date.
                                //pass the date from the config file
    }
}
