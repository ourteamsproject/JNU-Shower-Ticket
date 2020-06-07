package com.sevenpounds.shower.util;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";

    public StringUtils() {
    }

    public static byte[] getBytes(String str) {
        if (str != null) {
            try {
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var2) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }

    public static boolean inString(String str, String... strs) {
        if (str != null) {
            String[] var2 = strs;
            int var3 = strs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        } else {
            String regEx = "<.+?>";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(html);
            String s = m.replaceAll("");
            return s;
        }
    }

    public static String replaceMobileHtml(String html) {
        return html == null ? "" : html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
    }

    public static Double toDouble(Object val) {
        if (val == null) {
            return 0.0D;
        } else {
            try {
                return Double.valueOf(trim(val.toString()));
            } catch (Exception var2) {
                return 0.0D;
            }
        }
    }

    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        } else {
            s = s.toLowerCase();
            StringBuilder sb = new StringBuilder(s.length());
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        } else {
            s = toCamelCase(s);
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }
    }

    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean upperCase = false;

            for(int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                boolean nextUpperCase = true;
                if (i < s.length() - 1) {
                    nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
                }

                if (i > 0 && Character.isUpperCase(c)) {
                    if (!upperCase || !nextUpperCase) {
                        sb.append('_');
                    }

                    upperCase = true;
                } else {
                    upperCase = false;
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static void setValueIfNotBlank(String target, String source) {
        if (isNotBlank(source)) {
            ;
        }

    }

    public static String jsGetVal(String objectString) {
        StringBuilder result = new StringBuilder();
        StringBuilder val = new StringBuilder();
        String[] vals = split(objectString, ".");

        for(int i = 0; i < vals.length; ++i) {
            val.append("." + vals[i]);
            result.append("!" + val.substring(1) + "?'':");
        }

        result.append(val.substring(1));
        return result.toString();
    }

    public static String toStringFromList(List<String> list, String symbol) {
        String result = "";
        if (null != list && list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
                result = result + (String)list.get(i);
                if (i < list.size() - 1) {
                    result = result + symbol;
                }
            }
        }

        return result;
    }

    private static String replaceSpecChar(final String str) {
        return str.replace("　", "").replace(" ", "").replace(" ", "");
    }

    public static String normalizeRawText(String str) {
        if (str == null) {
            str = "";
        }

        str = str.replaceAll("<[^>]+>", " ").replaceAll("&nbsp;", " ").replaceAll("\\s+", " ").trim();
        boolean var1 = false;

        int length;
        do {
            length = str.length();
            str = replaceSpecChar(str);
        } while(length != str.length());

        return str.trim();
    }

    public static String parseRegex(final String str, final String regex) {
        return parseRegex(str, regex, 1);
    }

    public static String parseRegex(final String str, final String regex, final int index) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find() ? m.group(index) : null;
    }

    public static int parseRegexInt(final String str, final String regex) {
        String aInt = parseRegex(str, regex);
        return notEmpty(aInt) ? Integer.parseInt(aInt) : 0;
    }

    public static boolean notEmpty(final String str) {
        return !isEmpty(str);
    }

    public static Map<String, Integer> getCabinFees(String str) {
        Map<String, Integer> cabinFees = new HashMap();
        if (isEmpty(str)) {
            return cabinFees;
        } else {
            String pattern = "\\[(.*?)\\]";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(str);

            while(true) {
                String[] findValueArray;
                String cabins;
                Integer fee;
                do {
                    do {
                        String findValue;
                        do {
                            if (!m.find()) {
                                return cabinFees;
                            }

                            findValue = m.group(1);
                        } while(!findValue.contains(":"));

                        findValueArray = findValue.split(":");
                    } while(findValueArray.length < 2);

                    cabins = findValueArray[0];
                    fee = 0;
                } while(!isNumeric(findValueArray[1]));

                fee = Integer.valueOf(findValueArray[1]);
                String[] var9 = cabins.split("/");
                int var10 = var9.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    String cabin = var9[var11];
                    if (!isEmpty(cabin)) {
                        cabinFees.put(cabin, fee);
                    }
                }
            }
        }
    }

    public static String assemblySplitStrBuilder(String split, String... stringArray) {
        StringBuilder keyBuilder = new StringBuilder();
        boolean isFalse = false;
        String[] var4 = stringArray;
        int var5 = stringArray.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String value = var4[var6];
            if (isFalse) {
                keyBuilder.append(split);
            }

            isFalse = true;
            keyBuilder.append(value);
        }

        return keyBuilder.toString();
    }

    public static String assemblySplitStrBuffer(String split, String... stringArray) {
        StringBuffer keyBuffer = new StringBuffer();
        boolean isFalse = false;
        String[] var4 = stringArray;
        int var5 = stringArray.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String value = var4[var6];
            if (isFalse) {
                keyBuffer.append(split);
            }

            isFalse = true;
            keyBuffer.append(value);
        }

        return keyBuffer.toString();
    }

    public static String assemblyStrBuffer(Object... stringArray) {
        StringBuffer keyBuffer = new StringBuffer();
        Object[] var2 = stringArray;
        int var3 = stringArray.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object value = var2[var4];
            keyBuffer.append(value);
        }

        return keyBuffer.toString();
    }

    public static String subStrNum(String str, int n) {
        if (isEmpty(str)) {
            return "";
        } else if (str.length() <= n) {
            return str;
        } else {
            String substring = str.substring(str.length() - n);
            return substring;
        }
    }
}
