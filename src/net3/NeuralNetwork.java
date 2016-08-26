/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net3;

import java.text.*;
import java.util.*;
 
public class NeuralNetwork {
    static {
        Locale.setDefault(Locale.ENGLISH);
    }
 
    final boolean isTrained = false;
    final Random rand = new Random();
    List<Neuron> inputLayer = new ArrayList<Neuron>();
    List<Neuron> hiddenLayer = new ArrayList<Neuron>();
    List<Neuron> outputLayer = new ArrayList<Neuron>();
    Neuron bias = new Neuron();
    final int randomWeightMultiplier = 1;
 
    final double epsilon = 0.00000000001;
 
    final double learningRate = 0.9f;
    final double momentum = 0.7f;
 
    double[] inputs;
 
    double expectedOutputs[];
    double resultOutputs[];
    double output[];
 
    // for weight update all
    final HashMap<String, Double> weightUpdate = new HashMap<String, Double>();
 
    public NeuralNetwork(int nbInput, int nbHidden, int nbOutput) {
 
        /**
         * Create all neurons and connections Connections are created in the
         * neuron class
         */
        
        //Input layer
        for (int j = 0; j < nbInput; j++) {
            Neuron neuron = new Neuron();
            inputLayer.add(neuron);
        }
        
        //Hidden layer
        for (int j = 0; j < nbHidden; j++) {
            Neuron neuron = new Neuron();
            neuron.addInConnectionsS(inputLayer);
            neuron.addBiasConnection(bias);
            hiddenLayer.add(neuron);
        }
        
        //Output layer
        for (int j = 0; j < nbOutput; j++) {
            Neuron neuron = new Neuron();
            neuron.addInConnectionsS(hiddenLayer);
            neuron.addBiasConnection(bias);
            outputLayer.add(neuron);
        }
 
        // initialize random weights
        for (Neuron neuron : hiddenLayer) {
            List<Synapse> connections = neuron.getAllInConnections();
            for (Synapse conn : connections) {
                double newWeight = getRandom();
                conn.setWeight(newWeight);
            }
        }
        for (Neuron neuron : outputLayer) {
            List<Synapse> connections = neuron.getAllInConnections();
            for (Synapse conn : connections) {
                double newWeight = getRandom();
                conn.setWeight(newWeight);
            }
        }
 
        // reset id counters
        Neuron.counter = 0;
        Synapse.counter = 0;
        
        inputs = new double[nbInput];
        output = new double[nbOutput];
        resultOutputs = new double[nbOutput];
        expectedOutputs = new double[nbOutput];
 
        if (isTrained) {
            trainedWeights();
            updateAllWeights();
        }
    }
 
    // random
    double getRandom() {
        return randomWeightMultiplier * (rand.nextDouble() * 2 - 1); // [-1;1[
    }
 
    /**
     * 
     * @param inputs
     *            There is equally many neurons in the input layer as there are
     *            in input variables
     */
    public void setInput(double inputs[]) {
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }
 
    public double[] getOutput() {
        double[] outputs = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++)
            outputs[i] = outputLayer.get(i).getOutput();
        return outputs;
    }
 
    /**
     * Calculate the output of the neural network based on the input The forward
     * operation
     */
    public void activate() {
        for (Neuron n : hiddenLayer)
            n.calculateOutput();
        for (Neuron n : outputLayer)
            n.calculateOutput();
    }
 
    /**
     * all output propagate back
     * 
     * @param expectedOutput
     *            first calculate the partial derivative of the error with
     *            respect to each of the weight leading into the output neurons
     *            bias is also updated here
     */
    public void applyBackpropagation(double expectedOutput[]) {
 
        // error check, normalize value ]0;1[
        for (int i = 0; i < expectedOutput.length; i++) {
            double d = expectedOutput[i];
            if (d < 0 || d > 1) {
                if (d < 0)
                    expectedOutput[i] = 0 + epsilon;
                else
                    expectedOutput[i] = 1 - epsilon;
            }
        }
 
        int i = 0;
        for (Neuron n : outputLayer) {
            List<Synapse> connections = n.getAllInConnections();
            for (Synapse con : connections) {
                double ak = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double desiredOutput = expectedOutput[i];
 
                double partialDerivative = -ak * (1 - ak) * ai
                        * (desiredOutput - ak);
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
            i++;
        }
 
        // update weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            List<Synapse> connections = n.getAllInConnections();
            for (Synapse con : connections) {
                double aj = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double sumKoutputs = 0;
                int j = 0;
                for (Neuron out_neu : outputLayer) {
                    double wjk = out_neu.getConnection(n.id).getWeight();
                    double desiredOutput = (double) expectedOutput[j];
                    double ak = out_neu.getOutput();
                    j++;
                    sumKoutputs = sumKoutputs
                            + (-(desiredOutput - ak) * ak * (1 - ak) * wjk);
                }
 
                double partialDerivative = aj * (1 - aj) * ai * sumKoutputs;
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
        }
    }
 
    double train(double[] signals, double[] target) {
        
        inputs = signals;
        expectedOutputs = target;
        double error = 0;
        setInput(inputs);

        activate();

        output = getOutput();
        resultOutputs = output;

        for (int j = 0; j < expectedOutputs.length; j++) {
            double err = 0.5 * Math.pow(output[j] - expectedOutputs[j], 2);
            error += err;
        }
        
        applyBackpropagation(expectedOutputs);
        
        System.out.println("out = " + output[0] + ", expected = " + expectedOutputs[0] + ", error = " + error);

        return error;
    }
    
    public void test(double[] signals)
    {
        inputs = signals;
        
        setInput(inputs);

        activate();

        output = getOutput();
        resultOutputs = output;
        
        System.out.println("out = " + output[0]);
    }
 
    String weightKey(int neuronId, int conId) {
        return "N" + neuronId + "_C" + conId;
    }
 
    /**
     * Take from hash table and put into all weights
     */
    public void updateAllWeights() {
        // update weights for the output layer
        for (Neuron n : outputLayer) {
            List<Synapse> connections = n.getAllInConnections();
            for (Synapse con : connections) {
                String key = weightKey(n.id, con.id);
                double newWeight = weightUpdate.get(key);
                con.setWeight(newWeight);
            }
        }
        // update weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            List<Synapse> connections = n.getAllInConnections();
            for (Synapse con : connections) {
                String key = weightKey(n.id, con.id);
                double newWeight = weightUpdate.get(key);
                con.setWeight(newWeight);
            }
        }
    }
 
    // trained data
    void trainedWeights() {
        weightUpdate.clear();
         
        weightUpdate.put(weightKey(3, 0), 1.03);
        weightUpdate.put(weightKey(3, 1), 1.13);
        weightUpdate.put(weightKey(3, 2), -.97);
        weightUpdate.put(weightKey(4, 3), 7.24);
        weightUpdate.put(weightKey(4, 4), -3.71);
        weightUpdate.put(weightKey(4, 5), -.51);
        weightUpdate.put(weightKey(5, 6), -3.28);
        weightUpdate.put(weightKey(5, 7), 7.29);
        weightUpdate.put(weightKey(5, 8), -.05);
        weightUpdate.put(weightKey(6, 9), 5.86);
        weightUpdate.put(weightKey(6, 10), 6.03);
        weightUpdate.put(weightKey(6, 11), .71);
        weightUpdate.put(weightKey(7, 12), 2.19);
        weightUpdate.put(weightKey(7, 13), -8.82);
        weightUpdate.put(weightKey(7, 14), -8.84);
        weightUpdate.put(weightKey(7, 15), 11.81);
        weightUpdate.put(weightKey(7, 16), .44);
    }
 
    public void printAllWeights() {
        System.out.println("printAllWeights");
        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            List<Synapse> connections = n.getAllInConnections();
            for (Synapse con : connections) {
                double w = con.getWeight();
                System.out.println("n=" + n.id + " c=" + con.id + " w=" + w);
            }
        }
        // weights for the output layer
        for (Neuron n : outputLayer) {
            List<Synapse> connections = n.getAllInConnections();
            for (Synapse con : connections) {
                double w = con.getWeight();
                System.out.println("n=" + n.id + " c=" + con.id + " w=" + w);
            }
        }
        System.out.println();
    }
}