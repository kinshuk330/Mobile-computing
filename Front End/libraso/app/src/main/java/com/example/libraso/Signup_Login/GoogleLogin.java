package com.example.libraso.Signup_Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.libraso.MainActivity;
import com.example.libraso.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class GoogleLogin extends AppCompatActivity {
    TabLayout tablayout;
    ViewPager viewpage;
    int RC_SIGN_IN=0;
    String TAG="GoogleLogin";
    ImageButton signin;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
//      app signin
        tablayout=findViewById(R.id.tablayout);
        viewpage=findViewById(R.id.viewpager);

        tablayout.addTab(tablayout.newTab().setText("Login"));
        tablayout.addTab(tablayout.newTab().setText("Signup"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ls_adapter adapter = new ls_adapter(getSupportFragmentManager(),this,tablayout.getTabCount());
        viewpage.setAdapter(adapter);
        viewpage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));


//        File f;
//        f = new File(getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/isuserloged.txt");
//        System.out.println("start");
//        String s=null;
//
//        if(f.exists()!=false){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            BufferedReader br = null;
//            try {
//                br = new BufferedReader(new FileReader(f));
//                s = br.readLine();
//                br.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            intent.putExtra("User_details",s);
//            System.out.println("file exist");
//            startActivity(intent);
//            finish();
//        }


//        google signin
        signin = findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressdialog= new ProgressDialog(GoogleLogin.this);
                progressdialog.show();
                progressdialog.setContentView(R.layout.progress_dialog);
                progressdialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                switch (view.getId()) {
                    case R.id.sign_in_button:
                        progressdialog.dismiss();
                        signIn();
                        break;
                    // ...
                }
            }
        });

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account != null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();

            String path= getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/isuserloged.txt";
            FileOutputStream writer = null;
            String obj=String.valueOf("{\"first_name\":\""+personGivenName+"\",\"last_name\":\""+personFamilyName+"\",\"username\":\""+personName+"\",\"email\":\""+personEmail+"\"}");
            try {
                writer = new FileOutputStream(path, false);
                writer.write(obj.getBytes());
                writer.close();
                System.out.println("Successfully saved");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("User_details",obj);
            startActivity(intent);

            finish();

        }else {
        }

    }


}