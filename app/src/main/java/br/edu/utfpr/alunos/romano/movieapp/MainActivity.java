package br.edu.utfpr.alunos.romano.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int option = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readThemePreference();
        changeTheme();
        setContentView(R.layout.activity_main);
    }

    public void goToRegisterScreen(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void goToListScreen(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
//
    private void changeTheme() {
          if(option == 0){
              setTheme(R.style.AppTheme);
          }else{
              setTheme(R.style.DarkTheme);
          }
    }

    private void readThemePreference() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.theme), Context.MODE_PRIVATE);
        option = sharedPref.getInt(getString(R.string.theme), option);
    }
    private void saveThemePreference(int newValue) {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.theme), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.theme), newValue);
        editor.commit();
        option = newValue;
        changeTheme();
    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.themes, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch(option) {
            case 0:
                menu.getItem(0).setChecked(true);
                return true;
            case 1:
                menu.getItem(1).setChecked(true);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        switch(item.getItemId()) {
            case R.id.menu_normal_theme:
//                option = R.style.AppTheme;
//                MainActivity.this.recreate();
                saveThemePreference(0);
                recreate();
                return true;
            case R.id.menu_dark_theme:
//                option = R.style.DarkTheme;
//                MainActivity.this.recreate();
                saveThemePreference(1);
                recreate();
                return true;
            case R.id.menu_info:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
