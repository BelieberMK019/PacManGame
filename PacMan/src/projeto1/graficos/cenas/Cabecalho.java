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
public class Cabecalho extends Ponto2f implements Animacao {

    private float time = 0f;
    private void zerarTime() {
        time = 0;
    }
    
    private void addTime(float delta) {
        this.time += delta;
    }
    
    private int getMinutes() {
        return (int) (time / 60);
    }
    
    private int getSeconds() {
        return (int) (time % 60);
    }
    
    private String getTime() {
        return (getMinutes()+":"+String.format("%02d",getSeconds()));
    }
    
    
    private float raio = 1f;
    private Tabuleiro tab;
    public Cabecalho(float x, Tabuleiro tab) {
        super(x, (tab.getTabuleiroMontado().getY())/2);
        this.tab = tab;
        zerarTime();
    }
    
    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        if(tab.isPlaying()) addTime((float) deltaTime);
    }

    @Override
    public void draw(GraphicsContext g) {
        g.save();
        float x = getX();
        float y = getY();
        float width = tab.getMapa().getWidth();
        float height = tab.getMapa().getHeight();
        float tam = y;
        g.setStroke(Color.BLUE);
        g.setFill(Color.WHITE);
        
        Text textNivel = new Text("Nível "+tab.getNivel().getNivel());
        textNivel.setFont(new Font("Arial", 2*tam));
        g.setFont(textNivel.getFont());
        float yText = (7*y/4);
        g.fillText(textNivel.getText(), x-width/2, yText);
        g.strokeText(textNivel.getText(), x-width/2, yText);
        
        textNivel.setText(getTime());
        textNivel.setFont(new Font("Arial", tam));
        g.setFont(textNivel.getFont());
        
        g.fillText(textNivel.getText(), x-textNivel.getLayoutBounds().getWidth()/2, yText);
        g.strokeText(textNivel.getText(), x-textNivel.getLayoutBounds().getWidth()/2, yText);
        
        yText /= 2;
        
        textNivel.setText("Time:");
        g.fillText(textNivel.getText(), x-textNivel.getLayoutBounds().getWidth()/2, yText);
        g.strokeText(textNivel.getText(), x-textNivel.getLayoutBounds().getWidth()/2, yText);

        textNivel.setText("Vidas: ");
        textNivel.setFont(new Font("Arial", tam));
        g.setFont(textNivel.getFont());
        
        g.fillText(textNivel.getText(), x+width/2-textNivel.getLayoutBounds().getWidth(), yText);
        g.strokeText(textNivel.getText(), x+width/2-textNivel.getLayoutBounds().getWidth(), yText);
        
        yText *= 2;
        float raio = 9*(yText/4)/10;
        yText *= 9f/10f;
        
        float xPacMan = x+width/2-raio;
        for(int i = 0; i < Math.min(5, tab.getPlayer().getVidas()); i++) {
            drawPacMan(g, xPacMan, yText, raio);
            xPacMan -= 2*raio;
        }
        if(tab.getPlayer().getVidas() > 5) {
            textNivel.setText("+");
            g.setStroke(Color.BLUE);
            g.setFill(Color.WHITE);
            g.fillText("+", xPacMan-textNivel.getLayoutBounds().getWidth()/2, yText+textNivel.getLayoutBounds().getHeight()/4);
            g.strokeText("+", xPacMan-textNivel.getLayoutBounds().getWidth()/2, yText+textNivel.getLayoutBounds().getHeight()/4);
        } else if(tab.getPlayer().getVidas() <= 0) {
            textNivel.setText("---");
            g.setStroke(Color.BLUE);
            g.setFill(Color.WHITE);
            g.fillText(textNivel.getText(), xPacMan-3*textNivel.getLayoutBounds().getWidth()/2, yText+textNivel.getLayoutBounds().getHeight()/4);
            g.strokeText(textNivel.getText(), xPacMan-3*textNivel.getLayoutBounds().getWidth()/2, yText+textNivel.getLayoutBounds().getHeight()/4);
        }
        
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
