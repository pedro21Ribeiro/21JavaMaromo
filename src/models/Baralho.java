package models;

import enums.Naipe;
import enums.Valor;

import java.util.ArrayList;
import java.util.Collections;

public class Baralho {
    private ArrayList<Carta> cartas = new ArrayList<Carta>();

    public Baralho() {
        setCards();
        embaralhar();
    }

    private void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta puxarCarta(){
        return cartas.removeFirst();
    }

    private void setCards(){
        cartas.clear();
        for(Naipe naipe: Naipe.values()){
            for(Valor valor: Valor.values()){
                cartas.add(new Carta(valor, naipe));
            }
        }
    }

    public void resetBaralho(){
        setCards();
        embaralhar();
    }

    public int getLen(){
        return cartas.size();
    }
}
