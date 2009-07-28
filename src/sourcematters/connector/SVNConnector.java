package sourcematters.connector;

import java.io.File;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNConnector implements Connector {
	private final SVNClientManager clientManager;
	private final SVNURL url;

	public SVNConnector(String repositoryLocation, String username, String password) throws SVNException {
		SVNRepositoryFactoryImpl.setup();
		url = SVNURL.parseURIEncoded(repositoryLocation);
		clientManager = SVNClientManager.newInstance();
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
		clientManager.setAuthenticationManager(authManager);
	}
	

	/**
	 * Creates a working copy for the repository. 
	 * If the location does not exist, it will create one.
	 * 
	 * @param workingCopy the location in which the working copy 
	 * will be deposited.
	 * @throws SVNException 
	 * 
	 * @exception IllegalArgumentException if the current 
	 * File reference exists and is not a directory.
	 */
	@Override
	public void setup(File workingCopy) throws ConnectorException {
		if(!workingCopy.exists()) {
			workingCopy.mkdir();
		}
		else if(workingCopy.isFile()) {
			throw new IllegalArgumentException("The working copy reference should be a directory, not a file");
		}
		
		try {
			SVNUpdateClient updateClient = clientManager.getUpdateClient();
			updateClient.doCheckout(url, workingCopy, SVNRevision.create(-1), SVNRevision.create(5), SVNDepth.INFINITY, true);
		}
		catch(SVNException e) {
			throw new ConnectorException(e);
		}
	}		
	
	@Override
	public String getCurrentRevision() throws ConnectorException {
		try {
			SVNRepository repository = clientManager.createRepository(url, true);
			return "" + repository.getLatestRevision();
		}
		catch(SVNException e) {
			throw new ConnectorException(e);
		}
	}

	@Override
	public String getNextRevision(String currentRevision) {
		return "";
	}

	

}
