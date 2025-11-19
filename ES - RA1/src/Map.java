import java.io.*;
import java.util.*;

public class Map {
    private char[][] matriz;
    private boolean[][] visitado;
    private int numLinhas;
    private int numColunas;
    private int heroX, heroY;

    private Heroi heroi;

    private FabricaMonstro fabricaMonstro;
    private FabricaAjudante fabricaAjudante;
    private FabricaItem fabricaItem;

    public Map(String nomeArq, int linhas, int colunas, Heroi heroi,
               FabricaMonstro fabricaMonstro,
               FabricaAjudante fabricaAjudante,
               FabricaItem fabricaItem) {
        this.numLinhas = linhas;
        this.numColunas = colunas;
        this.matriz = new char[linhas][colunas];
        this.visitado = new boolean[linhas][colunas];
        this.heroi = heroi;

        this.fabricaMonstro = fabricaMonstro;
        this.fabricaAjudante = fabricaAjudante;
        this.fabricaItem = fabricaItem;

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
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n------------------------------");
        heroi.mostrarStatus();
        System.out.println("------------------------------\n");
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
    
    private void tratarItemSubstituido(Heroi heroi, Item itemAntigo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Você já tem um item nessa mão: " + itemAntigo.getTipo());
        System.out.println("Deseja guardar na mochila (M) ou descartar (D)?");

        char escolha = scanner.next().toUpperCase().charAt(0);

        if (escolha == 'M') {

            ProxyItem proxyItemAntigo = new ProxyItem(itemAntigo);

            proxyItemAntigo.registrarGuardado();

            heroi.getProxyMochila().adicionar(proxyItemAntigo);

            System.out.println(itemAntigo.getTipo() + " foi guardado na mochila.");

        } else {
            System.out.println(itemAntigo.getTipo() + " foi descartado.");
        }
    }

    private void equiparItemNaMao(Heroi heroi, Item novoItem, String mao) {

        Item itemAtual;

        if (mao.equals("E")) {
            itemAtual = heroi.getMaoEsquerda();
        } else {
            itemAtual = heroi.getMaoDireita();
        }

        if (itemAtual != null) {
            tratarItemSubstituido(heroi, itemAtual);
        }

        if (mao.equals("E")) {
            heroi.setMaoEsquerda(novoItem);
        } else {
            heroi.setMaoDireita(novoItem);
        }
    }

    // movimento automático do herói
    public boolean moveHeroAutomatic() throws InterruptedException {
        int[][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : direcoes) {
            int novoX = heroX + dir[0];
            int novoY = heroY + dir[1];

            if (podeMover(novoX, novoY) && naoVisitado(novoX, novoY)) {
                char destino = matriz[novoX][novoY];
                Scanner scanner = new Scanner(System.in);

                // --- Itens ---
                if (destino == 'e' || destino == 'd' || destino == 'c') {

                    Item itemReal = fabricaItem.criarItem(destino);
                    ProxyItem item = new ProxyItem(itemReal);

                    item.registrarEncontrado();

                    System.out.println("Você encontrou uma " + item.getTipo());
                    System.out.println("(E)quipar esquerda");
                    System.out.println("(D)quipar direita");
                    System.out.println("(M)ochila - guardar");
                    System.out.println("(N)ão pegar");

                    char escolha = scanner.next().toUpperCase().charAt(0);

                    switch (escolha) {
                        case 'E':
                            if (heroi.getMaoEsquerda() != null)
                                heroi.getMaoEsquerda().retiraBonusHeroi(heroi);

                            equiparItemNaMao(heroi, item, "E");
                            item.aplicaBonusHeroi(heroi);
                            item.registrarEquipado("esquerda");
                            System.out.println("Equipado na mão esquerda.");
                            break;

                        case 'D':
                            if (heroi.getMaoDireita() != null)
                                heroi.getMaoDireita().retiraBonusHeroi(heroi);

                            equiparItemNaMao(heroi, item, "D");
                            item.aplicaBonusHeroi(heroi);
                            item.registrarEquipado("direita");
                            System.out.println("Equipado na mão direita.");
                            break;

                        case 'M':
                            heroi.getProxyMochila().adicionar(item);
                            item.registrarGuardado();
                            System.out.println("Abrir mochila? (S/N)");

                            char abrir = scanner.next().toUpperCase().charAt(0);

                            if (abrir == 'S')
                                heroi.getProxyMochila().abrirMenu(heroi);

                            break;

                        case 'N':
                            item.registrarIgnorado();
                            System.out.println("Você deixou o item para trás.");
                            break;
                    }
                }


                // --- Ajudantes ---
                else if (destino == '^' || destino == '&') {
                    Ajudante ajudante = new ProxyAjudante(fabricaAjudante.criarAjudante(destino));
                    if (ajudante == null) {
                        System.out.println("Erro: ajudante desconhecido");
                    } else {
                        System.out.println("Você encontrou um " + ajudante.getNome() + "!");
                        ajudante.apresentar();                
                        
                    Scanner scn = new Scanner(System.in);
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
                    }   else {
                            System.out.println("Você ignorou o ajudante.");
                        }
                    }
                }
                
                // --- Monstros ---
                else if (destino == '?' || destino == '*') {
                    Monstro monstro = new ProxyMonstro(fabricaMonstro.criarMonstro(destino));
                    if (monstro == null) {
                        System.out.println("Erro: monstro desconhecido");
                    } else {
                        if (heroi.getAjudante() != null) {
                            System.out.println("O " + heroi.getAjudante().getNome() + " apareceu para ajudar!");
                            heroi.getAjudante().aplicaDebuff(heroi, (Entidade) monstro);
                            System.out.println("O " + heroi.getAjudante().getNome() + " aplicou seu efeito e fugiu!\n");
                            heroi.perderAjudante();
                            Thread.sleep(1500);
                        }
                        monstro.batalha(heroi);
                        if (heroi.getVida() > 0) {
                            LoggerEvento.registrar("Herói derrotou " + monstro.getDescricao());
                        } else {
                            LoggerEvento.registrar("Herói foi derrotado por " + monstro.getDescricao());
                        }

                        if (heroi.getVida() <= 0) { limparTela(); System.out.println("Você morreu!"); System.exit(0); }
                        moveHero(novoX, novoY);
                    }
                }
                    
                // --- Saída ---
                else if (destino == '=') {
                    moveHero(novoX, novoY);
                    limparTela();
                    LoggerEvento.registrar("Herói encontrou a saída do labirinto!");
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
                moveHero(novoX, novoY);
                return true;
            }
        }

        return false;
    }
}
