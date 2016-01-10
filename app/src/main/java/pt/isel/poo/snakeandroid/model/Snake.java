package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gon�alo Veloso on 27-10-2015.
 */
public class Snake extends Obstacle {
    public Snake(int x, int y) {
        super(x, y);
    }

    /**
     * Obt�m as coordenadas de um determinado destino.
     * @param dir Dire��o.
     * @return Nova coordenada que corresponde ao destino.
     */
    public Coordinate getDest(Dir dir) {
        return new Coordinate(cur.x + dir.dX, cur.y + dir.dY);
    }
}
