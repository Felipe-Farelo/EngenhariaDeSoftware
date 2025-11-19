import java.util.*;

public class ProxyMochila {

    private final Mochila mochilaReal;

    public ProxyMochila(Mochila mochila) {
        this.mochilaReal = mochila;
    }

    public void adicionar(Item item) {
        mochilaReal.adicionarItem(item);
    }

    public int size() {
        return mochilaReal.size();
    }

    public MochilaIterator iterator() {
        return mochilaReal.getIterator();
    }

    public void abrirMenu(Heroi heroi) {
        Scanner sc = new Scanner(System.in);

        if (mochilaReal.size() == 0) {
            System.out.println("A mochila está vazia!");
            return;
        }

        boolean continuar = true;

        while (continuar) {

            System.out.println("\n--- Itens na mochila ---");
            MochilaIterator it = mochilaReal.getIterator();

            List<Item> itens = new ArrayList<>();
            int idx = 1;
            while (it.hasNext()) {
                Item item = it.next();
                System.out.println(idx + " - " + item.getTipo());
                itens.add(item);
                idx++;
            }

            System.out.print("Escolha um item pelo número (0 para sair): ");
            int escolha = sc.nextInt();
            sc.nextLine();

            if (escolha == 0) break;
            if (escolha < 1 || escolha > itens.size()) {
                System.out.println("Número inválido.");
                continue;
            }

            Item selecionado = itens.get(escolha - 1);
            mochilaReal.removerItemPorIndice(escolha - 1);

            System.out.print("Equipar na mão (E)esquerda ou (D)direita? ");
            char mao = sc.nextLine().trim().toUpperCase().charAt(0);

            Item antigo = null;

            if (mao == 'E') {
                LoggerEvento.registrar(
                    "Herói equipou uma " + selecionado.getTipo() + " na mão esquerda"
                ); 
                antigo = heroi.getMaoEsquerda();
                if (antigo != null) antigo.retiraBonusHeroi(heroi);
                heroi.setMaoEsquerda(selecionado);
                selecionado.aplicaBonusHeroi(heroi);
            } else if (mao == 'D') {
                LoggerEvento.registrar(
                    "Herói equipou uma " + selecionado.getTipo() + " na mão direita"
                );  
                antigo = heroi.getMaoDireita();
                if (antigo != null) antigo.retiraBonusHeroi(heroi);
                heroi.setMaoDireita(selecionado);
                selecionado.aplicaBonusHeroi(heroi);
            } else {
                System.out.println("Mão inválida, devolvendo item.");
                mochilaReal.adicionarItem(selecionado);
                continue;
            }

            if (antigo != null) {
                System.out.println("Deseja guardar o item antigo " + antigo.getTipo() + "? (S/N)");
                char resp = sc.nextLine().trim().toUpperCase().charAt(0);
                if (resp == 'S') {
                    LoggerEvento.registrar(
                        "Herói guardou uma " + antigo.getTipo() + " na mochila"
                    );
                    adicionar(antigo);
                }
                    
                else
                    LoggerEvento.registrar("Herói descartou " + antigo.getTipo());
            }

            System.out.println("Equipamento atualizado!");
            heroi.mostrarStatus();

            System.out.println("Escolher outro item? (S/N)");
            continuar = sc.nextLine().trim().equalsIgnoreCase("S");
        }
    }
}
