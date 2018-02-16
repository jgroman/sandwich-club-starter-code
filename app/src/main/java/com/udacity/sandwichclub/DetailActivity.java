package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView sandwichIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            Log.e(TAG, "Cannot parse sandwich JSON data");
            closeOnError();
            return;
        }

        populateUI(sandwich);

        // Check and load image
        String imageUrl = sandwich.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.with(this)
                    .load(imageUrl)
                    .into(sandwichIv);
        }

        // Check and set main name
        String mainName = sandwich.getMainName();
        if (mainName != null && !mainName.isEmpty()) {
            setTitle(mainName);
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Log.d(TAG, "populateUI: ");

        // If there are any data missing, hide appropriate content

        // Populate aliases
        List<String> aliases = sandwich.getAlsoKnownAs();
        TextView aliasesTextView = findViewById(R.id.also_known_tv);

        if (aliases != null && aliases.size() > 0) {
            // Create string containing comma separated list of aliases
            StringBuilder aliasesBuilder = new StringBuilder();
            for (String alias : aliases) {
                aliasesBuilder.append(alias);
                aliasesBuilder.append(", ");
            }
            // Remove trailing comma + space
            String aliasesString = aliasesBuilder.toString();
            aliasesString = aliasesString.substring(0, aliasesString.length() - 2);

            aliasesTextView.setText(aliasesString);
        }
        else {
            // Hide aliases label and content
            TextView aliasesLabelTextView = findViewById(R.id.also_known_as_label_tv);
            aliasesLabelTextView.setVisibility(View.GONE);
            aliasesTextView.setVisibility(View.GONE);
        }

        // Populate description text view
        String description = sandwich.getDescription();
        TextView descriptionTextView = findViewById(R.id.description_tv);

        if (description != null && !description.isEmpty()) {
            descriptionTextView.setText(description);
        }
        else {
            // Hide description label and content
            TextView descriptionLabelTextView = findViewById(R.id.description_label_tv);
            descriptionLabelTextView.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        }

        // Populate place of origin text view
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);

        if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
            placeOfOriginTextView.setText(placeOfOrigin);
        }
        else {
            // Hide place of origin label and content
            TextView placeOfOriginLabelTextView = findViewById(R.id.place_of_origin_label_tv);
            placeOfOriginLabelTextView.setVisibility(View.GONE);
            placeOfOriginTextView.setVisibility(View.GONE);
        }

        // Populate ingredients text view
        List<String> ingredients = sandwich.getIngredients();
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        if (ingredients != null && ingredients.size() > 0) {
            // Create string containing comma separated list of ingredients
            StringBuilder ingredientsBuilder = new StringBuilder();
            for (String ingredient : ingredients) {
                ingredientsBuilder.append(ingredient);
                ingredientsBuilder.append(", ");
            }
            // Remove trailing comma + space
            String ingredientsString = ingredientsBuilder.toString();
            ingredientsString = ingredientsString.substring(0, ingredientsString.length() - 2);

            ingredientsTextView.setText(ingredientsString);
        }
        else {
            // Hide ingredients label and content
            TextView ingredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
            ingredientsLabelTextView.setVisibility(View.GONE);
            ingredientsTextView.setVisibility(View.GONE);
        }

    }
}
