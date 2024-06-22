package fnn;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JDialog;

public class Vizualizer extends JDialog
{
  private final Network network;
  private final String title;

  public Vizualizer(Network network,String t)
  {
    this.network = network;
    title = t;
    setTitle("Schemat sieci");
    setSize(1400, 600);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setModal(true);
  }

  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    List<Layer> layers = network.layers;
    int numLayers = layers.size();
    int neuronRadius = 20;
    int horizontalSpacing = getWidth() / (numLayers-1)-60;

    Font dfont=g.getFont();
    Font font=dfont.deriveFont((float)20);
    int sw=g.getFontMetrics(font).stringWidth(title);
    g.setFont(font);
    g.drawString(title, getWidth()/2-sw , 100);
    g.setFont(dfont);
    
    for (int i = 0; i < numLayers; i++)
    {
      Layer layer = layers.get(i);
      int numNeuronsL = layer.neurons.size();
      int verticalSpacingL = getHeight() / (numNeuronsL + 1);

      for (int j = 0; j < numNeuronsL; j++)
      {
        Neuron neuron = layer.neurons.get(j);
        int x = (i) * horizontalSpacing+50;
        int y = (j + 1) * verticalSpacingL;

        g.setColor(Color.BLACK);
        g.drawOval(x - neuronRadius, y - neuronRadius, 2 * neuronRadius, 2 * neuronRadius);

        g.drawString(neuron.name, x - neuronRadius + 4 , y - neuronRadius - 10);
        
        g.drawString(String.format("%.2f", neuron.bias), x - neuronRadius + 6 , y + neuronRadius + 14);

        g.drawString(String.format("%.2f", neuron.value), x - neuronRadius + 6 , y +4);
        
        for (Connection connection : neuron.outputs)
        {
          int numNeuronsR = layers.get(i + 1).neurons.size();
          int verticalSpacingR = getHeight() / (numNeuronsR + 1);
          Neuron outputNeuron = connection.output;
          int outputX = (i + 1) * horizontalSpacing+50;
          int outputY = (layers.get(i + 1).neurons.indexOf(outputNeuron) + 1) * verticalSpacingR;

          g.drawLine(x + neuronRadius, y, outputX - neuronRadius, outputY);

          double weight = connection.weight;
          int weightX = (int) (x+0.8 * ( outputX-x));
          int weightY = (int) (y+0.8 * ( outputY-y));
          g.drawString(String.format("%.2f", weight), weightX, weightY);
        }
      }
    }
  }
}
