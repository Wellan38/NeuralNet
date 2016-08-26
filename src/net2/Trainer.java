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


class Trainer {

  double[] inputs;
  int answer;
 
  Trainer(double x, double y, int a) {
    inputs = new double[3];
    inputs[0] = x;
    inputs[1] = y;
    inputs[2] = 1;
    answer = a;
  }
}

