package edu.uw.cs.biglearn.clickprediction.preprocess;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Temporary storage class for loading features from separate feature files:
 * Each feature file represented as an ArrayList of a token (integer) set,
 * the index of the array is the id of the feature.
 *  
 * @author haijieg
 * 
 */
public class Features {
	public static ArrayList<Set<Integer>> descriptionFeature;
	public static ArrayList<Set<Integer>> keywordFeature;		
	public static ArrayList<Set<Integer>> queryFeature;
	public static ArrayList<Set<Integer>> titleFeature;
	
  /**
   * Load query, keywork, title and description features.
   * Path to the feature file is hard coded.
   * Size of each feature file is hard coded.
   * 
   * @return
   * 
   */
	public static boolean loadAllFeatures() {
		String basepath = "/usr1/haijieg/kdd/features/";
		descriptionFeature = new ArrayList<Set<Integer>>(3171830);
		titleFeature = new ArrayList<Set<Integer>>(4051441);
		queryFeature = new ArrayList<Set<Integer>>(26243606);
		keywordFeature = new ArrayList<Set<Integer>>(1249785);
		try {
			loadFeature(basepath + "descriptionid_tokensid.txt", descriptionFeature);
			loadFeature(basepath + "titleid_tokensid.txt", titleFeature);
			loadFeature(basepath + "purchasedkeywordid_tokensid.txt", keywordFeature);
			loadFeature(basepath + "queryid_tokensid.txt", queryFeature);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

/**
 * Load feature file from the given path.
 * @param path
 * @param feature
 * @throws FileNotFoundException
 */  
	private static void loadFeature (String path, ArrayList<Set<Integer>> feature) throws FileNotFoundException {
		System.err.println("Loading feature from "+ path);
			Scanner sc = new Scanner(new BufferedReader (new FileReader(path)));
			int line = 0;
			while(sc.hasNextLine()) {
				feature.add(parseFeature(sc.nextLine()));
				line++;
				if (line % 1000000 == 0)
					System.err.println("Loaded " + line  +" lines");
			}
			System.err.println("Done");			
	}
	
  /**
   * Parse a "|" separated token list into a set representing binary indicators. 
   * @param line
   * @return A set of tokens
   */
	private static Set<Integer> parseFeature (String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		Set<Integer> words = new HashSet<Integer>(3);
		String id = tokenizer.nextToken();
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken("\\|").trim();
			words.add(Integer.valueOf(token));
		}
		return words;
	}
	
  /**
   * For testing...
   */
	public static void main(String[] args) {
		Features.loadAllFeatures();
	}
}