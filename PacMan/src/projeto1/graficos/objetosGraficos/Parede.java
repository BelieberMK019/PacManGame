/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.objetosGraficos;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.ImagePattern;
import projeto1.engine.uteis.Ponto2f;

/**
 *
 * @author Vitor Rinaldini
 */
public class Parede extends Ponto2f implements ElementosMapa{
    
    private TexturasGraficas textura;
    
    private boolean cima = false;
    private boolean esquerda = false;
    private boolean direita = false;
    private boolean baixo = false;
    
    private float raio =1f;
    
    private boolean cantoCimaEsq = false;
    private boolean cantoCimaDir = false;
    private boolean cantoBaixoDir = false;
    private boolean cantoBaixoEsq = false;
    
    public Parede(TexturasGraficas textura, float x, float y, float raio) {
        super(x, y);
        this.textura = textura;
        this.raio = raio;
    
    }
    
    public void setBounds(boolean cima, boolean esquerda, boolean baixo, boolean direita) {  
        this.baixo = baixo;
        this.cima = cima;
        this.esquerda = esquerda;
        this.direita = direita;
    }
    
    public void setCantos(boolean cantoCimaEsq, boolean cantoCimaDir, boolean cantoBaixoDir, boolean cantoBaixoEsq) {  
        this.cantoCimaEsq = cantoCimaEsq;
        this.cantoCimaDir = cantoCimaDir;
        this.cantoBaixoDir = cantoBaixoDir;
        this.cantoBaixoEsq = cantoBaixoEsq;
    }
    
    @Override
    public void draw(GraphicsContext g) {
        
        ImagePattern materialOut = textura.getChaoMaterial();
        ImagePattern materialBounds = textura.getParedeMaterialBounds();
        ImagePattern materialMiddle = textura.getParedeMaterialMiddle();
        
        float x = getX();
        float y = getY();
        
        float lado = (float) (0.7*raio);
        float sobra = (float) raio - lado;
        
        g.setFill(materialOut);
        g.fillRect(x-raio, y-raio, 2*raio, 2*raio);
        
        g.setFill(materialBounds);
        
        g.save();
        g.beginPath();
        
        if(esquerda && cima && cantoCimaEsq) {
            g.moveTo(x-lado-sobra, y-lado-sobra);
        } else {
            g.moveTo(x-lado, y-lado);
        }

        if(esquerda) {
            g.lineTo(x-lado-sobra, y-lado);
            g.lineTo(x-lado-sobra, y+lado);
        }
        if(esquerda && baixo && cantoBaixoEsq) {
            g.lineTo(x-lado-sobra, y+lado+sobra);
        } else {
            g.lineTo(x -lado, y+lado);
        }
        
        if(baixo) {
            g.lineTo(x-lado, y+lado+sobra);
            g.lineTo(x+lado, y+lado+sobra);
        }
        
        if(direita && baixo && cantoBaixoDir) {
            g.lineTo(x+lado+sobra, y+lado+sobra);
        } else {
            g.lineTo(x+lado, y+lado);
        }
        
        if(direita) {
            g.lineTo(x+lado+sobra, y+lado);
            g.lineTo(x+lado+sobra, y-lado);
        }
        
        if(direita && cima && cantoCimaDir) {
            g.lineTo(x+lado+sobra, y-lado-sobra);
        } else {
            g.lineTo(x+lado, y-lado);
        }
        
        if(cima) {
            g.lineTo(x+lado, y-lado-sobra);
            g.lineTo(x-lado, y-lado-sobra);
        }
        g.closePath();
        
        g.fill();
        
        g.restore();
        
        g.setFill(materialMiddle);
        
        float ladoMiddle = (float) (0.5*lado);
        
        g.save();
        g.beginPath();
        
        if(esquerda && cima && cantoCimaEsq) {
            g.moveTo(x-lado-sobra, y-lado-sobra);
        } else {
            g.moveTo(x-ladoMiddle, y-ladoMiddle);
        }
      
        if(esquerda) {
            g.lineTo(x-lado-sobra, y-ladoMiddle);
            g.lineTo(x-lado-sobra, y+ladoMiddle);
        }
        
        if(esquerda && baixo && cantoBaixoEsq) {
            g.lineTo(x-lado-sobra, y+lado+sobra);
        } else {
            g.lineTo(x-ladoMiddle, y+ladoMiddle);
        }
        
        if(baixo) {
            g.lineTo(x-ladoMiddle, y+lado+sobra);
            g.lineTo(x+ladoMiddle, y+lado+sobra);
        }
        
        if(direita && baixo && cantoBaixoDir) {
            g.lineTo(x+lado+sobra, y+lado+sobra);
        } else {
            g.lineTo(x+ladoMiddle, y+ladoMiddle);
        }
        
        if(direita) {
            g.lineTo(x+lado+sobra, y+ladoMiddle);
            g.lineTo(x+lado+sobra, y-ladoMiddle);
        }
        
        if(direita && cima && cantoCimaDir) {
            g.lineTo(x+lado+sobra, y-lado-sobra);
        } else {
            g.lineTo(x+ladoMiddle, y-ladoMiddle);
        }
        
        if(cima) {
            g.lineTo(x+ladoMiddle, y-lado-sobra);
            g.lineTo(x-ladoMiddle, y-lado-sobra);
        }
        g.closePath();
        
        g.fill();
        
        g.restore();
    }

    @Override
    public boolean isFinish() {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        
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
