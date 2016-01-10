package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 03-11-2015.
 */

/**
 * Space � um Elemento que representa um espa�o vazio na array bidimensional do n�vel.
 */
public class Space extends Element {
    public Space(int x, int y) {
        super(x, y);
    }

    /**
     * Override do m�todo toString().
     * @return " "
     */
    @Override
    public String toString() {
        return " ";
    }
}
