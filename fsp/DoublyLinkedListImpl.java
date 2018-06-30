import java.util.ArrayList;

import java.util.ArrayList;

import java.util.ArrayList;

class Dados{
    String nomeProcesso;
    ArrayList<String> arrayLista;

    public Dados(){
        this.nomeProcesso = "";        
    }
}

public class DoublyLinkedListImpl{

    
    public static int busca(ArrayList<Dados> dados, String trace, int index){
        if(index < dados.size()){
            if(dados.get(index).arrayLista.contains(trace)){
                return dados.get(index).arrayLista.indexOf(trace);
            }else{
                return busca(dados, trace, ++index);
            }
        }else{
            return -1;
        }
    }
    
    public static void verifyTrace(ArrayList<Dados> dados, ArrayList<String> trace){
        int i , j, k;
        for(i = 0; i < trace.size(); i++){
            String tmp;
            for(j = 0; j < dados.size(); j++ ){
                for(k = 0; k < dados.get(j).arrayLista.size(); k++){
                    if(dados.get(j).arrayLista.get(k).equals(trace.get(i))){
                        System.out.println("Achou, index: " + k);
                        break;
                    }
                }
            }
            break;
        }
    }
    
    public static void main(String a[]){      
        ArrayList<String> maker = new ArrayList<String>();
        ArrayList<String> user = new ArrayList<String>();
        ArrayList<String> trace = new ArrayList<String>();
        ArrayList<Dados> arrayDados = new ArrayList<Dados>();
        Dados objDados = new Dados();
               
        
        maker.add("make");
        maker.add("ready");
        maker.add("used");
        
        user.add("ready");
        user.add("use");
        user.add("used");
        
        trace.add("make");
        trace.add("ready");
        trace.add("use");
        trace.add("used");
        
        
        objDados.nomeProcesso = "maker";
        objDados.arrayLista = maker;
        arrayDados.add(objDados);
    
        objDados = new Dados();
        objDados.nomeProcesso = "user";
        objDados.arrayLista = user;
        arrayDados.add(objDados);

        verifyTrace(arrayDados, trace);
        // System.out.println(busca(arrayDados, trace.get(0), 0));

        // for(int i = 0; i < arrayDados.size(); i++){
        //     System.out.println(arrayDados.get(i).nomeProcesso);
        //     for(int j = 0; j < arrayDados.get(i).arrayLista.size(); j++){
        //         System.out.println("\t" + arrayDados.get(i).arrayLista.get(j));
        //     }
        // }

                
    }
}