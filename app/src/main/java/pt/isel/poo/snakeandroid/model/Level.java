package pt.isel.poo.snakeandroid.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Gonçalo Veloso e André Carvalho on 27-10-2015.
 */
public class Level {
    private String title;   //Título do nível.
    private Dir atual = Dir.UP , before = Dir.UP; // A dir atual e a da jogada anterior
    private ElementListener elementListener;    //Interface a ser implementada.
    private boolean snakeDead, over;    //Booleans que determinam se a cobra está morta ou se o nível está acabado.
    public Element[][] board;   //Array bidimensional. Corresponde ao nível do jogo.
    private Head snake; //Inicialização de um objecto de cabeça da cobra.
    private int increment;    //Número de vértebras iniciais e a serem adicionadas quando a cobra come uma maçã.
    private int maxApples, currentApples; //Número de maçãs máximas num nível e número de maçãs existentes no nível.
    LinkedList<Snake> members = new LinkedList<Snake>(); //Lista que irá conter a cobra.

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

        //Se a direcao atual for inversa a anterior a jogada nao conta
        if(Dir.getOppositeDir(before) == dir){
            atual = before;
        }
        else {
            before = atual;
            atual = dir;
        }

        Coordinate dest = snake.getDest(atual); // Coordenada correspondente ao destino, de acordo com a direção.
        Coordinate current = snake.cur; // Coordenada onde a cabeça está antes de se movimentar.

        if (board[dest.x][dest.y] instanceof Space) { // Se o elemento no destino for um espaço vazio...

            moveTo(current.x, current.y, dest); // ...a cobra move-se para o destino sem problemas.
        }
        else if (snake.eat(board[dest.x][dest.y])) // Se for possível a cobra comer o elemento na coordenada destino...
        {
            snakeGrow();    // ...então come e cresce.
            appleGenerator();
            moveTo(current.x, current.y, dest);
        }
        if (maxApples > 0) { // Se não existirem mais maçãs no nível...
            if (currentApples < 2) appleGenerator(); /* ...e se ainda existirem maçãs para comer antes de o nível acabar,
                                                  é gerada uma nova maçã. */
        }else {
                //printBoard();
                endGame();
        } // Caso contrório, o jogo acaba.
    }

    /**
     * Gera uma nova maçã num sítio aleatório do nível.
     */
    private void appleGenerator() {
        Random rnd = new Random(); // Novo objecto random.
        int rndX = rnd.nextInt(Coordinate.maxLines); // Linha aleatória.
        int rndY = rnd.nextInt(Coordinate.maxColumns); // Coluna aleatória.
        while (!(board[rndX][rndY] instanceof Space)) { // Enquanto os valores gerados não corresponderem a um espaço...
            rndX = rnd.nextInt(Coordinate.maxLines); // ...são gerados valores novos.
            rndY = rnd.nextInt(Coordinate.maxColumns);
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
        Dir lastDir = members.getLast().getDirection();

        for (int i = increment; i > 0; i--) { // Cria novas vértebras de acordo com o valor em increment.
            if(i == 1){
                Tail t = new Tail(members.getLast().cur.x, members.getLast().cur.y);
                members.addLast(t);
            }else{
                Body b = new Body(members.getLast().cur.x, members.getLast().cur.y);
                b.setDirection(lastDir);
                members.addLast(b);
            }
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
        //printBoard();
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
            if(i == 1){
                members.addLast(new Tail(members.getLast().cur.x, members.getLast().cur.y));
            }else{
                members.addLast(new Body(members.getLast().cur.x, members.getLast().cur.y));
            }
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
        //Atualizamos a direcao da cabeça
        ((Snake)board[posX][posY]).setDirection(atual);

        Coordinate lastCur = members.getLast().cur; // Guarda a posição actual do último elemento da cobra.
        board[dest.x][dest.y] = board[posX][posY]; // Coloca a cabeça na coordenada destino.
        board[lastCur.x][lastCur.y] = new Space(lastCur.x, lastCur.y); // Na posição antiga da cauda está agora um objecto Space.
        board[posX][posY] = new Body(posX, posY); // Cria uma nova vertebra na coordenada antiga da cabeça.

        //Se a direcao atual for diferente da anterior acabamos de virar isso significa que a vertebra a seguir a cabeça sera curva
        if(!atual.name().equals(before.name())){

            //UP
            if(before.name().equals("UP") && atual.name().equals("LEFT")){
                ((Snake) board[posX][posY]).setDirection(Dir.DL) ;
            }
            else if(before.name().equals("UP") && atual.name().equals("RIGHT")){
                ((Snake) board[posX][posY]).setDirection(Dir.DR) ;
            }

            //DOWN
            if(before.name().equals("DOWN") && atual.name().equals("LEFT")){
                ((Snake) board[posX][posY]).setDirection(Dir.UL) ;
            }
            else if(before.name().equals("DOWN") && atual.name().equals("RIGHT")){
                ((Snake) board[posX][posY]).setDirection(Dir.UR) ;
            }

            //RIGHT
            if(before.name().equals("RIGHT") && atual.name().equals("UP")){
                ((Snake) board[posX][posY]).setDirection(Dir.UL) ;
            }
            else if(before.name().equals("RIGHT") && atual.name().equals("DOWN")){
                ((Snake) board[posX][posY]).setDirection(Dir.DL) ;
            }

            //LEFT
            if(before.name().equals("LEFT") && atual.name().equals("UP")){
                ((Snake) board[posX][posY]).setDirection(Dir.UR) ;
            }
            else if(before.name().equals("LEFT") && atual.name().equals("DOWN")){
                ((Snake) board[posX][posY]).setDirection(Dir.DR) ;
            }

        }else {
            //A vertebra aseguir a cabeca tera a mesma direcao que ela
            ((Snake) board[posX][posY]).setDirection(((Snake) board[dest.x][dest.y]).getDirection());
        }

        members.add(1, (Snake) board[posX][posY]); // Coloca o último elemento da lista na coordenada antiga da cabeça.
        members.removeLast(); // Remove o último elemento da cobra.

        int posXCauda = members.getLast().cur.x;
        int posYCauda = members.getLast().cur.y;
        Dir dirCauda = members.getLast().getDirection();
        dirCauda = Dir.correctDir(dirCauda, members.get(members.size() - 2).getDirection());

        board[posXCauda][posYCauda] = new Tail(posXCauda, posYCauda);
        ((Snake)board[posXCauda][posYCauda]).setDirection(dirCauda);

        members.removeLast();

        members.addLast((Snake)board[posXCauda][posYCauda]);

        // Actualiza as posições de cada vértebra a partir da segunda.
        for (int i = 2; i < members.size(); i++) {

                board[members.get(i).cur.x][members.get(i).cur.y] = members.get(i);

        }
        snake.cur = dest; // A posição actual da cobra é agora a coordenada destino.
        elementListener.show(board[lastCur.x][lastCur.y], lastCur.x, lastCur.y); // O view mostra o ultimo elemento da lista.
        elementListener.show(board[dest.x][dest.y], dest.x, dest.y); // O view mostra o elemento na coordenada destino.
        elementListener.show(board[posX][posY], posX, posY); // O view mostra o elemento que está agora na posição antiga da cobra.
        elementListener.show(board[posXCauda][posYCauda], posXCauda, posYCauda); // O view mostra a cauda
    }

    public Element getElement(int l, int c) {
        return board[l][c];
    }

    public void saveState(DataOutputStream data){


        try{
            //Guardamos o titulo
            data.writeUTF(this.getTitle());

            //Guardamos as var isOver e SnakeDead
            data.writeBoolean(this.isOver());
            data.writeBoolean(this.isSnakeDead());

            //Guardamos as maças
            data.writeInt(currentApples);
            data.writeInt(maxApples);

            //Guardamos o incremento das vertebras
            data.writeInt(this.increment);

            //Guardamos o numero de vertebras atuais( - 1 porque a nossa linked list tambem inclui a cabeca, e neste caso nao e
            // preciso, porque sera guardada no tabuleiro
            data.writeInt(members.size() - 1);

            //Guardamos o numero de linhas e colunas
            data.writeInt(Coordinate.maxLines);
            data.writeInt(Coordinate.maxColumns);

            //Guardamos o tabuleiro
            for(int i = 0; i < Coordinate.maxLines; i++){
                for(int j = 0; j < Coordinate.maxColumns; j++){
                   data.writeUTF(board[i][j].toString());

                }
            }


            //Guardamos a nossa linkedlist

            //Tamanho da cobra
            data.writeInt(members.size());

            for (Snake e : members){
                data.writeUTF(e.getDirection().name());
                data.writeInt(e.cur.x);
                data.writeInt(e.cur.y);

            }
        }catch (IOException e){
            e.printStackTrace();

        }finally {
            try {
                data.close();
            }catch (IOException e) {
                //Merda
            }
        }
    }

    public void loadState(DataInputStream data) {
        try {
            //Lemos o titulo
            title = data.readUTF();

            //Lemos o isOver e SnakeDead
            over = data.readBoolean();
            snakeDead = data.readBoolean();

            //Lemos as maçãs
            currentApples = data.readInt();
            maxApples = data.readInt();

            //Lemos o incremento das vertebras
            increment = data.readInt();

            int numero_vertebras = data.readInt();

            //Lemos o numero de linhas e colunas
            Coordinate.maxLines = data.readInt();
            Coordinate.maxColumns = data.readInt();

            //Lemos o tabuleiro
            board = new Element[Coordinate.maxLines][Coordinate.maxColumns];

            clearBoard(board);

            for (int i = 0; i < Coordinate.maxLines; i++) {
                for (int j = 0; j < Coordinate.maxColumns; j++) {
                    switch (data.readUTF()) {
                        case "X":
                            board[i][j] = new Wall(i, j);
                            break;
                        case "@":
                            board[i][j] = snake = new Head(i, j);
                            members.add(snake);
                            break;
                        case "O":
                            board[i][j] = new Apple(i, j);
                            break;
                        case " ":
                            board[i][j] = new Space(i, j);
                            break;
                        case "T":
                            board[i][j] = new Tail(i, j);
                            break;
                        case "#":
                            board[i][j] = new Body(i, j);
                            break;
                        default:
                            board[i][j] = new Space(i, j);
                            break;
                    }
                }
            }
            int tamanho = data.readInt();

            for (int i = 0; i < tamanho; i++) {
                String dir = data.readUTF();
                int dx = data.readInt();
                int dy = data.readInt();
                //Criacao da cauda
                if(i == tamanho - 1){
                    Tail aux = new Tail(dx, dy);
                    aux.setDirection(Dir.createDir(dir));
                    members.addLast(aux);
                }else{
                    Body aux = new Body(dx, dy);
                    aux.setDirection(Dir.createDir(dir));
                    members.addLast(aux);
                }
            }
            for (int i = 0; i < members.size(); i++) {
                System.out.println(members.get(i).getDirection());
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                data.close();
            }catch (IOException e){

            }
        }
    }
}