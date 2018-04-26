import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class StockMonitor implements Observer {

	private Stock stock;
	private JLabel symbolLabel, lastPriceLabel, dateLabel, timeLabel;
	private JFrame frame;
	
	/*
	 * TODO:
	 * REMOVE ME FROM THE OBSERVER LIST WHEN I CLOSE
	 */
	
	public StockMonitor(Stock stock) {
		this.stock = stock;
		this.stock.registerObserver(this);
		
		frame = new JFrame(stock.getSymbol() + " Monitor");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeWindow();
			}
		});
//		frame.setSize(600, 200);
		
		//Need to access os in order to get screen size, toolkit gives us that access
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//get the dimenions of our screen size
		Dimension dim = toolkit.getScreenSize();
		//the below coordinates will center the screen
		int xCord = (dim.width / 2) - (frame.getWidth() / 2);
		int yCor = (dim.height / 2) + (frame.getHeight() / 2);
		frame.setLocation(xCord, yCor);
		
		JPanel panel = new JPanel();
		symbolLabel = new JLabel("Symbol: " + stock.getSymbol());
		lastPriceLabel = new JLabel("Last Price:");
		dateLabel = new JLabel("Date: ");
		timeLabel = new JLabel("Time: ");
		
		panel.setLayout(new GridLayout(0,1));
		panel.add(symbolLabel);
		panel.add(lastPriceLabel);
		panel.add(dateLabel);
		panel.add(timeLabel);
		
		stock.fetchData();
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	
		
	}
	
	@Override
	public void update() {
		System.out.println("\n" + stock.getSymbol() + " " + stock.getLastTrade() + " " + stock.getDate() + " " + stock.getTime());
		lastPriceLabel.setText("Last Price: " + stock.getLastTrade());
		dateLabel.setText("Date: " + stock.getDate());
		timeLabel.setText("Time: " + stock.getTime());
	}
	
	public void closeWindow() {
		stock.removeObserver(this);
		frame.dispose();
	}

}
