/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.fantasmas;

import projeto1.engine.Tabuleiro;
import projeto1.engine.grafo.Caminho;
import projeto1.engine.grafo.Grafo;

/**
 *
 * @author Vitor Rinaldini
 */
public class Blinky extends FantasmaEsperto{
    /**
     * Classe responsável pela implementação do Blinky.
     * OBS: Esse fantasma tem alteração na sua velocidade.
     * @author Vitor Rinaldini
     */
    private float velocidade_atual;
    private static float fatorVelocidade = 0.000001f;
    public Blinky(float x, float y, Tabuleiro tab, int turnosPreso) {
        super(x, y, tab, 'B', turnosPreso);
        velocidade_atual = VELOCIDADE_NORMAL;
    }
    
    public void updateVelocidade(int pacDots) {
        velocidade_atual = VELOCIDADE_NORMAL+fatorVelocidade*pacDots;
        setVelocidadeToNormal();
    }

    @Override
    public void setVelocidadeToNormal() {
        setVelocidade(velocidade_atual);
    }

}
