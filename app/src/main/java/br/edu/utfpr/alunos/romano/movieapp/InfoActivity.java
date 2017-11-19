package br.edu.utfpr.alunos.romano.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {
    private Button btnBack;
    private int option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readColorPreference();
        changeTheme();
        setContentView(R.layout.activity_info);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
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
