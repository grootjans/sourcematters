package sourcematters.connector;

import java.io.File;

import org.tmatesoft.svn.core.SVNException;

/*
 * Simple, and slightly idiotic test because 
 * I was too lazy to add the junit tests on the initial commit
 */
public class BasicTest {

	public static void main(String[] args) throws SVNException {
		SVNConnector connector = new SVNConnector("svn://localhost/home/sourcematters/", "test", "test");
		try {
			connector.setup(new File("workingcopy"));
		} catch (ConnectorException e) {
			e.printStackTrace();
		}
	}
	
}
