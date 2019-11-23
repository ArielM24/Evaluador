import java.io.*;
import java.util.ArrayList;

public class Evaluador {
	public File directorio, entrada, salida;
	public Evaluador(File directorio, File entrada, File salida){
		this.directorio = directorio;
		this.entrada = entrada;
		this.salida = salida;
		verificaArchivos();
	}
	 
	public ArrayList<String[]> obtenExes(){
		ArrayList<String[]> exes = new ArrayList<String[]>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(entrada));
			String linea = br.readLine();
			while(linea != null){
				exes.add(linea.split(" "));
				linea = br.readLine();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return exes;
	}

	public void verificaArchivos(){
		try{
			if(salida.exists()){
				salida.delete();
			}
			salida.createNewFile();
			if(!entrada.exists()){
				entrada.createNewFile();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void ejecutaExes(){
		ArrayList<String[]> exes = obtenExes();
		for(String[] cmd: exes){
			ejecuta(cmd);
		}
	}

	public String[] obtenComando(String[] cmd){
		String[] exe = new String[cmd.length - 1];
		for(int i = 1; i < cmd.length; i++){
			if(i == 1)
				exe[0] = directorio.getAbsolutePath() + File.separator + cmd[1];
			else
				exe[i - 1] = cmd[i];
		}
		return exe;
	}

	public void ejecuta(String[] cmd){
		String[] exe = obtenComando(cmd);
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(salida,true));
			bw.write(cmd[0]);
			bw.newLine();
			if(new File(exe[0]).exists()){
				File tmp = new File(salida.getAbsolutePath()+".tmp");
				tmp.createNewFile();
				ProcessBuilder pb = new ProcessBuilder(exe);
				pb.redirectOutput(tmp);
				Process p = pb.start();
				p.waitFor();
				bw.write(leeTemporal(tmp));
				tmp.delete();
			}else{
				bw.write("Error");
				bw.newLine();
			}
			bw.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public String leeTemporal(File archivo){
		String s = "";
		try{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			String linea = br.readLine();
			while(linea != null){
				s += linea + "\n";
				linea = br.readLine();
			}
			br.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		Evaluador e = new Evaluador(new File(args[0]),
			new File(args[1]),new File(args[2]));
		e.ejecutaExes();
	}
}