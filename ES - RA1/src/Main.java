public class Main {
    public static void main(String[] args) throws InterruptedException {
        Heroi heroi = new Heroi(5, 0, 50);

        // instancia fábricas concretas para criação de monstros, ajudantes e itens
        FabricaMonstro fabricaMonstro = new FabricaMonstroConcreta();
        FabricaAjudante fabricaAjudante = new FabricaAjudanteConcreta();
        FabricaItem fabricaItem = new FabricaItemConcreta();

        // cria o mapa carregando do arquivo texto e passa as fábricas para manipulação dos objetos no jogo
        Map mapa = new Map("Mapa.txt", 17, 21, heroi, fabricaMonstro, fabricaAjudante, fabricaItem);

        // loop principal para movimentar o herói pelo mapa até acabar as possibilidades
        mapa.imprimeMapa();
        while (true) {
            Thread.sleep(250); // delay para visualização
            if (!mapa.moveHeroAutomatic()) {
                System.out.println("O herói não pode se mover!");
                break;
            }
            mapa.imprimeMapa();
        }
    }
}
