package software.ulpgc.ballbouncer.model;

public record Ball(String id,
                   double x, double r,
                   double h, double v,
                   double g, double cr
) {}
