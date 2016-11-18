package com.greilly.note.service;

import com.greilly.note.model.Note;

import java.util.List;

/**
 * Created by Greg on 11/18/2016.
 */
public interface NoteService {
    /**
     * Saves a new note in the DB.
     *
     * @param body the body of the new note
     * @return the new note, with ID populated
     * @throws Exception
     */
    Note saveNewNote(String body) throws Exception;

    /**
     * Gets a note by it's id.  If there is no note with the specified id, an exception is thrown.
     *
     * @param id the id of the note to find
     * @return
     * @throws Exception
     */
    Note getNoteById(String id) throws Exception;

    /**
     * Finds all notes that contain the query param.  If the query param is empty, all notes are returned.
     *
     * @param query text to search for in the notes' body, or empty/null
     * @return all notes matching the query.  Could be empty.
     * @throws Exception
     */
    List<Note> getNotesByQuery(String query) throws Exception;
}
