/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.uteis;

import projeto1.engine.Tabuleiro;
import projeto1.graficos.cenas.Cena;

/**
 *
 * @author Vitor Rinaldini
 */
public class Loader extends Thread{
    
    private Cena cena = null;
    private Tabuleiro tab = null;
    public Loader(Cena cena, Tabuleiro tab) {
        this.cena = cena;
        this.tab = tab;
    }

    @Override
    public void run() {
        
        long timePast = System.currentTimeMillis();

        if(tab != null) tab.prepare();
        cena.prepare();

        long delta = ((System.currentTimeMillis() - timePast));
        long time = 4000 - delta;
        if (time < 0)
            time = 0;
        
        try {
            System.gc();
            Runtime.getRuntime().gc();
            Thread.sleep(time);
        } catch (Exception ex) {
            System.out.println("Erro de carregamento: "+ ex.getMessage());
        }
        
        
    }
    
    
}
