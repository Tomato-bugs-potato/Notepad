/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataType;

import java.util.Date;

/**
 *
 * @author worki
 */
public class Note {
    //title, content, type, created_at, updated_at
    public int id;
    public String title;
    public String content;
    public String type;
    public java.util.Date created_at;
    public java.util.Date updated_at;
    
    public Note(int id, String title, String content, String type, java.util.Date created_at, java.util.Date updated_at){
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    public String getType() {
        return type;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }
    
}
