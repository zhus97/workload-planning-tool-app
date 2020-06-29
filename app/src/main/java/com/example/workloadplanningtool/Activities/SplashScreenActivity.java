package com.example.workloadplanningtool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {



    //Array of quotes
    String[] quotes = {"'Failure is the opportunity to begin again more intelligently. – Henry Ford'",
            "'Our greatest weakness lies in giving up. The most certain way to succeed is always to try just one more time. – Thomas A. Edison'",
    "'You’ve got to get up every morning with determination if you’re going to go to bed with satisfaction. – George Lorimer'"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Select random quote from the above array
        String selectedQuote = quotes[new Random().nextInt(quotes.length)];


        //Display the splash screen for 7 seconds
        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(7000)
                .withBackgroundColor(Color.parseColor("#e3e1da"))
                .withBeforeLogoText(selectedQuote);

        config.getBeforeLogoTextView().setTextColor(Color.BLACK);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }



}



