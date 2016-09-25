import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Predicter {
    public Map< String, ArrayList<ArrayList<String>> > Gram;

    private Map<String, ArrayList<String>> primeros;
    private Map<String, ArrayList<String>> siguientes;
    private Map<String, ArrayList<ArrayList<String>>> prediccion;

    public String first;
    public static final String EMPTY = "$";
    public static final String END = "?";

    public Predicter(){
        Gram = new HashMap< String, ArrayList<ArrayList<String>> >();
        primeros = new HashMap<String, ArrayList<String>>();
        siguientes = new HashMap<String, ArrayList<String>>();
        prediccion = new HashMap<String, ArrayList<ArrayList<String>>>();
    }

    public void recorrerGramaticaPrimeros(String key){
        ArrayList<ArrayList<String>> put = Gram.get(key);
        ArrayList<String> back, empty;
        empty = new ArrayList<String>();
        empty.add(EMPTY);

        for( int i = 0; i < put.size(); i++ ){

            if( put.get(i).equals( empty ) ){
                if (primeros.containsKey(key)) back = new ArrayList<String>(primeros.get(key));
                else back = new ArrayList<String>();
                back.add(EMPTY);
                primeros.put( key, back );
                break;
            }else {

                for (int j = 0; j < put.get(i).size(); j++) {

                    if ( !Gram.containsKey(put.get(i).get(j))){
                        if (primeros.containsKey(key)) back =new ArrayList<String>( primeros.get(key));
                        else back = new ArrayList<String>();
                        back.add(put.get(i).get(j));
                        primeros.put(key, back);
                        break;
                    } else {

                        recorrerGramaticaPrimeros(put.get(i).get(j));

                        back = new ArrayList<String>(primeros.get(put.get(i).get(j)));
                        if( primeros.containsKey(key) ) back.addAll(primeros.get(key));
                        back.remove(EMPTY);
                        primeros.put(key, back);

                        if (primeros.get(put.get(i).get(j)).contains(EMPTY)) {
                            if (put.get(i).size() - (j+1) == 0) {
                                back = new ArrayList<String>(primeros.get(key));
                                back.add(EMPTY);
                                primeros.put(key, back);
                                break;
                            }else{
                                continue;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<String> recorrerGramaticaPrimerosAux(String key, int regla){
        ArrayList<String> put = Gram.get(key).get(regla);
        ArrayList<String> back, empty;
        empty = new ArrayList<String>();
        empty.add(EMPTY);

        ArrayList<String> pr = new ArrayList<String>();

        if( put.equals( empty ) ){
            pr.add( EMPTY );
        }else {
            for (int j = 0; j < put.size(); j++) {

                if ( !Gram.containsKey(put.get(j))){
                    pr.add(put.get(j));
                    break;
                } else {
                    back = new ArrayList<String>( primeros.get( put.get(j) ) );

                    back.remove(EMPTY);
                    pr.addAll(back);

                    if ( primeros.get( put.get(j)).contains(EMPTY) ) {
                        if (put.size() - (j+1) == 0) {
                            pr.add(EMPTY);
                            break;
                        }else{
                            continue;
                        }
                    }else{
                        break;
                    }
                }
            }
        }
        return pr;
    }


    public void nivelarPrimerosDesconectados(){
        for ( String key : Gram.keySet())
        {
            if( !primeros.containsKey( key ) ){
                recorrerGramaticaPrimeros( key );
            }
        }
        Set<String> hs = new HashSet<>();
        for ( String key : primeros.keySet())
        {
            hs.addAll(primeros.get(key));
            primeros.get(key).clear();
            primeros.get(key).addAll(hs);
            hs.clear();
        }

    }

    public void calcularPrimeros(){
        recorrerGramaticaPrimeros(first);
        nivelarPrimerosDesconectados();
    }

    public void calcularSiguientes(){
        ArrayList<String> put;
        String key;
        ArrayList<ArrayList<String>> reglas;
        int k = 0, count = 0, rect = 0;
        boolean change = true;
        ArrayList<String> empty;
        empty = new ArrayList<String>();
        empty.add(EMPTY);


        for ( String llave : Gram.keySet()) siguientes.put(llave, new ArrayList<String>());

        siguientes.get(first).add(END);

        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : Gram.entrySet())
        {
            reglas = entry.getValue();
            for( int i = 0; i < reglas.size(); i++ ) {
                if (!reglas.get(i).equals(empty)){
                    for (int j = 0; j < reglas.get(i).size() - 1; j++) {
                        if (Gram.containsKey(reglas.get(i).get(j))) {
                            if (!Gram.containsKey(reglas.get(i).get(j + 1))) {
                                if (!siguientes.get(reglas.get(i).get(j)).contains(reglas.get(i).get(j + 1)))
                                    siguientes.get(reglas.get(i).get(j)).add(reglas.get(i).get(j + 1));
                            } else {
                                k = j + 1;
                                do {
                                    if (!Gram.containsKey(reglas.get(i).get(k))) {
                                        if (!siguientes.get(reglas.get(i).get(j)).contains(reglas.get(i).get(k)))
                                            siguientes.get(reglas.get(i).get(j)).add(reglas.get(i).get(k));
                                        break;
                                    } else {
                                        if (primeros.get(reglas.get(i).get(k)).contains(EMPTY)) {
                                            put = new ArrayList<String>(primeros.get(reglas.get(i).get(k)));
                                            put.remove(EMPTY);
                                            saveSiguientes(reglas.get(i).get(j), put);
                                        } else {
                                            saveSiguientes(reglas.get(i).get(j), primeros.get(reglas.get(i).get(k)));
                                            break;
                                        }
                                    }
                                    k++;
                                } while (k < reglas.get(i).size());
                            }
                        }
                    }
                }
            }
        }
        change = true;
        while(change) {
            change = false;
            for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : Gram.entrySet()) {
                key = entry.getKey();
                reglas = entry.getValue();
                for (int i = 0; i < reglas.size(); i++) {
                    if( !reglas.get(i).equals(empty) ) {
                        for (int j = reglas.get(i).size() - 1; j >= 0 && primeros.containsKey(reglas.get(i).get(j)); j--) {
                            count = siguientes.get(reglas.get(i).get(j)).size();
                            saveSiguientes(reglas.get(i).get(j), siguientes.get(key));
                            rect = siguientes.get(reglas.get(i).get(j)).size();
                            if( count != rect ) {
                                change = true;
                            }
                            if (!(primeros.get(reglas.get(i).get(j)).contains(EMPTY)))
                                break;
                        }
                    }
                }
            }
        }
    }

    public void calcularPrediccion(){
        ArrayList<String> pred, primer;
        for (String key : Gram.keySet())
        {
            if( !prediccion.containsKey(key) )
                prediccion.put(key, new ArrayList<ArrayList<String>>() );
            for( int i = 0; i < Gram.get(key).size(); i++ ){
                primer = recorrerGramaticaPrimerosAux( key, i );
                if( primer.contains(EMPTY) ){
                    primer.remove(EMPTY);
                    primer.addAll( siguientes.get(key) );
                }
                prediccion.get(key).add(primer);
            }
        }
    }

    public void saveSiguientes( String key, ArrayList<String> put ){
        for( int a = 0; a < put.size(); a++ ){
            if( !siguientes.get(key).contains( put.get(a) )){
                siguientes.get(key).add( put.get(a) );
            }
        }
    }

    public void imprimirPrimeros(){
        ArrayList<String> put;
        for (Map.Entry<String, ArrayList<String>> entry : primeros.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
    }
    public void imprimirSiguientes(){
        ArrayList<String> put;
        for (Map.Entry<String, ArrayList<String>> entry : siguientes.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
    }
    public void imprimirPrediccion(){
        ArrayList<ArrayList<String>> put;
        String key;
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : prediccion.entrySet())
        {
            key = entry.getKey();
            for( int j = 0; j < Gram.get(key).size(); j++ ){
                System.out.println( key+" -> "+Gram.get(key).get(j) +" Primeros: "+prediccion.get( key).get( j ) + " " );
            }
        }
    }

    public void imprimirPrimerosPorRegla(){
        ArrayList<ArrayList<String>> put;
        String key;
        for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : Gram.entrySet())
        {
            key = entry.getKey();
                for( int j = 0; j < Gram.get(key).size(); j++ ){
                    System.out.println( key+" -> "+Gram.get(key).get(j) +" Primeros: "+recorrerGramaticaPrimerosAux( key, j ) + " " );
                }
        }
    }

    public void crearArchivo(){

    }

    public static  void main( String[] args ) throws IOException{
        Predicter pred = new Predicter();
        Lexer lex = new Lexer();
        boolean f = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String e = "", izq;
        String[] exp;
        String[] der;
        ArrayList<ArrayList<String>> put;
        while ( ( e = br.readLine()) != null ) {
            if( e.equals( "exit" ) ) break;
            exp = e.split(" -> ");
            izq = exp[0];
            if( !f ){
                f = !f;
                pred.first = izq;
            }
            der = exp[1].split(" ");
            if ( pred.Gram.containsKey(izq) ) {
                put = pred.Gram.get(izq);
                put.add( new ArrayList<String>(Arrays.asList(der)));
                pred.Gram.put(izq, put);
            } else {
                put = new ArrayList<ArrayList<String>>();
                put.add(new ArrayList<String>(Arrays.asList(der)));
                pred.Gram.put(izq, put);
            }
        }
        pred.calcularPrimeros();
        pred.imprimirPrimeros();
        System.out.println();
        pred.calcularSiguientes();
        pred.imprimirSiguientes();

        System.out.println();
        pred.imprimirPrimerosPorRegla();

        System.out.println();
        pred.calcularPrediccion();
        pred.imprimirPrediccion();

    }
}