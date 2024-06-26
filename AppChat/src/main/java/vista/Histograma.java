package vista;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;

import modelo.Usuario;

public class Histograma implements ExampleChart<CategoryChart> {
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("M/yyyy");
	private final static int year = Calendar.getInstance().get(Calendar.YEAR);
	private final static String[] meses = new String[] {
			"Ene", "Feb", "Mar", "Abr", "May", "Jun",
			"Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
	};
	private Integer[] nmsgs = new Integer[12];
	
	private Usuario user;
	
	public Histograma(Usuario user) {
		this.user = user;
		Date d1 = null;//aquí se guardan las 00:00:00 del primer día del mes
		Date d2 = null;//aquí se guardan las 23:59:59 del último día del mes
		for (int i=1; i<=12; i++) {
			try {
				d1 = dateFormat.parse(i+"/"+year);
				d2 = Date.from(dateFormat.parse((i+1)+"/"+year).toInstant().minusSeconds(1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.nmsgs[i-1] = user.getNumberOfMessagesSent(d1,d2);
		}
	}
 
	@Override
	public CategoryChart getChart() {
		// Create Chart
		CategoryChart chart = new CategoryChartBuilder()
				.title("Mensajes enviados por "+user.getUsername()+" en "+year)
				.width(800)
				.height(600)
				.xAxisTitle("Mes")
				.yAxisTitle("Mensajes enviados")
				.build();
		 
		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setSeriesColors(new Color[] {Graphics.MAIN});
		// Series
		chart.addSeries("Mensajes/mes",Arrays.asList(meses),Arrays.asList(nmsgs));
		return chart;
	}

	@Override
	public String getExampleChartName() {
		return null;
	}
}