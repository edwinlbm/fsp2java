// import java.lang.reflect.*;

// public class DumpMethods {

//     public void foo() { }

//     public int bar() { return 12; }

//     public String baz() { return ""; }

//     public static void main(String args[])
//     {
//         try {
//             Class c = DumpMethods.class;
//             Method[] m = c.getDeclaredMethods();
//             for (int i = 0; i < m.length; i++)
//             System.out.println(m[i].toString());
//         } catch (Throwable e) {
//             System.err.println(e);
//         }
//     }
// }