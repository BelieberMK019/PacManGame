/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.grafo;

import projeto1.engine.uteis.Ponto2f;
import java.util.ArrayList;

/**
 *
 * @author Vitor Rinaldini
 */
public class Node extends Ponto2f{
    /**
     * Node é a arquitetura mínima do grafo.
     * @author Vitor Rinaldini
     */
    private final ArrayList<Node> nodes;
    
    /**
     * Construtor
     * @param x
     * @param y 
     */
    public Node(float x, float y) {
        super(x, y);
        nodes = new ArrayList();
    }
    
    /**
     * Get Copia do node.
     * @return 
     */
    public Node getCopy() {
        Node copia = new Node(getX(), getY());
        return copia;
    }
    
    /**
     * Get quantidade de conexoes
     * @return 
     */
    public int getQuantidadeConexoes() {
        return nodes.size();
    }
    
    /**
     * retorna todos os nodes com que ele faz conexão;
     * @return 
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    /**
     * retorna node de indice i com que ele faz conexão
     * @param i
     * @return 
     */
    public Node getNode(int i) {
        if(i < 0 || i >= nodes.size()) return null;
        return nodes.get(i);
    }
    
    /**
     * confere se ele e um outro node representam uma conexão de borda.
     * @param node
     * @param xMax
     * @param yMax
     * @return 
     */
    public boolean isBorda(Node node, float xMax,float yMax) {
        if ((getX() <= 0f && node.getX() >= xMax - 1) || (node.getX() <= 0f && getX() >= xMax - 1) || (getY() <= 0f && node.getY() >= yMax - 1) || (node.getY() <= 0f && getY() >= yMax - 1)) {
            return true;
        }
        return false;
    }
    
    /**
     * confere se ele está na borda
     * @param xMax
     * @param yMax
     * @return 
     */
    public boolean isBorda(float xMax,float yMax) {
        if (getX() <= 0 || getY() <= 0 || getX() >= xMax-1 ||getY() >= yMax-1) {
            return true;
        }
        return false;
    }
    
    /**
     * confere se ele está conectado com outro node.
     * @param node
     * @return 
     */
    public boolean isConected(Node node) {
        for(int i = 0; i < nodes.size(); i++) {
            if(nodes.get(i).equals(node)) return true;
        }
        return false;
    }
    
    /**
     * confere se a localização desses nodes são iguais.
     * @param node
     * @return 
     */
    public boolean isIgual(Node node) {
        return ((this.getX()) == (node.getX()) && (this.getY()) == (node.getY()));
    }
    
    /**
     * confere se a localizaçao aproximada é igual.
     * @param node
     * @return 
     */
    public boolean isIgualAproximado(Node node) {
        return (Math.round(this.getX()) == Math.round(node.getX()) && Math.round(this.getY()) == Math.round(node.getY()));
    }
    
    /**
     * conecta esse node com outro
     * @param node 
     */
    public void conectar(Node node) {
        if(isConected(node)) return;
        this.nodes.add(node);
        node.nodes.add(this);
    }
    
    /**
     * desconecta dois nodes.
     * @param node 
     */
    public void desconectar(Node node) {
        if(!isConected(node)) return;
        this.nodes.remove(node);
        node.nodes.remove(this);
    }
    
    /**
     * método para debug.
     * imprime o vaor desse node.
     */
    public void print() {
        System.out.println("Node: ("+getX()+"; "+getY()+")");
    }
    
    /**
     * método para debug.
     * Imprime os filhos desse node.
     */
    public void printFilhos() {
        print();
        System.out.println("Filhos:");
        for(Node node: nodes) node.print();
    }
}
