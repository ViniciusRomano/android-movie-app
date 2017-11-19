package br.edu.utfpr.alunos.romano.movieapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by romano on 11/14/17.
 */

@DatabaseTable
public class Movies {

    @DatabaseField(generatedId=true)
    private int   id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField
    private float score;

    @DatabaseField
    private String genre;

    @DatabaseField
    private boolean watched;

    public Movies(){
    }

    public Movies(int id, String name, float score, String genre, boolean watched) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.genre = genre;
        this.watched = watched;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Override
    public String toString(){
        return getName();
    }
}

