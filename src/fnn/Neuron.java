package fnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class Neuron
{
  String name;
  Double value;
  Double bias;
  List<Connection> inputs=new ArrayList<>();
  List<Connection> outputs=new ArrayList<>();
  
  Double deltaactivf;
  List<Double> deltabiases=new ArrayList<>();
  
  public Neuron(Random random,String name)
  {
    if (random==null) random=new Random();
    this.name=name;
    value=random.nextDouble()*2-1;
    bias=random.nextDouble()*2-1;
  }
  
  public void forward()
  {
    value=bias;
    for (Connection ic:inputs)
      value+=ic.input.value*ic.weight;
    value=Sigmoid.sigmoid(value);
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
