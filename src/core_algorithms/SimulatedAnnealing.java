package core_algorithms;

import problems.Problem;


public abstract class SimulatedAnnealing<S> {
    private long time;
    private double temp;
    private final Problem<S> problem;

    public SimulatedAnnealing(long initTime, double initTemp, Problem<S> p){
        this.time = initTime;
        this.temp = initTemp;
        this.problem = p;
    }

    public abstract double schedule(long time, double temp);

    public void search(){
        S currentState = problem.getInitState();
        while (temp>0){
            S newState = problem.generateNewState(currentState);
            double deltaE = problem.cost(newState) - problem.cost(currentState);
            if(accept(deltaE, temp)){
                currentState = newState;

            }
            time ++;
            temp = schedule(time, temp);
        }
    }
}
