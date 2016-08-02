/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class Synapse {
    
    private double input;
    private double weight;
    private double output;

    public Synapse() {
    }

    public Synapse(double input, double weight) {
        this.input = input;
        this.weight = weight;
        this.output = input * weight;
    }
    
    public Synapse(double weight) {
        this.input = 0;
        this.weight = weight;
        this.output = 0;
    }

    public double getInput() {
        return input;
    }

    public double getWeight() {
        return weight;
    }

    public double getOutput() {
        return output;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void compute()
    {
        output = input * weight;
    }
}
