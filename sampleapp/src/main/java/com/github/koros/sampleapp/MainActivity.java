package com.github.koros.sampleapp;

import static com.github.koros.sampleapp.util.DummyDataGenerator.getSampleActors;
import static com.github.koros.sampleapp.util.DummyDataGenerator.getSampleGenres;
import static com.github.koros.sampleapp.util.DummyDataGenerator.getSampleMovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.github.koros.gridrecyclerview.GridRecyclerViewAdapter;
import com.github.koros.gridrecyclerview.GridRecyclerViewHelper;
import com.github.koros.gridrecyclerview.GridDescriptor;
import com.github.koros.sampleapp.grid.SampleGridRecyclerViewHelper;
import com.github.koros.sampleapp.model.Actor;
import com.github.koros.sampleapp.model.Genre;
import com.github.koros.sampleapp.model.GridHeader;
import com.github.koros.sampleapp.model.Movie;
import com.github.koros.sampleapp.util.HeaderKey;

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