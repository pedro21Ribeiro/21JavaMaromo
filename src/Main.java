import models.Game;

import java.util.Scanner;

public class Main {
    private Game game = new Game();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = -1;
        Main main = new Main();

        while (choice != 9) {
            System.out.println("Placar\n Dealer: " + main.game.getDealerWins() + " | Player: " + main.game.getPlayerWins());
            menu();
            choice = Integer.parseInt(main.scanner.nextLine());
            switch (choice) {
                case 1:
                   main.game.newRound();
                   break;
                case 2:
                    main.game.startNewGame();
                    break;
                case 9:
                    System.out.println("Saindo do programa");
                    break;
                default:
                    System.out.println("Opcao invalida");
            }
        }
    }

    private static void menu(){
        System.out.println("----====Menu====----");
        System.out.println("1 - Jogar novo round");
        System.out.println("2 - Comecar novo jogo");
        System.out.println("9 - Sair");
        System.out.print("Digite sua opcao: ");
    }
}
