/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexandre
 */
public class TestNet3
{
    public static void main(String[] args)
    {
        NeuralNetwork net = new NeuralNetwork(2, 5, 1);

        for (int i = 1; i <= 10000; i++)
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
            if (s1 == 1 || s2 == 1)
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
