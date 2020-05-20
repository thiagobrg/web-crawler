package br.com.web.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.com.web.pojo.DocumentPOJO;

/**
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public interface WebInterface extends Remote{
	
	/**
	 * Faz o acesso a URL e retorna:<br>
	 * <br>
	 * - Titulo da Pagina<br>
	 * - Lista de Imagem<br>
	 * - Lista de SubLinks<br>
	 * 
	 * @param url
	 * @return
	 * @throws RemoteException
	 */
	DocumentPOJO search(String url) throws RemoteException;
	
}
