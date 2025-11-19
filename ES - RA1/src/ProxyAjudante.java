public class ProxyAjudante implements Ajudante{
    // referência ao ajudante verdadeiro
    private final Ajudante ajudanteReal;

    // construtor: recebe o ajudante
    public ProxyAjudante(Ajudante real) {
        this.ajudanteReal = real;
    }

    @Override
    public String getNome() {
        return ajudanteReal.getNome();
    }

    @Override
    public int getDebuffHeroi() { return ajudanteReal.getDebuffHeroi(); }

    @Override
    public int getDebuffMonstro() { return ajudanteReal.getDebuffMonstro(); }

    @Override
    public void apresentar() {
        LoggerEvento.registrar("Herói encontrou um " + ajudanteReal.getNome());
        ajudanteReal.apresentar();
    }

    @Override
    public void aplicaDebuff(Heroi heroi, Entidade monstro) {
        LoggerEvento.registrar("Herói utilizou o ajudante " + ajudanteReal.getNome());
        ajudanteReal.aplicaDebuff(heroi, monstro);
    }


}
