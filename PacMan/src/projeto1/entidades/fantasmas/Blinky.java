/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades.fantasmas;

import javafx.scene.paint.Color;
import projeto1.engine.Tabuleiro;

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
    private int pacDots = 0;
    private float fatorVelocidade = 0.00001f;
    public Blinky(float x, float y, Tabuleiro tab, int turnosPreso, float fatorVelocidade) {
        super(x, y, tab, Color.RED, turnosPreso, "Blinky");
        this.fatorVelocidade = fatorVelocidade;
        velocidade_atual = VELOCIDADE_NORMAL;
    }
    
    public void updateVelocidade(int pacDots) {
        this.pacDots = pacDots;
        velocidade_atual = VELOCIDADE_NORMAL+fatorVelocidade*pacDots;
    }

    @Override
    public void setVelocidadeToNormal() {
        setVelocidade(velocidade_atual);
    }
    
    @Override
    public float getRealX() {
        return getX();
    }

    @Override
    public float getRealY() {
        return getY();
    }

}
