import java.io.*;

public class Compile 
{
	public static void main(String argv[])
	{
		Scanner s = new Scanner(argv[0]);
		Parser p = new Parser(s);
		p.Parse();
		System.out.println("\n----------------------");
		// System.out.println("*Informações da compilação* \n\n\tNumbero de erros: " + p.errors.count);
		if(p.errors.count == 0){
			System.out.println("Compilado com Sucesso!");
		}else{
			System.out.println("Erro de Compilacao!");
		}
		System.out.println("\nNumero de erros: " + p.errors.count);
		System.out.println("----------------------");

		
	}
}
