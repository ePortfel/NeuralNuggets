package fnn;

/**
 * @author Maciej Wegorkiewicz
 */
public class Relu implements Activator
{
  @Override
  public double activator(double x)
  {
    return Math.max(0, x);
  }

  @Override
  public double pactivator(double x)
  {
    return x > 0 ? 1 : 0;
  }
}
