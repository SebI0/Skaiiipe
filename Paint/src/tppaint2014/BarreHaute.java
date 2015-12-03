
package tppaint2014;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;




public class BarreHaute extends Panel implements ActionListener{
    
    public Choice couleurs;
    public Choice formes;
    public Button efface;
    public Button effaceTout;
    public Button gomme;
    public Button effaceFamille;
    public Button effaceCouleur;
    
    public Button rotGauche;
    public Button rotDroite;
    
    public Fenetre saF;
    
   // Public JComboBox //swing


    public BarreHaute(Fenetre laF){
        saF = laF;
        
        this.setBackground(Color.lightGray);
        couleurs = new Choice();
        couleurs.add("BLEU");
        couleurs.add("ROUGE");
        couleurs.add("VERT");
        couleurs.add("ORANGE");
        
        formes = new Choice();
        formes.add("droite");
        formes.add("rectangle");
        formes.add("cercle");
        formes.add("disque");
        formes.add("triangle");
        formes.add("camion");
        
        
        
        efface = new Button("EFFACE");
        effaceTout = new Button("EFFACE TOUT");
        gomme = new Button("Gomme");
        
        effaceFamille = new Button("EFFACER UNE FAMILLE");
        effaceCouleur= new Button("EFFACER UNE COULEUR");
        
        rotGauche = new Button("ROTATION GAUCHE");
        rotDroite = new Button("ROTATION DROITE");
        
        rotGauche.addActionListener(this);
        rotDroite.addActionListener(this);
                
        //Ne pas oublier !
        efface.addActionListener(this);
        effaceTout.addActionListener(this);
        gomme.addActionListener(this);
        
        effaceFamille.addActionListener(this);
        effaceCouleur.addActionListener(this);
        
        this.add(rotDroite);
        this.add(rotGauche);
        
        this.add(gomme);
        this.add(couleurs);
        this.add(formes);
        this.add(efface);
        this.add(effaceTout);
        
        this.add(effaceFamille);
        this.add(effaceCouleur);
        
        this.add(rotDroite);
        this.add(rotGauche);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(efface))
        {
            System.out.println("Effacer");
            if (saF.lesFormes.size()>0)
            {
                //On parcourt tout le tableau jusqu'a ce que la forme a effacer ne soit pas une gomme
                int i = saF.lesFormes.size()-1;
                while (i>=0)
                {
                    if (!(saF.lesFormes.get(i) instanceof Gomme))
                    {
                        saF.lesFormes.remove(i); //supprime le dernier elt
                        i=-1;
                        saF.zg.repaint();
                    }
                    i--;
                }

            }
            else
                System.out.println("Plus d'elts à supprimer");
  
        }
        else if (ae.getSource().equals(effaceTout))
        {
            System.out.println("Effacer tout");
            saF.lesFormes.clear();
            saF.zg.repaint();
        }
        
        else if (ae.getSource().equals(gomme))
        {
            System.out.println("GOMME");
            if (saF.ModeGomme)
            {
                saF.ModeGomme=false;
                System.out.println("Gomme désactivée");
                saF.bb.changerMessage("Tout va bien");
                saF.bh.gomme.setLabel("Gomme");
            }else{
                System.out.println("Gomme active");
                saF.bb.changerMessage("Gomme en cours");
                saF.bh.gomme.setLabel("Gommage");
                saF.ModeGomme=true;
            }
        }
        
        else if (ae.getSource().equals(effaceFamille))
        {
            System.out.println("Effacement de la famille: " + formes.getSelectedItem());
            int i;
            
            for (i=0; i<saF.lesFormes.size();i++)
            {
                if ((formes.getSelectedItem().equals("rectangle") && saF.lesFormes.get(i) instanceof Rectangle)
                    || (formes.getSelectedItem().equals("droite") && saF.lesFormes.get(i) instanceof Droite)
                    || (formes.getSelectedItem().equals("cercle") && saF.lesFormes.get(i) instanceof Cercle)
                    || (formes.getSelectedItem().equals("disque") && saF.lesFormes.get(i) instanceof Disque)
                    || (formes.getSelectedItem().equals("triangle") && saF.lesFormes.get(i) instanceof Triangle))
                {
                    saF.lesFormes.remove(i);
                    i--;
                }
            }
            
            saF.zg.repaint();
        }
        
        else if (ae.getSource().equals(effaceCouleur))
        {
            System.out.println("Effacement de toutes les formes de couleur: " + couleurs.getSelectedItem());
            int i;
            
            for (i=0; i<saF.lesFormes.size();i++)
            {
               if ((couleurs.getSelectedItem().equals("BLEU") && saF.lesFormes.get(i).col.equals(Color.blue))
                    || (couleurs.getSelectedItem().equals("ROUGE") && saF.lesFormes.get(i).col.equals(Color.red))
                    || (couleurs.getSelectedItem().equals("VERT") && saF.lesFormes.get(i).col.equals(Color.green))
                    || (couleurs.getSelectedItem().equals("ORANGE") && saF.lesFormes.get(i).col.equals(Color.orange)))
                {
                    saF.lesFormes.remove(i);
                    i--;
                }
            }
            saF.zg.repaint();
        }
        
        else if (ae.getSource().equals(rotGauche))
        {
            System.out.println("ROTATION GAUCHE");


               for (Forme f: saF.lesFormes)
               {
                    int oldFa = f.a;
                    int oldFc = f.c;
                   if (f instanceof Droite)
                   {

                               
                       f.a = saF.zg.getWidth()/2 + f.b - saF.zg.getHeight()/2;
                       f.b = saF.zg.getHeight()/2 - oldFa + saF.zg.getWidth()/2;
                       
                       f.c = saF.zg.getWidth()/2 + f.d - saF.zg.getHeight()/2;
                       f.d = saF.zg.getHeight()/2 - oldFc + saF.zg.getWidth()/2;
                   }
                   
                    else if (f instanceof Rectangle)
                   {
                       f.a = saF.zg.getWidth()/2 + f.b - saF.zg.getHeight()/2;
                       f.b = saF.zg.getHeight()/2 - oldFa + saF.zg.getWidth()/2;
                       
                       
                   }

               }
               saF.zg.repaint();
            
        }
        
        else if (ae.getSource().equals(rotDroite))
        {
            System.out.println("Rot droite");
             for (Forme f: saF.lesFormes)
               {
                   
                int oldFa = f.a;
                int oldFc = f.c;
                   if (f instanceof Droite)
                   {
                       f.a = saF.zg.getWidth()/2 - f.b + saF.zg.getHeight()/2;
                       f.b = saF.zg.getHeight()/2 + oldFa - saF.zg.getWidth()/2;
                       
                       f.c = saF.zg.getWidth()/2 - f.d + saF.zg.getHeight()/2;
                       f.d = saF.zg.getHeight()/2 + oldFc - saF.zg.getWidth()/2;
                   }
                   
                   else if (f instanceof Rectangle)
                   {
                       f.a = saF.zg.getWidth()/2 - f.b + saF.zg.getHeight()/2;
                       f.b = saF.zg.getHeight()/2 + oldFa - saF.zg.getWidth()/2;
                       
                       
                   }

               }
               saF.zg.repaint();
        }
    }

  
    
}
