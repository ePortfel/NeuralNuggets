package fnn;

/**
 * @author Maciej Wegorkiewicz
 */
public interface Optimizer
{
  public double countDeltaBias(Neuron n, Double deltaactivf, double learningRate);
  public double countDeltaWeight(Connection c, Double outputdeltaactivf, Double inputvalue, double learningRate);
}
