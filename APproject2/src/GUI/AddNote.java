package GUI;

import Database.Operation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class AddNote extends JFrame {

    private JLabel titleLabel;
    private JTextField titleField;
    private JLabel typeLabel;
    private JTextField typeField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JButton backButton;
    private Operation operation;

    public AddNote() {
        operation = new Operation();
        initialize();
    }

    private void initialize() {
        setTitle("Add Note");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 2, 5)); // Use GridLayout with one row and four columns
        titleLabel = new JLabel("Title:");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        inputPanel.add(titleLabel);

        titleField = new JTextField();
        inputPanel.add(titleField);

        typeLabel = new JLabel("Type:");
        typeLabel.setFont(typeLabel.getFont().deriveFont(Font.BOLD));
        inputPanel.add(typeLabel);

        typeField = new JTextField();
        inputPanel.add(typeField);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setFont(contentLabel.getFont().deriveFont(Font.BOLD));
        mainPanel.add(contentLabel, BorderLayout.CENTER);

        contentArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(contentArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        saveButton = new JButton("Save");
        saveButton.setFont(saveButton.getFont().deriveFont(Font.BOLD));
        buttonPanel.add(saveButton);

        backButton = new JButton("Back");
        backButton.setFont(backButton.getFont().deriveFont(Font.BOLD));
        buttonPanel.add(backButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveNote();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateBack();
            }
        });
    }

    private void saveNote() {
        String title = titleField.getText();
        String type = typeField.getText();
        String content = contentArea.getText();
        Date now = new Date();

        try {
            operation.saveUS(title, content, type, now, now);
            JOptionPane.showMessageDialog(this, "Note saved successfully.");
            navigateBack();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving note: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateBack() {
        NotePadGUI notePadGUI = new NotePadGUI();
        notePadGUI.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AddNote().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
