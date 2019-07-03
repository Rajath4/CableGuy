package com.mb.sp.cableguy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.mb.sp.cableguy.MainActivity;
import com.mb.sp.cableguy.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //New User Sign Up Information Gathering
        MaterialButton requestForDemoButton = (MaterialButton) findViewById(R.id.request_for_demo_button);
        requestForDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,NewUserSignUpActivity.class));
            }
        });

        //Check authenticity of user and push to dashboard
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
