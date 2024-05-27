package GUI;

import Database.Operation;
import DataType.NoteHistory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class NoteHistoryGUI extends JFrame {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private Operation operation = new Operation();

    public NoteHistoryGUI(int noteId) {
        setTitle("Note Edit History");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adjusted padding

        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Edited Content", "Edited At"}, 0); // Updated column names
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        backButton.setFont(backButton.getFont().deriveFont(Font.BOLD));
        buttonPanel.add(backButton);

        backButton.addActionListener(e -> navigateBack());
        add(mainPanel);
        loadNoteHistory(noteId);
    }

    private void navigateBack() {
        dispose();
    }
    private void loadNoteHistory(int noteId) {
        try {
            List<NoteHistory> history = operation.viewEditHistory(noteId);
            for (NoteHistory entry : history) {
                tableModel.addRow(new Object[]{entry.getEditedContent(), entry.getEditedAt()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading note history: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NoteHistoryGUI(1).setVisible(true));
    }
}
