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
public class UtilDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2) {
        String dateFormatAsString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new Date(src.getTime()));
        return new JsonPrimitive(dateFormatAsString);
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        return DateUtil.buildDate(json.getAsString());
    }
}
