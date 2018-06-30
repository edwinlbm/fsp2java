import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


class LeituraTeclado{
    InputStreamReader reader;
    BufferedReader in;

    public LeituraTeclado(){        
        this.reader = new InputStreamReader(System.in);
        this.in = new BufferedReader(reader);        
    }

    public String getLinha(){
        try {
            return in.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public void encerrarLeitura(){
        try {
            if (this.reader != null) {this.reader.close();}
            if (this.in != null) {this.in.close();}            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}