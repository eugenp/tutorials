package com.baeldung.gson.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.baeldung.gson.entities.ActorGson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class ActorGsonSerializer implements JsonSerializer<ActorGson> {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public JsonElement serialize(ActorGson actor, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject actorJsonObj = new JsonObject();
        actorJsonObj.addProperty("<strong>IMDB Code</strong>", actor.getImdbId());
        actorJsonObj.addProperty("<strong>Date Of Birth</strong>", actor.getDateOfBirth() != null ? sdf.format(actor.getDateOfBirth()) : null);
        actorJsonObj.addProperty("<strong>N° Film:</strong> ", actor.getFilmography() != null ? actor.getFilmography().size() : null);
        actorJsonObj.addProperty("filmography", actor.getFilmography() != null ? convertFilmography(actor.getFilmography()) : null);

        return actorJsonObj;
    }

    private String convertFilmography(List<String> filmography) {
        return filmography.stream().collect(Collectors.joining("-"));
    }
}