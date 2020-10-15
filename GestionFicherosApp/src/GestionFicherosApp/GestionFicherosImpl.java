package GestionFicherosApp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gestionficheros.FormatoVistas;
import gestionficheros.GestionFicheros;
import gestionficheros.GestionFicherosException;
import gestionficheros.TipoOrden;

public class GestionFicherosImpl implements GestionFicheros{
	private File carpetaDeTrabajo=null;
	private Object [][] contenido;
	private int filas=0;
	private int columnas=3;
	private FormatoVistas formatoVistas = FormatoVistas.NOMBRES;
	private TipoOrden ordenado = TipoOrden.DESORDENADO;

	public GestionFicherosImpl() {
		carpetaDeTrabajo=File.listRoots()[0];
		actualiza();
	}

	private void actualiza() {
		// TODO Auto-generated method stub
		String[] ficheros = carpetaDeTrabajo.list(); // obtener los nombres
		// calcular el número de filas necesario
		filas = ficheros.length / columnas;
		if (filas * columnas < ficheros.length) {
			filas++; // si hay resto necesitamos una fila más
		}

		// dimensionar la matriz contenido según los resultados

		contenido = new String[filas][columnas];
		// Rellenar contenido con los nombres obtenidos
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				int ind = j * columnas + i;
				if (ind < ficheros.length) {
					contenido[j][i] = ficheros[ind];
				} else {
					contenido[j][i] = "";
				}
			}
		}
	}

	@Override
	public void arriba() {
		if(carpetaDeTrabajo.getParentFile()!=null) {
			carpetaDeTrabajo=carpetaDeTrabajo.getParentFile();
			actualiza();
		}

	}

	@Override
	public void creaCarpeta(String arg0) throws GestionFicherosException {

		//creamos la nueva carpeta  con la ruta y su nombre 
		File carpeta =new File(carpetaDeTrabajo,arg0);

		if(!carpeta.exists()&&carpetaDeTrabajo.canWrite()) {
			//si no exixte la carpeta y tienes permisos se creara 
			carpeta.mkdir();
		}else {
			//por lo contrario si existe te dira un error 
			throw new GestionFicherosException("la carpeta ya exixte");
		}
		actualiza();
	}

	@Override
	public void creaFichero(String arg0) throws GestionFicherosException {
		//creamos el nuevo fichero  con la ruta y su nombre 
		File fichero =new File(carpetaDeTrabajo, arg0);
		try {
			if(!fichero.exists()&&carpetaDeTrabajo.canWrite()) {
				//si no exixte el fichero y se tiene premisos se creara 
				fichero.createNewFile();
			}else {
				//por lo contrario si existe te dira un error 
				throw new GestionFicherosException("el fichero ya exixte");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualiza();

	}


	@Override
	public void elimina(String arg0) throws GestionFicherosException {
		File elimina =new File(carpetaDeTrabajo, arg0);

		if(elimina.exists()&&carpetaDeTrabajo.canWrite()) {
			//si exixte el fichero o directorio  se borra
			elimina.delete();
		}else {
			//por lo contrario si no existe te dira un error 
			throw new GestionFicherosException("el fichero no exixte  o no tienes permisos par aborrar-lo");
		}
		actualiza();
	}

	@Override
	public void entraA(String arg0) throws GestionFicherosException {
		// se crea la variable file con la que vamos a trabajar
		File file =new File(carpetaDeTrabajo,arg0);

		if(!file.exists()) {
			//si no existe la variable lanzamos la excepcion
			throw new GestionFicherosException("la ruta "+file.getAbsolutePath()+" NO existe");
		}

		if(!file.isDirectory()) {
			// si no es un directorio lanzamos la excepcion
			throw new GestionFicherosException(file.getName()+" NO es un directorio");
		}
		if(!file.canRead()) {
			// si no puedes leer lanzamos la excepcion
			throw new GestionFicherosException(" NO tienes premisos de lectura");
		}
		carpetaDeTrabajo=file;
		actualiza();

	}

	@Override
	public int getColumnas() {
		// TODO Auto-generated method stub
		return columnas;
	}

	@Override
	public Object[][] getContenido() {
		// TODO Auto-generated method stub
		return contenido;
	}

	@Override
	public String getDireccionCarpeta() {
		// TODO Auto-generated method stub
		return carpetaDeTrabajo.getAbsolutePath();
	}

	@Override
	public String getEspacioDisponibleCarpetaTrabajo() {
		String cadena="Espacio Libre";
		String cadena2="gb";
		long espacio =carpetaDeTrabajo.getFreeSpace();
		cadena=cadena+(espacio/1000000000)+cadena2;
		return cadena;
	}

	@Override
	public String getEspacioTotalCarpetaTrabajo() {
		String cadena="Espacio total ";
		String cadena2="gb";
		long espacio =carpetaDeTrabajo.getTotalSpace();
		cadena=cadena+(espacio/1000000000)+cadena2;
		return cadena;
	}

	@Override
	public int getFilas() {
		// TODO Auto-generated method stub
		return filas;
	}

	@Override
	public FormatoVistas getFormatoContenido() {
		// TODO Auto-generated method stub
		return formatoVistas;
	}

	@Override
	public String getInformacion(String arg0) throws GestionFicherosException {
		File file =new File (carpetaDeTrabajo,arg0);
		if(!file.canRead()) {
			throw new GestionFicherosException("no se puede leer el fichero");
		}else {
			Date fecha=new Date(file.lastModified());
			SimpleDateFormat date1=new SimpleDateFormat("yyyy/MM/dd G 'A las ' HH:mm:ss ");
			StringBuilder sb = new StringBuilder();
			sb.append("--------------------------------- INFORMACION DEL FICHERO ----------------------------------" + "\n\n");
			sb.append("Nombre: "+file.getName()+"\n");
			if(file.isDirectory()) {
				sb.append("Tipo: Directorio"+"\n");
			}else {
				sb.append("Tipo: Archivo"+"\n");
			}
			sb.append("Ruta absoluta:"+file.getAbsolutePath()+"\n");
			sb.append("Ultima fecha de modificacion: "+date1.format(fecha)+"\n");
			sb.append(file.isHidden()?"Esta oculto":"Esta visible"+"\n");
			if(file.isDirectory()) {
				long espacioTotal=file.getTotalSpace();
				long espaciolibre=file.getFreeSpace();
				long espaciodisponible=file.getUsableSpace();
				sb.append("Espacio total: "+espacioTotal/1000000000+"gb"+"\n");
				sb.append("Espacio libre: "+espaciolibre/1000000000+"gb"+"\n");
				sb.append("Espacio disponible: "+espaciodisponible/1000000000+"gb"+"\n");
				sb.append("Objetos totales: "+file.list().length+"\n");
			}else {
				long espacioTotal=file.getTotalSpace();
				sb.append("Espacio total: "+espacioTotal/8 +"\n");
			}
			String cadena=sb.toString();

			return cadena;
		}
	}

	@Override
	public boolean getMostrarOcultos() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNombreCarpeta() {
		// TODO Auto-generated method stub
		return carpetaDeTrabajo.getName();
	}

	@Override
	public TipoOrden getOrdenado() {
		// TODO Auto-generated method stub
		return ordenado;
	}

	@Override
	public String[] getTituloColumnas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getUltimaModificacion(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String nomRaiz(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numRaices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void renombra(String arg0, String arg1) throws GestionFicherosException {
		
		File elemento =new File(carpetaDeTrabajo, arg0);
		File renombre =new File(carpetaDeTrabajo, arg1);
			if(elemento.exists()&&carpetaDeTrabajo.canWrite()) {
				//si  exixte el fichero y se tiene premisos se renombra
				elemento.renameTo(renombre);
			}else {
				//por lo contrario si existe no  te dira un error 
				throw new GestionFicherosException("el fichero no existe o no tienes permisos");
			}
		
		actualiza();
	}

	@Override
	public boolean sePuedeEjecutar(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sePuedeEscribir(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sePuedeLeer(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColumnas(int arg0) {
		// TODO Auto-generated method stub
		columnas=arg0;
	}

	@Override
	public void setDirCarpeta(String arg0) throws GestionFicherosException {
		//creamos una nueva variable para poder tabajar
		File file =new File(arg0);

		if(!file.exists()) {
			//si la carpeta no existe  lanzamos una excepcion
			throw new GestionFicherosException("la ruta "+file.getAbsolutePath()+" NO existe");
		}

		if(!file.isDirectory()) {
			//si no es un directorio lanzamos una excepcion
			throw new GestionFicherosException(file.getName()+" NO es un directorio");
		}
		if(!file.canRead()) {
			//si no se puede leer lanzamos una excepcion
			throw new GestionFicherosException(" NO tienes premisos de lectura");
		}
		carpetaDeTrabajo=file;
		actualiza();


	}

	@Override
	public void setFormatoContenido(FormatoVistas arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMostrarOcultos(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrdenado(TipoOrden arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeEjecutar(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeEscribir(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeLeer(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUltimaModificacion(String arg0, long arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}
}
