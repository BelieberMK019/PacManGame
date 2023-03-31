/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.menu;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import projeto1.engine.InputKeyController;
import projeto1.engine.uteis.Ponto2f;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Menu extends Ponto2f implements Animacao{

    ArrayList<Opcao> opcoes;
    int selected = 0;
    
    InputKeyController teclado;
    float width;
    float height;
    float tamanhoFont;
    
    public Menu(float x, float y, InputKeyController teclado) {
        super(x, y);
        this.teclado = teclado;
        opcoes = new ArrayList();
        
    }
    
    public void addOption(Opcao o) {
        opcoes.add(o);
    }
    
    public int getSelecedOption() {
        return selected;
    }
    
    public void prepare(float width, float height, float tamanhoFont) {
        reusar();
        
        this.width = width;
        this.height = height;
        this.tamanhoFont = tamanhoFont;
        
        float dy = height/opcoes.size();
        float y = getY()-(opcoes.size()+1f)/2f*dy;
        
        
        for(Opcao o: opcoes) {
            o.setBounds(getX(), y, width, dy);
            y += dy;
        }
    }
    
    public void reusar() {
        if(opcoes.size() > 0) opcoes.get(selected).setSelected(false);
        selected = 0;
        teclado.reset();
    }
    
    private void descer() {
        teclado.descer = false;
        if(opcoes.size() > selected+1) selected++;
    }
    
    private void subir() {
        teclado.subir = false;
        if(selected > 0) selected--;
    }
    
    private float raio = 1f;
    
    @Override
    public boolean isFinish() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(double deltaTime) {
        if(opcoes.size() <= 0) return;
        opcoes.get(selected).setSelected(false);
        
        if(teclado.subir) subir();
        else if(teclado.descer) descer();
        
        opcoes.get(selected).setSelected(true);
    
        if(teclado.isCima()) {
            subir();
        } else if(teclado.isBaixo()) {
            descer();
        } else if(teclado.isGo()) {
            opcoes.get(selected).acao();
        }
    
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setLineWidth(1f);
        g.setFill(Color.LIGHTBLUE);
        g.setStroke(Color.BLACK);
        Font f = new Font("Arial", 2*tamanhoFont);
        g.setFont(f);
        for(Opcao o: opcoes) {
            g.save();
            o.draw(g);
            g.restore();
        }
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
