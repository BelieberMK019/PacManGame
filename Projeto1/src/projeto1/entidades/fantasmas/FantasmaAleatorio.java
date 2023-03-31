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
public abstract class FantasmaAleatorio extends Fantasma{

    /**
     * Definição de um fantasma comum (que realiza uma busca aleatoria);
     * @author Vitor Rinaldini
     * @param x
     * @param y
     * @param tab
     * @param cor
     * @param turnosPreso 
     */
    public FantasmaAleatorio(float x, float y, Tabuleiro tab, char cor, int turnosPreso) {
        super(x, y, tab, cor, turnosPreso);
    }
    
    /**
     * Fazer Busca Aleatória.
     */
    private void buscaAleatoria() {
        Grafo g = getTabuleiro().getGrafo();
        Caminho caminho = g.fazerBuscaAleatoria(this);
        if(caminho != null && caminho.size() > 1)setDest(caminho.get(1).getCopy());
        else setDest(null);
    }

    @Override
    public void buscar() {
        buscaAleatoria();
    }
    
}
