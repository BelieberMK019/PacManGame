/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import projeto1.engine.InputKeyController;

/**
 *
 * @author Vitor Rinaldini
 */
public class MainFrameController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    private InputKeyController teclado;
    
    public MainFrameController(InputKeyController teclado) {
        this.teclado = teclado;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GameLoop gl = new GameLoop(canvas, teclado);
        gl.play();
    }    
    
}
