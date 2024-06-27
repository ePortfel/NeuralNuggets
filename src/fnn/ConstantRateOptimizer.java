package fnn;

/**
 * @author Maciej Wegorkiewicz
 */
public class ConstantRateOptimizer implements Optimizer
{
  @Override
  public double countDeltaWeight(Connection c, Double outputdeltaactivf, Double inputvalue, double learningRate)
  {
    return outputdeltaactivf*inputvalue*learningRate;
  }

  @Override
  public double countDeltaBias(Neuron n, Double deltaactivf, double learningRate)
  {
    return deltaactivf*learningRate;
  }
}
