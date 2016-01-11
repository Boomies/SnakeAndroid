package pt.isel.poo.snakeandroid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import pt.isel.poo.snakeandroid.model.*;
import pt.isel.poo.snakeandroid.view.ElementView;
import pt.isel.poo.tile.OnBeatListener;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.TilePanel;

public class MainActivity extends AppCompatActivity implements OnTileTouchListener, OnBeatListener, ElementListener {
    private TextView levelTitle;
    private Level level;
    private static final int STEP_TIME = 500;
    Dir dir = Dir.UP;
    Dir aux = dir;
    private TilePanel panel;
    private int COLS, LINES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = new Level();
        loadLevel("level01.txt");

        levelTitle = (TextView) findViewById(R.id.levelTitle);
        levelTitle.setText(level.getTitle());

        panel = (TilePanel) findViewById(R.id.levelPanel);
        COLS = Coordinate.maxColumns;
        LINES = Coordinate.maxLines;
        panel.setSize(COLS, LINES);
        panel.setBackgroundColor(Color.BLACK);

        for (int l = 0; l < LINES; l++)
            for (int c = 0; c < COLS; c++)
                panel.setTile(c,l,new ElementView(this,level.getElement(l,c)));

        panel.setListener(this);
        level.setElementListener(this);
        panel.setHeartbeatListener(STEP_TIME, this);

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
        if(xFrom > xTo){
            aux = Dir.LEFT;
            return true;
        }
        else if(xFrom < xTo){
            aux = Dir.RIGHT;
            return true;
        }
        if(yFrom > yTo){
            aux = Dir.UP;
            return true;
        }
        else if(yFrom < yTo){
            aux = Dir.DOWN;
            return true;
        }

        return false;
    }

    @Override
    public void onDragEnd(int x, int y) {
        dir = aux;
    }

    @Override
    public void onDragCancel() {

    }

    @Override
    public void onBeat(long beat, long time) {
        if(!level.isOver()){
            level.move(dir);
        }
        //Talvez mostrar uma mensagem ou passar para o proximo nivel?
        else{

        }
    }

    @Override
    public void show(Element e, int X, int Y) {
        panel.setTile(Y, X, new ElementView(this, e));
    }

    @Override
    public void showDeadSnake(int x, int y) {

    }
}
