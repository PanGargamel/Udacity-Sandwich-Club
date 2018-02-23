package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json){
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwichJson = new JSONObject(json);

            JSONObject sandwichNames = sandwichJson.getJSONObject("name");
            mainName = sandwichNames.getString("mainName");

            JSONArray sandwichOtherNames = sandwichNames.getJSONArray("alsoKnownAs");
            for (int i = 0; i < sandwichOtherNames.length(); i++) {
                alsoKnownAs.add(sandwichOtherNames.getString(i));
            }

            placeOfOrigin = sandwichJson.getString("placeOfOrigin");
            description = sandwichJson.getString("description");
            image = sandwichJson.getString("image");

            JSONArray sandwichIngredients = sandwichJson.getJSONArray("ingredients");
            for (int i = 0; i < sandwichIngredients.length(); i++) {
                ingredients.add(sandwichIngredients.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
