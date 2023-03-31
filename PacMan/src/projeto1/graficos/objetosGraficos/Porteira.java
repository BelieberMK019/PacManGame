/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.objetosGraficos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import projeto1.engine.uteis.Ponto2f;

/**
 *
 * @author Vitor Rinaldini
 */
public class Porteira extends Ponto2f implements ElementosMapa{

    private TexturasGraficas textura;
    private float raio = 1f;
    
    public Porteira(TexturasGraficas textura, float x, float y, float raio) {
        super(x, y);
        this.textura = textura;
        
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
        
        float x = getX();
        float y = getY();
        
        g.setStroke(Color.BLACK);
        
        g.setFill(textura.getChaoMaterial());
        g.fillRect(x-raio, y-raio, 2*raio, 2*raio);
        
        g.setFill(textura.getPorteiraMaterial());
        g.fillRect(x-raio, y, 2*raio, 0.3*raio);
        g.strokeRect(x-raio, y, 2*raio, 0.3*raio);     
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
