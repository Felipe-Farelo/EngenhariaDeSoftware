public class Curupira extends Entidade implements Monstro{
    public Curupira(int ataque, int defesa, int vida) {
        super(ataque, defesa, vida);
    }

    @Override
    public void aplicarHabilidade() {
        ataque = (int)(ataque * 1.10); // +10% de ataque
        System.out.println("Ataque aumentado em 10%"); 
    }

    @Override
    public String getDescricao() {
        return "Curupira.";
    }

}