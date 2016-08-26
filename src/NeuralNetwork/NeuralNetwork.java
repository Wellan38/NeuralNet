package NeuralNetwork;


import java.util.ArrayList;
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
    
    public static final Double R = 0.9;
    
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
    
    public NeuralNetwork(int nbInput, int nbHidden, int nbOutput)
    {
        this.input = new ArrayList();
        
        for (int i = 0; i < nbInput; i++)
        {
            List<Synapse> in = new ArrayList();
            in.add(new Synapse(1));
            //in.add(new Synapse(1, Math.random()));
            Neuron n = new Neuron(in, new ArrayList());
            
            input.add(n);
        }
        
        this.hidden = new ArrayList();
        
        for (int i = 0; i < nbHidden; i++)
        {
            Neuron n = new Neuron(new ArrayList(), new ArrayList());
            
            for (Neuron ni : input)
            {
                Synapse s = new Synapse(-1 + 2. * Math.random());
                ni.getOutput().add(s);
                n.getInput().add(s);
            }
            
            //n.getInput().add(new Synapse(-1, Math.random()));
            
            hidden.add(n);
        }
        
        this.output = new ArrayList();
        
        for (int i = 0; i < nbOutput; i++)
        {
            Synapse s = new Synapse(1);
            List<Synapse> out = new ArrayList();
            out.add(s);
            
            Neuron n = new Neuron(new ArrayList(), out);
            
            for (Neuron nh : hidden)
            {
                Synapse sh = new Synapse(-1 + 2 * Math.random());
                nh.getOutput().add(sh);
                n.getInput().add(sh);
            }
            
            //n.getInput().add(new Synapse(-1, Math.random()));
            
            output.add(n);
        }
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
    
    public void setInputValues(List<Double> values)
    {
        if (input.size() == values.size())
        {
            for (int i = 0; i < values.size(); i++)
            {
                input.get(i).getInput().get(0).setInput(values.get(i));
            }
        }
    }
    
    public void setDesired(List<Double> desired)
    {
        if (output.size() == desired.size())
        {
            this.desired = new LinkedHashMap();
            
            for (int i = 0; i < desired.size(); i++)
            {
                this.desired.put(output.get(i), desired.get(i));
            }
        }
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
            List<Synapse> in = n.getInput();
            
            Double o_o = n.getOutput().get(0).getInput();
            
            Double d = (desired.get(n) - o_o) * o_o * (1. - o_o);
            
            for (Synapse s : in)
            {
                d_k.put(s, d);
                Double o_h = s.getInput();
                Double D = d * o_h;
                
                s.setWeight(s.getWeight() + R * D);
            }
        }
        
        for (Neuron n : hidden)
        {
            List<Synapse> in = n.getInput();
            List<Synapse> out = n.getOutput();
            
            Double o_h = out.get(0).getInput();
            Double d = 0.;
            
            for (Synapse s : out)
            {
                d += d_k.get(s) * s.getWeight();
            }
            
            d *= o_h * (1 - o_h);            
            
            for (Synapse s : in)
            {
                Double o_i = s.getInput();
                
                Double D = o_i * d;
                
                s.setWeight(s.getWeight() + R * D);
            }
        }
    }
    
    public void step(List<Double> in, List<Double> target)
    {
        setInputValues(in);
        setDesired(target);
        
        compute();
        
        updateWeigths();
        
        System.out.print("x1 = " + input.get(0).getInput().get(0).getInput()+ ", x2 = " + input.get(1).getInput().get(0).getInput() + ", out = " + output.get(0).getOutput().get(0).getInput() + ", desired = " + desired.get(output.get(0)));
        
        if (output.get(0).getOutput().get(0).getInput() != desired.get(output.get(0)))
        {
            System.out.println(" différent !");
        }
        else
        {
            System.out.println();
        }
    }
}
