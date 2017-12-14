package com.bootcamp.services;


import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.enums.EntityType;
import com.bootcamp.commons.enums.NoteType;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.models.Rule;
import com.bootcamp.crud.NoteCRUD;
import com.bootcamp.entities.Note;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Moh on 11/27/17.
 */
@Component
public class NoteService implements DatabaseConstants {

    public int create(Note note) throws SQLException {
        note.setDateCreation(System.currentTimeMillis());
        NoteCRUD.create(note);
        return note.getId();
    }

    public int update(Note note) throws SQLException {
        note.setDateCreation(System.currentTimeMillis());
        NoteCRUD.update(note);
        return note.getId();
    }

    public Note delete(int id) throws SQLException {
        Note note = read(id);
        NoteCRUD.delete(note);
        return note;
    }

    public Note read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        List<Note> notes = NoteCRUD.read(criterias);
        return notes.get(0);
    }
    
    public double getMoyenne() throws SQLException {
        double moyenne = 0;
        int count = 0;

        List<Note> notes = NoteCRUD.read();
        for (Note note : notes){
            NoteType noteType = NoteType.valueOf(note.getNoteType());
            int n = noteType.ordinal()+1;
            moyenne+=n;
            count++;
        }
        return moyenne/count;
    }

    public int getNoteCountsByNoteType(NoteType noteType) throws SQLException {
        int count = 0;
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria(new Rule("noteType", "=", noteType), null));
        count = NoteCRUD.read(criterias).size();
        return count;
    }
    
    public int getNotesCounts() throws SQLException {
        int count = 0;
        Criterias criterias = new Criterias();
        count = NoteCRUD.read(criterias).size();
        return count;
    }

    public NoteWS getNotes() throws SQLException {
        NoteWS noteWS = new NoteWS();

        noteWS.setMoyenne(this.getMoyenne());
        noteWS.setNoteOneCounts(this.getNoteCountsByNoteType( NoteType.UN));
        noteWS.setNoteTwoCounts(this.getNoteCountsByNoteType( NoteType.DEUX));
        noteWS.setNoteThreeCounts(this.getNoteCountsByNoteType(NoteType.TROIS));
        noteWS.setNoteFourCounts(this.getNoteCountsByNoteType( NoteType.QUATRE));
        noteWS.setNoteFiveCounts(this.getNoteCountsByNoteType(NoteType.CINQ));

        return noteWS;
    }
    

}
