import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

public class HashTable {
	public static int dubblett = 0;
	
	private static class CityList{
		public City city;
		public int listKey;
		private CityList next;
		public int listSize;
		
		
		public CityList(City theCity) {
			this(theCity, null);
			this.listKey = theCity.key;
			listSize++;
		}
		
		public CityList(City theCity, CityList n) {
			city = theCity;
			next = n;
			listKey = theCity.key;
			listSize++;
		}
	}
	

	private static CityList[] table; //The HashTable
	public static int count; // The number of cities
	

	public HashTable() {
	      table = new CityList[6247];
	   }
	
	public void add(City newCity) {
		
		int index = newCity.key;   //where to put the city in array
		//int index = hashCode(newCity);   //where to put the city in array
		
		CityList list = table[index];
		//här ändrar jag
		
		if (table[index] != null && !list.city.name.equals(newCity.name)) {
			// index = findPos(newCity, list, index);

			while (!list.city.name.equals(newCity.name) && table[index] != null) {

				index++;
				if (index == table.length) {
					index = 0;
				}

			}
			list = table[index];
			newCity.key = index;
		}
		
		while(list != null) {
			list = list.next;
		}
		
		if(list == null) {
			CityList newList = new CityList(newCity);
			newList.listKey = newCity.key;
			newList.next = table[index];
			table[index] = newList;
			count++;
		}
	}
	
	public int findPos(City city, CityList list, int ind) {

		int offset = 1;
		int index = ind;

		while (table[index] != null && !list.city.name.equals(city.name)) {
			index += offset;
			offset += 2;
			if (index >= table.length) {
				index -= table.length;
			}
		}
		return index;
	}
	
	/*
	public int hashCode(City city) {
		int hash = 1;
		hash = hash * 17 + String.valueOf(city.key).hashCode();
		hash = hash * 31 + city.name.hashCode();
		return (hash & 0x7fffffff) % table.length;
	}
	
	private int hash(int key) {
		String key2 = String.valueOf(key);
	      return (Math.abs(key2.hashCode())) % table.length;
	   }*/

	//prints the whole hashTable including lists
	public void dump() {
		System.out.println();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				System.out.println(i + ":");
				CityList list = table[i]; // For traversing linked list number i.
				while (list != null) {
					System.out.println(list.city);
					list = list.next;
				}
				System.out.println();
			}
		}
	}
	
	//print the list of the city
	public void searchCity(String str) {
		CityList list = table[City.cityKey(str)];
		
		if(list == null) {
			System.out.println("No city with that name");
		}else {
			while(list != null) {
				System.out.println(list.city);
				list = list.next;
			}
		}
	}
	
	
	public CityList getList(String str) {
		CityList list = table[City.cityKey(str)];
		int x = list.listSize;
		System.out.println(x);
		return list;
	}
	
	public static void size() {
		int x = 0;
		for (int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				x++;
			}
		}
		System.out.println(x);
	}

	//check if number is a prime number
	public static boolean isPrime(int number) {
		if (number % 2 == 0) {//skip even numbers numbers
			return false;
		}
		for (int i = 3; i * i <= number; i+=2) {//all odd numbers after two
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static int getNextPrime(int min) {
		for (int i = min; true; i++) {
			if (isPrime(i)) {
				return i;
			}
		}
	}
	
	public static void main (String[] args) {
		System.out.println(getNextPrime(548));
		HashTable tab = new HashTable();
		TreeSet<String> tree = new TreeSet<>();
		
		try {
			Scanner scan = new Scanner(new File("sveland.txt"));
			while (scan.hasNextLine()) {
				String temp = scan.nextLine().replaceAll(" +", " ");
				String[] temparr = temp.split(" ");
				City x = new City(temparr[0], temparr[1], Integer.parseInt(temparr[2]));
				tab.add(x);
				tree.add(temparr[0]);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//tree.forEach(System.out::println);
		tab.dump();
		
		System.out.println(tree.size());
		size();
		
		//System.out.println(count);
		//System.out.println(dubblett);
		
		//tab.searchCity("Götmark");
		//System.out.println(tab.getList("Götmark").listSize);
		
	}
}
