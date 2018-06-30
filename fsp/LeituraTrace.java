import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class LeituraTrace{
    private String caminhoArquivo;
    private ArrayList<String> arrayStringTrace;

    public LeituraTrace(String caminhoArquivo){
        this.caminhoArquivo = caminhoArquivo;
        this.arrayStringTrace = new ArrayList<String>();
    }

    public void lerTrace() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(this.caminhoArquivo));
        try{            
            String linha = br.readLine();            
            while(linha != null){
                if(!linha.equals("@TRACE")){
                    linha = br.readLine();
                }else{
                    linha = br.readLine();
                    break;
                }    
            }
            while(linha != null && !linha.equals("*/")){                 
                if(!linha.isEmpty()){
                    arrayStringTrace.add(linha);
                }
                linha = br.readLine();
            }            
            br.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getValor(){
        return this.arrayStringTrace;
    }
    
    //Teste:
    // public static void main (String args []){
    //     LeituraTrace read = new LeituraTrace(System.getProperty("user.dir")+"/tracefsp.txt");
    //     try{
    //         read.lerTrace();
    //     }catch(Exception e){
    //         e.printStackTrace();
    //     }
        
    //     for(int i = 0; i < read.getValor().size(); i++){
    //         System.out.println(read.getValor().get(i));
    //     }
    // }
}