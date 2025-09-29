import java.io.*;
import java.util.*;

public class Map {
    private char [][] matriz;
    private boolean [][] visitado;
    private int numLinhas;
    private int numColunas;
    private int heroX, heroY; // posição do herói no mapa

    private Heroi heroi;

    public Map(String nomeArq, int linhas, int colunas, Heroi heroi) {
        this.numLinhas = linhas;
        this.numColunas = colunas;
        this.matriz = new char[linhas][colunas];
        this.visitado = new boolean[linhas][colunas];
        this.heroi = heroi;
        carregarMapa(nomeArq);
    }

    private void carregarMapa(String nomeArq) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArq))) {
            for (int i = 0; i < numLinhas; i++) {
                String linha = br.readLine();
                for (int j = 0; j < numColunas; j++) {
                    matriz[i][j] = linha.charAt(j);

                    if (matriz[i][j] == '8') { //simbolo do herói
                        heroX = i;
                        heroY = j;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }    

    // Imprimir o mapa
    public void imprimeMapa() {
        limparTela();

        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                System.out.print(matriz[i][j]); // sem espaço extra
            }
            System.out.println();
        }
    }


    // atualiza a aposição do herói
    public void moveHero(int novoX, int novoY) {
        matriz[heroX][heroY] = ' '; // limpa a posição antiga
        heroX = novoX;
        heroY = novoY;
        matriz[heroX][heroY] = '8'; // herói na nova posição
    }

    // verifica se o herói pode se mover para a posição (x, y)
    private boolean podeMover(int x, int y) {
        if (x < 0 || x >= numLinhas || y < 0 || y >= numColunas) return false; // fora do mapa
        char destino = matriz[x][y];
        return destino != '#'; // só pode andar se não for parede
    }

    private boolean naoVisitado(int x, int y) {
        return !visitado[x][y];
    }

    // movimento automático
    public boolean moveHeroAutomatic() {
        // tenta mover para posições "não visitadas"
        // direita -> baixo -> esquerda -> cima
        int [][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : direcoes) {
            int novoX = heroX + dir[0];
            int novoY = heroY + dir[1];

            if (podeMover(novoX, novoY) && naoVisitado(novoX, novoY)) {
                char destino = matriz[novoX][novoY];

                if (destino == 'e' || destino == 'd' || destino == 'c') {

                    Item item = null;
                    String tipoItem = "";

                    if (destino == 'e') {
                        item = new Espada();
                        tipoItem = "espada";
                    } else if (destino == 'd') {
                        item = new Escudo();
                        tipoItem = "escudo";
                    } else if (destino == 'c') {
                        item = new Cura();
                        tipoItem = "cura";
                    }

                    // Pergunta ao jogador qual mão
                    System.out.println("Você encontrou uma " + tipoItem + "... Digite 'd' para mão direita, 'e' para mão esquerda, ou 'n' para ignorar.");
                    Scanner scanner = new Scanner(System.in);
                    // Lê escolha do jogador
                    String escolha = scanner.nextLine().trim().toLowerCase();

                    switch (escolha) {
                        case "d":
                            // remove item antigo da mão direita
                            Item antigo = heroi.getMaoDireita();
                            if (antigo != null) antigo.retiraBonusHeroi(heroi);

                            heroi.setMaoDireita(item);      // equipa novo item
                            item.aplicaBonusHeroi(heroi);   // aplica efeito
                            break;

                        case "e":
                            // remove item antigo da mão esquerda
                            Item antigoE = heroi.getMaoEsquerda();
                            if (antigoE != null) antigoE.retiraBonusHeroi(heroi);

                            heroi.setMaoEsquerda(item);
                            item.aplicaBonusHeroi(heroi);
                            break;

                        case "n":
                            System.out.println("Item ignorado.");
                            break;
                    }

                    
                }
                else if (destino == '=') {
                    moveHero(novoX, novoY);
                    limparTela();
                    System.out.println("O Herói encontrou a saída");
                    heroi.mostrarStatus();
                    System.exit(0);
                }
                moveHero(novoX, novoY);
                visitado[novoX][novoY] = true;
                return true;
            }
        }

        // se não houver posições novas, ele tenta qualquer posição para não ficar preso
        for (int[] dir : direcoes) {
            int novoX = heroX + dir[0];
            int novoY = heroY + dir[1];

            if (podeMover(novoX, novoY)) {
                if (matriz[novoX][novoY] == '=') { // saída encontrada!
                    moveHero(novoX, novoY);
                    limparTela();
                    System.out.println("O Herói encontrou a saída!");
                    System.exit(0);
                }
                moveHero(novoX, novoY);
                return true; // volta para posição já visitada
            }
        }

        return false; // não tem como se mover
    }
    
}
