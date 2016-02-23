package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 27-10-2015.
 */
public class Apple extends Element {
    public Apple(int x, int y) {
        super(x, y);
        points = 10;

    }
    /**
     * Override do método toString().
     * @return "O".
     */


    @Override
    public String toString() {
        return "O";
    }
}
