package fnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class Layer
{
  List<Neuron> neurons;
  
  public Layer(int n, Random random, String name, Activator a)
  {
    if (random==null) random=new Random();
    neurons=new ArrayList<>(n);
    for (int i=0;i<n;i++) neurons.add(new Neuron(random, name+"N"+i,a));
  }
  
  public void forward()
  {
    for (Neuron n:neurons) n.forward();
  }
}
