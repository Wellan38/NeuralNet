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
public class TestNet {
    
    public static void main(String[] args)
    {
        // Input neuron 1
        Synapse x1 = new Synapse(Math.random(), 1);
        Synapse y1 = new Synapse(Math.random());
        
        List<Synapse> i1 = new ArrayList();
        i1.add(x1);
        
        List<Synapse> o1 = new ArrayList();
        o1.add(y1);
        
        Neuron n1 = new Neuron(i1, o1);
        
        // Input neuron 2
        Synapse x2 = new Synapse(Math.random(), 1);
        Synapse y2 = new Synapse(Math.random());
        
        List<Synapse> i2 = new ArrayList();
        i2.add(x2);
        
        List<Synapse> o2 = new ArrayList();
        o2.add(y2);
        
        Neuron n2 = new Neuron(i2, o2);
        
        // Hidden neuron
        List<Synapse> h = new ArrayList();
        h.add(y1);
        h.add(y2);
        
        Synapse z = new Synapse(Math.random());
        
        List<Synapse> o3 = new ArrayList();
        o3.add(z);
        
        Neuron n3 = new Neuron(h, o3);
        
        //Ouput neuron
        List<Synapse> o = new ArrayList();
        o.add(z);
        
        Synapse fin = new Synapse(1);
        
        List<Synapse> o_o = new ArrayList();
        o_o.add(fin);
        
        Neuron n4 = new Neuron(o, o_o);
        
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
        
        for (int i = 1; i <= 4000; i++)
        {
            Double s1, s2;
            
            if (Math.random() >= 0.5)
            {
                s1 = 1.;
            }
            else
            {
                s1 = 0.;
            }
            
            if (Math.random() >= 0.5)
            {
                s2 = 1.;
            }
            else
            {
                s2 = 0.;
            }
            
            Double des;
            
//            if (s1 == 1 || s2 == 1)
//            {
//                des = 1.;
//            }
//            else
//            {
//                des = 0.;
//            }
            if (s1 == 1)
            {
                des = 1.;
            }
            else
            {
                des = 0.;
            }
            
            List<Double> signals = new ArrayList();
            
            signals.add(s1);
            signals.add(s2);
            
            List<Double> target = new ArrayList();
            
            target.add(des);
            
            net.step(signals, target);
        }
    }
}
