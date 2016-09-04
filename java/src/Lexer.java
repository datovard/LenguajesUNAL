import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by datovard on 4/09/2016.
 */

public class Lexer {
    public static void main( String[] args ) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, Integer> dic = new HashMap<String, Integer>();

        dic.put( "algoritmo", 1 );
        dic.put( "proceso", 1 );
        dic.put( "finproceso", 1 );
        dic.put( "finalgoritmo", 1 );

        dic.put( "definir", 1 );

        dic.put( "como", 1 );

        dic.put( "real", 1 );
        dic.put( "entero", 1 );
        dic.put( "numerico", 1 );
        dic.put( "logico", 1 );

        dic.put( "mientras", 1 );
        dic.put( "hacer", 1 );
        dic.put( "finmientras", 1 );

        dic.put( "si", 1 );
        dic.put( "finsi", 1 );

        dic.put( "~", 2 );
        dic.put( "no", 2 );
        dic.put( "=", 3 );
        dic.put( "<-", 4 );
        dic.put( "<>", 5 );
        dic.put( "<", 6 );
        dic.put( ">", 7 );
        dic.put( "<=", 8 );
        dic.put( ">=", 9 );
        dic.put( "+", 10 );
        dic.put( "-", 11 );
        dic.put( "/", 12 );
        dic.put( "*", 13 );
        dic.put( "%", 14 );
        dic.put( "mod", 14 );
        dic.put( ";", 15 );
        dic.put( ":", 16 );
        dic.put( "(", 17 );
        dic.put( ")", 18 );
        dic.put( "[", 19 );
        dic.put( "]", 20 );
        dic.put( "|", 21 );
        dic.put( "o", 21 );
        dic.put( "&", 22 );
        dic.put( "y", 22 );
        dic.put( ",", 23 );
        dic.put( "^", 24 );

        String e = "", t = "";
        int row_counter = 1;
        while (( e = br.readLine())!=null) {
            StringTokenizer tokenizr = new StringTokenizer(e);
            while( tokenizr.hasMoreTokens() ){
                ArrayList<String> token = new ArrayList<String>();
                t = tokenizr.nextToken();
                if( t.indexOf(";") == t.length()-1 ){
                    token.add( t.substring(0, t.length()-1) );
                    token.add( ";" );
                }else
                    token.add(t);
                for( int i = 0; i < token.size(); i++ ) {
                    if (dic.containsKey(token.get(i).toLowerCase())) {
                        switch (dic.get(token.get(i).toLowerCase())) {
                            case 1:
                                System.out.println("<" + token.get(i).toLowerCase() + "," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 2:
                                System.out.println("<token_neg," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 3:
                                System.out.println("<token_igual," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 4:
                                System.out.println("<token_asig," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 5:
                                System.out.println("<token_dif," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 6:
                                System.out.println("<token_menor," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 7:
                                System.out.println("<token_mayor," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 8:
                                System.out.println("<token_menor_igual," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 9:
                                System.out.println("<token_mayor_igual," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 10:
                                System.out.println("<token_mas," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 11:
                                System.out.println("<token_menos," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 12:
                                System.out.println("<token_div," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 13:
                                System.out.println("<token_mul," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 14:
                                System.out.println("<token_mod," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 15:
                                System.out.println("<token_pyc," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 16:
                                System.out.println("<token_dosp," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 17:
                                System.out.println("<token_par_izq," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 18:
                                System.out.println("<token_par_der," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 19:
                                System.out.println("<token_cor_izq," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 20:
                                System.out.println("<token_cor_der," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 21:
                                System.out.println("<token_o," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 22:
                                System.out.println("<token_y," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 23:
                                System.out.println("<token_coma," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                            case 24:
                                System.out.println("<token_pot," + row_counter + "," + (e.indexOf(token.get(i)) + 1) + ">");
                                break;
                        }
                    }
                }
            }
            row_counter++;
        }
    }
}
