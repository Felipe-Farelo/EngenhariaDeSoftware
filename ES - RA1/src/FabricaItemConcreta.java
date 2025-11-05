public class FabricaItemConcreta implements FabricaItem {
    @Override
    public Item criarItem(char tipo) {
        return switch (tipo) {
            case 'e' -> new Espada();
            case 'd' -> new Escudo();
            case 'c' -> new Cura();
            default -> null;
        };
    }
}
