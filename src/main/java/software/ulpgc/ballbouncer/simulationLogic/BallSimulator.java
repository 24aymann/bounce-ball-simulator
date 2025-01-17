package software.ulpgc.ballbouncer.simulationLogic;

import software.ulpgc.ballbouncer.model.Ball;

public class BallSimulator {
    private final double dt;

    public BallSimulator(double dt) {
        this.dt = dt;
    }

    public Ball simulate(Ball ball){
        return new Ball(
                ball.id(), ball.x(),
                ball.r(), newHeightOf(ball),
                newVelocityOf(ball), ball.g(),
                ball.cr()
        );
    }

    private double newHeightOf(Ball ball) {
        return willBounce(ball) ?
                newHeightAfterBouncedOf(ball) : ball.h() + ball.v() * dt;
    }

    private double newVelocityOf(Ball ball) {
        return willBounce(ball) ?
                newVelocityAfterBouncedOf(ball) : ball.v() + ball.g() * dt;
    }

    private boolean willBounce(Ball ball) {
        return ball.v() < 0 && dt > timeToBounceOf(ball);
    }

    private double newHeightAfterBouncedOf(Ball ball) {
        return ball.r() + newVelocityAfterBouncedOf(ball) * (dt - timeToBounceOf(ball));
    }

    private double newVelocityAfterBouncedOf(Ball ball) {
        return - ball.cr() * (ball.v() + ball.g() * timeToBounceOf(ball));
    }

    private static double timeToBounceOf(Ball ball) {
        return - (ball.h() - ball.r()) / ball.v();
    }
}
