import java.lang.reflect.Field;

public class Column {

    private String columnName;
    private Class type;
    private String modifier;
    public Column(Field f) {
        this.columnName = f.getName();
        this.type = f.getType();
        this.modifier = f.getName().equals("id") ? "PRIMARY KEY" : "";
    }
    public Column(String columnName, Class type, String modifier) {
        this.columnName = columnName;
        this.type = type;
        this.modifier = modifier;
    }

    public Column(String columnName, Class type) {
        this.columnName = columnName;
        this.type = type;
        this.modifier = "";
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getConditionRow(Object object) throws Exception {
        Object value = getVal(object);
        if (value == null)
            return "";

        return "%s = %s".formatted(
                columnName,
                getSqlVal(value)
        );

    }
    public String getSqlVal(Object value) throws Exception {


        return type.equals(String.class)? "\'%s\'".formatted(value.toString()) : value.toString();

    }

    public Object getVal(Object object) throws Exception {
        return object.getClass().getMethod(
                "get"+capitalizeFirstLetter(columnName)
        ).invoke(object);
    }
    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void setFieldValue(Object object, Object value) throws NoSuchFieldException, IllegalAccessException {
        value = autoCast(value);

        Field field = object.getClass().getDeclaredField(columnName);
        field.setAccessible(true);
        field.set(object, value);
    }


    private Object autoCast(Object value){
        if (value.getClass().equals(type))
            return value;
        else if(type.equals(Integer.class) || type.equals(int.class)){
            return Integer.parseInt(value.toString());
        }else if (type.equals(Long.class) || type.equals(long.class)){
            return Long.parseLong(value.toString());
        }
        return null;
    }
}
