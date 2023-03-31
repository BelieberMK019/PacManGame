/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import projeto1.engine.InputKeyController;
import projeto1.entidades.PacMan;
import projeto1.entidades.elementos.PacDots;
import projeto1.entidades.elementos.frutinhas.Cereja;
import projeto1.entidades.elementos.frutinhas.Laranja;
import projeto1.entidades.elementos.frutinhas.Morango;
import projeto1.entidades.fantasmas.Blinky;
import projeto1.entidades.fantasmas.Clyde;
import projeto1.entidades.fantasmas.Fantasma;
import projeto1.entidades.fantasmas.Inky;
import projeto1.entidades.fantasmas.Pinky;
import projeto1.graficos.objetosGraficos.Animacao;
import projeto1.graficos.Pincel;

/**
 *
 * @author Vitor Rinaldini
 */
public class LoadingCena extends Cena{
    
    ArrayList<Animacao> animes;
    
    public LoadingCena(InputKeyController teclado) {
        super(teclado);
        animes = new ArrayList();
    }
    
    @Override
    public void render(Pincel p) {
       for(Animacao a: animes) {
           p.save();           
           p.drawAnime(a);
           p.restore();
       }
       
       p.save();
       GraphicsContext g = p.getGrapics();
       
       g.setStroke(Color.WHITE);
       g.setFill(Color.BLUE);
       
       Text text = new Text("Carregando...");
       text.setFont(new Font("Verdana", 40));
       g.setFont(text.getFont());
       
       g.fillText(text.getText(), 330-text.getLayoutBounds().getWidth()/2, 475);
       g.strokeText(text.getText(), 330-text.getLayoutBounds().getWidth()/2, 475);
       p.restore();
       
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void update(double deltaTime) {
        for(Animacao a: animes) {
            a.update(deltaTime);
        }
    } 

    @Override
    public void prepare() {
        
        int x0 = 0;
        int n = 15;
        for(int i = 0; i < n; i++) {
            PacDots p = new PacDots(x0+i*50, 400, null) {
                @Override
                public void update(double deltaTime) {
                    setX((float) (getX()-deltaTime*20f));
                    if(getX() < -50) setX(x0+(n-1)*50);
                }
                
                @Override
                public float getRealX() {
                    return getX();
                }

                @Override
                public float getRealY() {
                    return getY();
                }

            };
            
            animes.add((Animacao) p);
            
        }
        
        float y = 300;
        animes.add(new PacMan(120, y, null, null, null));
        animes.add(new Inky(220, y, null, 0));
        animes.add(new Pinky(320, y, null, 0));
        animes.add(new Blinky(420, y, null, 0,0));
        animes.add(new Clyde(520, y, null, 0));
        
       
        y = 180;
        animes.add(new Cereja(180, y, null) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

        });
        
        animes.add(new Laranja(310, y+12, null) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

        });
        
        animes.add(new Morango(420, y+15f, null) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

        });
        
        for(Animacao a: animes) {
            a.refresh(50);
            if(a instanceof Fantasma) ((Fantasma) a).setDirecao(Fantasma.DIREITA);
        }
       
    }

    @Override
    public void finalize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
