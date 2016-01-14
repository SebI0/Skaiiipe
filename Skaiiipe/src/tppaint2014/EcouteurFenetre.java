package tppaint2014;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EcouteurFenetre implements WindowListener {

    private Fenetre f;

    public EcouteurFenetre(Fenetre fen) {
        this.f = fen;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        f.setVisible(false);
        if (f.isHost) {
            f.communication.shutdown();
        }
        f.dispose();

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
