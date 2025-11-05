public class Main {
    public static void main(String[] args) throws InterruptedException {
        Heroi heroi = new Heroi(5, 0, 50);

        FabricaMonstro fabricaMonstro = new FabricaMonstroConcreta();
        FabricaAjudante fabricaAjudante = new FabricaAjudanteConcreta();
        FabricaItem fabricaItem = new FabricaItemConcreta();

        Map mapa = new Map("Mapa.txt", 17, 21, heroi, fabricaMonstro, fabricaAjudante, fabricaItem);

        mapa.imprimeMapa();
        while (true) {
            Thread.sleep(250);
            if (!mapa.moveHeroAutomatic()) {
                System.out.println("O herói não pode se mover!");
                break;
            }
            mapa.imprimeMapa();
        }
    }
}
