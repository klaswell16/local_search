package core_algorithms;

import problems.Problem;

import java.util.Random;

public abstract class SimulatedAnnealing<S> {
    private long time;
    private double temp;
    private final Problem<S> problem;

    public SimulatedAnnealing(long initTime,
                              double initTemp,
                              Problem<S> p){
        this.time = initTime;
        this.temp = initTemp;
        this.problem = p;
    }

    public abstract double schedule (long time, double temp);

    public void search(){
        S currentState = problem.getInitState();
        while(temp>0){
            if(problem.cost(currentState)==problem.goalCost()){
                //the goal state has been reached; stop searching
                break;
            }
            S newState = problem.generateNewState(currentState);
            double deltaE = problem.cost(currentState) - problem.cost(newState);
            if(accept(deltaE, temp)){
                currentState = newState;
            }
            time ++;
            temp = schedule(time, temp);
        }
        problem.printState(currentState);
        System.out.println("The final cost is: "+problem.cost(currentState));
    }

    public boolean accept (double delta, double temp){
        if(delta > 0){
            return true;
        }else{
            double probability = Math.exp(delta/temp);
            assert probability >=0 && probability <= 1;
            Random r = new Random();
            return probability>r.nextDouble();
        }

    }

}
