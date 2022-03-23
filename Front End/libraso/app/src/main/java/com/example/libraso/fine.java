package com.example.libraso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fine extends Fragment implements View.OnClickListener {
    private TextView fine;
    private Button pay;
    private Double totalFine;
    private GoogleSignInAccount acc;

    public fine() {

    }

    public fine(GoogleSignInAccount user) {
        acc = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fine, container, false);;
        fine = (TextView) view.findViewById(R.id.TotalFine);
        pay = (Button) view.findViewById(R.id.PayFineButton);
        totalFine = -1.0;
        fine.setText("Fine: Rs."+totalFine);
        pay.setEnabled(false);
        pay.setOnClickListener(this);
        getFine();

        return view;
    }

    public void getFine() {
        //if(acc != null) {
        new GetFine(fine,pay,totalFine).execute("4");
        //}
    }

    @Override
    public void onClick(View view) {
        fine.setText("Fine: Rs.0");
        totalFine = 0.0;
        pay.setEnabled(false);
    }

    private class GetFine extends AsyncTask<String,Void,Double> {
        private TextView t;
        private Button b;
        private Double tf;

        public GetFine(TextView T, Button B, Double TF) {
            t = T;
            b = B;
            tf = TF;
        }

        @Override
        protected Double doInBackground(String... strings) {
            Double fine=0.0;
            try {
                Log.i("0",strings[0]);
                URL url = new URL("https://libraso.herokuapp.com/fines/"+strings[0]);
                Log.i("0","1");
                HttpURLConnection uCon = (HttpURLConnection) url.openConnection();
                Log.i("0","2");
                uCon.setRequestMethod("GET");
                Log.i("0","3");
                InputStream in = new BufferedInputStream(uCon.getInputStream());
                Log.i("0","4");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                Log.i("0","5");
                StringBuilder sb = new StringBuilder();
                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                JSONObject r = new JSONObject(sb.toString());
                fine = r.getDouble("amount_due")-r.getDouble("amount_paid");
            }
            catch(Exception e) {
                Log.i("0",e.toString());
            }
            tf = fine;

            return fine;
        }

        @Override
        protected void onPostExecute(Double fine) {
            super.onPostExecute(fine);

            t.setText("Fine: Rs."+fine);
            if(fine>0.0) b.setEnabled(true);
        }
    }
}