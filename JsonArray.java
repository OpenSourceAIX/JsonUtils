package cn.colintree.aix.JsonUtils;

import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.YailList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A non visible container for JSONArray
 *
 */
public final class JsonArray extends JsonType {

    public static JsonArray parseJsonArray(YailList list) throws JSONException {
        return new JsonArray(parseJSONArray(list));
    }

    public static JSONArray parseJSONArray(YailList list) throws JSONException {
        if (JsonObject.canParseJSONObject(list)) {
            throw new IllegalArgumentException();
        }
        int size = list.size();
        JSONArray array = new JSONArray();
        Object item;
        for (int i = 0; i < size; i++) {
            item = list.getObject(i);
            array.put(item instanceof YailList
                    ? JsonUtils.List2Json((YailList) item)
                    : item);
        }
        return array;
    }

    private final JSONArray array;

    JsonArray(ComponentContainer container) {
        this();
    }
    JsonArray() {
        this(new JSONArray());
    }
    JsonArray(JSONArray array) {
        super(null);
        if (array == null) {
            array = new JSONArray();
        }
        this.array = array;
    }
    JsonArray(String json) throws JSONException {
        this(new JSONArray(json));
    }

    @Override
    public String toString() {
        return array.toString();
    }

    public JSONArray getArray() {
        return array;
    }

    @Override
    public YailList toList() {
        return toList(array);
    }

    public static YailList toList(JSONArray array) {
        int length = array.length();
        Object[] objs = new Object[length];
        for (int i = 0; i < length; i++) {
            objs[i] = array.opt(i);
            if (objs[i] instanceof JSONArray) {
                objs[i] = JsonArray.toList((JSONArray) objs[i]);
            } else if (objs[i] instanceof JSONObject) {
                objs[i] = JsonObject.toList((JSONObject) objs[i]);
            }
        }
        return YailList.makeList(objs);
    }

}