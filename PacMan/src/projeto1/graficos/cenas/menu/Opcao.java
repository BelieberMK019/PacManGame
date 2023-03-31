/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import projeto1.engine.uteis.Ponto2f;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Opcao extends Ponto2f implements Animacao {
    
    private float raio = 1f;
    private String text;
    
    private float width;
    private float height;
    
    private boolean selected = false;
    
    public Opcao(String text) {
        this.text = text;
    }
    
    @Override
    public void refresh(float raio) {
        this.raio = raio;
    }
    
    public void setBounds(float x, float y, float width, float height) {
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
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

    @Override
    public boolean isFinish() {
        return false;
    }
    
    public void acao() {
        return;
    }

    @Override
    public void update(double deltaTime) {
        return;
    }

    @Override
    public void draw(GraphicsContext g) {
        float x = getX();
        float y = getY();
        
        Text text = new Text(this.text);
        text.setFont(g.getFont());
        
        if(selected) {
            
           g.setStroke(Color.BLACK);
           g.setFill(Color.RED);
           
           
           float altura = 12f;
           float angle = (float) (10*Math.PI/180f);
           g.beginPath();
           g.moveTo(x-text.getLayoutBounds().getWidth()/2f-3f, y-text.getLayoutBounds().getHeight()/3);
           g.lineTo(x-text.getLayoutBounds().getWidth()/2f-3f-altura, y-text.getLayoutBounds().getHeight()/3-altura/Math.cos(angle));
           g.lineTo(x-text.getLayoutBounds().getWidth()/2f-3f-altura, y-text.getLayoutBounds().getHeight()/3+altura/Math.cos(angle));
           g.closePath();
           g.fill();
           g.stroke();
           
           g.setStroke(Color.WHITE);
        }
        
        g.fillText(text.getText(), x-text.getLayoutBounds().getWidth()/2, y);
        g.strokeText(text.getText(), x-text.getLayoutBounds().getWidth()/2, y);
    }
    
}
