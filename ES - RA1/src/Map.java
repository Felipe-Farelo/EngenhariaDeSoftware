import java.io.*;
import java.util.*;

public class Map {
    private char[][] matriz;
    private boolean[][] visitado;
    private int numLinhas;
    private int numColunas;
    private int heroX, heroY;

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

                    if (matriz[i][j] == '8') { // símbolo do herói
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

    public void imprimeMapa() {
        limparTela();
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
    }

    public void moveHero(int novoX, int novoY) {
        matriz[heroX][heroY] = ' ';
        heroX = novoX;
        heroY = novoY;
        matriz[heroX][heroY] = '8';
    }

    private boolean podeMover(int x, int y) {
        if (x < 0 || x >= numLinhas || y < 0 || y >= numColunas) return false;
        return matriz[x][y] != '#';
    }

    private boolean naoVisitado(int x, int y) {
        return !visitado[x][y];
    }

    // Movimento automático do herói
    public boolean moveHeroAutomatic() throws InterruptedException {
        int[][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : direcoes) {
            int novoX = heroX + dir[0];
            int novoY = heroY + dir[1];

            if (podeMover(novoX, novoY) && naoVisitado(novoX, novoY)) {
                char destino = matriz[novoX][novoY];

                // --- Itens ---
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

                    System.out.println("Você encontrou uma " + tipoItem +
                            "! Digite 'd' para a mão direita, 'e' para a mão esquerda, ou 'n' para ignorar.");
                    Scanner scanner = new Scanner(System.in);
                    String escolha = scanner.nextLine().trim().toLowerCase();

                    switch (escolha) {
                        case "d":
                            if (heroi.getMaoDireita() != null)
                                heroi.getMaoDireita().retiraBonusHeroi(heroi);
                            heroi.setMaoDireita(item);
                            item.aplicaBonusHeroi(heroi);
                            break;
                        case "e":
                            if (heroi.getMaoEsquerda() != null)
                                heroi.getMaoEsquerda().retiraBonusHeroi(heroi);
                            heroi.setMaoEsquerda(item);
                            item.aplicaBonusHeroi(heroi);
                            break;
                        default:
                            System.out.println("Item ignorado.");
                            break;
                    }
                }

                // --- Ajudantes ---
                else if (destino == '^' || destino == '&') {
                    Ajudante ajudante = (destino == '^') ? new Duende() : new Anao();
                
                    System.out.println("Você encontrou um " + ajudante.getNome() + "!");
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
                
                // --- Monstros ---
                else if (destino == '?') { // Bicho Papão
                    BichoPapao bicho = new BichoPapao(20, 6, 30);

                    // Se o herói tiver um ajudante, ele age antes da batalha
                    if (heroi.getAjudante() != null) {
                        Ajudante ajudante = heroi.getAjudante();

                        System.out.println("O " + ajudante.getNome() + " apareceu para ajudar!");
                        ajudante.aplicaDebuff(heroi, bicho);
                        System.out.println("O " + ajudante.getNome() + " aplicou seu efeito e fugiu!\n");

                        heroi.perderAjudante();
                        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
                    }

                    // Inicia a batalha
                    bicho.batalha(heroi);

                    // Verifica se o herói morreu
                    if (heroi.getVida() <= 0) {
                        limparTela();
                        System.out.println("Você morreu! Fim de jogo.");
                        System.exit(0);
                    }

                    // Move o herói após vencer a luta
                    moveHero(novoX, novoY);
                }

                // --- Curupira ---
                else if (destino == '*') {
                    Curupira curupira = new Curupira(18, 9, 25);

                    if (heroi.getAjudante() != null) {
                        Ajudante ajudante = heroi.getAjudante();

                        System.out.println("O " + ajudante.getNome() + " apareceu para ajudar!");
                        ajudante.aplicaDebuff(heroi, curupira);
                        System.out.println("O " + ajudante.getNome() + " aplicou seu efeito e fugiu!\n");

                        heroi.perderAjudante();
                        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
                    }

                    curupira.batalha(heroi);

                    if (heroi.getVida() <= 0) {
                        limparTela();
                        System.out.println("Você morreu! Fim de jogo.");
                        System.exit(0);
                    }

                    moveHero(novoX, novoY);
                }


                // --- Saída ---
                else if (destino == '=') {
                    moveHero(novoX, novoY);
                    limparTela();
                    System.out.println("O Herói encontrou a saída!");
                    heroi.mostrarStatus();
                    System.exit(0);
                }

                moveHero(novoX, novoY);
                visitado[novoX][novoY] = true;
                return true;
            }
        }

        // tenta qualquer posição já visitada
        for (int[] dir : direcoes) {
            int novoX = heroX + dir[0];
            int novoY = heroY + dir[1];

            if (podeMover(novoX, novoY)) {
                if (matriz[novoX][novoY] == '=') {
                    moveHero(novoX, novoY);
                    limparTela();
                    System.out.println("O Herói encontrou a saída!");
                    System.exit(0);
                }
                moveHero(novoX, novoY);
                return true;
            }
        }

        return false;
    }
}
