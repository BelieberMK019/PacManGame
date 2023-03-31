/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.fantasmas;

import projeto1.engine.Tabuleiro;

/**
 *
 * @author Vitor Rinaldini
 */
public class Inky extends FantasmaAleatorio{

    /**
     * Classe responsável pela implementação do Inky
     * @author Vitor Rinaldini
     * @param x
     * @param y
     * @param tab
     * @param turnosPreso 
     */
    public Inky(float x, float y, Tabuleiro tab, int turnosPreso) {
        super(x, y, tab, 'I', turnosPreso);
    }

    

}
