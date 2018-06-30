

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.File;

//Inicio do compilador


public class Parser {
	public static final int _EOF = 0;
	public static final int _numero = 1;
	public static final int _nomeVar = 2;
	public static final int _nomeMin = 3;
	public static final int maxT = 36;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;

	ArrayList<String> arrayConstantes   = new ArrayList<String>();
//Array para armazenar os intervalos
ArrayList<String> arrayRange        = new ArrayList<String>();
//Array para salvar os metodos de um processo na regra Transicao
ArrayList<String> nomeMetodos       = new ArrayList<String>();
//Array para armazenar a lista de metodos que formam o alfabeto de um processo
ArrayList<String> arrayAlfabeto     = new ArrayList<String>();
//Array para armazenar os metodos de um processo na regra Escolha
ArrayList<String> arrayEscolha;
//Array para armazenar as acoes/metodos da regra Escolha quando ha "when" em um loop
ArrayList<String> arrayMetodoLoop   = null;
//Array para armazenar os parametros de um processo
ArrayList<Pares> procParam          = null;
//Array para armazenar os metodos e o proximo processo (objeto a ser instanciado em Java)
ArrayList<ArrayLista> arrayLista    = new ArrayList<ArrayLista>();
//Array para armazenar o nome do processo, os seus metodos e o proximo processo
ArrayList<Nupla> nomeClassesMetodos = new ArrayList<Nupla>();
//Array principal para armazenar o nome do processo, os seus metodos e o proximo processo
ArrayList<Nupla> arrayNupla         = new ArrayList<Nupla>();
//Array para armazenar cada item ( if(x> y) ) do loop na forma i:0..N
ArrayList<DadosLoop> arrayDadosLoop = new ArrayList<DadosLoop>();
ArrayList<Relabelling> relabelling = new ArrayList<Relabelling>();
//Array que contem os dados de cada processo
ArrayList<ArrayList<Nupla>> arrayProcessos = new ArrayList<>();
ArrayList<ComposicaoParalelaData> arrayComposicaoParalela = new ArrayList<ComposicaoParalelaData>();
ArrayList<DadosExecucao> dadosExecucao = new ArrayList<DadosExecucao>();
ArrayList<EscolhaCaminho> arrayCaminho;
ArrayList<DadosRepeticao> arrayDadosRepeticao = new ArrayList<DadosRepeticao>();
//Estrutura de dados que contÃ©m o nome da composiÃ§Ã£o e os seus respectivos dados
ComposicaoParalelaData composicaoParalelaData = null;
//Objeto com os dados de loop em uma composicao paralela usando rÃ³tulo 
DadosRepeticaoParalela dadosRepeticaoParalela;
DadosRepeticao dadosRepeticao = new DadosRepeticao();
Condicao condicao = null;
Condicao operacao = null;
CondicaoOperacao condicaoOperacao = null;
HashMap<String,ArrayList<String>> dicionario = new HashMap<String, ArrayList<String>>();
ArrayList<Escolhas> escolhas = new ArrayList<Escolhas>();
ArrayList<String> traceTmp = new ArrayList<String>();

// ArrayList<DadosRepeticaoParalela> arrayDadosRepeticaoParalela = new ArrayList<DadosRepeticaoParalela>();
//Variavel para armazenar o nome da classe temporariamente
String nomeClasse                   = "";
//Variavel para armazenar a acao/metodo escolhida de um alfabeto
String stringNomeAcao               = "";
//VariÃ¡vel para armazenar o nome do subprocesso
String nomeProcessoFatora           = "";
//Variavel para armazenar um numero | nomeVar | nzero da regra IndiceTipoI
String processoIndexadoProducao     = "";
//Variavel para armazenar dados da regra Condicao que estaram no metodo run() do programa principal
String stringRun                    = "";                                                
//Indice global para verificar em tempo de execuÃ§Ã£o se dado elemento do Trace eh satisfeito
int indexTrace                      = 0;
//Indice global da regra Subprocesso para verificar em tempo de execuÃ§Ã£o se dado elemento do Trace eh satisfeito
int indexTraceSub                   = 0;
//TODO ---> Variavel temporaria enquanto a repeticao de metodos com o mesmo nome nao eh solucionada
int metodoRepetido                  = 1;
//flag para validar a continucao do Trace caso o caminho atual seja valido na regra Transicao
boolean flagTraceContinue           = false;
//flag para validar a continucao do Trace caso o caminho atual seja valido na regra Escolha/RepeteAcaoEscolha
boolean flagTraceEscolha            = false;
//Evita que em um OU grave-se apenas um metodo do tipo (abc -> def -> PROCESSO) quando houver mais de uma opcao na regra Escolha
boolean flagAcaoGuarda              = false;
//Previne que em um loop o metodo de um objeto chamado do while seja instanciado e chamado por essa nova instancia
//Desta forma a pilha nao estoura
boolean ultimoMetodo                = false;
//Informa se ha um loop ou Stop, utilizado no programa principal
boolean flagLoop                    = false;
//char para evitar nomes repetidos, concatena-se como o processo que ira executar uma aÃ§Ã£o
int alfabetoParalelo = 1;
//Objeto para acessar o array com a sequencia do Trace
LeituraTrace objTrace               = null;
//Objeto para manipulacao de dados no disco
EscritaArquivo arquivo              = null;
//Objeto que reune os metodos utilizados para manipulacao de dados
MetodosUtilitarios objUtilitarios   = new MetodosUtilitarios();
//Objeto para manipulacao de itens que serao utilizados pelo programa principal
DadosLoop dadosLoop                 = new DadosLoop();

//Metodo para remover o uppercase dos nomes dos processos
//Considerando que a entrada Ã© upperCase
public String reformatClassName(String nomeClasse){
    return nomeClasse.charAt(0) + nomeClasse.substring(1).toLowerCase();
}



	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	void Fsp3() {
		Definicoes();
		Processo();
		Expect(4);
		while (la.kind == 2 || la.kind == 28) {
			if (la.kind == 2) {
				Processo();
			} else {
				ComposicaoParalela();
			}
			Expect(4);
		}
		objTrace = new LeituraTrace(System.getProperty("user.dir")+"/entrada.txt");
		try{
		   // System.out.println("\n========================");
		   objTrace.lerTrace();
		   if(objTrace.getValor().isEmpty()){
		       // System.out.println("TRACE VAZIO");
		       // System.out.println("ABORTANDO EXECUCAO...");
		       // System.exit(0);
		   }else{
		       // System.out.println("TRACE OBTIDO DO ARQUIVO:\n" + objTrace.getValor() + "\n");
		   }
		}catch(Exception e){
		   e.printStackTrace();
		}
		//private ArrayList<CondicaoOperacao> condicaoOperacao;	
		// private ArrayList<Pares> procParam;
		            //DEBUG
		// if(dadosRepeticao.getLimiteInferior() != null){
		   // System.out.println("\n\nDados Repeticao:");
		   // System.out.println("Nome processo: " + dadosRepeticao.getNomeProcesso());
		   // System.out.println("indice: " + dadosRepeticao.getIndice());
		   // System.out.println("valor indice: " + dadosRepeticao.getValorIndice());
		    // System.out.println("ProcParam PRIM: " + dadosRepeticao.getProcParam().get(0).getPrim());
		   // System.out.println("ProcParam ULT: " + dadosRepeticao.getProcParam().get(0).getUlt());
		    // System.out.println("Lim Inferior: " + dadosRepeticao.getLimiteInferior());
		   // System.out.println("Lim Superior: " + dadosRepeticao.getLimiteSuperior());
		   // System.out.println("Nome acao: " + dadosRepeticao.getCondicaoOperacao().get(0).getNomeAcao() );
		   // System.out.println("Cond E: " + dadosRepeticao.getCondicaoOperacao().get(0).getCondicao().getOperandoE());
		   // System.out.println("Cond comp: " + dadosRepeticao.getCondicaoOperacao().get(0).getCondicao().getComparador());
		   // System.out.println("Cond D: " + dadosRepeticao.getCondicaoOperacao().get(0).getCondicao().getOperandoD());
		        // System.out.println("OP E: " + dadosRepeticao.getCondicaoOperacao().get(0).getOperacao().getOperandoE());
		   // System.out.println("OP comp: " + dadosRepeticao.getCondicaoOperacao().get(0).getOperacao().getComparador());
		   // System.out.println("OP D: " + dadosRepeticao.getCondicaoOperacao().get(0).getOperacao().getOperandoD());
		             // System.out.println("prox processo: " + dadosRepeticao.getCondicaoOperacao().get(0).getProxProcesso() );
		     // System.out.println("Nome acao: " + dadosRepeticao.getCondicaoOperacao().get(1).getNomeAcao() );
		   // System.out.println("Cond E: " + dadosRepeticao.getCondicaoOperacao().get(1).getCondicao().getOperandoE());
		   // System.out.println("Cond comp: " + dadosRepeticao.getCondicaoOperacao().get(1).getCondicao().getComparador());
		   // System.out.println("Cond D: " + dadosRepeticao.getCondicaoOperacao().get(1).getCondicao().getOperandoD());
		        // System.out.println("OP E: " + dadosRepeticao.getCondicaoOperacao().get(1).getOperacao().getOperandoE());
		   // System.out.println("OP comp: " + dadosRepeticao.getCondicaoOperacao().get(1).getOperacao().getComparador());
		   // System.out.println("OP D: " + dadosRepeticao.getCondicaoOperacao().get(1).getOperacao().getOperandoD());
		             // System.out.println("prox processo: " + dadosRepeticao.getCondicaoOperacao().get(1).getProxProcesso() );
		
		// }
		
		Arrays.stream(new File(System.getProperty("user.dir") + "/Saida/").listFiles()).forEach(File::delete);
		            /*Loop para acessar cada processo (cada processo pode ter subprocesso)*/
		/*size - 1 pois a Ãºltima posiÃ§Ã£o sÃ£o os processos paralelos */ 
		// System.out.println("ENTRADAS: " + arrayProcessos.size());
		// objUtilitarios.imprimirArrayProcessos(arrayProcessos);
		for(int x = 0; x < arrayProcessos.size(); x++){
		    /*VariÃ¡vel Local*/
		   ArrayList<Nupla> arrayNupla = arrayProcessos.get(x);
		   for(int i = 0; i < arrayNupla.size(); i++){
		       if(i == arrayNupla.size() - 1){
		           //Para que o ultimo metodo nao crie uma instancia do metodo chamador que desencadeou a sequencia de acoes
		           //em caso de loop a repeticao se da pelo while(true)
		           ultimoMetodo = true;
		       }
		            //Possivel problema em passar o nome da classe variando
		       
		        if(!arrayNupla.get(i).getValor().isEmpty()){
		           // System.out.println("\nCHAMADA - Valor: " + arrayNupla.get(i).getValor() );
		           
		           objUtilitarios.escreverNoDiscov2(reformatClassName(arrayNupla.get(i).getChave()), reformatClassName(arrayNupla.get(i).getObj())
		           ,arrayNupla.get(i).getValor(), arrayNupla, ultimoMetodo, flagLoop, i, dicionario, dadosRepeticao);
		            
		           /*VariÃ¡veis para manipular o dicionÃ¡rio*/
		           String chave = arrayNupla.get(i).getChave();
		           ArrayList<String> arrayTmp = arrayNupla.get(i).getValor();
		            /*O dicionÃ¡rio sÃ³ pode conter aÃ§Ãµes Ãºnicas*/
		           if(dicionario.containsKey(arrayNupla.get(i).getChave())){
		               for(int j = 0; j < arrayTmp.size(); j++){                                            
		                   if(!dicionario.get(chave).contains(arrayTmp.get(j))){
		                       dicionario.get(chave).add(arrayTmp.get(j));    
		                   }
		               }                        
		           /*Caso nÃ£o haja no dicionÃ¡rio, insira todo o array a o nome da classe/processo*/
		           }else{  
		               dicionario.put(chave, new ArrayList<String>(arrayTmp));
		           }                      
		       }
		   }
		}
		// objUtilitarios.imprimirArrayProcessos(arrayProcessos);
		// System.out.println("dicionario");
		// for(String chave : dicionario.keySet()){
		//     System.out.print(chave + ": ");
		//     ArrayList<String> valor = dicionario.get(chave);
		//     System.out.println(valor);
		// }
		// System.out.println("dicionarioAtributo");
		// for(String chave : dicionarioAtributos.keySet()){
		//     System.out.print(chave + ": ");
		//     ArrayList<String> valor = dicionarioAtributos.get(chave);
		//     System.out.println(valor);
		// }
		
		  //Escrita da classe principal
		alfabetoParalelo = objUtilitarios.escreverClassePrincipalv3(arrayProcessos, composicaoParalelaData, alfabetoParalelo,
		               arrayDadosLoop, dicionario, objTrace.getValor(), traceTmp);
		
	}

	void Definicoes() {
		if (la.kind == 5) {
			Const();
			Definicoes();
		} else if (la.kind == 7) {
			Range();
			Definicoes();
		} else if (la.kind == 2) {
		} else SynErr(37);
	}

	void Processo() {
		nomeClasse = ""; String stringProcToObj = ""; 
		                   
		
		nomeClasse = Regra();
		Expect(6);
		if (la.kind == 19) {
			Get();
			stringProcToObj = Transicao();
			Expect(20);
		} else if (la.kind == 2) {
			Get();
			if((t.val).equals("STOP")){
			     //Teste repeticao
			// nomeAcao.add("_init_");
			// EscolhaCaminho objCaminho = new EscolhaCaminho(nomeClasse, nomeAcao, stringProcToObj);
			// arrayCaminho = new ArrayList<EscolhaCaminho>();
			// arrayCaminho.add(objCaminho);
			// objCaminho = null; 
			// nomeAcao = null;
			// DadosExecucao objDados = new DadosExecucao(nomeClasse, arrayCaminho, "null");            
			// dados.add(objDados);
			// objDados = null;  
			// arrayCaminho = null;
			      /*if(objTrace.getValor().get(0).equals(t.val)){
			       System.out.println("\n--------------");
			           System.out.println("|TRACE ACEITO|");
			           System.out.println("--------------");
			   }*/
			   //Se t.val == STOP, cria-se um metodo/acao fake para fins de nao quebrar o codigo
			   //"insere um metodo fake para nao ter um array vazio e gerar exception"
			   nomeMetodos.add("_stop_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista); 
			}else{
			   //TODO ---> tratar  o caso do trace qnd tive _init_
			   //Se nao for STOP, criar-se um metodo/acao fake para dar arranque
			   //"insere um metodo fake para nao ter um array vazio e gerar exception"
			   nomeMetodos.add("_init_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
			} 
			
			Indice();
		} else SynErr(38);
		int escolhaToInt = 0;
		/*if(flagAcaoGuarda == false && indexTrace < objTrace.getValor().size()){*/
		if(flagAcaoGuarda == false){                            
		   //Leitura do teclado se  arrayLista > 1, ou seja, hÃ¡ OUs.
		   //Retorna 0 se tamanho == 1 e 1..Size se tamanho > 1   
		   
		   escolhaToInt = objUtilitarios.inputEscolha(arrayLista); 
		   //TODO ---> Melhorar no caso do "when"
		    // ArrayList<String> auxTrace = new ArrayList<String>();
		   // objUtilitarios.teste(arrayLista, 0, nomeClasse, auxTrace);
		   // System.out.println("Aux trace: " + auxTrace);
		   Escolhas tmp = new Escolhas();
		    tmp.chave = nomeClasse;
		   tmp.arrayLista.addAll(arrayLista);
		   escolhas.add(tmp);
		   // System.out.println(escolhas.get(0).chave);
		   // objTrace.getValor().addAll(auxTrace);
		   // System.exit(0);
		   
		    objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(escolhaToInt).getChave()), arrayLista.get(escolhaToInt).getValor(), nomeClassesMetodos);
		                                   
		}else{
		   //REVER:
		   //Se ha uma acao de guarda, os metodos quando ocorre when precisam ser gravados sem encadeamento
		   arrayMetodoLoop = new ArrayList<String>();
		   //TODO ---> melhorar, pois os metodos ficaram em dois arrays (arrayMetodoLoop e nomeClassesMetodos) e somente na escrita que se remove de um
		   //e faz-se a distincao se o metodo pertence ao loop. Eh caro, melhorar o custo.
		   
		   // System.out.println("\nVERIFY>>>" );
		   for(int i = 0; i < arrayLista.size(); i++){
		       for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
		           // System.out.println("\nVERIFY: " + arrayLista.get(i).getValor().get(j) );
		           arrayMetodoLoop.add(arrayLista.get(i).getValor().get(j));
		       }
		   }
		                       
		   //TODO ---> Melhorar a forma como se obtem objetos/Processos nessa forma especifica de "when"
		   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(0).getChave()), arrayLista.get(0).getValor(), nomeClassesMetodos);                                
		}                     
		//Array com o nome da classe, seus metodos e obj - Contem tudo do processo atual! 
		// addClasseMetodos(IN: String nomeClasse, IN:  String nomeObj, IN: ArrayList<String> arrayMetodos, OUT: ArrayList<Nupla> nomeMetodoClasse)
		//Limpando o array de metodos
		nomeMetodos.clear();
		                //ArrayNupla global que recebe todos os dados de um processo
		ArrayList<ArrayLista> arrayListaTmp = new ArrayList<ArrayLista>(); 
		arrayListaTmp.add(arrayLista.get(escolhaToInt));
		objUtilitarios.addClasseMetodosObjts(reformatClassName(nomeClasse), arrayLista, arrayNupla); 
		// dadosExecucao = new DadosExecucao();
		// dadosExecucao.setNomeProcesso(reformatClassName(nomeClasse));
		// dadosExecucao.
		//Limpando o array para a chamada do Escolha pela Transicao dentro do Subprocesso
		arrayLista = new ArrayList<ArrayLista>();               
		            
		nomeClasse = Subprocesso();
		arrayProcessos.add(arrayNupla);
		arrayNupla = new ArrayList<Nupla>();
		//Caso nÃ£o haja loop no formato [i:0..N]            
		if(dadosRepeticao.getIndice() == null){
		   // System.out.println("\n<<<TESTE>>>\n");
		   objUtilitarios.teste(escolhas.get(0).arrayLista, 0, escolhas.get(0).chave, traceTmp, escolhas);                
		}else{
		   // System.out.println("Chamada do loop");
		   objUtilitarios.teste2(arrayProcessos, 0, dadosRepeticao, traceTmp);
		}
		
		ActionHiding();
	}

	void ComposicaoParalela() {
		Expect(28);
		Expect(2);
		String stringSaida = ""; 
		//---> Colocar em um array para o caso de N composiÃ§Ãµes
		            /*ContÃ©m o nome da composiÃ§Ã£o e os respectivos dados e, um array de array do tipo dados paralelos*/
		ComposicaoParalelaData objComposicaoParalela = new ComposicaoParalelaData();                        
		objComposicaoParalela.setNomeComposicao(t.val);
		/*Abriga uma chave e um valor (Array de strings)*/
		DadosParalelos objDadosParalelos = new DadosParalelos(); 
		ArrayList<DadosParalelos> arrayDadosParalelos = new ArrayList<DadosParalelos>(); 
		
		if (la.kind == 19) {
			ProcParametro();
		} else if (la.kind == 6) {
		} else SynErr(39);
		Expect(6);
		Expect(19);
		if (la.kind == 2 || la.kind == 3 || la.kind == 35) {
			stringSaida = Rotulo();
		} else if (la.kind == 30) {
			Get();
			Expect(3);
			while (la.kind == 25) {
				Get();
			}
			Expect(31);
			Expect(32);
		} else SynErr(40);
		Expect(2);
		if(dadosRepeticaoParalela.getIndice() != null){//Indica que hÃ¡ de fato parametrizaÃ§Ã£o
		   dadosRepeticaoParalela.setNomeProcesso(objComposicaoParalela.getNomeComposicao());
		   dadosRepeticaoParalela.setNomeProcessoRepeticao(t.val);
		   
		   objDadosParalelos.setChave(t.val);
		   //Add os objetos (aÃ§Ãµes) da rotulaÃ§Ã£o parametrizada aos dados paralelos
		   objDadosParalelos = objUtilitarios.escreverProcessoCompostoParametrizadov2(dadosRepeticaoParalela, objDadosParalelos);
		   //Array de dados paralelos
		   arrayDadosParalelos.add(objDadosParalelos); 
		    // arrayDadosRepeticaoParalela.add(dadosRepeticaoParalela);
		   dadosRepeticaoParalela = null;
		/*RÃ³tulo simples*/
		}else{
		   /*Acrescentando o nome do processo concorrente e o seu rÃ³tulo se houver*/
		   objDadosParalelos.setChave(t.val);
		   /*Caso nÃ£o haja rÃ³tulo, o nome padÃ£o do objeto para o processo/class Ã© objNomeProcesso*/
		   if(stringSaida.equals("")){
		        boolean check = false;
		       for(int k = 0; k < arrayDadosParalelos.size(); k++){
		           if(arrayDadosParalelos.get(k).getValor().contains((t.val).toLowerCase() + "$")){
		               check = true;
		           }
		       } 
		        if(check == false){
		           objDadosParalelos.addDadosParalelos((t.val).toLowerCase() + "$");
		           // alfabetoParalelo++;
		           arrayDadosParalelos.add(objDadosParalelos); 
		       }
		       
		    }else{
		       
		       
		           objDadosParalelos.addDadosParalelos( (t.val).toLowerCase() + "$" + stringSaida); stringSaida = "";
		           arrayDadosParalelos.add(objDadosParalelos); 
		        
		   }
		   // arrayDadosParalelos.add(objDadosParalelos); 
		}
		
		while (la.kind == 28) {
			Get();
			if (la.kind == 2 || la.kind == 3 || la.kind == 35) {
				stringSaida = Rotulo();
			} else if (la.kind == 30) {
				Get();
				Expect(3);
				while (la.kind == 25) {
					Get();
					Expect(3);
				}
				Expect(31);
				Expect(32);
			} else SynErr(41);
			Expect(2);
			if(dadosRepeticaoParalela.getIndice() != null){
			   dadosRepeticaoParalela.setNomeProcesso(objComposicaoParalela.getNomeComposicao());
			   dadosRepeticaoParalela.setNomeProcessoRepeticao(t.val);
			   
			   objDadosParalelos = new DadosParalelos();
			   /*Acrescentando o nome do processo concorrente e o seu rÃ³tulo se houver*/
			   objDadosParalelos.setChave(t.val);                                        
			   objDadosParalelos = objUtilitarios.escreverProcessoCompostoParametrizadov2(dadosRepeticaoParalela, objDadosParalelos);
			   
			   // arrayDadosRepeticaoParalela.add(dadosRepeticaoParalela);
			   dadosRepeticaoParalela = null;
			}else{
			   /*Caso nÃ£o seja um label no formato loop, entÃ£o faÃ§a: */
			   
			   objDadosParalelos = new DadosParalelos();
			   /*Acrescentando o nome do processo concorrente e o seu rÃ³tulo se houver*/
			   objDadosParalelos.setChave(t.val);
			   /*Caso nÃ£o haja rÃ³tulo, o nome padÃ£o do objeto para o processo/class Ã© objNomeProcesso*/
			   if(stringSaida.equals("")){
			       
			       boolean check = false;
			       for(int k = 0; k < arrayDadosParalelos.size(); k++){
			           if(arrayDadosParalelos.get(k).getValor().contains((t.val).toLowerCase() + "$")){
			               check = true;
			           }
			       }
			        if(check == false){
			           objDadosParalelos.addDadosParalelos((t.val).toLowerCase() + "$");
			           // alfabetoParalelo++;
			       }
			        
			   }else{                            
			       objDadosParalelos.addDadosParalelos( (t.val).toLowerCase() + "$" + stringSaida); stringSaida = "";
			   }
			}
			                    /*Verifica se o processo paralelo jÃ¡ existe no array, se sim acrescenta-se apenas o rÃ³tulo caso ocorra*/
			arrayDadosParalelos = objUtilitarios.haProcessoParalelo(arrayDadosParalelos, objDadosParalelos, t.val); 
			
		}
		Expect(20);
		objComposicaoParalela.getArrayComposicao().add(arrayDadosParalelos);
		            /*Salvando a referÃªncia no objeto global*/
		composicaoParalelaData = objComposicaoParalela;
		            //Array principal da composicao, caso haja mais de uma. NÃ£o estÃ¡ sendo utilizado
		// arrayComposicaoParalela.add(objComposicaoParalela);
		
		objComposicaoParalela = null;
		objDadosParalelos = null;
		arrayDadosParalelos = null;
		            // objUtilitarios.imprimirArrayProcessosParalelos(arrayComposicaoParalela);
		
		RenomearRotulo();
	}

	void Const() {
		Expect(5);
		Expect(2);
		Expect(6);
		Expect(1);
	}

	void Range() {
		Expect(7);
		Expect(2);
		Expect(6);
		if (la.kind == 2) {
			Get();
		} else if (la.kind == 1) {
			Get();
		} else SynErr(42);
		Intervalo();
	}

	void Intervalo() {
		Expect(8);
		if (la.kind == 2) {
			Get();
			dadosRepeticao.setLimiteSuperior(t.val); 
		} else if (la.kind == 3) {
			Get();
		} else if (la.kind == 1) {
			Get();
		} else SynErr(43);
		if (StartOf(1)) {
			Operacao();
			if (la.kind == 2) {
				Get();
			} else if (la.kind == 3) {
				Get();
			} else if (la.kind == 1) {
				Get();
			} else SynErr(44);
		} else if (StartOf(2)) {
		} else SynErr(45);
	}

	void Operacao() {
		if (la.kind == 9) {
			Get();
		} else if (la.kind == 10) {
			Get();
		} else if (la.kind == 11) {
			Get();
		} else if (la.kind == 12) {
			Get();
		} else SynErr(46);
	}

	void IgualdadeDesig() {
		switch (la.kind) {
		case 13: {
			Get();
			break;
		}
		case 14: {
			Get();
			break;
		}
		case 15: {
			Get();
			break;
		}
		case 16: {
			Get();
			break;
		}
		case 17: {
			Get();
			break;
		}
		case 18: {
			Get();
			break;
		}
		default: SynErr(47); break;
		}
	}

	String  Regra() {
		String  nomeClasse;
		Expect(2);
		nomeClasse = t.val; 
		if (la.kind == 19) {
			ProcParametro();
		} else if (la.kind == 6 || la.kind == 21) {
			Indice();
		} else SynErr(48);
		return nomeClasse;
	}

	String  Transicao() {
		String  stringProcToObj;
		stringProcToObj = ""; 
		AcaoDeGuarda();
		if (la.kind == 3 || la.kind == 21) {
			if (la.kind == 3) {
				String auxiliarNomeMetodo = "";
				Get();
				auxiliarNomeMetodo += t.val;
				
				if (la.kind == 4) {
					Get();
					auxiliarNomeMetodo += t.val; 
					Expect(3);
					auxiliarNomeMetodo += t.val; 
				}
				nomeMetodos.add(auxiliarNomeMetodo); 
				                                         if(dadosRepeticao != null){
				    if(dadosRepeticao.getIndice() != null){
				        condicaoOperacao.setNomeAcao(auxiliarNomeMetodo);
				    }
				}
				
			} else {
				Get();
				if (la.kind == 1) {
					Get();
				} else if (la.kind == 3) {
					Get();
				} else if (la.kind == 2) {
					Get();
				} else SynErr(49);
				Expect(22);
			}
			Indice();
		} else if (la.kind == 30) {
			AlfabetoExt();
			stringNomeAcao = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos);
			
		} else SynErr(50);
		Expect(23);
		RepeteAcao();
		if (StartOf(3)) {
			ParentesisFatora();
			objUtilitarios.addToArrayEscolha(nomeProcessoFatora, nomeMetodos, arrayLista); 
		} else if (la.kind == 2) {
			Get();
			stringProcToObj = t.val;
			objUtilitarios.addToArrayEscolha(stringProcToObj, nomeMetodos, arrayLista);
			 if(dadosRepeticao != null){
			   if(dadosRepeticao.getIndice() != null){
			       condicaoOperacao.setProxProcesso(t.val);
			   }
			}
			
			Indice();
			if(dadosRepeticao != null){
			   if(operacao != null){
			       condicaoOperacao.setOperacao(operacao);
			       dadosRepeticao.getCondicaoOperacao().add(condicaoOperacao);
			   }
			}
			                        
			                            dadosLoop.setCondicaoVal(stringRun);
			                                //Para armazenar os dados do obj DadosLoop Ã© preciso instanciar um novo objeto uma vez que o obj antigo sera limpo
			//Copia
			//Contem condicao, indice, process parameter, novo indice ...
			                                
			DadosLoop dadosAux = new DadosLoop();
			dadosAux.setOrdemIntervalo(dadosLoop.getOrdemIntervalo());
			dadosAux.setIndiceVal(dadosLoop.getIndiceVal());
			dadosAux.setCondicaoVal(new String(dadosLoop.getCondicaoVal()));
			dadosAux.setNovoIndice(new String(dadosLoop.getNovoIndice()));
			//acrescenta-se ao array com dados de cada subprocesso
			arrayDadosLoop.add(dadosAux);
			                                //Limpando a string
			stringRun = "";
			//Limpando o conteudo do obj
			dadosLoop.clear();
			//TODO ---> Replicar para a repetiÃ§Ã£o do escolha
			
		} else SynErr(51);
		Escolha();
		return stringProcToObj;
	}

	void Indice() {
		if (la.kind == 21) {
			Get();
			if (la.kind == 1 || la.kind == 2) {
				IndiceTipoI();
			} else if (la.kind == 3) {
				IndiceTipoII();
			} else SynErr(52);
			Expect(22);
			Indice();
		} else if (StartOf(4)) {
		} else SynErr(53);
	}

	String  Subprocesso() {
		String  nomeClasse;
		nomeClasse = ""; String stringProcToObj = ""; nomeMetodos.clear();  
		if (la.kind == 25) {
			Get();
			nomeClasse = RegraTipoII();
			Expect(6);
			if (la.kind == 19) {
				nomeMetodos.clear(); /*flagTraceContinue = false; flagTraceEscolha = false; indexTraceSub = indexTrace;
				// System.out.println(indexTrace + " " + indexTraceSub);*/
				
				Get();
				stringProcToObj = Transicao();
				Expect(20);
			} else if (la.kind == 2) {
				Get();
				if((t.val).equals("STOP")){
				   // "insere um metodo fake para nao ter um array vazio e gerar exception"
				   nomeMetodos.add("_stop_"); 
				   objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista); 
				}else{
				   // "insere um metodo fake para nao ter um array vazio e gerar exception"
				   nomeMetodos.add("_init_"); 
				   objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
				} 
				
				Indice();
			} else SynErr(54);
			int escolhaToInt = 0;
			//TODO ---> colocar no loop do Subprocesso
			/*if(flagAcaoGuarda == false && indexTrace < objTrace.getValor().size())*/
			if(flagAcaoGuarda == false){
			    //Leitura do teclado se  arrayLista > 1, implica na ocorrencia de OUs.
			       //Retorna 0 se tamanho == 1 (so ha um elemento) ou algo no intervalo 1..Tam se tamanho > 1 
			        escolhaToInt = objUtilitarios.inputEscolha(arrayLista);
			       
			       
			       Escolhas tmp = new Escolhas();
			        tmp.chave = nomeClasse;
			       tmp.arrayLista.addAll(arrayLista);
			       escolhas.add(tmp);
			       
			       //Array com o nome da classe, seus metodos e obj 
			       //String nomeDaClasse, String nomeDoProxProcesso, ArrayList<String> metodosArray, ArrayList<Nuplas> arrayDeSaida 
			       objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(escolhaToInt).getChave()), arrayLista.get(escolhaToInt).getValor(), nomeClassesMetodos);
			    }else{//TODO --> fazer para o loop de Subprocesso
			       //Se ha when, copia-se todos os metodos para um array<String> pois todos sao necessarios
			       //TODO ---> Tentar fazer de maneira mais eficiente sem essa copia de array
			       arrayMetodoLoop = new ArrayList<String>();
			       for(int i = 0; i < arrayLista.size(); i++){
			           for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
			               arrayMetodoLoop.add(arrayLista.get(i).getValor().get(j));
			           }
			       }
			       //TODO ---> Melhorar a forma como se obtem objetos/Processos nessa forma especifica de "when"
			       //TODO ---> melhorar, pois os metodos ficaram em dois arrays (arrayMetodoLoop e nomeClassesMetodos) e somente na escrita que se remove de um
			       //e faz-se a distincao se o metodo pertence ao loop. Eh caro, melhorar o custo.
			        //So grava um metodo do OU, depois em outra funcao eu o removo e gravo em seu lugar os metodos do OU qnd tem when e loop ao mesmo tempo
			       //TODO --> adequar o When para quando nao haver loop e ativar duas possibilidades ou apenas uma
			       objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(0).getChave()), arrayLista.get(0).getValor(), nomeClassesMetodos);
			                                       
			   }
			    
			   //Limpando o array para prÃ³ximas aÃ§Ãµes
			   nomeMetodos.clear();
			    //Add ao arrayNupla
			   //Esse array contem em cada posicao: nomeDaclasse, obj, metodos
			   objUtilitarios.addClasseMetodosObjts(reformatClassName(nomeClasse), arrayLista, arrayNupla);                            
			    //Limpando o array para o loop do Subprocesso que chama o Transicao que por sua vez chama o Escolha
			   arrayLista.clear();
			
			while (la.kind == 25) {
				Get();
				nomeClasse = RegraTipoII();
				Expect(6);
				if (StartOf(5)) {
					nomeMetodos.clear(); /*flagTraceContinue = false; flagTraceEscolha = false;*/
					                            
					if (la.kind == 19) {
						Get();
					}
					stringProcToObj = Transicao();
					if (la.kind == 20) {
						Get();
					}
				} else if (la.kind == 2) {
					Get();
					if((t.val).equals("STOP")){
					   // "insere um metodo fake para nao ter um array vazio e gerar exception"
					   nomeMetodos.add("_stop_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista); 
					}else{
					   // "insere um metodo fake para nao ter um array vazio e gerar exception"
					   nomeMetodos.add("_init_"); objUtilitarios.addToArrayEscolha(t.val, nomeMetodos, arrayLista);
					} 
					
					Indice();
				} else SynErr(55);
				if(flagAcaoGuarda == false){
				   //Leitura do teclado se  arrayLista > 1, implica na ocorrencia de OUs.
				   //Retorna 0 se tamanho == 1 (so ha um elemento) ou algo no intervalo 1..Tam se tamanho > 1 
				  /*
				   
				   escolhaToInt = objUtilitarios.inputEscolhaTrace(arrayLista, objTrace.getValor().get(indexTrace) );*/
				   escolhaToInt = objUtilitarios.inputEscolha(arrayLista);
				     Escolhas tmp = new Escolhas();
				    tmp.chave = nomeClasse;
				   tmp.arrayLista.addAll(arrayLista);
				   escolhas.add(tmp);
				                                        
				   //Array com o nome da classe, seus metodos e obj 
				   //String nomeDaClasse, String nomeDoProxProcesso, ArrayList<String> metodosArray, ArrayList<Nuplas> arrayDeSaida 
				   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(escolhaToInt).getChave()), arrayLista.get(escolhaToInt).getValor(), nomeClassesMetodos);
				   
				        
				   
				}else{//TODO --> fazer para o loop de Subprocesso
				   //Se ha when, copia-se todos os metodos para um array<String> pois todos sao necessarios
				   //TODO ---> Tentar fazer de maneira mais eficiente sem essa copia de array
				   arrayMetodoLoop = new ArrayList<String>();
				   for(int i = 0; i < arrayLista.size(); i++){
				       for(int j = 0; j < arrayLista.get(i).getValor().size(); j++){
				           arrayMetodoLoop.add(arrayLista.get(i).getValor().get(j));
				       }
				   }
				   //TODO ---> Melhorar a forma como se obtem objetos/Processos nessa forma especifica de "when"
				   //TODO ---> melhorar, pois os metodos ficaram em dois arrays (arrayMetodoLoop e nomeClassesMetodos) e somente na escrita que se remove de um
				   //e faz-se a distincao se o metodo pertence ao loop. Eh caro, melhorar o custo.
				    //So grava um metodo do OU, depois em outra funcao eu o removo e gravo em seu lugar os metodos do OU qnd tem when e loop ao mesmo tempo
				   //TODO --> adequar o When para quando nao haver loop e ativar duas possibilidades ou apenas uma
				   objUtilitarios.addClasseMetodos(reformatClassName(nomeClasse), reformatClassName(arrayLista.get(0).getChave()), arrayLista.get(0).getValor(), nomeClassesMetodos);
				                                   
				}
				
				//Limpando o array para prÃ³ximas aÃ§Ãµes
				nomeMetodos.clear();
				//Add ao arrayNupla
				//Esse array contem em cada posicao: nomeDaclasse, obj, metodos
				objUtilitarios.addClasseMetodosObjts(reformatClassName(nomeClasse), arrayLista, arrayNupla);                            
				//Limpando o array para o loop do Subprocesso que chama o Transicao que por sua vez chama o Escolha
				arrayLista.clear();
				
			}
		} else if (la.kind == 4 || la.kind == 33 || la.kind == 34) {
		} else SynErr(56);
		return nomeClasse;
	}

	void ActionHiding() {
		if (la.kind == 33) {
			Get();
			Expect(30);
			Expect(3);
			while (la.kind == 25) {
				Get();
				Expect(3);
			}
			Expect(31);
		} else if (la.kind == 34) {
			Get();
			Expect(30);
			Expect(3);
			while (la.kind == 25) {
				Get();
				Expect(3);
			}
			Expect(31);
		} else if (la.kind == 4) {
		} else SynErr(57);
	}

	void ProcParametro() {
		procParam = new ArrayList<Pares>(); 
		Expect(19);
		Expect(2);
		String tmp = t.val; 
		Expect(6);
		Expect(1);
		Pares par = new Pares(tmp, t.val); 
		while (StartOf(1)) {
			Operacao();
			par.setUlt(par.getUlt() + t.val); 
			Expect(1);
			par.setUlt(par.getUlt() + t.val); 
		}
		procParam.add(par);
		dadosRepeticao.getProcParam().add(par);
		
		while (la.kind == 25) {
			Get();
			Expect(2);
			tmp = t.val; 
			Expect(6);
			Expect(1);
			par = new Pares(tmp, t.val); 
			while (StartOf(1)) {
				Operacao();
				par.setUlt(par.getUlt() + t.val); 
				Expect(1);
				par.setUlt(par.getUlt() + t.val); 
			}
			procParam.add(par); dadosRepeticao.getProcParam().add(par);
		}
		Expect(20);
	}

	String  RegraTipoII() {
		String  nomeClasse;
		Expect(2);
		nomeClasse = t.val;
		 dadosRepeticao.setNomeProcesso(t.val);
		
		Indice();
		return nomeClasse;
	}

	void AcaoDeGuarda() {
		if (la.kind == 27) {
			Get();
			flagAcaoGuarda = true; 
			Guarda();
		} else if (la.kind == 3 || la.kind == 21 || la.kind == 30) {
		} else SynErr(58);
	}

	void AlfabetoExt() {
		Expect(30);
		if (la.kind == 3) {
			Get();
			arrayAlfabeto.add(t.val);                                        
			
			while (la.kind == 4) {
				Get();
				Expect(3);
			}
		} else if (la.kind == 21) {
			Get();
			Expect(1);
			Expect(22);
		} else SynErr(59);
		while (la.kind == 25) {
			Get();
			if (la.kind == 3) {
				Get();
				arrayAlfabeto.add(t.val);                                            
				
				while (la.kind == 4) {
					Get();
					Expect(3);
				}
			} else if (la.kind == 21) {
				Get();
				if (la.kind == 1) {
					Get();
				} else if (la.kind == 3) {
					Get();
				} else if (la.kind == 2) {
					Get();
				} else SynErr(60);
				Expect(22);
			} else SynErr(61);
		}
		Expect(31);
	}

	void RepeteAcao() {
		if (la.kind == 3 || la.kind == 21 || la.kind == 30) {
			if (la.kind == 3 || la.kind == 21) {
				if (la.kind == 3) {
					String auxiliarNomeMetodo = "";
					Get();
					auxiliarNomeMetodo += t.val; 
					
					if (la.kind == 4) {
						Get();
						auxiliarNomeMetodo += t.val; 
						Expect(3);
						auxiliarNomeMetodo += t.val; 
					}
					if(!nomeMetodos.contains(auxiliarNomeMetodo)){
					   nomeMetodos.add(auxiliarNomeMetodo);
					}else{
					   nomeMetodos.add(auxiliarNomeMetodo + "$" + metodoRepetido);
					   metodoRepetido++;
					}
					
				} else {
					Get();
					if (la.kind == 1) {
						Get();
					} else if (la.kind == 3) {
						Get();
					} else if (la.kind == 2) {
						Get();
					} else SynErr(62);
					Expect(22);
				}
				Indice();
			} else {
				AlfabetoExt();
				String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos);
				// TODO ---> colocar essa verificao nessa funcao para evitar acao repetida, nao precisa do add nessa linha pq a funcao ja add o metodo ao array
				// if(!nomeMetodos.contains(tmpVal)){           
				//     nomeMetodos.add(tmpVal);
				// }else{
				//     nomeMetodos.add(tmpVal + "_" + metodoRepetido);
				//     metodoRepetido++;
				// }
				//TODO ---> Evitar add o metodo repetido, fazer a checagem o o if acima. SE for preciso, alterar a funcao escolhaoptalfabeto
				  
				/*if(flagTraceContinue == true && (indexTrace < objTrace.getValor().size()) ){
				   String saidaCheckTrace = objUtilitarios.alfabetoCheckTrace(arrayAlfabeto, objTrace.getValor().get(indexTrace), 0);
				   if(!saidaCheckTrace.equals("")){
				       indexTrace++;
				       
				       if(!nomeMetodos.contains(saidaCheckTrace)){           
				           nomeMetodos.add(saidaCheckTrace);
				       }else{
				           nomeMetodos.add(saidaCheckTrace + "_" + metodoRepetido);
				           metodoRepetido++;
				       }
				       arrayAlfabeto.clear();
				       System.out.println("VALOR ATINGIDO: " + saidaCheckTrace);
				   }else{
				       System.out.println("VALOR OBTIDO: " + objTrace.getValor().get(indexTrace));
				       System.out.println("VALOR ESPERADO: " + arrayAlfabeto + "\n");
				       flagTraceContinue = false;                                    
				       nomeMetodos.clear();
				       arrayAlfabeto.clear();
				   }                                    
				}else{
				   flagTraceContinue = false;   
				   System.out.println("\t  VALOR OBTIDO: null");
				   System.out.println("\tVALOR ESPERADO: " + arrayAlfabeto + "\n");                                 
				   nomeMetodos.clear();
				   arrayAlfabeto.clear();                                   
				   // System.out.println("TRACE REJEITADO");
				   // System.out.println("VALOR ESPERADO: " + objTrace.getValor().get(indexTrace));
				} */                               
				                            
			}
			Expect(23);
			while (la.kind == 3 || la.kind == 21 || la.kind == 30) {
				if (la.kind == 3 || la.kind == 21) {
					if (la.kind == 3) {
						String auxiliarNomeMetodo = "";
						Get();
						auxiliarNomeMetodo += t.val;
						
						if (la.kind == 4) {
							Get();
							auxiliarNomeMetodo+=t.val;
							Expect(3);
							auxiliarNomeMetodo+= t.val; 
						}
						if(!nomeMetodos.contains(auxiliarNomeMetodo)){
						   nomeMetodos.add(auxiliarNomeMetodo);
						}else{
						   nomeMetodos.add(auxiliarNomeMetodo + "$" + metodoRepetido);
						   metodoRepetido++;
						}
						
					} else {
						Get();
						if (la.kind == 1) {
							Get();
						} else if (la.kind == 3) {
							Get();
						} else if (la.kind == 2) {
							Get();
						} else SynErr(63);
						Expect(22);
					}
					Indice();
				} else {
					AlfabetoExt();
					String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos);
					  //TODO ---> Evitar add o metodo repetido, fazer a checagem o o if acima. SE for preciso, alterar a funcao escolhaoptalfabeto
					
				}
				Expect(23);
			}
		} else if (StartOf(6)) {
		} else SynErr(64);
	}

	void ParentesisFatora() {
		if (la.kind == 19) {
			Get();
			BlocoFatorado();
			Expect(20);
		} else if (StartOf(7)) {
		} else SynErr(65);
	}

	void Escolha() {
		if (la.kind == 26) {
			Get();
			arrayEscolha = new ArrayList<String>(); 
			AcaoDeGuarda();
			if (la.kind == 3 || la.kind == 21) {
				if (la.kind == 3) {
					String auxiliarNomeMetodo = "";
					Get();
					auxiliarNomeMetodo += t.val;
					
					if (la.kind == 4) {
						Get();
						auxiliarNomeMetodo += t.val; 
						Expect(3);
						auxiliarNomeMetodo += t.val; 
					}
					arrayEscolha.add(auxiliarNomeMetodo); 
					                                         if(dadosRepeticao != null){
					    if(dadosRepeticao.getIndice() != null){
					        condicaoOperacao.setNomeAcao(auxiliarNomeMetodo);
					    }
					}
					
				} else {
					Get();
					if (la.kind == 1) {
						Get();
					} else if (la.kind == 3) {
						Get();
					} else if (la.kind == 2) {
						Get();
					} else SynErr(66);
					Expect(22);
				}
				Indice();
			} else if (la.kind == 30) {
				AlfabetoExt();
				stringNomeAcao = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); //Melhorar
				arrayEscolha.add(stringNomeAcao);
				
			} else SynErr(67);
			Expect(23);
			RepeteAcaoEscolha(arrayEscolha);
			if (StartOf(3)) {
				ParentesisFatora();
				objUtilitarios.addToArrayEscolha(nomeProcessoFatora, arrayEscolha, arrayLista);
			} else if (la.kind == 2) {
				Get();
				String procObj = "";
				                                        procObj = t.val;
				   objUtilitarios.addToArrayEscolha(procObj, arrayEscolha, arrayLista);    
				   //CONTINUE
				    if(dadosRepeticao != null){
				       
				       if(dadosRepeticao.getIndice() != null){
				           condicaoOperacao.setProxProcesso(t.val);                                                
				       }
				   }
				
				Indice();
				if(dadosRepeticao != null){
				   if(operacao != null){
				       condicaoOperacao.setOperacao(operacao);
				       dadosRepeticao.getCondicaoOperacao().add(condicaoOperacao);
				       
				   }
				}
				
			} else SynErr(68);
			while (la.kind == 26) {
				Get();
				AcaoDeGuarda();
				if (la.kind == 3 || la.kind == 21) {
					if (la.kind == 3) {
						String auxiliarNomeMetodo = "";
						Get();
						auxiliarNomeMetodo += t.val;
						arrayEscolha.clear();
						
						if (la.kind == 4) {
							Get();
							auxiliarNomeMetodo += t.val;
							Expect(3);
							auxiliarNomeMetodo += t.val; arrayEscolha.add(auxiliarNomeMetodo);
						}
						arrayEscolha.add(auxiliarNomeMetodo);
						if(dadosRepeticao != null){
						   if(dadosRepeticao.getIndice() != null){
						       condicaoOperacao.setNomeAcao(auxiliarNomeMetodo);
						   }
						}
						
					} else {
						Get();
						if (la.kind == 1) {
							Get();
						} else if (la.kind == 3) {
							Get();
						} else if (la.kind == 2) {
							Get();
						} else SynErr(69);
						Expect(22);
					}
					Indice();
				} else if (la.kind == 30) {
					AlfabetoExt();
					stringNomeAcao = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); 
					arrayEscolha.add(stringNomeAcao);
					
				} else SynErr(70);
				Expect(23);
				RepeteAcaoEscolha(arrayEscolha);
				if (StartOf(3)) {
					ParentesisFatora();
					objUtilitarios.addToArrayEscolha(nomeProcessoFatora, arrayEscolha, arrayLista);
				} else if (la.kind == 2) {
					Get();
					String procObj = "";
					procObj = t.val;
					 objUtilitarios.addToArrayEscolha(t.val, arrayEscolha, arrayLista);
					 if(dadosRepeticao != null){
					   if(dadosRepeticao.getIndice() != null){
					       condicaoOperacao.setProxProcesso(t.val);
					   }
					}
					                                    
					Indice();
					if(dadosRepeticao != null){
					   if(operacao != null){
					       condicaoOperacao.setOperacao(operacao);
					       dadosRepeticao.getCondicaoOperacao().add(condicaoOperacao);
					   }
					}
					
				} else SynErr(71);
			}
			
		} else if (StartOf(8)) {
		} else SynErr(72);
	}

	void RepeteAcaoEscolha(ArrayList<String> arrayEscolha ) {
		if (la.kind == 3 || la.kind == 21 || la.kind == 30) {
			if (la.kind == 3 || la.kind == 21) {
				if (la.kind == 3) {
					String auxiliarNomeMetodo = "";
					Get();
					auxiliarNomeMetodo += t.val; 
					
					if (la.kind == 4) {
						Get();
						auxiliarNomeMetodo += t.val;
						Expect(3);
						auxiliarNomeMetodo+= t.val;
					}
					if(!arrayEscolha.contains(auxiliarNomeMetodo)){
					   arrayEscolha.add(auxiliarNomeMetodo);
					}else{
					   arrayEscolha.add(auxiliarNomeMetodo + "$" + metodoRepetido);
					   metodoRepetido++;
					}
					
				} else {
					Get();
					if (la.kind == 1) {
						Get();
					} else if (la.kind == 2) {
						Get();
					} else SynErr(73);
					Expect(22);
				}
				Indice();
			} else {
				AlfabetoExt();
				String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); arrayEscolha.add(tmpVal);
				//TODO ---> Evitar add o metodo repetido, fazer a checagem o o if acima. SE for preciso, alterar a funcao escolhaoptalfabeto
				 
			}
			Expect(23);
			while (la.kind == 3 || la.kind == 21 || la.kind == 30) {
				if (la.kind == 3 || la.kind == 21) {
					if (la.kind == 3) {
						String auxiliarNomeMetodo = "";
						Get();
						auxiliarNomeMetodo += t.val; 
						
						if (la.kind == 4) {
							Get();
							auxiliarNomeMetodo += t.val;
							Expect(3);
							auxiliarNomeMetodo += t.val;
						}
						if(!arrayEscolha.contains(auxiliarNomeMetodo)){
						   arrayEscolha.add(auxiliarNomeMetodo);
						}else{
						   arrayEscolha.add(auxiliarNomeMetodo + "$" + metodoRepetido);
						   metodoRepetido++;
						}
						
					} else {
						Get();
						if (la.kind == 1) {
							Get();
						} else if (la.kind == 3) {
							Get();
						} else if (la.kind == 2) {
							Get();
						} else SynErr(74);
						Expect(22);
					}
					Indice();
				} else {
					AlfabetoExt();
					String tmpVal = objUtilitarios.escolhaOptAlfabeto(arrayAlfabeto, nomeMetodos); arrayEscolha.add(tmpVal); 
					//TODO ---> Evitar add o metodo repetido, fazer a checagem o o if acima. SE for preciso, alterar a funcao escolhaoptalfabeto
					
					                                
				}
				Expect(23);
			}
		} else if (StartOf(6)) {
		} else SynErr(75);
	}

	void IndiceTipoI() {
		if (la.kind == 1) {
			Get();
			processoIndexadoProducao += t.val;  
		} else if (la.kind == 2) {
			Get();
			processoIndexadoProducao += t.val; 
		} else SynErr(76);
		if (StartOf(1)) {
			Operacao();
			if (la.kind == 1) {
				Get();
			} else if (la.kind == 3) {
				Get();
			} else if (la.kind == 2) {
				Get();
			} else SynErr(77);
		} else if (la.kind == 22) {
		} else SynErr(78);
	}

	void IndiceTipoII() {
		Expect(3);
		dadosLoop = new DadosLoop(); 
		dadosLoop.setOrdemIntervalo(processoIndexadoProducao); //TODO ---> se 0 comeÃ§a de 0 se N comeÃ§a de N
		dadosLoop.setIndiceVal(t.val);//TODO ---> Arruamr um jeito de saber qnd a variavel recebe a constante em numero ou N        
		dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );
		                            
		//Novo
		if(dadosRepeticao.getIndice() == null){
		   dadosRepeticao.setIndice(t.val);
		   // dadosRepeticao.setValorIndice(processoIndexadoProducao);
		}else{
		   operacao = new Condicao();
		   operacao.setOperandoE(t.val);
		}
		
		
		if (la.kind == 24) {
			Get();
			if (la.kind == 2) {
				Get();
				if (la.kind == 8) {
					Intervalo();
				} else if (la.kind == 22) {
				} else SynErr(79);
			} else if (la.kind == 1) {
				Get();
				dadosRepeticao.setLimiteInferior(t.val); 
				Intervalo();
			} else SynErr(80);
		} else if (StartOf(1)) {
			Operacao();
			if(dadosLoop != null){ 
			   dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );
			} 
			 if(condicao != null){
			   operacao.setComparador(t.val);
			}
			 
			
			if (la.kind == 1) {
				Get();
				if(dadosLoop != null){dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );} 
			} else if (la.kind == 3) {
				Get();
				if(dadosLoop != null){dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );} 
			} else if (la.kind == 2) {
				Get();
				if(dadosLoop != null){dadosLoop.setNovoIndice(dadosLoop.getNovoIndice() + t.val );} 
			} else SynErr(81);
			if(condicao != null){
			   operacao.setOperandoD(t.val);
			}
			
		} else if (la.kind == 22) {
		} else SynErr(82);
	}

	void BlocoFatorado() {
		if (la.kind == 3) {
			Get();
			nomeMetodos.add(t.val); 
			Expect(23);
			while (la.kind == 3 || la.kind == 21) {
				if (la.kind == 3) {
					Get();
					nomeMetodos.add(t.val); 
				} else {
					Get();
					if (la.kind == 1) {
						Get();
					} else if (la.kind == 3) {
						Get();
					} else if (la.kind == 2) {
						Get();
					} else SynErr(83);
					Expect(22);
				}
				Expect(23);
			}
		} else if (la.kind == 21) {
			Get();
			if (la.kind == 1) {
				Get();
			} else if (la.kind == 3) {
				Get();
			} else if (la.kind == 2) {
				Get();
			} else SynErr(84);
			Expect(22);
			Expect(23);
			while (la.kind == 3 || la.kind == 21) {
				if (la.kind == 3) {
					Get();
				} else {
					Get();
					if (la.kind == 1) {
						Get();
					} else if (la.kind == 3) {
						Get();
					} else if (la.kind == 2) {
						Get();
					} else SynErr(85);
					Expect(22);
				}
				Expect(23);
			}
		} else SynErr(86);
		if (la.kind == 2) {
			Get();
			nomeProcessoFatora = t.val; 
		} else if (la.kind == 19 || la.kind == 20) {
			ParentesisFatora();
		} else SynErr(87);
	}

	void Guarda() {
		if (la.kind == 2) {
			Get();
			stringRun += ("(" + t.val + ")"); 
		} else if (la.kind == 3) {
			Get();
		} else if (la.kind == 19) {
			CallParentesis();
		} else SynErr(88);
	}

	void CallParentesis() {
		Parentesis();
	}

	void Parentesis() {
		Expect(19);
		if (la.kind == 19) {
			Parentesis();
			while (la.kind == 28 || la.kind == 29) {
				if (la.kind == 28) {
					Get();
				} else {
					Get();
				}
				Parentesis();
			}
		} else if (la.kind == 1 || la.kind == 2 || la.kind == 3) {
			BlocoInterno();
		} else SynErr(89);
		Expect(20);
	}

	void BlocoInterno() {
		Fator();
		while (la.kind == 28 || la.kind == 29) {
			if (la.kind == 28) {
				Get();
			} else {
				Get();
			}
			if (la.kind == 19) {
				Parentesis();
			} else if (la.kind == 1 || la.kind == 2 || la.kind == 3) {
				Fator();
			} else SynErr(90);
		}
	}

	void Fator() {
		if (la.kind == 1) {
			Get();
			stringRun += t.val; 
		} else if (la.kind == 3) {
			Get();
			stringRun += t.val; 
		} else if (la.kind == 2) {
			Get();
			stringRun += t.val; 
		} else SynErr(91);
		if(dadosRepeticao != null){
		   if(dadosRepeticao.getLimiteInferior() != null){
		       condicao = new Condicao();
		       condicao.setOperandoE(t.val);
		   }
		}
		
		if (StartOf(9)) {
			if (StartOf(1)) {
				Operacao();
				stringRun += t.val; 
			} else {
				IgualdadeDesig();
				stringRun += t.val;
				if(condicao != null ) {condicao.setComparador(t.val);}
				
			}
			if (la.kind == 1) {
				Get();
				stringRun += t.val; 
			} else if (la.kind == 3) {
				Get();
				stringRun += t.val; 
			} else if (la.kind == 2) {
				Get();
				stringRun += t.val; 
			} else SynErr(92);
			if(dadosRepeticao != null){
			   if(condicao != null ) {
			       condicao.setOperandoD(t.val);
			       /*Cria-se agora pois hÃ¡ indicaÃ§Ã£o de loop e uma condicao com when*/
			       condicaoOperacao = new CondicaoOperacao();
			       condicaoOperacao.setCondicao(condicao);
			   }
			}
			
		}
	}

	void Condicao() {
		Expect(19);
		stringRun += t.val; 
		if (la.kind == 1) {
			Get();
			stringRun += t.val; 
		} else if (la.kind == 3) {
			Get();
			stringRun += t.val; 
		} else if (la.kind == 2) {
			Get();
			stringRun += t.val; 
		} else SynErr(93);
		if (StartOf(9)) {
			if (StartOf(1)) {
				Operacao();
				stringRun += t.val; 
			} else {
				IgualdadeDesig();
				stringRun += t.val; 
			}
			if (la.kind == 1) {
				Get();
				stringRun += t.val; 
			} else if (la.kind == 3) {
				Get();
				stringRun += t.val; 
			} else if (la.kind == 2) {
				Get();
				stringRun += t.val; 
			} else SynErr(94);
		}
		while (la.kind == 28 || la.kind == 29) {
			if (la.kind == 29) {
				Get();
			} else {
				Get();
			}
			if (la.kind == 1) {
				Get();
				stringRun += t.val; 
			} else if (la.kind == 3) {
				Get();
				stringRun += t.val; 
			} else if (la.kind == 2) {
				Get();
				stringRun += t.val; 
			} else SynErr(95);
			if (StartOf(9)) {
				if (StartOf(1)) {
					Operacao();
					stringRun += t.val; 
				} else {
					IgualdadeDesig();
					stringRun += t.val; 
				}
				if (la.kind == 1) {
					Get();
					stringRun += t.val; 
				} else if (la.kind == 3) {
					Get();
					stringRun += t.val; 
				} else if (la.kind == 2) {
					Get();
					stringRun += t.val; 
				} else SynErr(96);
			}
		}
		Expect(20);
		stringRun += t.val; 
	}

	String  Rotulo() {
		String  stringSaida;
		stringSaida = ""; 
		dadosRepeticaoParalela = new DadosRepeticaoParalela(); 
		
		if (la.kind == 3) {
			Get();
			stringSaida += t.val; 
			dadosRepeticaoParalela.setObjNomeAcao(t.val);  
			
			ComposicaoParametrizadaTipoII();
			if (la.kind == 24) {
				Get();
			} else if (la.kind == 32) {
				Get();
			} else SynErr(97);
		} else if (la.kind == 35) {
			ComposicaoParametrizadaTipoI();
		} else if (la.kind == 2) {
		} else SynErr(98);
		return stringSaida;
	}

	void ComposicaoParametrizadaTipoII() {
		if (la.kind == 21) {
			Get();
			Expect(3);
			if(dadosRepeticaoParalela != null){
			   if(procParam != null){
			       dadosRepeticaoParalela.setProcParam(procParam);
			   }
			   dadosRepeticaoParalela.setIndice(t.val);
			} 
			
			Expect(24);
			if (la.kind == 3) {
				Get();
			} else if (la.kind == 2) {
				Get();
				dadosRepeticaoParalela.setLimiteInferior(t.val); 
			} else if (la.kind == 1) {
				Get();
				dadosRepeticaoParalela.setLimiteInferior(t.val); 
			} else SynErr(99);
			Expect(8);
			if (la.kind == 2) {
				Get();
				dadosRepeticaoParalela.setLimiteSuperior(t.val); 
			} else if (la.kind == 1) {
				Get();
				dadosRepeticaoParalela.setLimiteSuperior(t.val); 
			} else SynErr(100);
			Expect(22);
		} else if (la.kind == 24 || la.kind == 32) {
		} else SynErr(101);
	}

	void ComposicaoParametrizadaTipoI() {
		Expect(35);
		if(dadosRepeticaoParalela != null){
		   dadosRepeticaoParalela.setProcParam(procParam);                                        
		} 
		
		Expect(21);
		Expect(3);
		if(dadosRepeticaoParalela != null){
		   dadosRepeticaoParalela.setIndice(t.val);
		}
		
		Expect(24);
		if (la.kind == 3) {
			Get();
		} else if (la.kind == 2) {
			Get();
			dadosRepeticaoParalela.setLimiteInferior(t.val); 
		} else if (la.kind == 1) {
			Get();
			dadosRepeticaoParalela.setLimiteInferior(t.val); 
		} else SynErr(102);
		Expect(8);
		if (la.kind == 2) {
			Get();
			dadosRepeticaoParalela.setLimiteSuperior(t.val); 
		} else if (la.kind == 1) {
			Get();
			dadosRepeticaoParalela.setLimiteSuperior(t.val); 
		} else SynErr(103);
		Expect(22);
		Expect(3);
		dadosRepeticaoParalela.setObjNomeAcao(t.val); 
		Expect(21);
		Expect(3);
		dadosRepeticaoParalela.setIndiceObjRepeticao(t.val);
		Expect(22);
		if (la.kind == 24) {
			Get();
		} else if (la.kind == 32) {
			Get();
		} else SynErr(104);
	}

	void RenomearRotulo() {
		if (la.kind == 12) {
			String aux = ""; 
			Get();
			Expect(30);
			Expect(3);
			aux += t.val; 
			Expect(12);
			Expect(3);
			relabelling.add(new Relabelling(aux, t.val)); 
			while (la.kind == 25) {
				Get();
				Expect(3);
				aux = t.val; 
				Expect(12);
				Expect(3);
				relabelling.add(new Relabelling(aux, t.val));  
			}
			Expect(31);
		} else if (la.kind == 4) {
		} else SynErr(105);
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Fsp3();
		Expect(0);

	}

	private static final boolean[][] set = {
		{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,x,x,x, x,x,x,x, x,T,T,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,x,T,x, x,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x},
		{x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,x,x,x, x,T,T,x, x,x,x,x, x,T,T,x, x,x},
		{x,x,x,x, T,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,T, x,T,T,x, x,x,x,x, x,T,T,x, x,x},
		{x,x,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,T,x,x, x,x,x,T, x,x,T,x, x,x,x,x, x,x},
		{x,x,T,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,x,x,x, x,T,T,x, x,x,x,x, x,T,T,x, x,x},
		{x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,T,T,x, x,x,x,x, x,T,T,x, x,x},
		{x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, x,T,x,x, x,x,x,x, x,T,T,x, x,x},
		{x,x,x,x, x,x,x,x, x,T,T,T, T,T,T,T, T,T,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "numero expected"; break;
			case 2: s = "nomeVar expected"; break;
			case 3: s = "nomeMin expected"; break;
			case 4: s = "\".\" expected"; break;
			case 5: s = "\"const\" expected"; break;
			case 6: s = "\"=\" expected"; break;
			case 7: s = "\"range\" expected"; break;
			case 8: s = "\"..\" expected"; break;
			case 9: s = "\"+\" expected"; break;
			case 10: s = "\"-\" expected"; break;
			case 11: s = "\"*\" expected"; break;
			case 12: s = "\"/\" expected"; break;
			case 13: s = "\"==\" expected"; break;
			case 14: s = "\">\" expected"; break;
			case 15: s = "\"<\" expected"; break;
			case 16: s = "\">=\" expected"; break;
			case 17: s = "\"<=\" expected"; break;
			case 18: s = "\"!=\" expected"; break;
			case 19: s = "\"(\" expected"; break;
			case 20: s = "\")\" expected"; break;
			case 21: s = "\"[\" expected"; break;
			case 22: s = "\"]\" expected"; break;
			case 23: s = "\"->\" expected"; break;
			case 24: s = "\":\" expected"; break;
			case 25: s = "\",\" expected"; break;
			case 26: s = "\"|\" expected"; break;
			case 27: s = "\"when\" expected"; break;
			case 28: s = "\"||\" expected"; break;
			case 29: s = "\"&&\" expected"; break;
			case 30: s = "\"{\" expected"; break;
			case 31: s = "\"}\" expected"; break;
			case 32: s = "\"::\" expected"; break;
			case 33: s = "\"\\\\\" expected"; break;
			case 34: s = "\"@\" expected"; break;
			case 35: s = "\"forall\" expected"; break;
			case 36: s = "??? expected"; break;
			case 37: s = "invalid Definicoes"; break;
			case 38: s = "invalid Processo"; break;
			case 39: s = "invalid ComposicaoParalela"; break;
			case 40: s = "invalid ComposicaoParalela"; break;
			case 41: s = "invalid ComposicaoParalela"; break;
			case 42: s = "invalid Range"; break;
			case 43: s = "invalid Intervalo"; break;
			case 44: s = "invalid Intervalo"; break;
			case 45: s = "invalid Intervalo"; break;
			case 46: s = "invalid Operacao"; break;
			case 47: s = "invalid IgualdadeDesig"; break;
			case 48: s = "invalid Regra"; break;
			case 49: s = "invalid Transicao"; break;
			case 50: s = "invalid Transicao"; break;
			case 51: s = "invalid Transicao"; break;
			case 52: s = "invalid Indice"; break;
			case 53: s = "invalid Indice"; break;
			case 54: s = "invalid Subprocesso"; break;
			case 55: s = "invalid Subprocesso"; break;
			case 56: s = "invalid Subprocesso"; break;
			case 57: s = "invalid ActionHiding"; break;
			case 58: s = "invalid AcaoDeGuarda"; break;
			case 59: s = "invalid AlfabetoExt"; break;
			case 60: s = "invalid AlfabetoExt"; break;
			case 61: s = "invalid AlfabetoExt"; break;
			case 62: s = "invalid RepeteAcao"; break;
			case 63: s = "invalid RepeteAcao"; break;
			case 64: s = "invalid RepeteAcao"; break;
			case 65: s = "invalid ParentesisFatora"; break;
			case 66: s = "invalid Escolha"; break;
			case 67: s = "invalid Escolha"; break;
			case 68: s = "invalid Escolha"; break;
			case 69: s = "invalid Escolha"; break;
			case 70: s = "invalid Escolha"; break;
			case 71: s = "invalid Escolha"; break;
			case 72: s = "invalid Escolha"; break;
			case 73: s = "invalid RepeteAcaoEscolha"; break;
			case 74: s = "invalid RepeteAcaoEscolha"; break;
			case 75: s = "invalid RepeteAcaoEscolha"; break;
			case 76: s = "invalid IndiceTipoI"; break;
			case 77: s = "invalid IndiceTipoI"; break;
			case 78: s = "invalid IndiceTipoI"; break;
			case 79: s = "invalid IndiceTipoII"; break;
			case 80: s = "invalid IndiceTipoII"; break;
			case 81: s = "invalid IndiceTipoII"; break;
			case 82: s = "invalid IndiceTipoII"; break;
			case 83: s = "invalid BlocoFatorado"; break;
			case 84: s = "invalid BlocoFatorado"; break;
			case 85: s = "invalid BlocoFatorado"; break;
			case 86: s = "invalid BlocoFatorado"; break;
			case 87: s = "invalid BlocoFatorado"; break;
			case 88: s = "invalid Guarda"; break;
			case 89: s = "invalid Parentesis"; break;
			case 90: s = "invalid BlocoInterno"; break;
			case 91: s = "invalid Fator"; break;
			case 92: s = "invalid Fator"; break;
			case 93: s = "invalid Condicao"; break;
			case 94: s = "invalid Condicao"; break;
			case 95: s = "invalid Condicao"; break;
			case 96: s = "invalid Condicao"; break;
			case 97: s = "invalid Rotulo"; break;
			case 98: s = "invalid Rotulo"; break;
			case 99: s = "invalid ComposicaoParametrizadaTipoII"; break;
			case 100: s = "invalid ComposicaoParametrizadaTipoII"; break;
			case 101: s = "invalid ComposicaoParametrizadaTipoII"; break;
			case 102: s = "invalid ComposicaoParametrizadaTipoI"; break;
			case 103: s = "invalid ComposicaoParametrizadaTipoI"; break;
			case 104: s = "invalid ComposicaoParametrizadaTipoI"; break;
			case 105: s = "invalid RenomearRotulo"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
