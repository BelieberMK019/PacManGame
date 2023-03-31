/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.uteis;

/**
 *
 * @author Vitor Rinaldini
 */
public class Ponto2f {
    /** Classe padrão para ser usada como localização
     * @author Vítor Rinaldini
     */
    private float x;
    private float y;
    
    /** Construtor vazio. 
     * Seta o ponto para a origem
     * (x, y) -> (0, 0)
     * @author Vítor Rinaldini
     */
    public Ponto2f() {
        x = 0.0f;
        y = 0.0f;
    }
    
    /** Construtor padrão.
     * Cria um ponto a partir de uma coordenada dada
     * @param x localização x
     * @param y localização y
     */
    public Ponto2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /** Retorna o valor de X
     * 
     * @return valor da coordenada X
     */
    public float getX() {
        return x;
    }
    
    /**Retorna o valor de Y
     * 
     * @return valor da coordenada Y
     */
    public float getY() {
        return y;
    }
    
    /** Muda o valor de X
     * 
     * @param x valor da coordenada x
     */
    public void setX(float x) {
        this.x = x;
    }
    
    /** Muda o valor de Y
     * 
     * @param y valor da coordenada Y
     */
    public void setY(float y) {
        this.y = y;
    }
    
    /** Calcula a distância euclidiana entre 'this' e o ponto qualquer
     * 
     * @param ponto Ponto qualquer;
     * @return float distancia
     */
    public float distancia(Ponto2f ponto) {
        return (float) (Math.sqrt(Math.pow(this.x-ponto.x, 2) + Math.pow(this.y-ponto.y, 2)));
    }
     
    /**Método que confere o alinhamento de 3 pontos, sendo dois fornecdidos pelo
     * usuário.
     * @param p1
     * @param p2
     * @return TRUE, se alinhado; False, se desalinhado
     */
    public boolean isAlinhado(Ponto2f p1, Ponto2f p2) {
        if(this.x == p1.getX() && this.x == p2.getX()) return true;
        else if(this.x == p1.getX() || this.x == p2.getX()) return false;
        
        float m1 = (this.y-p1.getY())/(this.getX()-p1.getX());
        float m2 = (this.y-p2.getY())/(this.getX()-p2.getX());
        return m1 == m2;
    }
}
