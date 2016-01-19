package pt.isel.poo.snakeandroid.model;

/**
 * Created by André Carvalho on 16/01/16.
 */
public class Tail extends Snake {
    private Dir direcao = Dir.UP;

    public Tail(int x, int y) {
        super(x, y);
    }

    /**
     * Override do método toString().
     * @return "#".
     */
    @Override
    public String toString() {
        return "T";
    }

    public void setDirection(Dir dir){
        this.direcao = dir;

    }

    public Dir getDirection(){
        return direcao;
    }
}