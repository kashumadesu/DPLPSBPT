package bigpersonality;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProfileManager {
    private PersonalityQuizApp app;
    @SuppressWarnings("unused")
	private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public ProfileManager(PersonalityQuizApp app, JFrame frame, JPanel mainPanel, CardLayout cardLayout) {
        this.app = app;
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }
    
    public void createProfilePanel() {
        JPanel profilePanel = new JPanel(new BorderLayout(20, 20));
        profilePanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // This panel will be dynamically updated when the user views their profile
        mainPanel.add(profilePanel, "profile");
    }
    
    public void updateProfilePanel() {
        // Get the profile panel
        JPanel profilePanel = (JPanel) app.getComponentByName(mainPanel, "profile");
        profilePanel.removeAll();
        
        // Get current user data
        UserData user = app.getCurrentUserData();
        
        // Create modern profile layout
        JPanel dashboardPanel = new JPanel(new BorderLayout(20, 20));
        dashboardPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        // User info in header
        JPanel userInfoPanel = new JPanel(new BorderLayout(20, 0));
        userInfoPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        // User avatar (circle with initials)
        JPanel avatarPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int size = Math.min(getWidth(), getHeight()) - 10;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                // Draw circle
                g2d.setColor(PersonalityQuizApp.PRIMARY_COLOR);
                g2d.fillOval(x, y, size, size);
                
                // Draw initials
                String initials = String.valueOf(user.getFirstName().charAt(0)) + 
                                 String.valueOf(user.getLastName().charAt(0));
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, size / 2));
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(initials);
                int textHeight = fm.getHeight();
                g2d.drawString(initials, 
                              x + (size - textWidth) / 2, 
                              y + (size + textHeight) / 2 - fm.getDescent());
                
                g2d.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(60, 60);
            }
        };
        avatarPanel.setOpaque(false);
        
        JPanel namePanel = new JPanel(new BorderLayout(5, 5));
        namePanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        JLabel usernameLabel = new JLabel("@" + user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
        
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(usernameLabel, BorderLayout.CENTER);
        
        userInfoPanel.add(avatarPanel, BorderLayout.WEST);
        userInfoPanel.add(namePanel, BorderLayout.CENTER);
        
        // Back button
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(_ -> {
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        headerPanel.add(userInfoPanel, BorderLayout.WEST);
        headerPanel.add(backButton, BorderLayout.EAST);
        
        // Create tabbed pane for profile sections
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // User details panel
        JPanel userDetailsPanel = new JPanel(new BorderLayout(20, 20));
        userDetailsPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        userDetailsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Create a card-like panel for user details
        JPanel detailsCard = new JPanel(new BorderLayout(20, 20));
        detailsCard.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel detailsHeaderLabel = new JLabel("Personal Information");
        detailsHeaderLabel.setFont(new Font("Arial", Font.BOLD, 18));
        detailsHeaderLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        // Create grid for user details
        JPanel detailsGrid = new JPanel(new GridLayout(0, 2, 15, 15));
        detailsGrid.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        
        // Add user details
        addProfileDetailRow(detailsGrid, "First Name", user.getFirstName());
        addProfileDetailRow(detailsGrid, "Last Name", user.getLastName());
        addProfileDetailRow(detailsGrid, "Middle Name", user.getMiddleName());
        addProfileDetailRow(detailsGrid, "Age", String.valueOf(user.getAge()));
        addProfileDetailRow(detailsGrid, "Birthday", new SimpleDateFormat("MM/dd/yyyy").format(user.getBirthday()));
        addProfileDetailRow(detailsGrid, "Username", user.getUsername());
        
        detailsCard.add(detailsHeaderLabel, BorderLayout.NORTH);
        detailsCard.add(detailsGrid, BorderLayout.CENTER);
        
        // Add change password button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        changePasswordButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        changePasswordButton.setForeground(Color.BLACK);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        buttonPanel.add(changePasswordButton);
        detailsCard.add(buttonPanel, BorderLayout.SOUTH);
        
        changePasswordButton.addActionListener(_ -> {
            cardLayout.show(mainPanel, "changePassword");
        });
        
        userDetailsPanel.add(detailsCard, BorderLayout.CENTER);
        
        // Results panel
        JPanel resultsPanel = new JPanel(new BorderLayout(20, 20));
        resultsPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Check if user has any quiz results
        List<PersonalityProfile> profiles = app.getCurrentUserProfiles();
        
        if (profiles == null || profiles.isEmpty()) {
            JPanel noResultsPanel = new JPanel(new BorderLayout(0, 20));
            noResultsPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
            noResultsPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(80, 80, 100), 1, true),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
            ));
            
            JLabel noResultsLabel = new JLabel("No quiz results available");
            noResultsLabel.setFont(new Font("Arial", Font.BOLD, 18));
            noResultsLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
            noResultsLabel.setHorizontalAlignment(JLabel.CENTER);
            
            JLabel instructionLabel = new JLabel("Take a quiz to see your personality profile and learning recommendations");
            instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            instructionLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
            instructionLabel.setHorizontalAlignment(JLabel.CENTER);
            
            JButton takeQuizButton = new JButton("Take a Quiz Now");
            takeQuizButton.setFont(new Font("Arial", Font.BOLD, 14));
            takeQuizButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
            takeQuizButton.setForeground(Color.BLACK);
            takeQuizButton.setFocusPainted(false);
            takeQuizButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            takeQuizButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            takeQuizButton.addActionListener(_ -> {
                cardLayout.show(mainPanel, "quizIntro");
            });
            
            JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel2.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
            buttonPanel2.add(takeQuizButton);
            
            JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
            contentPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
            contentPanel.add(noResultsLabel, BorderLayout.NORTH);
            contentPanel.add(instructionLabel, BorderLayout.CENTER);
            contentPanel.add(buttonPanel2, BorderLayout.SOUTH);
            
            noResultsPanel.add(contentPanel, BorderLayout.CENTER);
            resultsPanel.add(noResultsPanel, BorderLayout.CENTER);
        } else {
            // Create a panel to display all results
            JPanel allResultsPanel = new JPanel();
            allResultsPanel.setLayout(new BoxLayout(allResultsPanel, BoxLayout.Y_AXIS));
            allResultsPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            
            // Add each result
            for (int i = 0; i < profiles.size(); i++) {
                PersonalityProfile profile = profiles.get(i);
                JPanel resultPanel = createResultSummaryPanel(profile, i + 1);
                allResultsPanel.add(resultPanel);
                allResultsPanel.add(Box.createVerticalStrut(20)); // Add spacing
            }
            
            // Add scroll pane for results
            JScrollPane scrollPane = new JScrollPane(allResultsPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            resultsPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        // Add panels to tabbed pane
        tabbedPane.addTab("User Details", userDetailsPanel);
        tabbedPane.addTab("Quiz Results", resultsPanel);
        
        // Add header and tabbed pane to dashboard
        dashboardPanel.add(headerPanel, BorderLayout.NORTH);
        dashboardPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add dashboard to profile panel
        profilePanel.add(dashboardPanel, BorderLayout.CENTER);
        
        profilePanel.revalidate();
        profilePanel.repaint();
    }
    
    private void addProfileDetailRow(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        valueComponent.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
        
        panel.add(labelComponent);
        panel.add(valueComponent);
    }
    
    private JPanel createResultSummaryPanel(PersonalityProfile profile, int resultNumber) {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        
        // Header with result number and date
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        
        JLabel resultLabel = new JLabel("Result #" + resultNumber);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        headerPanel.add(resultLabel, BorderLayout.WEST);
        
        // Add date if available
        if (profile.getDate() != null) {
            JLabel dateLabel = new JLabel("Taken on: " + new SimpleDateFormat("MM/dd/yyyy").format(profile.getDate()));
            dateLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            dateLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
            headerPanel.add(dateLabel, BorderLayout.EAST);
        }
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Create content panel with traits and recommendation
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        
        // Create traits panel with modern visualization
        JPanel traitsPanel = new JPanel(new BorderLayout(10, 10));
        traitsPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        traitsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100), 1, true),
            "Personality Traits",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            PersonalityQuizApp.TEXT_COLOR
        ));
        
        // Create grid for trait bars
        JPanel traitsGrid = new JPanel(new GridLayout(5, 1, 10, 15));
        traitsGrid.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        traitsGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Add trait bars with modern style
        app.getResultsManager().addResultTraitBar(traitsGrid, "Openness", profile.getOpenness(), "Curious and open to new experiences", new Color(255, 165, 0));
        app.getResultsManager().addResultTraitBar(traitsGrid, "Conscientiousness", profile.getConscientiousness(), "Organized and responsible", new Color(65, 105, 225));
        app.getResultsManager().addResultTraitBar(traitsGrid, "Extraversion", profile.getExtraversion(), "Outgoing and energetic", new Color(255, 69, 0));
        app.getResultsManager().addResultTraitBar(traitsGrid, "Agreeableness", profile.getAgreeableness(), "Friendly and compassionate", new Color(50, 205, 50));
        app.getResultsManager().addResultTraitBar(traitsGrid, "Neuroticism", profile.getNeuroticism(), "Sensitive and nervous", new Color(148, 0, 211));
        
        traitsPanel.add(traitsGrid, BorderLayout.CENTER);
        
        // Create recommendation panel
        JPanel recommendationPanel = new JPanel(new BorderLayout(10, 10));
        recommendationPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        recommendationPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100), 1, true),
            "Learning Path Recommendation",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            PersonalityQuizApp.TEXT_COLOR
        ));
        
        // Create a summarized recommendation
        String recommendation = app.getResultsManager().createSummarizedRecommendation(profile);
        JTextArea recommendationText = new JTextArea(recommendation);
        recommendationText.setEditable(false);
        recommendationText.setLineWrap(true);
        recommendationText.setWrapStyleWord(true);
        recommendationText.setFont(new Font("Arial", Font.PLAIN, 14));
        recommendationText.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        recommendationText.setForeground(PersonalityQuizApp.TEXT_COLOR);
        recommendationText.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        recommendationPanel.add(recommendationText, BorderLayout.CENTER);
        
        // Add view full recommendation button
        JButton viewFullButton = new JButton("View Full Recommendation");
        viewFullButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewFullButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        viewFullButton.setForeground(Color.BLACK);
        viewFullButton.setFocusPainted(false);
        viewFullButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        viewFullButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewFullButton.addActionListener(_ -> {
            app.getResultsManager().displayDetailedRecommendation(profile);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        buttonPanel.add(viewFullButton);
        recommendationPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add traits and recommendation to content panel
        contentPanel.add(traitsPanel, BorderLayout.CENTER);
        contentPanel.add(recommendationPanel, BorderLayout.SOUTH);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
}
