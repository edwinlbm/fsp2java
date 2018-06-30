import java.applet.Applet;
import java.awt.Graphics;
 
public class InterfaceGrafica extends Applet {
    String chave;

    public InterfaceGrafica(){
        this.chave = null;
    }

    public void paint(Graphics g) {
        g.drawString(this.chave, 80, 80);
    }

    public void setChave(String chave){
        this.chave = chave;
    }

    public String getChave(){
        return this.chave;
    }
}