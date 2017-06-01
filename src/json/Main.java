package json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class Main {
	
	static int xxx=0;
	static boolean pp=true;

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text=cargarLista();
		String[] verso=text.split("00");
		generarJSON(verso);
		for (int x=0;x<verso.length;x++){
			System.out.println("-VERSO: "+verso[x]+"\n");
		}
		

	}
	
	public static void generarJSON(String[] verso){
		try {
			JsonObject o = new JsonObject();
			JsonArray a=new JsonArray();
			int i=0;
			String titulo="";
			for(int x=0;x<verso.length;x++){
				JsonObject jsonObject2 = new JsonObject();
				if(verso[x].length()>0){
					String v=verso[x].substring(0, 1);
					if(isNumeric(v)||v.equals(" ")){
						String ver="";
						if(v.equals(" ")){
							ver=verso[x].substring(1);
						}else{
							ver=verso[x];
						}
						i++;
						jsonObject2.addProperty("numero", i);
						jsonObject2.addProperty("texto", ver);
						jsonObject2.addProperty("titulo", titulo);
						titulo="";
						a.add(jsonObject2);
					}else{
						titulo=verso[x];
					}
				}
				
			}
			o.add("versiculos", a);
			o.addProperty("numero", xxx);
			
			JsonObject o2 = new JsonObject();
			JsonArray a2=new JsonArray();
			a2.add(o);
			o2.add("capitulos",a2);
			o2.addProperty("libro","Génesis");
			JsonArray a3=new JsonArray();
			a3.add(o2);
			System.out.println(o.toString());
			guardarArchivo(o.toString());
                
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
	}
	
	public static void guardarArchivo(String texto){
		
		Path path=Paths.get("prueba.txt");
		Charset utf8 = StandardCharsets.UTF_8;
		String tmp;
		String text="";
		try(BufferedReader r =Files.newBufferedReader(path,utf8)){
			while((tmp = r.readLine()) != null){
				text=text+tmp+"\n";				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		Path path2=Paths.get("prueba.txt");
		Charset utf82 = StandardCharsets.UTF_8;
		
		try(BufferedWriter w =Files.newBufferedWriter(path2, utf82)){
			w.write(text+","+texto);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String cargarLista(){
		Path path=Paths.get("archivo.txt");
		Charset utf8 = StandardCharsets.UTF_8;
		String tmp;
		String text="";
		try(BufferedReader r =Files.newBufferedReader(path,utf8)){
			while((tmp = r.readLine()) != null){
				
						String[] ll=tmp.split(" ");
						for(int x=0;x<ll.length;x++){
							if(!ll[x].equals("")){
								if(isNumeric(ll[x])){
									if(pp){
										pp=false;
										text=text+"1"+" ";
										xxx=Integer.valueOf(ll[x]);
									}else{
										text=text+"00"+ll[x]+" ";										
									}
								}else{
									text=text+ll[x]+" ";
								}
							}
						}					
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return text;
	}
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

}
