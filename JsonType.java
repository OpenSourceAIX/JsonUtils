package cn.colintree.aix.JsonUtils;

import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.YailList;

public abstract class JsonType extends AndroidNonvisibleComponent {

    public JsonType(ComponentContainer container) {
        super(null);
    }

    @Override
    public abstract String toString();

    public abstract YailList toList();

}