package com.wedgwoodtom.contest.ui;

//import com.vaadin.addon.charts.Chart;
//import com.vaadin.addon.charts.model.*;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.springframework.util.StringUtils;
//import org.vaadin.addon.JFreeChartWrapper;

/**
 * Created by thomaspatterson on 11/20/16.
 */
public class ContestResultsView extends VerticalLayout implements View
{
    public static final String NAME = "contestResults";

    public ContestResultsView()
    {

//
//        VerticalLayout layout = new VerticalLayout(); // Gesamtlayout Haupt
//
//        XYSeries series = new XYSeries("Sprint 1");
//        series.add(0, 165);
//        series.add(1, 150);
//        series.add(2, 130);
//
//        XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(series);
//        // Generate the graph
//        JFreeChart chart = ChartFactory.createXYLineChart("Burn Down Chart", // Title
//                "days", // x-axis Label
//                "Esimated Effort", // y-axis Label
//                dataset, // Dataset
//                PlotOrientation.VERTICAL, // Plot Orientation
//                true, // Show Legend
//                true, // Use tooltips
//                false // Configure chart to generate URLs?
//        );
//        JFreeChartWrapper wrapper = new JFreeChartWrapper(chart) {
//            @Override
//            public void attach() {
//                super.attach();
//                markAsDirty();
//            }
//        };
//        layout.addComponent(wrapper);
//        addComponent(layout);


//        Chart chart = new Chart(ChartType.BAR);
//        chart.setWidth("400px");
//        chart.setHeight("300px");
//
//// Modify the default configuration a bit
//        Configuration conf = chart.getConfiguration();
//        conf.setTitle("Planets");
//        conf.setSubTitle("The bigger they are the harder they pull");
//        conf.getLegend().setEnabled(false); // Disable legend
//
//// The data
//        ListSeries series = new ListSeries("Diameter");
//        series.setData(4900, 12100, 12800,
//                6800, 143000, 125000,
//                51100, 49500);
//        conf.addSeries(series);
//
//// Set the category labels on the axis correspondingly
//        XAxis xaxis = new XAxis();
//        xaxis.setCategories("Mercury", "Venus", "Earth",
//                "Mars", "Jupiter", "Saturn",
//                "Uranus", "Neptune");
//        xaxis.setTitle("Planet");
//        conf.addxAxis(xaxis);
//
//// Set the Y axis title
//        YAxis yaxis = new YAxis();
//        yaxis.setTitle("Diameter");
//        yaxis.getLabels().setFormatter(
//                "function() {return Math.floor(this.value/1000) + \' Mm\';}");
//        yaxis.getLabels().setStep(2);
//        conf.addyAxis(yaxis);
//
//        addComponent(chart);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    }
}