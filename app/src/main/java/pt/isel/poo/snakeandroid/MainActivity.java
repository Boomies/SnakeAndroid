package pt.isel.poo.snakeandroid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import pt.isel.poo.snakeandroid.model.*;
import pt.isel.poo.snakeandroid.view.ElementView;
import pt.isel.poo.tile.OnBeatListener;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.TilePanel;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, OnTileTouchListener, OnBeatListener, ElementListener {
    private TextView levelTitle;
    private Level level;
    private static final int STEP_TIME = 300;
    Dir dir = Dir.UP;
    Dir aux = dir;
    private TilePanel panel;
    private int COLS, LINES;
    LinearLayout topView;
    float xFrom, yFrom, xTo, yTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topView = (LinearLayout) findViewById(R.id.topView);
        panel = (TilePanel) findViewById(R.id.levelPanel);
        levelTitle = (TextView) findViewById(R.id.levelTitle);


        level = new Level();

        if(savedInstanceState != null) {
            //noinspection ConstantConditions
            ByteArrayInputStream array = new ByteArrayInputStream(savedInstanceState.getByteArray("savestate"));
            DataInputStream data = new DataInputStream(array);

            //noinspection ConstantConditions
            switch(savedInstanceState.getString("direction")){
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
        }else{
            loadLevel("level01.txt");
        }

        COLS = Coordinate.maxColumns;
        LINES = Coordinate.maxLines;

        levelTitle.setText(level.getTitle());
        topView.setOnTouchListener(this);
        panel.setBackgroundColor(Color.BLACK);
        panel.setSize(COLS, LINES);
        panel.setOnTouchListener(this);
        level.setElementListener(this);
        panel.setHeartbeatListener(STEP_TIME, this);

        for (int l = 0; l < LINES; l++) {
            for (int c = 0; c < COLS; c++){
                panel.setTile(c, l, new ElementView(this, level.getElement(l, c)));
            }
        }
    }

    private boolean loadLevel(String fileName) {
        InputStream file = null;
        try {
            file =  getAssets().open(fileName);
            level.load(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            if (file != null)
                try { file.close(); } catch (IOException e) {
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
        if(!level.isOver()){
            level.move(dir);
        }
    }

    @Override
    public void show(Element e, int X, int Y) {
        panel.setTile(Y, X, new ElementView(this, e));
    }

    @Override
    public void showDeadSnake(int x, int y) {
            Toast.makeText(this,"Game Over", Toast.LENGTH_SHORT).show();
    }

    private boolean changeDir(int xFrom, int yFrom, int xTo, int yTo) {
        double xDif = Math.abs(xTo - xFrom), yDif = Math.abs(yTo - yFrom);

        if(xDif >= yDif) aux = (xFrom > xTo ? Dir.LEFT : Dir.RIGHT);
        else aux = (yFrom > yTo ? Dir.UP : Dir.DOWN);

        dir = aux;
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(array);
        level.saveState(data);
        savedInstanceState.putByteArray("savestate", array.toByteArray());

        savedInstanceState.putString("direction", dir.name());
        super.onSaveInstanceState(savedInstanceState);
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
                xTo = event.getX();
                yTo = event.getY();
                return changeDir((int) xFrom, (int) yFrom, (int) xTo, (int) yTo);
        }
        return false;
    }
}
