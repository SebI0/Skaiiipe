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
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import modele.Salon;
import server.ConnexionClient;
import services.Message;
import tppaint2014.EcouteurFenetre;
import tppaint2014.Fenetre;
import tppaint2014.TPPaint2014;

/**
 *
 * @author Seb
 */
public class StartView extends javax.swing.JFrame {

    private Socket s1;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    ArrayList<Salon> listeSalons;

    /**
     * Creates new form Start
     */
    public StartView() {
        initComponents();
        connectionServeur();
        listerSalons();
        listerCategorieSalon();
    }

    private void connectionServeur() {
        try {
            this.s1 = new Socket();
            InetSocketAddress sa = new InetSocketAddress("localhost", 60001);
            System.out.println("Try to connect");
            s1.connect(sa);
            System.out.println("Connexion Accepted");
            outputStream = new ObjectOutputStream(s1.getOutputStream());
            Message demandeSalons = new Message(Message.LIST_SALONS, 0);

            outputStream.writeObject(demandeSalons);
            inputStream = new ObjectInputStream(s1.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listerCategorieSalon() {
        jComboBox1.removeAllItems();

        for (Salon sal : listeSalons) {
            jComboBox1.addItem(sal.getCatégorie());
        }

    }

    public void listerSalons() {
        int vrai = 1;
        try {

            System.out.println("__client__ reception données");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(68, 68, 68))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField2)
                                            .addComponent(jComboBox1, 0, 206, Short.MAX_VALUE)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3)))
                .addGap(29, 29, 29)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseReleased
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selectedNode.isLeaf() == true) {
            Salon salonSelected = (Salon) selectedNode.getUserObject();
            salonNameTxt.setText(salonSelected.getNom());
            salonCategoryTxt.setText(salonSelected.getCatégorie());
            salonIpTxt.setText(salonSelected.getIp());
            salonUsersTxt.setText(String.valueOf(salonSelected.getNbUsers()));

        }
    }//GEN-LAST:event_jTree1MouseReleased

    private void connectionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionBtnActionPerformed
        /*Connexion au client*/
        try {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
            if (selectedNode.isLeaf() == true) {
                Salon salonSelected = (Salon) selectedNode.getUserObject();
                this.s1 = new Socket();
                InetSocketAddress sa = new InetSocketAddress(salonSelected.getIp(), 60002);
                System.out.println("Try to connect");
                s1.connect(sa);
                System.out.println("Connexion Accepted");
                outputStream = new ObjectOutputStream(s1.getOutputStream());
                inputStream = new ObjectInputStream(s1.getInputStream());
            }

        } catch (IOException ex) {
            Logger.getLogger(StartView.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }//GEN-LAST:event_connectionBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        try {
            System.out.println("Demande création de salon");
            outputStream.writeObject(new Message(Message.CREATION_SALON, new Salon(/*InetAddress.getLocalHost().toString()*/"192.168.70.124", 6008, jTextField2.getText(), "MCS3")));
            Object msg = (Object) inputStream.readObject();
            System.out.println("Considéré comme un salon d'id: "+msg.toString());

            Host hote = new Host();
            hote.start();
            
        Socket s1 = new Socket();
        InetSocketAddress sa = new InetSocketAddress("localhost", 60002);
        s1.connect(sa);
        System.out.println("Connexion Accepted");
        ConnexionClient ConnexionSock = new ConnexionClient(s1);
        ConnexionSock.start();
        Fenetre f = new Fenetre(ConnexionSock);
        f.setVisible(true);
        
        EcouteurFenetre ef = new EcouteurFenetre();
        
        f.addWindowListener(ef);
        
        

} catch (IOException ex) {
            Logger.getLogger(StartView.class  

.getName()).log(Level.SEVERE, null, ex);
        } 

catch (ClassNotFoundException ex) {
            Logger.getLogger(StartView.class  

.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }//GEN-LAST:event_jButton2ActionPerformed

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
        } 

catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartView.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectionBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel salonCategoryTxt;
    private javax.swing.JLabel salonIpTxt;
    private javax.swing.JLabel salonNameTxt;
    private javax.swing.JLabel salonUsersTxt;
    // End of variables declaration//GEN-END:variables
}
