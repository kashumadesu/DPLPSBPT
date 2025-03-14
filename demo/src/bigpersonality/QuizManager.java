package bigpersonality;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizManager {
    private PersonalityQuizApp app;
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int[] answers;
    
    public QuizManager(PersonalityQuizApp app, JFrame frame, JPanel mainPanel, CardLayout cardLayout) {
        this.app = app;
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        
        // Initialize quiz questions
        initializeQuestions();
    }
    
    private void initializeQuestions() {
        questions = new ArrayList<>();
        
        // Openness questions (5 questions)
        questions.add(new Question("I have a vivid imagination.", PersonalityTrait.OPENNESS));
        questions.add(new Question("I am interested in abstract ideas.", PersonalityTrait.OPENNESS));
        questions.add(new Question("I enjoy trying new activities and experiences.", PersonalityTrait.OPENNESS));
        questions.add(new Question("I appreciate art, music, and creative expression.", PersonalityTrait.OPENNESS));
        questions.add(new Question("I enjoy thinking about philosophical questions.", PersonalityTrait.OPENNESS));
        
        // Conscientiousness questions (5 questions)
        questions.add(new Question("I am always prepared for what I need to do.", PersonalityTrait.CONSCIENTIOUSNESS));
        questions.add(new Question("I pay attention to details and am thorough in my work.", PersonalityTrait.CONSCIENTIOUSNESS));
        questions.add(new Question("I like to follow a schedule and be organized.", PersonalityTrait.CONSCIENTIOUSNESS));
        questions.add(new Question("I complete tasks successfully and follow through with my plans.", PersonalityTrait.CONSCIENTIOUSNESS));
        questions.add(new Question("I strive for excellence in everything I do.", PersonalityTrait.CONSCIENTIOUSNESS));
        
        // Extraversion questions (5 questions)
        questions.add(new Question("I feel comfortable around people and in social situations.", PersonalityTrait.EXTRAVERSION));
        questions.add(new Question("I start conversations with strangers easily.", PersonalityTrait.EXTRAVERSION));
        questions.add(new Question("I enjoy being the center of attention in groups.", PersonalityTrait.EXTRAVERSION));
        questions.add(new Question("I feel energized after spending time with other people.", PersonalityTrait.EXTRAVERSION));
        questions.add(new Question("I am outgoing and sociable at parties and gatherings.", PersonalityTrait.EXTRAVERSION));
        
        // Agreeableness questions (5 questions)
        questions.add(new Question("I am interested in other people's problems and feelings.", PersonalityTrait.AGREEABLENESS));
        questions.add(new Question("I sympathize with others' feelings and show empathy.", PersonalityTrait.AGREEABLENESS));
        questions.add(new Question("I make people feel at ease and comfortable around me.", PersonalityTrait.AGREEABLENESS));
        questions.add(new Question("I avoid conflicts and try to maintain harmony in relationships.", PersonalityTrait.AGREEABLENESS));
        questions.add(new Question("I am willing to compromise and cooperate with others.", PersonalityTrait.AGREEABLENESS));
        
        // Neuroticism questions (5 questions)
        questions.add(new Question("I get stressed out easily in difficult situations.", PersonalityTrait.NEUROTICISM));
        questions.add(new Question("I worry about things more than others do.", PersonalityTrait.NEUROTICISM));
        questions.add(new Question("I am easily disturbed or upset by negative events.", PersonalityTrait.NEUROTICISM));
        questions.add(new Question("I experience mood swings and emotional ups and downs.", PersonalityTrait.NEUROTICISM));
        questions.add(new Question("I feel anxious or nervous in new or challenging situations.", PersonalityTrait.NEUROTICISM));
    }
    
    public void createQuizIntroPanel() {
        JPanel quizIntroPanel = new JPanel(new BorderLayout(20, 20));
        quizIntroPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        quizIntroPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JLabel titleLabel = new JLabel("Big 5 Personality Assessment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.BLACK);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerPanel.add(backButton, BorderLayout.EAST);
        
        quizIntroPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Create intro text with 16personalities style
        JPanel introTextPanel = new JPanel();
        introTextPanel.setLayout(new BoxLayout(introTextPanel, BoxLayout.Y_AXIS));
        introTextPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        JLabel welcomeLabel = new JLabel("Welcome to the Big 5 Personality Assessment!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea descriptionText = new JTextArea(
            "This assessment will help identify your personality traits based on the Big 5 model, " +
            "the most scientifically validated framework for understanding personality. " +
            "Your results will be used to create personalized learning path recommendations " +
            "tailored to your unique personality profile.\n\n" +
            "The assessment consists of 25 questions. For each statement, indicate how accurately " +
            "it describes you on a scale from Strongly Disagree to Strongly Agree.\n\n" +
            "The Big 5 personality traits are:"
        );
        descriptionText.setEditable(false);
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionText.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        descriptionText.setForeground(PersonalityQuizApp.TEXT_COLOR);
        descriptionText.setBorder(null);
        descriptionText.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Create trait descriptions
        JPanel traitsPanel = new JPanel(new GridLayout(5, 1, 10, 15));
        traitsPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        traitsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        traitsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        addTraitDescription(traitsPanel, "Openness", "Curiosity, creativity, and openness to new experiences", new Color(255, 165, 0)); // Orange
        addTraitDescription(traitsPanel, "Conscientiousness", "Organization, responsibility, and goal-directed behavior", new Color(65, 105, 225)); // Royal Blue
        addTraitDescription(traitsPanel, "Extraversion", "Sociability, assertiveness, and emotional expressiveness", new Color(255, 69, 0)); // Red-Orange
        addTraitDescription(traitsPanel, "Agreeableness", "Cooperation, compassion, and trust towards others", new Color(50, 205, 50)); // Lime Green
        addTraitDescription(traitsPanel, "Neuroticism", "Emotional sensitivity, anxiety, and tendency to experience negative emotions", new Color(148, 0, 211)); // Dark Violet
        
        JTextArea instructionText = new JTextArea(
            "Remember, there are no right or wrong answers. The most accurate results come from " +
            "honest responses that reflect your natural tendencies rather than how you wish to be.\n\n" +
            "Click 'Start Assessment' when you're ready to begin."
        );
        instructionText.setEditable(false);
        instructionText.setLineWrap(true);
        instructionText.setWrapStyleWord(true);
        instructionText.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionText.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        instructionText.setForeground(PersonalityQuizApp.TEXT_COLOR);
        instructionText.setBorder(null);
        instructionText.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add components to intro text panel
        introTextPanel.add(welcomeLabel);
        introTextPanel.add(Box.createVerticalStrut(15));
        introTextPanel.add(descriptionText);
        introTextPanel.add(Box.createVerticalStrut(10));
        introTextPanel.add(traitsPanel);
        introTextPanel.add(Box.createVerticalStrut(10));
        introTextPanel.add(instructionText);
        
        // Add scroll pane for intro text
        JScrollPane scrollPane = new JScrollPane(introTextPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        quizIntroPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton startButton = new JButton("Start Assessment");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(startButton);
        quizIntroPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        startButton.addActionListener(_ -> {
            // Reset quiz state
            currentQuestionIndex = 0;
            answers = new int[questions.size()];
            updateQuizPanel();
            cardLayout.show(mainPanel, "quiz");
        });
        
        backButton.addActionListener(_ -> {
            app.getDashboardManager().updateHomePanel();
            cardLayout.show(mainPanel, "home");
        });
        
        mainPanel.add(quizIntroPanel, "quizIntro");
    }
    
    private void addTraitDescription(JPanel panel, String trait, String description, Color color) {
        JPanel traitPanel = new JPanel(new BorderLayout(10, 5));
        traitPanel.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        traitPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, color),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel traitLabel = new JLabel(trait);
        traitLabel.setFont(new Font("Arial", Font.BOLD, 16));
        traitLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(PersonalityQuizApp.SECONDARY_TEXT_COLOR);
        
        traitPanel.add(traitLabel, BorderLayout.NORTH);
        traitPanel.add(descLabel, BorderLayout.CENTER);
        
        panel.add(traitPanel);
    }
    
    public void createQuizPanel() {
        JPanel quizPanel = new JPanel(new BorderLayout(20, 20));
        quizPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        quizPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // This panel will be dynamically updated for each question
        mainPanel.add(quizPanel, "quiz");
    }
    
    public void updateQuizPanel() {
        // Get the quiz panel
        JPanel quizPanel = (JPanel) app.getComponentByName(mainPanel, "quiz");
        quizPanel.removeAll();
        
        Question currentQuestion = questions.get(currentQuestionIndex);
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        headerPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Progress information
        JLabel progressLabel = new JLabel("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        progressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        progressLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        headerPanel.add(progressLabel, BorderLayout.WEST);
        
        // Add progress bar
        JProgressBar progressBar = new JProgressBar(0, questions.size());
        progressBar.setValue(currentQuestionIndex + 1);
        progressBar.setStringPainted(true);
        progressBar.setString((currentQuestionIndex + 1) + " / " + questions.size());
        progressBar.setForeground(PersonalityQuizApp.PRIMARY_COLOR);
        progressBar.setBackground(PersonalityQuizApp.LIGHT_BG_COLOR);
        progressBar.setPreferredSize(new Dimension(200, 20));
        headerPanel.add(progressBar, BorderLayout.EAST);
        
        quizPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create content panel with 16personalities style
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 100), 1, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        // Question text
        JLabel questionLabel = new JLabel("<html><body style='width: 500px'>" + currentQuestion.getText() + "</body></html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setForeground(PersonalityQuizApp.TEXT_COLOR);
        
        // Get trait color for styling
        Color traitColor;
        switch (currentQuestion.getTrait()) {
            case OPENNESS:
                traitColor = new Color(255, 165, 0); // Orange
                break;
            case CONSCIENTIOUSNESS:
                traitColor = new Color(65, 105, 225); // Royal Blue
                break;
            case EXTRAVERSION:
                traitColor = new Color(255, 69, 0); // Red-Orange
                break;
            case AGREEABLENESS:
                traitColor = new Color(50, 205, 50); // Lime Green
                break;
            case NEUROTICISM:
                traitColor = new Color(148, 0, 211); // Dark Violet
                break;
            default:
                traitColor = PersonalityQuizApp.PRIMARY_COLOR;
        }
        
        // Add colored indicator for trait type
        JPanel questionPanel = new JPanel(new BorderLayout(15, 0));
        questionPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        questionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, traitColor),
            BorderFactory.createEmptyBorder(0, 15, 20, 0)
        ));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        
        contentPanel.add(questionPanel, BorderLayout.NORTH);
        
        // Create options panel with horizontal radio buttons 
        JPanel optionsPanel = new JPanel(new BorderLayout(0, 15));
        optionsPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        
        // Radio buttons in a row with labels on sides
        JPanel radioPanel = new JPanel(new GridLayout(1, 7));
        radioPanel.setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
        ButtonGroup buttonGroup = new ButtonGroup();

        // Custom radio button colors
        Color agreeColor = new Color(34, 197, 94);    // Green
        Color neutralColor = new Color(156, 163, 175); // Gray
        Color disagreeColor = new Color(168, 85, 247); // Purple

        // Add "Agree" label on the left
        JLabel agreeLabel = new JLabel("Agree");
        agreeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        agreeLabel.setForeground(agreeColor);
        agreeLabel.setHorizontalAlignment(JLabel.CENTER);
        radioPanel.add(agreeLabel);

        JRadioButton[] radioButtons = new JRadioButton[5];
        for (int i = 0; i < 5; i++) {
            final int buttonIndex = i;
            radioButtons[i] = new JRadioButton() {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int size = 60;
                    int x = (getWidth() - size) / 2;
                    int y = (getHeight() - size) / 2;
                    
                    // Determine color based on position
                    Color buttonColor;
                    if (buttonIndex < 2) {
                        buttonColor = agreeColor;
                    } else if (buttonIndex == 2) {
                        buttonColor = neutralColor;
                    } else {
                        buttonColor = disagreeColor;
                    }
                    
                    // Draw circle
                    g2d.setColor(buttonColor);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawOval(x, y, size, size);
                    
                    // Fill circle if selected
                    if (getModel().isSelected()) {
                        g2d.setColor(buttonColor);
                        g2d.fillOval(x + 4, y + 4, size - 8, size - 8);
                    }
                    
                    g2d.dispose();
                }
                
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(40, 40);
                }
            };
            
            radioButtons[i].setBackground(PersonalityQuizApp.MEDIUM_BG_COLOR);
            radioButtons[i].setHorizontalAlignment(JLabel.CENTER);
            radioButtons[i].setBorderPainted(false);
            radioButtons[i].setFocusPainted(false);
            radioButtons[i].setContentAreaFilled(false);
            
            final int value = 5 - i; // Reverse the values (5,4,3,2,1)
            radioButtons[i].addActionListener(_ -> answers[currentQuestionIndex] = value);
            buttonGroup.add(radioButtons[i]);
            radioPanel.add(radioButtons[i]);
        }

        // Add "Disagree" label on the right
        JLabel disagreeLabel = new JLabel("Disagree");
        disagreeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        disagreeLabel.setForeground(disagreeColor);
        disagreeLabel.setHorizontalAlignment(JLabel.CENTER);
        radioPanel.add(disagreeLabel);

        // If this question has been answered before, select the appropriate radio button
        if (answers[currentQuestionIndex] > 0) {
            radioButtons[5 - answers[currentQuestionIndex]].setSelected(true);
        }
        
        optionsPanel.add(radioPanel, BorderLayout.CENTER);
        
        contentPanel.add(optionsPanel, BorderLayout.CENTER);
        
        quizPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Create navigation buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtonPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtonPanel.setBackground(PersonalityQuizApp.DARK_BG_COLOR);
        
        JButton prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
        prevButton.setBackground(new Color(100, 100, 120));
        prevButton.setForeground(Color.BLACK);
        prevButton.setFocusPainted(false);
        prevButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        prevButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevButton.setEnabled(currentQuestionIndex > 0);
        
        JButton nextButton = new JButton(currentQuestionIndex == questions.size() - 1 ? "Finish" : "Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(PersonalityQuizApp.PRIMARY_COLOR);
        nextButton.setForeground(Color.BLACK);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        leftButtonPanel.add(prevButton);
        rightButtonPanel.add(nextButton);
        
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        
        quizPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        prevButton.addActionListener(_ -> {
            currentQuestionIndex--;
            updateQuizPanel();
        });
        
        nextButton.addActionListener(_ -> {
            if (answers[currentQuestionIndex] == 0) {
                JOptionPane.showMessageDialog(frame, "Please select an answer before continuing", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (currentQuestionIndex == questions.size() - 1) {
                // Last question, calculate results
                PersonalityProfile profile = calculatePersonalityProfile();
                
                // Add date to profile
                profile.setDate(new Date());
                
                // Save profile to user's profiles
                app.addUserProfile(profile);
                
                app.getResultsManager().displayResults(profile);
                cardLayout.show(mainPanel, "results");
            } else {
                // Move to next question
                currentQuestionIndex++;
                updateQuizPanel();
            }
        });
        
        quizPanel.revalidate();
        quizPanel.repaint();
    }
    
    private PersonalityProfile calculatePersonalityProfile() {
        int opennessSum1 = 0;
        int conscientiousnessSum1 = 0;
        int extraversionSum1 = 0;
        int agreeablenessSum = 0;
        int neuroticismSum = 0;
        
        int opennessCount = 0;
        int conscientiousnessCount = 0;
        int extraversionCount = 0;
        int agreeablenessCount = 0;
        int neuroticismCount = 0;
        
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int answer = answers[i];
            
            switch (question.getTrait()) {
                case OPENNESS:
                    opennessSum1 += answer;
                    opennessCount++;
                    break;
                case CONSCIENTIOUSNESS:
                    conscientiousnessSum1 += answer;
                    conscientiousnessCount++;
                    break;
                case EXTRAVERSION:
                    extraversionSum1 += answer;
                    extraversionCount++;
                    break;
                case AGREEABLENESS:
                    agreeablenessSum += answer;
                    agreeablenessCount++;
                    break;
                case NEUROTICISM:
                    neuroticismSum += answer;
                    neuroticismCount++;
                    break;
            }
        }
        
        // Calculate percentages (1-5 scale converted to 0-100%)
        int openness = (int) ((opennessSum1 / (double) (opennessCount * 5)) * 100);
        int conscientiousness = (int) ((conscientiousnessSum1 / (double) (conscientiousnessCount * 5)) * 100);
        int extraversion = (int) ((extraversionSum1 / (double) (extraversionCount * 5)) * 100);
        int agreeableness = (int) ((agreeablenessSum / (double) (agreeablenessCount * 5)) * 100);
        int neuroticism = (int) ((neuroticismSum / (double) (neuroticismCount * 5)) * 100);
        
        return new PersonalityProfile(openness, conscientiousness, extraversion, agreeableness, neuroticism);
    }
    
    public List<Question> getQuestions() {
        return questions;
    }
}