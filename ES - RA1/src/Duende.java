public class Duende extends Ajudante{
    public Duende() {
        super("Duende", 2, 6);
    }

    @Override
    public void aplicaDebuff(Heroi heroi, Entidade monstro) {
        // Debuff no monstro
        monstro.setDefesa(monstro.getDefesa() - 6);
        if (monstro.getDefesa() < 0) monstro.setDefesa(0);

        // Debuff no herói
        heroi.setDefesa(heroi.getDefesa() - 2);
        if (heroi.getDefesa() < 0) heroi.setDefesa(0);
 
        System.out.println("O Duende enfraqueceu a defesa do monstro em 6, mas sua defesa caiu em 2.");
    }

    @Override
    public void apresentar() {
        System.out.println("Olá, sou um duende, eu consigo te ajudar em uma batalha, " +
        "diminuindo " + debuffMonstro + " da defesa do seu inimigo, " +
        "mas em troca eu diminuo " + debuffHeroi + " da sua defesa.");
    }

}
