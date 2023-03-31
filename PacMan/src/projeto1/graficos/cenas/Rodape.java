/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import projeto1.engine.Tabuleiro;
import projeto1.engine.uteis.Ponto2f;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class Rodape extends Ponto2f implements Animacao {
   
    private float raio = 1f;
    private Tabuleiro tab;
    public Rodape(float x, Tabuleiro tab) {
        super(x, (tab.getTabuleiroMontado().getY())+tab.getMapa().getHeight()+(3*tab.getTabuleiroMontado().getY()/2));
        raio = tab.getTabuleiroMontado().getY()/2;
        this.tab = tab;
    }
    
    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void draw(GraphicsContext g) {
        g.save();
        float x = getX();
        float y = getY();
        float width = tab.getMapa().getWidth();
        float height = tab.getMapa().getHeight();
        float tam = raio;
        g.setStroke(Color.BLUE);
        g.setFill(Color.WHITE);
        
        Text textNivel = new Text("");
        textNivel.setFont(new Font("Arial", tam));
        g.setFont(textNivel.getFont());
        
        float yText = (7*y/8);
        
        float scoreTotal = tab.getPlayer().getScoreAcumulado()+tab.getPlayer().getScore();
        textNivel.setText("Score Total: "+scoreTotal);
        g.fillText(textNivel.getText(), x-width/2, yText);
        g.strokeText(textNivel.getText(), x-width/2, yText);

        float scoreProx =tab.getPlayer().SCOREVIDA-tab.getPlayer().getScore();
        textNivel.setText("Score próxima vida: "+scoreProx);
        g.fillText(textNivel.getText(), x+width/2-textNivel.getLayoutBounds().getWidth(), yText);
        g.strokeText(textNivel.getText(), x+width/2-textNivel.getLayoutBounds().getWidth(), yText);
        
        
        g.restore();
        
    }
    
    public void drawPacMan(GraphicsContext g, float x, float y, float raio) {
        float angle = 50f;
        float angleInicial = angle/2f;
        
        g.setFill(Color.YELLOW);
        g.fillArc(x-raio, y-raio, 2*raio, 2*raio, angleInicial, 360-angle, ArcType.ROUND);
        g.setStroke(Color.BLACK);
        g.strokeArc(x-raio, y-raio, 2*raio, 2*raio, angleInicial, 360-angle, ArcType.ROUND);
        
        float distOlho = 0.7f*raio;
        float raioOlho = 0.2f*raio;
        
        g.setFill(Color.BLACK);
        g.fillOval(x+distOlho*Math.cos(45*Math.PI/180)-raioOlho, y-distOlho*Math.sin(45*Math.PI/180)-raioOlho, raioOlho, raioOlho);
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
