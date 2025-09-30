public class BichoPapao extends Entidade implements Monstro {

    public BichoPapao(int ataque, int defesa, int vida) {
        super(ataque, defesa, vida);
    }

    @Override
    public void aplicarHabilidade() {
        defesa = (int)(defesa * 1.10);
        System.out.println("Bicho Papão usou sua habilidade! Defesa aumentada em 10%.");
    }

    @Override
    public String getDescricao() {
        return "Bicho Papão.";
    }
}
