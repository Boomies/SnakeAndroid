package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 31-10-2015.
 */
public class Head extends Snake{
    public Head(int x, int y) {
        super(x, y);
    }

    /**
     * Tenta comer um determinado elemento.
     * @param element Elemento a ser comido.
     * @return False se o elemento for um obstáculo, a snake morre neste caso. True se o objeto for comestível.
     */
    public boolean eat(Element element) {
        if (element instanceof Obstacle){ lvl.killSnake(); return false; }
        return true;
    }

    /**
     * Override do método toString().
     * @return "@".
     */
    @Override
    public String toString() {
        return "@";
    }
}
