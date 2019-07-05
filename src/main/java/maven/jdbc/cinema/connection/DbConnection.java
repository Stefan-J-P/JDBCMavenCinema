package maven.jdbc.cinema.connection;


import maven.jdbc.cinema.exceptions.ExceptionCode;
import maven.jdbc.cinema.exceptions.MyException;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConnection
{
    // SINGLETON OBJECT
    private static DbConnection ourInstance = new DbConnection();

    // GETTER get instance
    public static DbConnection getInstance()
    {
        return ourInstance;
    }


    private final String DRIVER = "org.sqlite.JDBC";
    private final String DATABASE = "jdbc:sqlite:test.db";
    private Connection connection;

    // GETTER CONNECTION
    public Connection getConnection()
    {
        return connection;
    }

    // NON ARG CONSTRUCTOR
    private DbConnection()
    {
        connect();
        createTables();
    }


    // CONNECT WITH DATA BASE ----------------------------------------------------------------------------------------------------------------------
    private void connect()
    {
        try
        {
            Class.forName(DRIVER);
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(DATABASE, config.toProperties());


        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATABASE, "CONNECTION WITH DATA BASE FAILED: " + e.getMessage());
        }
    }


    // CREATING TABLES with usage of builder class --------------------------------------------------------------------------------------------------
    @SuppressWarnings("Duplicates")
    private void createTables()
    {
        try (Statement statement = connection.createStatement())
        {
            final String movieTable = SqlTableCommand.builder()
                    .table("movies")
                    .primaryKey("id")
                    .stringColumn("title", 50, "not null")
                    .stringColumn("genre", 50, "not null")
                    .decimalColumn("yourPrice", 2,2, "not null")
                    .intColumn("duration", "")
                    .column("releaseDate", "date", "not null")
                    .builder().toString();

            final String ticketTable = SqlTableCommand.builder()
                    .table("tickets")
                    .primaryKey("id")
                    .intColumn("customerId", "not null")
                    .intColumn("movieId", "not null")
                    .dateTimeColumn("startDate", "")
                    .builder().toString();

            final String customerTable = SqlTableCommand.builder()
                    .table("customers")
                    .primaryKey("id")
                    .stringColumn("name", 50, "not null")
                    .stringColumn("surname", 50, "not null")
                    .intColumn("age", "")
                    .stringColumn("email", 100, "")
                    .intColumn("loyaltyCardId", "")
                    .builder().toString();

            final String loyaltyCardTable = SqlTableCommand.builder()
                    .table("loyaltyCards")
                    .primaryKey("id")
                    .dateTimeColumn("expirationDate", "")
                    .decimalColumn("discount", 2, 2, "not null")
                    .intColumn("moviesNumber", "")
                    .dateTimeColumn("creationDate", "")     // ???
                    .builder().toString();
            statement.execute(movieTable);
            statement.execute(ticketTable);
            statement.execute(customerTable);
            statement.execute(loyaltyCardTable);


        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATABASE, "CREATE TABLE EXCEPTION: " + e.getMessage());
        }
    }

}






















