package software.ulpgc.ballbouncer.view;

import java.util.List;

public interface BallDisplay {
    void drawBall(List<Circle> circles);
    void on(Grabbed grabbed);
    void off(Released released);

    record Circle(String id, int xComponent, int yComponent, int radius) {
        public boolean isAt(int xComponent, int yComponent) {
            double xCoordDiff = Math.abs(xComponent - this.xComponent);
            double yCoordDiff = Math.abs(yComponent - this.yComponent);
            return xCoordDiff < this.radius && yCoordDiff < this.radius;
        }
    }

    interface Grabbed {
        void at(Circle circle);
    }

    interface Released {
        void at(Circle circle);
    }
}
