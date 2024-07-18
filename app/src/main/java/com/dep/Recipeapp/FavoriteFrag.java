package com.dep.Recipeapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoriteFrag extends Fragment {
    private List<RecipeMethods> lstFavorites;
    private RecyclerView myrv;
    private DatabaseReference mRootRef;
    private ProgressBar progressBar;
    private TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        Toolbar mToolbarContact = rootView.findViewById(R.id.toolbar_favorites);
        progressBar = rootView.findViewById(R.id.progressbar);
        emptyView = rootView.findViewById(R.id.empty_view);
        getFavorites(rootView);
        return rootView;
    }

    private void getFavorites(final View rootView) {
        mRootRef = FirebaseDatabase.getInstance().getReference().child("favorites"); // Use a constant reference or dynamic path if needed
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstFavorites = new ArrayList<>();
                HashMap favorites = (HashMap) dataSnapshot.getValue();
                if (favorites != null) {
                    for (Object recipe : favorites.keySet()) {
                        String title = (String) dataSnapshot.child(recipe.toString()).child("title").getValue();
                        String img = (String) dataSnapshot.child(recipe.toString()).child("img").getValue();
                        lstFavorites.add(new RecipeMethods(recipe.toString(), title, img, 0, 0));
                    }
                }
                progressBar.setVisibility(View.GONE);
                myrv = rootView.findViewById(R.id.recycleview_favorites);
                if (lstFavorites.isEmpty()) {
                    myrv.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    RecyclerViewAdapterFavorites myAdapter = new RecyclerViewAdapterFavorites(getContext(), lstFavorites);
                    myrv.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.log_out, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
