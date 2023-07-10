package top.gink.cas.util.gson.adapter;

import com.google.gson.*;
import top.gink.cas.util.DateUtil;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gink
 * @create 2021-12-02 18:59:39
 */
public class SqlDateTypeAdapter implements JsonSerializer<java.sql.Date>,
        JsonDeserializer<java.sql.Date> {

    @Override
    public JsonElement serialize(java.sql.Date src, Type arg1, JsonSerializationContext arg2) {
        String dateFormatAsString = new SimpleDateFormat("yyyy-MM-dd").format(
                new Date(src.getTime()));
        return new JsonPrimitive(dateFormatAsString);
    }

    @Override
    public java.sql.Date deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {

        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        return DateUtil.buildSqlDate(json.getAsString());
    }

}
