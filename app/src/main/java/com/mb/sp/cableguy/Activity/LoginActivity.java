package com.mb.sp.cableguy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.mb.sp.cableguy.MainActivity;
import com.mb.sp.cableguy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    String LOGIN_API_URL = "https://t22ezb35rb.execute-api.us-west-2.amazonaws.com/DEVELOPMNET/manage_agent_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);

        //New User Sign Up Information Gathering
        TextView requestForDemoButton = (TextView) findViewById(R.id.request_for_demo_button);
        requestForDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, NewUserSignUpActivity.class));
            }
        });

        TextView forgotPasswordButton = (TextView) findViewById(R.id.forgot_password);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Call for Mobiezy", Toast.LENGTH_SHORT).show();
            }
        });

        //Check authenticity of user and push to dashboard
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                new MyAsyncTask().execute();
//sendPost();
            }
        });
    }


    public void sendPost() {
        final String[] loginStatusResponceJSONArray = {null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(LOGIN_API_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Content-Type", "application/json");

                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("userName", "h");
                    jsonParam.put("password", "h");
//                        jsonParam.put("message", message.getMessage());
//                        jsonParam.put("latitude", 0D);
//                        jsonParam.put("longitude", 0D);

                    Log.e("JSON", jsonParam.toString());

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());

//                    os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                    os.writeBytes(jsonParam.toString());
                    os.flush();

                    os.close();

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG", conn.getResponseMessage());

                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    String inputLine;
                    loginStatusResponceJSONArray[0] = in.readLine();
                    in.close();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }


    //AsyncTask to display collection card
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        //Shimmer animation while loading data Refer : https://github.com/facebook/shimmer-android


        @Override
        protected void onPreExecute() {
            //Show animation while loading data
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Authenticating");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String loginStatusResponceJSONArray = null;
            try {
                URL url = new URL(LOGIN_API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                EditText userNameEditText = (EditText) findViewById(R.id.loginUsernameEditText);
                EditText passwordEditText = (EditText) findViewById(R.id.loginPasswordEditText);

                String userNameString = userNameEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("userName", userNameString);
                jsonParam.put("password", passwordString);

                Log.e("JSON", jsonParam.toString());

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());

//                    os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                os.writeBytes(jsonParam.toString());
                os.flush();

                os.close();

                Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                Log.e("MSG", conn.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                loginStatusResponceJSONArray = in.readLine();
                in.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return loginStatusResponceJSONArray;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Log.e("result", result);


            try {
                //Create json object of result string of json from api
                JSONObject jsonobject = new JSONObject(result);

                String p_out_mssg_flg = jsonobject.getString("p_out_mssg_flg");
                String p_out_mssg = jsonobject.getString("p_out_mssg");

                if (p_out_mssg_flg.equalsIgnoreCase("S")) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    AlertDialog.Builder loginFailDialog = new AlertDialog.Builder(LoginActivity.this);
                    loginFailDialog.setTitle("Authentication Failed");
                    loginFailDialog.setIcon(R.drawable.ic_warning_black_24dp);
                    loginFailDialog.setMessage("Please enter proper user id and password");
                    loginFailDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    loginFailDialog.show();
                }


            } catch (Exception e) {
                Log.d("Exception", "" + e);
            }

        }


    }
}