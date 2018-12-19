package cn.colintree.aix.JsonUtils;

import java.util.Iterator;

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

    @Deprecated
    @SimpleFunction(
        description = "Deprecated method, use ToJsonString(object) instead",
        userVisible = false)
    public static String List2JsonString(YailList list) {
        return ToJsonString(list);
    }

    @SimpleFunction(
        description = "Convert list, JsonObject & JsonArray to json string")
    public static String ToJsonString(Object object) {
        if (object instanceof YailList) {
            object = List2Json((YailList) object);
        }
        return object.toString();
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
        } catch (JSONException ok) {
            return new JSONArray();
        }
    }

    /**
     * @param list
     * @return boolean - if the List can be converted into a JsonObject
     */
    @Deprecated
    @SimpleFunction(
        description = "Check if the List can be converted into a JsonObject. \n"
                    + "THIS METHOD IS DEPRECATED, PLEASE USE CanParseJsonObject() INSTEAD!",
        userVisible = false)
    public static boolean isJsonObject(YailList list) {
        return CanParseJsonObject(list);
    }

    /**
     * @param list
     * @return boolean - if the List can be converted into a JsonObject
     */
    @SimpleFunction(
        description = "Check if the List can be parsed into a JsonObject")
    public static boolean CanParseJsonObject(YailList list) {
        return JsonObject.canParseJSONObject(list);
    }

    /**
     * @param object - json string / JSONArray / JSONObject / JsonArray / JsonObject
     * @return list
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
     * @param list
     * @return JsonArray / JsonObject
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
     * @param jsonString
     * @return JsonArray / JsonObject
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
     * @param object
     * @return boolean - if the object is null
     */
    @SimpleFunction(
        description = "Check if the object is null")
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * @param object
     * @return boolean - if the object is a JsonArray
     */
    @SimpleFunction(
        description = "Check if the object is a JsonArray")
    public static boolean isAJsonArray(Object object) {
        return object instanceof JsonArray;
    }

    /**
     * @param object
     * @return boolean - if the object is a JsonObject
     */
    @SimpleFunction(
        description = "Check if the object is a JsonObject")
    public static boolean isAJsonObject(Object object) {
        return object instanceof JsonObject;
    }

    /**
     * @param object
     * @return boolean - if the object is a JsonArray or JsonObject
     */
    @SimpleFunction(
        description = "Check if the object is a JsonArray or JsonObject")
    public static boolean isAJsonArrayOrObject(Object object) {
        return isAJsonArray(object) || isAJsonObject(object);
    }



    /**
     * @param jsonArray
     * @param index
     * @return (value) / null
     */
    @SimpleFunction(
        description = "Get the object value associated with an index. "
                    + "Return null if there is no object at that index.")
    public static Object JsonArray_Get(JsonArray jsonArray, int index) {
        return jsonArray.getArray().opt(index - 1);
    }

    /**
     * Put or replace an object value in the JsonArray.
     * If the index is greater than the length of the JsonArray,
     * then null elements will be added as necessary to pad it out.
     * @param jsonArray
     * @param index
     * @param value
     * @throws JSONException
     */
    @SimpleFunction(
        description = "Put or replace an object value in the JsonArray. "
                    + "If the index is greater than the length of the JsonArray, "
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
     * @param jsonArray
     * @param value
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
     * @param jsonArray
     * @param index
     */
    @SimpleFunction(
        description = "Remove an index and close the hole.")
    public static void JsonArray_Remove(JsonArray jsonArray, int index) {
        jsonArray.getArray().remove(index - 1);
    }

    /**
     * @param jsonArray
     * @return int - the number of elements in the JsonArray, included nulls.
     */
    @SimpleFunction(
        description = "Get the number of elements in the JsonArray, included nulls.")
    public static int JsonArray_Length(JsonArray jsonArray) {
        return jsonArray.getArray().length();
    }

    /**
     * @return JsonArray - empty JsonArray: []
     */
    @SimpleFunction(
        description = "Return a empty JsonArray: []")
    public static JsonArray JsonArray_CreateEmpty() {
        return new JsonArray();
    }

    /**
     * Get a value from jsonArray with a path
     * e.g. jsonArray=[1,[1,2,3,{"key":"valueWanted"]],3], path=2.4.key, result="valueWanted"
     * @param jsonArray
     * @param path
     * @return (value) / null - value from jsonArray with a path
     */
    @SimpleFunction(
        description = "Get a value from jsonArray with a path"
                    + "e.g. jsonArray=[1,[1,2,3,{\"key\":\"valueWanted\"]],3], path=2.4.key, result=\"valueWanted\"")
    public static Object JsonArray_GetPath(JsonArray jsonArray, String path) {
        try {
            String[] splitRes = path.split("\\.", 2);
            int index = Integer.parseInt(splitRes[0]);
            Object value = makeSureJSONTypeConverted(JsonArray_Get(jsonArray, index));
            if (splitRes.length == 1) {
                return value;
            }
            String relativePath = splitRes[1];
            if (value instanceof JsonArray) {
                return JsonArray_GetPath((JsonArray) value, relativePath);
            }
            if (value instanceof JsonObject) {
                return JsonObject_GetPath((JsonObject) value, relativePath);
            }
        }
        catch (IndexOutOfBoundsException ok) {}
        catch (NumberFormatException ok) {}
        catch (NullPointerException ok) {}
        return null;
    }

    private static Object makeSureJSONTypeConverted(Object obj) {
        if (obj instanceof JSONArray) {
            return new JsonArray((JSONArray) obj);
        }
        if (obj instanceof JSONObject) {
            return new JsonObject((JSONObject) obj);
        }
        return obj;
    }

    /**
     * Put a value into jsonArray with a path
     * e.g. jsonArray=[1,[1,2,3,{"key":"value"]],3], path=2.4.key, value="newValue"
     *      result=[1,[1,2,3,{"key":"newValue"]],3]
     * @param jsonArray
     * @param path
     * @param value
     */
    @SimpleFunction(
        description = "Put a value into jsonArray with a path"
                    + "e.g. jsonArray=[1,[1,2,3,{\"key\":\"value\"]],3], path=2.4.key, value=\"newValue\", "
                    + "result=[1,[1,2,3,{\"key\":\"newValue\"]],3]")
    public static void JsonArray_PutPath(JsonArray jsonArray, String path, Object value) {
        try {
            String[] splitRes = path.split("\\.", 2);
            int index = Integer.parseInt(splitRes[0]);
            if (splitRes.length == 1) {
                jsonArray.getArray().put(index, value);
                return;
            }
            Object child = makeSureJSONTypeConverted(JsonArray_Get(jsonArray, index));
            String relativePath = splitRes[1];
            if (child instanceof JsonArray) {
                JsonArray_PutPath((JsonArray) child, relativePath, value);
            }
            if (child instanceof JsonObject) {
                JsonObject_PutPath((JsonObject) child, relativePath, value);
            }
        }
        catch (IndexOutOfBoundsException ok) {}
        catch (NumberFormatException ok) {}
        catch (NullPointerException ok) {}
        catch (JSONException ok) {}
    }



    /**
     * @param jsonObject
     * @param key
     * @return boolean - if the JsonObject contains the specified key
     */
    @SimpleFunction(
        description = "Determine if the JsonObject contains the specified key.")
    public static boolean JsonObject_Contains(JsonObject jsonObject, String key) {
        return jsonObject.getObject().has(key);
    }

    /**
     * @param jsonObject
     * @param key
     * @return (value) - object associated with a key. Returns null if there is no value.
     */
    @SimpleFunction(
        description = "Get the value object associated with a key. "
                    + "Returns null if there is no value.")
    public static Object JsonObject_Get(JsonObject jsonObject, String key) {
        return jsonObject.getObject().opt(key);
    }

    /**
     * @param jsonObject
     * @return list - all keys in the JsonObject
     */
    @SimpleFunction(
        description = "List all keys in the JsonObject")
    public static YailList JsonObject_GetKeyList(JsonObject jsonObject) {
        Iterator<String> iterator = jsonObject.getObject().keys();
        String[] keys = new String[JsonObject_Size(jsonObject)];
        int index = 0;
        while (iterator.hasNext()) {
            keys[index++] = iterator.next();
        }
        return YailList.makeList(keys);
    }

    /**
     * Put a key-value pair in the JsonObject.
     * @param jsonObject
     * @param key
     * @param value
     * @throws JSONException
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
     * @param jsonObject
     * @param key
     */
    @SimpleFunction(
        description = "Remove a key and its value, if present.")
    public static void JsonObject_Remove(JsonObject jsonObject, String key) {
        jsonObject.getObject().remove(key);
    }

    /**
     * @param jsonObject
     * @return int - number of keys stored in the JsonObject
     */
    @SimpleFunction(
        description = "Get the number of keys stored in the JsonObject.")
    public static int JsonObject_Size(JsonObject jsonObject) {
        return jsonObject.getObject().length();
    }

    /**
     * @return JsonObject - empty JsonObject: {}
     */
    @SimpleFunction(
        description = "Return a empty JsonObject: {}")
    public static JsonObject JsonObject_CreateEmpty() {
        return new JsonObject();
    }

    /**
     * Get a value from jsonObject with a path
     * e.g. jsonObject={"a":[1,2,3,{"b":"valueWanted"}]}, path=a.4.b, result="valueWanted"
     * @param jsonObject
     * @param path
     * @return (value) - from jsonObject with a path
     */
    @SimpleFunction(
        description = "Get a value from jsonArray with a path"
                    + "e.g. jsonObject={\"a\":[1,2,3,{\"b\":\"valueWanted\"}]}, path=a.4.b, result=\"valueWanted\"")
    public static Object JsonObject_GetPath(JsonObject jsonObject, String path) {
        try {
            String[] splitRes = path.split("\\.", 2);
            String key = splitRes[0];
            Object value = makeSureJSONTypeConverted(JsonObject_Get(jsonObject, key));
            if (splitRes.length == 1) {
                return value;
            }
            String relativePath = splitRes[1];
            if (value instanceof JsonArray) {
                return JsonArray_GetPath((JsonArray) value, relativePath);
            }
            if (value instanceof JsonObject) {
                return JsonObject_GetPath((JsonObject) value, relativePath);
            }
        }
        catch (IndexOutOfBoundsException ok) {}
        catch (NumberFormatException ok) {}
        catch (NullPointerException ok) {}
        return null;
    }

    /**
     * Put a value into jsonObject with a path
     * e.g. jsonObject={"a":[1,2,3,{"b":"value"}]}, path=a.4.b, value="newValue"
     *      result={"a":[1,2,3,{"b":"newValue"}]}
     * @param jsonObject
     * @param path
     * @param value
     */
    @SimpleFunction(
        description = "Put a value into jsonArray with a path"
                    + "e.g. jsonObject={\"a\":[1,2,3,{\"b\":\"value\"}]}, path=a.4.b, value=\"newValue\", "
                    + "result={\"a\":[1,2,3,{\"b\":\"newValue\"}]}")
    public static void JsonObject_PutPath(JsonObject jsonObject, String path, Object value) {
        try {
            String[] splitRes = path.split("\\.", 2);
            String key = splitRes[0];
            if (splitRes.length == 1) {
                jsonObject.getObject().putOpt(key, value);
                return;
            }
            Object child = makeSureJSONTypeConverted(JsonObject_Get(jsonObject, key));
            String relativePath = splitRes[1];
            if (child instanceof JsonArray) {
                JsonArray_PutPath((JsonArray) child, relativePath, value);
            }
            if (child instanceof JsonObject) {
                JsonObject_PutPath((JsonObject) child, relativePath, value);
            }
        }
        catch (IndexOutOfBoundsException ok) {}
        catch (NumberFormatException ok) {}
        catch (NullPointerException ok) {}
        catch (JSONException ok) {}
    }

}