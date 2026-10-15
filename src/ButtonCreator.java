
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class ButtonCreator extends JPanel
        implements ActionListener {
     JButton b1, b2;
    public ButtonCreator() {



        ImageIcon leftButtonIcon = createImageIcon("Images/crying.png");
        ImageIcon middleButtonIcon = createImageIcon("Images/snake.png");

        b1 = new JButton("Exit", leftButtonIcon);
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        b1.setMnemonic(KeyEvent.VK_E);
        b1.setActionCommand("EXIT");

        b2 = new JButton("Restart", middleButtonIcon);
        b2.setVerticalTextPosition(AbstractButton.CENTER);
        b2.setHorizontalTextPosition(AbstractButton.LEADING);
        b2.setMnemonic(KeyEvent.VK_R);
        b2.setActionCommand("RESTART");


        b1.addActionListener(this);
        b2.addActionListener(this);

        add(b1);
        add(b2);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("RESTART")) {
            SnakeGame.requestRestart();
            SnakeGame.ButtonActive = false;
            SnakeGame.Choosing = false;
        } else {
            System.exit(0);
        }
    }


    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ButtonCreator.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    static boolean alreadyExist = false;
    public static void createAndShowGUI() {

        if (!alreadyExist) {

            JFrame frame = new JFrame("Chooser");
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ButtonCreator newContentPane = new ButtonCreator();
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);

            frame.pack();
            frame.setVisible(true);
            frame.requestFocus();
            alreadyExist = true;
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(ButtonCreator::createAndShowGUI);
    }
}
