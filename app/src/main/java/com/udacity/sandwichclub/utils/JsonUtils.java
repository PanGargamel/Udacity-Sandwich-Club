package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static String JSON_KEY_NAME = "name";
    public static String JSON_KEY_MAINNAME = "mainName";
    public static String JSON_KEY_ALSOKNOWNAS = "alsoKnownAs";
    public static String JSON_KEY_PLACEOFORIGIN = "placeOfOrigin";
    public static String JSON_KEY_DESCRIPTION = "description";
    public static String JSON_KEY_IMAGE = "image";
    public static String JSON_KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json){
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject sandwichNames = sandwichJson.getJSONObject(JSON_KEY_NAME);
            mainName = sandwichNames.optString(JSON_KEY_MAINNAME);

            JSONArray sandwichOtherNames = sandwichNames.getJSONArray(JSON_KEY_ALSOKNOWNAS);
            for (int i = 0; i < sandwichOtherNames.length(); i++) {
                alsoKnownAs.add(sandwichOtherNames.optString(i));
            }

            placeOfOrigin = sandwichJson.optString(JSON_KEY_PLACEOFORIGIN);
            description = sandwichJson.optString(JSON_KEY_DESCRIPTION);
            image = sandwichJson.optString(JSON_KEY_IMAGE);

            JSONArray sandwichIngredients = sandwichJson.getJSONArray(JSON_KEY_INGREDIENTS);
            for (int i = 0; i < sandwichIngredients.length(); i++) {
                ingredients.add(sandwichIngredients.optString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
