package conc.controller;
import conc.view.SimulationView;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class SequentialBodySimulationMain {

    public static void main(String[] args) throws InterruptedException {
                
    	SimulationView viewer = new SimulationView(620,620);

    	Simulator sim = new Simulator(viewer);
        sim.execute(50000);
    }
}
