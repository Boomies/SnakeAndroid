package pt.isel.poo.snakeandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import pt.isel.poo.snakeandroid.model.Coordinate;
import pt.isel.poo.snakeandroid.model.Dir;
import pt.isel.poo.snakeandroid.model.Element;
import pt.isel.poo.snakeandroid.model.ElementListener;
import pt.isel.poo.snakeandroid.model.Level;
import pt.isel.poo.snakeandroid.view.ElementView;
import pt.isel.poo.tile.OnBeatListener;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.TilePanel;

public class MainActivity extends AppCompatActivity implements OnTileTouchListener, OnBeatListener, ElementListener {
    private TextView levelTitle;
    private Level level;
    private static final int STEP_TIME = 500;
    private static final boolean TIMED = true;
    Dir dir;
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
            /*for (int l = 0; l < LINES; l++)
                for (int c = 0; c < COLS; c++)
                    panel.setTile(c,l,new ElementView(this,level.getElement(l,c)));*/
            panel.setListener(this);
            level.setElementListener(this);
            panel.setHeartbeatListener(250, this);
            dir = Dir.UP;
            panel.invalidate();
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
        return false;
    }

    @Override
    public void onDragEnd(int x, int y) {

    }

    @Override
    public void onDragCancel() {

    }

    @Override
    public void onBeat(long beat, long time) {

    }

    @Override
    public void show(Element e, int X, int Y) {
        panel.setTile(X, Y, new ElementView(this, e));
    }

    @Override
    public void repaint(Level level) {

    }

    @Override
    public void showDeadSnake(int x, int y) {

    }
}
