
public class City{
	public static int modulus = 1319;
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
	
	public City(String name, int distance) {
		this.name = name;
		this.distance = distance;
	}
	
	public static City getOtherCity(String name, int dist) {
		City temp = new City(name, dist);
		return temp;
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
	
	boolean toStringMethod = false;
	
	public String toString() {
		return "["+this.getName()+"]" + " connects with " + "["+this.connection+"]" 
				+ ", distance between: " + this.getDistance() + ". Key: " + this.key;
	}
	
	public static int cityKey(String s) {
		int x = 37;
		int val = 0;
		
		for (int i = 0; i < s.length(); i++) {
			val = val + (s.charAt(i) * (x^i)+7) + s.charAt(i);
		}
		return val % modulus;
	}

	public static void main(String[] args) {
		
		String s = "Grönholm";
		String p = "Söderbo";
		System.out.println(cityKey(s));
		System.out.println(cityKey(p));
	}

}
