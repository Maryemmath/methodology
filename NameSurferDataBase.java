import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */
public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	private HashMap<String, String> myMap = new HashMap<>();

	public NameSurferDataBase(String filename) {
		try {
			BufferedReader buf = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = buf.readLine();
				if (line == null) {
					break;
				} else {
					myMap.put(line.substring(0, line.indexOf(' ')).toUpperCase(), line);
				}
			}
			buf.close();
		} catch (Exception Ex) {
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		name = name.toUpperCase();
		if (myMap.containsKey(name)) {
			return new NameSurferEntry(myMap.get(name));
		}
		return null;
	}
}
