
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class NeuralNetwork {
    
    public static final Double R = 0.5;
    
    private List<Neuron> input;
    private List<Neuron> hidden;
    private List<Neuron> output;
    private Map<Neuron, Double> desired;

    public NeuralNetwork() {
    }

    public NeuralNetwork(List<Neuron> input, List<Neuron> hidden, List<Neuron> output, Map<Neuron, Double> desired) {
        this.input = input;
        this.hidden = hidden;
        this.output = output;
        this.desired = desired;
    }

    public List<Neuron> getInput() {
        return input;
    }

    public List<Neuron> getHidden() {
        return hidden;
    }

    public List<Neuron> getOutput() {
        return output;
    }

    public Map<Neuron, Double> getDesired() {
        return desired;
    }

    public void setInput(List<Neuron> input) {
        this.input = input;
    }

    public void setHidden(List<Neuron> hidden) {
        this.hidden = hidden;
    }

    public void setOutput(List<Neuron> output) {
        this.output = output;
    }

    public void setDesired(Map<Neuron, Double> desired) {
        this.desired = desired;
    }
    
    public void compute()
    {
        for (Neuron n : input)
        {
            n.compute();
        }
        
        for (Neuron n : hidden)
        {
            n.compute();
        }
        
        for (Neuron n : output)
        {
            n.compute();
        }
    }
    
    public void updateWeigths()
    {
        for (Neuron n : output)
        {
            List<Synapse> syn = n.getInput();
            
            for (Synapse s : syn)
            {
                Double o_h = s.getOutput();
                Double o_o = n.getOutput().getInput();
                Double D = (o_o - desired.get(n)) * o_o * (1 - o_o) * o_h;
                
                s.setWeight(s.getWeight() - R * D);
            }
        }
    }
}
