package br.edu.utfpr.alunos.romano.movieapp.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by romano on 11/21/17.
 */

public class Genres {
    @DatabaseField(generatedId=true)
    private int   id;

    @DatabaseField(canBeNull = false, unique = true)
    private String genre;

    public Genres() {
    }

    public Genres(String text) {
        setGenre(text);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString(){
        return getGenre();
    }
}
