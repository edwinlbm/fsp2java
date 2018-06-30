// import java.lang.reflect.Method;
// // import java.lang.reflect.Field;

// class Trafficlight{

// 	public void red(){
// 		System.out.println("red");
// 	}
// 	public void orange(){
// 		System.out.println("orange");
// 	}
// 	public void green(){
// 		System.out.println("green");
// 	}
// }

// public class ExemploReflexao {
 
//     public static void main(String args[]) {                 
//         //  Trafficlight obj = new Trafficlight();
//         //  System.out.println(Trafficlight.class.isInstance(obj));
         
//         /*
//          * Carregamos a classe Arquivo através do Class.forName que nos possibilita
//          * carregar uma classe através de uma dada string que deve corresponder ao local
//          * onde a classe está, além disso, por padrão a classe é carregada no ClassLoader
//          * que está sendo utilizado pela classe que está executando o comando.
//          * */
//         Object arquivoFromReflection = null;
//         try {
//             arquivoFromReflection = Class.forName("Trafficlight").newInstance();
//             // System.out.println("OK" + Class.forName("Trafficlight").isInstance("red"));

//         } catch (InstantiationException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         } catch (IllegalAccessException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         } catch (ClassNotFoundException e) {
//             // TODO Auto-generated catch block
//             System.out.println("A classe Trafficlights nao existe.");
//             // e.printStackTrace();
//         }
         
//         //Recupera o nome da classe
//         System.out.println("Nome da Classe: "+arquivoFromReflection.getClass().getName());
 
//         /*
//          * A Classe Method do Reflection nos da a possibilidade de manusear
//          * todos os métodos dentro do objeto carregado 
//          * */
//         System.out.println("");
//         System.out.println("Metodos: ");
//         Method m[] = arquivoFromReflection.getClass().getDeclaredMethods();
//         for(Method method : m){
//             System.out.print(method.getName()+", ");
//         }
         
//         /*
//          * Vamos agora capturar os atributos da classe. Temos agora outra classe 
//          * muito importante para uso do Reflection, a classe Field. Esta nos permite
//          * manusear os campos/fields da nossa classe carregada.
//          * */
         
//         // System.out.println("");
//         // System.out.println("Atributos: ");
//         // for(Field f : arquivoFromReflection.getClass().getDeclaredFields()){
//         //     System.out.print(f.getName()+", ");
//         // }
         
//         /*
//          * Perceba que nossa abordagem é bem simples, ou seja, estamos capturando apenas
//          * os nomes dos métodos e atributos, mas você pode ir muito além, capturando os modificadores, 
//          * tipos, retorno e etc.
//          * */
         
//     }
 
// }