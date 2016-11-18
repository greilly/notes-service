package com.greilly.note.service;

import com.greilly.note.dao.NoteDAO;
import com.greilly.note.dao.NoteDAOImpl;
import com.greilly.note.model.Note;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Greg on 11/18/2016.
 */
public class NoteServiceImpl implements NoteService {
    private final NoteDAO noteDAO;

    public NoteServiceImpl(Connection conn) {
        noteDAO = new NoteDAOImpl(conn);
    }

    @Override
    public Note saveNewNote(String body) throws Exception {
        return noteDAO.insert(body);
    }

    @Override
    public Note getNoteById(String id) throws Exception {
        return noteDAO.findById(id);
    }

    @Override
    public List<Note> getNotesByQuery(String query) throws Exception {
        return noteDAO.findNotes(query);
    }
}
