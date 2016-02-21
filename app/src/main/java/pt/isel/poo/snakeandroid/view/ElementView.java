package pt.isel.poo.snakeandroid.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import pt.isel.poo.snakeandroid.R;
import pt.isel.poo.snakeandroid.model.Apple;
import pt.isel.poo.snakeandroid.model.Body;
import pt.isel.poo.snakeandroid.model.Element;
import pt.isel.poo.snakeandroid.model.Head;
import pt.isel.poo.snakeandroid.model.Mouse;
import pt.isel.poo.snakeandroid.model.Poison;
import pt.isel.poo.snakeandroid.model.Tail;
import pt.isel.poo.snakeandroid.model.Wall;
import pt.isel.poo.tile.Tile;

/**
 * Created by Gonçalo Veloso e André Carvalho on 04-01-2016.
 */
public class ElementView implements Tile{
    private Element element;
    private static Paint paint;

    private final static int[] Imgs_ids = {
            R.drawable.apple, R.drawable.body_dl, R.drawable.body_dr, R.drawable.body_lr,
            R.drawable.body_ud, R.drawable.body_ul, R.drawable.body_ur, R.drawable.head_d,
            R.drawable.head_l, R.drawable.head_r, R.drawable.head_u, R.drawable.tail_d,
            R.drawable.tail_l, R.drawable.tail_r, R.drawable.tail_u, R.drawable.wall, R.drawable.poison,
            R.drawable.mouse_u, R.drawable.mouse_d, R.drawable.mouse_l, R.drawable.mouse_r
    };

    public static final int MAX_IMAGES = Imgs_ids.length;
    private static Bitmap[] Snake_imgs;

    public ElementView(Context ctx, Element e){
        this.element = e;

        if(Snake_imgs == null){
            Snake_imgs = new Bitmap[MAX_IMAGES];
            paint = new Paint();
            paint.setColor(Color.BLUE);
            Resources res = ctx.getResources();
            for (int i = 0; i < MAX_IMAGES; i++)
                Snake_imgs[i] = BitmapFactory.decodeResource(res,Imgs_ids[i]);
        }
    }

    @Override
    public void draw(Canvas canvas, int side) {
        if (element instanceof Head) {
            switch (((Head) element).getDirection().name()){
                case "UP":
                    drawBitmap(canvas,Snake_imgs[10]);
                    break;
                case "DOWN":
                    drawBitmap(canvas,Snake_imgs[7]);
                    break;
                case "LEFT":
                    drawBitmap(canvas,Snake_imgs[8]);
                    break;
                case "RIGHT":
                    drawBitmap(canvas,Snake_imgs[9]);
                    break;
            }

        }
        if (element instanceof Body) {
            switch (((Body) element).getDirection().name()){
                case "UP":
                    //O mesmo que o Down por isso deixamos isto passar para baixo
                case "DOWN":
                    drawBitmap(canvas,Snake_imgs[4]);
                    break;
                case "LEFT":
                    //O mesmo que UP
                case "RIGHT":
                    drawBitmap(canvas,Snake_imgs[3]);
                    break;
                case "DL":
                    drawBitmap(canvas,Snake_imgs[1]);
                    break;
                case "DR":
                    drawBitmap(canvas,Snake_imgs[2]);
                    break;
                case "UL":
                    drawBitmap(canvas,Snake_imgs[5]);
                    break;
                case "UR":
                    drawBitmap(canvas,Snake_imgs[6]);
                    break;

            }
        }
        if (element instanceof Tail) {
            switch (((Tail) element).getDirection().name()){
                case "UP":
                    drawBitmap(canvas,Snake_imgs[14]);
                    break;
                case "DOWN":
                    drawBitmap(canvas,Snake_imgs[11]);
                    break;
                case "LEFT":
                    drawBitmap(canvas,Snake_imgs[12]);
                    break;
                case "RIGHT":
                    drawBitmap(canvas,Snake_imgs[13]);
                    break;
            }
        }

        if (element instanceof Apple) {
            drawBitmap(canvas,Snake_imgs[0]);
        }

        if (element instanceof Wall) {
            drawBitmap(canvas,Snake_imgs[15]);
        }
        if (element instanceof Poison){
            drawBitmap(canvas,Snake_imgs[16]);
        }

        if (element instanceof Mouse) {
            switch (((Mouse) element).getDirection().name()){
                case "UP":
                    drawBitmap(canvas,Snake_imgs[17]);
                    break;
                case "DOWN":
                    drawBitmap(canvas,Snake_imgs[18]);
                    break;
                case "LEFT":
                    drawBitmap(canvas,Snake_imgs[19]);
                    break;
                case "RIGHT":
                    drawBitmap(canvas,Snake_imgs[20]);
                    break;
            }

        }


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
