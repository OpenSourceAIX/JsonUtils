package cn.colintree.aix.JsonUtils;

import java.util.Iterator;

import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.YailList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A non visible container for JSONObject
 */
public final class JsonObject extends JsonType {

    public static JsonObject parseJsonObject(YailList list) throws JSONException {
        return new JsonObject(parseJSONObject(list));
    }

    public static JSONObject parseJSONObject(YailList list) throws JSONException {
        if (!canParseJSONObject(list)) {
            throw new IllegalArgumentException();
        }
        int size = list.size();
        JSONObject object = new JSONObject();
        YailList itemList;
        for (int i = 0; i < size; i++) {
            itemList = (YailList) list.getObject(i);
            object.put(
                (String) itemList.getObject(0),
                itemList.getObject(1) instanceof YailList
                    ? JsonUtils.List2Json((YailList) itemList.getObject(1))
                    : itemList.getObject(1)
            );
        }
        return object;
    }

    public static boolean canParseJSONObject(YailList list) {
        int size = list.size();
        Object item;
        for (int i = 0; i < size; i++) {
            item = list.getObject(i);
            if (item instanceof YailList && ((YailList)item).size() == 2
                && ((YailList)item).getObject(0) instanceof String) {
                // this item is a valid object item
            } else {
                return false;
            }
        }
        return true;
    }


    private final JSONObject object;

    JsonObject(ComponentContainer container) {
        this(new JSONObject());
    }
    JsonObject(JSONObject object) {
        super(null);
        this.object = object;
    }

    @Override
    public String toString() {
        return object.toString();
    }

    public JSONObject getObject() {
        return object;
    }

    @Override
    public YailList toList() {
        return toList(object);
    }

    public static YailList toList(JSONObject object) {
        int length = object.length();
        Object[] objs = new Object[length];
        int i = 0;
        String key;
        Object value;
        Iterator<String> iterator = object.keys();
        while (iterator.hasNext()) {
            key = iterator.next();
            value = object.opt(key);
            if (value instanceof JSONArray) {
                value = JsonArray.toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = JsonObject.toList((JSONObject) value);
            }
            objs[i] = YailList.makeList(new Object[]{ key, value });
            i++;
        }
        return YailList.makeList(objs);
    }

}