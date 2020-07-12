package sommarengine.tool;

public class Console {
    private static StringBuilder builder = new StringBuilder();
    public static void log(Object ...objects) {
        builder.setLength(0);
        for (Object o : objects){
             builder.append((o == null ? "null" : o.toString()) + " ");
        }
        System.out.println(builder.toString());
    }
}
