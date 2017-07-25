/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import org.bson.types.ObjectId;

/**
 *
 * @author hoangnh
 */
public class MongoObjectIdTypeAdapte extends TypeAdapter<ObjectId> implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId>{

    /**
     *
     */
    private static final Gson gson = new GsonBuilder().create();

    private static final TypeAdapter<String> dateTypeAdapter = gson.getAdapter(String.class);

    /**
     *
     * @param src
     * @param type
     * @param context
     * @return
     */
    public JsonElement serialize(ObjectId src, Type type,
                                 JsonSerializationContext context) {

        System.out.println("1");
        if (src == null) {

            return null;
        } else {
            JsonObject jo = new JsonObject();
            jo.addProperty("$oid", src.toString() );
            return jo;
        }
    }


    /**
     *
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {

        try
        {
            return new ObjectId(json.getAsJsonObject().get("$oid").getAsString());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     *
     * @param out
     * @param value
     * @throws IOException
     */
    @Override
    public void write(final JsonWriter out, final ObjectId value) throws IOException {

        if(value == null){
            out.nullValue();
            return;
        }
        dateTypeAdapter.write(out, value.toString());
        System.out.println(value.toString());


    }

    /**
     *
     * @param in
     * @return
     * @throws IOException
     */
    @Override
    public ObjectId read(final JsonReader in) throws IOException {

        System.out.println("4");
        ObjectId read = new ObjectId(dateTypeAdapter.read(in));
        return read;

    }
    
}
