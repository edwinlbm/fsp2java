

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.io.File;


// import java.text.DateFormat;
// import java.text.SimpleDateFormat;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Calendar;
// import java.util.Date;

// //Inicio do compilador


// public class Parser {
// 	public static final int _EOF = 0;
// 	public static final int _numero = 1;
// 	public static final int _nzero = 2;
// 	public static final int _nomeVar = 3;
// 	public static final int _nomeMin = 4;
// 	public static final int maxT = 31;

// 	static final boolean T = true;
// 	static final boolean x = false;
// 	static final int minErrDist = 2;

// 	public Token t;    // last recognized token
// 	public Token la;   // lookahead token
// 	int errDist = minErrDist;
	
// 	public Scanner scanner;
// 	public Errors errors;

// 	ArrayList<String> arrayConstantes   = new ArrayList<String>();
// //Array para armazenar os intervalos
// ArrayList<String> arrayRange        = new ArrayList<String>();
// //Array para salvar os metodos de um processo na regra Transicao
// ArrayList<String> nomeMetodos       = new ArrayList<String>();
// //Array para armazenar a lista de metodos que formam o alfabeto de um processo
// ArrayList<String> arrayAlfabeto     = new ArrayList<String>();
// //Array para armazenar os metodos de um processo na regra Escolha
// ArrayList<String> arrayEscolha;
// //Array para armazenar as acoes/metodos da regra Escolha quando ha "when" em um loop
// ArrayList<String> arrayMetodoLoop   = null;
// //Array para armazenar os parametros de um processo
// ArrayList<Pares> procParam          = null;
// //Array para armazenar os metodos e o proximo processo (objeto a ser instanciado em Java)
// ArrayList<ArrayLista> arrayLista    = new ArrayList<ArrayLista>();
// //Array para armazenar o nome do processo, os seus metodos e o proximo processo
// ArrayList<Nupla> nomeClassesMetodos = new ArrayList<Nupla>();
// //Array principal para armazenar o nome do processo, os seus metodos e o proximo processo
// ArrayList<Nupla> arrayNupla         = new ArrayList<Nupla>();
// //Array para armazenar cada item ( if(x> y) ) do loop na forma i:0..N
// ArrayList<DadosLoop> arrayDadosLoop = new ArrayList<DadosLoop>();
// //Variavel para armazenar o nome da classe temporariamente
// String nomeClasse                   = "";
// //Variavel para armazenar a acao/metodo escolhida de um alfabeto
// String stringNomeAcao               = "";
// //Variavel para armazenar um numero | nomeVar | nzero da regra IndiceTipoI
// String processoIndexadoProducao     = "";
// //Variavel para armazenar dados da regra Condicao que estaram no metodo run() do programa principal
// String stringRun                    = "";                                                
// //Indice global para verificar em tempo de execuÃ§Ã£o se dado elemento do Trace eh satisfeito
// int indexTrace                      = 0;
// //Indice global da regra Subprocesso para verificar em tempo de execuÃ§Ã£o se dado elemento do Trace eh satisfeito
// int indexTraceSub                   = 0;
// //TODO ---> Variavel temporaria enquanto a repeticao de metodos com o mesmo nome nao eh solucionada
// int metodoRepetido                  = 1;
// //flag para validar a continucao do Trace caso o caminho atual seja valido na regra Transicao
// boolean flagTraceContinue           = false;
// //flag para validar a continucao do Trace caso o caminho atual seja valido na regra Escolha/RepeteAcaoEscolha
// boolean flagTraceEscolha            = false;
// //Evita que em um OU grave-se apenas um metodo do tipo (abc -> def -> PROCESSO) quando houver mais de uma opcao na regra Escolha
// boolean flagAcaoGuarda              = false;
// //Previne que em um loop o metodo de um objeto chamado do while seja instanciado e chamado por essa nova instancia
// //Desta forma a pilha nao estoura
// boolean ultimoMetodo                = false;
// //Informa se ha um loop ou Stop, utilizado no programa principal
// boolean flagLoop                    = false;
// //Objeto para acessar o array com a sequencia do Trace
// LeituraTrace objTrace               = null;
// //Objeto para manipulacao de dados no disco
// EscritaArquivo arquivo              = null;
// //Objeto que reune os metodos utilizados para manipulacao de dados
// MetodosUtilitarios objUtilitarios   = new MetodosUtilitarios();
// //Objeto para manipulacao de itens que serao utilizados pelo programa principal
// DadosLoop dadosLoop                 = new DadosLoop();

// EscritaLog escritaLog = new EscritaLog("log", ".txt");

// //Metodo para remover o uppercase dos nomes dos processos
// //Considerando que a entrada Ã© upperCase
// public String reformatClassName(String nomeClasse){
//     return nomeClasse.charAt(0) + nomeClasse.substring(1).toLowerCase();
// }



// 	public Parser(Scanner scanner) {
// 		this.scanner = scanner;
// 		errors = new Errors();
// 	}

// 	void SynErr (int n) {
// 		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
// 		errDist = 0;
// 	}

// 	public void SemErr (String msg) {
// 		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
// 		errDist = 0;
// 	}
	
// 	void Get () {
// 		for (;;) {
// 			t = la;
// 			la = scanner.Scan();
// 			if (la.kind <= maxT) {
// 				++errDist;
// 				break;
// 			}

// 			la = t;
// 		}
// 	}
	
// 	void Expect (int n) {
// 		if (la.kind==n) Get(); else { SynErr(n); }
// 	}
	
// 	boolean StartOf (int s) {
// 		return set[s][la.kind];
// 	}
	
// 	void ExpectWeak (int n, int follow) {
// 		if (la.kind == n) Get();
// 		else {
// 			SynErr(n);
// 			while (!StartOf(follow)) Get();
// 		}
// 	}
	
// 	boolean WeakSeparator (int n, int syFol, int repFol) {
// 		int kind = la.kind;
// 		if (kind == n) { Get(); return true; }
// 		else if (StartOf(repFol)) return false;
// 		else {
// 			SynErr(n);
// 			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
// 				Get();
// 				kind = la.kind;
// 			}
// 			return StartOf(syFol);
// 		}
// 	}
	
// 	void Fsp3() {
// 		Definicoes();
// 		Processo();
// 		Expect(5);
// 	}

// 	void Definicoes() {
// 		if (la.kind == 6) {
// 			Const();
// 			Definicoes();
// 		} else if (la.kind == 8) {
// 			Range();
// 			Definicoes();
// 		} else if (la.kind == 3) {
// 		} else SynErr(32);
// 	}

// 	void Processo() {
// 		nomeClasse = ""; String stringProcToObj = ""; 
// 		            objTrace = new LeituraTrace(System.getProperty("user.dir")+"/entrada.txt");
// 		try{
// 		   System.out.println("\n========================");
// 		   objTrace.lerTrace();
// 		   if(objTrace.getValor().isEmpty()){
// 		       System.out.println("TRACE VAZIO");
// 		       System.out.println("ABORTANDO EXECUCAO...");
// 		       System.exit(0);
// 		   }else{
// 		       System.out.println("TRACE OBTIDO DO ARQUIVO:\n" + objTrace.getValor() + "\n");
// 		   }
// 		}catch(Exception e){
// 		   e.printStackTrace();
// 		}        
		
// 		nomeClasse = Regra();
// 		Expect(7);
// 		if (StartOf(1)) {
// 			if (la.kind == 20) {
// 				Get();
// 			}
// 			stringProcToObj = Transicao();
// 			if (la.kind == 21) {
// 				Get();
// 			}
// 		} else if (la.kind == 3) {
// 			Get();
// 			if((t.val).equals("STOP")){
// 			   if(objTrace.getValor().get(0).equals(t.val)){
// 			       System.out.println("\n--------------");
// 			           System.out.println("|TRACE ACEITO|");
// 			           System.out.println("--------------");
// 			   }
// 			   //Se t.val == STOP, cria-se um metodo/acao fake para fins de nao quebrar o codigo
// 			   //"insere um metodo fake para nao ter um array vazio e gerar exception"
// 			   nomeMetodos.add("_stop_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista); 
// 			}else{
// 			   //TODO ---> tratar  o caso do trace qnd tive _init_
// 			   //Se nao for STOP, criar-se um metodo/acao fake para dar arranque
// 			   //"insere um metodo fake para nao ter um array vazio e gerar exception"
// 			   nomeMetodos.add("_init_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
// 			} 
			
// 			Indice();
// 		} else SynErr(33);
// 		int escolhaToInt = 0;
// 		if(flagAcaoGuarda == false && indexTrace < objTrace.getValor().size()){                            
// 		   //Leitura do teclado se  arrayLista > 1, ou seja, hÃ¡ OUs.
// 		   //Retorna 0 se tamanho == 1 e 1..Size se tamanho > 1   
// 		   escolhaToInt = objUtilitarios.inputEscolha(arrayLista); //TODO ---> Melhorar no caso do "when"
// 		   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(escolhaToInt).getChave()), arrayLista.get(escolhaToInt).getValor(), nomeClassesMetodos);
		                                   
// 		}else{
// 		   //REVER:
// 		   //Se ha uma acao de guarda, os metodos quando ocorre when precisam ser gravados sem encadeamento
// 		   arrayMetodoLoop = new ArrayList<String>();
// 		   //TODO ---> melhorar, pois os metodos ficaram em dois arrays (arrayMetodoLoop e nomeClassesMetodos) e somente na escrita que se remove de um
// 		   //e faz-se a distincao se o metodo pertence ao loop. Eh caro, melhorar o custo.
		   
// 		   // System.out.println("\nVERIFY>>>" );
// 		   for(int i = 0; i < arrayLista.size(); i++){
// 		       for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
// 		           // System.out.println("\nVERIFY: " + arrayLista.get(i).getValor().get(j) );
// 		           arrayMetodoLoop.add(arrayLista.get(i).getValor().get(j));
// 		       }
// 		   }
		                       
// 		   //TODO ---> Melhorar a forma como se obtem objetos/Processos nessa forma especifica de "when"
// 		   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(0).getChave()), arrayLista.get(0).getValor(), nomeClassesMetodos);                                
// 		}                            
// 		//Array com o nome da classe, seus metodos e obj - Contem tudo do processo atual! 
// 		//addClasseMetodos(IN: String nomeClasse, IN:  String nomeObj, IN: ArrayList<String> arrayMetodos, OUT: ArrayList<Nupla> nomeMetodoClasse)
// 		//Limpando o array de metodos
// 		nomeMetodos.clear();
// 		                objUtilitarios.addClasseMetodosObjts(reformatClassName(nomeClasse), arrayLista, arrayNupla); 
// 		//Limpando o array para a chamada do Escolha pela Transicao dentro do Subprocesso
// 		arrayLista.clear(); 
// 		 //Se falhar ba transicao e na escolha(se houver) nao precisa buscar e no Subprocesso.
// 		if(flagTraceContinue == false && flagTraceEscolha == false){
// 		   System.out.println("NAO HA CAMINHO VALIDO");
// 		   System.out.println("TRACE REJEITADO");
// 		   System.out.println("ABORTANDO EXECUCAO...");
// 		   System.exit(0);
// 		}
		            
// 		nomeClasse = Subprocesso();
// 		if(flagTraceContinue == true){
// 		   if(indexTrace < objTrace.getValor().size()){
// 		       System.out.println("NAO HA CAMINHO VALIDO");
// 		       System.out.println("TRACE REJEITADO");
// 		       System.out.println("ABORTANDO EXECUCAO...");
// 		       System.exit(0);
// 		   }
// 		}
// 		if(flagTraceContinue == true && indexTrace == objTrace.getValor().size()){
// 		   System.out.println("\n--------------");
// 		   System.out.println("|TRACE ACEITO|");
// 		   System.out.println("--------------");
// 		}
		
// 		Arrays.stream(new File(System.getProperty("user.dir") + "/Saida/").listFiles()).forEach(File::delete);
		
// 		for(int i = 0; i < arrayNupla.size(); i++){
// 		   if(i == arrayNupla.size() - 1){
// 		       //Para que o ultimo metodo nao crie uma instancia do metodo chamador que desencadeou a sequencia de acoes
// 		       //em caso de loop a repeticao se da pelo while(true)
// 		       ultimoMetodo = true;
// 		   }
// 		    //Possivel problema em passar o nome da classe variando
		   
// 		   if(!arrayNupla.get(i).getValor().isEmpty()){
// 		       objUtilitarios.escreverNoDiscoTrace(reformatClassName(arrayNupla.get(i).getChave()), reformatClassName(arrayNupla.get(i).getObj()), arrayNupla.get(i).getValor(), arrayNupla, ultimoMetodo, flagLoop, i);
// 		       // objUtilitarios.escreverNoDiscoPython(reformatClassName(arrayNupla.get(i).getChave()), reformatClassName(arrayNupla.get(i).getObj()), arrayNupla.get(i).getValor(), arrayNupla, ultimoMetodo, flagLoop, i);
// 		   }
// 		}  
// 		//Escrita da classe principal
// 		objUtilitarios.escreverClassePrincipalTrace(nomeClassesMetodos.get(0).getChave(), nomeClassesMetodos.get(0).getValor(), flagLoop, arrayDadosLoop, procParam, arrayMetodoLoop, arrayNupla);
// 		// objUtilitarios.escreverClassePrincipalPython(nomeClassesMetodos.get(0).getChave(), nomeClassesMetodos.get(0).getValor(), flagLoop, arrayDadosLoop, procParam, arrayMetodoLoop, arrayNupla);            
		
// 	}

// 	void Const() {
// 		Expect(6);
// 		Expect(3);
// 		Expect(7);
// 		Expect(1);
// 	}

// 	void Range() {
// 		Expect(8);
// 		Expect(3);
// 		Expect(7);
// 		Intervalo();
// 	}

// 	void Intervalo() {
// 		Expect(2);
// 		Expect(9);
// 		if (la.kind == 3) {
// 			Get();
// 		} else if (la.kind == 4) {
// 			Get();
// 		} else if (la.kind == 1) {
// 			Get();
// 		} else SynErr(34);
// 		if (StartOf(2)) {
// 			Operacao();
// 			if (la.kind == 3) {
// 				Get();
// 			} else if (la.kind == 1) {
// 				Get();
// 			} else if (la.kind == 4) {
// 				Get();
// 			} else SynErr(35);
// 		} else if (StartOf(3)) {
// 		} else SynErr(36);
// 	}

// 	void Operacao() {
// 		if (la.kind == 10) {
// 			Get();
// 		} else if (la.kind == 11) {
// 			Get();
// 		} else if (la.kind == 12) {
// 			Get();
// 		} else if (la.kind == 13) {
// 			Get();
// 		} else SynErr(37);
// 	}

// 	void IgualdadeDesig() {
// 		switch (la.kind) {
// 		case 14: {
// 			Get();
// 			break;
// 		}
// 		case 15: {
// 			Get();
// 			break;
// 		}
// 		case 16: {
// 			Get();
// 			break;
// 		}
// 		case 17: {
// 			Get();
// 			break;
// 		}
// 		case 18: {
// 			Get();
// 			break;
// 		}
// 		case 19: {
// 			Get();
// 			break;
// 		}
// 		default: SynErr(38); break;
// 		}
// 	}

// 	String  Regra() {
// 		String  nomeClasse;
// 		Expect(3);
// 		nomeClasse = t.val; 
// 		if (la.kind == 20) {
// 			ProcParametro();
// 		} else if (la.kind == 7 || la.kind == 23) {
// 			Indice();
// 		} else SynErr(39);
// 		return nomeClasse;
// 	}

// 	String  Transicao() {
// 		String  stringProcToObj;
// 		stringProcToObj = ""; if(indexTraceSub > indexTrace){indexTrace = indexTraceSub;};
// 		if(objTrace.getValor().isEmpty()){System.out.println("TRACE INVALIDO");}
		
// 		AcaoDeGuarda();
// 		if (la.kind == 4) {
// 			Get();
// 			if(flagTraceContinue == false){
// 			   if( (indexTrace < objTrace.getValor().size()) &&  objTrace.getValor().get(indexTrace).equals(t.val)){
// 			       indexTrace++;
// 			       flagTraceContinue = true;
// 			       nomeMetodos.add(t.val);
// 			       System.out.println("VALOR ATINGIDO: " + t.val);
// 			   }else{
// 			       nomeMetodos.clear();
// 			       System.out.println("CAMINHO INVALIDO");
// 			       System.out.println("\t  VALOR OBTIDO: null");
// 			       System.out.println("\tVALOR ESPERADO: " + t.val + "\n");
// 			       flagTraceContinue = false;
// 			   }                                        
// 			}
			                                       
			
// 			Indice();
// 		} else if (la.kind == 29) {
// 			AlfabetoExt();
// 			if(flagTraceContinue == false && indexTrace < objTrace.getValor().size()){
// 			   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 			   if(!saidaCheckTrace.equals("")){
// 			       System.out.println("VALOR ATINGIDO: " + saidaCheckTrace);                                            
// 			       indexTrace++;
// 			       flagTraceContinue = true;
// 			       nomeMetodos.add(saidaCheckTrace);
// 			       arrayAlfabeto.clear();
// 			   }else{
// 			       flagTraceContinue = false; 
// 			       nomeMetodos.clear();
// 			       // System.out.println("CAMINHO INVALIDO"); --> A funcao printa o invalido
// 			       System.out.println("\t  VALOR OBTIDO: " + objTrace.getValor().get(indexTrace)); 
// 			       System.out.println("\tVALOR ESPERADO: " + arrayAlfabeto + "\n");
// 			       arrayAlfabeto.clear();
// 			   }
// 			}else{
// 			   System.out.println("CAMINHO INVALIDO");
// 			   System.out.println("\t  VALOR OBTIDO: null");
// 			   System.out.println("\tVALOR ESPERADO: " + arrayAlfabeto + "\n");
// 			   arrayAlfabeto.clear();
// 			}
			
// 		} else SynErr(40);
// 		Expect(22);
// 		RepeteAcao();
// 		if (StartOf(4)) {
// 			FatoraProcesso();
// 		} else if (la.kind == 3) {
// 			Get();
// 			if(flagTraceContinue == true){
// 			   stringProcToObj = t.val;
// 			   //Apos todos os metodos dessa regra serem obtidos, grave-se no arrayLista junto com
// 			   //o nome do proximo processo.
// 			   objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
// 			}
			                            
// 			Indice();
// 			dadosLoop.setCondicaoVal(stringRun);
// 			                                //Para armazenar os dados do obj DadosLoop Ã© preciso instanciar um novo objeto uma vez que o obj antigo sera limpo
// 			//Copia
// 			//Contem condicao, indice, process parameter, novo indice ...
// 			DadosLoop dadosAux = new DadosLoop();
// 			dadosAux.setOrdemIntervalo(dadosLoop.getOrdemIntervalo());
// 			dadosAux.setIndiceVal(dadosLoop.getIndiceVal());
// 			dadosAux.setCondicaoVal(new String(dadosLoop.getCondicaoVal()));
// 			dadosAux.setNovoIndice(new String(dadosLoop.getNovoIndice()));
// 			//acrescenta-se ao array com dados de cada subprocesso
// 			arrayDadosLoop.add(dadosAux);
// 			                                //Limpando a string
// 			stringRun = "";
// 			//Limpando o conteudo do obj
// 			dadosLoop.clear();
// 			//TODO ---> Replicar para a repetiÃ§Ã£o do escolha
			
// 		} else SynErr(41);
// 		if(flagTraceContinue == true && indexTrace < objTrace.getValor().size()){
// 		   // System.out.println("\n--------------");
// 		   // System.out.println("|TRACE ACEITO??|");
// 		   // System.out.println("--------------");
// 		   System.out.println("\nBUSCANDO OUTRO CAMINHO...\n");
// 		}
// 		// }else{
// 		//     //Tenta na regra Escolha se nao gerar Lambda ou continua a verificacao no Subprocesso
// 		// }
// 		if(flagTraceContinue == true){
// 		   if(indexTrace < objTrace.getValor().size()){                                    
// 		       System.out.println("CONTINUANDO O TRACE EM UM SUBPROCESSO, INDICE ATUAL:  " + indexTrace + "\n");
// 		       indexTraceSub = indexTrace;
// 		   }
// 		}
		
// 		Escolha();
// 		return stringProcToObj;
// 	}

// 	void Indice() {
// 		if (la.kind == 23) {
// 			Get();
// 			if (la.kind == 1 || la.kind == 2 || la.kind == 3) {
// 				IndiceTipoI();
// 			} else if (la.kind == 4) {
// 				IndiceTipoII();
// 			} else SynErr(42);
// 			Expect(24);
// 			Indice();
// 		} else if (StartOf(5)) {
// 		} else SynErr(43);
// 	}

// 	String  Subprocesso() {
// 		String  nomeClasse;
// 		nomeClasse = ""; String stringProcToObj = ""; nomeMetodos.clear();  
// 		if (la.kind == 26) {
// 			Get();
// 			nomeClasse = RegraTipoII();
// 			Expect(7);
// 			if (StartOf(1)) {
// 				nomeMetodos.clear(); flagTraceContinue = false; flagTraceEscolha = false; indexTraceSub = indexTrace;
// 				// System.out.println(indexTrace + " " + indexTraceSub);
				
// 				if (la.kind == 20) {
// 					Get();
// 				}
// 				stringProcToObj = Transicao();
// 				if (la.kind == 21) {
// 					Get();
// 				}
// 			} else if (la.kind == 3) {
// 				Get();
// 				if((t.val).equals("STOP")){
// 				   // "insere um metodo fake para nao ter um array vazio e gerar exception"
// 				   nomeMetodos.add("_stop_"); 
// 				   objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista); 
// 				}else{
// 				   // "insere um metodo fake para nao ter um array vazio e gerar exception"
// 				   nomeMetodos.add("_init_"); 
// 				   objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
// 				} 
				
// 				Indice();
// 			} else SynErr(44);
// 			int escolhaToInt = 0;
// 			//TODO ---> colocar no loop do Subprocesso
// 			 if(flagAcaoGuarda == false && indexTrace < objTrace.getValor().size()){
// 			   //Leitura do teclado se  arrayLista > 1, implica na ocorrencia de OUs.
// 			       //Retorna 0 se tamanho == 1 (so ha um elemento) ou algo no intervalo 1..Tam se tamanho > 1 
// 			        escolhaToInt = objUtilitarios.inputEscolha(arrayLista);  
			       
// 			       //Array com o nome da classe, seus metodos e obj 
// 			       //String nomeDaClasse, String nomeDoProxProcesso, ArrayList<String> metodosArray, ArrayList<Nuplas> arrayDeSaida 
// 			       objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(escolhaToInt).getChave()), arrayLista.get(escolhaToInt).getValor(), nomeClassesMetodos);
// 			    }else{//TODO --> fazer para o loop de Subprocesso
// 			       //Se ha when, copia-se todos os metodos para um array<String> pois todos sao necessarios
// 			       //TODO ---> Tentar fazer de maneira mais eficiente sem essa copia de array
// 			       arrayMetodoLoop = new ArrayList<String>();
// 			       for(int i = 0; i < arrayLista.size(); i++){
// 			           for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
// 			               arrayMetodoLoop.add(arrayLista.get(i).getValor().get(j));
// 			           }
// 			       }
// 			       //TODO ---> Melhorar a forma como se obtem objetos/Processos nessa forma especifica de "when"
// 			       //TODO ---> melhorar, pois os metodos ficaram em dois arrays (arrayMetodoLoop e nomeClassesMetodos) e somente na escrita que se remove de um
// 			       //e faz-se a distincao se o metodo pertence ao loop. Eh caro, melhorar o custo.
// 			        //So grava um metodo do OU, depois em outra funcao eu o removo e gravo em seu lugar os metodos do OU qnd tem when e loop ao mesmo tempo
// 			       //TODO --> adequar o When para quando nao haver loop e ativar duas possibilidades ou apenas uma
// 			       objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(0).getChave()), arrayLista.get(0).getValor(), nomeClassesMetodos);
			                                       
// 			   }
			    
// 			   //Limpando o array para prÃ³ximas aÃ§Ãµes
// 			   nomeMetodos.clear();
// 			    //Add ao arrayNupla
// 			   //Esse array contem em cada posicao: nomeDaclasse, obj, metodos
// 			   objUtilitarios.addClasseMetodosObjts(reformatClassName(nomeClasse), arrayLista, arrayNupla);                            
// 			    //Limpando o array para o loop do Subprocesso que chama o Transicao que por sua vez chama o Escolha
// 			   arrayLista.clear();
			
// 			while (la.kind == 26) {
// 				Get();
// 				nomeClasse = RegraTipoII();
// 				Expect(7);
// 				if (StartOf(1)) {
// 					indexTrace = indexTrace;
// 					nomeMetodos.clear(); flagTraceContinue = false; flagTraceEscolha = false;
					
// 					if (la.kind == 20) {
// 						Get();
// 					}
// 					stringProcToObj = Transicao();
// 					if (la.kind == 21) {
// 						Get();
// 					}
// 				} else if (la.kind == 3) {
// 					Get();
// 					if((t.val).equals("STOP")){
// 					   // "insere um metodo fake para nao ter um array vazio e gerar exception"
// 					   nomeMetodos.add("_stop_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista); 
// 					}else{
// 					   // "insere um metodo fake para nao ter um array vazio e gerar exception"
// 					   nomeMetodos.add("_init_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
// 					} 
					
// 					Indice();
// 				} else SynErr(45);
// 				if(flagAcaoGuarda == false && indexTrace < objTrace.getValor().size()){
// 				   //Leitura do teclado se  arrayLista > 1, implica na ocorrencia de OUs.
// 				   //Retorna 0 se tamanho == 1 (so ha um elemento) ou algo no intervalo 1..Tam se tamanho > 1 
// 				   System.out.println("ASK");
// 				   System.out.println(">>>>>>" + objTrace.getValor().get(indexTrace));
// 				   System.out.println(indexTrace);
// 				   escolhaToInt = objUtilitarios.inputEscolhaTrace(arrayLista, objTrace.getValor().get(indexTrace) );
// 				   // escolhaToInt = objUtilitarios.inputEscolha(arrayLista);
// 				   System.out.println("ASK");
// 				   if(escolhaToInt == -1){
// 				       System.out.println("\nTRACE INVALIDO");
// 				       System.out.println("ABORTANDO EXECUCAO...");
// 				       arrayLista.clear();
// 				       System.exit(0);
// 				    }else{
// 				       //Array com o nome da classe, seus metodos e obj 
// 				   //String nomeDaClasse, String nomeDoProxProcesso, ArrayList<String> metodosArray, ArrayList<Nuplas> arrayDeSaida 
// 				   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(escolhaToInt).getChave()), arrayLista.get(escolhaToInt).getValor(), nomeClassesMetodos);
// 				   }
				      
				   
// 				}else{//TODO --> fazer para o loop de Subprocesso
// 				   //Se ha when, copia-se todos os metodos para um array<String> pois todos sao necessarios
// 				   //TODO ---> Tentar fazer de maneira mais eficiente sem essa copia de array
// 				   arrayMetodoLoop = new ArrayList<String>();
// 				   for(int i = 0; i < arrayLista.size(); i++){
// 				       for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
// 				           arrayMetodoLoop.add(arrayLista.get(i).getValor().get(j));
// 				       }
// 				   }
// 				   //TODO ---> Melhorar a forma como se obtem objetos/Processos nessa forma especifica de "when"
// 				   //TODO ---> melhorar, pois os metodos ficaram em dois arrays (arrayMetodoLoop e nomeClassesMetodos) e somente na escrita que se remove de um
// 				   //e faz-se a distincao se o metodo pertence ao loop. Eh caro, melhorar o custo.
// 				    //So grava um metodo do OU, depois em outra funcao eu o removo e gravo em seu lugar os metodos do OU qnd tem when e loop ao mesmo tempo
// 				   //TODO --> adequar o When para quando nao haver loop e ativar duas possibilidades ou apenas uma
// 				   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(0).getChave()), arrayLista.get(0).getValor(), nomeClassesMetodos);
				                                   
// 				}
				
// 				//Limpando o array para prÃ³ximas aÃ§Ãµes
// 				nomeMetodos.clear();
// 				//Add ao arrayNupla
// 				//Esse array contem em cada posicao: nomeDaclasse, obj, metodos
// 				objUtilitarios.addClasseMetodosObjts(reformatClassName(nomeClasse), arrayLista, arrayNupla);                            
// 				//Limpando o array para o loop do Subprocesso que chama o Transicao que por sua vez chama o Escolha
// 				arrayLista.clear();
				
// 			}
// 		} else if (la.kind == 5) {
// 			if(flagTraceContinue == true && indexTrace < objTrace.getValor().size()){
// 			   System.out.println("\nNAO HA CAMINHO VALIDO");
// 			   System.out.println("TRACE REJEITADO");
// 			   System.out.println("ABORTANDO EXECUCAO...");
// 			   System.exit(0);
// 			}
// 			if(flagTraceEscolha == true && indexTrace < objTrace.getValor().size()){
// 			   System.out.println("\nNAO HA CAMINHO VALIDO");
// 			   System.out.println("TRACE REJEITADO");
// 			   System.out.println("ABORTANDO EXECUCAO...");
// 			   System.exit(0);
// 			}                                                       
			
// 		} else SynErr(46);
// 		return nomeClasse;
// 	}

// 	void ProcParametro() {
// 		procParam = new ArrayList<Pares>(); 
// 		Expect(20);
// 		Expect(3);
// 		String tmp = t.val; 
// 		Expect(7);
// 		Expect(1);
// 		Pares par = new Pares(tmp, t.val); 
// 		while (StartOf(2)) {
// 			Operacao();
// 			par.setUlt(par.getUlt() + t.val); 
// 			Expect(1);
// 			par.setUlt(par.getUlt() + t.val); 
// 		}
// 		procParam.add(par); 
// 		while (la.kind == 26) {
// 			Get();
// 			Expect(3);
// 			tmp = t.val; 
// 			Expect(7);
// 			Expect(1);
// 			par = new Pares(tmp, t.val); 
// 			while (StartOf(2)) {
// 				Operacao();
// 				par.setUlt(par.getUlt() + t.val); 
// 				Expect(1);
// 				par.setUlt(par.getUlt() + t.val); 
// 			}
// 			procParam.add(par); 
// 		}
// 		Expect(21);
// 	}

// 	String  RegraTipoII() {
// 		String  nomeClasse;
// 		Expect(3);
// 		nomeClasse = t.val; 
// 		Indice();
// 		return nomeClasse;
// 	}

// 	void AcaoDeGuarda() {
// 		if (la.kind == 28) {
// 			Get();
// 			flagAcaoGuarda = true; 
// 			Guarda();
// 		} else if (la.kind == 4 || la.kind == 29) {
// 		} else SynErr(47);
// 	}

// 	void AlfabetoExt() {
// 		Expect(29);
// 		Expect(4);
// 		arrayAlfabeto.add(t.val);                                        
		
// 		while (la.kind == 26) {
// 			Get();
// 			Expect(4);
// 			arrayAlfabeto.add(t.val);
			                                          
// 		}
// 		Expect(30);
// 	}

// 	void RepeteAcao() {
// 		if (la.kind == 4 || la.kind == 29) {
// 			if (la.kind == 4) {
// 				Get();
// 				if(flagTraceContinue == true && (indexTrace < objTrace.getValor().size())  ){
// 				   if( (indexTrace < objTrace.getValor().size())  && (objTrace.getValor().get(indexTrace).equals(t.val))){
// 				       indexTrace++;
				       
// 				       //TODO ---> Temporario, enquanto a repeticao de metodos com o mesmo nome nao eh solucionada. Ex: Trafficlight
// 				       if(!nomeMetodos.contains(t.val)){
// 				           nomeMetodos.add(t.val);
// 				       }else{
// 				           nomeMetodos.add(t.val + "_" + metodoRepetido);
// 				           metodoRepetido++;
// 				       }
				       
// 				       System.out.println("VALOR ATINGIDO: " + t.val);
// 				   }else{                                        
// 				       nomeMetodos.clear();
// 				       flagTraceContinue = false;
// 				       // System.out.println("TRACE REJEITADO");
// 				       System.out.println("\nVALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
// 				       System.out.println("VALOR ESPERADO: " + t.val + "\n");
// 				    }
// 				}                                
				
// 				Indice();
// 			} else {
// 				AlfabetoExt();
// 				if(flagTraceContinue == true && (indexTrace < objTrace.getValor().size()) ){
// 				   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 				   if(!saidaCheckTrace.equals("")){
// 				       indexTrace++;
				       
// 				       if(!nomeMetodos.contains(saidaCheckTrace)){           
// 				           nomeMetodos.add(saidaCheckTrace);
// 				       }else{
// 				           nomeMetodos.add(saidaCheckTrace + "_" + metodoRepetido);
// 				           metodoRepetido++;
// 				       }
// 				       arrayAlfabeto.clear();
// 				       System.out.println("VALOR ATINGIDO: " + saidaCheckTrace);
// 				   }else{
// 				       System.out.println("VALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
// 				       System.out.println("VALOR ESPERADO: " + arrayAlfabeto + "\n");
// 				       flagTraceContinue = false;                                    
// 				       nomeMetodos.clear();
// 				       arrayAlfabeto.clear();
// 				   }                                    
// 				}else{
// 				   flagTraceContinue = false;   
// 				   System.out.println("\t  VALOR OBTIDO: null");
// 				   System.out.println("\tVALOR ESPERADO: " + arrayAlfabeto + "\n");                                 
// 				   nomeMetodos.clear();
// 				   arrayAlfabeto.clear();                                   
// 				   // System.out.println("TRACE REJEITADO");
// 				   // System.out.println("VALOR ESPERADO: " + objTrace.getValor().get(indexTrace));
// 				}                                
				                            
// 			}
// 			Expect(22);
// 			while (la.kind == 4 || la.kind == 29) {
// 				if (la.kind == 4) {
// 					Get();
// 					if(flagTraceContinue == true){
// 					   if((indexTrace < objTrace.getValor().size()) && objTrace.getValor().get(indexTrace).equals(t.val)){
// 					       indexTrace++;
					       
// 					       if(!nomeMetodos.contains(t.val)){
// 					           nomeMetodos.add(t.val);
// 					       }else{
// 					           nomeMetodos.add(t.val + "_" + metodoRepetido);
// 					           metodoRepetido++;
// 					       }
					       
// 					       System.out.println("VALOR ATINGIDO: " + t.val);
// 					   }else{
// 					       flagTraceContinue = false;
// 					       nomeMetodos.clear();
// 					       // System.out.println("TRACE REJEITADO");
// 					       // System.out.println("VALOR ESPERADO: " + objTrace.getValor().get(indexTrace));
// 					   }
// 					} 
					                                
// 					Indice();
// 				} else {
// 					AlfabetoExt();
// 					if(flagTraceContinue == true && (indexTrace < objTrace.getValor().size()) ){
// 					   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 					   if(!saidaCheckTrace.equals("")){
// 					       indexTrace++;
// 					        // TODO ---> colocar essa verificao nessa funcao para evitar acao repetida, nao precisa do add nessa linha pq a funcao ja add o metodo ao array
// 					       if(!nomeMetodos.contains(saidaCheckTrace)){
// 					           nomeMetodos.add(saidaCheckTrace);
// 					       }else{
// 					           nomeMetodos.add(saidaCheckTrace + "_" + metodoRepetido);
// 					           metodoRepetido++;
// 					       }
// 					       System.out.println("VALOR ATINGIDO: " + saidaCheckTrace);
// 					   }else{
// 					       System.out.println("VALOR OBTIDO:   " + objTrace.getValor().get(indexTrace));
// 					       System.out.println("VALOR ESPERADO:   " + arrayAlfabeto);
// 					       flagTraceContinue = false;
// 					       nomeMetodos.clear();
// 					       arrayAlfabeto.clear();
// 					   }                                    
// 					}else{
// 					   flagTraceContinue = false;
// 					   nomeMetodos.clear();
// 					   arrayAlfabeto.clear();                                    
// 					   // System.out.println("TRACE REJEITADO>>");
// 					   // System.out.println("VALOR ESPERADO: " + objTrace.getValor().get(indexTrace));
// 					}
// 					                                     // TODO --> evitar a pergunta, fazer via trace
// 					//  String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos);
					
// 				}
// 				Expect(22);
// 			}
// 		} else if (StartOf(6)) {
// 		} else SynErr(48);
// 	}

// 	void FatoraProcesso() {
// 		if (la.kind == 20) {
// 			Get();
// 			Expect(4);
// 			Indice();
// 			Expect(22);
// 			Expect(3);
// 			Indice();
// 			Expect(21);
// 		} else if (StartOf(7)) {
// 		} else SynErr(49);
// 	}

// 	void Escolha() {
// 		if (la.kind == 27) {
// 			if(flagTraceContinue == false && indexTraceSub == 0){indexTrace = 0;} 
// 			Get();
// 			arrayEscolha = new ArrayList<String>(); 
// 			AcaoDeGuarda();
// 			if (la.kind == 4) {
// 				Get();
// 				if(flagTraceContinue == false){
// 				  if(objTrace.getValor().get(indexTrace).equals(t.val)){
// 				      // objTrace.getValor().remove(t.val);
// 				      indexTrace++;
// 				      arrayEscolha.add(t.val);
// 				      flagTraceEscolha = true;
// 				      System.out.println("VALOR ATINGIDO: " + t.val);
// 				   }else{
// 				      flagTraceEscolha = false;
// 				      arrayEscolha.clear();
// 				      System.out.println("TRACE REJEITADO");
// 				      System.out.println("VALOR OBTIDO:   " + objTrace.getValor().get(indexTrace));
// 				      System.out.println("VALOR ESPERADO: " + t.val);
// 				  }
// 				}
				
// 				Indice();
// 			} else if (la.kind == 29) {
// 				AlfabetoExt();
// 				if(flagTraceContinue == false && indexTrace < objTrace.getValor().size()){
// 				   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 				   if(!saidaCheckTrace.equals("")){
// 				       indexTrace++;
// 				       flagTraceEscolha = true;
// 				       arrayEscolha.add(saidaCheckTrace);
// 				       arrayAlfabeto.clear();
// 				       System.out.println("VALOR ATINGIDO: " + saidaCheckTrace);                                            
				       
// 				   }else{
// 				       flagTraceEscolha = false;
// 				       System.out.println("TRACE REJEITADO");
// 				       System.out.println("VALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
// 				       System.out.println("VALOR ESPERADO: " + arrayAlfabeto);
// 				       arrayEscolha.clear();
// 				       arrayAlfabeto.clear();
// 				   }
// 				}
// 				                                    // stringNomeAcao = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); //Melhorar
// 				// arrayEscolha.add(stringNomeAcao);
				
// 			} else SynErr(50);
// 			Expect(22);
// 			RepeteAcaoEscolha(arrayEscolha);
// 			if (StartOf(4)) {
// 				FatoraProcesso();
// 			} else if (la.kind == 3) {
// 				Get();
// 				String procObj = "";
// 				if(flagTraceEscolha == true){
// 				   procObj = t.val;
// 				   // objUtilitarios.addToArrayEscolha(t.val, arrayEscolha, arrayLista);    
// 				}
				                                
// 				if(flagTraceEscolha == true){
// 				   // procObj += t.val;
// 				   objUtilitarios.addToArrayEscolha(procObj, arrayEscolha, arrayLista);    
// 				}
				
// 				Indice();
// 				if(dadosLoop != null){
// 				   dadosLoop.setCondicaoVal(stringRun);
// 				}
// 				// dadosLoop.setCondicaoVal(stringRun);
// 				DadosLoop dadosAux = new DadosLoop();
// 				dadosAux.setOrdemIntervalo(dadosLoop.getOrdemIntervalo());
// 				dadosAux.setIndiceVal(dadosLoop.getIndiceVal());
// 				dadosAux.setCondicaoVal(new String(dadosLoop.getCondicaoVal()));
// 				dadosAux.setNovoIndice(dadosLoop.getNovoIndice());
// 				arrayDadosLoop.add(dadosAux);
// 				stringRun = "";
// 				dadosLoop.clear();
// 				//TODO ---> Replicar para a repetiÃ§Ã£o do escolha
				
// 			} else SynErr(51);
// 			while (la.kind == 27) {
// 				Get();
// 				if (flagTraceContinue == false && indexTraceSub  == 0){indexTrace = 0;} 
// 				AcaoDeGuarda();
// 				if (la.kind == 4) {
// 					Get();
// 					if(flagTraceEscolha == false && flagTraceContinue == false && indexTrace < objTrace.getValor().size()){
// 					   if(indexTrace < objTrace.getValor().size() &&  objTrace.getValor().get(indexTrace).equals(t.val)){
// 					       // objTrace.getValor().remove(t.val);
// 					       indexTrace++;
// 					       flagTraceEscolha = true;
// 					       arrayEscolha.clear();
// 					       arrayEscolha.add(t.val);                                                            
// 					       System.out.println("VALOR ATINGIDO: " + t.val);                                                            
// 					   }else{
// 					   flagTraceEscolha = false;
// 					   arrayEscolha.clear();
// 					   System.out.println("TRACE REJEITADO");
// 					   System.out.println("VALOR ESPERADO: " + objTrace.getValor().get(indexTrace));
// 					   }
// 					}
// 					                                                // arrayEscolha.clear(); 
// 					// arrayEscolha.add(t.val);
					
// 					Indice();
// 				} else if (la.kind == 29) {
// 					AlfabetoExt();
// 					if(flagTraceEscolha == false && flagTraceContinue == false && indexTrace < objTrace.getValor().size()){
// 					   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 					   if(!saidaCheckTrace.equals("")){
// 					       indexTrace++;
// 					       flagTraceEscolha = true;
// 					       arrayEscolha.clear();
// 					       arrayEscolha.add(saidaCheckTrace);
// 					       System.out.println("TRACE: " + saidaCheckTrace);
// 					   }else{
// 					       flagTraceEscolha = false;
// 					       arrayEscolha.clear();
// 					       arrayAlfabeto.clear();             
// 					       System.out.println("TRACE REJEITADO");
// 					       System.out.println("VALOR ESPERADO: " + objTrace.getValor().get(indexTrace));
// 					   }
// 					}
// 					                                        // stringNomeAcao = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); 
// 					// arrayEscolha.add(stringNomeAcao);
					
// 				} else SynErr(52);
// 				Expect(22);
// 				RepeteAcaoEscolha(arrayEscolha);
// 				if (StartOf(4)) {
// 					FatoraProcesso();
// 				} else if (la.kind == 3) {
// 					Get();
// 					String procObj = "";
// 					if(flagTraceEscolha == true){
// 					   procObj = t.val;
// 					   objUtilitarios.addToArrayEscolha(t.val, arrayEscolha, arrayLista);
// 					}
					                                    
// 					if(flagTraceEscolha == true){
// 					   // procObj += t.val;
// 					objUtilitarios.addToArrayEscolha(procObj, arrayEscolha, arrayLista);    
// 					}
					
// 					Indice();
// 				} else SynErr(53);
// 			}
// 			if(flagTraceContinue == false){
// 			   if(indexTrace == objTrace.getValor().size()){
// 			       if(flagTraceEscolha == true){
// 			           System.out.println("\n--------------");
// 			           System.out.println("|TRACE ACEITO|");
// 			           System.out.println("--------------");
// 			       }                                    
// 			   }
// 			}
// 			                            if(flagTraceEscolha == true){
// 			   if(indexTrace < objTrace.getValor().size()){
// 			       System.out.println("Continuando o Trace em um SUBPROCESSO, indice atual: " + indexTrace + "\n");
// 			       indexTraceSub = indexTrace;
// 			   }
// 			}
// 			if(flagTraceContinue == false && flagTraceEscolha == false){
// 			   System.out.println("TRACE REJEITADO");
// 			   System.out.println("ABORTANDO EXECUCAO...");
// 			   System.exit(0);
// 			}
			
// 		} else if (la.kind == 5 || la.kind == 21 || la.kind == 26) {
// 			if(flagTraceContinue == false && flagTraceEscolha == false){
// 			       System.out.println("TRACE REJEITADO");
// 			       System.out.println("ABORTANDO EXECUCAO...");
// 			       System.exit(0);
// 			}
			
// 		} else SynErr(54);
// 	}

// 	void RepeteAcaoEscolha(ArrayList<String> arrayEscolha ) {
// 		if (la.kind == 4 || la.kind == 29) {
// 			if (la.kind == 4) {
// 				Get();
// 				if(flagTraceEscolha == true && (indexTrace < objTrace.getValor().size())){
// 				   if(objTrace.getValor().get(indexTrace).equals(t.val)){
// 				       indexTrace++;     
// 				       System.out.println("VALOR ATINGIDO: " + t.val);
				  
// 				       //TODO ---> melhorar
// 				       if(!arrayEscolha.contains(t.val)){
// 				           arrayEscolha.add(t.val);
// 				       }else{
// 				           arrayEscolha.add(t.val + "_" + metodoRepetido);
// 				           metodoRepetido++;
// 				       }
// 				    }else{
// 				       flagTraceEscolha = false;
// 				       arrayEscolha.clear();
// 				       // System.out.println("TRACE REJEITADO");
// 				       System.out.println("VALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
// 				       System.out.println("VALOR ESPERADO: " + t.val);
// 				   }
// 				}else if(flagTraceEscolha == true){
// 				   if(indexTrace == objTrace.getValor().size()){
// 				       System.out.println("\nTRACE INVALIDO");
// 				       System.out.println("ABORTANDO EXECUCAO...");
// 				       System.exit(0);
// 				   }
				   
// 				}
				
				                        
// 				Indice();
// 			} else {
// 				AlfabetoExt();
// 				if(flagTraceEscolha == true && (indexTrace < objTrace.getValor().size()) ){
// 				   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 				   if(!saidaCheckTrace.equals("")){
// 				       indexTrace++;
				       
// 				       if(!arrayEscolha.contains(saidaCheckTrace)){
// 				           arrayEscolha.add(saidaCheckTrace);
// 				       }else{
// 				           arrayEscolha.add(saidaCheckTrace + "_" + metodoRepetido);
// 				           metodoRepetido++;
// 				       }
// 				        System.out.println("VALOR ATINGIDO: " + saidaCheckTrace);
// 				   }else{
// 				   flagTraceEscolha = false;
// 				   // System.out.println("TRACE REJEITADO");
// 				   System.out.println("VALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
// 				   System.out.println("VALOR ESPERADO: " + arrayAlfabeto);
// 				   arrayEscolha.clear();
// 				   arrayAlfabeto.clear();                                    
// 				   }
// 				}else if(flagTraceEscolha == true){
// 				   if(indexTrace == objTrace.getValor().size()){
// 				       System.out.println("\nTRACE INVALIDO");
// 				       System.out.println("ABORTANDO EXECUCAO...");
// 				       arrayAlfabeto.clear();
// 				       System.exit(0);
// 				   }
// 				}
// 				// String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); arrayEscolha.add(tmpVal);
				                                     
// 			}
// 			Expect(22);
// 			while (la.kind == 4 || la.kind == 29) {
// 				if (la.kind == 4) {
// 					Get();
// 					if(flagTraceEscolha == true && indexTrace < objTrace.getValor().size()){
// 					   if(objTrace.getValor().get(indexTrace).equals(t.val)){
// 					       // objTrace.getValor().remove(t.val);
// 					       indexTrace++;
// 					       System.out.println("VALOR ATINGIDO: " + t.val);
// 					        if(!arrayEscolha.contains(t.val)){
// 					           arrayEscolha.add(t.val);
// 					       }else{
// 					           arrayEscolha.add(t.val + "_" + metodoRepetido);
// 					           metodoRepetido++;
// 					       }
// 					    }else{
// 					       flagTraceContinue = false;
// 					       arrayEscolha.clear();
// 					       // System.out.println("TRACE REJEITADO");
// 					       System.out.println("VALOR OBTIDO:   " + objTrace.getValor().get(indexTrace));
// 					       System.out.println("VALOR ESPERADO: " + t.val);
// 					   }
// 					}else if(flagTraceEscolha == true){
// 					   if(indexTrace == objTrace.getValor().size()){
// 					       System.out.println("\nTRACE INVALIDO");
// 					       System.out.println("ABORTANDO EXECUCAO...");
// 					       arrayAlfabeto.clear();
// 					       System.exit(0);
// 					   }
// 					}
					                            
// 					Indice();
// 				} else {
// 					AlfabetoExt();
// 					if(flagTraceEscolha == true && indexTrace < objTrace.getValor().size()){
// 					   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
// 					   if(!saidaCheckTrace.equals("")){
// 					       indexTrace++;
// 					        if(!arrayEscolha.contains(saidaCheckTrace)){
// 					           arrayEscolha.add(saidaCheckTrace);
// 					       }else{
// 					           arrayEscolha.add(saidaCheckTrace + "_" + metodoRepetido);
// 					           metodoRepetido++;
// 					       }
// 					   }else{
// 					   flagTraceEscolha = false;
// 					   // System.out.println("TRACE REJEITADO");
// 					   System.out.println("VALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
// 					   System.out.println("VALOR ESPERADO: " + arrayAlfabeto);
// 					   arrayEscolha.clear();
// 					   arrayAlfabeto.clear();                                    
// 					   }
// 					}else if(flagTraceEscolha == true){
// 					   if(indexTrace == objTrace.getValor().size()){
// 					       System.out.println("\nTRACE INVALIDO");
// 					       System.out.println("ABORTANDO EXECUCAO...");
// 					       arrayAlfabeto.clear();
// 					       System.exit(0);
// 					   }
// 					}
// 					// String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); arrayEscolha.add(tmpVal); 
					                                
// 				}
// 				Expect(22);
// 			}
// 		} else if (StartOf(6)) {
// 		} else SynErr(55);
// 	}

// 	void IndiceTipoI() {
// 		if (la.kind == 1) {
// 			Get();
// 			processoIndexadoProducao += t.val;  
// 		} else if (la.kind == 3) {
// 			Get();
// 			processoIndexadoProducao += t.val; 
// 		} else if (la.kind == 2) {
// 			Get();
// 			processoIndexadoProducao += t.val; 
// 		} else SynErr(56);
// 		if (StartOf(2)) {
// 			Operacao();
// 			if (la.kind == 1) {
// 				Get();
// 			} else if (la.kind == 4) {
// 				Get();
// 			} else if (la.kind == 3) {
// 				Get();
// 			} else SynErr(57);
// 		} else if (la.kind == 24) {
// 		} else SynErr(58);
// 	}

// 	void IndiceTipoII() {
// 		Expect(4);
// 		dadosLoop = new DadosLoop(); 
// 		dadosLoop.setOrdemIntervalo(processoIndexadoProducao); //TODO ---> se 0 comeÃ§a de 0 se N comeÃ§a de N
// 		dadosLoop.setIndiceVal(t.val);//TODO ---> Arruamr um jeito de saber qnd a variavel recebe a constante em numero ou N        
// 		dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );       
		
// 		if (la.kind == 25) {
// 			Get();
// 			if (la.kind == 2) {
// 				Intervalo();
// 			} else if (la.kind == 3) {
// 				Get();
// 			} else SynErr(59);
// 		} else if (StartOf(2)) {
// 			Operacao();
// 			if(dadosLoop != null){ 
// 			   dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );
// 			} 
			
// 			if (la.kind == 1) {
// 				Get();
// 				if(dadosLoop != null){dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );} 
// 			} else if (la.kind == 4) {
// 				Get();
// 				if(dadosLoop != null){dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );} 
// 			} else if (la.kind == 3) {
// 				Get();
// 				if(dadosLoop != null){dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );} 
// 			} else SynErr(60);
// 		} else if (la.kind == 24) {
// 		} else SynErr(61);
// 	}

// 	void Guarda() {
// 		if (la.kind == 3) {
// 			Get();
// 			stringRun += ("(" + t.val + ")"); 
// 		} else if (la.kind == 20) {
// 			Condicao();
// 		} else SynErr(62);
// 	}

// 	void Condicao() {
// 		Expect(20);
// 		stringRun += t.val; 
// 		if (la.kind == 1) {
// 			Get();
// 			stringRun += t.val; 
// 		} else if (la.kind == 4) {
// 			Get();
// 			stringRun += t.val; 
// 		} else if (la.kind == 3) {
// 			Get();
// 			stringRun += t.val; 
// 		} else SynErr(63);
// 		if (StartOf(8)) {
// 			if (StartOf(2)) {
// 				Operacao();
// 				stringRun += t.val; 
// 			} else {
// 				IgualdadeDesig();
// 				stringRun += t.val; 
// 			}
// 			if (la.kind == 1) {
// 				Get();
// 				stringRun += t.val; 
// 			} else if (la.kind == 4) {
// 				Get();
// 				stringRun += t.val; 
// 			} else if (la.kind == 3) {
// 				Get();
// 				stringRun += t.val; 
// 			} else SynErr(64);
// 		}
// 		Expect(21);
// 		stringRun += t.val; 
// 	}



// 	public void Parse() {
// 		la = new Token();
// 		la.val = "";		
// 		Get();
// 		Fsp3();
// 		Expect(0);

// 	}

// 	private static final boolean[][] set = {
// 		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
// 		{x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,x,x, T,T,x,x, x},
// 		{x,x,x,x, x,x,x,x, x,x,T,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x},
// 		{x,x,x,T, x,x,T,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,x,x,x, x},
// 		{x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,x,x, x,x,T,T, x,x,x,x, x},
// 		{x,x,x,x, x,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,T,T,x, x,x,T,T, x,x,x,x, x},
// 		{x,x,x,T, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,x,x, x,x,T,T, x,x,x,x, x},
// 		{x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,T,x,x, x,x,T,T, x,x,x,x, x},
// 		{x,x,x,x, x,x,x,x, x,x,T,T, T,T,T,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x, x}

// 	};
// } // end Parser


// class Errors {
// 	public int count = 0;                                    // number of errors detected
// 	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
// 	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
// 	private String dataAqr = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); 
// 	private EscritaLog escritaLog = new EscritaLog("log_"+dataAqr, ".txt");
	
// 	protected void printMsg(int line, int column, String msg) {
// 		StringBuffer b = new StringBuffer(errMsgFormat);
// 		int pos = b.indexOf("{0}");
// 		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
// 		pos = b.indexOf("{1}");
// 		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
// 		pos = b.indexOf("{2}");
// 		if (pos >= 0) b.replace(pos, pos+3, msg);
// 		escritaLog.escrever(b.toString());		
// 		errorStream.println(b.toString());
// 	}
	
// 	public void SynErr (int line, int col, int n) {
// 		String s;
// 		switch (n) {
// 			case 0: s = "EOF expected"; break;
// 			case 1: s = "numero expected"; break;
// 			case 2: s = "nzero expected"; break;
// 			case 3: s = "nomeVar expected"; break;
// 			case 4: s = "nomeMin expected"; break;
// 			case 5: s = "\".\" expected"; break;
// 			case 6: s = "\"const\" expected"; break;
// 			case 7: s = "\"=\" expected"; break;
// 			case 8: s = "\"range\" expected"; break;
// 			case 9: s = "\"..\" expected"; break;
// 			case 10: s = "\"+\" expected"; break;
// 			case 11: s = "\"-\" expected"; break;
// 			case 12: s = "\"*\" expected"; break;
// 			case 13: s = "\"/\" expected"; break;
// 			case 14: s = "\"==\" expected"; break;
// 			case 15: s = "\">\" expected"; break;
// 			case 16: s = "\"<\" expected"; break;
// 			case 17: s = "\">=\" expected"; break;
// 			case 18: s = "\"<=\" expected"; break;
// 			case 19: s = "\"!=\" expected"; break;
// 			case 20: s = "\"(\" expected"; break;
// 			case 21: s = "\")\" expected"; break;
// 			case 22: s = "\"->\" expected"; break;
// 			case 23: s = "\"[\" expected"; break;
// 			case 24: s = "\"]\" expected"; break;
// 			case 25: s = "\":\" expected"; break;
// 			case 26: s = "\",\" expected"; break;
// 			case 27: s = "\"|\" expected"; break;
// 			case 28: s = "\"when\" expected"; break;
// 			case 29: s = "\"{\" expected"; break;
// 			case 30: s = "\"}\" expected"; break;
// 			case 31: s = "??? expected"; break;
// 			case 32: s = "invalid Definicoes"; break;
// 			case 33: s = "invalid Processo"; break;
// 			case 34: s = "invalid Intervalo"; break;
// 			case 35: s = "invalid Intervalo"; break;
// 			case 36: s = "invalid Intervalo"; break;
// 			case 37: s = "invalid Operacao"; break;
// 			case 38: s = "invalid IgualdadeDesig"; break;
// 			case 39: s = "invalid Regra"; break;
// 			case 40: s = "invalid Transicao"; break;
// 			case 41: s = "invalid Transicao"; break;
// 			case 42: s = "invalid Indice"; break;
// 			case 43: s = "invalid Indice"; break;
// 			case 44: s = "invalid Subprocesso"; break;
// 			case 45: s = "invalid Subprocesso"; break;
// 			case 46: s = "invalid Subprocesso"; break;
// 			case 47: s = "invalid AcaoDeGuarda"; break;
// 			case 48: s = "invalid RepeteAcao"; break;
// 			case 49: s = "invalid FatoraProcesso"; break;
// 			case 50: s = "invalid Escolha"; break;
// 			case 51: s = "invalid Escolha"; break;
// 			case 52: s = "invalid Escolha"; break;
// 			case 53: s = "invalid Escolha"; break;
// 			case 54: s = "invalid Escolha"; break;
// 			case 55: s = "invalid RepeteAcaoEscolha"; break;
// 			case 56: s = "invalid IndiceTipoI"; break;
// 			case 57: s = "invalid IndiceTipoI"; break;
// 			case 58: s = "invalid IndiceTipoI"; break;
// 			case 59: s = "invalid IndiceTipoII"; break;
// 			case 60: s = "invalid IndiceTipoII"; break;
// 			case 61: s = "invalid IndiceTipoII"; break;
// 			case 62: s = "invalid Guarda"; break;
// 			case 63: s = "invalid Condicao"; break;
// 			case 64: s = "invalid Condicao"; break;
// 			default: s = "error " + n; break;
// 		}
// 		printMsg(line, col, s);
// 		count++;
// 	}

// 	public void SemErr (int line, int col, String s) {	
// 		printMsg(line, col, s);
// 		count++;
// 	}
	
// 	public void SemErr (String s) {
// 		errorStream.println(s);
// 		count++;
// 	}
	
// 	public void Warning (int line, int col, String s) {	
// 		printMsg(line, col, s);
// 	}
	
// 	public void Warning (String s) {
// 		errorStream.println(s);
// 	}
// } // Errors


// class FatalError extends RuntimeException {
// 	public static final long serialVersionUID = 1L;
// 	public FatalError(String s) { super(s); }
// }
