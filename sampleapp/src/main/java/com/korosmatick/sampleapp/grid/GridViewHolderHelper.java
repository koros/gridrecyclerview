package com.korosmatick.sampleapp.grid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.korosmatick.gridrecyclerview.GridCellViewHolder;
import com.korosmatick.sampleapp.R;
import com.korosmatick.sampleapp.model.Actor;
import com.korosmatick.sampleapp.model.Director;
import com.korosmatick.sampleapp.model.Genre;
import com.korosmatick.sampleapp.model.GridHeader;
import com.korosmatick.sampleapp.model.Movie;
import com.korosmatick.sampleapp.model.Studio;

public class GridViewHolderHelper {

    @NonNull
    public static ViewGroup getGridView(GridHeader key, @NonNull ViewGroup parent) {
        switch (key.getKey()) {
            case ACTOR:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_view, parent, false);
            case GENRE:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_view, parent, false);
            case MOVIE:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_view, parent, false);
            case STUDIO:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.studio_view, parent, false);
            case DIRECTOR:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.director_view, parent, false);
            default:
                throw new IllegalArgumentException("Unknown Header Key Value");
        }
    }

    @NonNull
    public static GridCellViewHolder getGridViewHolder(GridHeader key, @NonNull ViewGroup parent) {
        switch (key.getKey()) {
            case ACTOR:
                return new ActorViewHolder(parent);
            case GENRE:
                return new GenreViewHolder(parent);
            case MOVIE:
                return new MovieViewHolder(parent);
            case STUDIO:
                return new StudioViewHolder(parent);
            case DIRECTOR:
                return new DirectorViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unknown Header Key Value");
        }
    }

    public static void onBindGridView(@NonNull GridCellViewHolder viewHolder, Object item) {
        viewHolder.bind(item);
    }
}
