package lotteryProject;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class LotteryPanel extends JPanel {
    
    JButton quickPick, show, clear;
    JLabel title, lotteryName, odds, frequency, disclaimer;
    JTextField[] list, percent;
    JTextArea field;
    JScrollPane quickPickList;
    private int width, height;
    
    public LotteryPanel(Lotteries parent) {
        width = 480;
        height = 360;
        
        title = new JLabel("GA Lotto");
        lotteryName = new JLabel();
        odds = new JLabel();
        frequency = new JLabel("Percentage of all winnings*:");
        disclaimer = new JLabel("*Percentages above represent how often each number occured in the history of drawings.");
        
        quickPick = new JButton("Quick Pick!");
        show = new JButton("Show All Quick Picks");
        clear = new JButton("Clear All Quick Picks");
        
        field = new JTextArea();
        quickPickList = new JScrollPane(field);
        quickPickList.setPreferredSize(new Dimension((int)(width*0.75),(int)(height*0.75)));
        
        list = new JTextField[6];
        for (int i = 0; i < list.length; i++) {
        	list[i] = new JTextField(3);
        	list[i].setHorizontalAlignment(JTextField.CENTER);
        }
        percent = new JTextField[6];
        for (int i = 0; i < percent.length; i++) {
        	percent[i] = new JTextField(3);
        	percent[i].setHorizontalAlignment(JTextField.CENTER);
        }
        
        Font largeFont = new Font("CenturyGothic",Font.BOLD,60);
        title.setFont(largeFont);
        
        Font mediumFont = new Font("CenturyGothic",Font.PLAIN,30);
        lotteryName.setFont(mediumFont);
        
        Font boldMediumFont = new Font("CenturyGothic",Font.BOLD,24);
        quickPick.setFont(boldMediumFont);
        for (int i = 0; i < list.length; i++) {
        	list[i].setFont(boldMediumFont);
        }
        for (int i = 0; i < percent.length; i++) {
        	percent[i].setFont(boldMediumFont);
        }
        
        Font smallFont = new Font("CenturyGothic",Font.PLAIN,12);
        odds.setFont(smallFont);
        frequency.setFont(smallFont);
        show.setFont(smallFont);
        clear.setFont(smallFont);
        quickPickList.setFont(smallFont);
        
        Font fineFont = new Font("CenturyGothic",Font.ITALIC,10);
        disclaimer.setFont(fineFont);
        
        quickPick.addActionListener(parent);
        show.addActionListener(parent);
        clear.addActionListener(parent);
        
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.PAGE_AXIS));
        upperPanel.setPreferredSize(new Dimension(width,height/2));
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        upperPanel.add(title);
        upperPanel.add(Box.createVerticalGlue());
        lotteryName.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        upperPanel.add(lotteryName);
        upperPanel.add(Box.createVerticalGlue());
        odds.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        upperPanel.add(odds);
        upperPanel.add(Box.createRigidArea(new Dimension(0,25)));
        upperPanel.add(Box.createVerticalGlue());
        
        JPanel numberPanel = new JPanel();
        for (int i = 0; i < list.length; i++) {
        	numberPanel.add(list[i]);
        }
        numberPanel.add(Box.createRigidArea(new Dimension(0,20)));
        numberPanel.add(frequency);
        
        JPanel frequencyPanel = new JPanel();
        for (int i = 0; i < percent.length; i++) {
        	frequencyPanel.add(percent[i]);
        }
        frequencyPanel.add(disclaimer);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        show.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonPanel.add(show);
        buttonPanel.add(Box.createRigidArea(new Dimension(50,0)));
        clear.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonPanel.add(clear);
        
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.PAGE_AXIS));
        lowerPanel.setPreferredSize(new Dimension(width,5*height/8));
        quickPick.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        lowerPanel.add(quickPick);
        lowerPanel.add(Box.createRigidArea(new Dimension(0,10)));
        lowerPanel.add(numberPanel);
        lowerPanel.add(frequencyPanel);
        lowerPanel.add(buttonPanel);
        lowerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        
        setLayout(new BorderLayout());
        add(upperPanel, BorderLayout.NORTH);
        add(lowerPanel, BorderLayout.CENTER);
    }
}