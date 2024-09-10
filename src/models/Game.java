package models;

import enums.Condition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private int dealerWins;
    private int playerWins;

    private int dealerCount;
    private int playerCount;

    private ArrayList<Carta> playerCartas;
    private ArrayList<Carta> dealerCartas;
    private Baralho carteado;

    private Scanner scanner = new Scanner(System.in);

    public Game(){
        dealerWins = 0;
        playerWins = 0;

        dealerCount = 0;
        playerCount = 0;

        playerCartas = new ArrayList<Carta>();
        dealerCartas = new ArrayList<Carta>();
        carteado = new Baralho();
    }

    public void startNewGame(){
        carteado.resetBaralho();
        playerWins = 0;
        dealerWins = 0;
    }

    public void newRound(){
        //Limpando mão anterior
        playerCartas.clear();
        dealerCartas.clear();
        playerCount = 0;
        dealerCount = 0;

        //Caso não haja cartas o suficiente para um novo round
        if(carteado.getLen() < 10) {
            System.out.println("Cartas insuficientes rembaralhando...");
            carteado.resetBaralho();
        }

        /*
        * Entrega-se as cartas do Dealer primeiro,
        * pois o Dealer não pode sair com 21 de cara
        * */
        System.out.println("\nEntregando as cartas do Dealer");
        programSleep(2);
        System.out.println("Primeira carta do Dealer: " + dealerPuxarCarta());
        programSleep(2);
        System.out.println("Dealer recebendo carta escondida");
        dealerPuxarCarta();
        if(dealerCount == 21){
            System.out.println("Dealer saiu com 21, puxando outras cartas");
            programSleep(2);
            newRound();
            return;
        }

        System.out.println("puxando suas duas cartas");
        programSleep(2);
        playerPuxarCarta();
        programSleep(2);
        playerPuxarCarta();
        programSleep(2);
        if(playerCount == 21){
            System.out.println("parabens, voce saiu com 21");
            System.out.println("Vitoria do jogador");
            programSleep(5);
            playerWins++;
        }

        System.out.println("Comecando round");
        PlayerLoop();
    }

    private void playerPuxarCarta(){
        int valor;
        Carta carta = carteado.puxarCarta();
        if(carta.getValue() >= 11){
            valor = 10;
        }else{
            valor = carta.getValue();
        }

        System.out.println("Carta puxada é um " + carta);

        if(isBlackJack(playerCount, playerCartas)){
            if(valor == 10 || valor == 1){
                playerCartas.add(carta);
                playerCount = 21;
            }else{
                playerCartas.add(carta);
                playerCount += valor;
            }
        }else{
            playerCartas.add(carta);
            playerCount += valor;
        }
    }

    private Carta dealerPuxarCarta(){
        int valor;
        Carta carta = carteado.puxarCarta();
        if(carta.getValue() >= 11){
            valor = 10;
        }else{
            valor = carta.getValue();
        }

        if(isBlackJack(dealerCount, dealerCartas)){
            if(valor == 10 || valor == 1){
                dealerCartas.add(carta);
                dealerCount = 21;
            }else{
                dealerCartas.add(carta);
                dealerCount += valor;
            }
        }else{
            dealerCartas.add(carta);
            dealerCount += valor;
        }

        return carta;
    }

    private boolean isBlackJack(int count, ArrayList<Carta> cartas){
        return cartas.size() == 1 && ( count == 10 || count == 1);
    }

    private void PlayerLoop(){
        int choice = -1;
        Condition gameCondition;


        while (choice != 0) {
            System.out.println("\nSoma total: " + playerCount);
            System.out.println("Suas cartas são: ");
            for (Carta carta : playerCartas) {
                System.out.println(" - " + carta);
            }
            System.out.println("\nDigite 1 para puxar mais uma, ou 0 para parar");
            choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                playerPuxarCarta();
                gameCondition = checkEndRoundConditions(playerCount);
                if(gameCondition == Condition.Estouro){
                    System.out.println("Que pena voce estourou com " + playerCount);
                    System.out.println("Vitoria do Dealer");
                    programSleep(5);
                    dealerWins++;
                    return;
                }else if(gameCondition == Condition.VinteEUm){
                    System.out.println("Parabens, voce fez um 21");
                    System.out.println("Vitoria do Jogador");
                    programSleep(5);
                    playerWins++;
                    return;
                }else {
                    System.out.println("Jogo continua");
                }
            }else if (choice == 0) {
                System.out.println("Comecando vez do Dealer");
                programSleep(2);
                DealerLoop();
            }else {
                System.out.println("opcao invalida!");
            }
        }
    }

    private void DealerLoop(){
        Condition gameCondition;
        System.out.println("Mostrando a carta escondida");

        System.out.println("\nSoma do Dealer: " + dealerCount);
        System.out.println("Cartas do dealer: ");
        for (Carta carta : dealerCartas) {
            System.out.println(" - " + carta);
        }

        programSleep(5);
        while (dealerCount <= 16) {
            System.out.println("\nSoma do Dealer: " + dealerCount);
            System.out.println("Cartas do dealer: ");
            for (Carta carta : dealerCartas) {
                System.out.println(" - " + carta);
            }
            programSleep(2);

            System.out.println("Dealer puxou a carta " + dealerPuxarCarta());
            programSleep(2);
            gameCondition = checkEndRoundConditions(dealerCount);
            if(gameCondition == Condition.Estouro){
                System.out.println("Dealer estourou com " + dealerCount);
                System.out.println("Vitoria do Jogador");
                programSleep(5);
                playerWins++;
                return;
            }else if(gameCondition == Condition.VinteEUm){
                System.out.println("Que pena, dealer fez um 21");
                System.out.println("Vitoria do dealer");
                programSleep(5);
                dealerWins++;
                return;
            }else {
                System.out.println("jogo continua");
            }
        }

        System.out.println("Dealer parou");
        programSleep(2);
        contagemFinal();
    }

    private void contagemFinal(){
        if(dealerCount > playerCount){
            System.out.println("Dealer ganhou!");
            programSleep(5);
            dealerWins++;
        } else if (playerCount > dealerCount) {
            System.out.println("Jogador ganhou!");
            programSleep(5);
            playerWins++;
        }else {
            System.out.println("Empate!");
            System.out.println("Sem ganhadores");
            programSleep(5);
        }
    }

    private Condition checkEndRoundConditions(int count){
        if(count == 21){
            return Condition.VinteEUm;
        } else if (count > 21) {
            return Condition.Estouro;
        }

        return Condition.Prosseguir;
    }

    private void programSleep(long seconds){
        try{
            Thread.sleep(Duration.ofSeconds(seconds));
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public int getDealerWins() {
        return dealerWins;
    }

    public int getPlayerWins() {
        return playerWins;
    }
}
