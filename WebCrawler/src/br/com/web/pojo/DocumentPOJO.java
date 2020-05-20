package br.com.web.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto Serializado para transferencia entre Servidor e Cliente.
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class DocumentPOJO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String title;
	private List<ImagePOJO> imagemPOJOList;
	private List<SubLinksPOJO> subLinkList;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ImagePOJO> getImagemPOJOList() {
		return imagemPOJOList;
	}
	public void setImagemPOJOList(List<ImagePOJO> imagemPOJOList) {
		this.imagemPOJOList = imagemPOJOList;
	}
	public List<SubLinksPOJO> getSubLinkList() {
		return subLinkList;
	}
	public void setSubLinkList(List<SubLinksPOJO> subLinkList) {
		this.subLinkList = subLinkList;
	}
}
