package filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Filter {

	public static String punctuationsRemoval(String word) {
		String alphabets = "abcdefghijklmnopqrstuvwxyz";
		String temp = null;
		int i = 0;
		String store[] = word.split("");
		for (String letter : store) {
			if (alphabets.contains(letter)) {
				if (i == 0) {
					temp = letter;
					i++;
				} else {
					temp = temp + letter;
				}
			}
		}
		return temp;
	}

	public static List<String> stopWordsRemoval(List<String> DataList) {
		List<String> stopWordsRemovedList = new ArrayList<String>();

		Iterator<String> itr = DataList.iterator();
		while (itr.hasNext()) {
			int i = 0;
			String word = itr.next();
			Scanner sc;
			try {
				sc = new Scanner(new BufferedReader(new FileReader("C:\\Project SAM\\stopwords\\stopwords.txt")));

				while (sc.hasNext()) {
					String stopWord = sc.next();
					// System.out.println(stopWord);
					if (stopWord.equalsIgnoreCase(word)) {
						// System.out.println(word);
						i++;
						break;
					}
				}
				if (i == 0) {
					stopWordsRemovedList.add(word);
				}
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Stop Words Removed...");
		return stopWordsRemovedList;
	}

	public static List<String> tokenizer(String data) {
		List<String> tokenList = new ArrayList<String>();
		StringTokenizer st1 = new StringTokenizer(data);
		while (st1.hasMoreTokens()) {
			String tempToken=st1.nextToken();
			if(tempToken.startsWith("http"))
			{
				
			}
			else if(tempToken.startsWith("@"))
			{
				
			}
			else if(tempToken.equalsIgnoreCase("rt"))
			{
				
			}
			else
			{
			tokenList.add(tempToken);
			}
		}
		return tokenList;
	}

}
