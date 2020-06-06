package ec.edu.espe.ajjacome2.distancecalculator.util;

/**
 *
 * @author jon_m
 */
public class StringUtils {
    public static String repeat(String sequence, int times) {
        return new String(new char[times]).replace("\0", sequence);
    }
}
