package pt.isel.poo.snakeandroid.model;

/**
 * Created by Andre on 21/02/16.
 */
public class Mouse extends Element {
    private Dir direcao = Dir.UP;
    /**
     * Construtor Element. Cria um elemento na linha x e coluna y.
     *
     * @param x Linha x.
     * @param y Coluna y.
     */
    public Mouse(int x, int y) {
        super(x, y);
        points = 30;
    }
    /**
     * Override do método toString().
     * @return "O".
     */
    @Override
    public String toString() {
        return "M";
    }


    public void setDirection(Dir dir){
        this.direcao = dir;

    }

    public Dir getDirection(){
        return direcao;
    }
}
