package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 30-10-2015.
 */
public class Coordinate {
    public static int maxLines, maxColumns; // Dimens�es m�ximas das linhas e colunas.
    public int x, y;                        // Posi��es x e y.

    /**
     * Construtor Coordinate. Cria uma coordenada correspondente � x linha e y coluna, e corrige os seus valores caso
     * n�o estejam dentro dos limites.
     * @param x Linha.
     * @param y Coluna.
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
        correct();
    }

    /**
     * Corrige as coordenadas caso estas estejam fora dos limites (maxLines e maxColumns).
     */
    public void correct(){
        x = (x + maxColumns) % maxColumns;
        y = (y + maxLines) % maxLines;
    }

    /**
     * Override do m�todo toString().
     * @return "(x,y)"
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
