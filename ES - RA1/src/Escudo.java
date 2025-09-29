public class Escudo extends Item{
    public Escudo() {
        super(EnumBonusItem.BONUS_ESCUDO);
    }

    @Override
    public void aplicaBonusHeroi(Heroi heroi) {
        heroi.setDefesa((heroi.getDefesa() + bonus.getValor()));
    }

    @Override
    public void retiraBonusHeroi(Heroi heroi) {
        heroi.setDefesa(heroi.getDefesa() - bonus.getValor());
    }

    @Override
    public void imprimeDescricao() {
        System.out.println("Escudo: aumenta a defesa em " + bonus.getValor());
    }

    @Override
    public String getTipo() {
        return "Escudo";
    }
}
