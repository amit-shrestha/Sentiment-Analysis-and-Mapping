package counter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import client.Home;
import database.DBConnection;

public class Counter {
	Connection con=null;
	public Counter()
	{
		con=DBConnection.getDBCon();
	}
	public void main() {
		int positive=0;
		int negative=0;
		int undeterministic=0;
		
		try {
			String read_sql = "select * from imported";
			Statement read_stm = con.createStatement();
			ResultSet rs = read_stm.executeQuery(read_sql);
			while (rs.next()) {
				int result=rs.getInt("Class");
				if(result==1)
				{
					positive++;
				}
				else if(result==0)
				{
					negative++;
				}
				else if(result==5)
				{
					undeterministic++;
				}
			}
			Home.setPositiveLabel("Number of Positive Tweets= "+positive);
			Home.setNegativeLabel("Number of Negative Tweets= "+negative);
			Home.setUndeterministicLabel("Number of Undeterministic Tweets= "+undeterministic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
