package analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Analysis {

	public static void main(List<String> readyData, int id) {
		double pNo = 0;
		double nNo = 0;
		int result;
		double temp = 0;
		double temp1 = 0;
		double positivetemp;
		double negativetemp;
		double positiveLikelihood;
		double negativeLikelihood;
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\Project SAM\\knowledge base\\values.txt")));
			pNo = sc.nextDouble();
			nNo = sc.nextDouble();
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp = (pNo / (pNo + nNo));
		temp1 = (nNo / (pNo + nNo));
		positivetemp = positiveAnalysis(readyData, pNo);
		System.out.println("postivetemp= " + positivetemp);
		positiveLikelihood = (positivetemp * temp);
		System.out.println("pLikelihood= " + positiveLikelihood);
		negativetemp = negativeAnalysis(readyData, nNo);
		System.out.println("negativetemp= " + negativetemp);
		negativeLikelihood = (negativetemp * temp1);
		System.out.println("nLikelihood= " + negativeLikelihood);
		if (positiveLikelihood > negativeLikelihood) {
			result = 1;
		} else if (negativeLikelihood > positiveLikelihood) {
			result = 0;
		} else {
			result = 5;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter_database", "root", "");
			String update_sql = "update imported set PositiveLikelihood=?,NegativeLikelihood=?,Class=? where Id='"
					+ id + "'";
			PreparedStatement update_pstm = connect.prepareStatement(update_sql);
			update_pstm.setDouble(1, positiveLikelihood);
			update_pstm.setDouble(2, negativeLikelihood);
			update_pstm.setInt(3, result);
			update_pstm.execute();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static double positiveAnalysis(List<String> readyData, double pNo) {

		double likelihood = 0;
		double probability;
		double temp;
		Iterator<String> itr = readyData.iterator();
		while (itr.hasNext()) {
			temp = 0;
			probability = 0;
			likelihood = 1;
			String word = itr.next();
			try {
				Scanner pfile = new Scanner(new BufferedReader(
						new FileReader("C:\\Project SAM\\knowledge base\\positive knowledge base.txt")));
				while (pfile.hasNext()) {
					String wordFromBag = pfile.next();
					if (word.equalsIgnoreCase(wordFromBag)) {
						temp = temp + 1;
					}
				}
				if (temp== 0) {
					probability=0.5;
				}
				else
				{
					probability = temp / pNo;
				}
				likelihood = likelihood * probability;
				pfile.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return likelihood;
	}

	public static double negativeAnalysis(List<String> readyData, double nNo) {

		double likelihood = 0;
		double probability;
		double temp;
		Iterator<String> itr = readyData.iterator();
		while (itr.hasNext()) {
			temp = 0;
			probability = 0;
			likelihood = 1;
			String word = itr.next();
			try {
				Scanner nfile = new Scanner(new BufferedReader(
						new FileReader("C:\\Project SAM\\knowledge base\\negative knowledge base.txt")));
				while (nfile.hasNext()) {
					String wordFromBag = nfile.next();
					if (word.equalsIgnoreCase(wordFromBag)) {
						temp = temp + 1;
					}
				}
				if (temp == 0) {
					probability=0.5;
				}
				else
				{
					probability = temp / nNo;
				}
				likelihood = likelihood * probability;
				nfile.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return likelihood;
	}
}
