public abstract class Ajudante {
    protected String nome;
    protected int debuffHeroi;
    protected int debuffMonstro;

    public Ajudante(String nome, int debuffHeroi, int debuffMonstro) {
        this.nome = nome;
        this.debuffHeroi = debuffHeroi;
        this.debuffMonstro = debuffMonstro;
    }

    public String getNome() {
        return nome;
    }

    public int getDebuffHeroi() {
        return debuffHeroi;
    }

    public int getDebuffMonstro() {
        return debuffMonstro;
    }

    public abstract void apresentar();

    // aplica o debuff quando encontrarmos um monstro
    public abstract void aplicaDebuff (Heroi heroi, Entidade monstro);

}
