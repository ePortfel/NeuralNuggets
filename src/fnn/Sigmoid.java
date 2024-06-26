package fnn;

/**
 * @author Maciej Wegorkiewicz
 */
public class Sigmoid implements Activator
{
  @Override
  public double activator(double x)
  {
    return 1/(1+Math.exp(-x));
  }
  
  @Override
  public double pactivator(double x)
  {
    x=activator(x);
    return x*(1-x);
  }  
}
