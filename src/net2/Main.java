/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net2;

/**
 *
 * @author Alexandre
 */
public class Main {
    
    public static Perceptron ptron;
    public static Trainer[] training;
    public static int count;
    
    public static int cpt = 0;
    
    public static void main(String[] args)
    {
        setup();
        
        for (int i = 0; i < training.length; i++)
        {
            ptron.train(training[i].inputs, training[i].answer);
        }
        
        System.out.println(cpt);
    }
    
    

    public static double f(double x) {
      return 2*x+1;
    }


    public static void setup() {

      ptron = new Perceptron(3);
      training = new Trainer[4000];
      count = 0;

      for (int i = 0; i < training.length; i++) {
//        double x = -2. + 4 * Math.random();
//        double y = -2. + 4 * Math.random();

        double x = (int)(2 * Math.random());
        double y = (int)(2 * Math.random());

        int answer = 1;
//        if (y < f(x)) answer = -1;
        if (x == 0 && y == 0) answer = -1;

        training[i] = new Trainer(x, y, answer);
      }
    }
    
}
