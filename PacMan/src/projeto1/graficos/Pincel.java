/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos;

import projeto1.graficos.objetosGraficos.Animacao;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Vitor Rinaldini
 */
public class Pincel {
    
    private Canvas canvas;
    private GraphicsContext g;
    private Color backgroundColor;
    private int width;
    private int height;
    
    
    public Pincel(Canvas canvas) {
        this.canvas = canvas;
        g = canvas.getGraphicsContext2D();
        reset();
        
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public void reset() {
        this.backgroundColor = Color.BLACK;
        this.width = 800;
        this.height = 600;
    }
    
    public void refresh() {
        this.width = (int) canvas.getWidth();
        this.height = (int) canvas.getHeight();
    }
    
    public void drawAnime(Animacao a) {
        a.draw(g);
    }
    
    public void drawString(String text, int x, int y, Color cor, Font font) {
        g.setStroke(Color.BLACK);
        g.setFill(cor);
        g.setFont(font);
        g.fillText(text, x, y);
        g.strokeText(text, x, y);
    }
    
    public void drawImage(Image image, double x, double y, double width, double height) {
        g.drawImage(image, x, y, width, height);
    }
    
    public void save() {
        g.save();
    }
    
    public void restore() {
        g.restore();
    }
    
    public void tanslate(float x, float y) {
        g.translate(x, y);
    }
    
    public void dispose() {
        g.setFill(backgroundColor);
        g.fillRect(0, 0, width, height);
    }
    
    public GraphicsContext getGrapics() {
        return g;
    }
    
}
