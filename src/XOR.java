/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class XOR {
    
    public static final double R = 0.5;
    public static final int ITER = 1000;
    
    public static void main(String[] args)
    {
        double w1 = Math.random();
        double w2 = Math.random();
        double w3 = Math.random();
        double w4 = Math.random();
        
        for (int i = 1; i <= ITER; i++)
        {
            double x1, x2;
            
            if (Math.random() >= 0.5)
            {
                x1 = 1;
            }
            else
            {
                x1 = 0;
            }
            
            if (Math.random() >= 0.5)
            {
                x2 = 1;
            }
            else
            {
                x2 = 0;
            }
            
            double d;
            
            if (x1 == x2)
            {
                d = 0;
            }
            else
            {
                d = 1;
            }
            
            double p1 = x1 * w1;
            double y1 = 1 / (1 + Math.exp(-p1));
            
            double p2 = x2 * w2;
            double y2 = 1 / (1 + Math.exp(-p2));
            
            double s = y1 * w3 + y2 * w4;
            double z = 1 / (1 + Math.exp(-s));
            
            double E = 0.5 * Math.pow(d-z, 2);
            
            double dE4 = -(d-z) * z*(1-z) * y2;
            double dE3 = -(d-z) * z*(1-z) * y1;
            
            double dE2 = -(d-z) * z*(1-z) * w4 * y2*(1-y2) * x2;
            double dE1 = -(d-z) * z*(1-z) * w3 * y1*(1-y1) * x1;
            
            w1 += R * dE1;
            w2 += R * dE2;
            w3 += R * dE3;
            w4 += R * dE4;
            
            System.out.println("x1 = " + x1 + ", x2 = " + x2 + ", d = " + d + ", z = " + z);
        }
    }
}
