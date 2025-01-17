package software.ulpgc.ballbouncer.swingApp;

import software.ulpgc.ballbouncer.view.BallDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SwingBallDisplay extends JPanel implements BallDisplay {
    private int width;
    private int height;
    private Grabbed grabbed = null;
    private Released released = null;
    private Optional<Circle> currentCircle;
    private final List<Circle> circles;

    public SwingBallDisplay() {
        this.circles = new ArrayList<>();
        this.setupListeners();
    }

    @Override
    public void drawBall(List<Circle> circles) {
        synchronized (this.circles) {
            this.circles.clear();
            this.circles.addAll(circles);
            repaint();
        }
    }

    @Override
    public void on(Grabbed grabbed) {
        this.grabbed = grabbed;
    }

    @Override
    public void off(Released released) {
        this.released = released;
    }

    private void setupListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (grabbed == null)
                    return;
                currentCircle = findCircle(xCoordinateAdjustment(e.getX()), yCoordinateAdjustment(e.getY()));
                currentCircle.ifPresent(grabbed::at);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(currentCircle.isEmpty())
                    return;
                currentCircle.ifPresent(circle ->
                        released.at(new Circle(
                        circle.id(), xCoordinateAdjustment(e.getX()),
                        yCoordinateAdjustment(e.getY()), circle.radius())
                ));
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(grabbed == null || currentCircle.isEmpty())
                    return;
                currentCircle.ifPresent(circle ->
                        grabbed.at(new Circle(
                        circle.id(), xCoordinateAdjustment(e.getX()),
                        yCoordinateAdjustment(e.getY()), circle.radius())
                ));
            }
        });
    }

    private Optional<Circle> findCircle(int xCoordinate, int yCoordinate) {
        synchronized (circles) {
            return circles.stream()
                    .filter(circle -> circle.isAt(xCoordinate, yCoordinate))
                    .findFirst();
        }
    }

    private int xCoordinateAdjustment(int x) {
        return x - width / 2;
    }

    private int yCoordinateAdjustment(int y) {
        return height - y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        synchronized (this.circles){
            super.paintComponent(g);
            width = getWidth();
            height = getHeight() - 5;
            wipeCanvas(g, width, height);
            circles.forEach(circle -> paintCircle(g, circle));
        }
    }

    private void paintCircle(Graphics g, Circle circle) {
        g.setColor(Color.orange);
        g.fillOval(
                width / 2 + circle.xComponent() - circle.radius(),
                height - circle.yComponent() - circle.radius(),
                circle.radius() * 2,
                circle.radius() * 2
        );
    }

    private void wipeCanvas(Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, height, width, height + 5);
    }
}
