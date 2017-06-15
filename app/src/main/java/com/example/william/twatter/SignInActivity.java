package com.example.william.twatter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by William on 6/6/2017.
 */

public class SignInActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        setContentView(R.layout.sign_in);
        super.onCreate(savedInstanceState, persistentState);
    }
}
