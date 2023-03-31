/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto1.engine.uteis;

/**
 *
 * @author Vitor Rinaldini
 */
public class Contador extends Thread{
    
    private float contagem = 0;
    private float erro = 1f;
    
    public Contador() {
        contagem = 99;
    }
    
    public synchronized void setErro(float erro) {
        this.erro = erro;
    }

    public synchronized void setContagem(float contagem) {
        this.contagem = contagem;
    }
    
    public synchronized float getContagem() {
        float aprox = (float) Math.round(contagem/erro);  
        return aprox*erro;
    }
    
    public boolean isZero() {
        return (contagem <= 0);
    }
    
    @Override
    public void run() {
        while(contagem > 0) {
            
            contagem -= erro;
            if(contagem > 0) {
                try {
                    Thread.sleep((int) (erro * 1000));
                } catch (Exception e) {
                    System.out.println("Erro Contador: " + e.getMessage());
                }
            }
        }
        contagem = 0;
    }
    
    
}
