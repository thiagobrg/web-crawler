package br.com.web.pojo;

import java.io.Serializable;

/**
 * Objeto Serializado para transferencia entre Servidor e Cliente.
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class SubLinksPOJO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String url;
	
	public SubLinksPOJO(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
}
