public class Entidade {
    protected int ataque;
    protected int defesa;
    protected int vida;

    public Entidade(int ataque, int defesa, int vida) {
        this.ataque = ataque;
        this.defesa = defesa;
        this.vida = vida;
    }

    //region Ataque
    public int getAtaque() { return ataque; }

    public void setAtaque(int ataque) { this.ataque = ataque; }
    //endregion

    //region Defesa
    public int getDefesa() { return defesa; }

    public void setDefesa(int defesa) { this.defesa = defesa; }
    //endregion

    //region Vida
    public int getVida() { return vida; }

    public void setVida(int vida) { this.vida = vida; }
    //endregion

    public void receberDano(int dano) {
        int danoFinal = dano - defesa;

        if (danoFinal < 0) danoFinal = 0;
        vida -= danoFinal;
        if (vida < 0) vida = 0;
    }

}
