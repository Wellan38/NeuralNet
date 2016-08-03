
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        Map<Synapse, Double> d_k = new LinkedHashMap();
        for (Neuron n : output)
        {
            List<Synapse> syn = n.getInput();
            
            Double o_o = n.getOutput().get(0).getInput();
            
            Double d = (o_o - desired.get(n)) * o_o * (1 - o_o);
            
            for (Synapse s : syn)
            {
                d_k.put(s, d);
                Double o_h = s.getOutput();
                Double D = d * o_h;
                
                s.setWeight(s.getWeight() - R * D);
            }
        }
        
        for (Neuron n : hidden)
        {
            List<Synapse> in = n.getInput();
            List<Synapse> out = n.getOutput();
            
            Double o_h = n.getOutput().get(0).getInput();
            Double d = 0.;
            
            for (Synapse s : out)
            {
                d += d_k.get(s) * s.getWeight();
            }
            
            d *= o_h * (1 - o_h);
            
            for (Synapse s : in)
            {
                Double o_i = s.getOutput();
                
                Double D = o_i * d;
                
                s.setWeight(s.getWeight() - R * D);
            }
        }
    }
    
    public void step(List<Double> in, List<Double> target)
    {
        if (input.size() == in.size() && output.size() == target.size())
        {
            for (int i = 0; i < input.size(); i++)
            {
                input.get(i).getInput().get(0).setInput(in.get(i));
            }
            
            for (int i = 0; i < output.size(); i++)
            {
                desired.replace(output.get(i), target.get(i));
            }
        }
        
        compute();
        
        updateWeigths();
        
        System.out.println("x1 = " + input.get(0).getInput().get(0).getInput()+ ", x2 = " + input.get(1).getInput().get(0).getInput() + ", out = " + output.get(0).getOutput().get(0).getInput());
    }
}
