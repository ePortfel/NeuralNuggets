package fnn;

/**
 * @author Maciej Wegorkiewicz
 */
public class Sigmoid
{
  public static double sigmoid(double x)
  {
    return 1/(1+Math.exp(-x));
  }
  
  public static double psigmoid(double x)
  {
    x=sigmoid(x);
    return x*(1-x);
  }  
}
