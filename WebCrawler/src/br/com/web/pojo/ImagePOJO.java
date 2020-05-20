package br.com.web.pojo;

import java.io.Serializable;

/**
 * Objeto Serializado para transferencia entre Servidor e Cliente.
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class ImagePOJO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String path;
	private String name;
	
	public ImagePOJO(String path, String name) {
		this.path = path;
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	public String getName() {
		return name;
	}
}
