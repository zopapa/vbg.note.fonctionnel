package com.bootcamp.controllers;

import com.bootcamp.commons.enums.EntityType;

import com.bootcamp.entities.Note;
import com.bootcamp.services.NoteService;
import com.bootcamp.services.NoteWS;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController("NoteController")
@RequestMapping("/notes")
@Api(value = "Note API", description = "Note API")
@CrossOrigin(origins = "*")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new note", notes = "Create a new note")
//    @CrossOrigin(origins = "*")
    public ResponseEntity<Integer> create(@RequestBody @Valid Note note) {

        HttpStatus httpStatus = null;

        int id = 0;
        try {
            id = noteService.create(note);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(NoteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ResponseEntity<Integer>(id, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a notes", notes = "Read a note")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Note> read(@PathVariable(name = "id") int id) {

        Note note = new Note();
        HttpStatus httpStatus = null;

        try {
            note = noteService.read(id);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(NoteController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Note>(note, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get all notes on the platform", notes = "Get all notes on the platform")
    @CrossOrigin(origins = "*")
    public ResponseEntity<NoteWS> readByEntity() {

        NoteWS noteWS = new NoteWS();
        HttpStatus httpStatus = null;

        try {
            noteWS = noteService.getNotes();
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
            Logger.getLogger(NoteController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<NoteWS>(noteWS, httpStatus);
    }
}
