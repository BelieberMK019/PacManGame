/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.cenas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import projeto1.engine.Game;
import projeto1.engine.InputKeyController;
import projeto1.entidades.fantasmas.Blinky;
import projeto1.entidades.fantasmas.Clyde;
import projeto1.entidades.fantasmas.Inky;
import projeto1.entidades.fantasmas.Pinky;
import projeto1.graficos.GameLoop;
import projeto1.graficos.Pincel;
import projeto1.graficos.cenas.menu.Menu;
import projeto1.graficos.cenas.menu.NewGame;
import projeto1.graficos.cenas.menu.OpcaoExit;
import projeto1.graficos.objetosGraficos.Animacao;

/**
 *
 * @author Vitor Rinaldini
 */
public class MenuPrincipal extends Cena{

    private Game game;
    private GameLoop gameloop;
    private Menu menu;
    
    ArrayList<Animacao> animes;
    public MenuPrincipal(InputKeyController teclado, Game game, GameLoop gameloop) {
        super(teclado);
        this.gameloop = gameloop;
        this.game = game;
        
        animes = new ArrayList();
        
        menu = new Menu(320, 450, getTeclado());
        
        menu.addOption(new NewGame(gameloop));
        menu.addOption(new OpcaoExit());
        
    }
    
    Text text;

    @Override
    public void render(Pincel pincel) {

        for(Animacao a : animes) {
            pincel.save();
            a.draw(pincel.getGrapics());
            pincel.restore();
        }
        
        GraphicsContext g = pincel.getGrapics();
        g.setStroke(Color.BLACK);
        g.setFill(Color.YELLOW);
        g.setFont(text.getFont());
        g.fillText(text.getText(), text.getX(), text.getY());
        g.strokeText(text.getText(), text.getX(), text.getY());
        
        g.setFill(Color.WHITE);
        Text meusDados = new Text("Vítor Fernando Rinaldini - 11232305");
        meusDados.setFont(new Font("Verdana", 20));
        g.setFont(meusDados.getFont());
        g.fillText(meusDados.getText(), 450-meusDados.getLayoutBounds().getWidth()/2f, 720-meusDados.getLayoutBounds().getHeight()/2);
        g.strokeText(meusDados.getText(), 450-meusDados.getLayoutBounds().getWidth()/2f, 720-meusDados.getLayoutBounds().getHeight()/2);
        
        menu.draw(pincel.getGrapics());
    }

    @Override
    public void pause() {
    }

    @Override
    public void update(double deltaTime) {
        menu.update(deltaTime);
        
        for(Animacao a : animes) {
            a.update(deltaTime);
        }
    }

    @Override
    public void prepare() {
        Font title = null;
        try {
            title = Font.loadFont(new FileInputStream(new File("./fonts//menu.otf")), 80);
        } catch (FileNotFoundException ex) {
            System.out.println("Erro na abertura da fonte: " + ex.getMessage());
            System.exit(1);
        }
        
        text = new Text("Pac - Man");
        text.setFont(title);
        text.setX(100);
        text.setY(150);
        
        animes.add(new Pinky((float)(text.getX()+text.getLayoutBounds().getWidth()), (float)(text.getY()-text.getLayoutBounds().getHeight()), null, 0) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

            @Override
            public void draw(GraphicsContext g) {
                
                Affine rotate = new Affine();
                rotate.appendRotation(30, getX(), getY());
                g.setTransform(rotate);
                super.draw(g);
            }
            
            
        });
        
        animes.add(new Inky((float)(text.getX()+text.getLayoutBounds().getWidth()-210f), (float)(text.getY()-text.getLayoutBounds().getHeight()), null, 0) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

            @Override
            public void draw(GraphicsContext g) {
                
                Affine rotate = new Affine();
                rotate.appendRotation(-15, getX(), getY());
                g.setTransform(rotate);
                super.draw(g);
            }
            
            
        });
        
        animes.add(new Clyde((float)(text.getX()+text.getLayoutBounds().getWidth()-290f), (float)(text.getY()-text.getLayoutBounds().getHeight()), null, 0) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

            @Override
            public void draw(GraphicsContext g) {
                
                Affine rotate = new Affine();
                rotate.appendRotation(15, getX(), getY());
                g.setTransform(rotate);
                super.draw(g);
            }
 
        });
        
        animes.add(new Blinky((float)(text.getX()+text.getLayoutBounds().getWidth()-110f), (float)(text.getY()-text.getLayoutBounds().getHeight()), null, 0, 0) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

            @Override
            public boolean isVivo() {
                return false;
            }

        });
        
        animes.add(new Blinky((float)(text.getX()), (float)(text.getY()-text.getLayoutBounds().getHeight()), null, 0, 0) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

            @Override
            public void draw(GraphicsContext g) {
                Affine rotate = new Affine();
                rotate.appendRotation(-30, getX(), getY());
                g.setTransform(rotate);
                super.draw(g); //To change body of generated methods, choose Tools | Templates.
            }

            
            
        });
        
        animes.add(new Blinky((float)(15*text.getX()/8f), (float)(text.getY()-text.getLayoutBounds().getHeight()), null, 0, 0) {
            @Override
            public float getRealX() {
                return getX();
            }

            @Override
            public float getRealY() {
                return getY();
            }

            @Override
            public boolean isVivo() {
                return false;
            }
 
            
        });
        
        
        for(Animacao a: animes) {
            a.refresh(40f);
        }
        
        menu.prepare(640, 200, 30);
    }

    @Override
    public void finalize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
