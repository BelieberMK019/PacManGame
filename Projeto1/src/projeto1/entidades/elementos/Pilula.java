/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.elementos;

import projeto1.engine.Tabuleiro;
import projeto1.entidades.PacMan;

/**
 *
 * @author Vitor Rinaldini
 */
public class Pilula extends Ponto{
    /**
     * Classe responsável pela implementação da Pílula.
     * @author Vitor Rinaldini
     */
    private final Tabuleiro tab;
    public Pilula(float x, float y, Tabuleiro tab) {
        super(x, y, '+');
        this.tab = tab;
        setVALOR(40.0f);
    }
    
}
