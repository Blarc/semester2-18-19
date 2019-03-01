import java.util.Map;
import java.lang.Math;

public class Potenca extends Izraz {
    private Izraz e1;
    private Izraz e2;

    public Potenca(Izraz e1, Izraz e2) {
        super();
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public Double eval(Map<String, Double> env) {
        return Math.pow(e1.eval(env), e2.eval(env));
    }

    @Override
    public String toString() {
        return "(^" + e1 + " " + e2 + ")";
    }

    @Override
    public Izraz poenostavi() {
        Izraz p1 = e1.poenostavi();
        Izraz p2 = e2.poenostavi();

        if (p1 instanceof Konstanta && p2 instanceof Konstanta) {
            return new Konstanta(Math.pow(p1.eval(null), p2.eval(null)));
        }

        return this;
    }
}