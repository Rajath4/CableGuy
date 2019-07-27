package com.mb.sp.cableguy.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mb.sp.cableguy.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    String DASHBOARD_API_URL = "https://gxk7f7615b.execute-api.us-west-2.amazonaws.com/production/rupayee_getDashboardStats";

    TextView pendingAmoutTextView,partialAmoutTextView,collectedAmoutTextView,totalAmoutTextView;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        pendingAmoutTextView = (TextView) view.findViewById(R.id.pendingAmoutTextView);
        partialAmoutTextView = (TextView) view.findViewById(R.id.partialAmountTextView);
        collectedAmoutTextView = (TextView) view.findViewById(R.id.collectedAMoutTextView);
        totalAmoutTextView = (TextView) view.findViewById(R.id.totalAmountTextView);


        new MyAsyncTask().execute();

        return view;
    }


    //AsyncTask to display collection card
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        //Shimmer animation while loading data Refer : https://github.com/facebook/shimmer-android


        @Override
        protected void onPreExecute() {
            //Show animation while loading data
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setTitle("Authenticating");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String loginStatusResponceJSONArray = null;
            try {
                URL url = new URL(DASHBOARD_API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

//                EditText userNameEditText = (EditText) findViewById(R.id.loginUsernameEditText);
//                EditText passwordEditText = (EditText) findViewById(R.id.loginPasswordEditText);
//
//                String userNameString = userNameEditText.getText().toString();
//                String passwordString = passwordEditText.getText().toString();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("agent_id", "7258");

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
                Log.e("Data",loginStatusResponceJSONArray);
                in.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return loginStatusResponceJSONArray;
        }

        protected void onPostExecute(String result) {
//            progressDialog.dismiss();
            Log.e("resultAA", result);

            pendingAmoutTextView.setText("dfsjh");

            try {
                Log.e("sj","dsnfkn");

                //Create json object of result string of json from api
                JSONObject jsonobject = new JSONObject(result);

                double pendingAmount = Double.parseDouble(jsonobject.getString("pendingAmount"));
                double partialAmount = Double.parseDouble(jsonobject.getString("partialAmount"));
                double collectedAmount = Double.parseDouble(jsonobject.getString("collectedAmount"));
                double totalAmount = Double.parseDouble(jsonobject.getString("totalAmount"));

                DecimalFormat formatter = new DecimalFormat("#,###.00");

                pendingAmoutTextView.setText(formatter.format(pendingAmount));
                partialAmoutTextView.setText(formatter.format(partialAmount));
                collectedAmoutTextView.setText(formatter.format(collectedAmount));
                totalAmoutTextView.setText(formatter.format(totalAmount));



            } catch (Exception e) {
                Log.d("Exception", "" + e);
            }

        }


    }



}
