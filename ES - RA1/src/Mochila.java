import java.util.*;

public class Mochila {
    private static final int LIMITE = 10;
    private final List<Item> itens = new ArrayList<>();


    // retorna iterator para percorrer itens da mochila
    public MochilaIterator getIterator() {
        return new MochilaIterator(itens);
    }    

    // adiciona um item na mochila, verificando limite
    public void adicionarItem(Item item) {
        if (itens.size() >= LIMITE) {
            System.out.println("A mochila está cheia! Não foi possível adicionar " + item.getTipo());
            return;
        }
        itens.add(item);
        System.out.println(item.getTipo() + " adicionado à mochila.");
    }

    public Item getItemPorIndice(int indice) {
        if (indice < 0 || indice >= itens.size()) return null;
        return itens.get(indice);
    }

    public void removerItemPorIndice(int indice) {
        if (indice >= 0 && indice < itens.size()) { itens.remove(indice); }
    }

    public int size() { return itens.size(); }

    // usado pelo iterator que você já implementou
    public List<Item> getItens() {
        return itens;
    }

    // exibe o conteúdo da mochila
    public void mostrarConteudo() {
        if (itens.isEmpty()) {
            System.out.println("Mochila: vazia");
            return;
        }
        System.out.println("Mochila:");
        int idx = 1;
        for (Item it : itens) {
            System.out.println(idx++ + " - " + it.getTipo());
        }
    }

    public void tratarItemSubstituido(Heroi heroi, Item itemAntigo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Você já tem um item nessa mão: " + itemAntigo.getTipo());
        System.out.println("Deseja guardar na mochila (M) ou descartar (D)?");

        char escolha = scanner.next().toUpperCase().charAt(0);
        if (escolha == 'M') {
            heroi.adicionarItemNaMochila(itemAntigo);
        } else {
            System.out.println(itemAntigo.getTipo() + " foi descartado.");
        }
    }
}
