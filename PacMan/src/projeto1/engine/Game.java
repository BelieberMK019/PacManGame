/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine;

import projeto1.entidades.Player;
import projeto1.graficos.objetosGraficos.TexturasGraficas;

/**
 *
 * @author Vitor Rinaldini
 */
public class Game {
    /**Classe responsável pelo gerenciamento do game: Instância do jogador, uma nova fase, etc.
     * @author Vitor Rinaldini
     */
    
    private final Player player;
    private final TexturasGraficas graficos;
    
    /**
     * Construtor vazio. Detalhe, o nome do jogador por enquanto está sendo atribuido no momento de compilação.
     */
    public Game() {
        player = new Player("Beta-Tester");
        resetPlayer();
        
        graficos = new TexturasGraficas();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void resetPlayer() {
        player.setVidas(6);
        player.resetScore();
    }

    public TexturasGraficas getGraficos() {
        return graficos;
    }
    
}
