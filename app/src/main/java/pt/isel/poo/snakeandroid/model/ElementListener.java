package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 30-10-2015.
 */
public interface ElementListener {
    void show(Element e, int X, int Y);
    void repaint(Level level);

    void showDeadSnake(int x, int y);
}
