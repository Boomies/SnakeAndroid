package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 27-10-2015.
 */
public class Apple extends Element {
    public Apple(int x, int y) {
        super(x, y);
    }

    /**
     * Override do m�todo toString().
     * @return "O".
     */
    @Override
    public String toString() {
        return "O";
    }
}
