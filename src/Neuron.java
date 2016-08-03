
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class Neuron {
    private List<Synapse> input;
    private List<Synapse> output;

    public Neuron(List<Synapse> input, List<Synapse> output) {
        this.input = input;
        this.output = output;
    }

    public Neuron() {
    }

    public List<Synapse> getInput() {
        return input;
    }

    public List<Synapse> getOutput() {
        return output;
    }

    public void setInput(List<Synapse> input) {
        this.input = input;
    }

    public void setOutput(List<Synapse> output) {
        this.output = output;
    }
    
    public void compute()
    {
        double res = 0;
        for (Synapse s : input)
        {
            s.compute();
            res += s.getOutput();
        }
        
        res = 1 / (1 + Math.exp(-res));
        
        for (Synapse s : output)
        {
            s.setInput(res);
        }
    }
}
