/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.niveis;

import projeto1.entidades.elementos.frutinhas.Cereja;
import projeto1.entidades.elementos.frutinhas.Laranja;
import projeto1.entidades.elementos.frutinhas.Morango;

/**
 *
 * @author Vitor Rinaldini
 */
public class NivelN extends Nivel{
    /**
     * Construtor do nível N > 3.
     */
    public NivelN(int n) {
        super(n, ".//mapas//map1.txt", 70, 100, 10, Laranja.class);
        corrigirFruta();
    }
    
    /**
     * Seta  a fruta daquele nível.
     */
    public void corrigirFruta() {
        int resto = getNivel() % 3;
        if(resto == 1) setFruta(Cereja.class);
        else if(resto == 2) setFruta(Morango.class);
        else if(resto == 0) setFruta(Laranja.class);
    }
}
