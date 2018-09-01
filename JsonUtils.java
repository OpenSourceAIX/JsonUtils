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
    iconName = "images/extension.png",
    nonVisible = true,
    helpUrl = "https://github.com/OpenSourceAIX/JsonUtils",
    version = 1)
public class JsonUtils extends AndroidNonvisibleComponent {
    
    public JsonUtils(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(
        description = "Convert list into an json string, "
                    + "for more details see https://github.com/OpenSourceAIX/JsonUtils")
    public static String List2JsonString(YailList list) throws JSONException {
        return List2Json(list).toString();
    }
    public static Object List2Json(YailList list) throws JSONException {
        if (list == null) {
            throw new IllegalArgumentException();
        }
        if (list.size()==0) {
            return new JSONArray();
        }
        int size = list.size();
        if (isJsonObject(list)) {
            JSONObject object = new JSONObject();
            YailList itemList;
            for (int i = 0; i < size; i++) {
                itemList = (YailList) list.getObject(i);
                object.put(
                    (String) itemList.getObject(0),
                    itemList.getObject(1) instanceof YailList
                        ? List2Json((YailList) itemList.getObject(1))
                        : itemList.getObject(1)
                );
            }
            return object;
        } else {
            JSONArray array = new JSONArray();
            Object item;
            for (int i = 0; i < size; i++) {
                item = list.getObject(i);
                array.put(item instanceof YailList
                        ? List2Json((YailList) item)
                        : item);
            }
            return array;
        }
    }

    /**
     * Check if the List can be converted into a JsonObject
     */
    @SimpleFunction(
        description = "Check if the List can be converted into a JsonObject")
    public static boolean isJsonObject(YailList list) {
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
}