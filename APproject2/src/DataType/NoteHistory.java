package DataType;

import java.util.Date;

public class NoteHistory {
    private String editedContent;
    private Date editedAt;

    public NoteHistory(String editedContent, Date editedAt) {
        this.editedContent = editedContent;
        this.editedAt = editedAt;
    }

    // Getters and setters
    public String getEditedContent() {
        return editedContent;
    }

    public void setEditedContent(String editedContent) {
        this.editedContent = editedContent;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }
}
 
