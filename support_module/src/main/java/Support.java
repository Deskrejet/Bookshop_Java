import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Support {


    public static String toString(List<String> strings){
        StringBuilder res = new StringBuilder();

        for (String s: strings){
            res.append(" ").append(s).append(" ");
        }

        return res.toString();
    }

    public static String rsToStr(ResultSet rs, Schema schema) throws SQLException {
        String string = "";

        while (rs.next()){
            for (Column c: schema.columns()){
                string+= c.getColumnName()+": "+rs.getObject(c.getColumnName()).toString()+"\n";
            }
            string+="\n";
        }

        return string;

    }
    public static Object readObject(Schema schema) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Object o = schema.getSource().newInstance();
        Scanner scanner = new Scanner(System.in);
        for (Field f: schema.getSimple()) {
            System.out.print(f.getName()+"... ");
            String value = scanner.nextLine().trim();
            if(value.isEmpty())
                continue;
            setValue(o, f.getName(), convertValue(value, f.getType()));
        }
        System.out.println(o.toString());
        return o;
    }

    public static Object convertValue(String val, Class type){
        if (type.equals(Integer.class) || type.equals(int.class))
            return Integer.parseInt(val);
        if (type.equals(Long.class) || type.equals(long.class))
            return Long.parseLong(val);
        return val;
    }

    public static void setValue(Object o, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(o, value);
    }
    public static Object readObject(Schema schema, HttpServletRequest request, String prefix) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        int count = 0;
        Object object = schema.getSource().newInstance();
        for (Column c: schema.getColumns()) {
            String val = request.getParameter(prefix+c.getColumnName());
            if (val == null || val.isEmpty())
                continue;
            c.setFieldValue(object, val);
            count+=1;
        }
        return count!=0? object:null;
    }

    public static Object readObject(Schema schema, HttpServletRequest request) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        return readObject(schema, request, "");
    }
}
