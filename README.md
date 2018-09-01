# JsonUtils

Some Json utilities for App Inventor (& Thunkable etc.)

Functions:

* List2JsonString
    * Convert an App Inventor list into a json string
    * Any App Inventor list except for **lists of key-value pairs** would be considered as a JsonArray
        * **list of key-value pairs:** list with all list items are lists with two items and the first list item is a text) (see sample below
    * ![](https://user-images.githubusercontent.com/22613139/44947212-54df6f00-ae3c-11e8-86e6-50bc0d462a69.png)
* isJsonObject
    * Check if the List can be converted into a JsonObject