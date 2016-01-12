package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 30-10-2015.
 */
public interface ElementListener {
    void show(Element e, int X, int Y);
    void showDeadSnake(int x, int y);
}
