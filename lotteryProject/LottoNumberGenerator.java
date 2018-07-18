package lotteryProject;

import javax.swing.*;

public class LottoNumberGenerator {
    
    public static void main(String[] arg) {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Lotteries());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}