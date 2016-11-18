package com.greilly.note.controller;

import com.greilly.note.JsonTransformer;
import com.greilly.note.exception.ResponseError;
import com.greilly.note.service.NoteServiceImpl;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by Greg on 11/18/2016.
 */
public class NoteController {
    private final NoteServiceImpl noteService;

    private final String API_LOCATION = "/api/notes";

    public NoteController(NoteServiceImpl noteService) {
        this.noteService = noteService;
        setEndpoints();
    }

    private void setEndpoints() {
        post(API_LOCATION, "application/json", (request, response) -> {
            try {
                return noteService.saveNewNote(request.body());
            } catch (Exception e) {
                return new ResponseError(e);
            }
        }, new JsonTransformer());

        get(API_LOCATION + "/:id", "application/json", (request, response) -> {
            try {
                return noteService.getNoteById(request.params(":id"));
            } catch (Exception e) {
                return new ResponseError(e);
            }
        }, new JsonTransformer());

        get(API_LOCATION, "application/json", (request, response) -> {
            try {
                return noteService.getNotesByQuery(request.queryParams("query"));
            } catch (Exception e) {
                return new ResponseError(e);
            }
        }, new JsonTransformer());
    }
}
