package pt.isel.poo.snakeandroid.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import pt.isel.poo.snakeandroid.R;
import pt.isel.poo.snakeandroid.model.Apple;
import pt.isel.poo.snakeandroid.model.Body;
import pt.isel.poo.snakeandroid.model.Element;
import pt.isel.poo.snakeandroid.model.Head;
import pt.isel.poo.snakeandroid.model.Space;
import pt.isel.poo.snakeandroid.model.Tail;
import pt.isel.poo.snakeandroid.model.Wall;
import pt.isel.poo.tile.Tile;

/**
 * Created by Gonçalo Veloso e André Carvalho on 04-01-2016.
 */
public class ElementView implements Tile{
    private Element element;
    private Context context;
    private static Paint paint;

    public ElementView(Context ctx, Element e){
        this.element = e;
        this.context = ctx;

        if(paint == null){
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(3);
        }
    }

    @Override
    public void draw(Canvas canvas, int side) {
        if (element instanceof Head) {
            switch (((Head) element).getDirection()){
                case "UP":
                    drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.head_u));
                    break;
                case "DOWN":
                    drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.head_d));
                    break;
                case "LEFT":
                    drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.head_l));
                    break;
                case "RIGHT":
                    drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.head_r));
                    break;
                default:
                    drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.head_u));
                    break;
            }

        }
        if (element instanceof Body) {

            paint.setColor(Color.GREEN); canvas.drawCircle(side/2, side/2, side/2, paint);

        }
        if (element instanceof Tail) {

            paint.setColor(Color.RED); canvas.drawCircle(side/2, side/2, side/2, paint);

        }
        if (element instanceof Apple) { drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.apple)); }
        if (element instanceof Wall) { drawBitmap(canvas, BitmapFactory.decodeResource(context.getResources(), R.drawable.wall));}
    }

    private void drawBitmap(Canvas canvas, Bitmap img) {
        Rect r = new Rect(0,0,img.getWidth(),img.getHeight());
        canvas.drawBitmap(img, r, canvas.getClipBounds(), paint);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
