package software.ulpgc.ballbouncer.simulationLogic;

import software.ulpgc.ballbouncer.model.Ball;

public class BallSimulator {
    private final double dt;

    public BallSimulator(double dt) {
        this.dt = dt;
    }

    public Ball simulate(Ball ball){
        return new Ball(
                ball.id(), ball.xAxisPosition(),
                ball.radius(), newHeightOf(ball),
                newVelocityOf(ball), ball.gravity(),
                ball.cr()
        );
    }

    private double newHeightOf(Ball ball) {
        return willBounce(ball) ?
                newHeightAfterBouncedOf(ball) : ball.initialHeight() + ball.velocity() * dt;
    }

    private double newVelocityOf(Ball ball) {
        return willBounce(ball) ?
                newVelocityAfterBouncedOf(ball) : ball.velocity() + ball.gravity() * dt;
    }

    private boolean willBounce(Ball ball) {
        return ball.velocity() < 0 && dt > timeToBounceOf(ball);
    }

    private double newHeightAfterBouncedOf(Ball ball) {
        return ball.radius() + newVelocityAfterBouncedOf(ball) * (dt - timeToBounceOf(ball));
    }

    private double newVelocityAfterBouncedOf(Ball ball) {
        return - ball.cr() * (ball.velocity() + ball.gravity() * timeToBounceOf(ball));
    }

    private static double timeToBounceOf(Ball ball) {
        return - (ball.initialHeight() - ball.radius()) / ball.velocity();
    }
}
