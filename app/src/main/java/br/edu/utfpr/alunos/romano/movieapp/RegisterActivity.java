package br.edu.utfpr.alunos.romano.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.edu.utfpr.alunos.romano.movieapp.model.Genres;
import br.edu.utfpr.alunos.romano.movieapp.model.Movies;
import br.edu.utfpr.alunos.romano.movieapp.persistence.DatabaseHelper;
import br.edu.utfpr.alunos.romano.movieapp.utils.UGui;


public class RegisterActivity extends AppCompatActivity {

    private EditText edtNome;
    private RatingBar rtgBar;
    private Spinner spnrGenre;
    private CheckBox cbWatched;
    private Button btnRegister;
    private int option;
    private List<Genres> listGenres;
    ArrayAdapter<Genres> listAdapter;
    //private Movies movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readColorPreference();
        changeTheme();
        //popularDatabaseGenres();
        setContentView(R.layout.activity_register);
        setTitle(R.string.title_register);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edtNome = (EditText) findViewById(R.id.edtNome);
        rtgBar = (RatingBar) findViewById(R.id.ratingBar);
        spnrGenre = (Spinner) findViewById(R.id.spinnerGenre);
        cbWatched = (CheckBox) findViewById(R.id.checkBoxWatched);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        popularSpinner();

        // register movie
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Movies movie = new Movies();

                String nome  = UGui.validaCampoTexto(RegisterActivity.this,
                        edtNome,
                        R.string.alert_empty_name);
                if (nome == null){
                    return;
                }
                movie.setName(String.valueOf(edtNome.getText()));
                movie.setGenre((Genres) spnrGenre.getSelectedItem());
                //movie.setGenre(registerGenre(spnrGenre.getSelectedItem().toString()));
                movie.setWatched(cbWatched.isChecked());
                movie.setScore(rtgBar.getRating());

                try {

                    DatabaseHelper conexao = DatabaseHelper.getInstance(getApplicationContext());

                    conexao.getMovieDao().create(movie);
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_created, Toast.LENGTH_LONG);
                    toast.show();


                    setResult(Activity.RESULT_OK);

                    finish();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void popularSpinner(){

        listGenres = null;

        try {
            DatabaseHelper conexao = DatabaseHelper.getInstance(this);

            listGenres = conexao.getGenreDao()
                    .queryBuilder()
                    .orderBy("genre", true)
                    .query();

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Genres> spinnerAdapter = new ArrayAdapter<Genres>(this,
                android.R.layout.simple_list_item_1,
                listGenres);

        spnrGenre.setAdapter(spinnerAdapter);
    }
//    private Genres registerGenre(String newGenre){
//        List<Genres> listGenres= null;
//        Genres myGenre = new Genres();
//        try {
//            DatabaseHelper conexao = DatabaseHelper.getInstance(this);
//
//            listGenres = conexao.getGenreDao()
//                    .queryBuilder().where().eq("genre",newGenre)
//                    .query();
//
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        }
//
//        if(listGenres.size() == 0){
//            Toast.makeText(getApplicationContext(),"cima", Toast.LENGTH_LONG).show();
//            try {
//                DatabaseHelper conexao = DatabaseHelper.getInstance(getApplicationContext());
//                Toast.makeText(getApplicationContext(), newGenre, Toast.LENGTH_LONG).show();
//                myGenre.setGenre(newGenre);
//                Toast.makeText(getApplicationContext(), myGenre.getGenre()+"ajasidjsa", Toast.LENGTH_LONG).show();
//                conexao.getGenreDao().create(myGenre);
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (java.sql.SQLException e) {
//                e.printStackTrace();
//            }
//        }else {
//
//            for(Genres genre: listGenres){
//                //Toast.makeText(getApplicationContext(), genre.getGenre(), Toast.LENGTH_LONG).show();
//                if(genre.getGenre().equals(newGenre))
//                    myGenre = genre;
//
//            }
//        }
//        Toast.makeText(getApplicationContext(), myGenre.getGenre(), Toast.LENGTH_LONG).show();
//        return myGenre;
//
//    }
}
