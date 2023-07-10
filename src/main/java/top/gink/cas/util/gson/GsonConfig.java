package top.gink.cas.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.gink.cas.util.gson.adapter.SqlDateTypeAdapter;
import top.gink.cas.util.gson.adapter.TimestampTypeAdapter;
import top.gink.cas.util.gson.adapter.UtilDateTypeAdapter;

import java.sql.Timestamp;

/**
 * @author Gink
 * @create 2018/2/2 下午3:12
 */
public class GsonConfig {

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gb.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter());
        gb.registerTypeAdapter(java.sql.Date.class, new SqlDateTypeAdapter());
        gb.registerTypeAdapter(java.util.Date.class, new UtilDateTypeAdapter());
        return gb;
    }

    public Gson gson() {
        GsonBuilder gb = getGsonBuilder();
        gb.serializeNulls();
        return gb.create();
    }

    public Gson gsonLite() {
        GsonBuilder gb = getGsonBuilder();
        return gb.create();
    }
}
