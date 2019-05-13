import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTable {
	
	private static class CityNode{
		public City city;
		public int NodeKey;
		private CityNode next;
		
		
		public CityNode(City theCity) {
			this(theCity, null);
			this.NodeKey = theCity.key;
		}
		
		public CityNode(City theCity, CityNode n) {
			city = theCity;
			next = n;
			NodeKey = theCity.key;
		}
	}
	
	private static CityNode[] table; //The HashTable
	public static int count; // The number of cities
	

	public HashTable() {
	      table = new CityNode[City.modulus];
	   }
	

	public void add(City newCity) {
		
		int index = newCity.key;   //where to put the city in array
		int offset = 1;
		
		CityNode Node = table[index];
			
		while (table[index] != null && !Node.city.name.equals(newCity.name)) {
			
			if (Node.NodeKey == newCity.key && Node.city.name.equals(newCity.name)) {
				break;
			}
			
			index += offset;
			offset += 2;
			if (index >= table.length) {
				index -= table.length;
			}
			Node = table[index];
		}
		
		newCity.key = index;
		
		if (table[index] == null) {
			Node = new CityNode(newCity);
			Node.next = table[index];
			table[index] = Node;
			Node.NodeKey = newCity.key;
			count++;
		} else {
			
			while (Node != null) {
				Node = Node.next;
			}
			CityNode temp = new CityNode(newCity);
			if (Node == null) {
				temp.next = table[index];
				table[index] = temp;
				Node = temp;
				Node.NodeKey = newCity.key;
			}
		}
	}

	//prints the whole hashTable including Nodes
	public void dump() {
		System.out.println();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				System.out.println(i + ":");
				CityNode Node = table[i];
				while (Node != null) {
					System.out.println(Node.city);
					Node = Node.next;
				}
				System.out.println();
			}
		}
	}
	
	public static int getIndex(String str) {
		CityNode Node = table[City.cityKey(str)];
		int index = Node.NodeKey;
		int offset = 1;
		while(table[index] != null && !Node.city.name.equals(str)) {
			index += offset;
			offset += 2;
			if (index >= table.length) {
				index -= table.length;
			}
			Node = table[index];
		}
		return index;
	}
	
	//print the Node of the city
	public static void searchCity(String str) {
		CityNode Node = table[getIndex(str)];
		
		if(Node == null) {
			System.out.println("No city with that name");
		}else {
			while(Node != null) {
				System.out.println(Node.city);
				Node = Node.next;
			}
		}
	}
	
	public static CityNode getNode(String str) {
		CityNode Node = table[getIndex(str)];
		return Node;
	}
	
	
	//returns connections of a city in a ArrayList
	public static ArrayList<City> getConnections(String str){
		ArrayList<City> connections = new ArrayList<City>();
		CityNode Node = table[getIndex(str)];
		if(Node == null) {
			return null;
		}else {
			while(Node != null) {
				City temp = City.getOtherCity(Node.city.getConnection(), Node.city.getDistance());
				connections.add(temp);
				Node = Node.next;
			}
		}
		
		
		return connections;
	}
	
	
	public static int size() {
		System.out.println(count);
		return count;
	}
	
	public static void main (String[] args) {
		HashTable tab = new HashTable();
		
		try {
			Scanner scan = new Scanner(new File("sveland.txt"));
			while (scan.hasNextLine()) {
				String temp = scan.nextLine().replaceAll(" +", " ");
				String[] temparr = temp.split(" ");
				City x = new City(temparr[0], temparr[1], Integer.parseInt(temparr[2]));
				tab.add(x);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//tab.dump();
		searchCity("Söderbo");
		//CityNode test = getNode("Söderbo");
		//System.out.println(getNode("Söderbo").NodeKey);
		size();
		System.out.println(getIndex("Söderbo"));
		
		ArrayList<City> connections = getConnections("Söderbo");
		connections.forEach(System.out::println);
	}
}
