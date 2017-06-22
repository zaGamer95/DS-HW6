/**
 * Created by zaGamer on 2017-06-06.
 */
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String [] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        BufferedReader sr = new BufferedReader(new FileReader("search.txt"));
        rb_tree rb = new rb_tree();
        int n;

        while (true) {
            String line = br.readLine();
            line = onlyNum(line);
            n = Integer.parseInt(line);
            if (n > 0) rb.insert(n);
            else if (n < 0) {
                n = Math.abs(n);
                rb.delete(n);
            } else if (n == 0) break;
        }

        rb.numbering(rb.root);

        BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));

        while (true) {
            String line = sr.readLine();
            line = onlyNum(line);
            n = Integer.parseInt(line);
            rb_tree.Node t1 = rb.search(rb.root, n);
            if (t1==null) t1 = rb.root;
            if(n==0)break;
            if (t1 != null) {
                if (t1.l == null) out.write("NIL ");
                else out.write(t1.l.val + " ");
                out.write(t1.val);
                if (t1.r == null) out.write(" NIL");
                else out.write(" " + t1.r.val);
            } else {
                int[] s = rb.smallbig(t1,n);
                if (s[0]==0) out.write("NIL ");
                else out.write(s[0]+" ");
                out.write(" NIL ");
                if (s[1]==0) out.write(" NIL");
                else out.write(" " + s[1]);
            }
            out.newLine();
        }
        out.close();
    }

    public static String onlyNum(String str) {
        if ( str == null ) return "";
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < str.length(); i++){
            if( Character.isDigit( str.charAt(i) ) || str.charAt(i) == '-' ) { sb.append( str.charAt(i) ); }
        } return sb.toString();
    }
}