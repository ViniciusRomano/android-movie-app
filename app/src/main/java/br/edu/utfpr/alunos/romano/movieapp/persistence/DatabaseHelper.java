package br.edu.utfpr.alunos.romano.movieapp.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.edu.utfpr.alunos.romano.movieapp.model.Movies;

/**
 * Created by romano on 11/14/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME    = "movies.db";
    private static final int    DB_VERSION = 1;

    private static DatabaseHelper instance;
    private Dao<Movies, Integer> movieDao;

    public static DatabaseHelper getInstance(Context contexto){

        if (instance == null){
            instance = new DatabaseHelper(contexto);
        }

        return instance;
    }

    private DatabaseHelper(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
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
}