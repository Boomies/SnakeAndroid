package pt.isel.poo.snakeandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import pt.isel.poo.snakeandroid.model.*;
import pt.isel.poo.snakeandroid.view.ElementView;
import pt.isel.poo.tile.OnBeatListener;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.TilePanel;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, OnTileTouchListener, OnBeatListener, ElementListener {
    private TextView levelTitle, currentScore;
    private Level level;
    private static final int STEP_TIME = 500;
    Dir dir = Dir.UP;
    private TilePanel panel;
    LinearLayout topView;
    private float xFrom, yFrom;
    private int[] scores = new int[3];
    private int cur_level = 1, maxLevel = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topView = (LinearLayout) findViewById(R.id.topView);
        panel = (TilePanel) findViewById(R.id.levelPanel);
        levelTitle = (TextView) findViewById(R.id.levelTitle);
        currentScore = (TextView) findViewById(R.id.currentScore);
        level = new Level();
        /*
        try {
            for (String s : getAssets().list("")){
                maxLevel++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        */

        if (savedInstanceState != null) {
            //noinspection ConstantConditions
            ByteArrayInputStream array = new ByteArrayInputStream(savedInstanceState.getByteArray("savestate"));
            DataInputStream data = new DataInputStream(array);

            cur_level = savedInstanceState.getInt("level");
            //noinspection ConstantConditions
            switch (savedInstanceState.getString("direction")) {
                case "LEFT":
                    dir = Dir.LEFT;
                    break;
                case "RIGHT":
                    dir = Dir.RIGHT;
                    break;
                case "UP":
                    dir = Dir.UP;
                    break;
                case "DOWN":
                    dir = Dir.DOWN;
                    break;
            }
            level.loadState(data);
            level.printMembers();
        } else {
            loadLevel("level" + cur_level + ".txt");
        }

        setUI();
    }

    private void setUI() {
        int COLS = Coordinate.maxColumns;
        int LINES = Coordinate.maxLines;
        levelTitle.setText(level.getTitle());
        currentScore.setText("Score: " + level.scoreCheck());
        panel.setSize(COLS, LINES);
        topView.setOnTouchListener(this);
        panel.setBackgroundColor(Color.BLACK);
        panel.setOnTouchListener(this);
        level.setElementListener(this);
        panel.setHeartbeatListener(STEP_TIME, this);
        for (int l = 0; l < LINES; l++) {
            for (int c = 0; c < COLS; c++) {
                panel.setTile(c, l, new ElementView(this, level.getElement(l, c)));
            }
        }

    }

    private boolean loadLevel(String fileName) {
        InputStream file = null;
        try {
            file = getAssets().open(fileName);
            level.load(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (file != null)
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }

    @Override
    public boolean onClick(int xTile, int yTile) {
        return false;
    }

    @Override
    public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
        return true;
    }

    @Override
    public void onDragEnd(int x, int y) {
    }

    @Override
    public void onDragCancel() {

    }

    @Override
    public void onBeat(long beat, long time) {
        currentScore.setText("Score: " + level.scoreCheck());
        if (!level.isOver()) {
            level.move(dir);
        } else {
            panel.removeHeartbeatListener();

            if (cur_level == maxLevel) {
                updateScores();
                //Conseguimos passar o jogo
                if (!level.isSnakeDead()) {
                    Toast.makeText(MainActivity.this, "Congrats, you finished the game!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                //Conseguimos passar o nivel
                if (!level.isSnakeDead()) {

                    if(maxLevel == cur_level){
                        updateScores();
                        Toast.makeText(this, "You completed this game", Toast.LENGTH_SHORT).show();
                        finish();//Closes windows
                    }else {
                        updateScores();
                        new AlertDialog.Builder(this)
                                .setTitle("Level complete! Go to next level?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                level = new Level();
                                                ++cur_level;
                                                dir = Dir.UP;

                                                loadLevel("level" + cur_level + ".txt");
                                                setUI();
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent it = new Intent(MainActivity.this, Intro.class);
                                        startActivity(it);
                                        MainActivity.this.finish();
                                    }
                                })
                                .setCancelable(false)
                                .create().show();
                    }
                } else {
                    updateScores();
                    new AlertDialog.Builder(this)
                            .setTitle("Game Over")
                            .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    level = new Level();
                                    dir = Dir.UP;
                                    loadLevel("level" + cur_level + ".txt");
                                    setUI();
                                }
                            })
                            .setNegativeButton("Exit to Menu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent it = new Intent(MainActivity.this, Intro.class);
                                    startActivity(it);
                                    MainActivity.this.finish();
                                }
                            })
                            .setCancelable(false)
                            .create().show();
                }
                    /*Runnable r = new Runnable() {
                        @Override
                    public void run(){
                            finish();
                        }
                    };
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                    Handler h = new Handler();
                    h.postDelayed(r, 2000);
                }*/
            }
        }
    }

    @Override
    public void show(Element e, int X, int Y) {
        panel.setTile(Y, X, new ElementView(this, e));
    }

    @Override
    public void showDeadSnake(int x, int y) {
    }

    private boolean changeDir(int xFrom, int yFrom, int xTo, int yTo) {
        double xDif = Math.abs(xTo - xFrom), yDif = Math.abs(yTo - yFrom);

        if (xDif >= yDif) dir = (xFrom > xTo ? Dir.LEFT : Dir.RIGHT);
        else dir = (yFrom > yTo ? Dir.UP : Dir.DOWN);

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        panel.removeHeartbeatListener();
        super.onSaveInstanceState(savedInstanceState);
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(array);
        level.saveState(data);
        savedInstanceState.putByteArray("savestate", array.toByteArray());

        savedInstanceState.putString("direction", dir.name());

        savedInstanceState.putInt("level", cur_level);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                xFrom = event.getX();
                yFrom = event.getY();

            case MotionEvent.ACTION_MOVE:
                return true;

            case MotionEvent.ACTION_UP:
                float xTo = event.getX();
                float yTo = event.getY();
                return changeDir((int) xFrom, (int) yFrom, (int) xTo, (int) yTo);
        }
        return false;
    }

    public void updateScores(){
        System.out.println("SCORE = " + level.scoreCheck());
        if(loadScores()){
            int thisScore = level.scoreCheck();
            if (thisScore > scores[0]){
                scores[2] = scores[1];
                scores[1] = scores[0];
                scores[0] = thisScore;
            }
            else if (thisScore > scores[1]){
                scores[2] = scores[1];
                scores[1] = thisScore;
            }
            else if (thisScore > scores[2]){
                scores[2] = thisScore;
            }
            saveScores();
        }
        else Toast.makeText(this, "Error updating Scoreboard", Toast.LENGTH_SHORT);
    }

    public boolean loadScores(){
        Scanner io = null;
        try {
            io = new Scanner(openFileInput("scores.txt"));
            for (int i = 0; i < scores.length; i++) {
                scores[i] = io.nextInt();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (io != null) io.close();
        }
    }

    public void saveScores(){
        BufferedWriter file = null;
        try {
            OutputStream out = openFileOutput("scores.txt", MODE_PRIVATE);
            file = new BufferedWriter(new OutputStreamWriter(out));
            for (int i = 0; i < scores.length; i++) {
                file.write(scores[i] + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FAILURE");
        } finally {
            try {
                if (file != null) file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }
