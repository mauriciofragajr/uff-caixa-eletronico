package caixaeletronico.utils;

import java.text.DecimalFormat;

public class StringUtil {
    
    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }
    public static String centavoToReal(int centavos) {
        double real = ((double) centavos)/100;
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(real);
    }
    public static String centavoToReal(String centavosStr) {
        int centavos = Integer.parseInt(centavosStr);
        double real = ((double) centavos)/100;
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(real);
    }
}
