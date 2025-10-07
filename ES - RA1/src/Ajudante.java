public interface Ajudante {

    String getNome();
    int getDebuffHeroi();
    int getDebuffMonstro();

    void apresentar(); // se apresenta ao herói
    void aplicaDebuff(Heroi heroi, Entidade monstro); // aplica o efeito
}
