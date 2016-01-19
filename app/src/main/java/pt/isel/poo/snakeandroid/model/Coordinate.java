package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 30-10-2015.
 */
public class Coordinate {
    public static int maxLines, maxColumns; // Dimensões máximas das linhas e colunas.
    public int x, y;                        // Posições x e y.

    /**
     * Construtor Coordinate. Cria uma coordenada correspondente, x linha e y coluna, e corrige os seus valores caso
     * não estejam dentro dos limites.
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
        x = (x +maxLines)  % maxLines;
        y = (y+maxColumns) % maxColumns;
    }

    /**
     * Override do método toString().
     * @return "(x,y)"
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
