import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

class EscritaLog {
    final String FILE = System.getProperty("user.dir") + "/"; //Caso não exista, criar via Java mkdir
    FileWriter fw = null;
    PrintWriter out = null;
    BufferedWriter bw = null;

    public EscritaLog(String fileName, String extensao) {
        try {
            this.fw = new FileWriter(FILE + fileName + extensao, true);
            this.bw = new BufferedWriter(this.fw);
            this.out = new PrintWriter(this.bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileWriter getArquivo() {
        return this.fw;
    }

    public PrintWriter getEscritor() {
        return this.out;
    }

    public void escrever(String dado) {
        try{
            this.out.println(dado);
            this.bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }    
    }

    public void encerrarEscrita(){
        try {
            if (this.bw != null) {this.bw.close();}
            if (this.out != null) {this.out.close();}
            if (this.fw != null) {this.fw.close();}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}