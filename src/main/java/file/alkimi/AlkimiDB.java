package file.alkimi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AlkimiDB {
        private String url;
        private String username;
        private String password;
        private Properties properties;
        private Connection connection;
        private  String Bidquery;
        private  String Winquery;
        private  String Diffquery;
        private  String date;



    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getBidquery() {
        return Bidquery;
    }

    public String getWinquery() {
        return Winquery;
    }

    public String getDiffquery() {
        return Diffquery;
    }

    public String getDate() {
        return date;
    }

    public AlkimiDB() throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        this.url = properties.getProperty("url");
        this.password = properties.getProperty("password");
        this.username = properties.getProperty("user");
        this.Bidquery= properties.getProperty("Bidquery");
        this.Winquery= properties.getProperty("Winquery");
        this.Diffquery= properties.getProperty("Diffquery");
        this.date=properties.getProperty("date");
    }

    public void ConnectDb() throws SQLException {
        connection= DriverManager.getConnection(getUrl(), getUsername(), getPassword());

    }
    public void Calculate() throws SQLException {
        Connection connection=getConnection();
        PreparedStatement bid_statement= connection.prepareStatement(Bidquery);
        PreparedStatement win_statement= connection.prepareStatement(Winquery);
        PreparedStatement diff_statement= connection.prepareStatement(Diffquery);
        win_statement.setString(1,date);
        bid_statement.setString(1,date);
        diff_statement.setString(1,date);
                double dspBidPrice_total=0;
        double dspWinprice_total=0;
        double diffprice_total=0;


        ResultSet winSet=win_statement.executeQuery();
        ResultSet bidSet=bid_statement.executeQuery();
        ResultSet diffSet=diff_statement.executeQuery();
        List<String> dspWinprice=new ArrayList<>();
        while(winSet.next())
        {
            dspWinprice.add(winSet.getString(1));
        }
        for(String price:dspWinprice)
        {
            dspWinprice_total=dspWinprice_total+Double.parseDouble(price);
        }
        List<String> dspBidPrice=new ArrayList<>();
        while(bidSet.next())
        {
            dspBidPrice.add(bidSet.getString(1));
        }
        for(String price:dspBidPrice)
        {
           dspBidPrice_total=dspBidPrice_total+ Double.parseDouble(price);
        }
        List<String> diffPrice=new ArrayList<>();
        while(diffSet.next())
        {
            diffPrice.add(diffSet.getString(1));
        }
        for(String price:diffPrice)
        {
            diffprice_total=diffprice_total+ Double.parseDouble(price);
        }
        System.out.println("Total dspBidPrice for the Date: "+date+" is: "+dspBidPrice_total);
        System.out.println("Total dspWinPrice for the Date: "+date+" is: "+dspWinprice_total);
        System.out.println("Total dspWinBidDif for the Date: "+date+" is: "+diffprice_total);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
