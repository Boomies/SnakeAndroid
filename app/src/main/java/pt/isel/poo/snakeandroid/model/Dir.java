package pt.isel.poo.snakeandroid.model;

import java.util.LinkedList;

/**
 * Created by Gonçalo Veloso e André Carvalho on 27-10-2015.
 *
 */
public enum Dir {
    /**
     * Cada direção corresponde a determinadas alterações às coordenadas actuais de um objecto.
     */
    DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1), UP(-1, 0),
    //Placeholder, para ser mais facil carregar a imagem correta para o body
    DL(0, 0), DR(0, 0), UL(0, 0), UR(0, 0);


    public final int dX, dY;

    private Dir(int dX, int dY) {
        this.dX = dX;
        this.dY = dY;
    }

    /**
     * Devolve a coordenada oposta a passada em parametro
     */
    public static boolean getOppositeDir(Dir atual, Dir nova) {
        if(atual.name().equals("RIGHT") && nova.name().equals("LEFT")) return true;
        if(atual.name().equals("LEFT") && nova.name().equals("RIGHT")) return true;
        if(atual.name().equals("UP") && nova.name().equals("DOWN")) return true;
        if(atual.name().equals("DOWN") && nova.name().equals("UP")) return true;

        return false;

    }

    public static Dir correctBodyDir(Dir atual, Dir before) {
        Dir cur = null;
        String a = atual.name();
        String b = before.name();

        if (a.equals(b)) {
            return atual;

        } else {

            if (a.equals("UP")) {
                if (b.equals("LEFT")) {
                    cur = Dir.UR;

                } else if (b.equals("RIGHT")) {
                    cur = Dir.UL;

                }
            } else if (a.equals("DOWN")) {
                if (b.equals("RIGHT")) {
                    cur = Dir.DL;

                } else if (b.equals("LEFT")) {
                    cur = Dir.DR;

                }
            } else if (a.equals("LEFT")) {

                if (b.equals("DOWN")) {
                    cur = Dir.UL;

                } else if (b.equals("UP")) {
                    cur = Dir.DL;

                }
            } else if (a.equals("RIGHT")) {
                if (b.equals("DOWN")) {
                    cur = Dir.UR;

                } else if (b.equals("UP")) {
                    cur = Dir.DR;

                }
            }
        }

        return cur;
    }


    public static Dir correctTailDir(LinkedList<Snake> e) {
        int numElem = e.size();
        Dir cur;

        if (e.get(numElem - 2).getDirection().name().equals("DL")
                || e.get(numElem - 2).getDirection().name().equals("DR")
                || e.get(numElem - 2).getDirection().name().equals("UL")
                || e.get(numElem - 2).getDirection().name().equals("UR")) {


            if(e.getLast().cur.x != e.get(numElem - 2).cur.x){
                if (e.getLast().cur.x < e.get(numElem - 2).cur.x) {
                    cur = Dir.DOWN;
                } else {
                    cur = Dir.UP;
                }
            }else if(e.getLast().cur.y != e.get(numElem - 2).cur.y){
                if (e.getLast().cur.y < e.get(numElem - 2).cur.y) {
                    cur = Dir.RIGHT;
                } else {
                    cur = Dir.LEFT;
                }
            }else{
               cur = e.getLast().getDirection();
            }
        } else {

            if (e.get(numElem - 2).cur.x != e.get(numElem - 3).cur.x) {
                if (e.get(numElem - 2).cur.x < e.get(numElem - 3).cur.x) {
                    cur = Dir.DOWN;
                } else {
                    cur = Dir.UP;
                }

            } else if(e.get(numElem - 2).cur.y != e.get(numElem - 3).cur.y){
                if (e.get(numElem - 2).cur.y < e.get(numElem - 3).cur.y) {
                    cur = Dir.RIGHT;
                } else {
                    cur = Dir.LEFT;
                }
            }
            else{
                cur = e.get(numElem - 2).getDirection();
            }
        }

        return cur;
    }

    public static Dir createDir(String dir) {
        switch (dir) {
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
