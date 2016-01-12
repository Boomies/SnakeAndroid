package pt.isel.poo.snakeandroid.model;

/**
 * Created by Gonçalo Veloso e André Carvalho on 27-10-2015.
 */
public enum Dir {
    /**
     * Cada direção corresponde a determinadas alterações às coordenadas actuais de um objecto.
     */
    DOWN(1,0), LEFT(0,-1), RIGHT(0,1), UP(-1,0);

    public final int dX, dY;

    private Dir(int dX, int dY){
        this.dX = dX;
        this.dY = dY;
    }

    public static Dir getOppositeDir(Dir d){
        if (d == Dir.DOWN) return Dir.UP;
        if (d == Dir.UP) return Dir.DOWN;
        if (d == Dir.LEFT) return Dir.RIGHT;
        if (d == Dir.RIGHT) return Dir.LEFT;
        return null;
    }
}
