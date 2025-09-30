public class Heroi extends Entidade{
    private Item maoEsquerda; 
    private Item maoDireita;
    private Ajudante ajudante;

    public Heroi(int ataque, int defesa, int vida) {
        super(ataque, defesa, vida);
    }

    //region mão esquerda
    public void setMaoEsquerda(Item item) { this.maoEsquerda = item; }

    public Item getMaoEsquerda() { return maoEsquerda; }
    //endregion

    //region mão direita
    public void setMaoDireita(Item item) { this.maoDireita = item; }

    public Item getMaoDireita() { return maoDireita; }
    //endregion

    //region Ajudante
    public void setAjudante(Ajudante ajudante) {
        this.ajudante = ajudante;
    }

    public Ajudante getAjudante() {
        return ajudante;
    }

    public void perderAjudante() {
        this.ajudante = null;
    }
    //endregion

    public void mostrarStatus() {
        System.out.println("== STATUS DO HERÓI ==");
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa: " + defesa);
        System.out.println("Vida: " + vida);
        System.out.println("Mão Esquerda: " + (maoEsquerda == null ? "vazia" : maoEsquerda.getTipo()));
        System.out.println("Mão Direita: " + (maoDireita == null ? "vazia" : maoDireita.getTipo()));
        System.out.println("Ajudante: " + (ajudante == null ? "vazia" : ajudante.getNome()));
    }

}
