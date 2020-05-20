package br.com.web.interfaces.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.web.interfaces.WebInterface;
import br.com.web.pojo.DocumentPOJO;
import br.com.web.pojo.ImagePOJO;
import br.com.web.pojo.SubLinksPOJO;

/**
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class WebInterfaceImpl extends UnicastRemoteObject implements WebInterface {
	private static final long serialVersionUID = 1L;

	public WebInterfaceImpl() throws RemoteException {
	}

	@Override
	public DocumentPOJO search(String url) throws RemoteException {
		DocumentPOJO pojo = null;

		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true)
											 .ignoreHttpErrors(true)
											 .timeout(10000).get();

			if (doc != null) {
				pojo = new DocumentPOJO();

				pojo.setTitle(doc.title());
				pojo.setImagemPOJOList(this.getImages(doc));
				pojo.setSubLinkList(this.getSubLinks(doc, url));
			}
			
		} catch (ConnectException e) {
			System.out.println("::: URL NOT REACHEBALE " + url + " :::");

		} catch (HttpStatusException e) {
			System.out.println("::: CONNECTION ERROR, ERRO CODE: " + e.getStatusCode() + " :::");

		} catch (IOException ex) {
			System.out.println("::: ERROR TO READ SITE "+ url + " :::");
		}

		return pojo;
	}
	
	/**
	 * Retorna a lista de Imagens do Documento.
	 * 
	 * @param document
	 * @return
	 */
	private List<ImagePOJO> getImages(Document document){
		
		ArrayList<ImagePOJO> imgList = new ArrayList<>();
		
		Elements imgs = document.select("img");
		
		if (imgs != null && !imgs.isEmpty()) {

			for (Element img : imgs) {

				String src = img.absUrl("src").replace(" ", "%20");
				String imageName = src.substring(src.lastIndexOf("/") + 1);

				imgList.add(new ImagePOJO(src, imageName));
			}
		}
		
		return imgList;
	}
	
	/**
	 * Retorna a lista de Links do Documento 
	 * 
	 * @param document
	 * @param urlFather
	 * @return
	 */
	private List<SubLinksPOJO> getSubLinks(Document document, String urlFather){
		
		Set<String> resultList = new HashSet<String>();
		
		Elements links = document.select("a");
		
		if(links!=null && !links.isEmpty()) {
			
			for (Element link : links) {
				
				String url = link.absUrl("href");
				
				if(isValidURL(url, urlFather)) {
					resultList.add(url);
				}
			}
		}
		
		return resultList.stream().map(SubLinksPOJO::new).collect(Collectors.toList());
	}
	
	/**
	 * Valida a URL.
	 * 
	 * @param url
	 * @param urlFather
	 * @return
	 */
	private boolean isValidURL(String url, String urlFather) {
		
		if(url!=null && url.trim().length() > 0 && !url.equals(urlFather) )
			return true;
		
		return false;
	}
}
