//package com.example.libraso;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//
//import org.json.JSONObject;
//import org.w3c.dom.Text;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PipedOutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
//import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
//import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
//import dev.shreyaspatil.easyupipayment.model.PaymentApp;
//import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
//import dev.shreyaspatil.easyupipayment.model.TransactionStatus;
//
//public class fine extends Fragment implements View.OnClickListener, PaymentStatusListener {
//    private TextView fine;
//    private Button pay;
//    private Double totalFine;
//    private View v;
//
//    public fine() {
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fine, container, false);;
//        fine = (TextView) view.findViewById(R.id.TotalFine);
//        pay = (Button) view.findViewById(R.id.PayFineButton);
//        totalFine = 0.00;
//        fine.setText("Fine: Rs."+totalFine);
//        pay.setEnabled(false);
//        pay.setOnClickListener(this);
//        getFine();
//        v = view;
//        Log.i(Integer.toString(MainActivity.userid)," Login Successful!");
//
//        return view;
//    }
//
//    public void getFine() {
//        if(MainActivity.userid != -1) {
//            Log.i(Integer.toString(MainActivity.userid)," Tying to Pay Fine!");
//            new GetFine(fine,pay,totalFine).execute(Integer.toString(MainActivity.userid));
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        if(MainActivity.userid != -1) {
//            EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(getActivity())
//                    .setPayeeVpa("pranavsemailid@okhdfcbank")
//                    .setPayeeName("Libraso IIIT Delhi")
//                    .setPayeeMerchantCode("BCR2DN4TRCC27JDB")
//                    .setTransactionId(Integer.toString(MainActivity.userid) + System.currentTimeMillis())
//                    .setTransactionRefId("LIBRASO" + Integer.toString(MainActivity.userid) + System.currentTimeMillis())
//                    .setDescription("User #" + MainActivity.userid + " paying amount of " + totalFine)
//                    .setAmount(Double.toString(totalFine))
//                    .with(PaymentApp.GOOGLE_PAY);
//
//            Log.i("PAYMENT","Payment Built!");
//
//            EasyUpiPayment easyUpiPayment;
//            try {
//                easyUpiPayment = builder.build();
//                easyUpiPayment.setPaymentStatusListener(this);
//                easyUpiPayment.startPayment();
//                Log.i("PAYMENT"," Payment Started!");
//
//            } catch (AppNotFoundException e) {
//                Toast.makeText(getActivity().getApplicationContext(), "Google Pay Not Found! Payment Not Possible!", Toast.LENGTH_SHORT);
//            }
//        }
//        else {
//            Toast.makeText(getActivity().getApplicationContext(), "Invalid User ID!", Toast.LENGTH_SHORT);
//        }
//    }
//
//    @Override
//    public void onTransactionCancelled() {
//        Toast.makeText(getActivity().getApplicationContext(), "Transaction Cancelled!", Toast.LENGTH_SHORT);
//        Log.i("PAYMENT","Payment Cancelled!");
//        new GetFine(fine,pay,totalFine).execute(Integer.toString(MainActivity.userid));
//    }
//
//    @Override
//    public void onTransactionCompleted(TransactionDetails transactionDetails) {
//        if(transactionDetails.getTransactionStatus() == TransactionStatus.FAILURE) {
//            Toast.makeText(getActivity().getApplicationContext(), "Transaction Failed!", Toast.LENGTH_SHORT);
//        }
//        else {
//            Toast.makeText(getActivity().getApplicationContext(), "Transaction Successful!", Toast.LENGTH_SHORT);
//
//            try {
//                URL url = new URL("https://libraso.herokuapp.com/userfine/"+MainActivity.userid);
//                HttpURLConnection uCon = (HttpURLConnection) url.openConnection();
//                uCon.setRequestMethod("POST");
//                OutputStream out = new BufferedOutputStream(uCon.getOutputStream());
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
//                writer.write("{\"amount_due\": 0.0, \"id\": null, \"amount_paid\": 5.0, \"due_date\": null, \"book_id\": null, \"user_id\": "+MainActivity.userid+"}");
//                writer.flush();
//                writer.close();
//                out.close();
//                uCon.connect();
//            }
//            catch(Exception e) {}
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) { }
//            new GetFine(fine,pay,totalFine).execute(Integer.toString(MainActivity.userid));
//        }
//    }
//
//    private class GetFine extends AsyncTask<String,Void,Double> {
//        private TextView t;
//        private Button b;
//        private Double tf;
//
//        public GetFine(TextView T, Button B, Double TF) {
//            t = T;
//            b = B;
//            tf = TF;
//        }
//
//        @Override
//        protected Double doInBackground(String... strings) {
//            Double fine=0.0;
//            try {
//                URL url = new URL("https://libraso.herokuapp.com/userfine/"+strings[0]);
//                HttpURLConnection uCon = (HttpURLConnection) url.openConnection();
//                uCon.setRequestMethod("GET");
//                InputStream in = new BufferedInputStream(uCon.getInputStream());
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                StringBuilder sb = new StringBuilder();
//                String line;
//
//                try {
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line).append('\n');
//                    }
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        in.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                JSONObject r = new JSONObject(sb.toString());
//                r = new JSONObject(r.getJSONArray("data").getJSONObject(0).toString());
//                fine = r.getDouble("amount_due")-r.getDouble("amount_paid");
//            }
//            catch(Exception e) {
//                Log.i("0",e.toString());
//            }
//            tf = fine;
//
//            return fine;
//        }
//
//        @Override
//        protected void onPostExecute(Double fine) {
//            super.onPostExecute(fine);
//
//            t.setText("Fine: Rs."+fine);
//            totalFine = fine;
//            if(fine>0.0) b.setEnabled(true);
//        }
//    }
//}