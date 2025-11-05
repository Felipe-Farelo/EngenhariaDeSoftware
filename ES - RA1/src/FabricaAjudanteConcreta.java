public class FabricaAjudanteConcreta implements FabricaAjudante {
    @Override
    public Ajudante criarAjudante(char tipo) {
        return switch (tipo) {
            case '^' -> new Duende();
            case '&' -> new Anao();
            default -> null;
        };
    }
}
