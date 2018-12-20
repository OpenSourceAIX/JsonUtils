# JsonUtils

Some Json utilities for App Inventor (& Thunkable etc.)

[Download (Releases)](https://github.com/OpenSourceAIX/JsonUtils/releases)

## Defined JsonType(s)

Developers can use these as texts when using join-text block (although they are not accepted by some blocks)  
For the other use, please be careful that it is not a strict String type and may causes problem.  
So a prefered way to convert this into a strict String via a join-text block.

* JsonArray
  * JsonArray is a container of org.json.JSONArray. It is defined for functions like `JsonArray_Append`
  * Can be parsed as a Json array String, e.g. `[]`, `["1", 2, true]`
* JsonObject
  * JsonObject is a container of org.json.JSONObject. It is defined for functions like `JsonObject_Put`
  * Can be parsed as a Json object String, e.g. `{}`, `{"number":1, "text":"foobar", "boolean":true}`
* JSONArray & JSONObject
  * These are native Android types, usually you don't need to know about them.

The following table is about converting different types to each other

![image](https://user-images.githubusercontent.com/22613139/50241709-3ad59680-0403-11e9-83f0-15fed9148be7.png)

## Functions

![image](https://user-images.githubusercontent.com/22613139/44954326-fb764f00-aed2-11e8-9867-b1bee527ebf0.png)

* CanParseJsonObject
  * Check if the List can be parsed into a JsonObject
  * parameters:
    * list
  * returns:
    * boolean (true / false)
* Json2List
  * Parse a json string / JsonArray / JsonObject into a List
  * parameters:
    * a Json String / JsonArray / JsonObject to be parsed
  * returns:
    * a list that represents the json

![image](https://user-images.githubusercontent.com/22613139/50244052-e550b800-0409-11e9-8571-8c858d1083c7.png)

* JsonArray_Append
  * Append an value. This increases the array's length by one.
  * parameters:
    * JsonArray
    * value (any)
* JsonArray_CreateEmpty
  * returns:
    * a empty JsonArray (json string: `[]`)
* JsonArray_Get
  * Get the object value associated with an index. Return null if there is no object at that index.
  * parameters:
    * JsonArray
    * index of the item (index start from 1)
  * returns:
    * the value of the item

![image](https://user-images.githubusercontent.com/22613139/50244077-f8fc1e80-0409-11e9-8492-65d53b2b8638.png)

* JsonArray_Length
  * Get the number of elements in the JsonArray, included nulls.
  * parameters:
    * JsonArray
  * returns:
    * the length of the JsonArray
* JsonArray_Put
  * Put or replace an object value in the JSONArray. If the index is greater than the length of the JSONArray, then null elements will be added as necessary to pad it out.
  * parameters:
    * JsonArray
    * index of the item (index start from 1)
    * value to be put
* JsonArray_Remove
  * Remove an index and close the hole.
  * parameters:
    * JsonArray
    * index of the item (index start from 1)

![image](https://user-images.githubusercontent.com/22613139/50244117-13ce9300-040a-11e9-80e2-ce9cef054af5.png)

* JsonObject_Contains
  * Determine if the JSONObject contains a specific key.
  * parameters:
    * JsonObject
    * key (String) of the item
  * returns:
    * if the key exists in JsonObject
* JsonObject_CreateEmpty
  * returns:
    * a empty JsonObject (json string: `{}`)
* JsonObject_Get
  * Get the value object associated with a key. Returns null if there is no value.
  * parameters:
    * JsonObject
    * key (String) of the item
  * returns:
    * value of the key paired up to
* JsonObject_GetKeyList
  * List all keys in the JsonObject
  * parameters:
    * jsonObject
  * returns:
    * a list that contains all keys exist in the jsonObject

![image](https://user-images.githubusercontent.com/22613139/50244162-2ea10780-040a-11e9-8ff3-06db1c7d2215.png)

* JsonObject_Put
  * Put a key-value pair in the JsonObject.
  * parameters:
    * JsonObject
    * key (String) of the item
    * value to be put
* JsonObject_Remove
  * Remove a key and its value, if present.
  * parameters:
    * JsonObject
    * key (String) of the item
* JsonObject_Size
  * Get the number of keys stored in the JsonObject.
  * parameters:
    * JsonObject
  * returns:
    * number of the key-value pairs

![image](https://user-images.githubusercontent.com/22613139/50244224-598b5b80-040a-11e9-96b4-5b251eff8251.png)

* Json_GetPath
  * Get a value from JsonArray / JsonObject with a path.
  * parameters:
    * json
    * path
  * returns:
    * (value) / null
  * example:
    * json=`[1,[1,2,3,{"key":"valueWanted"]],3]`
    * path=`2.4.key`
    * result=`"valueWanted"`
* Json_PutPath
  * Put a value into JsonArray / JsonObject with a path.
  * parameters:
    * json
    * path
    * value
  * example:
    * json=`[1,[1,2,3,{"key":"value"]],3]`
    * path=`2.4.key`
    * value=`"newValue"`
    * json after put: `[1,[1,2,3,{"key":"newValue"]],3]`
* List2JsonString
  * ***DEPRECATED***, use ToJsonString(object) instead

![image](https://user-images.githubusercontent.com/22613139/50244320-9fe0ba80-040a-11e9-85ea-9ef9a1ad2cf0.png)

* ParseJsonString
  * Parse a json string into a JsonArray / JsonObject
  * parameters:
    * Json String
  * returns:
    * a corresponding JsonArray / JsonObject
* ParseList
  * Parse a list into a JsonArray / JsonObject
  * parameters:
    * list
  * returns:
    * a corresponding JsonArray / JsonObject
* ToJsonString
  * Convert an App Inventor list into a json string
  * parameters:
    * object - list / JsonArray / JsonObject
      * list: Any App Inventor list except for **lists of key-value pairs** would be considered as a JsonArray. **List of key-value pairs:** list with all list items are lists with two items and the first list item is a text). e.g. `((key1 value1) (key2 value2))`
  * returns:
    * corresponding Json String

![image](https://user-images.githubusercontent.com/22613139/50244393-ca327800-040a-11e9-9524-bc08ea44830c.png)

* isAJsonArray
  * Check if an object is a JsonArray
* isAJsonArrayOrObject
  * Check if an object is a JsonArray or a JsonObject
* isAJsonObject
  * Check if an object is a JsonObject
* isJsonObject
  * ***DEPRECATED*** use `CanParseJsonObject(list)` instead
* isNull
  * Check if an object is null
