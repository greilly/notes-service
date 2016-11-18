package com.greilly.note.dao;

import com.greilly.note.model.Note;

import java.util.List;

/**
 * Created by Greg on 11/18/2016.
 */
public interface NoteDAO {
    /**
     * Creates a new Note from the passed-in body, saves it to the DB, and returns it with ID populated.
     *
     * @param requestBody the body of the new Note to be created
     * @return a Note with ID populated
     * @throws Exception
     */
    Note insert(String requestBody) throws Exception;

    /**
     * Finds a Note with the specified ID. If there is no note with the specified id, an exception is thrown.
     *
     * @param id the ID of the Note to find
     * @return
     * @throws Exception
     */
    Note findById(String id) throws Exception;

    /**
     * Finds all notes that contain the query param.  If the param is empty/null, all notes are fetched.
     *
     * @param query text to search for in the notes' body, or empty/null
     * @return
     * @throws Exception
     */
    List<Note> findNotes(String query) throws Exception;
}
