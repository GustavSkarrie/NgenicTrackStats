package com.example;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class AppUI {
    public List<TrackObject> tracks = new ArrayList<TrackObject>();
    public Set<Integer> active = new HashSet<>();
    JFrame myWindow;
    ChartPanel chartPanel;

    JTextField averageIntervalText;
    float averageInterval = 15;
    int mode = 1;

    public AppUI(List<TrackObject> tracks) {
        this.tracks = tracks;

        myWindow = new JFrame("Ngenic Track Stats");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setSize(1250, 650);
        myWindow.setLayout(null);
        myWindow.setVisible(true);
        myWindow.setPreferredSize(new Dimension(1250, 650)); 

        for (int i = 0; i < tracks.size(); i++) {
            final int x = i;
            JToggleButton toggleButton = new JToggleButton(tracks.get(i).name);
            toggleButton.addActionListener((ActionEvent e) -> ActiveTrack(x));
            toggleButton.setBounds(10, 10 + i * 20, 240, 18);
            myWindow.add(toggleButton);
        }

        JLabel textLabel = new JLabel("Average Interval:");
        textLabel.setBounds(260, 10, 200, 18);
        myWindow.add(textLabel);

        averageIntervalText = new JTextField("15");
        averageIntervalText.setBounds(360, 10, 50, 18);
        myWindow.add(averageIntervalText);

        ButtonGroup buttonGroup = new ButtonGroup();
        JToggleButton normalMode = new JToggleButton("Show actual");
        normalMode.addActionListener((ActionEvent e) -> {mode = 1; UpdateTracks();});
        normalMode.setBounds(420, 10, 150, 18);
        buttonGroup.add(normalMode);

        JToggleButton averageMode = new JToggleButton("Show average");
        averageMode.addActionListener((ActionEvent e) -> {mode = 2; UpdateTracks();});
        averageMode.setBounds(570, 10, 150, 18);
        buttonGroup.add(averageMode);

        JToggleButton bothMode = new JToggleButton("Show both");
        bothMode.addActionListener((ActionEvent e) -> {mode = 3; UpdateTracks();});
        bothMode.setBounds(720, 10, 150, 18);
        buttonGroup.add(bothMode);

        myWindow.add(normalMode);
        myWindow.add(averageMode);
        myWindow.add(bothMode);
        myWindow.repaint();
    }

    public void ActiveTrack(int index) {
        TrackObject track = tracks.get(index);

        track.active = !track.active;

        if (track.active) {
            active.add(index);
        }
        else {
            active.remove(index);
        }

        UpdateTracks();
    }

    public void UpdateTracks() {
        if (chartPanel != null)
            myWindow.remove(chartPanel);

        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for (Integer i : active) {
            TimeSeries series;
            
            if (mode == 1)
                series = CreateTimeSeries(tracks.get(i));
            else
                series = CreateAverageTimeSeries(tracks.get(i)); 
                
            dataset.addSeries(series);

            if (mode == 3) {
                TimeSeries timeSeries = CreateTimeSeries(tracks.get(i));
                dataset.addSeries(timeSeries);
            }
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Graph",
            "Time",
            "Values",
            dataset,
            true,    
            false,   
            false);  
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(260, 30, 950, 580);
        System.out.println("create");
        myWindow.add(chartPanel);
        myWindow.repaint();
    }

    public TimeSeries CreateTimeSeries(TrackObject track) {
        TimeSeries series = new TimeSeries(track.name);

        for (Node n : track.nodes) {
            series.add(n.secTime, n.value);
        }

        return series;
    }

    public TimeSeries CreateAverageTimeSeries(TrackObject track) {
        try {
            averageInterval = Float.parseFloat(averageIntervalText.getText());
        } catch (Exception e) {
        }

        TimeSeries series = new TimeSeries("Average: "+ track.name);

        float curTime = 0;
        float value = 0;
        Second time = null;
        int i = 0;
        for (Node n : track.nodes) {
            curTime += n.timeDelta / 60f;
            i++;
            value += n.value;

            if (time == null)
                time = n.secTime;

            if (curTime >= averageInterval) {
                series.add(time, value / i);
                if (time != n.secTime)
                    series.add(n.secTime, value / i);
                i = 0;
                value = 0;
                time = null;
                curTime = 0;
            }
        }

        return series;
    }
}
