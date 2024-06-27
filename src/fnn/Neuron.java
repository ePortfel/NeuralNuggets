package fnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class Neuron
{
  Activator activator;
  
  String name;
  Double value;
  Double bias;
  List<Connection> inputs=new ArrayList<>();
  List<Connection> outputs=new ArrayList<>();
  
  Double deltaactivf;
  List<Double> deltabiases=new ArrayList<>();
  
  public Neuron(Random random,String name,Activator a)
  {
    if (random==null) random=new Random();
    this.name=name;
    activator=a;
    value=random.nextDouble()*2-1;
    bias=random.nextDouble()*2-1;
  }
  
  public void forward()
  {
    value=bias;
    for (Connection ic:inputs)
      value+=ic.input.value*ic.weight;
    value=activator.activator(value);
  }

  void addDeltaBias(double d)
  {
    deltabiases.add(d);
  }

  void applyDeltaBiases()
  {
    bias+=deltabiases.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    deltabiases.clear();
  }
}
