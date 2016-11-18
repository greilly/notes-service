package com.greilly.note.dao;

import com.google.gson.Gson;
import com.greilly.note.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Greg on 11/18/2016.
 */
public class NoteDAOImpl implements NoteDAO {
    private final Connection connection;

    public NoteDAOImpl(Connection conn) {
        connection = conn;
    }

    @Override
    public Note insert(String requestBody) throws Exception {
        try {
            Note noteToInsert = new Gson().fromJson(requestBody, Note.class);
            String sql = "INSERT INTO NOTE values(null, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, noteToInsert.getBody());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            long newId = rs.getLong(1);
            rs.close();
            stmt.close();

            return new Note(newId, noteToInsert.getBody());
        } catch (SQLException e) {
            throw new Exception("Failed to insert a new note: " + e.getMessage());
        }
    }

    @Override
    public Note findById(String id) throws Exception {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT ID, body FROM note WHERE ID=?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Note note = new Note(rs.getLong("ID"), rs.getString("body"));
                rs.close();
                stmt.close();
                return note;
            } else {
                throw new Exception("Failed to find note with id " + id);
            }
        } catch (SQLException e) {
            throw new Exception("Failed while fetching note with id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public List<Note> findNotes(String query) throws Exception {
        if (query == null || query.length() == 0) {
            PreparedStatement stmt = connection.prepareStatement("SELECT ID, body FROM note");
            return findMultipleNotes(stmt);
        }
        PreparedStatement stmt = connection.prepareStatement("SELECT ID, body FROM note WHERE body LIKE ?");
        stmt.setString(1, "%" + query + "%");
        return findMultipleNotes(stmt);
    }

    /**
     * Runs the specified statement on the notes table, and returns the result set as a List of Notes.
     *
     * @param stmt the statement to execute
     * @return
     * @throws Exception
     */
    private List<Note> findMultipleNotes(PreparedStatement stmt) throws Exception {
        try {
            ResultSet rs = stmt.executeQuery();
            List<Note> allNotes = new ArrayList<>();
            while (rs.next()) {
                allNotes.add(createNoteFromRS(rs));
            }
            rs.close();
            stmt.close();
            return allNotes;
        } catch (SQLException e) {
            throw new Exception("Failed while fetching notes: " + e.getMessage());
        }
    }

    /**
     * Creates a Note from a single ResultSet.
     *
     * @param rs from the note table
     * @return
     * @throws SQLException
     */
    private Note createNoteFromRS(ResultSet rs) throws SQLException {
        return new Note(rs.getLong("ID"), rs.getString("body"));
    }
}
