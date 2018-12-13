package classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import filter.Filter;
import stem.Stemmer;

public class TrainClassifier {

	public static void main(String[] args) throws IOException {
		int positiveDatas = positivetrain();
		int negativeDatas = negativetrain();
		FileWriter fw = new FileWriter("C:\\Project SAM\\knowledge base\\values.txt");
		fw.write(positiveDatas + " \n" + negativeDatas);
		fw.close();
	}

	public static int positivetrain() throws IOException {
		int positiveDatas = 0;
		int positiveWordCount = 0;
		List<String> positiveWordList = new ArrayList<String>();
		List<String> stopWordsRemovedList = new ArrayList<String>();
		List<String> positiveDataList = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(
					new BufferedReader(new FileReader("C:\\Project SAM\\training set\\positive_training.txt")));
			while (sc.hasNext()) {
				System.out.println(sc.nextLine());
				positiveDatas++;
			}
			sc.close();
			Scanner sc1 = new Scanner(
					new BufferedReader(new FileReader("C:\\Project SAM\\training set\\positive_training.txt")));

			while (sc1.hasNext()) {
				String Data = sc1.next().toLowerCase();
				positiveDataList.add(Data);
				positiveWordCount++;
			}
			System.out.println("Positive Training Set Scanning Completed...");
			sc1.close();
			stopWordsRemovedList = Filter.stopWordsRemoval(positiveDataList);
			Iterator<String> itr = stopWordsRemovedList.iterator();
			while (itr.hasNext()) {
				String punctuationRemovedWord = Filter.punctuationsRemoval(itr.next());
				System.out.println(punctuationRemovedWord);
				if (punctuationRemovedWord == null) {

				} else {
					String stemmedWord = Stemmer.main(punctuationRemovedWord);
					positiveWordList.add(stemmedWord);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter fw = new FileWriter("C:\\Project SAM\\knowledge base\\positive knowledge base.txt");
		Iterator<String> itr1 = positiveWordList.iterator();
		while (itr1.hasNext()) {
			try {
				String wordFromArray = itr1.next();

				fw.write(wordFromArray);
				fw.write(" \n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		fw.close();
		System.out.println("Positive training process completed...");
		System.out.println("total positive words=" + positiveWordCount);
		System.out.println("total positive datas in traning set=" + positiveDatas);
		return positiveDatas;
	}

	public static int negativetrain() throws IOException {
		int negativeDatas = 0;
		int negativeWordCount = 0;
		List<String> negativeWordList = new ArrayList<String>();
		List<String> stopWordsRemovedList = new ArrayList<String>();
		List<String> negativeDataList = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(
					new BufferedReader(new FileReader("C:\\Project SAM\\training set\\negative_training.txt")));

			while (sc.hasNext()) {
				System.out.println(sc.nextLine());
				negativeDatas++;
			}
			sc.close();
			Scanner sc1 = new Scanner(
					new BufferedReader(new FileReader("C:\\Project SAM\\training set\\negative_training.txt")));

			while (sc1.hasNext()) {
				String Data = sc1.next().toLowerCase();
				negativeDataList.add(Data);
				negativeWordCount++;
			}
			System.out.println("Negative Training Set Scanning Completed...");
			sc1.close();
			stopWordsRemovedList = Filter.stopWordsRemoval(negativeDataList);
			Iterator<String> itr = stopWordsRemovedList.iterator();
			while (itr.hasNext()) {
				String punctuationRemovedWord = Filter.punctuationsRemoval(itr.next());
				System.out.println(punctuationRemovedWord);
				if (punctuationRemovedWord == null) {

				} else {
					String stemmedWord = Stemmer.main(punctuationRemovedWord);
					negativeWordList.add(stemmedWord);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter fw = new FileWriter("C:\\Project SAM\\knowledge base\\negative knowledge base.txt");
		Iterator<String> itr1 = negativeWordList.iterator();
		while (itr1.hasNext()) {
			try {
				String wordFromArray = itr1.next();

				fw.write(wordFromArray);
				fw.write(" \n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		fw.close();
		System.out.println("Negative training process completed...");
		System.out.println("total negative words=" + negativeWordCount);
		System.out.println("total negative datas in training set=" + negativeDatas);
		return negativeDatas;
	}
}
