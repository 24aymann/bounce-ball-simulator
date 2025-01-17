package software.ulpgc.ballbouncer.presenter;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import software.ulpgc.ballbouncer.simulationLogic.BallSimulator;
import software.ulpgc.ballbouncer.model.Ball;
import software.ulpgc.ballbouncer.view.BallDisplay;
import software.ulpgc.ballbouncer.view.BallDisplay.Circle;

public class BallPresenter {
    private Ball grabbedBall;
    private List<Ball> balls;
    private final BallDisplay ballDisplay;
    private final BallSimulator ballSimulator;
    private final static double dt = 0.001;
    public  final static Timer timer = new Timer();
    private static final int period = (int) (dt * 1000);
    private static final double PIXELS_PER_METER = 5 / 0.2;

    public BallPresenter(BallDisplay ballDisplay, BallSimulator ballSimulator) {
        this.balls = new ArrayList<>();
        this.ballDisplay = ballDisplay;
        this.ballDisplay.on(ballGrabbed());
        this.ballDisplay.off(ballReleased());
        this.ballSimulator = ballSimulator;
    }

    private BallDisplay.Grabbed ballGrabbed() {
        return c -> grabbedBall = toBall(c);
    }

    private BallDisplay.Released ballReleased() {
        return _ -> grabbedBall = null;
    }

    private Ball toBall(Circle circle) {
        Ball ball = findBallByItsId(circle.id());
        return new Ball(
                ball.id(),
                toMeters(circle.xComponent()),
                ball.r(),
                toMeters(circle.yComponent()),
                0,
                ball.g(),
                ball.cr());
    }

    private Ball findBallByItsId(String id) {
        return balls.stream()
                .filter(ball -> ball.id().equals(id))
                .findFirst().orElse(null);
    }

    private static double toMeters(double meters) {
        return meters / PIXELS_PER_METER;
    }

    public void execute() {
        timer.schedule(simulateTask(), 0, period);
    }

    private TimerTask simulateTask() {
        return new TimerTask() {
            @Override
            public void run() {
                simulate();
                drawBalls();
            }

            private void drawBalls() {
                ballDisplay.drawBall(toCircle(balls));
            }
        };
    }

    private List<Circle> toCircle(List<Ball> balls) {
        return balls.stream()
                .map(this::ballsMap)
                .toList();
    }

    private Circle ballsMap(Ball ball) {
        return new Circle(
                ball.id(), toPixels(ball.x()),
                toPixels(ball.h()), toPixels(ball.r())
        );
    }

    private static int toPixels(double meters) {
        return (int) (meters * PIXELS_PER_METER);
    }

    private void simulate() {
        balls = balls.stream()
                .map(this::simulateBall)
                .toList();
    }

    private Ball simulateBall(Ball ball) {
        if (grabbedBall != null && ball.id().equals(grabbedBall.id()))
            return grabbedBall;
        return ballSimulator.simulate(ball);
    }

    public BallPresenter add(Ball ball) {
        this.balls.add(ball);
        return this;
    }
}
