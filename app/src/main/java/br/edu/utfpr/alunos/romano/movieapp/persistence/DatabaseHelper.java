package br.edu.utfpr.alunos.romano.movieapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.alunos.romano.movieapp.R;
import br.edu.utfpr.alunos.romano.movieapp.model.Genres;
import br.edu.utfpr.alunos.romano.movieapp.model.Movies;

/**
 * Created by romano on 11/14/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME    = "movies.db";
    private static final int    DB_VERSION = 2;
    private Context               context;
    private static DatabaseHelper instance;
    private Dao<Movies, Integer> movieDao;
    private Dao<Genres, Integer> genreDao;
    public static DatabaseHelper getInstance(Context contexto){

        if (instance == null){
            instance = new DatabaseHelper(contexto);
        }

        return instance;
    }

    private DatabaseHelper(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
        context = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Genres.class);

            String[] basicGenres = context.getResources().getStringArray(R.array.genres);

            List<Genres> lista = new ArrayList<Genres>();

            for(int cont = 0; cont < basicGenres.length; cont++){

                Genres tipo = new Genres(basicGenres[cont]);
                lista.add(tipo);
            }

            getGenreDao().create(lista);
            TableUtils.createTable(connectionSource, Movies.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "onCreate", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            TableUtils.dropTable(connectionSource, Movies.class, true);

            onCreate(database, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "onUpgrade", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Movies, Integer> getMovieDao() throws SQLException {

        if (movieDao == null) {
            movieDao = getDao(Movies.class);
        }

        return movieDao;
    }

    public Dao<Genres, Integer> getGenreDao() throws SQLException {

        if (genreDao == null) {
            genreDao = getDao(Genres.class);
        }

        return genreDao;
    }
}