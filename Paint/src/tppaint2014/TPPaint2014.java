
package tppaint2014;


public class TPPaint2014 {

    public static void main(String[] args) {
        // TODO code application logic here
        Fenetre f = new Fenetre();
        f.setVisible(true);
        
        EcouteurFenetre ef = new EcouteurFenetre();
        
        f.addWindowListener(ef); //Présentation: qui écoute qui
    }
    
}
