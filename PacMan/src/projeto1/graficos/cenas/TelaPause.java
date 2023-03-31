/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projeto1.engine.InputKeyController;
import projeto1.engine.Tabuleiro;
import projeto1.engine.uteis.Ponto2f;
import projeto1.graficos.GameLoop;
import projeto1.graficos.cenas.menu.Menu;
import projeto1.graficos.cenas.menu.OpcaoContinue;
import projeto1.graficos.cenas.menu.VoltarMenu;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class TelaPause extends Ponto2f implements Animacao{
    private float raio = 1f;
    private InputKeyController teclado;
    private Menu menu;
    
    float width;
    float height;
    
    Tabuleiro tab;
    public TelaPause(float xCenter, float yCenter, InputKeyController teclado, Tabuleiro tabuleiro, Fase fase, GameLoop loop) {
        super(xCenter, yCenter);
        this.teclado = teclado;
        this.tab = tabuleiro;
        menu = new Menu(xCenter, yCenter, teclado);
        
        menu.addOption(new OpcaoContinue(fase));
        menu.addOption(new VoltarMenu(loop));

        prepare();
    }
    
    public void prepare() {
        this.width = 30*tab.getMapa().getRaio();
        this.height = 7*tab.getMapa().getRaio();
        menu.prepare(this.width, this.height, 15);
    }

    @Override
    public boolean isFinish() {
        return false;
    }
    
    public void reusar() {
        menu.reusar();
    }

    @Override
    public void update(double deltaTime) {
        menu.update(deltaTime);
    }

    @Override
    public void draw(GraphicsContext g) {
        float xCenter = getX();
        float yCenter = getY();
        float raioBorda = tab.getMapa().getRaio();
        
        g.setFill(Color.BLACK);
        g.setGlobalAlpha(0.6);
        
        g.fillRoundRect(xCenter-width/2, yCenter-height-tab.getMapa().getRaio(), width, height, raioBorda, raioBorda);
        
        g.setGlobalAlpha(1.0);
        g.setLineWidth(4f);
        g.setStroke(Color.BLACK);
        
        g.strokeRoundRect(xCenter-width/2, yCenter-height-tab.getMapa().getRaio(), width, height, raioBorda, raioBorda);
        
        g.setLineWidth(0.5f);
        g.setStroke(Color.WHITE);
        
        g.strokeRoundRect(xCenter-width/2, yCenter-height-tab.getMapa().getRaio(), width, height, raioBorda, raioBorda);
        
        g.setLineWidth(1f);
        
        menu.draw(g);
    }

        @Override
    public void refresh(float raio) {
        this.raio = raio;
    }

    @Override
    public void setRealX(float x) {
        setX(x);
    }

    @Override
    public void setRealY(float y) {
       setY(y);
    }

    @Override
    public float getRealX() {
        return getX();
    }

    @Override
    public float getRealY() {
        return getY();
    }
    
}
