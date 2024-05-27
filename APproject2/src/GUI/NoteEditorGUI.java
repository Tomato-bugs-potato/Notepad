package GUI;


import Database.Operation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import DataType.*;

public class NoteEditorGUI extends JFrame {
    private JTextField titleField;
    private JTextField typeField;
    private JTextArea contentArea;
    private JButton saveButton;
    private JButton backButton;
    private int noteId;

    public NoteEditorGUI(int noteId) {
        this.noteId = noteId;
        initialize();
    }

    private void initialize() {
        setTitle("Edit Note");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(mainPanel);

        JPanel inputPanel = new JPanel(new GridBagLayout()); // Changed layout to GridBagLayout
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        inputPanel.add(titleLabel, gbc);

        titleField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(typeLabel.getFont().deriveFont(Font.BOLD));
        inputPanel.add(typeLabel, gbc);

        typeField = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(typeField, gbc);

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

        loadNote();
    }

    private void loadNote() {
        try {
            Operation operation = new Operation();
            Note note = operation.Find(noteId);
            if (note != null) {
                titleField.setText(note.getTitle());
                typeField.setText(note.getType());
                contentArea.setText(note.getContent());
            } else {
                JOptionPane.showMessageDialog(this, "Note not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading note: " + ex.getMessage());
        }
    }

    private void saveNote() {
        String title = titleField.getText();
        String type = typeField.getText();
        String content = contentArea.getText();

        try {
            Operation operation = new Operation();
            operation.save(noteId, title, content, type, new java.util.Date());
            JOptionPane.showMessageDialog(this, "Note saved successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving note: " + ex.getMessage());
        }
    }

    private void navigateBack() {
        NotePadGUI notePadGUI = new NotePadGUI();
        notePadGUI.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NoteEditorGUI(1).setVisible(true));
    }
}
