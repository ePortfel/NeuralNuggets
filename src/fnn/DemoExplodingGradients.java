package fnn;

import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class DemoExplodingGradients
{
  public static void main(String[] args)
  {
    boolean swing=true;
    double learningRate=1;
    double iterations=1;
    Activator actf=new Identity();
    Optimizer opt=new ConstantRateOptimizer();
    boolean debug=false;

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
    List<List<Double>> inputs=List.of(List.of(0.0,1.0)); 
    List<List<Double>> targets=List.of(List.of(0.01));

    // prezentacja działania sieci przed uczeniem
    network.forward(inputs.get(0));
    Utils.report(swing,network,inputs.get(0),targets.get(0), null);

    // uczenie
    for (int i=1;i<=iterations;i++)
    {
      network.forward(inputs.get(0));
      double result1=network.layers.get(network.layers.size()-1).neurons.get(0).value;
      System.out.println("Iteracja "+i+" strata "+String.format("%.4f", Math.abs(targets.get(0).get(0)-(result1))));
      network.train(inputs,targets,learningRate);
    }

    // prezentacja sieci wytrenowanej
    network.forward(inputs.get(0));
    Utils.report(swing,network,inputs.get(0),targets.get(0),"Zbyt duże amplitudy zmian - wynik przetwarzania oddalił się od celu.");
    
    learningRate=0.001;
    iterations=10;
    
    network = new Network(layerSizes,new Random(),actf,opt,debug);
    network.setParameters(weights, biases);
    
    // prezentacja działania sieci przed uczeniem
    network.forward(inputs.get(0));
    Utils.report(swing,network,inputs.get(0),targets.get(0), null);

    // uczenie
    for (int i=1;i<=iterations;i++)
    {
      network.forward(inputs.get(0));
      double result1=network.layers.get(network.layers.size()-1).neurons.get(0).value;
      System.out.println("Iteracja "+i+" strata "+String.format("%.4f", Math.abs(targets.get(0).get(0)-(result1))));
      network.train(inputs,targets,learningRate);
    }

    // prezentacja sieci wytrenowanej
    network.forward(inputs.get(0));
    Utils.report(swing,network,inputs.get(0),targets.get(0),"Zmniejszenie szybkości zmian rozwiązuje problem eksplozji gradientów.");
  }    
}
