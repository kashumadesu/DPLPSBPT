package bigpersonality;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/personality_quiz";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; 
    
    private Connection connection;
    
    public DatabaseManager() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found");
            e.printStackTrace();
        }
    }
    
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
    }
    
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void initializeDatabase() {
        try {

            Connection serverConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/", DB_USER, DB_PASSWORD);
            
            Statement stmt = serverConnection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS personality_quiz");
            stmt.close();
            serverConnection.close();
            
            connect();
            
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(50) PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL, " +
                "first_name VARCHAR(50) NOT NULL, " +
                "last_name VARCHAR(50) NOT NULL, " +
                "middle_name VARCHAR(50), " +
                "age INT NOT NULL, " +
                "birthday DATE NOT NULL" +
                ")";
            
            String createProfilesTable = "CREATE TABLE IF NOT EXISTS profiles (" +
                "profile_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL, " +
                "openness INT NOT NULL, " +
                "conscientiousness INT NOT NULL, " +
                "extraversion INT NOT NULL, " +
                "agreeableness INT NOT NULL, " +
                "neuroticism INT NOT NULL, " +
                "date_taken TIMESTAMP NOT NULL, " +
                "FOREIGN KEY (username) REFERENCES users(username)" +
                ")";
            
            Statement statement = connection.createStatement();
            statement.executeUpdate(createUsersTable);
            statement.executeUpdate(createProfilesTable);
            statement.close();
            
        } catch (SQLException e) {
            System.err.println("Error initializing database");
            e.printStackTrace();
        }
    }
    
    public boolean userExists(String username) {
        try {
            connect();
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean authenticateUser(String username, String password) {
        try {
            connect();
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return storedPassword.equals(password);
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void createUser(UserData userData) {
        try {
            connect();
            String query = "INSERT INTO users (username, password, first_name, last_name, middle_name, age, birthday) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userData.getUsername());
            statement.setString(2, userData.getPassword());
            statement.setString(3, userData.getFirstName());
            statement.setString(4, userData.getLastName());
            statement.setString(5, userData.getMiddleName());
            statement.setInt(6, userData.getAge());
            statement.setDate(7, new java.sql.Date(userData.getBirthday().getTime()));
            
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public UserData getUserData(String username) {
        try {
            connect();
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                int age = resultSet.getInt("age");
                Date birthday = resultSet.getDate("birthday");
                String password = resultSet.getString("password");
                
                UserData userData = new UserData(firstName, lastName, middleName, age, birthday, username, password);
                
                resultSet.close();
                statement.close();
                return userData;
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateUserPassword(String username, String newPassword) {
        try {
            connect();
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Profile methods
    public void addProfile(String username, PersonalityProfile profile) {
        try {
            connect();
            String query = "INSERT INTO profiles (username, openness, conscientiousness, extraversion, agreeableness, neuroticism, date_taken) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, profile.getOpenness());
            statement.setInt(3, profile.getConscientiousness());
            statement.setInt(4, profile.getExtraversion());
            statement.setInt(5, profile.getAgreeableness());
            statement.setInt(6, profile.getNeuroticism());
            statement.setTimestamp(7, new java.sql.Timestamp(profile.getDate().getTime()));
            
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<PersonalityProfile> getUserProfiles(String username) {
        List<PersonalityProfile> profiles = new ArrayList<>();
        
        try {
            connect();
            String query = "SELECT * FROM profiles WHERE username = ? ORDER BY date_taken DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                int openness = resultSet.getInt("openness");
                int conscientiousness = resultSet.getInt("conscientiousness");
                int extraversion = resultSet.getInt("extraversion");
                int agreeableness = resultSet.getInt("agreeableness");
                int neuroticism = resultSet.getInt("neuroticism");
                Date dateTaken = resultSet.getTimestamp("date_taken");
                
                PersonalityProfile profile = new PersonalityProfile(openness, conscientiousness, extraversion, agreeableness, neuroticism);
                profile.setDate(dateTaken);
                
                profiles.add(profile);
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return profiles;
    }
}