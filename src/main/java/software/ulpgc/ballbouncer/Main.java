package software.ulpgc.ballbouncer;

import software.ulpgc.ballbouncer.model.Ball;
import software.ulpgc.ballbouncer.presenter.BallPresenter;
import software.ulpgc.ballbouncer.simulationLogic.BallSimulator;
import software.ulpgc.ballbouncer.swingApp.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        BallPresenter presenter = new BallPresenter(mainFrame.getBallDisplay(), new BallSimulator(0.001))
                .add(new Ball("B1", -8, 0.9, 15, 0, -9.8, 0.5))
                .add(new Ball("B2", 0, 1.4, 20, 0, -8, 0.3))
                .add(new Ball("B3", 8, 0.4, 15, 0, -9, 0.7));
        presenter.execute();
        mainFrame.setVisible(true);
    }
}
