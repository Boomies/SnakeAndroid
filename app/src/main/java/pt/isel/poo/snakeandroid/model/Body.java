package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 31-10-2015.
 */
public class Body extends Snake {
    private Dir direcao = Dir.UP;

    public Body(int x, int y) {
        super(x, y);
    }

    /**
     * Override do método toString().
     * @return "#".
     */
    @Override
    public String toString() {
        return "#";
    }

    public void setDirection(Dir dir){
        this.direcao = dir;

    }

    public Dir getDirection(){
        return direcao;
    }
}
