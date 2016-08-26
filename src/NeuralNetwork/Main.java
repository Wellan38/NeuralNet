package NeuralNetwork;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex-Laptop
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    
    final static double R = 0.5;
    
    public static void main(String[] args)
    {
        double w1 = Math.random(), w2 = Math.random();
        
        for (int iter = 1; iter <= 100000; iter++)
        {
            int x;
            
            if (Math.random() >= 0.5)
            {
                x = 1;
            }
            else
            {
                x = 0;
            }
            
            double p1 = (double)x * w1;
            double y = 1 / (1 + Math.exp(- p1));
            
            double p2 = y * w2;
            double z = 1 / (1 + Math.exp(- p2));
            
            double P = -0.5 * Math.pow((double)x - z, 2);
            
            double dP1 = ((double)x - z) * z * (1 - z) * w2 * y * (1 - y) * (double)x;
            double dP2 = ((double)x - z) * z * (1 - z) * y;
            
            w1 += R * dP1;
            w2 += R * dP2;
            
            System.out.println("x = " + x + ", z = " + z + ", P = " + P);
        }
    }
    
}
