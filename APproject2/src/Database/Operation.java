/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DataType.Note;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author worki
 */
public class Operation {
    public void saveUS(String title, String content, String type, java.util.Date created_at, java.util.Date updated_at) throws SQLException{
        
        Connection conn = DatabaseConnect.getConnection();

        int id = -1;

        String sql = "INSERT INTO notes (title, content, type, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, title);
        stmt.setString(2, content);
        stmt.setString(3, type);
        stmt.setDate(4, converter(created_at));
        stmt.setDate(5, converter(updated_at));

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 1) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1); 
            }
        }
        conn.close();
    }
    
    public void save(int notePadID, String title, String content, String type, java.util.Date updated_at) throws SQLException {
        Connection conn = DatabaseConnect.getConnection();

        String sql = "UPDATE notes SET title = ?, content = ?, type = ?, created_at = ?, updated_at = ? WHERE id = ?";
        PreparedStatement updateStmt = conn.prepareStatement(sql);
        updateStmt.setString(1, title);
        updateStmt.setString(2, content);
        updateStmt.setString(3, type);

        // Assuming converter method converts to java.sql.Date
        java.sql.Date sqlDate = converter(updated_at);
        updateStmt.setDate(4, sqlDate);

        updateStmt.setInt(5, notePadID);  // Set notePadID as a parameter in WHERE clause
        updateStmt.executeUpdate();

        // Consider if both content updates are necessary in your data model
        storeEditedNote(notePadID, content);
        conn.close();
     }
    
    public Note Find(int noteID)throws SQLException{
        Connection conn = DatabaseConnect.getConnection();
        String query = "SELECT * FROM notes WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, noteID);

        try (ResultSet result = pstmt.executeQuery()) {
            if (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String content = result.getString("content");
                String type = result.getString("type");
                java.util.Date created_at = result.getDate("created_at");
                java.util.Date updated_at = result.getDate("updated_at");

                return new Note(title, content, type, created_at, updated_at);
            }
        }

        return null;
    }
    
    public static List<Note> DisplayNotes() throws SQLException {
        List<Note> results = new ArrayList<>();
        Connection conn = DatabaseConnect.getConnection();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM notes");

        try (ResultSet result = pstmt.executeQuery()) {
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String content = result.getString("content");
                String type = result.getString("type");
                java.util.Date created_at = result.getDate("created_at");
                java.util.Date updated_at = result.getDate("updated_at");
               
                Note note = new Note(title, content, type, created_at, updated_at);
                results.add(note);
            }
        }

        return results;
    }
    
    public void storeEditedNote(int noteId, String editedContent) throws SQLException{
        Connection conn = DatabaseConnect.getConnection();
        try(PreparedStatement SQL = conn.prepareStatement("INSERT INTO editednote (note_id, edited_content, edited_at) VALUES (?, ?, CURRENT_TIMESTAMP)")){
            SQL.setInt(1,noteId);
            SQL.setString(2, editedContent);
            SQL.executeUpdate();
        }
        finally{
            conn.close();
        }
    }
    
    public java.sql.Date converter(java.util.Date date){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH) + 1;  // Month starts from 0
        int day = calendar.get(calendar.DAY_OF_MONTH);
        java.sql.Date sqlDate = new java.sql.Date(year - 1900, month - 1, day);
        return sqlDate;
    }
    }
    
 }

