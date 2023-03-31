/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.menu;

import projeto1.graficos.cenas.Fase;

/**
 *
 * @author Vitor Rinaldini
 */
public class OpcaoContinue extends Opcao{
    
    private Fase fase;
    public OpcaoContinue(Fase fase) {
        super("Continuar");
        this.fase = fase;
    }

    @Override
    public void acao() {
        fase.pause();
    }
    
    
    
}
