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
    public boolean moveHeroAutomatic() throws InterruptedException {
        // tenta mover para posições "não visitadas"
        // direita -> baixo -> esquerda -> cima
        int [][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : direcoes) {
            int novoX = heroX + dir[0];
            int novoY = heroY + dir[1];

            if (podeMover(novoX, novoY) && naoVisitado(novoX, novoY)) {
                char destino = matriz[novoX][novoY];

                if (destino == 'e' || destino == 'd' || destino == 'c') {
                    // -- Itens --

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
                else if (destino == '^' || destino == '&') {
                    // -- Ajudantes --

                    Ajudante ajudante = (destino == '^') ? new Duende() : new Anao();

                    System.out.println("VocÊ encontrou um " + ajudante.getNome() + "!");
                    ajudante.apresentar();

                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Deseja recrutar este ajudante? (s/n)");
                    String escolha = scanner.nextLine().trim().toLowerCase();

                    if (escolha.equals("s")) {
                        if (heroi.getAjudante() != null) {
                            System.out.println("Você já possui um ajudante (" + heroi.getAjudante().getNome() + "). Deseja trocar? (s/n)");
                            String troca = scanner.nextLine().trim().toLowerCase();
                            if (troca.equals("s")) {
                                heroi.setAjudante(ajudante);
                                System.out.println("Agora seu ajudante é o " + ajudante.getNome() + "!");
                            } else {
                                System.out.println("Você manteve seu ajudante atual.");
                            }
                        } else {
                            heroi.setAjudante(ajudante);
                            System.out.println("O " + ajudante.getNome() + " agora está ao seu lado!");
                        }
                    } else {
                        System.out.println("Você ignorou o ajudante.");
                    }
                }
                if (destino == '?') {  // Bicho Papão
                    BichoPapao bicho = new BichoPapao(20, 6, 30);
                
                    // aplica efeito do ajudante
                    if (heroi.getAjudante() != null) {
                        heroi.getAjudante().aplicaDebuff(heroi, bicho);
                    }
                
                    // inicia a batalha
                    bicho.batalha(heroi);
                
                    // checa se o herói morreu
                    if (heroi.getVida() <= 0) {
                        limparTela();
                        System.out.println("Você morreu! Fim de jogo.");
                        System.exit(0);
                    }
                
                    // só depois disso move o herói para a posição do monstro
                    moveHero(novoX, novoY);
                }
                
                else if (destino == '*') {  // Curupira
                    Curupira curupira = new Curupira(18, 9, 25);
                
                    if (heroi.getAjudante() != null) {
                        heroi.getAjudante().aplicaDebuff(heroi, curupira);
                    }
                
                    curupira.batalha(heroi);
                
                    if (heroi.getVida() <= 0) {
                        limparTela();
                        System.out.println("Você morreu! Fim de jogo.");
                        System.exit(0);
                    }
                
                    moveHero(novoX, novoY);
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
