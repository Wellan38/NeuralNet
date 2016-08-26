package NeuralNetwork;


import java.util.ArrayList;
import java.util.List;
import static net2.Main.f;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class TestNet2 {
    
    public static void main(String[] args)
    {
        Synapse x = new Synapse(Math.random());
        Synapse y = new Synapse(Math.random());
        Synapse bias = new Synapse(1, Math.random());
        Synapse z = new Synapse(Math.random());

        List<Synapse> in = new ArrayList();
        in.add(x);
        in.add(y);
        in.add(bias);
        
        List<Synapse> out = new ArrayList();
        out.add(z);
        
        Neuron n = new Neuron(in, out);
        
        int cpt = 0;
        
        for (int i = 0; i < 4000; i++)
        {
//            double a = -2. + 4 * Math.random();
//            double b = -2. + 4 * Math.random();
            double a = (int)(2 * Math.random());
            double b = (int)(2 * Math.random());

            int answer = 1;
//            if (b < f(a)) answer = -1;
            if (a == 0 && b == 0) answer = 0;
            
            n.getInput().get(0).setInput(a);
            n.getInput().get(1).setInput(b);
            
            n.compute();
            
            double guess = n.getOutput().get(0).getInput();
            
            double error = answer - guess;
            
            n.getInput().get(0).setWeight(n.getInput().get(0).getWeight() + 0.01 * error * a);
            n.getInput().get(1).setWeight(n.getInput().get(1).getWeight() + 0.01 * error * b);
            n.getInput().get(2).setWeight(n.getInput().get(2).getWeight() + 0.01 * error * 1);
            
            System.out.print("guess = " + guess + ", target = " + answer);
            
            if (answer != guess)
            {
                System.out.println(" différent !");
                cpt++;
            }
            else
            {
                System.out.println();
            }
        }
        
        System.out.println(cpt);
        //System.out.println("w1 = " + n.getInput().get(0).getWeight() + ", w2 = " + n.getInput().get(1).getWeight() + ", wb = " + n.getInput().get(2).getWeight());
    }
}
