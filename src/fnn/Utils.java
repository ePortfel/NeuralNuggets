package fnn;

import java.util.List;

/**
 * @author Maciej Wegorkiewicz
 */
public class Utils
{
  public static String dlist(List<Double> list, int precision)
  {
    StringBuilder sb=new StringBuilder("(");
    for (int i=0;i<list.size();i++) 
    {
      sb.append(String.format("%."+precision+"f", list.get(i)));
      if (i<list.size()-1) sb.append(";");
    }
    sb.append(")");
    return sb.toString();
  }  
  
  public static void report(boolean swing, Network network, List<Double> inputs, List<Double> targets, String caption)
  {
    System.out.println("Wejście: "+dlist(inputs, 2)+" wyjście: "+dlist(network.getOutput(), 2)+" cel: "+dlist(targets, 2));
    if (caption==null) caption="Wartość docelowa "+targets.get(0);
    if (swing) new Vizualizer(network, caption).setVisible(true); 
  }  
}
