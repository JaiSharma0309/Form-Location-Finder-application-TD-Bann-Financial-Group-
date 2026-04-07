import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Searches a folder's metadata file for a requested form ID.
 *
 * <p>This class is designed to be called by the shell script in the repository.
 * The script loops through many generated subfolders, while this class checks one
 * folder at a time by reading its {@code FORMS_CTRLFUNC_RPT.DAT} file and looking
 * for the provided form ID.</p>
 */
public class FormNumberExtractor {

	/** The form ID supplied by the user that should be located in the metadata file. */
	private static String formIDNumber;

	/** The folder currently being checked for a matching form ID. */
	private static String path;

	/**
	 * Accepts a folder path and form ID, then searches that folder's metadata file.
	 *
	 * @param args command-line arguments where:
	 *             {@code args[0]} is the folder path to inspect and
	 *             {@code args[1]} is the form ID to locate
	 * @throws IOException if an I/O error occurs while reading the metadata file
	 */
	public static void main (String[] args) throws IOException{

		if (args.length == 2) {
			path = args[0];
			formIDNumber = args[1];
		} else {
			System.out.println("The input is invalid.");
			return;
		}

		FormNumberExtractor obj = new FormNumberExtractor();
		obj.getPDF();
	}

	/**
	 * Reads the metadata file in the provided folder and prints the folder path if
	 * the target form ID is found.
	 *
	 * @throws IOException if an I/O error occurs while opening the metadata file
	 */
	public void getPDF() throws IOException {
		try {
			String locationOfFile = this.path + "//" + "FORMS_CTRLFUNC_RPT.DAT";
			String locatioOfFolder = this.path;

			BufferedReader br = new BufferedReader (new FileReader(locationOfFile));
			String line = br.readLine();

			while (line != null) {
				StringTokenizer split = new StringTokenizer(line, "|");

				while (split.hasMoreTokens()) {
					String next = split.nextToken();

					if (next.equals(formIDNumber)) {
						System.out.println("The folder in which the file resides is: " + locatioOfFolder);
					}
				}

				line = br.readLine();
			}

			br.close();

		} catch (Exception e) {
			System.out.println("An exception is thrown of type : " + e);
		}
	}
}
