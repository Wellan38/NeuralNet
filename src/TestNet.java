
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
public class TestNet {
    public static void main(String[] args)
    {
        // Input neuron 1
        Synapse x1 = new Synapse(0.5, 1);
        Synapse y1 = new Synapse(0.2);
        
        List<Synapse> i1 = new ArrayList();
        i1.add(x1);
        
        Neuron n1 = new Neuron(i1, y1);
        
        // Input neuron 2
        Synapse x2 = new Synapse(0.4, 1);
        Synapse y2 = new Synapse(0.3);
        
        List<Synapse> i2 = new ArrayList();
        i2.add(x2);
        
        Neuron n2 = new Neuron(i2, y2);
        
        // Hidden neuron
        List<Synapse> h = new ArrayList();
        h.add(y1);
        h.add(y2);
        
        Synapse z = new Synapse(0.3);
        
        Neuron n3 = new Neuron(h, z);
        
        //Ouput neuron
        List<Synapse> o = new ArrayList();
        o.add(z);
        
        Synapse fin = new Synapse(1);
        
        Neuron n4 = new Neuron(o, fin);
        
        //Building the network
        List<Neuron> input = new ArrayList();
        input.add(n1);
        input.add(n2);
        
        List<Neuron> hidden = new ArrayList();
        hidden.add(n3);
        
        List<Neuron> output = new ArrayList();
        output.add(n4);
        
        Map<Neuron, Double> desired = new LinkedHashMap();
        
        desired.put(n4, 1.);
        
        NeuralNetwork net = new NeuralNetwork(input, hidden, output, desired);
        
        net.compute();
        
        System.out.println(fin.getInput());
    }
}
