package vista;

import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;

import modelo.ChatGrupo;
import modelo.Usuario;

public class DiagramaTarta implements ExampleChart<PieChart> {
	
	private Usuario user;
	private List<ChatGrupo> firstN;

	public DiagramaTarta(Usuario user) {
		this.user = user;
		this.firstN = user.getGroupsSortedByMostUsed()
				.stream()
				.limit(6)
				.filter(g -> g.getRatioOfMessagesSent(user) > 0)
				.collect(Collectors.toList());
	}
	
	@Override
	public PieChart getChart() {
		// Create Chart
		PieChart chart = new PieChartBuilder()
				.title("Grupos en los que más participa "+user.getUsername())
				.width(800)
				.height(600)
				.build();
 
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		
	    // Series
		for (ChatGrupo g : firstN) {
			float ratio = g.getRatioOfMessagesSent(user) * 100;
			chart.addSeries(g.getName()+" ("+ratio+" %)", ratio);
		}
 
	    return chart;
	}

	@Override
	public String getExampleChartName() {
		return null;
	}
 
}