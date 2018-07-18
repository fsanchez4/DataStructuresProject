package lotteryProject;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Lotteries extends JPanel implements ActionListener {
    
    private LotteryPanel megaMillions;
    private LotteryPanel powerball;
    private static NumberGenerator gen = new NumberGenerator();
    private static int powerCount, megaCount;
    
    public Lotteries() {
        megaMillions = new LotteryPanel(this);
        powerball = new LotteryPanel(this);
        
        megaMillions.lotteryName.setText("Mega Millions");
        megaMillions.odds.setText("Odds of Winning: 1 in 302,575,350");
        
        powerball.lotteryName.setText("Powerball");
        powerball.odds.setText("Odds of Winning: 1 in 292,201,338");
        
//        for (int i = 1; i <= gen.getmmLastId(); i++) {
//    		String entry = gen.mmHistory(i);
//    		megaMillions.field.append("Quick Pick #"+ i +":\t\t "+ entry +"\n");
//    	}
//        
//        for (int i = 1; i <= gen.getpbLastId(); i++) {
//    		String entry = gen.pbHistory(i) +"";
//    		powerball.field.append("Quick Pick #"+ i +":\t\t "+ entry +"\n");
//    	}
        
        JTabbedPane tp = new JTabbedPane();
        tp.add("MegaMillions", megaMillions);
        tp.add("Powerball", powerball);
        add(tp);
    }
    
    public void actionPerformed(ActionEvent event) {
    	megaCount = gen.getmmLastId();
    	powerCount = gen.getpbLastId();
    	
        if (event.getSource() == megaMillions.quickPick) {
        	megaCount++;
        	int nums[] = gen.megaMillionsNumGenerator();
        	
        	megaMillions.field.append("Quick Pick #"+ megaCount +":\t\t ");
        	for (int i = 0; i < nums.length; i++) {
            	megaMillions.list[i].setText(nums[i] +"");
            	megaMillions.field.append(nums[i] +" ");
            }
        	megaMillions.field.append("\n");
        	gen.storeMegaMillionsQuickPick(nums, megaCount);
        	
        	for (int i = 0; i < 5; i++) {
        		String percentage = gen.mmNumberFrequency(nums[i]);
        		megaMillions.percent[i].setText(percentage);
        	}
        	String powerPercentage = gen.mmPowerFrequency(nums[5]);
        	megaMillions.percent[5].setText(powerPercentage);
        }
        
        if (event.getSource() == powerball.quickPick) {
        	powerCount++;
        	int nums[] = gen.powerballNumGenerator();
        	
        	powerball.field.append("Quick Pick #"+ powerCount +":\t\t ");
        	for (int i = 0; i < nums.length; i++) {
            	powerball.list[i].setText(nums[i] +"");
            	powerball.field.append(nums[i] +" ");
            }
        	powerball.field.append("\n");
        	gen.storePowerballQuickPick(nums, powerCount);
        	
        	for (int i = 0; i < 5; i++) {
        		String percentage = gen.pbNumberFrequency(nums[i]);
        		powerball.percent[i].setText(percentage);
        	}
        	String powerPercentage = gen.pbPowerFrequency(nums[5]);
        	powerball.percent[5].setText(powerPercentage);
        }
        
        if (event.getSource() == megaMillions.show) {
        	createFrame(megaMillions);
        }
        
        if (event.getSource() == powerball.show) {
        	createFrame(powerball);
        }
        
        if (event.getSource() == megaMillions.clear) {
        	megaCount = 0;
        	megaMillions.field.setText("");
        	gen.mmQuickPickReset();
        	for (int i = 0; i < megaMillions.list.length; i++) {
            	megaMillions.list[i].setText("");
            	megaMillions.percent[i].setText("");
            }
        }
        
        if (event.getSource() == powerball.clear) {
        	powerCount = 0;
        	powerball.field.setText("");
        	gen.pbQuickPickReset();
        	for (int i = 0; i < powerball.list.length; i++) {
            	powerball.list[i].setText("");
            	powerball.percent[i].setText("");
            }
        }
    }
    
    void createFrame(LotteryPanel input) {
    	JFrame qpFrame = new JFrame("Quick Pick List");
    	qpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	JPanel panel = new JPanel();
    	panel.add(input.quickPickList);
    	qpFrame.getContentPane().add(BorderLayout.CENTER, panel);
    	qpFrame.pack();
        qpFrame.setVisible(true);
        qpFrame.setResizable(false);
    }
}