package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 27-10-2015.
 */
public class Snake extends Obstacle {
    private Dir direcao = Dir.UP;

    public Snake(int x, int y) {
        super(x, y);
    }

    /**
     * Obtém as coordenadas de um determinado destino.
     * @param dir Direção.
     * @return Nova coordenada que corresponde ao destino.
     */
    public Coordinate getDest(Dir dir) {
        return new Coordinate(cur.x + dir.dX, cur.y + dir.dY);
    }

    public void setDirection(Dir dir){
        this.direcao = dir;

    }

    public String getDirection(){
        return direcao.name();
    }
}
