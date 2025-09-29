public enum EnumBonusItem {
    BONUS_ESPADA(10),
    BONUS_ESCUDO(8),
    BONUS_CURA(15);

    private final int valor;

    EnumBonusItem(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
