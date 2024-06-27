package fnn;

/**
 * @author Maciej Wegorkiewicz
 */
public class Identity implements Activator
{
  @Override
  public double activator(double x)
  {
    return x;
  }

  @Override
  public double pactivator(double x)
  {
    return 1.0;
  }
}
