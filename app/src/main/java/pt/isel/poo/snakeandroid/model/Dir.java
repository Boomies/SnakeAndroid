package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 27-10-2015.
 */
public enum Dir {
    /**
     * Cada dire��o corresponde a determinadas altera��es �s coordenadas actuais de um objecto.
     */
    DOWN(1,0), LEFT(0,-1), RIGHT(0,1), UP(-1,0);

    public final int dX, dY;

    private Dir(int dX, int dY){
        this.dX = dX;
        this.dY = dY;
    }
}
