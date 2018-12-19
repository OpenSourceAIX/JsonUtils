package cn.colintree.aix.JsonUtils;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.YailList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SimpleObject(external = true)
@DesignerComponent(
    category = ComponentCategory.EXTENSION,
    description = "",
    iconName = "images/extension.png",
    nonVisible = true,
    helpUrl = "https://github.com/OpenSourceAIX/JsonUtils",
    version = JsonUtils.VERSION)
public class JsonUtils extends AndroidNonvisibleComponent {

    public static final int VERSION = 2;

    public JsonUtils(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(
        description = "Convert list into an json string. Would "
                    + "For more details about accepted format, "
                    + "see https://github.com/OpenSourceAIX/JsonUtils")
    public static String List2JsonString(YailList list) {
        return List2Json(list).toString();
    }

    static Object List2Json(YailList list) {
        if (list == null || list.size() == 0) {
            return new JSONArray();
        }
        try {
            // using try-catch can reduce times to call JsonObject.CanParseJSONObject()
            try {
                return JsonArray.parseJSONArray(list);
            } catch (IllegalArgumentException ok) {
                return JsonObject.parseJSONObject(list);
            }
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    /**
     * Check if the List can be converted into a JSONObject
     */
    @Deprecated
    @SimpleFunction(
        description = "Check if the List can be converted into a JsonObject. \n"
                    + "THIS METHOD IS DEPRECATED, PLEASE USE CanParseJsonObject() INSTEAD!",
        userVisible = false)
    public static boolean isJsonObject(YailList list) {
        return JsonObject.canParseJSONObject(list);
    }

    /**
     * Check if the List can be parsed into a JSONObject
     */
    @SimpleFunction(
        description = "Check if the List can be parsed into a JsonObject")
    public static boolean CanParseJsonObject(YailList list) {
        return JsonObject.canParseJSONObject(list);
    }

    /**
     * Parse a json string / JSONArray / JSONObject / JsonArray / JsonObject into a List
     */
    @SimpleFunction(
        description = "Parse a json string / JsonArray / JsonObject into a List")
    public static YailList Json2List(Object object) {
        object = object instanceof String ? ParseJsonString((String) object) : object;
        object = object instanceof JSONArray ? new JsonArray((JSONArray) object) : object;
        object = object instanceof JSONObject ? new JsonObject((JSONObject) object) : object;
        if (object instanceof JsonArray || object instanceof JsonObject) {
            return ((JsonType) object).toList();
        }
        throw new IllegalArgumentException();
    }



    /**
     * Parse a list into a JsonArray / JsonObject
     */
    @SimpleFunction(
        description = "Parse a list into a JsonArray / JsonObject")
    public static JsonType ParseList(YailList list) {
        Object json = List2Json(list);
        return json instanceof JSONArray
            ? new JsonArray((JSONArray) json)
            : new JsonObject((JSONObject) json);
    }

    /**
     * Parse a json string into a JsonArray / JsonObject
     */
    @SimpleFunction(
        description = "Parse a json string into a JsonArray / JsonObject")
    public static JsonType ParseJsonString(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return new JsonArray(new JSONArray());
        }
        try {
            return new JsonArray(new JSONArray(jsonString));
        } catch (JSONException ok) {}
        try {
            return new JsonObject(new JSONObject(jsonString));
        } catch (JSONException ok) {}
        return new JsonArray(new JSONArray());
    }



    /**
     * Check if an object is null
     */
    @SimpleFunction(
        description = "Check if an object is null")
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * Check if an object is a JsonArray
     */
    @SimpleFunction(
        description = "Check if an object is a JsonArray")
    public static boolean isAJsonArray(Object object) {
        return object instanceof JsonArray;
    }

    /**
     * Check if an object is a JsonObject
     */
    @SimpleFunction(
        description = "Check if an object is a JsonObject")
    public static boolean isAJsonObject(Object object) {
        return object instanceof JsonObject;
    }

    /**
     * Check if an object is a JsonArray or a JsonObject
     */
    @SimpleFunction(
        description = "Check if an object is a JsonArray or a JsonObject")
    public static boolean isAJsonArrayOrObject(Object object) {
        return isAJsonArray(object) || isAJsonObject(object);
    }



    /**
     * Get the object value associated with an index.
     * Return null if there is no object at that index.
     */
    @SimpleFunction(
        description = "Get the object value associated with an index. "
                    + "Return null if there is no object at that index.")
    public static Object JsonArray_Get(JsonArray jsonArray, int index) {
        return jsonArray.getArray().opt(index - 1);
    }

    /**
     * Put or replace an object value in the JSONArray.
     * If the index is greater than the length of the JSONArray,
     * then null elements will be added as necessary to pad it out.
     */
    @SimpleFunction(
        description = "Put or replace an object value in the JSONArray. "
                    + "If the index is greater than the length of the JSONArray, "
                    + "then null elements will be added as necessary to pad it out.")
    public static void JsonArray_Put(JsonArray jsonArray, int index, Object value) throws JSONException {
        if (index < 1) {
            return;
        }
        value = value instanceof JsonObject ? ((JsonObject) value).getObject() : value;
        value = value instanceof JsonArray ? ((JsonArray) value).getArray() : value;
        jsonArray.getArray().put(index - 1,
            value instanceof YailList ? List2Json((YailList) value) : value);
        // Throws: JSONException - (If the index is negative or) if the the value is an invalid number.
    }

    /**
     * Append an value. This increases the array's length by one.
     */
    @SimpleFunction(
        description = "Append an value. This increases the array's length by one.")
    public static void JsonArray_Append(JsonArray jsonArray, Object value) {
        value = value instanceof JsonObject ? ((JsonObject) value).getObject() : value;
        value = value instanceof JsonArray ? ((JsonArray) value).getArray() : value;
        jsonArray.getArray().put(
            value instanceof YailList ? List2Json((YailList) value) : value);
    }

    /**
     * Remove an index and close the hole.
     */
    @SimpleFunction(
        description = "Remove an index and close the hole.")
    public static void JsonArray_Remove(JsonArray jsonArray, int index) {
        jsonArray.getArray().remove(index - 1);
    }

    /**
     * Get the number of elements in the JSONArray, included nulls.
     */
    @SimpleFunction(
        description = "Get the number of elements in the JsonArray, included nulls.")
    public static int JsonArray_Length(JsonArray jsonArray) {
        return jsonArray.getArray().length();
    }

    /**
     * Return a empty JsonArray: []
     */
    @SimpleFunction(
        description = "Return a empty JsonArray: []")
    public static JsonArray JsonArray_CreateEmpty() {
        return new JsonArray();
    }

    /**
     * Return json string of a JsonArray
     */
    @SimpleFunction(
        description = "Return json string of a JsonArray")
    public static String JsonArray_ToString(JsonArray jsonArray) {
        return jsonArray.toString();
    }



    /**
     * Determine if the JSONObject contains a specific key.
     */
    @SimpleFunction(
        description = "Determine if the JSONObject contains a specific key.")
    public static boolean JsonObject_Contains(JsonObject jsonObject, String key) {
        return jsonObject.getObject().has(key);
    }

    /**
     * Get the value object associated with a key. Returns null if there is no value.
     */
    @SimpleFunction(
        description = "Get the value object associated with a key. "
                    + "Returns null if there is no value.")
    public static Object JsonObject_Get(JsonObject jsonObject, String key) {
        return jsonObject.getObject().opt(key);
    }

    /**
     * Put a key-value pair in the JsonObject.
     */
    @SimpleFunction(
        description = "Put a key-value pair in the JsonObject.")
    public static void JsonObject_Put(JsonObject jsonObject, String key, Object value) throws JSONException {
        value = value instanceof JsonObject ? ((JsonObject) value).getObject() : value;
        value = value instanceof JsonArray ? ((JsonArray) value).getArray() : value;
        jsonObject.getObject().putOpt(key,
            value instanceof YailList ? List2Json((YailList) value) : value);
        // Throws: JSONException - If the value is a non-finite number.
    }

    /**
     * Remove a key and its value, if present.
     */
    @SimpleFunction(
        description = "Remove a key and its value, if present.")
    public static void JsonObject_Remove(JsonObject jsonObject, String key) {
        jsonObject.getObject().remove(key);
    }

    /**
     * Get the number of keys stored in the JSONObject.
     */
    @SimpleFunction(
        description = "Get the number of keys stored in the JsonObject.")
    public static int JsonObject_Size(JsonObject jsonObject) {
        return jsonObject.getObject().length();
    }

    /**
     * Return a empty JsonObject: {}
     */
    @SimpleFunction(
        description = "Return a empty JsonObject: {}")
    public static JsonObject JsonObject_CreateEmpty() {
        return new JsonObject();
    }

    /**
     * Return json string of a JsonObject
     */
    @SimpleFunction(
        description = "Return json string of a JsonObject")
    public static String JsonObject_ToString(JsonObject jsonObject) {
        return jsonObject.toString();
    }

}