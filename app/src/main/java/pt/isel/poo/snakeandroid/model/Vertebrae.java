package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 31-10-2015.
 */
public class Vertebrae extends Snake {
    public Vertebrae(int x, int y) {
        super(x, y);
    }

    /**
     * Override do m�todo toString().
     * @return "#".
     */
    @Override
    public String toString() {
        return "#";
    }
}
