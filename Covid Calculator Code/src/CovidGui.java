import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CovidGui {
    private JPanel panel1;
    private JPanel upperHalf;
    private JTextField epText;
    private JPanel fromContainer;
    private JPanel toContainer;
    private JLabel fromText;
    private JPanel lowerHalf;
    private JFormattedTextField nDText;
    private JTextField cases;
    private JTextField sCases;
    private JTextField asCases;
    private JTextField deathTot;
    private JButton calculateButton;
    private JTextField percAsymp;
    private JCheckBox maskOnOffCheckBox;
    private JDateChooser fromDate = new JDateChooser();
    private JDateChooser toDate = new JDateChooser();
    private XYSeries seriesC = new XYSeries("Cases");
    private XYSeries seriesS = new XYSeries("Sympt");
    private XYSeries seriesA = new XYSeries("Asympt");
    private XYSeries seriesD = new XYSeries("Deaths");

    public CovidGui() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seriesA.clear();
                seriesS.clear();
                seriesC.clear();
                seriesD.clear();
                long diff = toDate.getDate().getTime() - fromDate.getDate().getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                int initialCases = Integer.parseInt(nDText.getText());

                double growthFactor = Float.parseFloat(epText.getText());

                if (maskOnOffCheckBox.isSelected()) {
                    growthFactor = Float.parseFloat(epText.getText()) - 0.06;
                }

                long totCases = (long) (initialCases * Math.pow((1 + growthFactor), days));
                long asympCases = (totCases * 40 / 100);

                if (!(percAsymp.getText().equals(""))) {
                    asympCases = (totCases * Integer.parseInt(percAsymp.getText()) / 100);
                }

                long sympCases = totCases - asympCases;

                int deaths = (int) (sympCases / 100 * 3.5);


                for (int i = 1; i <= days; i++) {
                    long casesEDay = (long) (initialCases * Math.pow((1 + growthFactor), i));
                    long asympCa = casesEDay * 40 / 100;
                    if (!(percAsymp.getText().equals(""))) {
                        asympCa = (casesEDay * Integer.parseInt(percAsymp.getText()) / 100);
                    }
                    long sympCase = casesEDay - asympCa;
                    int death = (int) (sympCase / 100 * 3.5);
                    seriesC.add(i, casesEDay);
                    seriesS.add(i, sympCase);
                    seriesA.add(i, asympCa);
                    seriesD.add(i, death);
                }


                cases.setText("" + totCases);
                sCases.setText("" + sympCases);
                asCases.setText("" + asympCases);
                deathTot.setText("" + deaths);
            }
        });
    }

    public static void main(String[] args) {
        CovidGui a = new CovidGui();
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setContentPane(a.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Covid Calculator");


        a.fromDate.setPreferredSize(new Dimension(100, 30));
        a.fromDate.setMaxSelectableDate(new Date());
        a.fromContainer.add(a.fromDate);

        a.toDate.setPreferredSize(new Dimension(100, 30));
        //a.toDate.setMaxSelectableDate(new Date());
        a.toContainer.add(a.toDate);

        /*XYSeries series = new XYSeries("");
        series.add(1, 1);
        series.add(1, 2);
        series.add(2, 1);
        series.add(3, 9);
        series.add(4, 10); */

// Add the series to your data set
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(a.seriesC);
        dataset.addSeries(a.seriesS);
        dataset.addSeries(a.seriesA);
        dataset.addSeries(a.seriesD);

// Generate the graph
        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "Covid", // Title
                "days", // x-axis Label
                "cases", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
        a.lowerHalf.add(new ChartPanel(chart1));
        frame.setSize(800, 700);


    }

    public JPanel getFromContainer() {
        return fromContainer;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setPreferredSize(new Dimension(500, 400));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setOpaque(false);
        panel2.setPreferredSize(new Dimension(500, 600));
        panel1.add(panel2, BorderLayout.CENTER);
        upperHalf = new JPanel();
        upperHalf.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 0, 10, 0), 0, 0));
        upperHalf.setInheritsPopupMenu(false);
        upperHalf.setMinimumSize(new Dimension(200, 200));
        upperHalf.setPreferredSize(new Dimension(400, 250));
        upperHalf.setVisible(true);
        panel2.add(upperHalf, BorderLayout.NORTH);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        upperHalf.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        fromContainer = new JPanel();
        fromContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.add(fromContainer, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        fromText = new JLabel();
        Font fromTextFont = this.$$$getFont$$$("Droid Sans Mono", -1, 16, fromText.getFont());
        if (fromTextFont != null) fromText.setFont(fromTextFont);
        fromText.setHorizontalAlignment(2);
        fromText.setPreferredSize(new Dimension(40, 30));
        fromText.setText("From");
        fromContainer.add(fromText);
        toContainer = new JPanel();
        toContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel4.add(toContainer, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Droid Sans Mono", -1, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(11);
        label1.setPreferredSize(new Dimension(40, 30));
        label1.setText("To");
        toContainer.add(label1);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel3.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Droid Sans Mono", -1, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setPreferredSize(new Dimension(40, 30));
        label2.setText("Nd0");
        label2.setToolTipText("Starting nr of cases");
        panel5.add(label2);
        nDText = new JFormattedTextField();
        nDText.setColumns(10);
        Font nDTextFont = this.$$$getFont$$$("Fira Code", -1, 14, nDText.getFont());
        if (nDTextFont != null) nDText.setFont(nDTextFont);
        nDText.setMargin(new Insets(2, 6, 2, 6));
        nDText.setPreferredSize(new Dimension(96, 30));
        nDText.setToolTipText("Has to be >1");
        panel5.add(nDText);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        upperHalf.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7);
        maskOnOffCheckBox = new JCheckBox();
        Font maskOnOffCheckBoxFont = this.$$$getFont$$$("DialogInput", -1, 14, maskOnOffCheckBox.getFont());
        if (maskOnOffCheckBoxFont != null) maskOnOffCheckBox.setFont(maskOnOffCheckBoxFont);
        maskOnOffCheckBox.setForeground(new Color(-16777216));
        maskOnOffCheckBox.setHorizontalAlignment(0);
        maskOnOffCheckBox.setHorizontalTextPosition(11);
        maskOnOffCheckBox.setLabel(" Mask On/Off  ");
        maskOnOffCheckBox.setMargin(new Insets(0, 0, 0, 0));
        maskOnOffCheckBox.setText(" Mask On/Off  ");
        maskOnOffCheckBox.setToolTipText("-0,06");
        maskOnOffCheckBox.setVerticalAlignment(0);
        maskOnOffCheckBox.setVerticalTextPosition(0);
        panel7.add(maskOnOffCheckBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(0, 0), new Dimension(149, 30), new Dimension(200, 200), 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 0, 0));
        panel8.setPreferredSize(new Dimension(200, 90));
        panel6.add(panel8);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel8.add(panel9, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(400, 40), null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Droid Sans Mono", -1, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setHorizontalAlignment(0);
        label3.setHorizontalTextPosition(0);
        label3.setPreferredSize(new Dimension(40, 30));
        label3.setText("Ep");
        label3.setToolTipText("Growth Factor");
        label3.setVerticalAlignment(0);
        label3.setVerticalTextPosition(3);
        panel9.add(label3);
        epText = new JTextField();
        Font epTextFont = this.$$$getFont$$$("Fira Code", -1, 14, epText.getFont());
        if (epTextFont != null) epText.setFont(epTextFont);
        epText.setMargin(new Insets(2, 6, 2, 6));
        epText.setPreferredSize(new Dimension(45, 30));
        epText.setText("0.15");
        panel9.add(epText);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel8.add(panel10, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        calculateButton = new JButton();
        calculateButton.setAlignmentX(0.5f);
        calculateButton.setBackground(new Color(-16777216));
        calculateButton.setDoubleBuffered(false);
        calculateButton.setFocusPainted(false);
        Font calculateButtonFont = this.$$$getFont$$$("DialogInput", -1, 16, calculateButton.getFont());
        if (calculateButtonFont != null) calculateButton.setFont(calculateButtonFont);
        calculateButton.setForeground(new Color(-1705000));
        calculateButton.setHorizontalAlignment(0);
        calculateButton.setHorizontalTextPosition(0);
        calculateButton.setInheritsPopupMenu(false);
        calculateButton.setMargin(new Insets(0, 0, 0, 0));
        calculateButton.setOpaque(true);
        calculateButton.setPreferredSize(new Dimension(130, 30));
        calculateButton.setSelected(false);
        calculateButton.setText("Calculate");
        calculateButton.setVerifyInputWhenFocusTarget(false);
        calculateButton.setVerticalAlignment(0);
        calculateButton.setVerticalTextPosition(0);
        panel10.add(calculateButton);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel11);
        percAsymp = new JTextField();
        percAsymp.setText("");
        percAsymp.setToolTipText("Default(50%)");
        panel11.add(percAsymp, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(80, 26), null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("DialogInput", -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("% Asympt. cases");
        panel11.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new BorderLayout(0, 0));
        lowerHalf.setPreferredSize(new Dimension(200, 200));
        panel2.add(lowerHalf, BorderLayout.CENTER);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(2, 8, 0, 0), 1, 0, true, true));
        panel12.setBackground(new Color(-263169));
        panel12.setPreferredSize(new Dimension(200, 24));
        lowerHalf.add(panel12, BorderLayout.WEST);
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Inconsolata", -1, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Cases");
        panel12.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Inconsolata", -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Sympt. cases");
        panel12.add(label6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Inconsolata", -1, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Asymp. cases");
        panel12.add(label7, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("Inconsolata", -1, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Deaths");
        panel12.add(label8, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cases = new JTextField();
        cases.setColumns(12);
        cases.setEditable(false);
        cases.setText("");
        panel12.add(cases, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 20), null, 0, false));
        asCases = new JTextField();
        asCases.setColumns(12);
        asCases.setEditable(false);
        asCases.setText("");
        panel12.add(asCases, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 20), null, 0, false));
        deathTot = new JTextField();
        deathTot.setColumns(12);
        deathTot.setEditable(false);
        panel12.add(deathTot, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 20), null, 0, false));
        sCases = new JTextField();
        sCases.setEditable(false);
        panel12.add(sCases, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 20), null, 0, false));
        label3.setLabelFor(epText);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
