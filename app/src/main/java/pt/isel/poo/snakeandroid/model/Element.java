package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 27-10-2015.
 */
public class Element {
    Coordinate cur; // Coordenada actual do elemento.
    public static Level lvl; // Nível ao qual este elemento est� ligado.

    /**
     * Construtor Element. Cria um elemento na linha x e coluna y.
     * @param x Linha x.
     * @param y Coluna y.
     */
    public Element(int x, int y) {
        cur = new Coordinate(x, y); // Cria uma nova coordenada.
        setCur(cur.x, cur.y); // Atributi a este objecto essa coordenada.
    }

    /**
     * Altera a coordenada actual de um objecto.
     * @param x Nova linha.
     * @param y Nova Coluna.
     */
    public void setCur(int x, int y) {
        cur.x = x;
        cur.y = y;
    }
}
