public class Anao extends Ajudante {
    public Anao() {
        super("Anão", 2, 6);
    }

    @Override
    public void aplicaDebuff(Heroi heroi, Entidade monstro) {
        // Debuff no monstro (forte)
        monstro.setAtaque(monstro.getAtaque() - 6);
        if (monstro.getAtaque() < 0) monstro.setAtaque(0);

        // Debuff no herói (leve)
        heroi.setAtaque(heroi.getAtaque() - 2);
        if (heroi.getAtaque() < 0) heroi.setAtaque(0);

        System.out.println("O Anão reduziu o ataque do monstro em 6, mas seu ataque caiu em 2.");
    }

    @Override
    public void apresentar() {
        System.out.println("Olá, sou um anão, eu consigo te ajudar em uma batalha, " +
                "diminuindo " + debuffMonstro + " do ataque do seu inimigo, " +
                "mas em troca eu diminuo " + debuffHeroi + " do seu ataque.");
    }
}
