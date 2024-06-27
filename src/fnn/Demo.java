package fnn;

import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class Demo
{
  public static void main(String[] args)
  {
    boolean swing=true;          // czy wyświetlać graficzną prezentację sieci
    boolean debug=false;
    double learningRate=0.1;      // współczynnik "szybkości" uczenia
    double iterations=10000;       // liczba iteracji uczenia
    Activator actf=new Sigmoid();
    Optimizer opt=new ConstantRateOptimizer();

    List<Integer> layerSizes = List.of(2,2,2,1);
    Network network = new Network(layerSizes,new Random(),actf,opt,debug);

    // ustawienie parametrów sieci, można tę sekcję pominąć, wtedy parametry będą losowe
    List<List<List<Double>>> weights = 
            List.of(List.of(List.of(-4.0,0.2),List.of(0.4,3.0)),
                       List.of(List.of(2.0,0.1),List.of(0.1,8.0)),
                       List.of(List.of(0.2,0.1)));
    List<List<Double>> biases = 
            List.of(List.of(1.0,1.0),
            List.of(0.5,0.8),
            List.of(0.2));
    network.setParameters(weights, biases);
    
    // zbiór danych uczących, dwa neurony na wejściu, jeden na wyjściu
    List<List<Double>> inputs=List.of(List.of(0.0,1.0),List.of(1.0,0.0));
    List<List<Double>> targets=List.of(List.of(0.01),List.of(0.99));

    // prezentacja działania sieci przed uczeniem
    network.forward(inputs.get(0));
    report(swing,network,inputs.get(0),targets.get(0));
    network.forward(inputs.get(1));
    report(swing,network,inputs.get(1),targets.get(1));

    // uczenie
    for (int i=1;i<=iterations;i++)
    {
      network.forward(inputs.get(0));
      double result1=network.layers.get(network.layers.size()-1).neurons.get(0).value;
      if (i%(iterations/10)==0)
        System.out.println("Iteracja "+i+" strata "+String.format("%.4f", Math.abs(targets.get(0).get(0)-(result1))));
      network.train(inputs,targets,learningRate);
    }

    // prezentacja sieci wytrenowanej
    network.forward(inputs.get(0));
    report(swing,network,inputs.get(0),targets.get(0));
    network.forward(inputs.get(1));
    report(swing,network,inputs.get(1),targets.get(1));
  }  
  
  private static void report(boolean swing,Network network,List<Double> inputs,List<Double> targets)
  {
    System.out.println("Wejście: "+Utils.dlist(inputs, 2)+" wyjście: "+Utils.dlist(network.getOutput(), 2)+" cel: "+Utils.dlist(targets, 2));
    if (swing) new Vizualizer(network, "Wartość docelowa "+targets.get(0)).setVisible(true); 
  }  
}
