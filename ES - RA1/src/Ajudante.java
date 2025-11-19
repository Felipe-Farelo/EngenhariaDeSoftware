public interface Ajudante {

    String getNome(); // retorna o nome do ajudante

    int getDebuffHeroi(); // valor do debuff aplicado no herói

    int getDebuffMonstro(); // valor do debuff aplicado no monstro

    void apresentar(); // se apresenta ao herói
    void aplicaDebuff(Heroi heroi, Entidade monstro); // aplica o efeito
}
