import java.io.File;
import java.util.List;


import java.util.ArrayList;
import java.util.HashMap;


class MetodosUtilitarios {
    boolean flagMetodovalido = true;

    //Metodo para armazenar todas as informacoes dos processos no array do tipo Nupla
    public void addClasseMetodosObjts(String nomeClasse, ArrayList<ArrayLista> arrayLista,
            ArrayList<Nupla> arrayNupla) {
        for (int i = 0; i < arrayLista.size(); i++) {
            //new(nome processo, nome subprocesso);
            Nupla objNupla = new Nupla(nomeClasse, arrayLista.get(i).getChave());
            for (int j = 0; j < arrayLista.get(i).getValor().size(); j++) {
                objNupla.getValor().add(arrayLista.get(i).getValor().get(j));
            }
            arrayNupla.add(objNupla);
        }
    }

    //Metodo para armazenar o nome do processo, as acoes e o proximo objeto
    public void addClasseMetodos(String nomeClasse, String nomeObj, ArrayList<String> arrayMetodos,
            ArrayList<Nupla> nomeMetodoClasse) {
        //Em caso de classe ja definida, acrescenta-se apenas os metodos - especifico para loop i..N
        for (int i = 0; i < nomeMetodoClasse.size(); i++) {
            if (nomeMetodoClasse.get(i).getChave().equals(nomeClasse)) {
                for (int j = 0; j < arrayMetodos.size(); j++) {
                    nomeMetodoClasse.get(i).getValor().add(arrayMetodos.get(j));
                }
                return;
            }
        }

        //Caso a classe ainda nao exista
        nomeMetodoClasse.add(new Nupla(nomeClasse, nomeObj));
        int posArray = nomeMetodoClasse.size() - 1;//Pegando o valor mais recente

        for (int i = 0; i < arrayMetodos.size(); i++) { //Copiando para o novo array
            nomeMetodoClasse.get(posArray).getValor().add(arrayMetodos.get(i));
        }
    }

    //Metodo para armazenar em um array as acoes e proximo processo
    // (IN: String nomeProcess, IN: ArrayList<String> arrayMetodos, OUT: ArrayList<ArrayLista> arrayLista)
    public void addToArrayEscolha(String nomeProcess, ArrayList<String> arrayMetodos,
            ArrayList<ArrayLista> arrayLista) {
        
        arrayLista.add(new ArrayLista(nomeProcess));
        int posArray = arrayLista.size() - 1;//Pegando o valor mais recente, acabou de criar com o new
        for (int i = 0; i < arrayMetodos.size(); i++) { //Copiando para o novo array
            arrayLista.get(posArray).getValor().add(arrayMetodos.get(i));
        }
        // System.out.println("\naddToArrayEscolha:");
        // for(int i = 0; i < arrayLista.size(); i++){
        //     System.out.println("OBJ: " + arrayLista.get(i).getChave());
        //     for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
        //         System.out.println("\t" + arrayLista.get(i).getValor().get(j));
        //     }
        // }
        // System.out.println();
    }

    //Metodo para imprimir o array do tipo Nupla
    public void imprimirArrayNupla(ArrayList<Nupla> arrayNupla) {
        for (int i = 0; i < arrayNupla.size(); i++) {
            System.out.println("\nPROCESSO:    " + arrayNupla.get(i).getChave());
            System.out.println("ACOES:");
            for (int j = 0; j < arrayNupla.get(i).getValor().size(); j++) {
                System.out.println("\t     " + arrayNupla.get(i).getValor().get(j));
            }
            System.out.println("SUBPROCESSO: " + arrayNupla.get(i).getObj());
        }
    }

    //Metodo para imprimir array de array do tipo Nupla
    public void imprimirArrayProcessos(ArrayList<ArrayList<Nupla>> arrayProcessos) {
        for(int x = 0; x < arrayProcessos.size(); x++){
            ArrayList<Nupla> arrayNupla = arrayProcessos.get(x);
            System.out.println("\nPROCESSO: " + x);
            for (int i = 0; i < arrayNupla.size(); i++) {
                System.out.println("\n\tPROCESSO:    " + arrayNupla.get(i).getChave());
                System.out.println("\tACOES:");
                for (int j = 0; j < arrayNupla.get(i).getValor().size(); j++) {
                    System.out.println("\t\t     " + arrayNupla.get(i).getValor().get(j));
                }
                System.out.println("\tSUBPROCESSO: " + arrayNupla.get(i).getObj());
            }
        }
    }

    //Metodo para imprimir o array do tipo ArrayLista
    public void imprimeArrayLista(ArrayList<ArrayLista> arrayLista) {
        for (int i = 0; i < arrayLista.size(); i++) {
            for (int j = 0; j < arrayLista.get(i).getValor().size(); j++) {
                System.out.print(" " + arrayLista.get(i).getValor().get(j));
            }
            System.out.println(" -> " + arrayLista.get(i).getChave());
        }
        System.out.println();
    }

    //Metodo para ajustar o uma string quando a mesma esteja em caixa alta
    public String reformatClassName(String nomeClasse) {

        if(nomeClasse.contains(":")){
            int indice = nomeClasse.indexOf(':');
            nomeClasse = nomeClasse.substring(0, indice+1) + nomeClasse.charAt(nomeClasse.indexOf(':')+1)+"".toUpperCase() + nomeClasse.substring(indice+2).toLowerCase();
            return nomeClasse;
        }
        
        /*Todos para minúsculo com exceção do primeiro caractere*/
        nomeClasse = nomeClasse.charAt(0) + nomeClasse.substring(1).toLowerCase();

        int indice = nomeClasse.indexOf('_');
        /*Se encontrar um underscore '_' retorne != -1 */
        while(indice != -1){
            /*Evitar indice fora do range*/
            if(indice < nomeClasse.length() - 1){ 
                /*A inicial de cada palavra separada por underscore passa a ser maiúscula*/               
                nomeClasse = nomeClasse.substring(0, indice) + (nomeClasse.charAt(indice+1) + "").toUpperCase() 
                + nomeClasse.substring(indice+2,nomeClasse.length());
                /*Verifica-se a próxima ocorrência de underscore*/
                indice = nomeClasse.indexOf('_');
            }else{
                /*A palavra que terminar com 1 ou mais underscores perde tais caracteres*/
                nomeClasse = nomeClasse.replaceAll("_", "");
                break;
            }
        }
        return nomeClasse;  
    }

    //Metodo para definir qual opcao do Escolha sera adotada para escrita no disco
    public int inputEscolha(ArrayList<ArrayLista> arrayLista) {
        if (arrayLista.size() > 1) {
            int escolhaToInt = 0;
            // System.out.println("\nQual OU voce quer?");
            // LeituraTeclado sc = new LeituraTeclado();

            //Exibindo na tela as opcoes
            // for (int i = 0; i < arrayLista.size(); i++) {
            //     System.out.print(i + " - ");
            //     for (int j = 0; j < arrayLista.get(i).getValor().size(); j++) {
            //         System.out.print(" " + arrayLista.get(i).getValor().get(j));
            //     }
            //     System.out.println(" -> " + arrayLista.get(i).getChave());
            // }
            //Lendo a escolha do usuario
            // String escolhaParse = sc.getLinha();

            //Covertendo para inteiro
            try {
                // escolhaToInt = Integer.parseInt(escolhaParse);
                escolhaToInt = 0;
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido, setando indice para 0");
            }
            if (escolhaToInt < 0 || escolhaToInt >= arrayLista.size()) {
                System.out.println("Valor invalido, setando indice para 0");
                escolhaToInt = 0;
            }
            System.out.println();
            return escolhaToInt;
        }
        return 0;
    }

    //Metodo para definir qual opcao do Escolha sera adotada para escrita no disco
    public int inputEscolhaTrace(ArrayList<ArrayLista> arrayLista, String traceVal) {
        if (arrayLista.size() > 1) {
            int escolhaToInt = 0;

            for (int i = 0; i < arrayLista.size(); i++) {
                escolhaToInt = arrayLista.get(i).getValor().indexOf(traceVal);
                if (escolhaToInt != -1) {
                    System.out.println(">>>>>>>>" + escolhaToInt);
                    return escolhaToInt;
                } else {
                    System.out.println(">>>>>>>>" + escolhaToInt);
                }
            }
            System.out.println(">>>>>>>>" + escolhaToInt);

        }
        return -1;
    }

    //Metodo para definir uma acao dentre as opcoes do arrayAlfabeto
    public String escolhaOptAlfabeto(ArrayList<String> arrayAlfabeto, ArrayList<String> nomeMetodos) { //Arrumar o metodo
        String entradaUser = "";

        if (arrayAlfabeto.size() > 0) {
            LeituraTeclado sc = new LeituraTeclado();
            System.out.println("\nEscolha uma acao possivel do alfabeto:");
            for (int i = 0; i < arrayAlfabeto.size(); i++) {
                System.out.println("\t" + arrayAlfabeto.get(i));
            }

            System.out.print("\n?> ");
            entradaUser = sc.getLinha();
            System.out.println("---> " + entradaUser);

            if (arrayAlfabeto.contains(entradaUser)) {// Verificar se esta ok
                nomeMetodos.add(entradaUser); //Talvez tirar
                arrayAlfabeto.clear();
                return entradaUser;
            } else {
                entradaUser = arrayAlfabeto.get(0);
                arrayAlfabeto.clear();
                return entradaUser;
            }
        }
        return "";
    }

    //Metodo para fazer a busca da acao/metodo da Classe/Processo passada como parametro
    //Saida: Se nao encontrar retorna-se "", a classe pode ser o processo STOP(ele nao possui definicao) ou o processo nao foi bem definido 
    public String buscaProcesso(String nomeProcesso, ArrayList<Nupla> arrayNupla, int indexOccur) {
        for (int i = 0; i < arrayNupla.size(); i++) {
            if (arrayNupla.get(i).getChave().equalsIgnoreCase(nomeProcesso)) {
                //Se achou, retorna o primeiro metodo/acao do processo buscado
                return arrayNupla.get(i).getValor().get(0);
            }
        }
        //Senao, retorna vazi   o
        return "";
    }

    //Metodo para fazer a busca pela ocorrencia de uma classe dado um indice
    //Util para saber se ha mais metodos da mesma classe em outra posicao do array
    public boolean buscaClasse(String nomeClasse, ArrayList<Nupla> arrayNupla, int indexOccur) {
        for (int i = indexOccur; i < arrayNupla.size(); i++) {
            if (arrayNupla.get(i).getChave().equalsIgnoreCase(nomeClasse)) {
                return true;
            }
        }
        return false;
    }

    //Metodo para verificar se uma das acoes do array sao validas para o caminho do trace 
    public String alfabetoCheckTrace(ArrayList<String> arrayAlfabeto, String stringTrace, int pos) {
        if (pos > arrayAlfabeto.size() - 1) {
            System.out.println("\nCAMINHO INVALIDO");
            return "";
        }

        if (arrayAlfabeto.get(pos).equals(stringTrace)) {
            return stringTrace;
        } else {
            return alfabetoCheckTrace(arrayAlfabeto, stringTrace, pos + 1);
        }
    }


    

    // Metodo para escrever as classes de cada processo para o disco no diretorio
    // /Saida
    public void escreverNoDiscov2(String nomeClasse, String nomeObj, ArrayList<String> metodosClasse,
            ArrayList<Nupla> arrayNupla, boolean ultimoMetodo, boolean flagLoop, int indexOccur, HashMap<String,ArrayList<String>> dicionario,
            DadosRepeticao dadosRepeticao) {
       
          
        // TODO ---> Arrumar maneira mais eficiente
        boolean arqExits = false;
        File fp = new File(System.getProperty("user.dir") + "/Saida/" + nomeClasse + ".java");
        if (fp.exists()) {
            arqExits = true;
        }        
        boolean qntdClasses = false;
        qntdClasses = buscaClasse(nomeClasse, arrayNupla, indexOccur + 1);

        EscritaArquivo arqSaida = new EscritaArquivo(nomeClasse, ".java");

        

        String escritaDados = "";
        String buscaDado = "";
        buscaDado = buscaProcesso(nomeObj, arrayNupla, 0);
        // Pode retornar "" por um motivo, processo nao definido. Neste caso eh o
        // processo Stop ou um erro de definicao

        if (buscaDado.equals("")) {
            // TODO ---> Nao eh o mesmo da variavel global, tentar apagar a variavel global
            // (se nao ouver dependencias)
            if (nomeObj.equals("Stop")) {
                flagLoop = true;
            } else {
                System.out.println("Erro na definicao do processo.");
                return;
            }
        }

        // Em caso de ocorrer o processo STOP, o retorno eh ""
        // e precisa setar a thread pra null

        if (!arqExits) {
            escritaDados += "class " + nomeClasse + "{\n";
        }       


        if(metodosClasse.size() == 0){
            escritaDados += "}";
            arqSaida.escrever(escritaDados);
            return;
        }

        // TODO ---> Comentei os stops, rever depois, aparentemente, como o trace é finito...um return ao final basta
        // if(metodosClasse.size() > 0){

            //     escritaDados += "\tpublic void " + metodosClasse.get(metodosClasse.size() - 1) + "(){\n"
            //             + "\t\tSystem.out.println(" + "\"" + metodosClasse.get(metodosClasse.size() - 1) + "\"" + ");\n";
                        
            //     if (metodosClasse.get(metodosClasse.size() - 1).equals("stop")) {
            //         // escritaDados += "\t\tSystem.exit(0);\n";
            //     } else if (metodosClasse.get(metodosClasse.size() - 1).equals("_stop_")) {
            //         // escritaDados += "\t\tSystem.exit(0);\n";
            //     }
        
            //     if (nomeObj.equals("Stop")) {
            //         // escritaDados += "\t\tSystem.exit(0);\n";
            //     }
        
            //     if (!ultimoMetodo && !nomeClasse.equals(nomeObj)) {
            //         // Para o ultimo metodo nao criar uma instancia do primeiro que desencadeou o
            //         // loop e acabar estourando a pilha
            //         // e para nao instanciar um objeto da propria classe em que se encontra
        
            //         // Se o proximo processo eh STOP
            //         if (nomeObj.equals("Stop")) {
            //             // escritaDados += "\t\tSystem.exit(0);\n";
            //         }
            //         // Senao, instancia um obj
            //         // else {
            //         //     escritaDados += "\n\t\t" + nomeObj + " obj" + nomeObj + " = new " + nomeObj + "();\n" + "\t\tobj"
            //         //             + nomeObj + "." + buscaDado + "();\n";
            //         // }
            //     }
            //     escritaDados += "\t}";
        
            //     arqSaida.escrever(escritaDados);
            //     ultimoMetodo = false;
            // }
        arqSaida.escrever(escritaDados);       
        
        /*Recupera dados do dicionário para tratar ações duplicadas*/
        ArrayList<String> tmp = null;
        if(dicionario.size() > 0){
            if(dicionario.containsKey(nomeClasse)){
                tmp = dicionario.get(nomeClasse);
            }
        }

        ArrayList<String> arrayDicionario = new ArrayList<String>();

        for (int i = 0; i < metodosClasse.size(); i++) {            
            if(tmp != null){
                /*Se a ação ainda nã está no dicionário é porque não é uma cópia*/
                if(!tmp.contains(metodosClasse.get(i)) && !metodosClasse.get(i).contains("$")){
                    arqSaida.escrever("\tpublic void " + metodosClasse.get(i) + "(){");
                    arqSaida.escrever("\t\t" + "System.out.println(" + "\"" + metodosClasse.get(i) + "\");");
                    if(metodosClasse.get(i).equals("_init_")){
                        // System.out.println("DEBUG>>>>>>>1");
                        String saidaLoop = addDadosDoLoop(dadosRepeticao);
                        arqSaida.escrever(saidaLoop);
                    }
                    arqSaida.escrever("\t}");
                }
            /*Grava apenas a ação que não contiver $, caso contenha a mesma é uma ação duplicada*/
            }else if(!metodosClasse.get(i).contains("$")){ 
                arqSaida.escrever("\tpublic void " + metodosClasse.get(i) + "(){");
                arqSaida.escrever("\t\t" + "System.out.println(" + "\"" + metodosClasse.get(i) + "\");");
                if(metodosClasse.get(i).equals("_init_")){
                    // System.out.println("DEBUG>>>>>>>>>>2");
                    String saidaLoop = addDadosDoLoop(dadosRepeticao);
                    arqSaida.escrever(saidaLoop);
                }
                arqSaida.escrever("\t}");
            }
        }

        // Fecha chave da classe
        if (qntdClasses == false) {
            arqSaida.escrever("}");
        }

        arqSaida.encerrarEscrita();
    }

    public String addDadosDoLoop(DadosRepeticao dadosRepeticao){
            String escritaDados = "\t\t";
            if(dadosRepeticao != null){
                escritaDados += "\n\t\tint " + dadosRepeticao.getIndice() + " = " + dadosRepeticao.getValorIndice() + ";\n";
                //Escrevendo process parameter
                for(int k = 0; dadosRepeticao.getProcParam() != null && k < dadosRepeticao.getProcParam().size(); k++){
                    escritaDados += "\t\tint " + dadosRepeticao.getProcParam().get(k).getPrim() +
                    " = " + dadosRepeticao.getProcParam().get(k).getUlt() + ";";
                }
                //Loop
                escritaDados += "\n\t\t" + "while(true){";
                for(int i = 0; i < dadosRepeticao.getCondicaoOperacao().size(); i++){
                    escritaDados += "\n\t\t\tif(" + dadosRepeticao.getCondicaoOperacao().get(i).getCondicao().getOperandoE() 
                    + dadosRepeticao.getCondicaoOperacao().get(i).getCondicao().getComparador()  
                    + dadosRepeticao.getCondicaoOperacao().get(i).getCondicao().getOperandoD()
                    + ")"
                    +"{\n"
                        + "\t\t\t\t" + dadosRepeticao.getCondicaoOperacao().get(i).getNomeAcao() + "();\n"
                        //TODO ---> Aceitar em qualquer ordem
                        + "\t\t\t\t" + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getOperandoE() 
                        + " = " + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getOperandoE()
                        + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getComparador()
                        + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getOperandoD() + ";"
                    +"\n\t\t\t}";
                    
                }
                escritaDados += "\n\t\t}";
            }
        return escritaDados;
    }

    //Metodo para escrever as classes de cada processo para o disco no diretorio /Saida
    public void escreverNoDisco(String nomeClasse, String nomeObj, ArrayList<String> metodosClasse,
            ArrayList<Nupla> arrayNupla, boolean ultimoMetodo, boolean flagLoop, int indexOccur) {

        //TODO ---> Arrumar maneira mais eficiente
        boolean arqExits = false;
        File fp = new File(System.getProperty("user.dir") + "/Saida/" + nomeClasse + ".java");
        if (fp.exists()) {
            arqExits = true;
        }
        boolean qntdClasses = false;
        qntdClasses = buscaClasse(nomeClasse, arrayNupla, indexOccur + 1);

        EscritaArquivo arqSaida = new EscritaArquivo(nomeClasse, ".java");

        String escritaDados = "";
        String buscaDado = "";
        buscaDado = buscaProcesso(nomeObj, arrayNupla, 0);
        //Pode retornar "" por um motivo, processo nao definido. Neste caso eh o processo Stop ou um erro de definicao

        if (buscaDado.equals("")) {
            //TODO ---> Nao eh o mesmo da variavel global, tentar apagar a variavel global (se nao ouver dependencias)
            if (nomeObj.equals("Stop")) {
                flagLoop = true;
            } else {
                System.out.println("Erro na definicao do processo.");
                return;
            }
        }

        //Em caso de ocorrer o processo STOP, o retorno eh ""
        //e precisa setar a thread pra null

        if (!arqExits) {
            escritaDados += "class " + nomeClasse + "{\n";
        }


        //TODO ---> Comentei os stops, rever depois
        escritaDados += "\tpublic void " + metodosClasse.get(metodosClasse.size() - 1) + "(){\n"
                + "\t\tSystem.out.println(" + "\"" + metodosClasse.get(metodosClasse.size() - 1) + "\"" + ");\n";
        if (metodosClasse.get(metodosClasse.size() - 1).equals("stop")) {
            // escritaDados += "\t\tSystem.exit(0);\n";
        } else if (metodosClasse.get(metodosClasse.size() - 1).equals("_stop_")) {
            // escritaDados += "\t\tSystem.exit(0);\n";
        }

        if (nomeObj.equals("Stop")) {
            // escritaDados += "\t\tSystem.exit(0);\n";
        }

        if (!ultimoMetodo && !nomeClasse.equals(nomeObj)) {
            //Para o ultimo metodo nao criar uma instancia do primeiro que desencadeou o loop e acabar estourando a pilha
            //e para nao instanciar um objeto da propria classe em que se encontra

            //Se o proximo processo eh STOP
            if (nomeObj.equals("Stop")) {
                // escritaDados += "\t\tSystem.exit(0);\n";
            }
            //Senao, instancia um obj
            else {
                escritaDados += "\n\t\t" + nomeObj + " obj" + nomeObj + " = new " + nomeObj + "();\n" + "\t\tobj"
                        + nomeObj + "." + buscaDado + "();\n";
            }
        }
        escritaDados += "\t}";

        arqSaida.escrever(escritaDados);
        ultimoMetodo = false;

        //Escrita dos metodos na ordem reversa devido ao encadeamento de acoes na notacao FSP
        int j;
        for (int i = metodosClasse.size() - 2; i >= 0; i--) {
            j = i + 1;
            arqSaida.escrever("\tpublic void " + metodosClasse.get(i) + "(){");
            arqSaida.escrever("\t\t" + "System.out.println(" + "\"" + metodosClasse.get(i) + "\");");
            // arqSaida.escrever("\t\t" + metodosClasse.get(j) + "();");
            arqSaida.escrever("\t}");
        }
        //Fecha chave da classe

        if (qntdClasses == false) {
            arqSaida.escrever("}");
        }

        arqSaida.encerrarEscrita();
    }

    //Metodo para escrever as classes de cada processo para o disco no diretorio /Saida
    public void escreverNoDiscoTrace(String nomeClasse, String nomeObj, ArrayList<String> metodosClasse,
            ArrayList<Nupla> arrayNupla, boolean ultimoMetodo, boolean flagLoop, int indexOccur) {

        //TODO ---> Arrumar maneira mais eficiente
        boolean arqExits = false;
        File fp = new File(System.getProperty("user.dir") + "/Saida/" + nomeClasse + ".java");
        if (fp.exists()) {
            arqExits = true;
        }
        boolean qntdClasses = false;
        qntdClasses = buscaClasse(nomeClasse, arrayNupla, indexOccur + 1);

        EscritaArquivo arqSaida = new EscritaArquivo(nomeClasse, ".java");

        String escritaDados = "";
        String buscaDado = "";
        buscaDado = buscaProcesso(nomeObj, arrayNupla, 0);
        //Pode retornar "" por um motivo, processo nao definido. Neste caso eh o processo Stop ou um erro de definicao

        if (buscaDado.equals("")) {
            //TODO ---> Nao eh o mesmo da variavel global, tentar apagar a variavel global (se nao ouver dependencias)
            if (nomeObj.equals("Stop")) {
                flagLoop = true;
            } else {
                System.out.println("Erro na definicao do processo.");
                return;
            }
        }

        //Em caso de ocorrer o processo STOP, o retorno eh ""
        //e precisa setar a thread pra null

        if (!arqExits) {
            escritaDados += "class " + nomeClasse + "{\n";
        }

        if (!metodosClasse.get(metodosClasse.size() - 1).contains("_")) {
            escritaDados += "\tpublic void " + metodosClasse.get(metodosClasse.size() - 1) + "(){\n"
                    + "\t\tSystem.out.println(" + "\"" + metodosClasse.get(metodosClasse.size() - 1) + "\"" + ");\n";
            // if (metodosClasse.get(metodosClasse.size() - 1).equals("stop")) {
            //     escritaDados += "\t\tSystem.exit(0);\n";
            // } else if (metodosClasse.get(metodosClasse.size() - 1).equals("_stop_")) {
            //     escritaDados += "\t\tSystem.exit(0);\n";
            // }

            // if (nomeObj.equals("Stop")) {
            //     escritaDados += "\t\tSystem.exit(0);\n";
            // }

            if (!ultimoMetodo && !nomeClasse.equals(nomeObj)) {
                //Para o ultimo metodo nao criar uma instancia do primeiro que desencadeou o loop e acabar estourando a pilha
                //e para nao instanciar um objeto da propria classe em que se encontra

                //Se o proximo processo eh STOP
                // if (nomeObj.equals("Stop")) {
                //     escritaDados += "\t\tSystem.exit(0);\n";
                // }
            }
            escritaDados += "\t}";
        }

        arqSaida.escrever(escritaDados);
        ultimoMetodo = false;

        String stringClasse = "";
        //O ultimo metodo e escrito antes pois pode ser um stop
        for (int i = 0; i < metodosClasse.size() - 1; i++) {
            if (metodosClasse.get(i).contains("_")) {
                stringClasse += "\tpublic void " + metodosClasse.get(i).substring(0, metodosClasse.get(i).length() - 2)
                        + "(){\n";
                stringClasse += "\t\t" + "System.out.println(" + "\""
                        + metodosClasse.get(i).substring(0, metodosClasse.get(i).length() - 2) + "\");\n";
                stringClasse += "\t}\n";
            } else {
                stringClasse += "\tpublic void " + metodosClasse.get(i) + "(){\n";
                stringClasse += "\t\t" + "System.out.println(" + "\"" + metodosClasse.get(i) + "\");\n";
                stringClasse += "\t}\n";
            }
        }

        arqSaida.escrever(stringClasse);

        //Fecha chave da classe

        if (qntdClasses == false) {
            arqSaida.escrever("}");
        }

        arqSaida.encerrarEscrita();
    }
   
    public ArrayList<ArrayList<Nupla>> acaoRelabelling(ArrayList<ArrayList<Nupla>> arrayProcessos, ArrayList<Relabelling> relabelling){
        for(int i = 0; i < arrayProcessos.size(); i++){
            ArrayList<Nupla> arrayNupla = arrayProcessos.get(i);
            for(int j = 0; j < arrayNupla.size(); j++){
                ArrayList<String> nomeMetodos = arrayNupla.get(j).getValor(); 
                for(int k = 0; k < relabelling.size(); k++ ){
                    //TODO ---> Usar um Map em relabelling para evitar lentidao
                    for(int x = 0; x < nomeMetodos.size(); x++){
                        if(nomeMetodos.get(x).equals(relabelling.get(k).getRotuloAntigo())){
                            nomeMetodos.set(x, relabelling.get(k).getNovoRotulo());
                        }
                    }
                }
            }
        }
        return arrayProcessos;        
    }

    public String metodoRun(ArrayList<String> arrayTrace, String textoSaida){                       
        textoSaida += "\tpublic void run(){\n";
        textoSaida += "\t\ttry{\n";
        /*while*/
        textoSaida += "\t\t\twhile(true){\n";
        for(int i = 0; i < arrayTrace.size(); i++){
            textoSaida += "\t\t\t\t" + arrayTrace.get(i) + "();\n";            
        }
        textoSaida += "\t\t\t\t" + "return;\n";  
        textoSaida += "\t\t\t}\n";                    
        textoSaida += "\t\t}catch(Exception e){\n";
        textoSaida += "\t\t\t e.printStackTrace();\n\t\t}\n\t}\n";
        
        return textoSaida;
    }


    public int escreverClassePrincipalv3(ArrayList<ArrayList<Nupla>> arrayProcessos, ComposicaoParalelaData composicaoParalela, int contaNomeAcao,
    ArrayList<DadosLoop> arrayDadosLoop, HashMap<String,ArrayList<String>> dicionario, ArrayList<String> arrayTrace, ArrayList<String> traceTmp){

        //TODO ---> Verificar quando o size não for > 1
        String nomeClassePrincipal = "MainClass";
        EscritaArquivo objEscrita;
        String textoSaida = "";
        String textoStart = "";
        
        /*Imports*/
        textoSaida += "import java.lang.Runnable;\nimport java.lang.Thread;\n\n";
        /*Classe Principal*/
        if(composicaoParalela != null){
            nomeClassePrincipal = reformatClassName(composicaoParalela.getNomeComposicao());
        }
        textoSaida += "class " + nomeClassePrincipal + " implements Runnable{\n";
        textoSaida += "\tThread objThread;\n";

        /*Instância das ações de cada processo (que não está presente na composição) e/ou subprocesso*/
        boolean haProcesso;
        for(int i = 0; i < arrayProcessos.size(); i++){
            ArrayList<Nupla> arrayNupla = arrayProcessos.get(i);
            for(int j = 0; j < arrayNupla.size(); j++){
                haProcesso = false;
                /*Se há composição, escreve-se apenas os processos (subprocessos) que não ocorrem na composição*/
                /*Se não há composição escreve os objetos do arrayNupla*/
                if(composicaoParalela != null){
                    for(int k = 0; k < composicaoParalela.getArrayComposicao().size(); k++){
                        ArrayList<DadosParalelos> arrayDadosParalelos = composicaoParalela.getArrayComposicao().get(k);
                        for(int l = 0; l < arrayDadosParalelos.size(); l++){
                            DadosParalelos dadosParalelos = arrayDadosParalelos.get(l);
                            if(arrayNupla.get(j).getChave().equalsIgnoreCase(dadosParalelos.getChave())){
                                haProcesso = true;
                            }
                        }
                    }
                }
                //TODO ---> Verificar como fica a ação >> (ação.ação->ACAO).
                if(haProcesso == false){
                    // String nomeObj = arrayNupla.get(j).getChave().toLowerCase() +"$" + contaNomeAcao;
                    
                    String nomeObj = arrayNupla.get(j).getChave().toLowerCase() +"$";
                    // Verificação no if para evitar duplicação de atributos
                    if(!textoSaida.contains(arrayNupla.get(j).getChave() + " " + nomeObj + ";")){
                        textoSaida += "\t" + arrayNupla.get(j).getChave() + " " + nomeObj + ";\n";
                        /*Pre processamento do método start()*/
                        textoStart += "\t\t" + nomeObj + " = new " + arrayNupla.get(j).getChave() + "();\n";
                        contaNomeAcao++;
                    }                    
                }
            }
        }

        /*Se há composição paralela, add à classe principal objetos com base na composição*/
        if(composicaoParalela != null){                      
            //TODO ---> Fazer verificações para abortar a execução caso as necessidades não sejam satisfeitas    
            
            /*Objetos dos processos e Threads*/            
            for(int i = 0; i < composicaoParalela.getArrayComposicao().size(); i++){
                ArrayList<DadosParalelos> arrayDadosParalelos = composicaoParalela.getArrayComposicao().get(i);
                for(int j = 0; j < arrayDadosParalelos.size(); j++){
                    DadosParalelos dadosParalelos = arrayDadosParalelos.get(j);
                    ArrayList<String> nomeMetodos = dadosParalelos.getValor();
                    for(int k = 0; k < nomeMetodos.size(); k++){
                        textoSaida += "\t" + reformatClassName(dadosParalelos.getChave()) + " " + reformatClassName(nomeMetodos.get(k)) + ";\n";
                    }
                }
            }
        }

                            
        /*Método start()*/
        textoSaida += "\n\tpublic void start(){\n";
        textoSaida += textoStart;
        
        /*Se existir composição, inicializa-se a sua instância ao método start*/
        if(composicaoParalela != null){    
            for(int i = 0; i < composicaoParalela.getArrayComposicao().size(); i++){
                ArrayList<DadosParalelos> arrayDadosParalelos = composicaoParalela.getArrayComposicao().get(i);
                for(int j = 0; j < arrayDadosParalelos.size(); j++){
                    DadosParalelos dadosParalelos = arrayDadosParalelos.get(j);
                    ArrayList<String> nomeMetodos = dadosParalelos.getValor();
                    for(int k = 0; k < nomeMetodos.size(); k++){
                        textoSaida += "\t\t" + reformatClassName(nomeMetodos.get(k)) + " = new " +reformatClassName(dadosParalelos.getChave()) + "();\n";
                    }
                }
            }
        }
        textoSaida += "\t\tobjThread = new Thread(this);\n";
        textoSaida += "\t\tobjThread.start();\n\t}\n\n";

        /*Método run() com base no Trace*/
                

        // for(int k = 0; k < arrayTrace.size(); k++){
        //     validaTrace(dicionario, arrayTrace.get(k).split("[$]")[0], arrayTrace.get(k).split("[$]")[1].split("[.]")[1]);
        // }




        //Se o arrayDadosLoop nao for vazio, acrescenta-se a variavel; 
        //  try {
        //     if (arrayDadosLoop != null) {
        //         if (!arrayDadosLoop.get(0).getIndiceVal().equals("")) {
        //             textoSaida += "\n\t" + "int " + arrayDadosLoop.get(0).getIndiceVal() + ";";
        //         }
        //     }
        // } catch (Exception e) {
        //     System.out.println("\nExcecao, nao ha dados em arrayDadosLoop");
        //     e.printStackTrace();
        // }

        // if (procParam != null && !procParam.isEmpty()) {
        //     classePrincipal += "\n\t" + "int " + procParam.get(0).getPrim() + " = " + procParam.get(0).getUlt() + ";";
        // }


        // textoSaida = metodoRun(arrayTrace, textoSaida);
        textoSaida = metodoRun(traceTmp, textoSaida);
        
        /*Método principal*/
        textoSaida += "\tpublic static void main (String args []){\n";
        textoSaida += "\t\t" + nomeClassePrincipal + " obj" + nomeClassePrincipal+ " = new " + nomeClassePrincipal +"();\n";
        textoSaida += "\t\tobj" + nomeClassePrincipal +".start();\n";
        textoSaida += "\t}\n}";
        
        objEscrita = new EscritaArquivo(nomeClassePrincipal, ".java");
        objEscrita.escrever(textoSaida);
        objEscrita = null;

        return contaNomeAcao;
    }    

    public void escreverClassePrincipal(String nomeClasse, ArrayList<String> nomeMetodos, boolean flagLoop,
            ArrayList<DadosLoop> arrayDadosLoop, ArrayList<Pares> procParam, ArrayList<String> arrayMetodoLoop,
            ArrayList<Nupla> arrayNupla) {
        EscritaArquivo objEscrita = new EscritaArquivo("Main" + reformatClassName(nomeClasse), ".java");
        String classePrincipal = "import java.lang.Runnable;\nimport java.lang.Thread;\n";
        //Classe
        classePrincipal += "\nclass Main" + reformatClassName(nomeClasse) + " implements Runnable{";
        classePrincipal += "\n\tThread objThread;" + "\n\t" + reformatClassName(nomeClasse) + " obj"
                + reformatClassName(nomeClasse) + ";";

        //Se o arrayDadosLoop nao for vazio, acrescenta-se a variavel; 
        try {
            if (arrayDadosLoop != null) {
                if (!arrayDadosLoop.get(0).getIndiceVal().equals("")) {
                    classePrincipal += "\n\t" + "int " + arrayDadosLoop.get(0).getIndiceVal() + ";";
                }
            }
        } catch (Exception e) {
            System.out.println("\nExcecao, nao ha dados em arrayDadosLoop");
        }

        if (procParam != null && !procParam.isEmpty()) {
            classePrincipal += "\n\t" + "int " + procParam.get(0).getPrim() + " = " + procParam.get(0).getUlt() + ";";
        }

        //Metodo start()
        classePrincipal += "\n\n\tpublic void start(){" + "\n\t\tobj" + reformatClassName(nomeClasse) + " = new "
                + reformatClassName(nomeClasse) + "();" + "\n\t\tobjThread = new Thread(this);";
        //Se o arrayDadosLoop nao for vazio, acrescenta-se ex: i = N; 
        try {
            if (arrayDadosLoop != null) {
                if (!arrayDadosLoop.get(0).getIndiceVal().equals("")) {
                    classePrincipal += "\n\t\t" + arrayDadosLoop.get(0).getIndiceVal() + "="
                            + arrayDadosLoop.get(0).getOrdemIntervalo() + ";";
                }
            }
        } catch (Exception e) {
            System.out.println("\nExcecao, nao ha dados em arrayDadosLoop");
        }

        classePrincipal += "\n\t\tobjThread.start();" + "\n\t}";
        //Metodo run()
        classePrincipal += "\n\n\tpublic void run(){" + "\n\t\twhile(true){";
        //Para evitar chamar o metodo fake

        // if(!nomeMetodos.get(0).equals("_init_")){
        classePrincipal += "\n\t\t\tobj" + reformatClassName(nomeClasse) + "." + nomeMetodos.get(0) + "();";
        // }

        // else if(arrayNupla.size() > 1 ){
        //     classePrincipal += "\n\t\t\tobj" + arrayNupla.get(0).getChave() +"."+ arrayNupla.get(1).getValor().get(0) + "();";            
        // }                    

        if (arrayDadosLoop != null) {

            for (int i = 0; i < arrayDadosLoop.size(); i++) {
                if (arrayDadosLoop.get(i).getCondicaoVal().equals("")) {
                    break;
                } else {
                    classePrincipal += "\n\t\t\tif" + arrayDadosLoop.get(i).getCondicaoVal() + "{";
                }
                if (arrayMetodoLoop != null && !arrayMetodoLoop.get(i).equals("_init_")) {
                    classePrincipal += "\n\t\t\t\tobj" + nomeClasse + "." + arrayMetodoLoop.get(i) + "();";
                }

                if (!arrayDadosLoop.get(i).getNovoIndice().equals("")) {
                    classePrincipal += "\n\t\t\t\t" + arrayDadosLoop.get(i).getIndiceVal() + "="
                            + arrayDadosLoop.get(i).getNovoIndice() + ";";
                }

                classePrincipal += "\n\t\t\t}";
            }
        }

        //Se ha loop, a thread eh encerrada
        if (flagLoop) {
            classePrincipal += "\n\t\t\treturn;";
        }

        classePrincipal += "\n\t\t}\n\t}";
        //Metodo main()
        classePrincipal += "\n\n\tpublic static void main(String args[]){" + "\n\t\tMain"
                + reformatClassName(nomeClasse) + " obj" + reformatClassName(nomeClasse) + " = new " + "Main"
                + reformatClassName(nomeClasse) + "();" + "\n\t\tobj" + reformatClassName(nomeClasse) + ".start();";
        classePrincipal += "\n\t}\n}";
        objEscrita.escrever(classePrincipal);
    }

    public void validaTrace(HashMap<String, ArrayList<String>> dicionarioAtributos, String nomeClasse, String nomeMetodo){
        
        String aux = "";
        aux = (nomeClasse.charAt(0) + "");
        aux = aux.toUpperCase() + nomeClasse.substring(1);
        
        ArrayList<String> metodos = null;
        if(dicionarioAtributos.size() > 0){
            if(dicionarioAtributos.containsKey(aux)){
                metodos = dicionarioAtributos.get(aux);
            }else{
                throw new ClassNotFoundException("\n\tA instância " + "\'" + nomeClasse + "$\' em @TRACE é inválida.\n");
                // System.out.println("A classe especificada no trace nao existe.\nAbortando...");
                // System.exit(0);
            }
            
            if(metodos.contains(nomeMetodo)){
            }else{
                throw new MethodNotFoundException("\n\tO método " + "\'" +nomeMetodo + "\' em @TRACE não é um método da classe " + aux +".\n");
                // System.out.println("O metodo " + nomeMetodo + " nao existe.\nAbortando");
                // System.exit(0);
            }
        }       
    }

       

    /*Imprimir dados do processo paralelo*/
    public void imprimirArrayProcessosParalelos(ComposicaoParalelaData objComposicaoParalela){
        // ComposicaoParalelaData objComposicaoParalela;
        // for(int x = 0; x < arrayComposicaoParalela.size(); x++){
            // objComposicaoParalela = arrayComposicaoParalela.get(x);
            System.out.println("\nComposicao: " + objComposicaoParalela.getNomeComposicao());
            for(int i = 0; i < objComposicaoParalela.getArrayComposicao().size(); i++){
                ArrayList<DadosParalelos> arrayDadosParalelosTmp = objComposicaoParalela.getArrayComposicao().get(i);
                for(int j = 0; j < arrayDadosParalelosTmp.size(); j++){
                    DadosParalelos dadosParalelosTmp = arrayDadosParalelosTmp.get(j);                                    
                    System.out.println("\nNome processo: "  + dadosParalelosTmp.getChave() );
                    System.out.println("Acoes: ");
                    for(int k = 0; k < dadosParalelosTmp.getValor().size(); k++ ){
                        System.out.println(dadosParalelosTmp.getValor().get(k));
                    }
                }
            }
        // }
    }

            
    public int recuperaValor(DadosRepeticaoParalela dadosRepeticao, String checkValue) {
		int value = -9999;
		// String checkValue = "";
		//Se eh um valor inteiro, entao converta
		if (validaInteiro(checkValue)) {
            value = Integer.parseInt(checkValue);
		} else {
			//Se for uma variavel/constante existente, atribua o valor da variavel/constante
			if (checkValue.equals(dadosRepeticao.getIndice())) {
                checkValue = dadosRepeticao.getLimiteInferior();
                if(validaInteiro(checkValue)){
                    value = Integer.parseInt(checkValue); 
                }
			} else if (checkValue.equals(dadosRepeticao.getProcParam().get(0).getPrim())) {
				value = Integer.parseInt(dadosRepeticao.getProcParam().get(0).getUlt());
			} else {
				System.out.println("Um ou mais valores invalidos, abortando...");
				System.exit(0);
			}
		}
		return value;
    }
    
    public boolean validaInteiro(String valor) {
		try {
			Integer.parseInt(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
    }
    

    public DadosParalelos escreverProcessoCompostoParametrizadov2(DadosRepeticaoParalela dadosRepeticaoParalela, DadosParalelos dadosParalelos){
        int N = -9999;
        int i = -9999;
        int limiteSuperiorTmp = -9999;
        try{
            /*Verifica se foi instanciado e se essa instancia eh valida, somente o procparam não serve pra validar pois ele
            eh chamado no loop comum de um processo*/
            if(dadosRepeticaoParalela != null && dadosRepeticaoParalela.getIndice() != null){
                if(dadosRepeticaoParalela.getProcParam() != null){
                    N = recuperaValor(dadosRepeticaoParalela, dadosRepeticaoParalela.getProcParam().get(0).getPrim());
                }
                
                limiteSuperiorTmp = recuperaValor(dadosRepeticaoParalela, dadosRepeticaoParalela.getLimiteSuperior());

                i = recuperaValor(dadosRepeticaoParalela, dadosRepeticaoParalela.getLimiteInferior());    

            }else{
                /*Devolve o conteudo original*/
                return dadosParalelos;
            }
        }catch(Exception e){
            System.out.println("Infomacoes invalidas...Abortando");
            System.exit(0);
        }

        //TODO ---> Um array com variaveis caso tenha mais de um proc param
        
        if(i > limiteSuperiorTmp || ( N >=0 && i > N)){
            System.out.println("Indice Fora do intervalo");
            System.exit(0);
        }
        
        int loop = limiteSuperiorTmp >= 0 ? limiteSuperiorTmp : N;

        

        if( loop != -9999 && i != -9999){
            String tmp = "";
            for(; i <= loop; i++){
                tmp =  dadosRepeticaoParalela.getObjNomeAcao()+i;
                dadosParalelos.addDadosParalelos("$" + tmp);
            } 
            return dadosParalelos;
        }else{
            /*Composicao paralela parametrizada mal especificada*/
            System.out.println("Infomacoes invalidas...Abortando");
            System.exit(0);
            /*Devolve o conteudo original*/
            return dadosParalelos;
        }  
    }

    
    public ComposicaoParalelaData escreverProcessoCompostoParametrizado(DadosRepeticaoParalela dadosRepeticaoParalela, String textoSaida, ComposicaoParalelaData composicaoParalela){
        int N = -9999;
        int i = -9999;
        try{
            /*Verifica se foi instanciado e se essa instancia eh valida, somente o procparam não serve pra validar pois ele
            eh chamado no loop comum de um processo*/
            if(dadosRepeticaoParalela != null && dadosRepeticaoParalela.getIndice() != null){
                N = recuperaValor(dadosRepeticaoParalela, dadosRepeticaoParalela.getProcParam().get(0).getPrim());
                i = recuperaValor(dadosRepeticaoParalela, dadosRepeticaoParalela.getLimiteInferior());    
                
            }else{
                /*Devolve o conteudo original*/
                return composicaoParalela;
            }
        }catch(Exception e){
            System.out.println("Infomacoes invalidas...Abortando");
            System.exit(0);
        }

        if( N != -9999 && i != -9999){
            String tmp = "";
            for(; i <= N; i++){
                tmp =  dadosRepeticaoParalela.getObjNomeAcao()+i;
                composicaoParalela = addMetodoArrayProcessos(composicaoParalela, dadosRepeticaoParalela, tmp);
                // System.out.println("TMP: " + tmp);
                // imprimirArrayProcessosParalelos(composicaoParalela);   
            } 
            return composicaoParalela;
        }else{
            /*Composicao paralela parametrizada mal especificada*/
            System.out.println("Infomacoes invalidas...Abortando");
            System.exit(0);
            /*Devolve o conteudo original*/
            return composicaoParalela;
        }
    }

    public ComposicaoParalelaData addMetodoArrayProcessos(ComposicaoParalelaData composicaoParalela, DadosRepeticaoParalela dadosRepeticaoParalela, String nomeAcao){
        
        //TODO---> Evitar escrever uma ação pro processo quando há forall pois as ações serão obtidas por ele 
        
        if(composicaoParalela.getNomeComposicao().equalsIgnoreCase(dadosRepeticaoParalela.getNomeProcesso())){            
            for(int i = 0; i < composicaoParalela.getArrayComposicao().size(); i++){
                ArrayList<DadosParalelos> arrayDadosParalelos = composicaoParalela.getArrayComposicao().get(i);
               for(int j = 0 ; j < arrayDadosParalelos.size(); j++){                    
                   if(arrayDadosParalelos.get(j).getChave().equalsIgnoreCase(dadosRepeticaoParalela.getNomeProcessoRepeticao())){
                        arrayDadosParalelos.get(j).addDadosParalelos(nomeAcao);
                        return composicaoParalela;
                   }
               }
            }
        }
        return composicaoParalela;
    }

    //mudar para busca binária
    public int buscaProcesso(ArrayList<Nupla> arrayNupla, String processo){
        for(int i = 0; i < arrayNupla.size(); i++){
            if(arrayNupla.get(i).getChave().equalsIgnoreCase(processo)){
                return i;
            }
        }
        return -1;
    }

    public int buscaOutroProcesso(ArrayList<Nupla> arrayNupla, String chave, String obj, int indice){
        for(int i = indice+1; i < arrayNupla.size(); i++){
            if(arrayNupla.get(i).getChave().equalsIgnoreCase(obj)){
                return i;
            }
        }
        for(int i = 0; i < arrayNupla.size(); i++){
            if(arrayNupla.get(i).getChave().equalsIgnoreCase(obj)){
                return i;
            }
        }
        return -1;
    }

    

    /*Verifica se o processo paralelo já existe no array, se sim acrescenta-se apenas o rótulo se houver*/    
    public ArrayList<DadosParalelos> haProcessoParalelo(ArrayList<DadosParalelos> arrayDadosParalelos, DadosParalelos objDadosParalelos, String nomeProcessoParalelo){
        boolean buscaProcessoParalelo = false;
        int indice = -1;
        for(int i = 0; i < arrayDadosParalelos.size(); i++){
            if(arrayDadosParalelos.get(i).getChave().equals(nomeProcessoParalelo)){
                ArrayList<String> valor = objDadosParalelos.getValor();
                for(int j = 0; j < valor.size(); j++){
                    indice = arrayDadosParalelos.get(i).getValor().indexOf(valor.get(j));
                    if(indice == -1){
                        arrayDadosParalelos.get(i).getValor().add(valor.get(j));
                    }else{
                        //TODO ---> Fazer tratamentos como este nos demais trechos
                        System.out.println("\nNotacao invalida, ha acoes duplicadas.\nAbortando...");
                        System.exit(0);
                        // arrayDadosParalelos.get(i).getValor().add(valor.get(j) + alfabetoParalelo);
                        // alfabetoParalelo++;
                    }
                }

                // arrayDadosParalelos.get(i).getValor().addAll(objDadosParalelos.getValor());
                buscaProcessoParalelo = true;
                break;
            }
        }

        /*Se não existir um processo com o nome em questão, add um novo elemento*/
        if(buscaProcessoParalelo == false){
            arrayDadosParalelos.add(objDadosParalelos);
        }

        return arrayDadosParalelos;
    }

    //Para recursividade
    public void teste(ArrayList<ArrayLista> arrayLista, int index, String nomeClasse, ArrayList<String> auxTrace, ArrayList<Escolhas> escolhas){
        int escolhaToInt = 0;
        System.out.println("\nQual OU voce quer?");
        LeituraTeclado sc = new LeituraTeclado();

        //Exibindo na tela as opcoes
        for (int i = 0; i < arrayLista.size(); i++) {
            System.out.print(i + " - ");
            for (int j = 0; j < arrayLista.get(i).getValor().size(); j++) {
                System.out.print(" " + arrayLista.get(i).getValor().get(j));
            }
            System.out.println(" -> " + arrayLista.get(i).getChave());
        }
        System.out.println("\nDigite -1 para sair\n");
        //Lendo a escolha do usuario
        String escolhaParse = sc.getLinha();

        //Covertendo para inteiro
        try {
            escolhaToInt = Integer.parseInt(escolhaParse);
        } catch (NumberFormatException e) {
            System.out.println("Valor invalido, setando indice para 0");
        }
        if (escolhaToInt < -1 || escolhaToInt >= arrayLista.size()) {
            System.out.println("Valor invalido, setando indice para 0");
            escolhaToInt = 0;
        }

        if(escolhaToInt == -1){
            return;
        }

        System.out.println();

        
        for(int k = 0; k < arrayLista.get(escolhaToInt).getValor().size(); k++ ){
            String tmp =  nomeClasse.toLowerCase()  + "$." + arrayLista.get(escolhaToInt).getValor().get(k);  
            auxTrace.add(tmp);
        }
                   

        // auxTrace.addAll(arrayLista.get(escolhaToInt).getValor());
        System.out.println("TRACE ATUAL: " + auxTrace);

        if(arrayLista.get(escolhaToInt).getChave().equalsIgnoreCase(nomeClasse)){
            teste(arrayLista, 0, nomeClasse, auxTrace, escolhas);
        }else{
            for(int i = 0; i < escolhas.size(); i++){
                if(escolhas.get(i).chave.equalsIgnoreCase(arrayLista.get(escolhaToInt).getChave())){
                    teste(escolhas.get(i).arrayLista, 0, escolhas.get(i).chave, auxTrace, escolhas);
                }
            }

        }      
    }

    //Para loop iterativo
    public void teste2(ArrayList<ArrayList<Nupla>> arrayProcessos, int index, DadosRepeticao dadosRepeticao, ArrayList<String> auxTrace){
        
        ArrayList<Nupla> arrayNupla = arrayProcessos.get(0);
        if(arrayNupla.size() > 0 && index < arrayNupla.size() ){
            if(arrayNupla.get(index).getValor().contains("_init_")){
                if(index+1 < arrayNupla.size()){
                    teste2(arrayProcessos, index+1, dadosRepeticao, auxTrace);
                }
                
            }else{
                
                System.out.println("===========================================================");
                System.out.println("\nValor atual de " + dadosRepeticao.getIndice()+ ": " + dadosRepeticao.getValorIndice());
                System.out.println("Valor atual de " + dadosRepeticao.getProcParam().get(0).getPrim()+ ": " + dadosRepeticao.getProcParam().get(0).getUlt()+"\n");

                System.out.println(
                        "\nCAMINHO DISPONIVEL em " + arrayNupla.get(index).getChave() + ", ESCOLHA UMA OPCAO:\n");
                        
                
                //Se o processo eh o mesmo
                ArrayList<Integer> opcoes = new ArrayList<Integer>();
                opcoes.add(-1);
                if (arrayNupla.get(index).getChave().equalsIgnoreCase(dadosRepeticao.getNomeProcesso())) {
                    //busca uma condicao valida
                    for (int x = 0; x < dadosRepeticao.getCondicaoOperacao().size(); x++) {                        
                        //Se a condicao eh valida
                        boolean saida = verificaCondicao2(dadosRepeticao,
                                dadosRepeticao.getCondicaoOperacao().get(x));
                        if (saida == true) {
                            opcoes.add(x);
                            System.out.println(x + " - " + dadosRepeticao.getCondicaoOperacao().get(x).getNomeAcao() + "->" 
                        + dadosRepeticao.getCondicaoOperacao().get(x).getProxProcesso() + "[" + dadosRepeticao.getCondicaoOperacao().get(x).getOperacao().getOperandoE() 
                        + dadosRepeticao.getCondicaoOperacao().get(x).getOperacao().getComparador()
                        + dadosRepeticao.getCondicaoOperacao().get(x).getOperacao().getOperandoD() + "]") ;                                
                        }                        
                    }
                }

                int escolhaToInt = 0;
                // System.out.println("\nQual OU voce quer?");
                LeituraTeclado sc = new LeituraTeclado(); 


                String nomeAcao = dadosRepeticao.getCondicaoOperacao().get(0).getNomeAcao();
                if(arrayNupla.get(index).getChave().equalsIgnoreCase(dadosRepeticao.getNomeProcesso())){
                    // System.out.println("\nValor atual de " + dadosRepeticao.getIndice()+ ": " + dadosRepeticao.getValorIndice());
                    // System.out.println("Valor atual de " + dadosRepeticao.getProcParam().get(0).getPrim()+ ": " + dadosRepeticao.getProcParam().get(0).getUlt()+"\n");

                    // for(int i = 0; i < dadosRepeticao.getCondicaoOperacao().size(); i++){                      
                    //     System.out.println(i + " - " + dadosRepeticao.getCondicaoOperacao().get(i).getNomeAcao() + "->" 
                    //     + dadosRepeticao.getCondicaoOperacao().get(i).getProxProcesso() + "[" + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getOperandoE() 
                    //     + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getComparador()
                    //     + dadosRepeticao.getCondicaoOperacao().get(i).getOperacao().getOperandoD() + "]") ;
                    // }

                    System.out.println("\nDigite -1 para sair\n");
                    
                    do{
                        //Lendo a escolha do usuario
                    String escolhaParse = sc.getLinha();
                        //Covertendo para inteiro
                        try {
                            escolhaToInt = Integer.parseInt(escolhaParse);
                        } catch (NumberFormatException e) {
                            System.out.println("Valor invalido, setando indice para 0");
                        }

                        if(!opcoes.contains(escolhaToInt)){
                            System.out.println("Valor invalido, escolha uma acima");
                        }
                        
                    }while(escolhaToInt < -1 || escolhaToInt >= dadosRepeticao.getCondicaoOperacao().size() || !opcoes.contains(escolhaToInt));
                                

                    if(escolhaToInt == -1){
                        System.out.println("TRACE ATUAL: " + auxTrace);

                        return;
                    }

                    System.out.println();

                    //TODO --->Colocar num array as acoes
                    // for(int k = 0; k < dadosRepeticao.getCondicaoOperacao().get(escolhaToInt).getNomeAcao(); k++ ){
                        
                        String tmp =  dadosRepeticao.getNomeProcesso().toLowerCase()  + "$." + dadosRepeticao.getCondicaoOperacao().get(escolhaToInt).getNomeAcao();  
                        auxTrace.add(tmp);
                    // }
                               
            
                    // auxTrace.addAll(arrayLista.get(escolhaToInt).getValor());
                    System.out.println("TRACE ATUAL: " + auxTrace);

                   
								int saidaVerifica = verificaCondicao(dadosRepeticao,
										dadosRepeticao.getCondicaoOperacao().get(escolhaToInt));
								// System.out.println("\nValor atual de " + dadosRepeticao.getIndice()+ ": " + dadosRepeticao.getValorIndice());

								if (saidaVerifica != -999) {
									// System.out.println("Aplicando operacao...");
									dadosRepeticao.setValorIndice(saidaVerifica);
									// System.out.println(
									// 		"Valor de " +dadosRepeticao.getIndice() + ": " + dadosRepeticao.getValorIndice() + "\n");
								} else {
									System.out.println("Nada a se fazer...");
								}
							
					// }
				// }


                    if(arrayNupla.get(index).getChave().equalsIgnoreCase(dadosRepeticao.getCondicaoOperacao().get(escolhaToInt).getProxProcesso())){
                        teste2(arrayProcessos, index, dadosRepeticao, auxTrace);
                    }//TODO--> fazer o else
                }
            }                        
        }        
    }

    //Metodo para vericar qual escolha esta habilitada
	public boolean verificaCondicao2(DadosRepeticao dadosRepeticao, CondicaoOperacao condicaoOperacao) {
		int val1 = recuperaValor(dadosRepeticao, condicaoOperacao, true);
		int val2 = recuperaValor(dadosRepeticao, condicaoOperacao, false);

		if (condicaoOperacao.getCondicao().getComparador().equals("==")) {
			if (val1 == val2) {
				// System.out.println(">>>>>>>Entrou no igual!!");
				return true;
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals("<")) {
			if (val1 < val2) {
				// System.out.println(">>>>>>>Entrou no menor!!");
				return true;
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals(">")) {
			if (val1 > val2) {
				// System.out.println(">>>>>>>Entrou no maior!!");
				return true;
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals("<=")) {
			if (val1 <= val2) {
				return true;
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals(">=")) {
			if (val1 >= val2) {
				return true;
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals("!=")) {
			if (val1 != val2) {
				return true;
			}
		}
		return false;
	}

    public int recuperaValor(DadosRepeticao dadosRepeticao, CondicaoOperacao condicaoOperacao, boolean lado) {
		int value = 0;
		String checkValue = "";
		if (lado == true) {
			checkValue = condicaoOperacao.getCondicao().getOperandoE();
		} else {
			checkValue = condicaoOperacao.getCondicao().getOperandoD();
		}
		//Se e hum valor inteiro, entao converta
		if (validaInteiro(checkValue)) {
			value = Integer.parseInt(checkValue);
		} else {
			//Se for uma variavel/constante existente, atribua o valor da variavel/constante
			if (checkValue.equals(dadosRepeticao.getIndice())) {
				value = dadosRepeticao.getValorIndice();
			} else if (checkValue.equals(dadosRepeticao.getProcParam().get(0).getPrim())) {
				value = Integer.parseInt(dadosRepeticao.getProcParam().get(0).getUlt());
			} else {
				System.out.println("Um ou mais valores invalidos, abortando...");
				System.exit(0);
			}
		}
		return value;
	}

    public int verificaCondicao(DadosRepeticao dadosRepeticao, CondicaoOperacao condicaoOperacao) {
		int val1 = recuperaValor(dadosRepeticao, condicaoOperacao, true);
		int val2 = recuperaValor(dadosRepeticao, condicaoOperacao, false);

		if (condicaoOperacao.getCondicao().getComparador().equals("==")) {
			if (val1 == val2) {
				System.out.println("IGUAL");
				return verificaOperacao(val1, val2, condicaoOperacao, dadosRepeticao);
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals("<")) {
			if (val1 < val2) {
				System.out.println("MENOR");
				return verificaOperacao(val1, val2, condicaoOperacao, dadosRepeticao);
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals(">")) {
			if (val1 > val2) {
				System.out.println("MAIOR");
				return verificaOperacao(val1, val2, condicaoOperacao, dadosRepeticao);
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals("<=")) {
			if (val1 <= val2) {
				System.out.println("MENOR IGUAL");
				return verificaOperacao(val1, val2, condicaoOperacao, dadosRepeticao);
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals(">=")) {
			if (val1 >= val2) {
				System.out.println("MAIOR IGUAL");
				return verificaOperacao(val1, val2, condicaoOperacao, dadosRepeticao);
			}
		} else if (condicaoOperacao.getCondicao().getComparador().equals("!=")) {
			if (val1 != val2) {
				System.out.println("DIFERENTE");
				System.out.println(condicaoOperacao.getCondicao().getComparador());
				return verificaOperacao(val1, val2, condicaoOperacao, dadosRepeticao);
			}
		}
		return -999;
	}

	public int verificaOperacao(int val1, int val2, CondicaoOperacao condicaoOperacao, DadosRepeticao dadosRepeticao) {
		switch (condicaoOperacao.getOperacao().getComparador()) {
		case "+":
			return dadosRepeticao.getValorIndice() + Integer.parseInt(condicaoOperacao.getOperacao().getOperandoD());
		case "-":
			return dadosRepeticao.getValorIndice() - Integer.parseInt(condicaoOperacao.getOperacao().getOperandoD());
		case "*":
			return dadosRepeticao.getValorIndice() * Integer.parseInt(condicaoOperacao.getOperacao().getOperandoD());
		case "/":
			// Verificar problema na precedencia da divisao
			return dadosRepeticao.getValorIndice() / Integer.parseInt(condicaoOperacao.getOperacao().getOperandoD());
		default:
			return -999;
		}
	}
}
