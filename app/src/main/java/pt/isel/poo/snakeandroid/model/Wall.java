package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 27-10-2015.
 */
public class Wall extends Obstacle {
    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "X";
    }
}
