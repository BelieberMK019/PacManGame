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
public class NewGame extends Opcao{
    
    GameLoop loop;
    public NewGame(GameLoop game) {
        super("Novo jogo");
        this.loop = game;
    }

    @Override
    public void acao() {
        loop.mudarToLoad();
        loop.getGame().resetPlayer();
        loop.createAndLoadFase(loop.getGame().getPlayer(), 1, loop.getGame().getGraficos());
    }
}
