package com.korosmatick.sampleapp;

import static com.korosmatick.sampleapp.util.DummyDataGenerator.getSampleActors;
import static com.korosmatick.sampleapp.util.DummyDataGenerator.getSampleGenres;
import static com.korosmatick.sampleapp.util.DummyDataGenerator.getSampleMovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.korosmatick.gridrecyclerview.GridRecyclerViewAdapter;
import com.korosmatick.gridrecyclerview.GridRecyclerViewHelper;
import com.korosmatick.gridrecyclerview.GridDescriptor;
import com.korosmatick.sampleapp.grid.SampleGridRecyclerViewHelper;
import com.korosmatick.sampleapp.model.Actor;
import com.korosmatick.sampleapp.model.Genre;
import com.korosmatick.sampleapp.model.GridHeader;
import com.korosmatick.sampleapp.model.Movie;
import com.korosmatick.sampleapp.util.HeaderKey;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private GridRecyclerViewAdapter gridRecyclerViewAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        GridRecyclerViewHelper helper = new SampleGridRecyclerViewHelper();

        Map<GridHeader, GridDescriptor<?>> gridItems = new HashMap<>();

        GridDescriptor<Genre> genres = new GridDescriptor<>(1, getSampleGenres());
        gridItems.put(new GridHeader("Genres", HeaderKey.GENRE), genres);

        GridDescriptor<Movie> movies = new GridDescriptor<>(2, getSampleMovies());
        gridItems.put(new GridHeader("Movies", HeaderKey.MOVIE), movies);

        GridDescriptor<Actor> actors = new GridDescriptor<>(3, getSampleActors());
        gridItems.put(new GridHeader("Actors", HeaderKey.ACTOR), actors);

        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(helper, gridItems, false);
        recyclerView.setAdapter(gridRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}