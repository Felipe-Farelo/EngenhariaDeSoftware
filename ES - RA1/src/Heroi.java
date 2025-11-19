public class Heroi extends Entidade {
    private Item maoEsquerda;
    private Item maoDireita;
    private Ajudante ajudante;
    private Mochila mochila;

    public Heroi(int ataque, int defesa, int vida) {
        super(ataque, defesa, vida);
        this.mochila = new Mochila();
        proxyMochila = new ProxyMochila(mochila);
    }

    // métodos para gerenciar equipamentos, ajudante e mochila...

    //region mão esquerda
    public void setMaoEsquerda(Item item) { this.maoEsquerda = item; }

    public Item getMaoEsquerda() { return maoEsquerda; }
    //endregion

    //region mão direita
    public void setMaoDireita(Item item) { this.maoDireita = item; }

    public Item getMaoDireita() { return maoDireita; }
    //endregion

    //region Ajudante
    public Ajudante getAjudante() { return ajudante; }
    
    public void setAjudante(Ajudante ajudante) { this.ajudante = ajudante; }
    
    public void perderAjudante() { this.ajudante = null; } 
    //endregion

    //region Mochila
    public Mochila getMochila() { return mochila; }

    public void adicionarItemNaMochila(Item item) { mochila.adicionarItem(item); }

    private ProxyMochila proxyMochila;

    public ProxyMochila getProxyMochila() { return proxyMochila; }

    public void setProxyMochila(ProxyMochila p) { this.proxyMochila = p; }
 
    //endregion

    public void mostrarStatus() {
        System.out.println("== STATUS DO HERÓI ==");
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa: " + defesa);
        System.out.println("Vida: " + vida);
        System.out.println("Mão Esquerda: " + (maoEsquerda == null ? "vazia" : maoEsquerda.getTipo()));
        System.out.println("Mão Direita: " + (maoDireita == null ? "vazia" : maoDireita.getTipo()));
        System.out.println("Ajudante: " + (ajudante == null ? "vazia" : ajudante.getNome()));
        System.out.print("Mochila: ");

        MochilaIterator it = mochila.getIterator();
        if (!it.hasNext()) {
            System.out.println("vazia");
        } else {
            while (it.hasNext()) {
                Item item = it.next();
                System.out.print(item.getTipo() + " ");
            }
            System.out.println();
        }
    }
}
