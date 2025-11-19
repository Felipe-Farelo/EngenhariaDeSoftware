import java.util.List;

public class MochilaIterator {
    private final List<Item> itens;
    private int pos = 0;

    public MochilaIterator(List<Item> itens) {
        this.itens = itens;
    }

    public boolean hasNext() {
        return pos < itens.size();
    }

    public Item next() {
        if (!hasNext()) return null;
        return itens.get(pos++);
    }
}
