public class Main {
    public static void main(String[] args) throws InterruptedException {
        Heroi heroi = new Heroi(5, 2, 50);
        Map mapa = new Map("Mapa.txt" , 17, 21, heroi);

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