public class Duende implements Ajudante {

    private final String nome = "Duende";
    private final int debuffHeroi = 2;
    private final int debuffMonstro = 6;

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getDebuffHeroi() {
        return debuffHeroi;
    }

    @Override
    public int getDebuffMonstro() {
        return debuffMonstro;
    }

    @Override
    public void apresentar() {
        System.out.println("Olá, sou um duende! Eu consigo te ajudar em uma batalha, " +
                "diminuindo " + debuffMonstro + " da defesa do seu inimigo, " +
                "mas em troca eu diminuo " + debuffHeroi + " da sua defesa.");
    }

    @Override
    public void aplicaDebuff(Heroi heroi, Entidade monstro) {
        monstro.setDefesa(monstro.getDefesa() - debuffMonstro);
        if (monstro.getDefesa() < 0) monstro.setDefesa(0);

        heroi.setDefesa(heroi.getDefesa() - debuffHeroi);
        if (heroi.getDefesa() < 0) heroi.setDefesa(0);

        System.out.println("O Duende reduziu a defesa do monstro em " + debuffMonstro +
                ", mas sua defesa caiu em " + debuffHeroi + ".");
    }
}
