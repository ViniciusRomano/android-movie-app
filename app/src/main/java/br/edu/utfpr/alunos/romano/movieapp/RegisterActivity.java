package br.edu.utfpr.alunos.romano.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

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
    //private Movies movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readColorPreference();
        changeTheme();
        setContentView(R.layout.activity_register);
        setTitle(R.string.title_register);

        edtNome = (EditText) findViewById(R.id.edtNome);
        rtgBar = (RatingBar) findViewById(R.id.ratingBar);
        spnrGenre = (Spinner) findViewById(R.id.spinnerGenre);
        cbWatched = (CheckBox) findViewById(R.id.checkBoxWatched);
        btnRegister = (Button) findViewById(R.id.btnRegister);

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
                movie.setGenre(spnrGenre.getSelectedItem().toString());
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

}
