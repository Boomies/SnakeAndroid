package pt.isel.poo.snakeandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import pt.isel.poo.snakeandroid.model.Apple;
import pt.isel.poo.snakeandroid.model.Element;
import pt.isel.poo.snakeandroid.model.Head;
import pt.isel.poo.snakeandroid.model.Space;
import pt.isel.poo.snakeandroid.model.Vertebrae;
import pt.isel.poo.snakeandroid.model.Wall;
import pt.isel.poo.tile.Tile;

/**
 * Created by user on 04-01-2016.
 */
public class ElementView implements Tile{
    Element element;
    Paint paint;
    public ElementView(Context ctx, Element e){
        this.element = e;
    }
    @Override
    public void draw(Canvas canvas, int side) {
        if (element instanceof Head) { paint.setColor(Color.BLUE); canvas.drawCircle(0, 0, side/2, paint); }
        if (element instanceof Vertebrae) { paint.setColor(Color.BLUE); canvas.drawCircle(0, 0, side/2, paint); }
        if (element instanceof Apple) { paint.setColor(Color.RED); canvas.drawRect(side, side, side, side, paint); }
        if (element instanceof Wall) { paint.setColor(Color.BLUE); canvas.drawRect(side, side, side, side, paint); }
        if (element instanceof Space);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
