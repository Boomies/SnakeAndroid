package pt.isel.poo.snakeandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ScoreBoard extends AppCompatActivity {
    TextView firstScore, secondScore, thirdScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        final View scoreLayout = findViewById(R.id.scoreLayout);
        firstScore = (TextView) findViewById(R.id.score1);
        secondScore = (TextView) findViewById(R.id.score2);
        thirdScore = (TextView) findViewById(R.id.score3);
        loadScores();
        Button mainMenu = (Button)findViewById(R.id.main);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Start our mainActivity
                Intent it = new Intent(ScoreBoard.this, Intro.class);
                startActivity(it);
                finish();
            }
        });
    }
    public void loadScores(){
        Scanner io = null;
        try {
            io = new Scanner(openFileInput("scores.txt"));
            firstScore.setText("1st - " + io.nextInt());
            secondScore.setText("2nd - " + io.nextInt());
            thirdScore.setText("3rd - " + io.nextInt());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (io != null) io.close();
        }

    }
    }

