package models;

import enums.Naipe;
import enums.Valor;

public class Carta {
    private Valor valor;
    private Naipe naipe;

    public Carta(Valor valor, Naipe naipe){
        this.valor = valor;
        this.naipe = naipe;
    }

    @Override
    public String toString() {
        return valor + " de " + naipe;
    }

    public int getValue(){
        return valor.ordinal() + 1;
    }

    public Naipe getNaipe(){
        return naipe;
    }
}
