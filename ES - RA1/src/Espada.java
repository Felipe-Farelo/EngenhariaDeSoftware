public class Espada extends Item{
    public Espada() {
        super(EnumBonusItem.BONUS_ESPADA);
    }

    @Override
    public void aplicaBonusHeroi(Heroi heroi) {
        heroi.setAtaque((heroi.getAtaque() + bonus.getValor()));
    }

    @Override
    public void retiraBonusHeroi(Heroi heroi) {
        heroi.setAtaque(heroi.getAtaque() - bonus.getValor());
    }

    @Override
    public void imprimeDescricao() {
        System.out.println("Espada: aumenta o ataque em " + bonus.getValor());
    }

    @Override
    public String getTipo() {
        return "Espada";
    }
}
