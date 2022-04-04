package conc.controller;

import conc.model.Body;
import conc.model.Boundary;
import conc.model.P2d;
import conc.model.V2d;
import conc.model.agent.MasterAgent;
import conc.view.SimulationView;

import java.util.ArrayList;
import java.util.Random;

public class Simulator {
	/* bodies in the field */
	private ArrayList<Body> bodies;
	/* boundary of the field */
	private Boundary bounds;

	private int nBodies, nSteps, nWorkers;

	public Simulator(int nBodies, int nSteps, int nWorkers) {
		this.nBodies = nBodies;
		this.nSteps = nSteps;
		this.nWorkers = nWorkers;
//		testBodySetManyBodies();
		testWithNumBodies(nBodies);
	}
	
	public void execute() {
		//SimulationView viewer = new SimulationView(620,620);
		MasterAgent master = new MasterAgent(/*viewer,*/ bodies, bounds, nSteps , nWorkers);
		master.start();
	}

	private void testBodySetManyBodies() {
		bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);
		int nBodies = 1000;
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
			double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}

	private void testWithNumBodies(int n) {
		bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);

		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
			double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}
}
