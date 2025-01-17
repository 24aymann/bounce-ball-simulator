package software.ulpgc.ballbouncer.swingApp;

import software.ulpgc.ballbouncer.view.BallDisplay;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainFrame extends JFrame {
    private final BallDisplay ballDisplay;

    public MainFrame() throws HeadlessException {
        this.setTitle("Bouncing Ball Simulator");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add((Component) (ballDisplay = createBallDisplay()));
        this.applicationIcon();
    }

    public BallDisplay getBallDisplay() {
        return ballDisplay;
    }

    private SwingBallDisplay createBallDisplay() {
        return new SwingBallDisplay();
    }

    private void applicationIcon() {
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("ballIcon.png"))
            );
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
