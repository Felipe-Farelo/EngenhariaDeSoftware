public class ProxyMonstro extends Entidade implements Monstro {
    private final Monstro monstroReal;

    public ProxyMonstro(Monstro real) {
        super(real.getAtaque(), real.getDefesa(), real.getVida()); // inicializa Entidade base
        this.monstroReal = real;
    }

    @Override
    public void aplicarHabilidade() {
        monstroReal.aplicarHabilidade();
    }

    @Override
    public String getDescricao() {
        return monstroReal.getDescricao();
    }

    @Override
    public void mostrarStatus(Heroi heroi) {
        monstroReal.mostrarStatus(heroi);
    }

    @Override
    public void batalha(Heroi heroi) {
        LoggerEvento.registrar("Herói vai batalhar contra um " + monstroReal.getDescricao());
        monstroReal.batalha(heroi);
    }

    @Override
    public int getAtaque() {
        return monstroReal.getAtaque();
    }

    @Override
    public int getDefesa() {
        return monstroReal.getDefesa();
    }

    @Override
    public int getVida() {
        return monstroReal.getVida();
    }
}
