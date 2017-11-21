package br.edu.utfpr.alunos.romano.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import br.edu.utfpr.alunos.romano.movieapp.model.Movies;
import br.edu.utfpr.alunos.romano.movieapp.persistence.DatabaseHelper;

public class ListActivity extends AppCompatActivity {
    List<String> opcoes;
    List<String> movies;
    ArrayAdapter<Movies> listAdapter;
    ListView lvMovies;
    private int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readColorPreference();
        changeTheme();
        setContentView(R.layout.activity_list);
        setTitle(R.string.title_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        lvMovies = (ListView) findViewById(R.id.lvmovies);
        createList();
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movies movieSelected = (Movies) parent.getItemAtPosition(position);
                int movieId = movieSelected.getId();
                Intent intent = new Intent(ListActivity.this, EditActivity.class);
                intent.putExtra("movieId", movieId);
                startActivity(intent);
            }
        });

    }

    private void changeTheme() {
        if(option == 0){
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }

    private void readColorPreference() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.theme), Context.MODE_PRIVATE);
        option = sharedPref.getInt(getString(R.string.theme), option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.createList();
    }

    private void createList() {

        lvMovies = (ListView) findViewById(R.id.lvmovies);
        List<Movies> listMovies = null;
        try {
            DatabaseHelper conexao = DatabaseHelper.getInstance(this);

            listMovies = conexao.getMovieDao()
                    .queryBuilder()
                    .orderBy("name", true)
                    .query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listAdapter = new ArrayAdapter<Movies>(ListActivity.this,
                android.R.layout.simple_list_item_1,
                listMovies);

        lvMovies.setAdapter(listAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}