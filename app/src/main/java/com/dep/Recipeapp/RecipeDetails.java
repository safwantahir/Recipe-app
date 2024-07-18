package com.dep.Recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeDetails extends AppCompatActivity {

    private TextView title, ready_in, servings, healthy, instructions;
    private ImageView img, vegeterian;
    private DatabaseReference mRootRef;
    private JSONArray ingredientsArr;
    private List<PresentIngredient> ingredientsLst = new ArrayList<>();
    private RecyclerView myrv;
    private FloatingActionButton fab;
    private boolean like = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        final Intent intent = getIntent();
        final String recipeId = intent.getStringExtra("id");

        img = findViewById(R.id.recipe_img);
        title = findViewById(R.id.recipe_title);
        ready_in = findViewById(R.id.recipe_ready_in);
        servings = findViewById(R.id.recipe_servings);
        healthy = findViewById(R.id.recipe_healthy);
        vegeterian = findViewById(R.id.recipe_vegetarian);
        instructions = findViewById(R.id.recipe_instructions);
        fab = findViewById(R.id.floatingActionButton);

        mRootRef = FirebaseDatabase.getInstance().getReference().child("favorites").child(recipeId); // Directly initialize the reference

        getRecipeData(recipeId);

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("mRootRef", String.valueOf(dataSnapshot));
                if (dataSnapshot.getValue() != null) {
                    fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    like = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like = !like;
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (like) {
                            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Map<String, String> favorites = new HashMap<>();
                            favorites.put("img", intent.getStringExtra("img"));
                            favorites.put("title", intent.getStringExtra("title"));
                            mRootRef.setValue(favorites);
                        } else {
                            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            mRootRef.setValue(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors.
                    }
                });
            }
        });

        myrv = findViewById(R.id.recipe_ingredients_rv);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void getRecipeData(final String recipeId) {
        String URL = "https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey="put your api here"";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Picasso.get().load(response.getString("image")).into(img);
                            title.setText(response.getString("title"));
                            ready_in.setText(String.valueOf(response.getInt("readyInMinutes")));
                            servings.setText(String.valueOf(response.getInt("servings")));
                            if (response.getBoolean("veryHealthy")) {
                                healthy.setText("Healthy");
                            }
                            if (response.getBoolean("vegetarian")) {
                                vegeterian.setImageResource(R.drawable.vegeterian);
                            }
                            if (response.has("instructions") && !response.getString("instructions").isEmpty()) {
                                instructions.setText(Html.fromHtml(response.getString("instructions")));
                            } else {
                                String msg = "Unfortunately, the recipe you were looking for not found, to view the original recipe click on the link below:" +
                                        "<a href=" + response.getString("spoonacularSourceUrl") + ">" + response.getString("spoonacularSourceUrl") + "</a>";
                                instructions.setMovementMethod(LinkMovementMethod.getInstance());
                                instructions.setText(Html.fromHtml(msg));
                            }
                            ingredientsArr = response.getJSONArray("extendedIngredients");
                            for (int i = 0; i < ingredientsArr.length(); i++) {
                                JSONObject jsonObject1 = ingredientsArr.getJSONObject(i);
                                ingredientsLst.add(new PresentIngredient(jsonObject1.optString("originalString"), jsonObject1.optString("image")));
                            }
                            RecyclerViewAdapterRecipeIngredient myAdapter = new RecyclerViewAdapterRecipeIngredient(getApplicationContext(), ingredientsLst);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
