package bigpersonality;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ResultsManager {
    private PersonalityQuizApp app;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public ResultsManager(PersonalityQuizApp app, JFrame frame, JPanel mainPanel, CardLayout cardLayout) {
        this.app = app;
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }
    
    public void createResultsPanel() {
        JPanel resultsPanel = new JPanel(new BorderLayout(20, 20));
        resultsPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // This panel will be dynamically updated with results
        mainPanel.add(resultsPanel, "results");
    }
    
    public void displayResults(PersonalityProfile profile) {
        // Get the results panel
        JPanel resultsPanel = (JPanel) app.getComponentByName(mainPanel, "results");
        resultsPanel.removeAll();
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JLabel titleLabel = new JLabel("Your Personality Profile Results");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        resultsPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create scrollable content panel
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        // Create summary panel
        JPanel summaryPanel = new JPanel(new BorderLayout(20, 20));
        summaryPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        summaryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        summaryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Create personality type summary
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        typePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        
        JLabel summaryLabel = new JLabel("Your Personality Overview");
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        summaryLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        summaryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Determine dominant traits (traits with scores > 70%)
        List<String> dominantTraits = new ArrayList<>();
        if (profile.getOpenness() > 70) dominantTraits.add("highly open to new experiences");
        if (profile.getConscientiousness() > 70) dominantTraits.add("very conscientious");
        if (profile.getExtraversion() > 70) dominantTraits.add("strongly extraverted");
        if (profile.getAgreeableness() > 70) dominantTraits.add("very agreeable");
        if (profile.getNeuroticism() > 70) dominantTraits.add("more sensitive to stress");
        
        String personalitySummary;
        if (!dominantTraits.isEmpty()) {
            personalitySummary = "Based on your responses, you are " + String.join(", ", dominantTraits) + ".";
        } else {
            personalitySummary = "Based on your responses, you have a balanced personality profile without strongly dominant traits.";
        }
        
        JTextArea summaryText = new JTextArea(personalitySummary);
        summaryText.setEditable(false);
        summaryText.setLineWrap(true);
        summaryText.setWrapStyleWord(true);
        summaryText.setFont(new Font("Arial", Font.PLAIN, 16));
        summaryText.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        summaryText.setForeground(PersonalityQuizApp.TEXT_COLOR);
        summaryText.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        summaryText.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        typePanel.add(summaryLabel);
        typePanel.add(Box.createVerticalStrut(10));
        typePanel.add(summaryText);
        
        summaryPanel.add(typePanel, BorderLayout.CENTER);
        
        // Create traits visualization panel
        JPanel traitsPanel = new JPanel(new BorderLayout(20, 20));
        traitsPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        traitsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        traitsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel traitsLabel = new JLabel("Your Big 5 Personality Traits");
        traitsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        traitsLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        // Create modern visualization for traits
        JPanel traitsVisualization = new JPanel(new GridLayout(5, 1, 0, 20));
        traitsVisualization.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        traitsVisualization.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Add trait bars with descriptions
        addResultTraitBar(traitsVisualization, "Openness", profile.getOpenness(), 
                         "Openness reflects creativity, curiosity, and preference for variety and intellectual stimulation.",
                         new Color(255, 165, 0)); // Orange
        
        addResultTraitBar(traitsVisualization, "Conscientiousness", profile.getConscientiousness(),
                         "Conscientiousness reflects organization, responsibility, and goal-directed behavior.",
                         new Color(65, 105, 225)); // Royal Blue
        
        addResultTraitBar(traitsVisualization, "Extraversion", profile.getExtraversion(),
                         "Extraversion reflects sociability, assertiveness, and emotional expressiveness.",
                         new Color(255, 69, 0)); // Red-Orange
        
        addResultTraitBar(traitsVisualization, "Agreeableness", profile.getAgreeableness(),
                         "Agreeableness reflects cooperation, compassion, and trust towards others.",
                         new Color(50, 205, 50)); // Lime Green
        
        addResultTraitBar(traitsVisualization, "Neuroticism", profile.getNeuroticism(),
                         "Neuroticism reflects emotional sensitivity, anxiety, and tendency to experience negative emotions.",
                         new Color(148, 0, 211)); // Dark Violet
        
        traitsPanel.add(traitsLabel, BorderLayout.NORTH);
        traitsPanel.add(traitsVisualization, BorderLayout.CENTER);
        
        // Create learning path recommendation panel
        JPanel recommendationPanel = new JPanel(new BorderLayout(20, 20));
        recommendationPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        recommendationPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        recommendationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel recommendationLabel = new JLabel("Your Personalized Learning Path");
        recommendationLabel.setFont(new Font("Arial", Font.BOLD, 20));
        recommendationLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        JPanel recommendationContent = new JPanel(new BorderLayout());
        recommendationContent.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        recommendationContent.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Generate learning path recommendations
        String learningPathRecommendation = generateLearningPathRecommendation(profile);
        JTextArea learningPathText = new JTextArea(learningPathRecommendation);
        learningPathText.setEditable(false);
        learningPathText.setLineWrap(true);
        learningPathText.setWrapStyleWord(true);
        learningPathText.setFont(new Font("Arial", Font.PLAIN, 14));
        learningPathText.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        learningPathText.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        JScrollPane recommendationScroll = new JScrollPane(learningPathText);
        recommendationScroll.setBorder(null);
        recommendationContent.add(recommendationScroll, BorderLayout.CENTER);
        
        recommendationPanel.add(recommendationLabel, BorderLayout.NORTH);
        recommendationPanel.add(recommendationContent, BorderLayout.CENTER);
        
        // Add all panels to scroll content
        scrollContent.add(summaryPanel);
        scrollContent.add(traitsPanel);
        scrollContent.add(recommendationPanel);
        
        // Add scroll pane for content
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton homeButton = new JButton("Back to Dashboard");
        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton profileButton = new JButton("View All Results");
        profileButton.setFont(new Font("Arial", Font.BOLD, 14));
        profileButton.setBackground(PersonalityQuizApp.ACCENT_COLOR);
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(homeButton);
        buttonPanel.add(profileButton);
        resultsPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        homeButton.addActionListener(_ -> {
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        profileButton.addActionListener(_ -> {
            app.getProfileManager().updateProfilePanel();
            cardLayout.show(mainPanel, "profile");
            // Select the Results tab
            JTabbedPane tabbedPane = findTabbedPane(app.getComponentByName(mainPanel, "profile"));
            if (tabbedPane != null) {
                tabbedPane.setSelectedIndex(1);
            }
        });
        
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
    
    void addResultTraitBar(JPanel panel, String traitName, int value, String description, Color traitColor) {
        JPanel traitPanel = new JPanel(new BorderLayout(15, 10));
        traitPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        traitPanel.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, traitColor));
        
        // Trait header with name and value
        JPanel headerPanel = new JPanel(new BorderLayout(5, 0));
        headerPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        
        JLabel nameLabel = new JLabel(traitName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        JLabel valueLabel = new JLabel(value + "%");
        valueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valueLabel.setForeground(traitColor);
        
        headerPanel.add(nameLabel, BorderLayout.WEST);
        headerPanel.add(valueLabel, BorderLayout.EAST);
        
        // Progress bar
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(value);
        progressBar.setStringPainted(false);
        progressBar.setForeground(traitColor);
        progressBar.setBackground(new Color(60, 60, 80));
        
        // Description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        descLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
        
        // Level description
        String levelDesc;
        if (value < 30) {
            levelDesc = "Low";
        } else if (value < 70) {
            levelDesc = "Moderate";
        } else {
            levelDesc = "High";
        }
        
        JLabel levelLabel = new JLabel("Level: " + levelDesc);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 14));
        levelLabel.setForeground(traitColor);
        
        // Add components to trait panel
        JPanel contentPanel = new JPanel(new BorderLayout(0, 5));
        contentPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(progressBar, BorderLayout.CENTER);
        
        JPanel infoPanel = new JPanel(new BorderLayout(10, 0));
        infoPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        infoPanel.add(descLabel, BorderLayout.WEST);
        infoPanel.add(levelLabel, BorderLayout.EAST);
        
        contentPanel.add(infoPanel, BorderLayout.SOUTH);
        
        traitPanel.add(contentPanel, BorderLayout.CENTER);
        
        panel.add(traitPanel);
    }
    
    public String createSummarizedRecommendation(PersonalityProfile profile) {
        StringBuilder sb = new StringBuilder();
        
        // Add personality type summary
        sb.append("Based on your personality profile, you are ");
        
        // Determine dominant traits (traits with scores > 70%)
        List<String> dominantTraits = new ArrayList<>();
        if (profile.getOpenness() > 70) dominantTraits.add("highly open to new experiences");
        if (profile.getConscientiousness() > 70) dominantTraits.add("very conscientious");
        if (profile.getExtraversion() > 70) dominantTraits.add("strongly extraverted");
        if (profile.getAgreeableness() > 70) dominantTraits.add("very agreeable");
        if (profile.getNeuroticism() > 70) dominantTraits.add("more sensitive to stress");
        
        if (!dominantTraits.isEmpty()) {
            sb.append(String.join(", ", dominantTraits)).append(".\n\n");
        } else {
            sb.append("balanced across all personality dimensions.\n\n");
        }
        
        // Determine primary learning style based on trait combinations
        sb.append("Recommended Learning Style: ");
        
        if (profile.getOpenness() > 60 && profile.getExtraversion() > 60) {
            sb.append("Interactive & Creative Learning\n");
            sb.append("You thrive in discussion-based courses with creative components, collaborative projects with room for innovation, and interactive workshops where you can express your ideas and engage with others.");
        } else if (profile.getOpenness() > 60 && profile.getExtraversion() <= 50) {
            sb.append("Independent Creative Learning\n");
            sb.append("You excel with self-paced courses that include creative assignments, research-based learning with conceptual exploration, and independent study that allows you to dive deep into subjects that interest you.");
        } else if (profile.getConscientiousness() > 60 && profile.getOpenness() <= 50) {
            sb.append("Structured Practical Learning\n");
            sb.append("You perform best with well-organized courses that have clear objectives, step-by-step tutorials with practical exercises, and certification programs with defined milestones to track your progress.");
        } else if (profile.getConscientiousness() > 60 && profile.getOpenness() > 60) {
            sb.append("Systematic Innovative Learning\n");
            sb.append("You benefit from project-based learning with clear guidelines but room for creativity, research methodologies that allow for exploration within structure, and courses that encourage critical thinking.");
        } else if (profile.getExtraversion() > 60 && profile.getAgreeableness() > 60) {
            sb.append("Collaborative & Supportive Learning\n");
            sb.append("You thrive in team-based learning environments, peer teaching and mentoring programs, and community service learning projects where you can help others while learning.");
        } else {
            sb.append("Balanced Learning Approach\n");
            sb.append("You benefit from a mix of individual and group activities, a combination of structured content and creative exploration, and varied learning formats including reading, discussion, and practice.");
        }
        
        return sb.toString();
    }
    
    public String generateLearningPathRecommendation(PersonalityProfile profile) {
        StringBuilder sb = new StringBuilder();
        
        // Overall personality type
        sb.append("Your Personality Overview:\n\n");
        
        // Determine dominant traits (traits with scores > 70%)
        List<String> dominantTraits = new ArrayList<>();
        if (profile.getOpenness() > 70) dominantTraits.add("Openness to Experience");
        if (profile.getConscientiousness() > 70) dominantTraits.add("Conscientiousness");
        if (profile.getExtraversion() > 70) dominantTraits.add("Extraversion");
        if (profile.getAgreeableness() > 70) dominantTraits.add("Agreeableness");
        if (profile.getNeuroticism() > 70) dominantTraits.add("Neuroticism");
        
        if (!dominantTraits.isEmpty()) {
            sb.append("Your dominant traits are: ").append(String.join(", ", dominantTraits)).append(".\n\n");
        } else {
            sb.append("You have a balanced personality profile without strongly dominant traits.\n\n");
        }
        
        // Learning style recommendations based on traits
        sb.append("Recommended Learning Approaches:\n\n");
        
        // Openness recommendations
        sb.append("Openness to Experience (").append(profile.getOpenness()).append("%):\n");
        if (profile.getOpenness() > 70) {
            sb.append("• You thrive with creative, conceptual learning approaches\n");
            sb.append("• Explore interdisciplinary subjects and connect diverse ideas\n");
            sb.append("• Consider project-based learning with room for innovation\n");
            sb.append("• Seek out courses that encourage critical thinking and exploration\n");
            sb.append("• Look for learning environments that value originality and new perspectives\n");
        } else if (profile.getOpenness() < 30) {
            sb.append("• You may prefer practical, concrete learning materials\n");
            sb.append("• Focus on established methods and clear instructions\n");
            sb.append("• Consider structured learning paths with clear objectives\n");
            sb.append("• Look for courses with proven, traditional approaches\n");
            sb.append("• Benefit from learning environments with consistent, reliable methods\n");
        } else {
            sb.append("• Balance theoretical concepts with practical applications\n");
            sb.append("• Mix established methods with some creative exploration\n");
            sb.append("• Appreciate both traditional and innovative approaches to learning\n");
            sb.append("• Can adapt to various teaching styles and course structures\n");
            sb.append("• Benefit from a mix of concrete examples and abstract concepts\n");
        }
        sb.append("\n");
        
        // Conscientiousness recommendations
        sb.append("Conscientiousness (").append(profile.getConscientiousness()).append("%):\n");
        if (profile.getConscientiousness() > 70) {
            sb.append("• Create detailed study schedules and stick to them\n");
            sb.append("• Use systematic approaches with clear milestones\n");
            sb.append("• Set specific goals and track your progress\n");
            sb.append("• Benefit from courses with well-organized structure and clear expectations\n");
            sb.append("• Excel in learning environments that reward diligence and thoroughness\n");
        } else if (profile.getConscientiousness() < 30) {
            sb.append("• Use external accountability systems or study groups\n");
            sb.append("• Break learning into smaller, manageable chunks\n");
            sb.append("• Consider gamified learning to maintain motivation\n");
            sb.append("• Look for courses with frequent deadlines and check-ins\n");
            sb.append("• Benefit from learning environments with built-in reminders and structure\n");
        } else {
            sb.append("• Balance structured learning with flexibility\n");
            sb.append("• Set moderate goals with some room for adjustment\n");
            sb.append("• Combine planning with adaptability in your approach\n");
            sb.append("• Can work well with both structured and flexible learning formats\n");
            sb.append("• Benefit from a mix of self-directed and externally structured learning\n");
        }
        sb.append("\n");
        
        // Extraversion recommendations
        sb.append("Extraversion (").append(profile.getExtraversion()).append("%):\n");
        if (profile.getExtraversion() > 70) {
            sb.append("• Engage in group learning and discussion-based classes\n");
            sb.append("• Consider teaching concepts to others to reinforce learning\n");
            sb.append("• Join study groups or collaborative learning environments\n");
            sb.append("• Look for courses with interactive components and verbal participation\n");
            sb.append("• Benefit from learning environments with social engagement and energy\n");
        } else if (profile.getExtraversion() < 30) {
            sb.append("• Focus on independent study with self-paced materials\n");
            sb.append("• Consider online courses with minimal group interaction\n");
            sb.append("• Create a quiet, distraction-free learning environment\n");
            sb.append("• Look for courses that allow for reflection and independent work\n");
            sb.append("• Benefit from learning formats that don't require extensive social interaction\n");
        } else {
            sb.append("• Balance individual study with occasional group work\n");
            sb.append("• Mix self-paced learning with discussion-based activities\n");
            sb.append("• Appreciate both collaborative and independent learning approaches\n");
            sb.append("• Can adapt to both social and solitary learning environments\n");
            sb.append("• Benefit from courses that offer a mix of interaction styles\n");
        }
        sb.append("\n");
        
        // Agreeableness recommendations
        sb.append("Agreeableness (").append(profile.getAgreeableness()).append("%):\n");
        if (profile.getAgreeableness() > 70) {
            sb.append("• Seek cooperative learning environments\n");
            sb.append("• Consider service-learning or helping others understand concepts\n");
            sb.append("• Look for mentorship opportunities\n");
            sb.append("• Thrive in learning environments that emphasize teamwork and support\n");
            sb.append("• Benefit from courses with collaborative projects and peer feedback\n");
        } else if (profile.getAgreeableness() < 30) {
            sb.append("• Focus on independent, self-directed learning\n");
            sb.append("• Consider competitive learning environments\n");
            sb.append("• Develop skills in constructive criticism and debate\n");
            sb.append("• May excel in environments that reward individual achievement\n");
            sb.append("• Look for courses that value critical analysis and challenging ideas\n");
        } else {
            sb.append("• Balance cooperative and independent learning\n");
            sb.append("• Mix collaborative projects with individual assignments\n");
            sb.append("• Practice both giving and receiving feedback\n");
            sb.append("• Can work well in both competitive and cooperative settings\n");
            sb.append("• Benefit from learning environments that balance teamwork with individual contribution\n");
        }
        sb.append("\n");
        
        // Neuroticism recommendations
        sb.append("Neuroticism (").append(profile.getNeuroticism()).append("%):\n");
        if (profile.getNeuroticism() > 70) {
            sb.append("• Incorporate stress management techniques into your study routine\n");
            sb.append("• Break learning into smaller, manageable goals to reduce anxiety\n");
            sb.append("• Consider mindfulness practices before challenging learning sessions\n");
            sb.append("• Look for courses with clear expectations and supportive feedback\n");
            sb.append("• Benefit from learning environments that acknowledge emotional aspects of learning\n");
        } else if (profile.getNeuroticism() < 30) {
            sb.append("• Challenge yourself with ambitious learning goals\n");
            sb.append("• Consider fast-paced learning environments\n");
            sb.append("• Take on leadership roles in group learning settings\n");
            sb.append("• Excel in high-pressure or competitive learning situations\n");
            sb.append("• Benefit from courses that push boundaries and present challenges\n");
        } else {
            sb.append("• Balance challenging material with adequate preparation\n");
            sb.append("• Practice basic stress management for difficult topics\n");
            sb.append("• Recognize when to push forward and when to take breaks\n");
            sb.append("• Can handle moderate pressure while maintaining emotional balance\n");
            sb.append("• Benefit from learning environments with reasonable challenges and support\n");
        }
        sb.append("\n");
        
        // Specific learning path recommendations
        sb.append("Recommended Learning Paths:\n\n");
        
        // Determine primary learning style based on trait combinations
        if (profile.getOpenness() > 60 && profile.getExtraversion() > 60) {
            sb.append("Interactive & Creative Learning:\n");
            sb.append("• Discussion-based courses with creative components\n");
            sb.append("• Collaborative projects with room for innovation\n");
            sb.append("• Workshops and interactive seminars\n");
            sb.append("• Debate clubs and creative problem-solving groups\n");
            sb.append("• Courses that combine social interaction with creative exploration\n");
        } else if (profile.getOpenness() > 60 && profile.getExtraversion() <= 50) {
            sb.append("Independent Creative Learning:\n");
            sb.append("• Self-paced courses with creative assignments\n");
            sb.append("• Research-based learning with conceptual exploration\n");
            sb.append("• Arts and design-focused independent study\n");
            sb.append("• Online courses that allow for creative expression\n");
            sb.append("• Learning environments that value deep thinking and originality\n");
        } else if (profile.getConscientiousness() > 60 && profile.getOpenness() <= 50) {
            sb.append("Structured Practical Learning:\n");
            sb.append("• Well-organized courses with clear objectives\n");
            sb.append("• Step-by-step tutorials and practical exercises\n");
            sb.append("• Certification programs with defined milestones\n");
            sb.append("• Traditional classroom settings with clear expectations\n");
            sb.append("• Learning paths with measurable outcomes and practical applications\n");
        } else if (profile.getConscientiousness() > 60 && profile.getOpenness() > 60) {
            sb.append("Systematic Innovative Learning:\n");
            sb.append("Systematic Innovative Learning:\n");
            sb.append("• Project-based learning with clear guidelines but room for creativity\n");
            sb.append("• Research methodologies and experimental design\n");
            sb.append("• Structured courses that encourage critical thinking\n");
            sb.append("• Programs that balance innovation with methodical approaches\n");
            sb.append("• Learning environments that reward both thoroughness and originality\n");
        } else if (profile.getExtraversion() > 60 && profile.getAgreeableness() > 60) {
            sb.append("Collaborative & Supportive Learning:\n");
            sb.append("• Team-based learning environments\n");
            sb.append("• Peer teaching and mentoring programs\n");
            sb.append("• Community service learning projects\n");
            sb.append("• Group discussions and collaborative problem-solving\n");
            sb.append("• Learning environments that emphasize social connection and helping others\n");
        } else {
            sb.append("Balanced Learning Approach:\n");
            sb.append("• Mix of individual and group activities\n");
            sb.append("• Combination of structured content and creative exploration\n");
            sb.append("• Varied learning formats (reading, discussion, practice)\n");
            sb.append("• Flexible programs that offer multiple learning pathways\n");
            sb.append("• Learning environments that accommodate different learning preferences\n");
        }
        
        return sb.toString();
    }
    
    public void displayDetailedRecommendation(PersonalityProfile profile) {
        JDialog dialog = new JDialog(frame, "Detailed Learning Path Recommendation", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(frame);
        dialog.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("Your Personalized Learning Path");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Generate full learning path recommendations
        String learningPathRecommendation = generateLearningPathRecommendation(profile);
        JTextArea learningPathText = new JTextArea(learningPathRecommendation);
        learningPathText.setEditable(false);
        learningPathText.setLineWrap(true);
        learningPathText.setWrapStyleWord(true);
        learningPathText.setFont(new Font("Arial", Font.PLAIN, 14));
        learningPathText.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        learningPathText.setForeground(PersonalityQuizApp.TEXT_COLOR);
        learningPathText.setCaretColor(PersonalityQuizApp.TEXT_COLOR);
        
        JScrollPane scrollPane = new JScrollPane(learningPathText);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(_ -> dialog.dispose());
        
        buttonPanel.add(closeButton);
        
        // Add components to content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(mainPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.setVisible(true);
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