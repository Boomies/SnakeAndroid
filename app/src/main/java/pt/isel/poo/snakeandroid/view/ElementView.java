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
    private Element element;
    private static Paint paint;
    public ElementView(Context ctx, Element e){
        this.element = e;

        if(paint == null){
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(3);
        }

    }
    @Override
    public void draw(Canvas canvas, int side) {
        if (element instanceof Head) { paint.setColor(Color.BLUE); canvas.drawCircle(side/2, side/2, side/2, paint); }
        if (element instanceof Vertebrae) { paint.setColor(Color.RED); canvas.drawCircle(side/2, side/2, side/2, paint); }
        if (element instanceof Apple) { paint.setColor(Color.RED); canvas.drawRect(0, 0, side, side, paint); }
        if (element instanceof Wall) { paint.setColor(Color.BLUE); canvas.drawRect(0, 0, side, side, paint); }
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
