package com.greilly.note;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by Greg on 11/18/2016.
 *
 * Transforms an object to json, for use in Spark Java.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

}
