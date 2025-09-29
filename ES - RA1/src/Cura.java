public class Cura extends Item{
    public Cura() {
        super(EnumBonusItem.BONUS_CURA);
    }

    @Override
    public void aplicaBonusHeroi(Heroi heroi) {
        heroi.setVida((heroi.getVida() + bonus.getValor()));
    }

    @Override
    public void retiraBonusHeroi(Heroi heroi) {
        heroi.setVida((heroi.getVida() - bonus.getValor()));
    }

    @Override
    public void imprimeDescricao() {
        System.out.println("Cura: restaura " + bonus.getValor() + " pontos de vida");
    }

    @Override
    public String getTipo() {
        return "Cura";
    }
}
