
package Builder;

import javax.swing.JOptionPane;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Generator extends AbstractAnalysis{
    
    private int rangeStart = -5;
    private int rangeEnd = 5;
    private int steps = 60;// min: 3, max: 100.
    
    public static void main(String[] args)  {
        try {
            AnalysisLauncher.open(new Generator());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                      "Error al mostrar la grafica");
            
            System.exit(0);
        }
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public void init() {
       // Create a expression.
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return Math.sin(Math.pow(x,2) + Math.pow(y,2));
            }
        };
        
        final Range range = new Range(rangeStart, rangeEnd);
        
        final OrthonormalGrid grid = new OrthonormalGrid(range, steps);
        
        

        // Create the Shape object.
        final Shape surface = Builder.buildOrthonormal(grid, mapper);
        
        final double min = surface.getBounds().getZmin();
        final double max = surface.getBounds().getZmax();
        Color color = new Color(1, 1, 1, .5f);
        
        ColorMapper colorMaper = new ColorMapper(new ColorMapRainbow(),min,max,color);
        surface.setColorMapper(colorMaper);
        
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // Creamos el gráfico
        
        chart = AWTChartComponentFactory.chart(Quality.Advanced,
                                               getCanvasType());
        
        chart.getAxeLayout().setMainColor(Color.WHITE);// Color de ejes
        chart.getView().setBackgroundColor(Color.BLACK);// Color de fondo
        
        chart.getScene().getGraph().add(surface);
    }
}
