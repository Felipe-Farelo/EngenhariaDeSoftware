public class ProxyItem extends Item {
    private final Item itemReal;

    public ProxyItem(Item real) {
        super(real.bonus);
        this.itemReal = real;
    }

    @Override
    public void aplicaBonusHeroi(Heroi heroi) {
        itemReal.aplicaBonusHeroi(heroi);
    }

    @Override
    public void retiraBonusHeroi(Heroi heroi) {
        itemReal.retiraBonusHeroi(heroi);
    }

    @Override
    public void imprimeDescricao() {
        itemReal.imprimeDescricao();
    }

    @Override
    public String getTipo() {
        return itemReal.getTipo();
    }

    public void registrarEquipado(String mao) {
        LoggerEvento.registrar(
            "Herói equipou uma " + itemReal.getTipo() + " na mão " + mao
        );
    }

    public void registrarGuardado() {
        LoggerEvento.registrar(
            "Herói guardou uma " + itemReal.getTipo() + " na mochila"
        );
    }

    public void registrarEncontrado() {
        LoggerEvento.registrar(
            "Herói encontrou uma " + itemReal.getTipo()
        );
    }

    public void registrarIgnorado() {
        LoggerEvento.registrar(
            "Herói ignorou uma " + itemReal.getTipo()
        );
    }
}
