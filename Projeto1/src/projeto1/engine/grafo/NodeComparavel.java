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
public class NodeComparavel implements Comparable<NodeComparavel>{
    /**
     * Classe que será utilizada para comparação e, consequentemente, ordenação de nodes.
     * @author Vitor Rinaldini
     */
    
    private final float valor;
    private final Node node;
    
    /**
     * Construtor
     * @param node
     * @param valor 
     */
    public NodeComparavel(Node node, float valor) {
        this.node = node;
        this.valor = valor;
    }
    
    /**
     * Get the node
     * @return 
     */
    public Node getNode() {
        return node;
    }

    /**
     * Implementação do compareTo
     * @param o
     * @return 
     */
    @Override
    public int compareTo(NodeComparavel o) {
        if(this.valor > o.valor) return 1;
        else if(this.valor < o.valor) return -1;
        else return 0;
    }
    
}
