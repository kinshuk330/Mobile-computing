package com.example.libraso.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.libraso.Complaint;
import com.example.libraso.Events.upcoming_events;
import com.example.libraso.Loading;
import com.example.libraso.R;
import com.example.libraso.Signup_Login.GoogleLogin;
import com.example.libraso.Suggest_ebook;
import com.example.libraso.fine;
import com.example.libraso.show_book_grid;
import com.example.libraso.show_holds.show_all_holds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Admin_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    static public FragmentManager fm;
    public static int userid=-1;
    private AlertDialog progressDialog;
    ImageView userimage;
    TextView username;
    String personName=null;
    Uri personPhoto=null;
    GoogleSignInClient mGoogleSignInClient;
    Loading loader=new Loading();
    IntentFilter startload= new IntentFilter("START_LOADING");
    IntentFilter endload= new IntentFilter("STOP_LOADING");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Code for navigation Drawer
        super.onCreate(savedInstanceState);
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        LayoutInflater infl=this.getLayoutInflater();
//        builder.setView(infl.inflate(R.layout.progress_dialog,null));
//        builder.setCancelable(false);
//        progressDialog=builder.create();
//        progressDialog.show();
//        System.out.println("start");
        Intent intent = getIntent();
        try {
            JSONObject user_account= new JSONObject(intent.getStringExtra("User_details"));
            System.out.println(user_account);

            userid=user_account.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//above lines to be uncommented commented to bypass login
        setContentView(R.layout.activity_admin);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.admin_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //        yaha kar saka
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName().toString();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
        }

//        username= findViewById(R.id.username);
//
//        userimage=findViewById(R.id.userimage);
//        try {
//            username.setText(personName);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        try{
//            userimage.setImageURI(personPhoto);}
//        catch (Exception e){
//            e.printStackTrace();
//        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container,
                    new show_book_grid(fm)).commit();
            navigationView.setCheckedItem(R.id.issue_book);
        }
        // End of code of navigation drawer

        Toast toast=Toast.makeText(getApplicationContext(),"Hello MALIK!!!!!!",Toast.LENGTH_LONG);
        toast.setMargin(50,50);
        toast.show();


    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin_add_books:
                fm.beginTransaction().replace(R.id.fragment_container,
                        new admin_add_books()).commit();
                break;

            case R.id.admin_add_student:
                fm.beginTransaction().replace(R.id.fragment_container,
                        new admin_add_student()).commit();
                break;
            case R.id.fine:
                fm.beginTransaction().replace(R.id.fragment_container,
                        new fine()).commit();
                break;
            case R.id.Complaint:
                fm.beginTransaction().replace(R.id.fragment_container,
                        new Complaint()).commit();
                break;
            case R.id.show_all_holds:
                fm.beginTransaction().replace(R.id.fragment_container,
                        new show_all_holds()).commit();
                break;
            case R.id.suggest_book:
                fm.beginTransaction().replace(R.id.fragment_container,
                        new Suggest_ebook()).commit();
                break;
            case R.id.logout:
                signOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        String path= getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/isuserloged.txt";
                        try {
                            Files.deleteIfExists(Paths.get(path));
                        }
                        catch (IOException e) {

                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
                        startActivity(intent);
                        Toast.makeText(Admin_activity.this,"Signed Out Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(loader,startload );
        registerReceiver(loader, endload);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(loader);
    }
}