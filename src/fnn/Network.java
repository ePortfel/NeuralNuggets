package fnn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Maciej Wegorkiewicz
 */
public class Network
{
  Activator activator;
  Optimizer optimizer;
  List<Layer> layers;
  boolean debug;
  
  public Network(List<Integer> layerSizes, Random random, Activator a, Optimizer o,boolean debug)
  {
    this.debug=debug;
    activator=a;
    optimizer=o;
    if (random==null) random=new Random();
    layers=new ArrayList<>(layerSizes.size());
    for (int i=0;i<layerSizes.size();i++)
      layers.add(new Layer(layerSizes.get(i), random, "L"+i, a));
    for (int i=0;i<layers.size()-1;i++)
    {
      Layer l1=layers.get(i);
      Layer l2=layers.get(i+1);
      for (int j=0;j<l1.neurons.size();j++)
        for (int k=0;k<l2.neurons.size();k++)
        {
          Connection c=new Connection(l1.neurons.get(j),l2.neurons.get(k),random);
          c.input.outputs.add(c);
          c.output.inputs.add(c);
        }
    }
  }
  
  public void forward(List<Double> inputs)
  {
    List<Neuron> inputNeurons=layers.get(0).neurons;
    for (int i=0;i<inputNeurons.size();i++) inputNeurons.get(i).value=inputs.get(i);
    for (int i=1;i<layers.size();i++) layers.get(i).forward();
  }
  
  public void train(List<List<Double>> binputs,List<List<Double>> btargets,double learningRate)
  {
    for (int bitem=0;bitem<binputs.size();bitem++)
    {
      List<Double> inputs=binputs.get(bitem);
      List<Double> targets=btargets.get(bitem);
      forward(inputs);
      if (debug) System.out.println("Krok treningu dla wejścia "+Utils.dlist(inputs,2));
      Layer last=layers.get(layers.size()-1);
      for (int i=0;i<last.neurons.size();i++)
      {
        Neuron n=last.neurons.get(i);
        n.deltaactivf=(targets.get(i)-n.value)*activator.pactivator(n.value);
        if (debug) 
          System.out.println("Gradient dla połączeń *->"+n.name+"="+String.format("%.4f",n.deltaactivf)+" =("+targets.get(i)+"-"+String.format("%.4f",n.value)+")*Pochodna("+String.format("%.4f",n.value)+")");
      }
      for (int i=layers.size()-2;i>=0;i--)
      {
        Layer layer=layers.get(i);
        for (int j=0;j<layer.neurons.size();j++)
        {
          List<Double> ssum=debug?new ArrayList<>():null;
          Neuron n=layer.neurons.get(j);
          double dactvf=0;
          for (Connection c:n.outputs)
          {
            double s=c.output.deltaactivf*c.weight;
            if (debug) ssum.add(s);
            dactvf+=s;
          }
          n.deltaactivf=dactvf*activator.pactivator(n.value);
          if (debug) 
            System.out.println("Gradient dla połączeń *->"+n.name+"="+String.format("%.4f",n.deltaactivf)+" =SUMA"+Utils.dlist(ssum,4)+"*Pochodna("+String.format("%.4f",n.value)+")");
        }
      }
      for (Layer l:layers)
        for (Neuron n:l.neurons)
        {
          n.addDeltaBias(optimizer.countDeltaBias(n, n.deltaactivf, learningRate));
          if (debug) System.out.println(n.name+" bias delta="+String.format("%.4f",n.deltaactivf*learningRate)+" ="+String.format("%.4f",n.deltaactivf)+"*"+learningRate);
          for (Connection c:n.outputs)
          {
            double deltaWeight=optimizer.countDeltaWeight(c,c.output.deltaactivf,c.input.value,learningRate);
            c.addDeltaWeight(deltaWeight);
            if (debug) System.out.println(c.input.name+"->"+c.output.name+" waga delta="+String.format("%.4f",c.output.deltaactivf*c.input.value*learningRate)+" ="+String.format("%.4f",c.output.deltaactivf)+"*"+String.format("%.4f",c.input.value)+"*"+learningRate);            
          }
        }
    }
    for (Layer l:layers)
      for (Neuron n:l.neurons)
      {
        n.applyDeltaBiases();
        for (Connection c:n.outputs)
          c.applyDeltaWeights();
      }
  }

  public void setParameters(List<List<List<Double>>> weights,List<List<Double>> biases)
  {
    for (int i=1;i<layers.size();i++)
    {
      List<List<Double>> ws=weights.get(i-1);
      List<Double> bs=biases.get(i-1);
      Layer lr=layers.get(i);
      for (int j=0;j<lr.neurons.size();j++)
      {
        Neuron ner=lr.neurons.get(j);
        ner.bias=bs.get(j);
        for (int k=0;k<ner.inputs.size();k++)
          ner.inputs.get(k).weight=ws.get(j).get(k);
      }
    }
  }
  
  public List<Double> getOutput()
  {
    List<Double> r=new ArrayList<>();
    for (Neuron n:layers.get(layers.size()-1).neurons)
      r.add(n.value);
    return r;
  }
}
