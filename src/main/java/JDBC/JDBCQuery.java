package JDBC;


import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCQuery {
    private static DataSource dataSource;

    public static void main(String[] args) throws SQLException {
        init();
        queryCreateTableRoles();
        queryCreateTableUsers();
        queryCreateTableAuthors();
        queryCreateTableBooks();
        queryCreateTableCoAuthors();
        queryCreateTableLoggers();
        queryAddDateToRoles();
        queryAddDateToUsers();
        queryAddDateToAuthors();

    }

    private static void queryAddDateToAuthors() {
    }

    private static void queryAddDateToUsers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM users;");
            statement.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1;");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users(first_name, last_name, email, password, role_id, age)" +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1,"Anton");
            preparedStatement.setString(2,"Dorohov");
            preparedStatement.setString(3,"dorohov@mail.com");
            preparedStatement.setString(4, "qwerty");
            preparedStatement.setInt(5,1);
            preparedStatement.setInt(6,23);
            preparedStatement.executeUpdate();
            preparedStatement.setString(1,"Ivan");
            preparedStatement.setString(2,"Popov");
            preparedStatement.setString(3,"popov@mail.com");
            preparedStatement.setString(4, "pop123");
            preparedStatement.setInt(5,2);
            preparedStatement.setInt(6,33);
            preparedStatement.executeUpdate();
            preparedStatement.setString(1,"Ilya");
            preparedStatement.setString(2,"Ivanov");
            preparedStatement.setString(3,"ivanov@mail.com");
            preparedStatement.setString(4, "ivan45");
            preparedStatement.setInt(5,2);
            preparedStatement.setInt(6,45);
            preparedStatement.executeUpdate();
            preparedStatement.setString(1,"Olga");
            preparedStatement.setString(2,"Kravec");
            preparedStatement.setString(3,"olga@mail.com");
            preparedStatement.setString(4, "ol321");
            preparedStatement.setInt(5,2);
            preparedStatement.setInt(6,32);
            preparedStatement.executeUpdate();

        }
    }


    private static void queryAddDateToRoles() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM roles;");
            statement.execute("ALTER SEQUENCE roles_id_seq RESTART WITH 1;");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO roles(id, role_name) VALUES (?, ?);"
            );
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Manager");
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Reader");
            preparedStatement.executeUpdate();

        }
    }

    private static void queryCreateTableLoggers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS loggers (" +
                            "    id SERIAL," +
                            "    book_id INT NOT NULL," +
                            "    user_id INT NOT NULL," +
                            "    date_of_start DATE DEFAULT now()," +
                            "    date_of_return DATE DEFAULT NULL," +
                            "    status VARCHAR(255) NOT NULL," +
                            "    CONSTRAINT PK_loggers PRIMARY KEY (id)," +
                            "    CONSTRAINT FK_loggers_users FOREIGN KEY (user_id) REFERENCES users(id)," +
                            "    CONSTRAINT FK_loggers_books FOREIGN KEY (book_id) REFERENCES books(id)" +
                            ");");
        }
    }

    private static void queryCreateTableCoAuthors() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS co_author (" +
                            "    books_id INT," +
                            "    authors_id INT," +
                            "    CONSTRAINT PK_co_author PRIMARY KEY (books_id, authors_id)," +
                            "    CONSTRAINT FK_co_author_books FOREIGN KEY (books_id) REFERENCES books(id)," +
                            "    CONSTRAINT FK_co_author_authors FOREIGN KEY (authors_id) REFERENCES authors(id)" +
                            ");");
        }
    }

    private static void queryCreateTableBooks() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS books (" +
                            "    id SERIAL," +
                            "    title VARCHAR(255) NOT NULL," +
                            "    available_amount INT DEFAULT 1," +
                            "    main_author_id INT NOT NULL," +
                            "    CONSTRAINT PK_books PRIMARY KEY (id)," +
                            "    CONSTRAINT FK_books_authors FOREIGN KEY (main_author_id) REFERENCES authors(id)" +
                            ");");
        }
    }

    private static void queryCreateTableAuthors() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS authors (" +
                            "    id SERIAL,\n" +
                            "    first_name VARCHAR(255) NOT NULL," +
                            "    last_name VARCHAR(255) NOT NULL," +
                            "    CONSTRAINT PK_authors PRIMARY KEY (id)" +
                            ");");
        }
    }

    private static void queryCreateTableRoles() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS roles (" +
                            "    id SERIAL," +
                            "    role_name VARCHAR(255) NOT NULL," +
                            "    CONSTRAINT PK_roles PRIMARY KEY (id)" +
                            ");");
        }
    }

    private static void init() throws SQLException {
        dataSource = defaultConnectionToPostgres(
                "jdbc:postgresql://localhost:5432/library",
                "postgres",
                "1111");
    }

    private static DataSource defaultConnectionToPostgres(String url, String username, String password) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private static void queryCreateTableUsers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "    id SERIAL," +
                            "    first_name VARCHAR(255) NOT NULL," +
                            "    last_name VARCHAR(255) NOT NULL," +
                            "    email VARCHAR(255) NOT NULL," +
                            "    password VARCHAR(255) NOT NULL," +
                            "    role_id INT NOT NULL," +
                            "    age INT NOT NULL," +
                            "    CONSTRAINT PK_users PRIMARY KEY (id)," +
                            "    CONSTRAINT UQ_users_email UNIQUE (email)," +
                            "    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES roles(id)" +
                            ")");
            connection.commit();
        }
    }

}
