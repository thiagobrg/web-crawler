package br.com.web.server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.web.interfaces.impl.WebInterfaceImpl;

/**
 * Inicia um servidor, disponibilizando um servico de busca.
 * 
 * @author Thiago Guimaraes
 * @date 19/05/2020
 */
public class Servidor {
	
   public static void main(String[] args) {
	   
        System.out.println("Inicializando Servidor...");
        try {
        	
        	Registry registry = LocateRegistry.createRegistry(7777);
        	registry.rebind("Nome", new WebInterfaceImpl());
            
        } catch (Exception e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, "::: ERRO AO INCIAR SERVIDOR :::", e);
        }
    } 
}
