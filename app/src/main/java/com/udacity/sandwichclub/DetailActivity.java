package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.title_tv) TextView titleTextView;
    @BindView(R.id.origin_label_tv) TextView originLabelTextView;
    @BindView(R.id.origin_tv) TextView originTextView;
    @BindView(R.id.description_tv) TextView descriptionTextView;
    @BindView(R.id.ingredients_tv) TextView ingredientsTextView;
    @BindView(R.id.also_known_label_tv) TextView alsoKnownLabelTextView;
    @BindView(R.id.also_known_tv) TextView alsoKnownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.image_error_placeholder)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        titleTextView.setText(sandwich.getMainName());
        originTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());

        // populate ingredients
        for(int i = 0; i < sandwich.getIngredients().size(); i++){
            String ingredient = sandwich.getIngredients().get(i);

            ingredientsTextView.append("- " + ingredient);

            // append new line if that's not last ingredient
            if(i < sandwich.getIngredients().size()-1)
                ingredientsTextView.append("\n");
        }

        // populate "Also known as"
        for(int i = 0; i < sandwich.getAlsoKnownAs().size(); i++){
            String name = sandwich.getAlsoKnownAs().get(i);

            // append dash on the beginning of each line, if there's more than 1 name
            if(sandwich.getAlsoKnownAs().size() > 1)
                alsoKnownTextView.append("- ");

            alsoKnownTextView.append(name);

            // append new line if that's not last name
            if(i < sandwich.getAlsoKnownAs().size()-1)
                alsoKnownTextView.append("\n");
        }

        // hide origin if empty
        ViewGroup parent = (ViewGroup) originLabelTextView.getParent();
        if(originTextView.getText().length() == 0){
            parent.removeView(originLabelTextView);
            parent.removeView(originTextView);
        }

        // hide "Also known as" if empty
        if(alsoKnownTextView.getText().length() == 0){
            parent.removeView(alsoKnownLabelTextView);
            parent.removeView(alsoKnownTextView);
        }
    }
}
