import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LanguageLearningApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private HashMap<String, String> flashcards;
    private JLabel wordLabel;
    private JTextField answerField;
    private JLabel feedbackLabel;
    private int currentIndex;
    private String[] words;

    public LanguageLearningApp() {
        frame = new JFrame("Language Learning App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        flashcards = new HashMap<>();
        flashcards.put("Hello", "Hola");
        flashcards.put("Thank you", "Gracias");
        flashcards.put("Goodbye", "Adiós");

        words = flashcards.keySet().toArray(new String[0]);
        currentIndex = 0;

        setupUI();

        frame.setVisible(true);
    }

    private void setupUI() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel lessonPanel = createLessonPanel();
        JPanel quizPanel = createQuizPanel();

        cardPanel.add(lessonPanel, "Lesson");
        cardPanel.add(quizPanel, "Quiz");

        frame.add(createNavigationPanel(), BorderLayout.NORTH);
        frame.add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createLessonPanel() {
        JPanel lessonPanel = new JPanel(new BorderLayout());
        JLabel lessonLabel = new JLabel("Today's Lesson", JLabel.CENTER);
        lessonLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextArea lessonContent = new JTextArea();
        lessonContent.setEditable(false);
        lessonContent.setFont(new Font("Arial", Font.PLAIN, 16));

        StringBuilder content = new StringBuilder();
        for (String word : flashcards.keySet()) {
            content.append(word).append(" - ").append(flashcards.get(word)).append("\n");
        }
        lessonContent.setText(content.toString());

        lessonPanel.add(lessonLabel, BorderLayout.NORTH);
        lessonPanel.add(new JScrollPane(lessonContent), BorderLayout.CENTER);

        return lessonPanel;
    }

    private JPanel createQuizPanel() {
        JPanel quizPanel = new JPanel(new GridLayout(4, 1));
        wordLabel = new JLabel("Translate: " + words[currentIndex], JLabel.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        feedbackLabel = new JLabel("", JLabel.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        quizPanel.add(wordLabel);
        quizPanel.add(answerField);
        quizPanel.add(submitButton);
        quizPanel.add(feedbackLabel);

        return quizPanel;
    }

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout());
        JButton lessonButton = new JButton("Lesson");
        lessonButton.setFont(new Font("Arial", Font.BOLD, 16));
        JButton quizButton = new JButton("Quiz");
        quizButton.setFont(new Font("Arial", Font.BOLD, 16));

        lessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Lesson");
            }
        });

        quizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Quiz");
            }
        });

        navigationPanel.add(lessonButton);
        navigationPanel.add(quizButton);

        return navigationPanel;
    }

    private void checkAnswer() {
        String correctAnswer = flashcards.get(words[currentIndex]);
        String userAnswer = answerField.getText().trim();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            feedbackLabel.setText("Correct!");
            nextWord();
        } else {
            feedbackLabel.setText("Try again!");
        }
    }

    private void nextWord() {
        currentIndex = (currentIndex + 1) % words.length;
        wordLabel.setText("Translate: " + words[currentIndex]);
        answerField.setText("");
    }

    public static void main(String[] args) {
        new LanguageLearningApp();
    }
}
