package tppaint2014;

import java.awt.BorderLayout;
import java.awt.Frame; //important
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileInputStream;
import java.util.ArrayList;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import server.ConnexionClient;

public class Fenetre extends Frame { //Frame <=> Objet graphique JAVA

    public static int COLORIAGE_M23 = 0; //ctp 2014
    public BarreHaute bh;
    public BarreBasse bb;
    public ZoneGraphique zg;
    public MenuBar menu;

    public boolean ModeGomme;
    public boolean EditEnCours;
    public ConnexionClient connexionClient;

    public ArrayList<Forme> lesFormes;

    public CommunicationClientHote communication;

    public void setConnection(ConnexionClient c) {
        this.connexionClient = c;
        try {
            communication = new CommunicationClientHote(c.getSocket());
        } catch (Exception e) {
            System.out.println("Une erreur de communication s'est produite");
        }
    }
    public boolean isHost;

    public Fenetre(boolean host) {

        isHost = host;
        EcouteurFenetre ef = new EcouteurFenetre(this);

        this.addWindowListener(ef);

        lesFormes = new ArrayList<>(); //Ne pas oublier d'instacier l'array List

        //  this.connexionClient.start();
//        this.connexionClient.SetFenetre(this);
        this.setTitle("TP PAINT POO2");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null); //centrer

        bh = new BarreHaute(this);
        bb = new BarreBasse();
        zg = new ZoneGraphique(this);

        EcouteurSouris es = new EcouteurSouris(this);

        zg.addMouseListener(es);
        zg.addMouseMotionListener(es); //move et drag
        System.out.println("hello");

        this.setLayout(new BorderLayout()); //LAYOUT
        this.add(bh, BorderLayout.NORTH);
        this.add(zg, BorderLayout.CENTER);
        this.add(bb, BorderLayout.SOUTH);
        // this.add(new Button("CENTER"), BorderLayout.CENTER);

        initMenuBarre();

    }

    private void initMenuBarre() {
        menu = new MenuBar();
        Menu m1 = new Menu("FICHIER");
        MenuItem m11 = menuEnregisrer();
        MenuItem m12 = menuCharger();
        MenuItem m13 = menuQuiter();
        MenuItem m14 = menuCreer();

        Menu m2 = new Menu("CTP POO");
        MenuItem m21 = menuSymetrie();
        MenuItem m22 = menuColorier();
        MenuItem m23 = menuChoisir();

        m2.add(m21);
        m2.add(m22);
        m2.add(m23);

        m1.add(m14);
        m1.add(m11);
        m1.add(m12);
        m1.add(m13);

        menu.add(m1);
        menu.add(m2);
        this.setMenuBar(menu);

//        m11.addActionListener(this);
        //     m12.addActionListener(this);
        //      m13.addActionListener(this);
    }

    private MenuItem menuQuiter() {
        MenuItem m = new MenuItem("Quitter");
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        };
        m.addActionListener(a);
        return m;
    }

    private MenuItem menuCreer() {
        MenuItem m = new MenuItem("Nouveau");
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lesFormes.clear();
                zg.repaint();
            }

        };
        m.addActionListener(a);
        return m;
    }

    private MenuItem menuChoisir() {
        MenuItem m = new MenuItem("Colorier une forme cliquée");
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Chosir");
                if (COLORIAGE_M23 == 0) {
                    COLORIAGE_M23 = 1;
                } else {
                    COLORIAGE_M23 = 0;
                }
            }

        };
        m.addActionListener(a);
        return m;
    }

    private MenuItem menuColorier() {
        MenuItem m = new MenuItem("Colorier une famille");
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Colorier");
                for (Forme f : lesFormes) {
                    if (bh.formes.getSelectedItem() == "rectangle" && f instanceof Rectangle
                            || bh.formes.getSelectedItem() == "cercle" && f instanceof Cercle
                            || bh.formes.getSelectedItem() == "camion" && f instanceof Camion) {
                        System.out.println("CAMION !");
                        f.remplir(1);
                    }

                }
                zg.repaint();
            }
        };
        m.addActionListener(a);
        return m;
    }

    private MenuItem menuSymetrie() {
        MenuItem m = new MenuItem("Symétrie horizontale");
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Symétrie horizontale");
                for (Forme f : lesFormes) {
                    if (f instanceof Droite) {
                        f.a = zg.getWidth() - f.a;
                        f.c = zg.getWidth() - f.c;
                    } else {
                        f.a = zg.getWidth() - f.a - f.c;
                    }

                }
                zg.repaint();
                /* for (i=0; i<lesFormes.size() ;i++)
                {
                    
                }*/
            }
        };

        m.addActionListener(a);
        return m;
    }

    private MenuItem menuCharger() {

        MenuItem m = new MenuItem("Charger dessin");
        ActionListener a = new ActionListener() { //classe anonyme
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser jfc = new JFileChooser();
                FileNameExtensionFilter extension = new FileNameExtensionFilter("Fichier dessin", "desc");
                jfc.setFileFilter(extension);
                int returnVal = jfc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String chemin = jfc.getSelectedFile().getAbsolutePath();
                    System.out.println(chemin);
                    if (chemin != null) {
                        try {
                            FileInputStream fos = new FileInputStream(chemin);
                            ObjectInputStream ois = new ObjectInputStream(fos);

                            ArrayList<Forme> lesFormesLues;
                            lesFormesLues = new ArrayList<>();

                            boolean eof = false;
                            lesFormes.clear();
                            zg.repaint();
                            while (eof != true) {
                                try {
                                    Forme formeLue = (Forme) ois.readObject();
                                    lesFormes.add(formeLue);
                                } catch (EOFException e) {
                                    eof = true;
                                    System.out.println(lesFormes);
                                }
                            }

                            System.out.println(lesFormes);

                            ois.close();
                            fos.close();
                            zg.paint(zg.getGraphics());
                            JOptionPane.showMessageDialog(null, "Dessin chargé !", "Succès", JOptionPane.INFORMATION_MESSAGE);

                        } catch (FileNotFoundException ex) {
                            System.out.println("Problème lors de l'ouverture du fichier");
                            JOptionPane.showMessageDialog(null, "Erreur !\nChemin : " + chemin, "Erreur", JOptionPane.WARNING_MESSAGE);
                        } catch (IOException ex) {
                            System.out.println("probleme");
                            JOptionPane.showMessageDialog(null, "Erreur !\nChemin: " + chemin, "Erreur", JOptionPane.ERROR_MESSAGE);

                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
        m.addActionListener(a);
        return m;
    }

    private MenuItem menuEnregisrer() {
        MenuItem m = new MenuItem("Enregistrer dessin");
        ActionListener a = new ActionListener() { //classe anonyme
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser jfc = new JFileChooser();
                FileNameExtensionFilter extension = new FileNameExtensionFilter("Fichier dessin", "desc");
                jfc.setFileFilter(extension);
                int returnVal = jfc.showSaveDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String chemin = jfc.getSelectedFile().getAbsolutePath() + ".desc";
                    if (chemin != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(chemin);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);

                            for (Forme f : lesFormes) {
                                oos.writeObject(f);
                            }
                            oos.close();
                            fos.close();
                            JOptionPane.showMessageDialog(null, "Dessin enregistré !", "Succès", JOptionPane.INFORMATION_MESSAGE);

                        } catch (FileNotFoundException ex) {
                            System.out.println("Problème lors de l'ouverture du fichier");
                            JOptionPane.showMessageDialog(null, "Erreur !\nChemin : " + chemin, "Erreur", JOptionPane.WARNING_MESSAGE);
                        } catch (IOException ex) {
                            System.out.println("probleme");
                            JOptionPane.showMessageDialog(null, "Erreur !\nChemin: " + chemin, "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        };

        m.addActionListener(a);
        return m;
    }

}
