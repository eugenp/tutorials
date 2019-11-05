package com.baeldung.algorithms.ga.binary;

import lombok.Data;

@Data
public class Individual {

    protected int defaultGeneLength = 64;
    private byte[] genes = new byte[defaultGeneLength];
    private int fitness = 0;

    public Individual() {
        for (int i = 0; i < genes.length; i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    protected byte getSingleGene(int index) {
        return genes[index];
    }

    protected void setSingleGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = SimpleGeneticAlgorithm.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < genes.length; i++) {
            geneString += getSingleGene(i);
        }
        return geneString;
    }

}
