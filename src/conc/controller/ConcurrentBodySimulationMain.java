package conc.controller;

public class ConcurrentBodySimulationMain {

    public static void main(String[] args) {
        Simulator sim = new Simulator();
        sim.execute(50000);
    }
}
