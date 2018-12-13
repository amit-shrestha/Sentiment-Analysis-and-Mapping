package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import counter.Counter;
import getTweets.Tweets;
import models.Variables;
import services.Functions;

import javax.swing.SwingConstants;

public class Home extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblSentimentAnalysisAnd;
	private JTextField txtEnterTopic;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnExit;
	private static JLabel rateLabel;
	private JLabel processLabel;
	private static JLabel timeLabel;
	private static JLabel positiveLabel;
	private static JLabel negativeLabel;
	private static JLabel undeterministicLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setBackground(new Color(0, 0, 255));
		setTitle("Project SAM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 881, 596);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanel());
		contentPane.add(getScrollPane());
		contentPane.add(getBtnExit());
		populateData();
		setVisible(true);
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(10, 11, 845, 198);
			panel.setLayout(null);
			panel.add(getLblSentimentAnalysisAnd());
			panel.add(getTxtEnterTopic());
			panel.add(getBtnNewButton());
			panel.add(getRateLabel());
			panel.add(getProcessLabel());
			panel.add(getTimeLabel());
			panel.add(getPositiveLabel());
			panel.add(getNegativeLabel());
			panel.add(getUndeterministicLabel());
		}
		return panel;
	}
	private JLabel getLblSentimentAnalysisAnd() {
		if (lblSentimentAnalysisAnd == null) {
			lblSentimentAnalysisAnd = new JLabel("Sentiment Analysis and Mapping");
			lblSentimentAnalysisAnd.setForeground(Color.BLACK);
			lblSentimentAnalysisAnd.setHorizontalAlignment(SwingConstants.CENTER);
			lblSentimentAnalysisAnd.setFont(new Font("MV Boli", Font.BOLD, 31));
			lblSentimentAnalysisAnd.setBounds(10, 11, 825, 60);
		}
		return lblSentimentAnalysisAnd;
	}
	private JTextField getTxtEnterTopic() {
		if (txtEnterTopic == null) {
			txtEnterTopic = new JTextField();
			txtEnterTopic.setBackground(Color.WHITE);
			txtEnterTopic.setBounds(10, 84, 199, 20);
			txtEnterTopic.setColumns(10);
		}
		return txtEnterTopic;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Search");
			btnNewButton.setBackground(Color.GREEN);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					processLabel.setText("Process Completed");
					Variables v=new Variables();
					v.setTopic(txtEnterTopic.getText());
					Tweets impl=new Tweets();
					impl.searchTopic(v);
					impl.filter();
					Counter count=new Counter();
					count.main();
					//JOptionPane.showMessageDialog(null, "Process Completed...");
					populateData();
				}
			});
			btnNewButton.setBounds(235, 82, 89, 23);
		}
		return btnNewButton;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 232, 845, 290);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setBackground(Color.WHITE);
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"S.N.", "Created_At", "Tweet_Id", "Tweets"
				}
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(42);
			table.getColumnModel().getColumn(1).setPreferredWidth(173);
			table.getColumnModel().getColumn(2).setPreferredWidth(178);
			table.getColumnModel().getColumn(3).setPreferredWidth(333);
			table.getColumnModel().getColumn(3).setMinWidth(50);
		}
		return table;
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			btnExit.setBackground(Color.RED);
			btnExit.setBounds(766, 533, 89, 23);
		}
		return btnExit;
	}
	private void populateData(){
		Functions f=new Tweets();
		List<Variables>tweetList=f.getAllData();
		DefaultTableModel model=(DefaultTableModel)table.getModel();
		model.setRowCount(0);
		for(Variables v:tweetList)
		{
			model.addRow(new Object[]{v.getSn(),v.getCreatedAt(),v.getTweetId(),v.getTweets()});
		}
	}

	private JLabel getRateLabel() {
			if(rateLabel==null)
			{
			rateLabel=new JLabel("");
			rateLabel.setBounds(10, 146, 199, 20);
			}
			return rateLabel;
	}
	
	public static void setLabel1(String text)
	{
		rateLabel.setText(text);
	}
	
	public static void setLabel2(String text)
	{
		timeLabel.setText(text);
	}
	
	private JLabel getProcessLabel() {
		if (processLabel == null) {
			processLabel = new JLabel("");
			processLabel.setBounds(10, 115, 199, 20);
		}
		return processLabel;
	}
	
	private JLabel getTimeLabel() {
		if (timeLabel == null) {
			timeLabel = new JLabel("");
			timeLabel.setBounds(10, 171, 199, 20);
		}
		return timeLabel;
	}
	
	private JLabel getPositiveLabel() {
		if (positiveLabel == null) {
			positiveLabel = new JLabel("");
			positiveLabel.setBounds(469, 82, 319, 19);
		}
		return positiveLabel;
	}
	
	private JLabel getNegativeLabel() {
		if (negativeLabel == null) {
			negativeLabel = new JLabel("");
			negativeLabel.setBounds(469, 116, 319, 19);
		}
		return negativeLabel;
	}
	
	private JLabel getUndeterministicLabel() {
		if (undeterministicLabel == null) {
			undeterministicLabel = new JLabel("");
			undeterministicLabel.setBounds(469, 146, 319, 19);
		}
		return undeterministicLabel;
	}
	
	public static void setPositiveLabel(String text)
	{
		positiveLabel.setText(text);
	}
	
	public static void setNegativeLabel(String text)
	{
		negativeLabel.setText(text);
	}
	
	public static void setUndeterministicLabel(String text)
	{
		undeterministicLabel.setText(text);
	}
}
