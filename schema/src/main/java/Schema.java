import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Schema {
    private String tableName;
    private Class source;
    private List<Column> columns;

    public Schema(String tableName, Class source, List<Column> columns) {
        this.tableName = tableName;
        this.source = source;
        this.columns = columns;
    }

    public Schema(Class source){
        this.tableName = source.getSimpleName()+"s";
        this.source = source;
        this.columns = getColumns(source);
    }


    public String getTableRow(Column column){
        SqlTypes sqlTypes = new SqlTypes();
        return "%s %s %s".formatted(
                column.getColumnName(),
                sqlTypes.getType(column.getType()),
                column.getModifier()
        );
    }

    public List<Column> columns(){
        return columns;
    }




    public List<Field> getSimple(){
        SqlTypes sqlTypes = new SqlTypes();
        List<Field> fields = new ArrayList<>();
        for (Field f: source.getDeclaredFields()) {
            String type = sqlTypes.getType(f.getType());
            if (type != null){
                fields.add(
                        f
                );
            }
        }
        return fields;
    }
    private List<Column> getColumns(Class clz){
        SqlTypes sqlTypes = new SqlTypes();
        List<Column> columns = new ArrayList<>();
        for (Field f: clz.getDeclaredFields()) {
            String type = sqlTypes.getType(f.getType());
            if (type != null){
                columns.add(
                        new Column(f)
                );
            }
        }
        return columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class getSource() {
        return source;
    }

    public void setSource(Class source) {
        this.source = source;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
