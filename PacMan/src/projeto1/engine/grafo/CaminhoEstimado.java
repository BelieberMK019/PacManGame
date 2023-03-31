/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.grafo;

/**
 *
 * @author Vitor Rinaldini
 */
public class CaminhoEstimado extends Caminho implements Comparable<CaminhoEstimado>{

    /**
     * Classe foi utilizada para a implementação do algoritimo de busca A*.
     * Devido a existencia de conexão entre as bordas, esse método não foi eficiente.
     * @author Vitor Rinaldini
     */
    private final Node destino;
    
    /**
     * Constutor
     * @param destino necessário para o calculo da estimativa.
     * @param xMax
     * @param yMax 
     */
    public CaminhoEstimado(Node destino, float xMax, float yMax) {
        super(xMax, yMax);
        this.destino = destino;
    }
    
    /**
     * Get distancia euclidiana do ultimo node adicionado até o destino.
     * @return 
     */
    public float getDistEstimadaRestante() {
        return getLastNode().distancia(destino);
    }
    
    /**
     * Get a soma da distancia estimada com a distancia percorrida.
     * @return 
     */
    public float getEstimativaTotal() {
        float distPercorrida = getDistanciaPercorrida();
        float distEstimadaRestante = getDistEstimadaRestante();
        return distPercorrida+distEstimadaRestante;
    }

    /**
     * Método responsavél por comparar e assim, tornar possível uma ordenação.
     * @param o
     * @return 
     */
    @Override
    public int compareTo(CaminhoEstimado o) {
        float thisEstimativa = getEstimativaTotal();
        float anotherEstimativa = o.getEstimativaTotal();
        if(thisEstimativa > anotherEstimativa) return 1;
        if(thisEstimativa < anotherEstimativa) return -1;
        else return 0;
    }
 
    /**
     * Retorna uma copia do caminho estimado
     * @return 
     */
    public CaminhoEstimado getCopia() {
        CaminhoEstimado copia = new CaminhoEstimado(destino, getxMax(), getyMax());
        for(Node node: this) {
            copia.add(node);
        }
        return copia;
    }
    
}
