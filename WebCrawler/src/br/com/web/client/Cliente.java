package br.com.web.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import br.com.web.client.delegate.WebDelegate;
import br.com.web.pojo.DocumentPOJO;
import br.com.web.pojo.ImagePOJO;
import br.com.web.pojo.SubLinksPOJO;
import br.com.web.service.thread.DownloadImages;

/**
 * Classe do Cliente, passado uma URL e um savePath é feito uma busca no site e 
 * caso esteja habilitado é salvo as Imagens encontradas.<br>
 * <br>
 * É possivel tb habilitar a recursividade, onde será feito o Download de imagens de SubLinks
 * 
 * 
 * @author Thiago Guimaraes
 * @date 20/05/2020
 */
public class Cliente {
	
    public static void main(String[] args) throws Exception {
    	
    	long initialTime = System.currentTimeMillis();
    	
    	String url = "http://ucp.br/index.php?lang=pt";
    	String savePath = "/Users/vertis/Desktop/WebCrawler";
    	
    	boolean downloadImages = true;
    	boolean downloadImagesSubLink = true;
    	
    	DocumentPOJO pojo = null;
		try {
			
			//Faz o acesso a pagina via servidor e busca as informacoes
			pojo = new WebDelegate().search(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		if(pojo!=null) {

			
			logResume(pojo,savePath);
			
			List<ImagePOJO> imagemPOJOList = pojo.getImagemPOJOList();
			List<SubLinksPOJO> subLinkList = pojo.getSubLinkList();
			
			if(imagemPOJOList!=null) {
				
				//Verifica se esta habilidado o download de Imagens para a Main Page
				if(downloadImages) {
					System.out.println("::: BAIXANDO IMAGEM MAIN PAGE :::");
					
					String savePathFather = savePath + "/" + pojo.getTitle(); //Caminho para salvar as imagens 
					
					for (ImagePOJO imagePOJO : imagemPOJOList) { //Percorre todas as imagens baixando
						downloadImage(imagePOJO, savePathFather);
					}
				}
			}
			
			//Verifica se esta habilidado o download de Imagens para os SubLinks
			if (downloadImagesSubLink) { 

				if (!pojo.getSubLinkList().isEmpty()) {

					for (SubLinksPOJO subLinksPOJO : subLinkList) {
						
						//Faz o acesso ao sublink via servidor e busca as informacoes
						DocumentPOJO pojoSubLink = new WebDelegate().search(subLinksPOJO.getUrl());

						if (pojoSubLink != null && pojoSubLink.getImagemPOJOList() != null) {

							System.out.println("::: BAIXANDO IMAGEM DO SUB LINK: " + pojoSubLink.getTitle() + " :::");

							for (ImagePOJO imagePOJO : pojoSubLink.getImagemPOJOList()) {
								String savePathSubLink = savePath + "/" + pojo.getTitle() + "/" + pojoSubLink.getTitle();
								downloadImage(imagePOJO, savePathSubLink);
							}
						}
					}
				}
			}
		}
		
		//Calcula o tempo total de execucao do programa
		long finalTime = System.currentTimeMillis();
		
		long segundos = ((finalTime - initialTime) / 1000 ) % 60;  
		long minutos =  ((finalTime - initialTime) / 60000 ) % 60;  
		long horas    = (finalTime - initialTime) / 3600000;        
		
		System.out.println("Tempo de execução: " +  horas + " Horas "+ minutos + " Minutos "+ segundos + " Segundos");
    }
    
    /**
     * Loga o resumo da execucao<br>
     * É salvo o log na pasta informada no savePath. 
     * 
     * @param pojo
     * @param savePath
     */
    private static void logResume(DocumentPOJO pojo, String savePath) {
		
    	StringBuilder log = new StringBuilder();
		log.append(" --------------------------------------- \n");
		log.append(" ---------- RESUMO DA BUSCA ------------ \n");
		log.append(" --------------------------------------- \n");
		log.append("\n");
		log.append("Images encontradas: ").append( pojo.getImagemPOJOList()!=null ? pojo.getImagemPOJOList().size() : 0).append("\n");
		log.append("Sublinks encontrados: ").append( pojo.getSubLinkList()!=null ? pojo.getSubLinkList().size() : 0).append("\n");
		log.append("\n");
		log.append("\n ----------- LISTA DE FOTOS ------------ \n\n\n");
    	
    	if(pojo.getImagemPOJOList()!=null) {
    		for (ImagePOJO image : pojo.getImagemPOJOList()) {
    			log.append("   - " + image.getName() + "\n");
				
			}
    	}
    	
    	log.append("\n -------- LISTA DE SUBLINK ----------- \n\n");
    	
    	if(pojo.getSubLinkList()!=null) {
    		for (SubLinksPOJO subLinksPOJO : pojo.getSubLinkList()) {
    			log.append("   - " + subLinksPOJO.getUrl() + "\n");
    			
    		}
    	}
    	
    	log.append("\n --------------------------------------- \n");
    	log.append(" ------------ FIM DO RESUMO ------------ \n");
    	log.append(" --------------------------------------- \n");
    	
    	//Escreve no console o LOG.
    	System.out.println(log);
    	
    	//Escreve em disco o LOG
		try {
			
			String path = savePath +"/"+ pojo.getTitle() + ".txt";
			
			FileWriter grava = new FileWriter(path, true);
			PrintWriter escreve = new PrintWriter(grava);
			escreve.println(log.toString());
			escreve.close();
			grava.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
    
    /**
     * Executa as Threads para download das imagens em paralelo 
     * 
     * @param pojo
     * @param savePath
     */
	private static void downloadImage(ImagePOJO pojo, String savePath) {
		File file = new File(savePath);
		file.mkdir();
		
		new DownloadImages(pojo, file).start();
    }
}
