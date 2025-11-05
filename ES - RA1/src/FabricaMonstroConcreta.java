public class FabricaMonstroConcreta implements FabricaMonstro {
    @Override
    public Monstro criarMonstro(char tipo) {
        return switch (tipo) {
            case '?' -> new BichoPapao(20, 6, 30);
            case '*' -> new Curupira(18, 9, 25);
            default -> null;
        };
    }
}
