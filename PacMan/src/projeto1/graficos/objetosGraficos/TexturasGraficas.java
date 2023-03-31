/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.graficos.objetosGraficos;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author Vitor Rinaldini
 */
public class TexturasGraficas {
    
    private ImagePattern paredeMaterialBounds;
    private ImagePattern paredeMaterialMiddle;
    private ImagePattern chaoMaterial; 
    private ImagePattern porteiraMaterial;

    public ImagePattern getParedeMaterialBounds() {
        return paredeMaterialBounds;
    }

    public ImagePattern getParedeMaterialMiddle() {
        return paredeMaterialMiddle;
    }

    public ImagePattern getChaoMaterial() {
        return chaoMaterial;
    }

    public ImagePattern getPorteiraMaterial() {
        return porteiraMaterial;
    }
    
    public TexturasGraficas() {
        paredeMaterialBounds = createMaterial(Color.BLUE);
        paredeMaterialMiddle = createMaterial(Color.CYAN);
        chaoMaterial = createMaterial(Color.GREEN);
        porteiraMaterial = createMaterial(Color.YELLOW);
    }
    
    private ImagePattern createMaterial(Color cor) {
        
        WritableImage image = new WritableImage(400, 400);
        PixelWriter px = image.getPixelWriter();
        for(int i = 0; i < 400; i++) {
            for(int j = 0; j < 400; j++) {
                px.setColor(i, j, cor);
            }
        }
        
        ImagePattern result = new ImagePattern(image);
        return result;
    }
    
}
