package pt.isel.poo.snakeandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final View toAnimate = findViewById(R.id.toAnimate);
        Button play = (Button) findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the screen dimensions for the revealCircle Animation
                DisplayMetrics dm = new DisplayMetrics();
                Intro.this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

                int SCREEN_HEIGHT = dm.heightPixels;
                int SCREEN_WIDTH = dm.widthPixels;

                toAnimate.setVisibility(View.VISIBLE);

                //Make the circular reveal
                SupportAnimator mAnimator = ViewAnimationUtils.createCircularReveal(toAnimate, SCREEN_WIDTH / 2 ,SCREEN_HEIGHT / 2, 0, Math.max(SCREEN_WIDTH, SCREEN_HEIGHT));
                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(500);
                mAnimator.start();

                //Start our mainActivity
                Intent it = new Intent(Intro.this, MainActivity.class);
                startActivity(it);
                finish();

            }
        });


    }
}
