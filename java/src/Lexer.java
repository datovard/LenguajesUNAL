import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Lexer {
    private Map<String, Integer> dic;
    private Map<String, String> dic_token;

    public Lexer(){
        dic = new HashMap<String, Integer>();
        dic_token = new HashMap<String, String>();
        dic.put( "algoritmo", 1 );
        dic.put( "cadena", 1 );
        dic.put( "proceso", 1 );
        dic.put( "subproceso", 1 );
        dic.put( "finsubproceso", 1 );
        dic.put( "finproceso", 1 );
        dic.put( "finalgoritmo", 1 );
        dic.put( "definir", 1 );
        dic.put( "como", 1 );
        dic.put( "real", 1 );
        dic.put( "entero", 1 );
        dic.put( "numerico", 1 );
        dic.put( "numero", 1 );
        dic.put( "logico", 1 );
        dic.put( "caracter", 1 );
        dic.put( "mientras", 1 );
        dic.put( "hacer", 1 );
        dic.put( "finmientras", 1 );
        dic.put( "si", 1 );
        dic.put( "finsi", 1 );
        dic.put( "para", 1 );
        dic.put( "hasta", 1 );
        dic.put( "con", 1 );
        dic.put( "paso", 1 );
        dic.put( "leer", 1 );
        dic.put( "finpara", 1 );
        dic.put( "escribir", 1 );
        dic.put( "borrar", 1 );
        dic.put( "pantalla", 1 );
        dic.put( "tecla", 1 );
        dic.put( "esperar", 1 );
        dic.put( "segundos", 1 );
        dic.put( "milisegundos", 1 );
        dic.put( "si", 1 );
        dic.put( "entonces", 1 );
        dic.put( "sino", 1 );
        dic.put( "funcion", 1 );
        dic.put( "finfuncion", 1 );
        dic.put( "finsi", 1 );
        dic.put( "segun", 1 );
        dic.put( "hacer", 1 );
        dic.put( "de", 1 );
        dic.put( "otro", 1 );
        dic.put( "modo", 1 );
        dic.put( "finsegun", 1 );
        dic.put( "repetir", 1 );
        dic.put( "hasta", 1 );
        dic.put( "que", 1 );
        dic.put( "verdadero", 1 );
        dic.put( "falso", 1 );
        dic.put( "dimension", 1 );
        dic.put( "limpiar", 1 );
        dic.put( "texto", 1 );


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



        dic_token.put( "~", "token_neg" );
        dic_token.put( "no", "token_neg" );
        dic_token.put( "=",  "token_igual" );
        dic_token.put( "<-", "token_asig");
        dic_token.put( "<>", "token_dif" );
        dic_token.put( "<", "token_menor" );
        dic_token.put( ">", "token_mayor" );
        dic_token.put( "<=", "token_menor_igual" );
        dic_token.put( ">=", "token_mayor_igual" );
        dic_token.put( "+", "token_mas" );
        dic_token.put( "-", "token_menos" );
        dic_token.put( "/", "token_div" );
        dic_token.put( "*", "token_mul" );
        dic_token.put( "%", "token_mod" );
        dic_token.put( "mod", "token_mod" );
        dic_token.put( ";", "token_pyc" );
        dic_token.put( ":", "token_dosp" );
        dic_token.put( "(", "token_par_izq" );
        dic_token.put( ")", "token_par_der" );
        dic_token.put( "[", "token_cor_izq" );
        dic_token.put( "]", "token_cor_der" );
        dic_token.put( "|", "token_o" );
        dic_token.put( "o", "token_o" );
        dic_token.put( "&", "token_y" );
        dic_token.put( "y", "token_y" );
        dic_token.put( ",", "token_coma" );
        dic_token.put( "^", "token_pot" );
    }

    public boolean isNumber(char number){
        return ((int) number >= (int)'0' && (int) number <= (int)'9');
    }

    public boolean isAlphabetic(char letter){
        return (((int) letter >= (int)'A' && (int) letter <= (int)'Z') || ((int) letter >= (int)'a' && (int) letter <= (int)'z'));
    }

    public ArrayList<String> analyseLine(String e ){
        ArrayList<String> resp = new ArrayList<String>();
        String t = "";
        boolean error = false;

        int state = 0, nextState = 0, row_counter = 1, tokenStart = 0;
        e = e + "\0";
        state = 0; nextState = 0; tokenStart = 0;

        for(int j = 0; j < e.length() && !error; j++) {
            switch (state){
                case -1:
                    break;
                case 0:
                    if(e.charAt(j) == ' ' || e.charAt(j) == '\t')
                        nextState = 0;
                    else if (isAlphabetic(e.charAt(j))) {
                        nextState = 2;
                        tokenStart = j;
                    }else if (isNumber(e.charAt(j))) {
                        nextState = 4;
                        tokenStart = j;
                    }else if (e.charAt(j) == '\'' || e.charAt(j) == '\"') {
                        nextState = 1;
                        tokenStart = j + 1;
                    }else if (e.charAt(j) == '/') {
                        nextState = 11;
                        tokenStart = j;
                    }else if ( e.charAt(j) == '<' ){
                        nextState = 17;
                        tokenStart = j;
                    }else if( e.charAt(j) == '>' ){
                        nextState = 18;
                        tokenStart = j;
                    }else if (  dic.containsKey(e.substring(j,j+1)) && dic.get(e.substring(j, j+1)) != 1 ) {
                        nextState = 13;
                        tokenStart = j;
                    }else if ( e.charAt(j) == '\0' )
                        nextState = -1;
                    else{
                        nextState = 8;
                        j--;
                    }
                    break;
                case 1:
                    if( e.charAt(j) != '\'' &&  e.charAt(j) != '\"' )
                        nextState = 1;
                    else{
                        resp.add("<token_cadena," + e.substring(tokenStart, j) + "," + row_counter + "," + (tokenStart) + ">");
                        nextState = 0;
                    }
                    if (  j == e.length()-1  ) {
                        nextState = 9;
                        tokenStart--;
                        j--;
                    }
                    break;

                case 2:
                    if(isAlphabetic(e.charAt(j)) ||isNumber(e.charAt(j)) || e.charAt(j) == '_')
                        nextState = 2;
                    else {
                        nextState = 3;
                        j--;
                    }
                    break;
                case 3:
                    t = e.substring(tokenStart, j).toLowerCase();
                    if ( dic.containsKey(t) ) {
                        if( dic.get(t) != 1 )
                            resp.add("<" + dic_token.get(t) + "," + row_counter + "," + (tokenStart + 1) + ">");
                        else
                            resp.add("<" + t + "," + row_counter + "," + (tokenStart + 1) + ">");
                    }else
                        resp.add("<id," + t + "," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 4:
                    if( isNumber(e.charAt(j)) )
                        nextState = 4;
                    else if( e.charAt(j) == '.' )
                        nextState = 6;
                    else if ( isAlphabetic(e.charAt(j)) ){
                        nextState = 9;
                        j--;
                    }else{
                        nextState = 5;
                        j--;
                    }
                    break;
                case 5:
                    resp.add("<token_entero," + e.substring(tokenStart,j) + "," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 6:
                    if( isNumber(e.charAt(j)) )
                        nextState = 7;
                    else{
                        nextState = 5;
                        j-=2;
                    }
                    break;
                case 7:
                    if( isNumber(e.charAt(j)) )
                        nextState = 7;
                    else {
                        resp.add("<token_real," + e.substring(tokenStart, j) + "," + row_counter + "," + (tokenStart + 1) + ">");
                        j--;
                        nextState = 0;
                    }
                    break;

                case 8:
                    resp.add(">>> Error lexico (linea: " + row_counter + ", posicion: " + (j + 1) + ")");
                    error = true;
                    break;
                case 9:
                    resp.add(">>> Error lexico (linea: " + row_counter + ", posicion: " + (tokenStart + 1) + ")");
                    error = true;
                    break;

                case 11:
                    if(e.charAt(j) == '/')
                        nextState = 15;
                    else{
                        nextState = 16;
                        j--;
                    }
                    break;
                case 13:
                    t = e.substring(tokenStart, j);
                    resp.add("<" + dic_token.get(t) + "," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 15:
                    nextState = 15;
                    break;
                case 16:
                    resp.add("<token_div," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 17:
                    if( e.charAt(j) == '-' )
                        nextState = 19;
                    else if( e.charAt(j) == '=')
                        nextState = 20;
                    else if( e.charAt(j) == '>')
                        nextState = 21;
                    else{
                        resp.add("<token_menor," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                    }
                    break;
                case 18:
                    if( e.charAt(j) == '=')
                        nextState = 22;
                    else{
                        resp.add("<token_mayor," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                    }
                    break;
                case 19:
                    resp.add("<token_asig," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 20:
                    resp.add("<token_menor_igual," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 21:
                    resp.add("<token_dif," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
                case 22:
                    resp.add("<token_mayor_igual," + row_counter + "," + (tokenStart + 1) + ">");
                    nextState = 0;
                    j--;
                    break;
            }
            state = nextState;
        }
        row_counter++;

        return resp;
    }

    public static void main( String[] args ) throws IOException {
        Lexer check = new Lexer();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String e;
        ArrayList<String> resp;
        while (( e = br.readLine())!=null) {
            resp = check.analyseLine(e);
            for( String lel : resp ){
                System.out.println( lel );
            }
            /*e = e + "\0";
            state = 0; nextState = 0; tokenStart = 0;
            for(int j = 0; j < e.length() && !error; j++) {
                switch (state){
                    case -1:
                        break;
                    case 0:
                        if(e.charAt(j) == ' ' || e.charAt(j) == '\t')
                            nextState = 0;
                        else if (check.isAlphabetic(e.charAt(j))) {
                            nextState = 2;
                            tokenStart = j;
                        }else if (check.isNumber(e.charAt(j))) {
                            nextState = 4;
                            tokenStart = j;
                        }else if (e.charAt(j) == '\'' || e.charAt(j) == '\"') {
                            nextState = 1;
                            tokenStart = j + 1;
                        }else if (e.charAt(j) == '/') {
                            nextState = 11;
                            tokenStart = j;
                        }else if ( e.charAt(j) == '<' ){
                            nextState = 17;
                            tokenStart = j;
                        }else if( e.charAt(j) == '>' ){
                            nextState = 18;
                            tokenStart = j;
                        }else if (  dic.containsKey(e.substring(j,j+1)) && dic.get(e.substring(j, j+1)) != 1 ) {
                            nextState = 13;
                            tokenStart = j;
                        }else if ( e.charAt(j) == '\0' )
                            nextState = -1;
                        else{
                            nextState = 8;
                            j--;
                        }
                        break;
                    case 1:
                        if( e.charAt(j) != '\'' &&  e.charAt(j) != '\"' )
                            nextState = 1;
                        else{
                            System.out.println("<token_cadena," + e.substring(tokenStart, j) + "," + row_counter + "," + (tokenStart) + ">");
                            nextState = 0;
                        }
                        if (  j == e.length()-1  ) {
                            nextState = 9;
                            tokenStart--;
                            j--;
                        }
                        break;

                    case 2:
                        if(check.isAlphabetic(e.charAt(j)) || check.isNumber(e.charAt(j)) || e.charAt(j) == '_')
                            nextState = 2;
                        else {
                            nextState = 3;
                            j--;
                        }
                        break;
                    case 3:
                        t = e.substring(tokenStart, j).toLowerCase();
                        if ( dic.containsKey(t) ) {
                            if( dic.get(t) != 1 )
                                System.out.println("<" + dic_token.get(t) + "," + row_counter + "," + (tokenStart + 1) + ">");
                            else
                                System.out.println("<" + t + "," + row_counter + "," + (tokenStart + 1) + ">");
                        }else
                            System.out.println("<id," + t + "," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 4:
                        if( check.isNumber(e.charAt(j)) )
                            nextState = 4;
                        else if( e.charAt(j) == '.' )
                            nextState = 6;
                        else if ( check.isAlphabetic(e.charAt(j)) ){
                            nextState = 9;
                            j--;
                        }else{
                            nextState = 5;
                            j--;
                        }
                        break;
                    case 5:
                        System.out.println("<token_entero," + e.substring(tokenStart,j) + "," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 6:
                        if( check.isNumber(e.charAt(j)) )
                            nextState = 7;
                        else{
                            nextState = 5;
                            j-=2;
                        }
                        break;
                    case 7:
                        if( check.isNumber(e.charAt(j)) )
                            nextState = 7;
                        else {
                            System.out.println("<token_real," + e.substring(tokenStart, j) + "," + row_counter + "," + (tokenStart + 1) + ">");
                            j--;
                            nextState = 0;
                        }
                        break;

                    case 8:
                        System.out.println(">>> Error lexico (linea: " + row_counter + ", posicion: " + (j + 1) + ")");
                        error = true;
                        break;
                    case 9:
                        System.out.println(">>> Error lexico (linea: " + row_counter + ", posicion: " + (tokenStart + 1) + ")");
                        error = true;
                        break;

                    case 11:
                        if(e.charAt(j) == '/')
                            nextState = 15;
                        else{
                            nextState = 16;
                            j--;
                        }
                        break;
                    case 13:
                        t = e.substring(tokenStart, j);
                        System.out.println("<" + dic_token.get(t) + "," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 15:
                        nextState = 15;
                        break;
                    case 16:
                        System.out.println("<token_div," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 17:
                        if( e.charAt(j) == '-' )
                            nextState = 19;
                        else if( e.charAt(j) == '=')
                            nextState = 20;
                        else if( e.charAt(j) == '>')
                            nextState = 21;
                        else{
                            System.out.println("<token_menor," + row_counter + "," + (tokenStart + 1) + ">");
                            nextState = 0;
                            j--;
                        }
                        break;
                    case 18:
                        if( e.charAt(j) == '=')
                            nextState = 22;
                        else{
                            System.out.println("<token_mayor," + row_counter + "," + (tokenStart + 1) + ">");
                            nextState = 0;
                            j--;
                        }
                        break;
                    case 19:
                        System.out.println("<token_asig," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 20:
                        System.out.println("<token_menor_igual," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 21:
                        System.out.println("<token_dif," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                    case 22:
                        System.out.println("<token_mayor_igual," + row_counter + "," + (tokenStart + 1) + ">");
                        nextState = 0;
                        j--;
                        break;
                }
                state = nextState;
            }
            row_counter++;*/
        }
    }
}