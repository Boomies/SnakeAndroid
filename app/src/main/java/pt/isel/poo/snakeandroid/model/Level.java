package pt.isel.poo.snakeandroid.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
     * @return O t�tulo do n�vel.
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
     * @param dir Dire��o para onde a cobra se ir� mexer.
     */
    public void move(Dir dir) {
        Coordinate dest = snake.getDest(dir); // Coordenada correspondente ao destino, de acordo com a dire��o.
        Coordinate current = snake.cur; // Coordenada onde a cabe�a est� antes de se movimentar.
        if (board[dest.x][dest.y] instanceof Space) { // Se o elemento no destino for um espa�o vazio...
            moveTo(current.x, current.y, dest); // ...a cobra move-se para o destino sem problemas.
        } else if (snake.eat(board[dest.x][dest.y])) // Se for poss�vel a cobra comer o elemento na coordenada destino...
        {
            snakeGrow();    // ...ent�o come e cresce.
            moveTo(current.x, current.y, dest);
        }
        if (currentApples == 0) { // Se n�o existirem mais ma��s no n�vel...
            if (maxApples > 0) appleGenerator(); /* ...e se ainda existirem ma��s para comer antes de o n�vel acabar,
                                                   � gerada uma nova ma��. */
            else {
                endGame();
            } // Caso contr�rio, o jogo acaba.
        }
    }

    /**
     * Gera uma nova ma�� num s�tio aleat�rio do n�vel.
     */
    private void appleGenerator() {
        Random rnd = new Random(); // Novo objecto random.
        int rndX = rnd.nextInt(Coordinate.maxColumns); // Linha aleat�ria.
        int rndY = rnd.nextInt(Coordinate.maxLines); // Coluna aleat�ria.
        while (!(board[rndX][rndY] instanceof Space)) { // Enquanto os valores gerados n�o corresponderem a um espa�o...
            rndX = rnd.nextInt(Coordinate.maxColumns); // ...s�o gerados valores novos.
            rndY = rnd.nextInt(Coordinate.maxLines);
        }
        board[rndX][rndY] = new Apple(rndX, rndY); // A nova ma�� � colocada no local definido por rndX e rndY.
        elementListener.show(board[rndX][rndY], rndX, rndY); // � necess�rio que o view mostre essa nova ma��.
        currentApples++; // O n�mero de ma��s existente no n�vel � incrementado.
    }

    /**
     * Cresce a cobra.
     */
    public void snakeGrow() {
        maxApples--; //Decrementa o n�mero m�ximo de ma��s.
        currentApples--; //Decrementa o n�mero de ma��s existente no n�vel.
        for (int i = increment; i > 0; i--) { // Cria novas v�rtebras de acordo com o valor em increment.
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
     * Verifica se a cobra est� morta.
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
     * L� um n�vel atrav�s de um ficheiro input.
     *
     * @param file Ficheiro a ser lido.
     * @throws IOException Excep��o que pode surgir.
     */
    public void load(InputStream file){
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        String aux;
        String[] array;
        Scanner io;

        try {
            title = reader.readLine(); // A primeira linha corresponde ao t�tulo do n�vel.
        } catch (IOException e) {
            System.out.println("Erro a ler título.");;
        }

        try {
            aux = reader.readLine();

        io = new Scanner(aux); //aux corresponde agora � segunda linha, e io l� aux.

        /* O primeiro integer da segunda linha corresponde ao n�mero m�ximo de linhas do n�vel. */
        Coordinate.maxLines = Integer.parseInt(io.next());
        io.next(); // Passa � frente o 'x' entre os valores que s�o importantes.
        /* O segundo integer da segunda linha corresponde ao n�mero m�ximo de colunas do n�vel */
        Coordinate.maxColumns = Integer.parseInt(io.next());
        } catch (IOException e) {
            System.out.println("Erro a ler linha.");
        }

        try {
            aux = reader.readLine();
        io = new Scanner(aux); //aux corresponde agora � terceira linha, e io l� o aux.
        increment = Integer.parseInt(io.next()); // O primeiro integer da terceira linha corresponde a increment.
        maxApples = Integer.parseInt(io.next()); // O segundo integer da terceira linha corresponde ao n�mero m�ximo de ma��s.
        } catch (IOException e) {
            System.out.println("Erro ao ler linha.");
        }

        board = new Element[Coordinate.maxLines][Coordinate.maxColumns]; // Um novo n�vel � criado de acordo com os valores obtidos para maxLines e maxColumns.
        clearBoard(board);  // Esse novo n�vel � limpo.
        // Este for ir� percorrer a array bidimensional do n�vel e preenche-la com os objectos certos.
        for (int line = 0; line < Coordinate.maxLines; line++) {
            try {
                aux = reader.readLine();
            array = aux.split(""); // Separa cada char de modo a este ser lido individualmente.
            for (int column = 0; column < Coordinate.maxColumns; column++) {
                switch (array[column]) {
                    case "X":
                        board[line][column] = new Wall(line, column);
                        break;
                    case "@":
                        board[line][column] = snake = new Head(line, column);
                        members.add(snake);
                        break;
                    case "A":
                        board[line][column] = new Apple(line, column);
                        currentApples++;
                        break;
                    case " ":
                        board[line][column] = new Space(line, column);
                        break;
                }
            }
            } catch (IOException e) {
                System.out.println("Erro ao ler linha.");
            }
        }
        for (int i = increment; i > 0; i--) { // Cria novas v�rtebras de acordo com o valor em increment.
            members.addLast(new Vertebrae(members.getLast().cur.x, members.getLast().cur.y));
        }
    }

    /**
     * Limpa o n�vel, preenchedo-o com Spaces.
     *
     * @param board N�vel a ser limpo.
     */
    private void clearBoard(Element[][] board) {
        for (int i = 0; i < Coordinate.maxLines; i++) {
            for (int j = 0; j < Coordinate.maxColumns; j++) {
                board[i][j] = new Space(i, j);
            }
        }
    }

    /**
     * M�todo para debugging que imprime o board na consola.
     */
    public void printBoard() {
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
     * @param posX Linha onde a cobra est� actualmente.
     * @param posY Coluna onde a cobra est� actualmente.
     * @param dest Coordenada destino.
     */
    public void moveTo(int posX, int posY, Coordinate dest) {
        Coordinate lastCur = members.getLast().cur; // Guarda a posi��o actual do �ltimo elemento da cobra.
        board[dest.x][dest.y] = board[posX][posY]; // Coloca a cabe�a na coordenada destino.
        board[lastCur.x][lastCur.y] = new Space(lastCur.x, lastCur.y); // Na posi��o antiga da cauda est� agora um objecto Space.
        board[posX][posY] = new Vertebrae(posX, posY); // Cria uma nova vertebra na coordenada antiga da cabe�a.
        members.add(1, (Snake) board[posX][posY]); // Coloca o �ltimo elemento da lista na coordenada antiga da cabe�a.
        members.removeLast(); // Remove o �ltimo elemento da cobra.
        // Actualiza as posi��es de cada v�rtebra a partir da segunda.
        for (int i = 2; i < members.size(); i++) {
            board[members.get(i).cur.x][members.get(i).cur.y] = members.get(i);
        }
        snake.cur = dest; // A posi��o actual da cobra � agora a coordenada destino.
        elementListener.show(board[lastCur.x][lastCur.y], lastCur.x, lastCur.y); // O view mostra o ultimo elemento da lista.
        elementListener.show(board[dest.x][dest.y], dest.x, dest.y); // O view mostra o elemento na coordenada destino.
        elementListener.show(board[posX][posY], posX, posY); // O view mostra o elemento que est� agora na posi��o antiga da cobra.
    }

    public Element getElement(int l, int c) {
        return board[l][c];
    }
}