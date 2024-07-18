/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.dep.Recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchrecipeActivity extends Fragment {
    private List<PresentIngredient> lstIngredient = new ArrayList<>();
    private RecyclerView myrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_search, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar_search);
        myrv = RootView.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        RecyclerViewAdapterIngredient myAdapter = new RecyclerViewAdapterIngredient(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);

        Button searchIngredients = RootView.findViewById(R.id.ingredients_search);
        searchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tmp = RecyclerViewAdapterIngredient.ingredientsList;
                if(tmp.isEmpty()){
                    Toast.makeText(getActivity(), "You must select at least one ingredient", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent searchResultsIntent = new Intent(getActivity(), ShowResultsActivity.class);
                    startActivity(searchResultsIntent);
                }
            }
        });


        return RootView;
    }

    private void initializeIngredients() {
        lstIngredient.add(new PresentIngredient("Beef", "beef-cubes-raw.png"));
        lstIngredient.add(new PresentIngredient("Fish", "fish-fillet.jpg"));
        lstIngredient.add(new PresentIngredient("Chicken", "chicken-breasts.png"));
        lstIngredient.add(new PresentIngredient("Tuna", "canned-tuna.png"));
        lstIngredient.add(new PresentIngredient("Flour", "flour.png"));
        lstIngredient.add(new PresentIngredient("Rice", "uncooked-white-rice.png"));
        lstIngredient.add(new PresentIngredient("Pasta", "fusilli.jpg"));
        lstIngredient.add(new PresentIngredient("Cheese", "cheddar-cheese.png"));
        lstIngredient.add(new PresentIngredient("Butter", "butter.png"));
        lstIngredient.add(new PresentIngredient("Bread", "white-bread.jpg"));
        lstIngredient.add(new PresentIngredient("Onion", "brown-onion.png"));
        lstIngredient.add(new PresentIngredient("Garlic", "garlic.jpg"));
        lstIngredient.add(new PresentIngredient("Milk", "milk.png"));
        lstIngredient.add(new PresentIngredient("Eggs", "egg.png"));
        lstIngredient.add(new PresentIngredient("Oil", "vegetable-oil.jpg"));
        lstIngredient.add(new PresentIngredient("Yogurt", "plain-yogurt.jpg"));
        lstIngredient.add(new PresentIngredient("Salt", "salt.jpg"));
        lstIngredient.add(new PresentIngredient("Sugar", "sugar-in-bowl.png"));
        lstIngredient.add(new PresentIngredient("Pepper", "pepper.jpg"));
        lstIngredient.add(new PresentIngredient("Water", "water.jpg"));
        lstIngredient.add(new PresentIngredient("Parsley", "parsley.jpg"));
        lstIngredient.add(new PresentIngredient("Basil", "basil.jpg"));
        lstIngredient.add(new PresentIngredient("Chocolate", "milk-chocolate.jpg"));
        lstIngredient.add(new PresentIngredient("Nuts", "hazelnuts.png"));
        lstIngredient.add(new PresentIngredient("Tomato", "tomato.png"));
        lstIngredient.add(new PresentIngredient("Cucumber", "cucumber.jpg"));
        lstIngredient.add(new PresentIngredient("Bell pepper", "red-bell-pepper.jpg"));
        lstIngredient.add(new PresentIngredient("Mushrooms", "portabello-mushrooms.jpg"));
        lstIngredient.add(new PresentIngredient("Lemon", "lemon.jpg"));
        lstIngredient.add(new PresentIngredient("Orange", "orange.jpg"));
        lstIngredient.add(new PresentIngredient("Banana", "bananas.jpg"));
        lstIngredient.add(new PresentIngredient("Wine", "red-wine.jpg"));
        lstIngredient.add(new PresentIngredient("Apple", "apple.jpg"));
        lstIngredient.add(new PresentIngredient("Berries", "berries-mixed.jpg"));
        lstIngredient.add(new PresentIngredient("Biscuits", "buttermilk-biscuits.jpg"));
        lstIngredient.add(new PresentIngredient("Pineapple", "pineapple.jpg"));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.log_out, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }





}