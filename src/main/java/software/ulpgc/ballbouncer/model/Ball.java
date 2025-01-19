package software.ulpgc.ballbouncer.model;

public record Ball(String id,
                   double xAxisPosition, double radius,
                   double initialHeight, double velocity,
                   double gravity, double cr
) {}
