# JsonUtils

Some Json utilities for App Inventor (& Thunkable etc.)

[Download (Releases)](https://github.com/OpenSourceAIX/JsonUtils/releases)

## Defined JsonType(s)

Developers can use these as texts when using join-text block  
For the other use, please be careful that it is not a strict String type and may causes problem.  
So a prefered way to convert this into a strict String via a join-text block.

* JsonArray
    * JsonArray is a container of org.json.JSONArray. It is defined for functions like `JsonArray_Append`
    * Can be parsed as a Json array String, e.g. `[]`, `["1", 2, true]`
* JsonObject
    * JsonObject is a container of org.json.JSONObject. It is defined for functions like `JsonObject_Put`
    * Can be parsed as a Json object String, e.g. `{}`, `{"number":1, "text":"foobar", "boolean":true}`

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

![image](https://user-images.githubusercontent.com/22613139/44954323-edc0c980-aed2-11e8-85ac-ee28980c5cfd.png)

* JsonArray_Append
    * Append an value. This increases the array's length by one.
    * parameters:
        * JsonArray
        * value (any)
* JsonArray_Get
    * Get the object value associated with an index. Return null if there is no object at that index.
    * parameters:
        * JsonArray
        * index of the item (index start from 1)
    * returns:
        * the value of the item
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

![image](https://user-images.githubusercontent.com/22613139/44954312-cc5fdd80-aed2-11e8-8d7b-fb933d5af9da.png)

* JsonObject_Contains
    * Determine if the JSONObject contains a specific key.
    * parameters:
        * JsonObject
        * key (String) of the item
    * returns:
        * if the key exists in JsonObject
* JsonObject_Get
    * Get the value object associated with a key. Returns null if there is no value.
    * parameters:
        * JsonObject
        * key (String) of the item
    * returns:
        * value of the key paired up to
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

![image](https://user-images.githubusercontent.com/22613139/44954501-a425ae00-aed5-11e8-963b-6ae52286a613.png)

* List2JsonString
    * Convert an App Inventor list into a json string
    * Any App Inventor list except for **lists of key-value pairs** would be considered as a JsonArray
        * **list of key-value pairs:** list with all list items are lists with two items and the first list item is a text) (see sample below)
    * parameters:
        * list 
    * returns:
        * corresponding Json String
    * sample:  
      ![](https://user-images.githubusercontent.com/22613139/44947212-54df6f00-ae3c-11e8-86e6-50bc0d462a69.png)
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

![image](https://user-images.githubusercontent.com/22613139/44956212-fb864700-aef2-11e8-920b-73348de50434.png)

* isAJsonArray
    * Check if an object is a JsonArray
* isAJsonArrayOrObject
    * Check if an object is a JsonArray or a JsonObject
* isAJsonObject
    * Check if an object is a JsonObject
* isJsonObject
    * ***DEPRECATED*** use `CanParseJsonObject` instead
* isNull
    * Check if an object is null
