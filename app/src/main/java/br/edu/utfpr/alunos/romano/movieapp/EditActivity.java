package br.edu.utfpr.alunos.romano.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;

import br.edu.utfpr.alunos.romano.movieapp.model.Movies;
import br.edu.utfpr.alunos.romano.movieapp.persistence.DatabaseHelper;
import br.edu.utfpr.alunos.romano.movieapp.utils.UGui;

import static br.edu.utfpr.alunos.romano.movieapp.R.string.txt_active_edit_mode;
import static br.edu.utfpr.alunos.romano.movieapp.R.string.txt_disable_edit_mode;
import static br.edu.utfpr.alunos.romano.movieapp.R.string.txt_update_toast;


public class EditActivity extends AppCompatActivity {

    private EditText edtNome;
    private RatingBar rtgBar;
    private Spinner spnrGenre;
    private CheckBox cbWatched;
    private Movies movie;
    private Button btnUpdate;
    private int movieId;
    private ListView lvMovies;
    private static boolean EDIT_MODE = false;
    private int option = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readColorPreference();
        changeTheme();
        setContentView(R.layout.activity_edit);

        setTitle(R.string.title_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edtNome   = (EditText) findViewById(R.id.edtNome);
        rtgBar = (RatingBar) findViewById(R.id.ratingBar);
        spnrGenre = (Spinner) findViewById(R.id.spinnerGenre);
        cbWatched = (CheckBox) findViewById(R.id.checkBoxWatched);
        btnUpdate =(Button) findViewById(R.id.btnUpdate);
        lvMovies = (ListView) findViewById(R.id.lvmovies);
        // SET VIEW MODE
        edtNome.setFocusable(false);
        rtgBar.setIsIndicator(true);
        spnrGenre.setEnabled(false);
        cbWatched.setClickable(false);
        btnUpdate.setVisibility(View.GONE);
        EDIT_MODE = false;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        movieId = bundle.getInt("movieId");
        populate(movieId);
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

    public void populate(int movieId){
        try {

            DatabaseHelper conexao = DatabaseHelper.getInstance(this);
            movie = conexao.getMovieDao().queryForId(movieId);
            edtNome.setText(movie.getName());
            rtgBar.setRating(movie.getScore());
            spnrGenre.setSelection(getIndex(spnrGenre, movie.getGenre()));
            cbWatched.setChecked(movie.isWatched());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void updateMovie(View view){

        String nome  = UGui.validaCampoTexto(EditActivity.this,
                edtNome,
                R.string.alert_empty_name);
        if (nome == null){
            return;
        }
        movie.setName(String.valueOf(edtNome.getText()));
        movie.setGenre(spnrGenre.getSelectedItem().toString());
        movie.setWatched(cbWatched.isChecked());
        movie.setScore(rtgBar.getRating());

        try {
            DatabaseHelper conexao = DatabaseHelper.getInstance(this);
            conexao.getMovieDao().update(movie);
            setResult(Activity.RESULT_OK);
            Toast toast = Toast.makeText(EditActivity.this, txt_update_toast, Toast.LENGTH_SHORT);
            toast.show();
            finish();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editMode(){
        if(EDIT_MODE){
            Toast.makeText(EditActivity.this, txt_disable_edit_mode, Toast.LENGTH_SHORT).show();
            setTitle(R.string.title_view);
            edtNome.setFocusable(false);
            rtgBar.setIsIndicator(true);
            spnrGenre.setEnabled(false);
            cbWatched.setClickable(false);
            btnUpdate.setVisibility(View.GONE);
        }else{
            Toast.makeText(EditActivity.this, txt_active_edit_mode, Toast.LENGTH_SHORT).show();
            setTitle(R.string.title_edit);
            edtNome.setFocusableInTouchMode(true);
            rtgBar.setIsIndicator(false);
            spnrGenre.setEnabled(true);
            cbWatched.setClickable(true);
            btnUpdate.setVisibility(View.VISIBLE);
        }
        EDIT_MODE = !EDIT_MODE;
    }

    public void deleteMovie(){
        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                            try {
                                DatabaseHelper conexao = DatabaseHelper.getInstance(EditActivity.this);
                                conexao.getMovieDao().delete(movie);
                                setResult(Activity.RESULT_OK);
                                Toast toast = Toast.makeText(EditActivity.this, R.string.txt_delete_toast, Toast.LENGTH_SHORT);
                                toast.show();
                                finish();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UGui.confirmaAcao(this, R.string.txt_confirm_delete, listener);
//        try {
//            DatabaseHelper conexao = DatabaseHelper.getInstance(this);
//            conexao.getMovieDao().delete(movie);
//            setResult(Activity.RESULT_OK);
//            Toast toast = Toast.makeText(EditActivity.this, txt_delete_toast, Toast.LENGTH_SHORT);
//            toast.show();
//            finish();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selected_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.itemEdit:
                editMode();
                return true;
            case R.id.itemDelele:
                deleteMovie();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
