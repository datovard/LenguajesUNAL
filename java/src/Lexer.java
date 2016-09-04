import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by datovard on 4/09/2016.
 */

public class Lexer {
    public static void main( String[] args ) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String e = "";
        while (( e = br.readLine())!=null) {
            StringTokenizer tokens = new StringTokenizer(e);
            while( tokens.hasMoreTokens() ){
                System.out.println(tokens.nextToken());
            }
        }
    }
}
