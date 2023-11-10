package org.example;

import java.util.Random;

public class NewGa {
    int popSize = 100;
    double[] fitness = new double[popSize];
    int dimensions = 3;
    double[][] population = new double[popSize][3];


    public double fit(double[] p) {
        double x = p[0];
        double y = p[1];
        double z = p[2];
        return 2 * x * z * Math.exp(-x) - 2 * Math.pow(y, 3) + Math.pow(y, 2) - 3 * Math.pow(z, 3);
    }

    public double[][] initializePopulation(int popSize, int dimensions) {
        double[][] population = new double[popSize][dimensions];
        Random random = new Random();

        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < dimensions; j++) {
                population[i][j] = random.nextDouble() * 100; // Random values between 0 and 100
            }
        }

        return population;
    }

    public int getFittestIndividual() {
        double maxFitness = Double.NEGATIVE_INFINITY;
        int index = 0;

        for (int i = 0; i < popSize; i++) {
            if (fitness[i] > maxFitness) {
                maxFitness = fitness[i];
                index = i;
            }
        }

        return index;
    }

    public int getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < popSize; i++) {
            if (fitness[i] > fitness[maxFit1]) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (fitness[i] > fitness[maxFit2]) {
                maxFit2 = i;
            }
        }
        return maxFit2;
    }

    public double[] crossover(double[] fittest, double[] secondFittest) {

        Random random = new Random();
        double[] child = new double[dimensions];

        // Randomly select the crossover point
        int crossoverPoint = random.nextInt(fittest.length);

        for (int i = 0; i < fittest.length; i++) {
            if (i < crossoverPoint) {
                child[i] = fittest[i]; // Genes from parent1 before the crossover point
            } else {
                child[i] = secondFittest[i]; // Genes from parent2 after the crossover point
            }
        }

        return child;
    }

    public double[] mutation(double[] child) {
        Random random = new Random();

        // Randomly select a mutation point
        int mutationPoint = random.nextInt(child.length);

        // Apply mutation to the randomly selected point
        double randomValue = random.nextDouble() * 100; // Mutate the value at the selected mutation point

        child[mutationPoint] = randomValue;

        return child;

    }

    public int getLeastFittestIndex() {
        double minFitVal = Double.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < popSize; i++) {
            if (fitness[i] <= minFitVal) {
                minFitVal = fitness[i];
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    public void replaceLeastFittestWithMostFittest() {
        int leastFittestIndex = getLeastFittestIndex();
        int mostFittestIndex = getFittestIndividual();

        // Replace the least fittest individual with the most fittest individual
        for (int i = 0; i < 3; i++) {
            population[leastFittestIndex][i] = population[mostFittestIndex][i];
        }
        // Recalculate the fitness of the replaced individual
        fitness[leastFittestIndex] = fit(population[leastFittestIndex]);
    }





        public static void main (String[]args) {

            int popSize = 100;
            int dimensions = 3;
            int generations = 100;

            NewGa ga = new NewGa();
             double[][] population = new double[popSize][dimensions];

            for (int i = 0; i < ga.popSize; i++) {
                population  = ga.initializePopulation(ga.popSize, ga.dimensions);
            }

            //evolution
            for (int gen = 0; gen < generations; gen++) {
                double[][] newPopulation = new double[popSize][dimensions];

                // Calculate fitness for each individual in the population
                double[] fitness = new double[ga.popSize];
                for (int i = 0; i < ga.popSize; i++) {
                    fitness[i] = ga.fit(population[i]);
                }


                // Perform selection, crossover, and mutation to create the new population
                for (int i = 0; i < ga.popSize; i++) {
                    //selection
                    int parent1 = ga.getFittestIndividual();
                    int parent2 = ga.getSecondFittest();

                    //crossover
                    double[] child = ga.crossover(population[parent1], population[parent2]);

                    //mutation
                    newPopulation[i] = ga.mutation(child);

                }
                // Replace the least fittest individual with the most fittest individual in the new population
                ga.replaceLeastFittestWithMostFittest();

                // Replace the old population with the new one
                population = newPopulation;

            }

                // Find the solution with the best fitness
                double bestFitness = Double.NEGATIVE_INFINITY;
                double[] bestSolution = null;
                for (int i = 0; i < ga.popSize; i++) {
                    double currentFitness = ga.fit(population[i]);
                    if (currentFitness > bestFitness) {
                        bestFitness = currentFitness;
                        bestSolution = population[i];
                    }

                }
                System.out.println("Best Solution:");
                System.out.println("Fitness: " + bestFitness);
                System.out.println("Parameters (x, y, z): " + bestSolution[0] + ", " + bestSolution[1] + ", " + bestSolution[2]);


        }

    }



