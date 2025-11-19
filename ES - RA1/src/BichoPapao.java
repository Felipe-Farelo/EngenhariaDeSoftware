public class BichoPapao extends Entidade implements Monstro {

    public BichoPapao(int ataque, int defesa, int vida) {
        super(ataque, defesa, vida);
    }

    // aplica a habilidade especial que aumenta defesa em 10%
    @Override
    public void aplicarHabilidade() {
        defesa = (int)(defesa * 1.10);
        System.out.println("Bicho Papão usou sua habilidade! Defesa aumentada em 10%.");
    }

    // retorna a descrição do monstro
    @Override
    public String getDescricao() { return "Bicho Papão."; }

    @Override
    public int getAtaque() { return ataque; }
    @Override
    public int getDefesa() { return defesa; }
    @Override
    public int getVida() { return vida; }
}
