package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String SNDWCH_NAMES_OBJ = "name";
    private static final String SNDWCH_NAMES_MAIN = "mainName";
    private static final String SNDWCH_NAMES_AKA = "alsoKnownAs";

    private static final String SNDWCH_IMAGE = "image";

    private static final String SNDWCH_DESCRIPTION = "description";

    private static final String SNDWCH_ORIGIN = "placeOfOrigin";

    private static final String SNDWCH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            // Parsing complete Sandwich JSON string
            JSONObject jsonSandwichObject = new JSONObject(json);

            // Find main name string and AKA string list
            JSONObject names = jsonSandwichObject.getJSONObject(SNDWCH_NAMES_OBJ);
            sandwich.setMainName(names.getString(SNDWCH_NAMES_MAIN));
            JSONArray akaNames = names.getJSONArray(SNDWCH_NAMES_AKA);
            List<String> akaList = new ArrayList<>();
            if (akaNames != null && akaNames.length() > 0) {
                for (int i=0; i<akaNames.length(); i++) {
                    akaList.add(akaNames.getString(i));
                }
            }
            sandwich.setAlsoKnownAs(akaList);

            // Get image URL string
            sandwich.setImage(jsonSandwichObject.getString(SNDWCH_IMAGE));

            // Get description
            sandwich.setDescription(jsonSandwichObject.getString(SNDWCH_DESCRIPTION));

            // Get place of origin string
            sandwich.setPlaceOfOrigin(jsonSandwichObject.getString(SNDWCH_ORIGIN));

            // Get ingredient string list
            JSONArray ingredients = jsonSandwichObject.getJSONArray(SNDWCH_INGREDIENTS);
            List<String> ingredientList = new ArrayList<>();
            if (ingredients != null && ingredients.length() > 0) {
                for (int i=0; i<ingredients.length(); i++) {
                    ingredientList.add(ingredients.getString(i));
                }
            }
            sandwich.setIngredients(ingredientList);

        }
        catch (JSONException jsonEx) {
            Log.e(TAG, "JSON parsing error: " + jsonEx.getMessage());
            return null;
        }

        return sandwich;
    }
}
