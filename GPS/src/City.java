import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class City {

	public String name;
	public int distance;
	public String connection;
	public int key;
	
	public City(String name, String connection, int distance) {
		this.name = name;
		this.distance = distance;
		this.connection = connection;
		key = cityKey(name);
	}
	
	public String getName() {
		return this.name;
	}
	public String getConnection() {
		return this.connection;
	}
	
	public int getDistance() {
		return this.distance;
	}
	
	public String toString() {
		return "["+this.getName()+"]" + " connects with " + "["+this.connection+"]" 
				+ ", distance between: " + this.getDistance() + ". Key: " + this.key;
	}
	
	/*
	public static int hash(int x) {
		int hashVal = 0;
		String s = String.valueOf(x);
		for (int i = 0; i < s.length(); i++) {
			hashVal = (hashVal * 128 + s.charAt(i)) % 6247;
		}
		return hashVal;
	}*/
	
	public static int cityKey(String s) {
		int x = 37;
		int val = 0;
		
		for (int i = 0; i < s.length(); i++) {
			val = val + (s.charAt(i) * (x^i)+7) + s.charAt(i);
		}
		return val % 6247;
	}

	public static void main(String[] args) {
		
		Hashtable<String, City> test = new Hashtable<String, City>();
		City one = new City("Bergbacka", "Gotberg", 31);
		City two = new City("Gotberg", "Bergbacka", 31);
		int counter = 0;
		try {
			Scanner scan = new Scanner(new File("sveland.txt"));
			while(scan.hasNextLine() && counter < 10) {
				String temp = scan.nextLine().replaceAll(" +", " ");
				String[] temparr = temp.split(" ");
				City x = new City(temparr[0], temparr[1], Integer.parseInt(temparr[2]));
				test.put(temparr[0], x);
				counter++;
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String s = "Brittvik";
		String p = "Eriksås";
		System.out.println(test.values());
		System.out.println(test.size());
		System.out.println(cityKey(s));
		System.out.println(cityKey(p));
		
		
	}

}
