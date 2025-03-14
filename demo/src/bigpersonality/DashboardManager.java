package bigpersonality;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DashboardManager {
    private PersonalityQuizApp app;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public DashboardManager(PersonalityQuizApp app, JFrame frame, JPanel mainPanel, CardLayout cardLayout) {
        this.app = app;
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }
    
    public void createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout(20, 20));
        homePanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        homePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // This panel will be dynamically updated when the user logs in
        mainPanel.add(homePanel, "home");
    }
    
    public void updateHomePanel() {
        // Get the home panel
        JPanel homePanel = (JPanel) app.getComponentByName(mainPanel, "home");
        homePanel.removeAll();
        
        // Get current user data
        UserData user = app.getCurrentUserData();
        List<PersonalityProfile> profiles = app.getCurrentUserProfiles();
        int quizCount = profiles != null ? profiles.size() : 0;
        
        // Create modern dashboard layout
        JPanel dashboardPanel = new JPanel(new BorderLayout(20, 20));
        dashboardPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Create sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        userInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
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
                g2d.setColor(Color.WHITE); // Ensure white text on colored background
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
                return new Dimension(80, 80);
            }
        };
        avatarPanel.setOpaque(false);
        avatarPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE); // Changed to white for better visibility
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel usernameLabel = new JLabel("@" + user.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(220, 220, 220)); // Light gray for better visibility
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        userInfoPanel.add(avatarPanel);
        userInfoPanel.add(Box.createVerticalStrut(10));
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createVerticalStrut(5));
        userInfoPanel.add(usernameLabel);
        
        // Navigation menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Menu items with custom icons instead of emojis
        String[] menuItems = {"Dashboard", "Take Quiz", "Results", "Profile", "Settings"};
        int[] iconTypes = {0, 1, 2, 3, 4}; // 0=home, 1=quiz, 2=results, 3=profile, 4=settings
        
        for (int i = 0; i < menuItems.length; i++) {
            JPanel menuItemPanel = createMenuItemPanel(iconTypes[i], menuItems[i], i == 0);
            final int index = i;
            
            menuItemPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (index) {
                        case 0: // Dashboard - already here
                            break;
                        case 1: // Take Quiz
                            cardLayout.show(mainPanel, "quizIntro");
                            break;
                        case 2: // Results
                            if (quizCount > 0) {
                                app.getProfileManager().updateProfilePanel();
                                cardLayout.show(mainPanel, "profile");
                                // Select the Results tab
                                JTabbedPane tabbedPane = findTabbedPane(app.getComponentByName(mainPanel, "profile"));
                                if (tabbedPane != null) {
                                    tabbedPane.setSelectedIndex(1);
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "You haven't taken any quizzes yet. Take a quiz to see your results.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case 3: // Profile
                            app.getProfileManager().updateProfilePanel();
                            cardLayout.show(mainPanel, "profile");
                            break;
                        case 4: // Settings (Change Password)
                            cardLayout.show(mainPanel, "changePassword");
                            break;
                    }
                }
            });
            
            menuPanel.add(menuItemPanel);
            menuPanel.add(Box.createVerticalStrut(5));
        }
        
        // Sign out button with custom icon
        JPanel signOutPanel = createMenuItemPanel(5, "Sign Out", false); // 5=signout
        signOutPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                app.setCurrentUser(null);
                cardLayout.show(mainPanel, "login");
            }
        });
        
        // Add components to sidebar
        sidebarPanel.add(userInfoPanel);
        sidebarPanel.add(new JSeparator());
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(menuPanel);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(new JSeparator());
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(signOutPanel);
        
        // Create main content area
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Welcome header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE); // Changed to white for better visibility
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Date display
        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEEE, MMMM d, yyyy").format(new Date()));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(220, 220, 220)); // Light gray for better visibility
        headerPanel.add(dateLabel, BorderLayout.EAST);
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Dashboard cards in a grid
        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        // Card 1: Quick Stats
        JPanel statsCard = createDashboardCard("Quick Stats", null);
        JPanel statsContent = new JPanel();
        statsContent.setLayout(new BoxLayout(statsContent, BoxLayout.Y_AXIS));
        statsContent.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        // Stats items
        JPanel quizCountPanel = createStatItem("Quizzes Taken", String.valueOf(quizCount));
        statsContent.add(quizCountPanel);
        statsContent.add(Box.createVerticalStrut(15));
        
        // Last quiz date
        String lastQuizDate = "None";
        if (quizCount > 0) {
            lastQuizDate = new SimpleDateFormat("MM/dd/yyyy").format(profiles.get(profiles.size() - 1).getDate());
        }
        JPanel lastQuizPanel = createStatItem("Last Quiz", lastQuizDate);
        statsContent.add(lastQuizPanel);
        
        statsCard.add(statsContent, BorderLayout.CENTER);
        
        // Card 2: Personality Overview
        JPanel personalityCard = createDashboardCard("Personality Overview", null);
        
        if (quizCount > 0) {
            // Get the most recent profile
            PersonalityProfile latestProfile = profiles.get(profiles.size() - 1);
            
            // Create mini chart
            JPanel chartPanel = new JPanel(new GridLayout(5, 1, 5, 10));
            chartPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            
            // Add mini trait bars
            addMiniTraitBar(chartPanel, "O", latestProfile.getOpenness());
            addMiniTraitBar(chartPanel, "C", latestProfile.getConscientiousness());
            addMiniTraitBar(chartPanel, "E", latestProfile.getExtraversion());
            addMiniTraitBar(chartPanel, "A", latestProfile.getAgreeableness());
            addMiniTraitBar(chartPanel, "N", latestProfile.getNeuroticism());
            
            personalityCard.add(chartPanel, BorderLayout.CENTER);
            
            // Add view details button
            JButton viewDetailsButton = new JButton("View Full Results");
            viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 12));
            viewDetailsButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
            viewDetailsButton.setForeground(Color.BLACK); // Ensure black text on button
            viewDetailsButton.setFocusPainted(false);
            viewDetailsButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            viewDetailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewDetailsButton.addActionListener(_ -> {
                app.getProfileManager().updateProfilePanel();
                cardLayout.show(mainPanel, "profile");
                // Select the Results tab
                JTabbedPane tabbedPane = findTabbedPane(app.getComponentByName(mainPanel, "profile"));
                if (tabbedPane != null) {
                    tabbedPane.setSelectedIndex(1);
                }
            });
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            buttonPanel.add(viewDetailsButton);
            personalityCard.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            
            JLabel emptyLabel = new JLabel("Take a quiz to see your personality profile");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.WHITE); // Changed for better visibility
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            
            JButton takeQuizButton = new JButton("Take Quiz Now");
            takeQuizButton.setFont(new Font("Arial", Font.BOLD, 12));
            takeQuizButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
            takeQuizButton.setForeground(Color.BLACK); // Ensure black text on button
            takeQuizButton.setFocusPainted(false);
            takeQuizButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            takeQuizButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            takeQuizButton.addActionListener(_ -> {
                cardLayout.show(mainPanel, "quizIntro");
            });
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            buttonPanel.add(takeQuizButton);
            emptyPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            personalityCard.add(emptyPanel, BorderLayout.CENTER);
        }
        
        // Card 3: Quick Actions
        JPanel actionsCard = createDashboardCard("Quick Actions", null);
        JPanel actionsContent = new JPanel(new GridLayout(2, 1, 10, 10));
        actionsContent.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        actionsContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Action buttons with custom icons
        JButton newQuizButton = createActionButton("Take New Quiz", 1); // 1=quiz
        newQuizButton.setForeground(Color.BLACK); // Set font color to black
        newQuizButton.addActionListener(_ -> {
            cardLayout.show(mainPanel, "quizIntro");
        });

        JButton viewProfileButton = createActionButton("View Profile", 3); // 3=profile
        viewProfileButton.setForeground(Color.BLACK); // Set font color to black
        viewProfileButton.addActionListener(_ -> {
            app.getProfileManager().updateProfilePanel();
            cardLayout.show(mainPanel, "profile");
        });
        
        actionsContent.add(newQuizButton);
        actionsContent.add(viewProfileButton);
        actionsCard.add(actionsContent, BorderLayout.CENTER);
        
        // Card 4: Learning Path
        JPanel learningPathCard = createDashboardCard("Learning Path", null);
        
        if (quizCount > 0) {
            // Get the most recent profile
            PersonalityProfile latestProfile = profiles.get(profiles.size() - 1);
            
            // Create learning path summary
            JPanel learningPathPanel = new JPanel(new BorderLayout());
            learningPathPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            
            String recommendation = app.getResultsManager().createSummarizedRecommendation(latestProfile);
            JTextArea recommendationText = new JTextArea(recommendation);
            recommendationText.setEditable(false);
            recommendationText.setLineWrap(true);
            recommendationText.setWrapStyleWord(true);
            recommendationText.setFont(new Font("Arial", Font.PLAIN, 13));
            recommendationText.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            recommendationText.setForeground(Color.WHITE); // Changed for better visibility
            recommendationText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            learningPathPanel.add(recommendationText, BorderLayout.CENTER);
            
            // Add view details button
            JButton viewRecommendationButton = new JButton("View Full Recommendation");
            viewRecommendationButton.setFont(new Font("Arial", Font.BOLD, 12));
            viewRecommendationButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
            viewRecommendationButton.setForeground(Color.BLACK); // Ensure black text on button
            viewRecommendationButton.setFocusPainted(false);
            viewRecommendationButton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            viewRecommendationButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewRecommendationButton.addActionListener(_ -> {
                app.getResultsManager().displayDetailedRecommendation(latestProfile);
            });
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            buttonPanel.add(viewRecommendationButton);
            learningPathPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            learningPathCard.add(learningPathPanel, BorderLayout.CENTER);
        } else {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            
            JLabel emptyLabel = new JLabel("Take a quiz to get personalized learning recommendations");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.WHITE); // Changed for better visibility
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            
            learningPathCard.add(emptyPanel, BorderLayout.CENTER);
        }
        
        // Add cards to panel
        cardsPanel.add(statsCard);
        cardsPanel.add(personalityCard);
        cardsPanel.add(actionsCard);
        cardsPanel.add(learningPathCard);
        
        contentPanel.add(cardsPanel, BorderLayout.CENTER);
        
        // Add sidebar and content to dashboard
        dashboardPanel.add(sidebarPanel, BorderLayout.WEST);
        dashboardPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add dashboard to home panel
        homePanel.add(dashboardPanel, BorderLayout.CENTER);
        
        homePanel.revalidate();
        homePanel.repaint();
    }
    
    private JPanel createMenuItemPanel(int iconType, String text, boolean isActive) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(isActive ? PersonalityQuizApp.LIGHT_BG_COLOR : PersonalityQuizApp.MEDIUM_BG_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(isActive ? PersonalityQuizApp.PRIMARY_COLOR : PersonalityQuizApp.MEDIUM_BG_COLOR, 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create custom icon panel
        JPanel iconPanel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color iconColor = isActive ? PersonalityQuizApp.PRIMARY_COLOR : Color.WHITE;
                g2d.setColor(iconColor);
                g2d.setStroke(new BasicStroke(1.5f));
                
                int size = 16;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                switch (iconType) {
                    case 0: // Home icon
                        // House shape
                        int[] xPoints = {x, x + size/2, x + size};
                        int[] yPoints = {y + size/2, y, y + size/2};
                        g2d.fillPolygon(xPoints, yPoints, 3);
                        g2d.fillRect(x + 2, y + size/2, size - 4, size/2);
                        break;
                    case 1: // Quiz icon
                        // Document with lines
                        g2d.drawRect(x, y, size, size);
                        g2d.drawLine(x + 3, y + 4, x + size - 3, y + 4);
                        g2d.drawLine(x + 3, y + 8, x + size - 3, y + 8);
                        g2d.drawLine(x + 3, y + 12, x + size - 3, y + 12);
                        break;
                    case 2: // Results icon
                        // Bar chart
                        g2d.drawRect(x, y, size, size);
                        g2d.fillRect(x + 2, y + 12, 2, 4);
                        g2d.fillRect(x + 6, y + 8, 2, 8);
                        g2d.fillRect(x + 10, y + 4, 2, 12);
                        g2d.fillRect(x + 14, y + 6, 2, 10);
                        break;
                    case 3: // Profile icon
                        // Person silhouette
                        g2d.drawOval(x + 4, y, 8, 8); // Head
                        g2d.drawRect(x + 2, y + 8, 12, 8); // Body
                        break;
                    case 4: // Settings icon
                        // Gear
                        g2d.drawOval(x + 4, y + 4, 8, 8);
                        for (int i = 0; i < 8; i++) {
                            double angle = Math.toRadians(i * 45);
                            int x1 = (int)(x + size/2 + Math.cos(angle) * 6);
                            int y1 = (int)(y + size/2 + Math.sin(angle) * 6);
                            int x2 = (int)(x + size/2 + Math.cos(angle) * 10);
                            int y2 = (int)(y + size/2 + Math.sin(angle) * 10);
                            g2d.drawLine(x1, y1, x2, y2);
                        }
                        break;
                    case 5: // Sign out icon
                        // Door with arrow
                        g2d.drawRect(x + 4, y, 12, 16);
                        g2d.drawLine(x, y + 8, x + 8, y + 8);
                        g2d.drawLine(x, y + 8, x + 4, y + 4);
                        g2d.drawLine(x, y + 8, x + 4, y + 12);
                        break;
                }
                
                g2d.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(24, 24);
            }
        };
        iconPanel.setOpaque(false);
        
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        textLabel.setForeground(isActive ? PersonalityQuizApp.PRIMARY_COLOR : Color.WHITE);
        
        panel.add(iconPanel, BorderLayout.WEST);
        panel.add(textLabel, BorderLayout.CENTER);
        
        // Add hover effect
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isActive) {
                    panel.setBackground(new Color(55, 55, 70));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isActive) {
                    panel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
                }
            }
        });
        
        return panel;
    }
    
    private JPanel createStatItem(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);
        
        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLabel.setForeground(new Color(220, 220, 220));
        
        panel.add(valueLabel, BorderLayout.NORTH);
        panel.add(labelLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void addMiniTraitBar(JPanel panel, String traitCode, int value) {
        JPanel traitPanel = new JPanel(new BorderLayout(10, 0));
        traitPanel.setOpaque(false);
        
        JLabel codeLabel = new JLabel(traitCode);
        codeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        codeLabel.setForeground(Color.WHITE);
        codeLabel.setPreferredSize(new Dimension(20, 20));
        traitPanel.add(codeLabel, BorderLayout.WEST);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(value);
        progressBar.setStringPainted(true);
        progressBar.setString(value + "%");
        progressBar.setForeground(getTraitColor(value));
        progressBar.setBackground(new Color(60, 60, 80));
        traitPanel.add(progressBar, BorderLayout.CENTER);
        
        panel.add(traitPanel);
    }
    
    private Color getTraitColor(int value) {
        if (value < 30) {
            return new Color(255, 102, 102); // Light red
        } else if (value < 70) {
            return new Color(255, 204, 0);   // Yellow
        } else {
            return new Color(102, 204, 0);   // Green
        }
    }
    
    private JButton createActionButton(String text, int iconType) {
        JButton button = new JButton(text) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw icon
                Color iconColor = getForeground();
                g2d.setColor(iconColor);
                g2d.setStroke(new BasicStroke(1.5f));
                
                int size = 16;
                int x = 15; // Position from left
                int y = (getHeight() - size) / 2;
                
                switch (iconType) {
                    case 1: // Quiz icon
                        // Document with lines
                        g2d.drawRect(x, y, size, size);
                        g2d.drawLine(x + 3, y + 4, x + size - 3, y + 4);
                        g2d.drawLine(x + 3, y + 8, x + size - 3, y + 8);
                        g2d.drawLine(x + 3, y + 12, x + size - 3, y + 12);
                        break;
                    case 3: // Profile icon
                        // Person silhouette
                        g2d.drawOval(x + 4, y, 8, 8); // Head
                        g2d.drawRect(x + 2, y + 8, 12, 8); // Body
                        break;
                }
                
                g2d.dispose();
            }
        };
        
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(10, 40, 10, 15) // Increased left padding for icon
        ));
        button.setBorder(BorderFactory.createCompoundBorder(
        	    new LineBorder(new Color(80, 80, 100), 1, true),
        	    BorderFactory.createEmptyBorder(10, 40, 10, 15) // Increased left padding for icon
        	));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 70, 90));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
            }
        });
        
        return button;
    }
    
    private JPanel createDashboardCard(String title, String category) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Header with title and optional category
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE); // Changed for better visibility
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        if (category != null) {
            JLabel categoryLabel = new JLabel(category);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 12));
            categoryLabel.setForeground(PersonalityQuizApp.ACCENT_COLOR);
            headerPanel.add(categoryLabel, BorderLayout.EAST);
        }
        
        card.add(headerPanel, BorderLayout.NORTH);
        
        return card;
    }
    
    private JTabbedPane findTabbedPane(Component component) {
        if (component instanceof JTabbedPane) {
            return (JTabbedPane) component;
        }
        
        if (component instanceof Container) {
            Container container = (Container) component;
            for (Component child : container.getComponents()) {
                JTabbedPane found = findTabbedPane(child);
                if (found != null) {
                    return found;
                }
            }
        }
        
        return null;
    }
}