package pt.isel.poo.snakeandroid.model;

import android.widget.Switch;

/**
 * Created by Gonçalo Veloso e André Carvalho on 27-10-2015.
 */
public enum Dir {
    /**
     * Cada direção corresponde a determinadas alterações às coordenadas actuais de um objecto.
     */
    DOWN(1,0), LEFT(0,-1), RIGHT(0,1), UP(-1,0),
    //Placeholder, para ser mais facil carregar a imagem correta para o body
    DL(0,0), DR(0,0), UL(0,0), UR(0,0);


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

    public static Dir correctDir(Dir d){
        if (d == Dir.DL) return Dir.LEFT;
        if (d == Dir.DR) return Dir.DOWN;
        if (d == Dir.UR) return Dir.RIGHT;
        if (d == Dir.UL) return Dir.UP;
        
        return d;
    }

    public static Dir createDir(String dir){
        switch (dir){
            case "UP":
                return Dir.UP;
            case "DOWN":
                return Dir.DOWN;
            case "LEFT":
                return Dir.LEFT;
            case "RIGHT":
                return Dir.RIGHT;
            case "DL":
                return Dir.DL;
            case "DR":
                return Dir.DR;
            case "UR":
                return Dir.UR;
            case "UL":
                return Dir.UL;
        }

        return null;
    }
}
