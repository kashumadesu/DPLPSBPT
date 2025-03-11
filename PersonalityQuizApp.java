package bigpersonality;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class PersonalityQuizApp {
    // Main application frame
    private JFrame frame;
    
    // Card layout for switching between screens
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // User data storage
    private Map<String, UserData> userData = new HashMap<>();
    private Map<String, List<PersonalityProfile>> userProfiles = new HashMap<>();
    
    // Current user
    private String currentUser = null;
    
    // Managers
    private AuthenticationManager authManager;
    private DashboardManager dashboardManager;
    private ProfileManager profileManager;
    private QuizManager quizManager;
    private ResultsManager resultsManager;
    
    // Color scheme - dark theme with purple accents
    public static final Color PRIMARY_COLOR = new Color(138, 43, 226);   // BlueViolet
    public static final Color ACCENT_COLOR = new Color(186, 85, 211);    // MediumOrchid
    public static final Color DARK_BG_COLOR = new Color(30, 30, 40);     // Dark blue-gray
    public static final Color MEDIUM_BG_COLOR = new Color(45, 45, 60);   // Medium blue-gray
    public static final Color LIGHT_BG_COLOR = new Color(60, 60, 80);    // Light blue-gray
    public static final Color TEXT_COLOR = new Color(230, 230, 250);     // Lavender
    public static final Color SECONDARY_TEXT_COLOR = new Color(180, 180, 200); // Light gray-purple
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Set some UI defaults for dark theme
                UIManager.put("ProgressBar.selectionBackground", Color.WHITE);
                UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
                UIManager.put("TabbedPane.selected", new Color(80, 80, 100));
                UIManager.put("TabbedPane.background", new Color(45, 45, 60));
                UIManager.put("TabbedPane.foreground", new Color(230, 230, 250));
                UIManager.put("TabbedPane.contentAreaColor", new Color(45, 45, 60));
                UIManager.put("TabbedPane.light", new Color(60, 60, 80));
                UIManager.put("TabbedPane.highlight", new Color(80, 80, 100));
                UIManager.put("TabbedPane.shadow", new Color(20, 20, 30));
                UIManager.put("TabbedPane.darkShadow", new Color(10, 10, 20));
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PersonalityQuizApp().initialize();
        });
    }
    
    public void initialize() {
        // Load user data
        loadUserData();
        
        // Create main frame
        frame = new JFrame("Personality-Based Learning Path System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLocationRelativeTo(null);
        
        // Create card layout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(DARK_BG_COLOR);
        
        // Initialize managers
        authManager = new AuthenticationManager(this, frame, mainPanel, cardLayout);
        dashboardManager = new DashboardManager(this, frame, mainPanel, cardLayout);
        quizManager = new QuizManager(this, frame, mainPanel, cardLayout);
        resultsManager = new ResultsManager(this, frame, mainPanel, cardLayout);
        profileManager = new ProfileManager(this, frame, mainPanel, cardLayout);
        
        // Create different screens
        authManager.createLoginPanel();
        authManager.createSignupPanel();
        dashboardManager.createHomePanel();
        profileManager.createProfilePanel();
        authManager.createChangePasswordPanel();
        quizManager.createQuizIntroPanel();
        quizManager.createQuizPanel();
        resultsManager.createResultsPanel();
        
        // Add main panel to frame
        frame.add(mainPanel);
        
        // Show the frame
        frame.setVisible(true);
    }
    
    // User data management methods
    public UserData getCurrentUserData() {
        return userData.get(currentUser);
    }
    
    public List<PersonalityProfile> getCurrentUserProfiles() {
        return userProfiles.get(currentUser);
    }
    
    public void setCurrentUser(String username) {
        currentUser = username;
    }
    
    public String getCurrentUser() {
        return currentUser;
    }
    
    public void addUserProfile(PersonalityProfile profile) {
        if (!userProfiles.containsKey(currentUser)) {
            userProfiles.put(currentUser, new ArrayList<>());
        }
        userProfiles.get(currentUser).add(profile);
        saveUserData();
    }
    
    public boolean authenticateUser(String username, String password) {
        return userData.containsKey(username) && userData.get(username).getPassword().equals(password);
    }
    
    public void createNewUser(UserData newUser) {
        userData.put(newUser.getUsername(), newUser);
        userProfiles.put(newUser.getUsername(), new ArrayList<>());
        saveUserData();
    }
    
    public boolean userExists(String username) {
        return userData.containsKey(username);
    }
    
    public void updateUserPassword(String newPassword) {
        userData.get(currentUser).setPassword(newPassword);
        saveUserData();
    }
    
    // Manager getters
    public AuthenticationManager getAuthManager() {
        return authManager;
    }
    
    public DashboardManager getDashboardManager() {
        return dashboardManager;
    }
    
    public ProfileManager getProfileManager() {
        return profileManager;
    }
    
    public QuizManager getQuizManager() {
        return quizManager;
    }
    
    public ResultsManager getResultsManager() {
        return resultsManager;
    }
    
    // Utility methods
    public Component getComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
        }
        
        // If not found by name, return the component by card layout name
        CardLayout layout = (CardLayout) container.getLayout();
        for (Component component : container.getComponents()) {
            // Try to show this component
            layout.show(container, name);
            // If this is the visible component, it's the one we want
            if (component.isVisible()) {
                return component;
            }
        }
        
        return null;
    }
    
    @SuppressWarnings("unchecked")
	private void loadUserData() {
        try {
            File userDataFile = new File("user_data.dat");
            File profilesFile = new File("user_profiles.dat");
            
            if (userDataFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userDataFile))) {
                    userData = (Map<String, UserData>) ois.readObject();
                }
            }
            
            if (profilesFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(profilesFile))) {
                    userProfiles = (Map<String, List<PersonalityProfile>>) ois.readObject();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // If loading fails, start with empty maps
            userData = new HashMap<>();
            userProfiles = new HashMap<>();
        }
    }
    
    private void saveUserData() {
        try {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_data.dat"))) {
                oos.writeObject(userData);
            }
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_profiles.dat"))) {
                oos.writeObject(userProfiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving user data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}