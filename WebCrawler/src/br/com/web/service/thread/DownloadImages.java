package br.com.web.service.thread;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import br.com.web.pojo.ImagePOJO;

/**
 * Thread responsavel por baixar as imagens em paralelo.
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class DownloadImages extends Thread {
	
	private ImagePOJO imagePOJO;
	private File savePath;
	
	public DownloadImages(ImagePOJO imagePOJO, File savePath) {
		this.imagePOJO = imagePOJO;
		this.savePath = savePath;
	}

	@Override
	public void run() {
		
		if( !isValid()) {
			return;
		}
		
		InputStream in = null;
		OutputStream out = null;
		
		try {
			
			URL url = new URL(imagePOJO.getPath());
			in = url.openStream(); //Acessa a URL informada criando um fluxo.
			
			//Abre um fluxo ate a pasta de destino informada.
			out = new BufferedOutputStream(new FileOutputStream( savePath.getAbsolutePath() +"/"+ imagePOJO.getName()));
 
			//Salva o fluxo da imagem no local desejado
			for (int b; (b = in.read()) != -1;) {
			    out.write(b);
			}
			
		} catch (Exception e) {
			System.out.println("\n\n::: ERRO AO FAZER DOWNLOAD DA IMAGEM: "+ imagePOJO.getName() + " ::: \n"
							 + "::: URL: "+ imagePOJO.getPath() + " ::: \n");
			
		}finally {
			
			try {
				if(in!=null ) {
					in.close();
				}
				if(out!=null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Verifica se os parametros passados são validos.
	 * 
	 * @return
	 */
	private boolean isValid() {
		
		return (imagePOJO!=null &&
				imagePOJO.getName()!=null &&
				imagePOJO.getPath()!=null &&
				imagePOJO.getName().trim().length()>0 &&
				imagePOJO.getPath().trim().length()>0 &&
				(imagePOJO.getPath().endsWith("png") || imagePOJO.getPath().endsWith("jpg") || 
				 imagePOJO.getPath().endsWith("jpeg") || imagePOJO.getPath().endsWith("gif")));
	}
}
