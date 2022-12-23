import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Test {
    static void Procedure(String V1, int V2) {
        for(int i = 1; i <= V2; i++) {
            String S = Integer.toString(i) + " " + V1;
            System.out.println(S);
        }
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String V1 = bufferedReader.readLine();
            int    V2 = Integer.parseInt(bufferedReader.readLine());
            Procedure(V1,V2);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
