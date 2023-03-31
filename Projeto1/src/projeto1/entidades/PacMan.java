/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades;

import projeto1.engine.Mapa;
import projeto1.engine.Tabuleiro;
import projeto1.engine.niveis.Nivel;
import projeto1.entidades.fantasmas.Fantasma;

/**
 *
 * @author Vitor Rinaldini
 */
public class PacMan extends Entidade {

    /**
     * Classe responsável pela entidade em que o jogador irá controlar = PacMan.
     * @author Vitor Rinaldini.
     */
    private final Player player;
    
    private final char charImag = 'P';
    private int vezesRestantesComPoder;
    private int fantasmasMortosComPoder;
    
    /**
     * Construtor.
     * @param x
     * @param y
     * @param tabuleiro
     * @param player 
     */
    public PacMan(float x, float y, Tabuleiro tabuleiro, Player player) {
        super(x, y, tabuleiro);
        this.player = player;
        vezesRestantesComPoder = 0;
        fantasmasMortosComPoder = 0;
    }
    
    /**
     * Pegar poder da pilula.
     * @param nivel 
     */
    public void pegarPoder(Nivel nivel) {
        vezesRestantesComPoder = nivel.getTIME_WITH_PODER();
        fantasmasMortosComPoder = 0;
    }
    
    /**
     * Usar poder da pilula.
     */
    public void usarPoder() {
        vezesRestantesComPoder--;
        if(vezesRestantesComPoder <=0) {
            getTabuleiro().resetFantasmas();
            fantasmasMortosComPoder = 0;
        }
    }
    
    /**
     * Confere se ele está com a pilula ativada.
     * @return 
     */
    public boolean isPoder() {
        return vezesRestantesComPoder > 0;
    }
    
    /**
     * Retorna as vezes restantes com a pilula.
     * @return int
     */
    public int getVezesRestantesComPoder() {
        return vezesRestantesComPoder;
    }
    
    /**
     * Adiciona pontuação.
     * @param pontos 
     */
    public void addPontos(float pontos) {
        player.addPontos(pontos);
    }
    
    /**
     * Come um fantasma.
     */
    public void comerFantasma() {
        addPontos((float) (Fantasma.PONTOS_FANTASMA*Math.pow(2, fantasmasMortosComPoder)));
        fantasmasMortosComPoder++;
    }
    
    /**
     * Retorna a pontuação atual.
     * @return 
     */
    public float getPontuacao() {
        return player.getScore();
    }
    
    /**
     * Retorna a quantidade de vidas restantes.
     * @return 
     */
    public int getVidas() {
        return player.getVidas();
    }
    
    /**
     * Retorna a imagem do PacMan pro terminal.
     * @return 
     */
    @Override
    public char getCharImag() {
        return charImag;
    }

    /**
     * Mata o PacMan.
     */
    @Override
    public void morrer() {
        super.morrer(); //To change body of generated methods, choose Tools | Templates.
        player.perderVida();
    }

    @Override
    public void moverX(float dx) {
        super.moverX(dx);
    }

    @Override
    public void moverY(float dy) {
        super.moverY(dy); //To change body of generated methods, choose Tools | Templates.
    }
    
}
