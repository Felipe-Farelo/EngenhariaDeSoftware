public class Curupira extends Entidade implements Monstro{
    public Curupira(int ataque, int defesa, int vida) {
        super(ataque, defesa, vida);
    }

    // aplica a habilidade de 10% de ataque
    @Override
    public void aplicarHabilidade() {
        ataque = (int)(ataque * 1.10); // +10% de ataque
        System.out.println("Ataque aumentado em 10%"); 
    }

    // retorna descrição do monstro
    @Override
    public String getDescricao() {
        return "Curupira.";
    }

    @Override
    public int getAtaque() { return ataque; }
    @Override
    public int getDefesa() { return defesa; }
    @Override
    public int getVida() { return vida; }

}