package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 31-10-2015.
 */
public class Vertebrae extends Snake {
    public Vertebrae(int x, int y) {
        super(x, y);
    }

    /**
     * Override do método toString().
     * @return "#".
     */
    @Override
    public String toString() {
        return "#";
    }
}
