package bigpersonality;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthenticationManager {
    private PersonalityQuizApp app;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public AuthenticationManager(PersonalityQuizApp app, JFrame frame, JPanel mainPanel, CardLayout cardLayout) {
        this.app = app;
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }
    
    public void createLoginPanel() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Create a left panel for illustration/branding
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        leftPanel.setPreferredSize(new Dimension(400, 0));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel appNameLabel = new JLabel("Learning Path System");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        appNameLabel.setForeground(Color.WHITE);
        leftPanel.add(appNameLabel, BorderLayout.NORTH);
        
        JLabel taglineLabel = new JLabel("<html><div style='text-align: left;'>Discover your personalized learning path based on your unique personality traits</div></html>");
        taglineLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        taglineLabel.setForeground(new Color(230, 230, 250));
        taglineLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        leftPanel.add(taglineLabel, BorderLayout.CENTER);
        
        // Add illustration or logo
        JPanel logoPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw a simple brain logo
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.fillOval(50, 0, 200, 150);
                g2d.setColor(new Color(255, 255, 255, 150));
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawOval(50, 0, 200, 150);
                
                // Draw brain lines
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawArc(70, 20, 160, 110, 0, 180);
                g2d.drawArc(90, 40, 120, 70, 0, 180);
                g2d.drawLine(150, 0, 150, 150);
                
                g2d.dispose();
            }
        };
        logoPanel.setOpaque(false);
        logoPanel.setPreferredSize(new Dimension(0, 200));
        leftPanel.add(logoPanel, BorderLayout.SOUTH);
        
        // Create a right panel for login form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        rightPanel.add(welcomeLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Please sign in to continue");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 30, 0);
        rightPanel.add(subtitleLabel, gbc);
        
        // Username field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        rightPanel.add(usernameLabel, gbc);
        
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        usernameField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        usernameField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        rightPanel.add(usernameField, gbc);
        
        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        rightPanel.add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        passwordField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        passwordField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 25, 0);
        rightPanel.add(passwordField, gbc);
        
        // Login button
        JButton loginButton = new JButton("Sign In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        rightPanel.add(loginButton, gbc);
        
        // Sign up link
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.setOpaque(false);
        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
        signupPanel.add(noAccountLabel);
        
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 14));
        signupButton.setForeground(PersonalityQuizApp.ACCENT_COLOR);
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setFocusPainted(false);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupPanel.add(signupButton);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        rightPanel.add(signupPanel, gbc);
        
        // Add panels to main login panel
        loginPanel.add(leftPanel, BorderLayout.WEST);
        loginPanel.add(rightPanel, BorderLayout.CENTER);
        
        // Add action listeners
        loginButton.addActionListener(_ -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (app.authenticateUser(username, password)) {
                app.setCurrentUser(username);
                app.getDashboardManager().updateHomePanel();
                cardLayout.show(mainPanel, "home");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        signupButton.addActionListener(_ -> {
            cardLayout.show(mainPanel, "signup");
        });
        
        mainPanel.add(loginPanel, "login");
    }
    
    public void createSignupPanel() {
        JPanel signupPanel = new JPanel(new BorderLayout());
        signupPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Personal Information Section
        JLabel personalInfoLabel = new JLabel("Personal Information");
        personalInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        personalInfoLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        personalInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(personalInfoLabel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Name fields in a row
        JPanel namePanel = new JPanel(new GridLayout(1, 3, 10, 0));
        namePanel.setOpaque(false);
        namePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // First Name
        JPanel firstNamePanel = new JPanel(new BorderLayout(0, 5));
        firstNamePanel.setOpaque(false);
        JLabel firstNameLabel = new JLabel("First Name*");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        firstNameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        firstNamePanel.add(firstNameLabel, BorderLayout.NORTH);
        
        JTextField firstNameField = new JTextField();
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        firstNameField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        firstNameField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        firstNameField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        firstNameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        firstNamePanel.add(firstNameField, BorderLayout.CENTER);
        namePanel.add(firstNamePanel);
        
        // Last Name
        JPanel lastNamePanel = new JPanel(new BorderLayout(0, 5));
        lastNamePanel.setOpaque(false);
        JLabel lastNameLabel = new JLabel("Last Name*");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lastNameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        lastNamePanel.add(lastNameLabel, BorderLayout.NORTH);
        
        JTextField lastNameField = new JTextField();
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        lastNameField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        lastNameField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        lastNameField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        lastNameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        lastNamePanel.add(lastNameField, BorderLayout.CENTER);
        namePanel.add(lastNamePanel);
        
        // Middle Name
        JPanel middleNamePanel = new JPanel(new BorderLayout(0, 5));
        middleNamePanel.setOpaque(false);
        JLabel middleNameLabel = new JLabel("Middle Name");
        middleNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        middleNameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        middleNamePanel.add(middleNameLabel, BorderLayout.NORTH);
        
        JTextField middleNameField = new JTextField();
        middleNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        middleNameField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        middleNameField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        middleNameField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        middleNameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        middleNamePanel.add(middleNameField, BorderLayout.CENTER);
        namePanel.add(middleNamePanel);
        
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Age and Birthday in a row
        JPanel agePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        agePanel.setOpaque(false);
        agePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        agePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Age
        JPanel ageFieldPanel = new JPanel(new BorderLayout(0, 5));
        ageFieldPanel.setOpaque(false);
        JLabel ageLabel = new JLabel("Age*");
        ageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ageLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        ageFieldPanel.add(ageLabel, BorderLayout.NORTH);
        
        JTextField ageField = new JTextField();
        ageField.setFont(new Font("Arial", Font.PLAIN, 14));
        ageField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        ageField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        ageField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        ageField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        ageFieldPanel.add(ageField, BorderLayout.CENTER);
        agePanel.add(ageFieldPanel);
        
        // Birthday
        JPanel birthdayPanel = new JPanel(new BorderLayout(0, 5));
        birthdayPanel.setOpaque(false);
        JLabel birthdayLabel = new JLabel("Birthday (MM/DD/YYYY)*");
        birthdayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        birthdayLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        birthdayPanel.add(birthdayLabel, BorderLayout.NORTH);
        
        JTextField birthdayField = new JTextField();
        birthdayField.setFont(new Font("Arial", Font.PLAIN, 14));
        birthdayField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        birthdayField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        birthdayField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        birthdayField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        birthdayPanel.add(birthdayField, BorderLayout.CENTER);
        agePanel.add(birthdayPanel);
        
        formPanel.add(agePanel);
        formPanel.add(Box.createVerticalStrut(25));
        
        // Account Information Section
        JLabel accountInfoLabel = new JLabel("Account Information");
        accountInfoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        accountInfoLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        accountInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(accountInfoLabel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Username
        JPanel usernamePanel = new JPanel(new BorderLayout(0, 5));
        usernamePanel.setOpaque(false);
        usernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        usernamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel usernameLabel = new JLabel("Username*");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        usernameField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        usernameField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        
        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(15));
        
        // Password fields in a row
        JPanel passwordPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        passwordPanel.setOpaque(false);
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Password
        JPanel passwordFieldPanel = new JPanel(new BorderLayout(0, 5));
        passwordFieldPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Password*");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        passwordFieldPanel.add(passwordLabel, BorderLayout.NORTH);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        passwordField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        passwordField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        passwordFieldPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(passwordFieldPanel);
        
        // Confirm Password
        JPanel confirmPasswordPanel = new JPanel(new BorderLayout(0, 5));
        confirmPasswordPanel.setOpaque(false);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password*");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        confirmPasswordPanel.add(confirmPasswordLabel, BorderLayout.NORTH);
        
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        confirmPasswordField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        confirmPasswordField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);
        passwordPanel.add(confirmPasswordPanel);
        
        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(30));
        
        // Create account button
        JButton signupButton = new JButton("Create Account");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        formPanel.add(signupButton);
        
        // Add action listeners
        signupButton.addActionListener(_ -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String middleName = middleNameField.getText();
            String ageText = ageField.getText();
            String birthdayText = birthdayField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // Validate fields
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "First name, last name, username, and password are required", "Signup Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate age
            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age <= 0) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age", "Signup Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate birthday
            Date birthday;
            try {
                birthday = dateFormat.parse(birthdayText);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid birthday in MM/DD/YYYY format", "Signup Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate password match
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match", "Signup Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if username already exists
            if (app.userExists(username)) {
                JOptionPane.showMessageDialog(frame, "Username already exists", "Signup Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create new user
            UserData newUser = new UserData(firstName, lastName, middleName, age, birthday, username, password);
            app.createNewUser(newUser);
            
            JOptionPane.showMessageDialog(frame, "Account created successfully", "Signup Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Log in the new user
            app.setCurrentUser(username);
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        backButton.addActionListener(_ -> {
            cardLayout.show(mainPanel, "login");
        });
        
        // Add scroll pane for the form panel
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Add panels to main signup panel
        signupPanel.add(headerPanel, BorderLayout.NORTH);
        signupPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(signupPanel, "signup");
    }
    
    public void createChangePasswordPanel() {
        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        changePasswordPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Create card-like container
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(10, 10, 10, 10);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form fields
        JLabel instructionLabel = new JLabel("Please enter your current password and a new password to update your account.");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(0, 10, 20, 10);
        cardPanel.add(instructionLabel, cardGbc);
        
        JLabel currentPasswordLabel = new JLabel("Current Password");
        currentPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentPasswordLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        cardGbc.gridx = 0;
        cardGbc.gridy = 1;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(10, 10, 5, 10);
        cardPanel.add(currentPasswordLabel, cardGbc);
        
        JPasswordField currentPasswordField = new JPasswordField(20);
        currentPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPasswordField.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        currentPasswordField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        currentPasswordField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        currentPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        cardGbc.gridx = 0;
        cardGbc.gridy = 2;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(0, 10, 15, 10);
        cardPanel.add(currentPasswordField, cardGbc);
        
        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        newPasswordLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        cardGbc.gridx = 0;
        cardGbc.gridy = 3;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(10, 10, 5, 10);
        cardPanel.add(newPasswordLabel, cardGbc);
        
        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        newPasswordField.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        newPasswordField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        newPasswordField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        newPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        cardGbc.gridx = 0;
        cardGbc.gridy = 4;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(0, 10, 15, 10);
        cardPanel.add(newPasswordField, cardGbc);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPasswordLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        cardGbc.gridx = 0;
        cardGbc.gridy = 5;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(10, 10, 5, 10);
        cardPanel.add(confirmPasswordLabel, cardGbc);
        
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        confirmPasswordField.setForeground(PersonalityQuizApp.TEXT_COLOR);
        confirmPasswordField.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 100, 120), 1, true),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        cardGbc.gridx = 0;
        cardGbc.gridy = 6;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(0, 10, 25, 10);
        cardPanel.add(confirmPasswordField, cardGbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(100, 100, 120));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        cardGbc.gridx = 0;
        cardGbc.gridy = 7;
        cardGbc.gridwidth = 2;
        cardGbc.insets = new Insets(0, 10, 10, 10);
        cardPanel.add(buttonPanel, cardGbc);
        
        // Add card to form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        formPanel.add(cardPanel, gbc);
        
        // Add action listeners
        saveButton.addActionListener(_ -> {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // Validate current password
            if (!app.getCurrentUserData().getPassword().equals(currentPassword)) {
                JOptionPane.showMessageDialog(frame, "Current password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate new password
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "New password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate password match
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "New passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update password
            app.updateUserPassword(newPassword);
            
            JOptionPane.showMessageDialog(frame, "Password changed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        cancelButton.addActionListener(_ -> {
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        backButton.addActionListener(_ -> {
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        // Add panels to main panel
        changePasswordPanel.add(headerPanel, BorderLayout.NORTH);
        changePasswordPanel.add(formPanel, BorderLayout.CENTER);
        
        mainPanel.add(changePasswordPanel, "changePassword");
    }
}