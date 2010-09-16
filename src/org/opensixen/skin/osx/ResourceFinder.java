package org.opensixen.skin.osx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.compiere.apps.AEnv;
import org.opensixen.osgi.interfaces.IResourceFinder;

public class ResourceFinder implements IResourceFinder {

	/** Directorio fuente donde encontrar los recursos en modo migracion	*/
	private String sourceDir = "/home/harlock/workspace/workspace-opensixen-born//client/src/org/compiere/";

	/** Directorio donde se copiaran	*/
	private String targetDir = "/home/harlock//workspace/workspace-opensixen-born/org.opensixen.branding/";
	
	/** Modo migracion	*/
	private boolean migrationMode = false;
	
	public ResourceFinder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public URL getResource(String name) {
		URL url = getClass().getResource("/" + name);
	
		// Si no existe el recurso y estamos en modo migracion
		if (url == null && migrationMode)	{
			migrate(name);					
		}
				
		url = getClass().getResource("/" + name);
		return url;
	}
	
	/**
	 * Copia las imagenes del directorio fuente al destino
	 * Util para nuevos paquetes de brandig, para no tener que buscar
	 * las imagenes una a una. 
	 * @param name
	 */
	private void migrate (String name)	{
		try{
			File f1 = new File( sourceDir + name);
			
			if (!f1.exists())	{
				return;
			}
			File f2 = new File(targetDir +name);
			
		      InputStream in = new FileInputStream(f1);
		      
		      OutputStream out = new FileOutputStream(f2);

		      byte[] buf = new byte[1024];
		      int len;
		      while ((len = in.read(buf)) > 0){
		        out.write(buf, 0, len);
		      }
		      in.close();
		      out.close();
		      System.out.println("File copied.");
		    }
		    catch(FileNotFoundException ex){
		      System.out.println(ex.getMessage() + " in the specified directory.");
		      System.exit(0);
		    }
		    catch(IOException e){
		      System.out.println(e.getMessage());      
		    }
	}
}
