package br.com.web.client.delegate;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.web.client.Cliente;
import br.com.web.interfaces.WebInterface;
import br.com.web.pojo.DocumentPOJO;

/**
 * Responsavel por comunicação remota ao servidor. 
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class WebDelegate {
	
	private WebInterface webInterface;
	
	public WebDelegate() {
		 try {
			 webInterface = (WebInterface) Naming.lookup("rmi://127.0.0.1:7777/Nome");
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, "::: ERROR TO LOCALIZE REMOTE SERVER :::");
        }
	}
	
	public DocumentPOJO search(String url) throws Exception {
		try {
			return webInterface.search(url);
		} catch (RemoteException e) {
			throw new Exception(e);
		}
	}
}
