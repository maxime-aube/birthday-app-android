package fr.maximob.birthdayapp.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import fr.maximob.birthdayapp.android.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        if (false) {
            startActivity(new Intent(this, ListActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

//        finish();
    }

}