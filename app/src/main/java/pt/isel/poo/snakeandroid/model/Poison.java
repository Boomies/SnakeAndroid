package pt.isel.poo.snakeandroid.model;

/**
 * Created by Andre on 17/02/16.
 */
public class Poison extends Obstacle {
    public Poison(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "P";
    }
}
