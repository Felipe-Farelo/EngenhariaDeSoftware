import java.util.Random;

public interface Monstro {

    void aplicarHabilidade();  // habilidade especial do monstro
    String getDescricao();     // descrição do monstro

    default void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Mostrar status completo
    default void mostrarStatus(Heroi heroi) {
        System.out.println("== STATUS ATUAL ==");
        System.out.println("Herói -> Vida: " + heroi.getVida() + " | Ataque: " + heroi.getAtaque() + " | Defesa: " + heroi.getDefesa());
        System.out.println(getClass().getSimpleName() + " -> Vida: " + ((Entidade)this).getVida() +
                " | Ataque: " + ((Entidade)this).getAtaque() +
                " | Defesa: " + ((Entidade)this).getDefesa());
        System.out.println("====================");
    }

    default void batalha(Heroi heroi) {
        Random rand = new Random();

        limparTela();
        System.out.println(getDescricao());
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        aplicarHabilidade();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        while (heroi.getVida() > 0 && ((Entidade)this).getVida() > 0) {

            // Turno do Herói
            limparTela();
            System.out.println("== Turno do Herói ==");
            if (rand.nextDouble() <= 0.7) { // 70% de chance de acertar
                int ataqueBruto = heroi.getAtaque();
                System.out.println("Você atacou com força total de " + ataqueBruto + "!");
                ((Entidade)this).receberDano(ataqueBruto);

            } else {
                System.out.println("Você errou o ataque!");
            }
            mostrarStatus(heroi);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            if (((Entidade)this).getVida() <= 0) break;

            // Turno do Monstro
            limparTela();
            System.out.println("== Turno do " + getClass().getSimpleName() + " ==");
            if (rand.nextDouble() <= 0.7) { // 70% de chance de acertar
                int ataqueBruto = ((Entidade)this).getAtaque() - heroi.getDefesa();
                if (ataqueBruto < 0) ataqueBruto = 0;
                heroi.receberDano(ataqueBruto);
                System.out.println(getClass().getSimpleName() + " causou " + ataqueBruto + " de dano em você.");
            } else {
                System.out.println(getClass().getSimpleName() + " errou o ataque!");
            }
            mostrarStatus(heroi);
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

            if (heroi.getVida() <= 0) break;
        }

        if (heroi.getVida() <= 0) {
            System.out.println("Você morreu! Fim de jogo.");
            System.exit(0);
        } else {
            System.out.println(getClass().getSimpleName() + " foi derrotado!");
            try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
