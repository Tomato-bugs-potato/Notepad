/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataType;

/**
 *
 * @author worki
 */
public class Note {
    //title, content, type, created_at, updated_at
    public String title;
    public String content;
    public String type;
    public java.util.Date created_at;
    public java.util.Date updated_at;
    
    public Note(String title, String content, String type, java.util.Date created_at, java.util.Date updated_at){
        this.title = title;
        this.content = content;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
