package testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import analysis.Analysis;
import filter.Filter;
import stem.Stemmer;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing", "root", "");
		createDatabase();
		filtration();

		String read_sql = "select * from test_result";
		Statement read_stm;
		try {
			read_stm = connect.createStatement();
			ResultSet rs = read_stm.executeQuery(read_sql);
			while (rs.next()) {
				System.out.println(rs.getString("FilteredData"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createDatabase() throws ClassNotFoundException, SQLException {
		int i = 1;
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing", "root", "");
		String del_sql = "delete from test_result";
		Statement del_stm;
		try {
			del_stm = connect.createStatement();
			del_stm.execute(del_sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\project SAM\\test set\\testdata.txt")));
			String write_sql = "insert into test_result(SN,TestData) values(?,?)";

			while (sc.hasNext()) {
				PreparedStatement write_pstm = connect.prepareStatement(write_sql);
				write_pstm.setInt(1, i);
				write_pstm.setString(2, sc.nextLine());
				write_pstm.execute();
				i++;
			}
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void filtration() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing", "root", "");
		List<String> tokenList = new ArrayList<String>();
		List<String> stopWordsRemovedList = new ArrayList<String>();
		List<String> testList = new ArrayList<String>();
		String read_sql = "select * from test_result";
		Statement read_stm;
		try {
			read_stm = connect.createStatement();
			ResultSet rs = read_stm.executeQuery(read_sql);
			while (rs.next()) {
				int sn = rs.getInt("SN");
				String data = rs.getString("TestData").toLowerCase();

				tokenList = Filter.tokenizer(data);

				stopWordsRemovedList = Filter.stopWordsRemoval(tokenList);

				Iterator<String> itr = stopWordsRemovedList.iterator();
				while (itr.hasNext()) {
					String punctuationRemovedWord = Filter.punctuationsRemoval(itr.next());
					if (punctuationRemovedWord == null) {

					} else {
						String stemmedWord = Stemmer.main(punctuationRemovedWord);
						testList.add(stemmedWord);
					}
				}

				String update_sql = "update test_result set FilteredData=? where SN='" + sn + "'";
				PreparedStatement update_pstm = connect.prepareStatement(update_sql);
				update_pstm.setString(1, new String(testList.toString()));
				update_pstm.execute();
				Analysis.main(testList, sn);
				testList.clear();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
