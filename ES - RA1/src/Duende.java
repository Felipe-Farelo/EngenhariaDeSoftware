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
                "diminuindo " + debuffMonstro + " do ataque do seu inimigo, " +
                "mas em troca eu diminuo " + debuffHeroi + " do seu ataque.");
    }

    @Override
    public void aplicaDebuff(Heroi heroi, Entidade monstro) {
        // reduz ataque do monstro
        monstro.setAtaque(monstro.getAtaque() - debuffMonstro);
        if (monstro.getAtaque() < 0) monstro.setAtaque(0);

        // reduz ataque do herói
        heroi.setAtaque(heroi.getAtaque() - debuffHeroi);
        if (heroi.getAtaque() < 0) heroi.setAtaque(0);

        System.out.println("O Duende reduziu ataque do monstro em " + debuffMonstro +
                ", mas seu ataque caiu em " + debuffHeroi + ".");
    }
}
