/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xtest;

import org.netgene.ga.Population;
import org.netgene.ga.chromosome.IntegerChromosome;

/**
 *
 * @author cbaba
 */
public class FitnessSharing {
    private static final double SIGMA_SHARE = 2.0; // Sharing distance threshold
    private static final double ALPHA_SHARE = 1.0; // Sharing exponent

    public static double calculateSharedFitness(double originalFitness, Population population) {
        int similarCount = countSimilarSolutions(population);
        double sharingValue = calculateSharingValue(similarCount);
        double sharedFitness = originalFitness / sharingValue;
        return sharedFitness;
    }

    private static int countSimilarSolutions(Population population) {
        int similarCount = 0;
        for (int i = 0; i < population.size(); i++) {
            IntegerChromosome chromosome = (IntegerChromosome)population.getIndividual(i).getChromosome();
            int[] solution1 = chromosome.toArray();
            for (int j = i + 1; j < population.size(); j++) {
                chromosome = (IntegerChromosome)population.getIndividual(j).getChromosome();
                int[] solution2 = chromosome.toArray();
                double distance = calculateDistance(solution1, solution2);
                if (distance < SIGMA_SHARE) {
                    similarCount++;
                }
            }
        }
        return similarCount;
    }

    private static double calculateSharingValue(int similarCount) {
        double sharingValue = 1.0;
        if (similarCount > 0) {
            sharingValue = 1.0 / (1.0 + Math.pow(similarCount / SIGMA_SHARE, ALPHA_SHARE));
        }
        return sharingValue;
    }

    private static double calculateDistance(int[] solution1, int[] solution2) {
        // Implement distance calculation between two solutions
        // It can be Euclidean distance or Hamming distance, based on your problem domain
        if (solution1.length != solution2.length) {
        throw new IllegalArgumentException("Solution dimensions do not match.");
    }

    int sum = 0;
    for (int i = 0; i < solution1.length; i++) {
        int diff = solution1[i] - solution2[i];
        sum += Math.pow(diff, 2);
    }

    double distance = Math.sqrt(sum);
    return distance;
    }
}
