/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.menu;

import projeto1.graficos.GameLoop;

/**
 *
 * @author Vitor Rinaldini
 */
public class VoltarMenu extends Opcao {
    
    GameLoop loop;
    public VoltarMenu(GameLoop game) {
        super("Menu principal");
        this.loop = game;
    }

    @Override
    public void acao() {
        loop.mudarToLoad();
        loop.createAndLoadMenuPrincipal();
    }
    
    
    
}
