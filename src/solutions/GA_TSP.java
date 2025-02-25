package solutions;

import core_algorithms.GeneticAlgorithm;

import core_algorithms.Individual;

import problems.TSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GA_TSP extends GeneticAlgorithm<int[]> {

    private final TSP problem;

    public GA_TSP(int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }

    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q) {

        Random r = new Random();
        int startPos = r.nextInt(p.getChromosome().length);
        int endPos = r.nextInt(p.getChromosome().length);
        //If startPos is greater than endPos, swap them.
        if (startPos > endPos) {
            int t = startPos;
            startPos = endPos;
            endPos = t;
        }

        int[] childChromosome = Arrays.copyOf(p.getChromosome(), p.getChromosome().length);

        for (int i = 0; i < startPos; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }
        for (int i = endPos + 1; i<q.getChromosome().length; i++) {
            childChromosome[i] = q.getChromosome()[i];
        }
        return new Individual<>(childChromosome, calcFitnessScore(childChromosome));
    }

    public Individual<int[]> mutate(Individual<int[]> indiv){
        int[] chromosome = problem.generateNewState(indiv.getChromosome());
        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }

    public double calcFitnessScore(int[] chromosome){
        return problem.getN()*(problem.getN()-1)/(double)2 - problem.cost(chromosome);
    }

    public List<Individual<int[]>> generateInitPopulation(int popSize){
        List<Individual<int[]>> population = new ArrayList<>(popSize);
        for(int i=0; i<popSize; i++){
            int[] chromosome = problem.getInitState();
            population.add(new Individual<>(chromosome, calcFitnessScore(chromosome)));
        }
        return population;
    }

    public static void main(String[] args) {
        int MAX_GEN = 200;
        double MUTATION_RATE = 0.1;
        int POPULATION_SIZE = 5000;
        double ELITISM = 0.2;
        int SIZE = 17;

        TSP problem = new TSP(SIZE);
        GA_TSP agent =
                new GA_TSP(MAX_GEN,MUTATION_RATE,ELITISM,problem);
        Individual<int[]> best =
                agent.evolve(agent.generateInitPopulation(POPULATION_SIZE));
        System.out.println("Best solution:");
        problem.printState(best.getChromosome());
        System.out.println("Best cost is: "+problem.cost(best.getChromosome()));
    }
}
