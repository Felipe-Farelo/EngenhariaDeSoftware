public class Main {
    public static void main(String[] args) throws InterruptedException {
        Heroi heroi = new Heroi(10, 5, 30);
        Map mapa = new Map("Mapa.txt" , 17, 21, heroi);

        mapa.imprimeMapa();

        while (true) {
            Thread.sleep(500);
            if (!mapa.moveHeroAutomatic()) {
                System.out.println("O herói não pode se mover!");
                break;
            }
            mapa.imprimeMapa();
        }
    }
}
