/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import modele.Salon;
import server.ConnexionClient;
import services.Message;
import tppaint2014.EcouteurFenetre;
import tppaint2014.Fenetre;

/**
 * Classe principale de l'application client.
 *
 * @author Seb
 */
public class StartView extends javax.swing.JFrame {

    /**
     * @param s1 Socket utilisée avec le client pour communiquer avec le serveur
     * principal
     * @param inputStr Flux de lecture du serveur principal vers le client
     * @param outputStr Flux d'écriture du client vers serveur princpal
     * @param listeSalons Liste de Salon comportants les différents salons que
     * l'utilisateur peut rejoindre
     * @see Salon
     */
    private Socket s1;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ArrayList<Salon> listeSalons;

    /**
     * Constructeur de l'application
     *
     * @throws java.net.UnknownHostException En cas de problème de résolution de
     * l'hote
     */
    public StartView() throws UnknownHostException {
        //Initialisation des compostants graphiques
        initComponents();
        //Initialisation du jTree (navigations pour les salons)
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Non connecté");
        DefaultTreeModel dtm = new DefaultTreeModel(racine);
        DefaultMutableTreeNode salon = new DefaultMutableTreeNode("Aucun salon");
        racine.add(salon);
        jTree1.setModel(dtm);

        //debug
        this.jTextField4.setText(InetAddress.getLocalHost().getHostAddress());
        this.jTextField5.setText("6555");
    }

    /**
     * Fonction de connexion au serveur principal Un controle est réalisé sur la
     * valeur des textbox pour l'IP et le port
     */
    private void connectionServeur() {

        boolean valid = true;
        if (jTextField4.getText().isEmpty()) {
            valid = false;
            JOptionPane.showMessageDialog(this, "L'ip ne peut être nulle", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        if (jTextField5.getText().isEmpty()) {
            valid = false;
            JOptionPane.showMessageDialog(this, "Le port ne peut être nul", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        if (valid) {
            try {

                InetSocketAddress sa;
                this.s1 = new Socket();
                if (jRadioButton2.isSelected()) {
                    //connection via host (résolution dns)
                    sa = new InetSocketAddress(InetAddress.getByName(jTextField4.getText()), Integer.valueOf(jTextField5.getText()));
                } else {
                    //connexion via ip directe
                    sa = new InetSocketAddress(jTextField4.getText(), Integer.valueOf(jTextField5.getText()));
                }

                //Connexion
                s1.connect(sa, 5000);

                //Initialisation des flux d'écriture
                outputStream = new ObjectOutputStream(s1.getOutputStream());
                Message demandeSalons = new Message(Message.LIST_SALONS, 0);

                //Demande de récupération de la liste des salons
                outputStream.writeObject(demandeSalons);
                inputStream = new ObjectInputStream(s1.getInputStream());

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erreur de connexion au serveur de salons", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Permet de récuperer les listes des catégories et de les placer dans la
     * combobox
     */
    public void listerCategorieSalon() {
        jComboBox1.removeAllItems();

        for (Salon sal : listeSalons) {
            jComboBox1.addItem(sal.getCatégorie());
        }

    }

    /**
     * Fonction de reception du message du serveur au client lui indicant les
     * salons disponible. cette fonction va se charger ensuite de les mettre
     * dans le jTree à l'affichage
     */
    public void listerSalons() {
        int vrai = 1;
        try {

            Message salonsMessage = (Message) inputStream.readObject();
            while (salonsMessage != null) {
                if (salonsMessage.getType() == Message.LIST_SALONS) {
                    listeSalons = (ArrayList<Salon>) salonsMessage.getData();
                    System.out.println("__client__" + listeSalons);
                    //fin reception//

                    ArrayList<String> listeCategories = new ArrayList<>();
                    DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Salons");
                    for (Salon s : listeSalons) {
                        System.out.println(s);
                        int pos = listeCategories.indexOf(s.getCatégorie());
                        if (pos == -1) {
                            DefaultMutableTreeNode categorie = new DefaultMutableTreeNode(s.getCatégorie());
                            DefaultMutableTreeNode salon = new DefaultMutableTreeNode(s);
                            racine.add(categorie);
                            categorie.add(salon);
                            listeCategories.add(s.getCatégorie());
                        } else {
                            DefaultMutableTreeNode salon = new DefaultMutableTreeNode(s);
                            DefaultMutableTreeNode categorie = (DefaultMutableTreeNode) racine.getChildAt(pos);
                            categorie.add(salon);
                        }
                    }
                    DefaultTreeModel dtm = new DefaultTreeModel(racine);
                    jTree1.setModel(dtm);
                }
                salonsMessage = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel6 = new javax.swing.JLabel();
        salonNameTxt = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        salonCategoryTxt = new javax.swing.JLabel();
        salonIpTxt = new javax.swing.JLabel();
        salonUsersTxt = new javax.swing.JLabel();
        connectionBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Skaiiipe");

        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTree1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jLabel2.setText("Pseudo");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Nombre d'utilisateurs");

        salonNameTxt.setText("Nom du salon");

        jLabel3.setText("Nom du salon");

        jLabel4.setText("Catégorie");

        jLabel5.setText("IP");

        salonCategoryTxt.setText("Nom du salon");

        salonIpTxt.setText("Nom du salon");

        salonUsersTxt.setText("Nom du salon");

        connectionBtn.setText("Connexion");
        connectionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(72, 72, 72)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(salonIpTxt)
                                    .addComponent(salonCategoryTxt)
                                    .addComponent(salonNameTxt)))
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(salonUsersTxt)))
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(connectionBtn)
                        .addGap(82, 82, 82))))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(salonNameTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(salonCategoryTxt)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(salonIpTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(salonUsersTxt))
                .addGap(18, 18, 18)
                .addComponent(connectionBtn)
                .addGap(17, 17, 17))
        );
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(salonNameTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(salonCategoryTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(salonIpTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(salonUsersTxt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(connectionBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton2.setText("Créer salon");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Intitulé");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Thème");

        jLabel9.setText("Mot de passe");

        jLabel10.setText("IP serveur de salons : ");
        jLabel10.setToolTipText("");

        jButton3.setText("Connexion");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Adresse IP");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Nom d'hôte");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel11.setText("Port : ");
        jLabel11.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 25, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(5, 5, 5)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton2))
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fonction de gestions de clics dans le jTree pour afficher ou non les
     * informations du salons dans la barre de droite
     *
     * @param evt événement provoquant l'appel de la fonction
     */
    private void jTree1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseReleased
        try {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
            if (selectedNode.isLeaf() == true) {
                Salon salonSelected = (Salon) selectedNode.getUserObject();
                salonNameTxt.setText(salonSelected.getNom());
                salonCategoryTxt.setText(salonSelected.getCatégorie());
                salonIpTxt.setText(salonSelected.getIp());
                salonUsersTxt.setText(String.valueOf(salonSelected.getNbUsers()));

            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jTree1MouseReleased

    
    /**
     * Tentative de connexion à un salon
     * @param evt Événément provoquant l'appel de la fonction
     */
    private void connectionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionBtnActionPerformed
        if (jTextField1.getText().equals((String) "")) {
            JOptionPane.showMessageDialog(null, "Un nom d'utilisateur est nessaire");
        } else {
            /*Connexion au client maitre*/

            
            try {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
                if (selectedNode.isLeaf() == true) {
                    Salon salonSelected = (Salon) selectedNode.getUserObject(); //récupération du salon ==> ip et port du client maitre
                    
                    //Création d'une nouvelle socket
                    this.s1 = new Socket();
                    InetSocketAddress sa = new InetSocketAddress(salonSelected.getIp(), salonSelected.getPort());
                    s1.connect(sa);
                    
                    //Création d'une nouvelle fenêtre qui n'est pas celle de l'hôte
                    Fenetre f = new Fenetre(false);
                    ConnexionClient ConnexionSock = new ConnexionClient(s1, f); //socket d'écoute ==> on écoute ce que le client maitre envoie
                    f.setConnection(ConnexionSock);
                    ConnexionSock.start();
                    f.setVisible(true);
                }

            } catch (IOException ex) {
                Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_connectionBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTextField1.getText().equals((String) "") || jTextField2.getText().equals((String) "")) {
            if (jTextField1.equals((String) "")) {
                JOptionPane.showMessageDialog(null, "Un nom d'utilisateur est nessaire");
            } else {
                JOptionPane.showMessageDialog(null, "Un nom de salon est necessaire");
            }

        } else {
            //création salon
            try {
                ServerSocket s = new ServerSocket(0); //socketserveur du client maitre

                System.out.println("Demande création de salon");

                Message m = new Message(Message.CREATION_SALON, new Salon(s.getInetAddress().getLocalHost().getHostAddress(), s.getLocalPort(), jTextField2.getText(), "MCS3"));
                System.out.println(m);

                //envoi de la création de salon avec ip + port du client maitre
                outputStream.writeObject(m);
                //retour du serveur de salons
                Object msg = (Object) inputStream.readObject();
                System.out.println("Considéré comme un salon d'id: " + msg.toString());

                Message mesg = (Message) msg;
                int idsalon = (int) mesg.getData();

                //le maitre se connecte à lui même
                Socket s1 = new Socket();
                //  ServerSocket s1 = new ServerSocket(0);
                InetSocketAddress sa = new InetSocketAddress(s.getInetAddress().getHostAddress(), s.getLocalPort()); //
                s1.connect(sa);
                System.out.println("Connexion Accepted");

                Fenetre f = new Fenetre(true);

                ConnexionClient ConnexionSock = new ConnexionClient(s1, f);
                f.setConnection(ConnexionSock);
                ConnexionSock.start();

                Host hote = new Host(s, inputStream, outputStream, f);
                hote.setId_salon(idsalon);
                hote.start();

                System.out.println("1");
                ConnexionSock.SetFenetre(f);
//            ConnexionSock.start();
                System.out.println("2");

                System.out.println("3");
                f.setVisible(true);
                System.out.println("4");

            } catch (IOException ex) {
                Logger.getLogger(StartView.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StartView.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        connectionServeur();
        listerSalons();
        listerCategorieSalon();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        jLabel10.setText("Hote du serveur de salons");
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        jLabel10.setText("IP du serveur de salons");
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new StartView().setVisible(true);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton connectionBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel salonCategoryTxt;
    private javax.swing.JLabel salonIpTxt;
    private javax.swing.JLabel salonNameTxt;
    private javax.swing.JLabel salonUsersTxt;
    // End of variables declaration//GEN-END:variables
}
