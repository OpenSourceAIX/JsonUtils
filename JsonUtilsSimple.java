package cn.colintree.aix.JsonUtils;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;

import org.json.JSONException;

@SimpleObject(external = true)
@DesignerComponent(
    category = ComponentCategory.EXTENSION,
    description = "",
    iconName = "images/extension.png",
    nonVisible = true,
    helpUrl = "https://github.com/OpenSourceAIX/JsonUtils",
    version = JsonUtils.VERSION)
public class JsonUtilsSimple extends AndroidNonvisibleComponent {

    public JsonUtilsSimple(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Return: []")
    public static String EmptyArray() {
        return new JsonArray().toString();
    }

    @SimpleFunction(description = "Return: {}")
    public static String EmptyObject() {
        return new JsonObject().toString();
    }

    @SimpleFunction(
        description = "Return length of a json object or array. Return -1 if fail decoding json.")
    public static int Length(String json) {
        try {
            if (json.charAt(0) == '{') {
                return new JsonObject(json).getObject().length();
            }
            if (json.charAt(0) == '[') {
                return new JsonArray(json).getArray().length();
            }
        } catch (JSONException ok) {}
        return -1;
    }

    @SimpleFunction(
        description = "Get value from a json object or array. "
                    + "Return null if any error occured during get.")
    public static Object Get(String json, String keyOrIndex) { // String accept numbers in AI2
        try {
            if (json.charAt(0) == '{') {
                return JsonUtils.JsonObject_Get(new JsonObject(json), keyOrIndex);
            }
            if (json.charAt(0) == '[') {
                return JsonUtils.JsonArray_Get(new JsonArray(json), Integer.parseInt(keyOrIndex));
            }
        }
        catch (JSONException ok) {}
        catch (ClassCastException ok) {}
        catch (NumberFormatException ok) {}
        return null;
    }

    @SimpleFunction(
        description = "Put a value into a json object or array, and return the resulting json. "
                    + "Return original value of json if any error occured during put.")
    public static String Put(String json, String keyOrIndex, Object value) { // String accept numbers in AI2
        try {
            if (json.charAt(0) == '{') {
                JsonObject jObject = new JsonObject(json);
                JsonUtils.JsonObject_Put(jObject, keyOrIndex, value);
                return jObject.toString();
            }
            if (json.charAt(0) == '[') {
                JsonArray jArray = new JsonArray(json);
                JsonUtils.JsonArray_Put(jArray, Integer.parseInt(keyOrIndex), value);
                return jArray.toString();
            }
        }
        catch (JSONException ok) {}
        catch (ClassCastException ok) {}
        catch (NumberFormatException ok) {}
        return json;
    }

    @SimpleFunction(
        description = "Get value from a json object or array with specified path. "
                    + "Return null if any error occured during get. "
                    + "Behaviour same with JsonUtils.Json_GetPath.")
    public static Object GetPath(String json, String path) {
        try {
            if (json.charAt(0) == '{') {
                return JsonUtils.Json_GetPath(new JsonObject(json), path);
            }
            if (json.charAt(0) == '[') {
                return JsonUtils.Json_GetPath(new JsonArray(json), path);
            }
        } catch (JSONException ok) {}
        return null;
    }

    @SimpleFunction(
        description = "Put a value into a json object or array, and return the resulting json. "
                    + "Return original value of json if any error occured during put."
                    + "Behaviour same with JsonUtils.Json_PutPath.")
    public static String PutPath(String json, String path, Object value) {
        try {
            JsonType jsonInstance = null;
            if (json.charAt(0) == '{') {
                jsonInstance = new JsonObject(json);
            }
            if (json.charAt(0) == '[') {
                jsonInstance = new JsonArray(json);
            }
            JsonUtils.Json_PutPath(jsonInstance, path, value);
            return jsonInstance.toString();
        } catch (JSONException ok) {}
        return json;
    }

    @SimpleFunction(description = "Check if the object is null")
    public static boolean IsNull(Object object) {
        return object == null;
    }

}