package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 31-10-2015.
 */
public class Head extends Snake{
    public Head(int x, int y) {
        super(x, y);
    }

    /**
     * Tenta comer um determinado elemento.
     * @param element Elemento a ser comido.
     * @return False se o elemento for um obst�culo, a snake morre neste caso. True se o objeto for comest�vel.
     */
    public boolean eat(Element element) {
        if (element instanceof Obstacle){ lvl.killSnake(); return false; }
        return true;
    }

    /**
     * Override do m�todo toString().
     * @return "@".
     */
    @Override
    public String toString() {
        return "@";
    }
}
