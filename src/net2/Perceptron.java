/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net2;

import static net2.Main.cpt;

/**
 *
 * @author Alexandre
 */


class Perceptron {
  double[] weights;
  double c = 0.01;

 
  Perceptron(int n) {
    weights = new double[n];

    for (int i = 0; i < weights.length; i++) {
      weights[i] = Math.random();
    }

  }

  int feedforward(double[] inputs) {
    double sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sum += inputs[i]*weights[i];
    }
    
    return activate(sum);
  }

  int activate(double sum) {
    if (sum > 0) return 1;
    else return -1;
  }

  void train(double[] inputs, int desired) {
    int guess = feedforward(inputs);
    double error = desired - guess;
    for (int i = 0; i < weights.length; i++) {
      weights[i] += c * error * inputs[i];
    }
    
    System.out.print("desired = " + desired + ", guess = " + guess);
    
    if (guess != desired)
    {
        System.out.println(" différent !");
        cpt++;
    }
    else
    {
        System.out.println();
    }
  }

}

