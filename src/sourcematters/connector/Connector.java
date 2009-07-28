package sourcematters.connector;

import java.io.File;

/*
 * Abstraction of a remote, or local repository. Should have an 
 * abstraction capable of walking through a single, linear path of 
 * revisions.
 */
public interface Connector {

	/*
	 * Creates a reference to the remote repository, validates
	 * it's existence
	 */
	public void setup(File workingCopy) throws ConnectorException;
	
	/*
	 * Get's the current revision of the local working copy
	 */
	public String getCurrentRevision() throws ConnectorException;

	/*
	 * Retrieves the next revision in the repository
	 */
	public String getNextRevision(String currentRevision) throws ConnectorException;
	
		
	
	
}
