package JDBC;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.sql.Date;

public class JDBCQuery {
    private static DataSource dataSource;

    public static void main(String[] args) throws SQLException {
        long time = System.currentTimeMillis();
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
        queryAddDateToBooks();
        queryAddDateToCoAuthors();
        queryAddDateToLoggers();

        System.out.println(System.currentTimeMillis() - time + "ms execute program time.");
    }

    private static void queryAddDateToLoggers() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO loggers (book_id, user_id, date_of_start, date_of_return, status) " +
                            "VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setInt(1, 2);
            preparedStatement.setInt(2, 1);
            Calendar cal = Calendar.getInstance();
            Date date = new java.sql.Date(cal.getTimeInMillis());
            preparedStatement.setDate(3, date);
            preparedStatement.setDate(4, null);
            preparedStatement.setString(5, "reading");
            preparedStatement.executeUpdate();

            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 3);
            Date date1 = new java.sql.Date(120,11,5);
            Date date2 = new java.sql.Date(121,5,2);
            preparedStatement.setDate(3, date1);
            preparedStatement.setDate(4, date2);
            preparedStatement.setString(5, "returned");
            preparedStatement.executeUpdate();
        }
    }

    private static void queryAddDateToCoAuthors() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO co_author(books_id, authors_id) VALUES (?, ?)"
            );
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 4);
            preparedStatement.executeUpdate();

            preparedStatement.setInt(1, 3);
            preparedStatement.setInt(2, 3);
            preparedStatement.executeUpdate();
        }
    }

    private static void queryAddDateToBooks() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO books (title, available_amount, main_author_id) VALUES (?, ?, ?)"
            );
            preparedStatement.setString(1, "Effective Java");
            preparedStatement.setInt(2, 3);
            preparedStatement.setInt(3, 2);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "The Brothers Karamazov");
            preparedStatement.setInt(2, 23);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Java 8");
            preparedStatement.setInt(2, 4);
            preparedStatement.setInt(3, 4);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Core Java");
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 3);
            preparedStatement.executeUpdate();
        }
    }

    private static void queryAddDateToAuthors() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM authors;");
            statement.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO authors(first_name, last_name) VALUES (?,?)"
            );
            preparedStatement.setString(1, "Fedor");
            preparedStatement.setString(2, "Dostoevskiy");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Joshua");
            preparedStatement.setString(2, "Bloch");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Key");
            preparedStatement.setString(2, "Horstmann");
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Herbert");
            preparedStatement.setString(2, "Schildt");
            preparedStatement.executeUpdate();
        }
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
            preparedStatement.setString(1, "Anton");
            preparedStatement.setString(2, "Dorohov");
            preparedStatement.setString(3, "dorohov@mail.com");
            preparedStatement.setString(4, "qwerty");
            preparedStatement.setInt(5, 1);
            preparedStatement.setInt(6, 23);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Ivan");
            preparedStatement.setString(2, "Popov");
            preparedStatement.setString(3, "popov@mail.com");
            preparedStatement.setString(4, "pop123");
            preparedStatement.setInt(5, 2);
            preparedStatement.setInt(6, 33);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Ilya");
            preparedStatement.setString(2, "Ivanov");
            preparedStatement.setString(3, "ivanov@mail.com");
            preparedStatement.setString(4, "ivan45");
            preparedStatement.setInt(5, 2);
            preparedStatement.setInt(6, 45);
            preparedStatement.executeUpdate();

            preparedStatement.setString(1, "Olga");
            preparedStatement.setString(2, "Kravec");
            preparedStatement.setString(3, "olga@mail.com");
            preparedStatement.setString(4, "ol321");
            preparedStatement.setInt(5, 2);
            preparedStatement.setInt(6, 32);
            preparedStatement.executeUpdate();

        }
    }


    private static void queryAddDateToRoles() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
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
                            "    CONSTRAINT PK_roles PRIMARY KEY (id)," +
                            "    CONSTRAINT UQ_roles_role_name UNIQUE (role_name)" +
                            ");");
        }
    }

    private static void init() {
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
