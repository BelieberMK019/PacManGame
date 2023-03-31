/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.menu;

/**
 *
 * @author Vitor Rinaldini
 */
public class OpcaoExit extends Opcao{
    
    public OpcaoExit() {
        super("Sair do jogo");
    }

    @Override
    public void acao() {
        System.exit(0);
    }
    
    
    
}
