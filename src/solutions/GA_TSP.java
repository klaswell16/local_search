package solutions;

import core_algorithms.GeneticAlgorithm;

import core_algorithms.Individual;

import problems.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA_TSP extends GeneticAlgorithm<int[]> {

    private final TSP problem;

    public GA_TSP(int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }


    public Individual<int[]> reproduce(Individual<int[]> p, Individual<int[]> q) {
        int[] parent1 = p.getChromosome();
        int[] parent2 = q.getChromosome();
        int[] child = new int[parent1.length];

        Random rand = new Random();
        int startPos = rand.nextInt(parent1.length);
        int endPos = rand.nextInt(parent1.length - startPos) + startPos;

        for (int i = startPos; i <= endPos; i++) {
            child[i] = parent1[i];
        }

        int index = 0;
        for (int i = 0; i < parent2.length; i++) {
            if (index == startPos) {
                index = endPos + 1;
            }
            if (!contains(child, parent2[i])) {
                child[index++] = parent2[i];
            }
        }

        return new Individual<>(child, calcFitnessScore(child));
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Individual<int[]> mutate(Individual<int[]> indiv) {
        int[] chromosome = indiv.getChromosome().clone();

        Random rand = new Random();
        if (rand.nextDouble() <= getMutationRate()) {
            int index1 = rand.nextInt(chromosome.length);
            int index2;
            do {
                index2 = rand.nextInt(chromosome.length);
            } while (index1 == index2);


            int temp = chromosome[index1];
            chromosome[index1] = chromosome[index2];
            chromosome[index2] = temp;
        }

        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }

    public double calcFitnessScore(int[] chromosome){
        return 1/problem.cost(chromosome);
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
        int POPULATION_SIZE = 100;
        double ELITISM = 0.2;
        int SIZE = 26;

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
