package fnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class Connection
{
  Neuron input;
  Neuron output;
  Double weight;
  
  List<Double> deltaweights=new ArrayList<>();
  
  public Connection(Neuron i,Neuron o,Random random)
  {
    if (random==null) random=new Random();
    input=i;
    output=o;
    weight=random.nextDouble()*2-1;
  }

  void addDeltaWeight(double d)
  {
    deltaweights.add(d);
  }

  void applyDeltaWeights()
  {
    weight+=deltaweights.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    deltaweights.clear();
  }
}
