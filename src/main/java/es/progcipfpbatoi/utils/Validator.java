package es.progcipfpbatoi.utils;

public class Validator {

    public final static String DATE_REGEXP = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";

    public final static String PRODUCT_CODE_REGEXP = "[b|e|m|p|]\\d{1,10}";

    public final static String ORDER_CODE_REGEXP = "o\\d{1,10}";

    public static boolean isValidateDate(String date) {

        return date.matches(DATE_REGEXP);
    }

    public static boolean isValidProductCode(String productCode) {

        return productCode.matches(PRODUCT_CODE_REGEXP);

    }

    public static boolean isValidOrderCode(String orderCode) {

        return orderCode.matches(ORDER_CODE_REGEXP);

    }

}
