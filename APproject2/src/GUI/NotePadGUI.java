package GUI;

import Database.Operation;
import DataType.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class NotePadGUI extends JFrame {
    private JTable noteTable;
    private DefaultTableModel tableModel;
    private Operation operation = new Operation();

    public NotePadGUI() {
        setTitle("Notepad Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800); 
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        tableModel = new DefaultTableModel(new Object[]{"id", "Title", "Content", "Type", "Created At", "Updated At"}, 0);
        noteTable = new JTable(tableModel);
        noteTable.setRowHeight(30);
        noteTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); 
        JScrollPane scrollPane = new JScrollPane(noteTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

      
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
        JButton addButton = new JButton("Add Note");
        JButton editButton = new JButton("Edit Note");
        JButton deleteButton = new JButton("Delete Note");
        JButton historyButton = new JButton("View History");
        addButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        historyButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(historyButton);

       
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH); 

        add(mainPanel);

        
        loadNotes();

        addButton.addActionListener(e -> {
            AddNote addNoteGUI = new AddNote();
            addNoteGUI.setVisible(true);
        });

        editButton.addActionListener(e -> {
            int selectedRow = noteTable.getSelectedRow();
            if (selectedRow != -1) {
                int noteId = (Integer) tableModel.getValueAt(selectedRow, 0); 
                NoteEditorGUI editNoteGUI = new NoteEditorGUI(noteId);
                editNoteGUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a note to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e-> {
            int selectedRow = noteTable.getSelectedRow();
            if (selectedRow != -1) {
                int noteId = (Integer) tableModel.getValueAt(selectedRow, 0); 
                try {
                    operation.deleteByID(noteId);
                    JOptionPane.showMessageDialog(this, "Note Deleted successfully.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(this, "Unable to Delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a note to Delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        historyButton.addActionListener(e -> {
            int selectedRow = noteTable.getSelectedRow();
            if (selectedRow != -1) {
                int noteId = (Integer) tableModel.getValueAt(selectedRow, 0);
                NoteHistoryGUI historyGUI = new NoteHistoryGUI(noteId);
                historyGUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a note to view history.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        setVisible(true); 
    }

    private void loadNotes() {
        try {
            List<Note> notes = operation.DisplayNotes();
            for (Note note : notes) {
                tableModel.addRow(new Object[]{note.getId(), note.getTitle(), note.getContent(), note.getType(), note.getCreatedAt(), note.getUpdatedAt()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotePadGUI::new);
    }
}
