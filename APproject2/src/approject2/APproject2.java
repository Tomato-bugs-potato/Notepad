/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package approject2;
import Database.*;
import DataType.*;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author worki
 */
public class APproject2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Operation opp= new Operation();
        java.util.Date today = new java.util.Date();
        opp.saveUS("space", "hi are we correct is not wrong my spce is so cool its very vert sad my dick is bad", "type", today, today);
        opp.saveUS("cake", "spce is so cool its very vert sad my dick is bad", "type", today, today);
        opp.saveUS("pop cake", "sad my dick is bad", "type", today, today);
        Note x = opp.Find(4);
        String n = x.content;
        System.out.println(n);
        
       List<Note> results = opp.DisplayNotes();
    }
    
}
