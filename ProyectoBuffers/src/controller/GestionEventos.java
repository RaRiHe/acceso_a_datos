package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.*;
import view.*;

public class GestionEventos {

	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar;

	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() {
		actionListener_comparar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
			
				try {
					call_compararContenido();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			 
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);

		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					call_buscarPalabra();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);
	}

	private int call_compararContenido() throws IOException {
		try {
			String texto=view.getFichero1().getText();						//creo dos variables string 
			String texto2=view.getFichero2().getText();						
			if(texto.length()==0||texto2.length()==0) {						//para comproba su longitud y si es 0 lanzar un error por pantalla
				view.showError("falta alguno de los dos ficheros ");
			}else {
				boolean valorDevuelto = model.compararContenido(view.getFichero1().getText(), view.getFichero2().getText());	 //si su longitud no es 0 llamo a la funcion compara contenido
				String textoFichero1=view.getFichero1().getText();			//cojo el nombre de los dos ficheros 
				String textoFichero2=view.getFichero2().getText();
				if(valorDevuelto) {											//si el valor devuelto es true digo que son iguales 
					view.getTextArea().setText("el fichero "+textoFichero1+" es igual a  "+textoFichero2);
					return 1;
				}else {														//si el valor devuelto es false digo que son diferentes
					view.getTextArea().setText("el fichero "+textoFichero1+" es diferenrte de "+textoFichero2);
					return 0;
				}
			}
		} catch (IOException e) {
			view.showError("Uno de los dos ficheros o los dos ficheros no exixten "); 	//aqui cojo la excepcion que salta en gestion de datos i muestro por pantalla  que uno de los ficheros no existe 
			e.printStackTrace();
		}
		return 0;
		
	}

	private int call_buscarPalabra() throws IOException {
		
		int valorDevuelto = 0;
		try {
			String texto=view.getFichero1().getText();				//creo dos variables string 
			String palabra=view.getPalabra().getText();
			if(texto.length()==0||palabra.length()==0) { 			//para comproba su longitud y si es 0 lanzar un error por pantalla
				view.showError("Error o el campo de la palabra o del fichro estan vacios ");
			}else {
			valorDevuelto = model.buscarPalabra(view.getFichero1().getText(),view.getPalabra().getText(),view.getPrimera());			//llamo a la funcion buscar palabra 
			if (valorDevuelto!=-1) {								//si el valor debuelto es diferente =-1 el fichero contiene la palabra
				view.getTextArea().setText("el fichero "+view.getFichero1().getText()+" contiene la palabra "+view.getPalabra().getText()+" En la linia: " +valorDevuelto);
			}else {													//si el valor es =-1 el fichero no contiene la palabra 
				view.getTextArea().setText("el fichero "+view.getFichero1().getText()+" no contiene la palabra "+view.getPalabra().getText());
			}
			}
		} catch (IOException e) {
			view.showError("el fichero no exixte ");				//aqui cojo la excepcion que lanzo  en gestion de datos la cual dice que el fichero no existe 
			e.printStackTrace();
		}

	
		return valorDevuelto;
	}

}
