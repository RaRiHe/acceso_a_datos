package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GestionDatos {

	public GestionDatos() {

	}
	public static BufferedReader abrirFicheros(String fichero) throws IOException {
		FileReader fr =new FileReader(fichero);			//creamos el file reader del archivo que queremos 
		BufferedReader br1 =new BufferedReader(fr);		//creamos el bufferedreader desde el file reader
		return br1;										//Debolvemos el boferreader
	}

	public static void cerrarFicheros (BufferedReader br) throws IOException {
		br.close(); 									//cerramos el buferreader
		
	}

	public boolean compararContenido (String fichero1, String fichero2) throws IOException{
	
		try {
			File fi1=new File (fichero1);				//hago un acomprovacion de que los ficheros existan creando primero los dos objetos files i utilizando la funcion exists
			File fi2=new File (fichero2);
			if (!fi1.exists()||!fi2.exists()){
				throw new IOException("Uno de los dos ficheros o los dos ficheros no exixten ");		//si no exixten lanzo una excepcion que cogere en gestion de eventos
			}
			BufferedReader br=abrirFicheros(fichero1);	//creo los buffersreaders llamando a la funcion abrir ficheros y les doy una linia 
			BufferedReader br1=abrirFicheros(fichero2);
			String frase1=br.readLine();
			String frase2=br1.readLine();
			int cont=0,cont2=0;  
			while(frase1!=null&&frase2!=null){			//mientas ninguna frase se a null se repetira el bucle 
				cont2++;
				if(frase1.equals(frase2)) {				//a qui comparo las linia i si son iguales sumo uno a la variable contador 
					cont++;
				}
				frase1=br.readLine();					//renuevo las linias del bufferreader
				frase2=br1.readLine();
				if(frase1!=null&&frase2==null||frase2!=null&&frase1==null) { //compruevo que ninguna de las frases sea null si que la otra no 
					return false;
				}
				
			}
			cerrarFicheros(br);							//cierro los buferreaders llamando a la funcion cerrarficheros 
			cerrarFicheros(br1);
			if(cont==cont2){							//compruevo que sea iguales todas la linias si lo son devuelbo true si nos false
				return true;
			}else {
				return false;
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		return false;

	}

	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion) throws IOException{
		
		File fi1=new File (fichero1);					//compruevo que el fichero exista exactamente igual que antes 
		if (!fi1.exists()){
			throw new IOException("el fichero no existe ");
		}
		FileReader ArchivoUno = new FileReader (fichero1);        // creo el bufferreader igual que en el metodo anterior i le paso una linia 
		BufferedReader textoUno =new BufferedReader(ArchivoUno);
		String frase1=textoUno.readLine();
		int posicion=-1,cont=0;
		List<String> palabras= new ArrayList<String>();			//creo un array list para separar las palabras por los espacion con la ayuda de la funcion split
		if(primera_aparicion) {									
			while(frase1!=null) {								//mientas la frase tenga algun valor el bucle seguira 
				palabras =  Arrays.asList(frase1.split(" "));	//separo las palabras por espacios y compruevo si la frase contiene esa palabra si lo hace al tener la primera aparicion marcada acaba el codigo i devuelbo la linia en la que enciontrado la palabra
				if(palabras.contains(palabra)) {
					cont++;
					return cont;
				}
				frase1=textoUno.readLine(); 					//renuevo la linia 
				cont++;
			}
		}else {
			cont++;
			while(frase1!=null) {								//mientas la frase tenga algun valor el bucle seguira 
				palabras =  Arrays.asList(frase1.split(" "));	//separo las palabras por espacios y compruevo si la frase contiene esa palabra y guardo la linia de la palabra a la espera de que no alla ninguna mas para devolber la posicion
				if(palabras.contains(palabra)) {
					posicion=cont;
				}
				frase1=textoUno.readLine();
				cont++;
			}
			return posicion;
		}
		return -1;
		
		
	}	

}
