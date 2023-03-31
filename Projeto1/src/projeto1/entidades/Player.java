/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.entidades;

/**
 *
 * @author Vitor Rinaldini
 */
public class Player {
    /**
     * Classe Player: responsável por guardar os atributos do jogador.
     * @author Vitor Rinaldini
     */
    private String nome;
    private float scoreAcumulado;
    private float score;
    private int vidas;
    
    /**
     * Construtor
     * @param nome 
     */
    public Player(String nome) {
        this.nome = nome;
        vidas = 3;
        score = 0.0f;
        scoreAcumulado = 0.0f;
    }
    
    /**
     * Aumenta a quantidade de vidas.
     */
    public void addVida() {
        vidas++;
    }
    
    /**
     * diminui a quantidade de vidas.
     */
    public void perderVida() {
        vidas--;
    }
    
    /**
     * Confere se está vivo.
     * @return 
     */
    public boolean isAlive() {
        return (vidas >= 0);
    }
    
    /**
     * Retorna o nome do jogador.
     * @return 
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Adiciona pontos ao jogador.
     * @param pontos 
     */
    public void addPontos(float pontos) {
        this.score += pontos;
        if(score >= 10000) {
            score -= 10000;
            scoreAcumulado += 10000;
            addVida();
        }
    }

    /**
     * Retorna a pontuação total.
     * @return 
     */
    public float getScoreAcumulado() {
        return scoreAcumulado;
    }
    
    /**
     * retorna a pontuação atual, antes de conseguir uma nova vida.
     * @return 
     */
    public float getScore() {
        return score;
    }

    /**
     * retorna a quantidade de vidas restante.
     * @return 
     */
    public int getVidas() {
        return vidas;
    }
    
}
