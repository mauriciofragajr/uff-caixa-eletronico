package caixaeletronico.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    public static String dateToString(Date data) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(data);
    }
}
