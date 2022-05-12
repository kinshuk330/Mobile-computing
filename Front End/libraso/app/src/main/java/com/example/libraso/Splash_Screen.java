package com.example.libraso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.libraso.Signup_Login.GoogleLogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Splash_Screen extends AppCompatActivity {

    private Animation top, bottom;
    private ImageView logoimage, logoname, logoiiitd;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        top= AnimationUtils.loadAnimation(this,R.anim.anim_top);
        bottom= AnimationUtils.loadAnimation(this,R.anim.anim_botton);

        logoimage=findViewById(R.id.mainlogo);
        logoname=findViewById(R.id.mainname);
        logoiiitd=findViewById(R.id.mainiiitd);
        logoimage.setAnimation(top);
        logoname.setAnimation(bottom);
        logoiiitd.setAnimation(bottom);





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                File f;
                f = new File(getApplicationContext().getDir("file", Context.MODE_PRIVATE).getAbsolutePath()+"/isuserloged.txt");
                System.out.println("start");
                String s=null;
                if(f.exists()!=false){
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(f));
                        s = br.readLine();
                        br.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    intent.putExtra("User_details",s);
                    System.out.println("file exist");

                }
                else{
                intent= new Intent(Splash_Screen.this, GoogleLogin.class); }
                startActivity(intent);
                finish();
            }
        },4000);
    }
}