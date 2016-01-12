package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 03-11-2015.
 */

/**
 * Space é um Elemento que representa um espaço vazio na array bidimensional do nível.
 */
public class Space extends Element {
    public Space(int x, int y) {
        super(x, y);
    }

    /**
     * Override do método toString().
     * @return " "
     */
    @Override
    public String toString() {
        return "_";
    }
}
