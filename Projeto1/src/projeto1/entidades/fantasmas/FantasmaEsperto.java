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
public abstract class FantasmaEsperto extends FantasmaAleatorio{

    /**
     * Definição de um fantasma que utiliza um método de busca esperto.
     * Busca largura por distancia.
     * @author Vitor Rinaldini
     * @param x
     * @param y
     * @param tab
     * @param cor
     * @param turnosPreso 
     */
    public FantasmaEsperto(float x, float y, Tabuleiro tab, char cor, int turnosPreso) {
        super(x, y, tab, cor, turnosPreso);
    }

    /**
     * Implementação do método de busca.
     */
    private void buscaEsperta() {
        Grafo g = getTabuleiro().getGrafo();
        Caminho caminho = g.fazerBuscaLargDist(this, getTabuleiro().getJogador());
        if(caminho != null && caminho.size() > 1) {
            setCaminho(caminho);
            setDest(caminho.get(1).getCopy());
        }
        else {
            setDest(null);
        }
    }
    
    /**
     * Método que seta o destino de acordo com o comportamento do fantasma e do jogador.
     */
    @Override
    public void buscar() {
        if(!isPresa()) buscaEsperta();
        else super.buscar();
    }
    
}
