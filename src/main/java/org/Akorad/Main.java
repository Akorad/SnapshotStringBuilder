package org.Akorad;

public class Main {
    public static void main(String[] args) {
        // Пример использования SnapshotStringBuilder
        SnapshotStringBuilder msb = new SnapshotStringBuilder();
        StringBuilder sb = new StringBuilder();

        // append(Object)
        msb.append("Hello");
        msb.append(" World");
        System.out.println("append: " + msb); // Hello World

        sb.append("Hello");
        sb.append(" World");
        System.out.println("StringBuilder append: " + sb); // Hello World

        // insert(int, Object)
        msb.insert(5, "_");
        System.out.println("insert: " + msb); // Hello_ World

        sb.insert(5, "_");
        System.out.println("StringBuilder insert: " + sb); // Hello_ World

        // replace(int, int, String)
        msb.replace(6, 12, "Java");
        System.out.println("replace: " + msb); // Hello_Java

        sb.replace(6, 12, "Java");
        System.out.println("StringBuilder replace: " + sb); // Hello_Java

        // deleteCharAt(int)
        msb.deleteCharAt(5);
        System.out.println("deleteCharAt: " + msb); // HelloJava

        sb.deleteCharAt(5);
        System.out.println("StringBuilder deleteCharAt: " + sb); // HelloJava

        // delete(int, int)
        msb.delete(0, 5);
        System.out.println("delete: " + msb); // Java

        sb.delete(0, 5);
        System.out.println("StringBuilder delete: " + sb); // Java

        // indexOf(String, int)
        int idx1 = msb.indexOf("va", 0);
        System.out.println("indexOf: " + idx1); // 2

        int sbidx1 = sb.indexOf("va", 0);
        System.out.println("indexOf: " + sbidx1); // 2

        // lastIndexOf(String, int)
        msb.append("vaJava");
        int idx2 = msb.lastIndexOf("va", msb.length());
        System.out.println("lastIndexOf: " + idx2); // 6

        sb.append("vaJava");
        int idx2Sb = sb.lastIndexOf("va", sb.length());
        System.out.println("StringBuilder lastIndexOf: " + idx2Sb); //

        // charAt(int)
        char ch = msb.charAt(0);
        System.out.println("charAt: " + ch); // J

        char sbch = sb.charAt(0);
        System.out.println("charAt: " + sbch); // J


        // reverse()
        msb.reverse();
        System.out.println("reverse: " + msb);

        sb.reverse();
        System.out.println("reverse: " + sb);

        // setCharAt(int, char)
        msb.setCharAt(0, 'X');
        System.out.println("setCharAt: " + msb);

        sb.setCharAt(0, 'X');
        System.out.println("setCharAt: " + sb);

        // length()
        int len = msb.length();
        System.out.println("length: " + len);

        int sblen = sb.length();
        System.out.println("length: " + sblen);

        // undo()
        msb.undo(); // отмена setCharAt
        System.out.println("undo1: " + msb);
        msb.undo(); // отмена reverse
        System.out.println("undo2: " + msb);
    }
}