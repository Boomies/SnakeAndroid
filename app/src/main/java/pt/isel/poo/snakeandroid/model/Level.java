package pt.isel.poo.snakeandroid.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Gon�alo Veloso on 27-10-2015.
 */
public class Level {
    private String title;   //T�tulo do n�vel.
    private ElementListener elementListener;    //Interface a ser implementada.
    private boolean snakeDead, over;    //Booleans que determinam se a cobra est� morta ou se o n�vel est� acabado.
    public Element[][] board;   //Array bidimensional. Corresponde ao n�vel do jogo.
    private Head snake; //Inicializa��o de um objecto de cabe�a da cobra.
    private int increment;    //N�mero de v�rtebras iniciais e a serem adicionadas quando a cobra come uma ma��.
    private int maxApples, currentApples; //N�mero de ma��s m�ximas num n�vel e n�mero de ma��s existentes no n�vel.
    LinkedList<Snake> members = new LinkedList<Snake>(); //Lista que ir� conter a cobra.

    public Level() {
        Element.lvl = this;
        snakeDead = false;
        over = false;
    }

    /**
     * Getter de title.
     *
     * @return O título do nível.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter de elementListener.
     *
     * @param elementListener elementListener a ser definido.
     */
    public void setElementListener(ElementListener elementListener) {
        this.elementListener = elementListener;
    }

    /**
     * Mexe a cobra.
     *
     * @param dir Direção para onde a cobra se irá mexer.
     */
    public void move(Dir dir) {
        Coordinate dest = snake.getDest(dir); // Coordenada correspondente ao destino, de acordo com a direção.
        Coordinate current = snake.cur; // Coordenada onde a cabeça está antes de se movimentar.
        if (board[dest.x][dest.y] instanceof Space) { // Se o elemento no destino for um espaço vazio...
            moveTo(current.x, current.y, dest); // ...a cobra move-se para o destino sem problemas.
        } else if (snake.eat(board[dest.x][dest.y])) // Se for possível a cobra comer o elemento na coordenada destino...
        {
            snakeGrow();    // ...então come e cresce.
            appleGenerator();
            moveTo(current.x, current.y, dest);
        }
        if (currentApples == 0) { // Se não existirem mais maçãs no nível...
            if (maxApples > 0) appleGenerator(); /* ...e se ainda existirem maçãs para comer antes de o nível acabar,
                                                   é gerada uma nova maçã. */
            else {
                endGame();
            } // Caso contrório, o jogo acaba.
        }
    }

    /**
     * Gera uma nova maçã num sítio aleatório do nível.
     */
    private void appleGenerator() {
        Random rnd = new Random(); // Novo objecto random.
        int rndX = rnd.nextInt(Coordinate.maxColumns); // Linha aleatória.
        int rndY = rnd.nextInt(Coordinate.maxLines); // Coluna aleatória.
        while (!(board[rndX][rndY] instanceof Space)) { // Enquanto os valores gerados não corresponderem a um espaço...
            rndX = rnd.nextInt(Coordinate.maxColumns); // ...são gerados valores novos.
            rndY = rnd.nextInt(Coordinate.maxLines);
        }
        board[rndX][rndY] = new Apple(rndX, rndY); // A nova maçã é colocada no local definido por rndX e rndY.
        elementListener.show(board[rndX][rndY], rndX, rndY); // é necessário que o view mostre essa nova maçã.
        currentApples++; // O número de maçãs existente no nível é incrementado.
    }

    /**
     * Cresce a cobra.
     */
    public void snakeGrow() {
        maxApples--; //Decrementa o número máximo de maçãs.
        currentApples--; //Decrementa o número de maçãs existente no nivel.
        for (int i = increment; i > 0; i--) { // Cria novas vértebras de acordo com o valor em increment.
            members.addLast(new Vertebrae(members.getLast().cur.x, members.getLast().cur.y));
        }
    }

    /**
     * Verifica se o jogo acabou.
     *
     * @return O estado do jogo.
     */
    public boolean isOver() {
        return over;
    }

    /**
     * Verifica se a cobra está morta.
     *
     * @return O estado da cobra.
     */
    public boolean isSnakeDead() {
        return snakeDead;
    }

    /**
     * Mata a cobra.
     */
    public void killSnake() {
        snakeDead = true;
        elementListener.showDeadSnake(snake.cur.x, snake.cur.y);
        endGame();
    }

    /**
     * Acaba o jogo.
     */
    public void endGame() {
        over = true;
    }

    /**
     * Limpa o nível, preenchedo-o com Spaces.
     *
     * @param board Nível a ser limpo.
     */
    private void clearBoard(Element[][] board) {
        for (int i = 0; i < Coordinate.maxLines; i++) {
            for (int j = 0; j < Coordinate.maxColumns; j++) {
                board[i][j] = new Space(i, j);
            }
        }
    }

    /**
     * Lé um nivel através de um ficheiro input.
     *
     * @param file Ficheiro a ser lido.
     * @throws IOException Excepção que pode surgir.
     */
    public void load(InputStream file) throws IOException {
        Scanner io = new Scanner(file);
        int count = 1;

        while(io.hasNextLine()) {
            if (count == 1) {
                title = io.nextLine();
            } else if (count == 2) {
                Coordinate.maxLines = Integer.parseInt(io.next());
                io.next(); //Passa o 'X'
                Coordinate.maxColumns = Integer.parseInt(io.next());

                //Setup do tabuleiro
                board = new Element[Coordinate.maxLines][Coordinate.maxColumns];
                clearBoard(board);
            } else if (count == 3) {
                increment = Integer.parseInt(io.next());
                maxApples = Integer.parseInt(io.next());
                io.nextLine();
            } else {
                char[] array;
                for (int i = 0; i < Coordinate.maxLines; i++) {
                    String line = io.nextLine();
                    array = line.toCharArray();
                    for (int j = 0; j < array.length; j++) {
                        switch (array[j]) {
                            case 'X':
                                board[i][j] = new Wall(i, j);
                                break;
                            case '@':
                                board[i][j] = snake = new Head(i, j);
                                members.add(snake);
                                break;
                            case 'A':
                                board[i][j] = new Apple(i, j);
                                currentApples++;
                                break;
                            case ' ':
                                board[i][j] = new Space(i, j);
                                break;
                        }
                    }
                }
            }
            count++;
        }
        for (int i = increment; i > 0; i--) { // Cria novas vértebras de acordo com o valor em increment.
            members.addLast(new Vertebrae(members.getLast().cur.x, members.getLast().cur.y));
        }
    }

    public void printBoard(){
        for (int i = 0; i < Coordinate.maxLines; i++) {
            for (int j = 0; j < Coordinate.maxColumns; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
    /**
     * Move a cobra para o destino.
     *
     * @param posX Linha onde a cobra está actualmente.
     * @param posY Coluna onde a cobra está actualmente.
     * @param dest Coordenada destino.
     */
    public void moveTo(int posX, int posY, Coordinate dest) {
        Coordinate lastCur = members.getLast().cur; // Guarda a posição actual do último elemento da cobra.
        board[dest.x][dest.y] = board[posX][posY]; // Coloca a cabeça na coordenada destino.
        board[lastCur.x][lastCur.y] = new Space(lastCur.x, lastCur.y); // Na posição antiga da cauda está agora um objecto Space.
        board[posX][posY] = new Vertebrae(posX, posY); // Cria uma nova vertebra na coordenada antiga da cabeça.
        members.add(1, (Snake) board[posX][posY]); // Coloca o último elemento da lista na coordenada antiga da cabeça.
        members.removeLast(); // Remove o último elemento da cobra.
        // Actualiza as posições de cada vértebra a partir da segunda.
        for (int i = 2; i < members.size(); i++) {
            board[members.get(i).cur.x][members.get(i).cur.y] = members.get(i);
        }
        snake.cur = dest; // A posição actual da cobra é agora a coordenada destino.
        elementListener.show(board[lastCur.x][lastCur.y], lastCur.x, lastCur.y); // O view mostra o ultimo elemento da lista.
        elementListener.show(board[dest.x][dest.y], dest.x, dest.y); // O view mostra o elemento na coordenada destino.
        elementListener.show(board[posX][posY], posX, posY); // O view mostra o elemento que está agora na posição antiga da cobra.
    }

    public Element getElement(int l, int c) {
        return board[l][c];
    }
}