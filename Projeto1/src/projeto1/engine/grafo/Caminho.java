/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.grafo;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Vitor Rinaldini
 */
public class Caminho extends ArrayList<Node>{
    
    /**
     * Classe responsável por armazenar nodes referentes a um caminho.
     * @author Vitor Rinaldini
     */
    
    private float xMax;
    private float yMax;
    private float dist;
    
    /**
     * Construtor
     * @param xMax X Máximo do mapa;
     * @param yMax Y Máximo do mapa;
     */
    public Caminho(float xMax, float yMax) {
        dist = 0;
        this.xMax = xMax;
        this.yMax = yMax;
    }
    
    /**
     * retorna xMax;
     * @return 
     */
    
    public float getxMax() {
        return xMax;
    }

    /**
     * Retorna yMax;
     * @return 
     */
    public float getyMax() {
        return yMax;
    }

    
    /**
     * adiciona um node e atualiza a distancia.
     * @param e novo node;
     * @return 
     */
    @Override
    public boolean add(Node e) {
        boolean result = super.add(e);
        if(size() > 1) {
            if(get(size()-1).isBorda(get(size()-2), xMax, yMax)) {
                dist += 1.0f;
            } else {
                dist += get(size()-1).distancia(get(size()-2));
            }
        }
        return result;
    }
    
    /**
     * Get o primeiro node (ponto de partida).
     * @return 
     */
    public Node getFirstNode() {
        return get(0);
    }
    
    /**
     * Get o ultimo node (destino).
     * @return 
     */
    public Node getLastNode() {
        return get(size()-1);
    }
    
    /**
     * Get distancia total percorrida por esse caminho.
     * @return 
     */
    public float getDistanciaPercorrida() {
        return dist;
    }
    
    /**
     * Get quantidade de nodes que tem nesse caminho.
     * @return 
     */
    public int getQuantidadeNodes() {
        return size();
    }

    /**
     * Remove possíveis diplicatas presente na constituição desse caminho.
     */
    public void removerDuplicatasSeguidas() {
        for(int i = 0; i < size()-1; i++) {
            if(get(i).isIgual(get(i+1))) {
                Node menosFilhos;
                if(get(i).getQuantidadeConexoes() < get(i+1).getQuantidadeConexoes()) menosFilhos = get(i);
                else menosFilhos = get(i+1);
                remove(menosFilhos);
                i--;
            }
        }
    }
    
    /**
     * Método para debug.
     * Imprimir caminho.
     */
    public void print() {
        System.out.println("Caminho: ");
        for(Node node: this) {
            System.out.println("("+node.getX()+"; "+node.getY()+")  ");
        }
        System.out.println();
    }
    
    
    /**
     * Retorna um caminho com copias de cada node.
     * @return 
     */
    public Caminho getCopiaCaminhoComCopiaNodes() {
        Caminho copia = new Caminho(getxMax(), getyMax());
        for(Node node: this) {
            copia.add(node.getCopy());
        }
        return copia;
    }
    
    /**
     * retorna uma copia do caminho.
     * @return 
     */
    public Caminho getCopiaCaminho() {
        Caminho copia = new Caminho(getxMax(), getyMax());
        for(Node node: this) {
            copia.add(node);
        }
        return copia;
    }
    
}
