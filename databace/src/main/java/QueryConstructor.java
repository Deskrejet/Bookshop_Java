import java.util.ArrayList;
import java.util.List;

public class QueryConstructor {
    private Schema schema;
    public QueryConstructor(Schema schema) {
        this.schema = schema;
    }
    public String create(){
        StringBuilder ask = new StringBuilder("CREATE TABLE IF NOT EXISTS %s (".formatted(schema.getTableName()));

        for (int i = 0; i < schema.columns().size(); i++) {

            Column column = schema.columns().get(i);
            ask.append(schema.getTableRow(column));
            ask.append(
                    i < schema.columns().size()-1 ? ", ":");"
            );
        }
        return ask.toString();
    }
    public String drop(){
        return "DROP TABLE IF EXISTS %s;".formatted(schema.getTableName());
    }


    public String insert(Object object) throws Exception {
        List<String> rows = new ArrayList<>();
        List<Column> columns = schema.columns();

        rows.add("INSERT INTO %s VALUES (".formatted(schema.getTableName()));

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            String cond = column.getSqlVal(object);
            if(cond.isEmpty())
                return "";

            rows.add(cond);
            rows.add(",");
        }
        rows.remove(rows.size()-1);

        return Support.toString(rows)+");";
    }

    public String selectAll(){
        return "SELECT * FROM %s;".formatted(schema.getTableName());
    }

    public String select(Object object) throws Exception {
        String ask = "SELECT * FROM %s WHERE ".formatted(schema.getTableName());
        ask+= getFullCondition(object, "and");
        ask+= ";";

        return ask;
    }

    public String delete(Object object) throws Exception {
        String ask = "DELETE FROM %s WHERE ".formatted(schema.getTableName());
        ask+= getFullCondition(object, "and");
        ask+= ";";

        return ask;
    }

    public String update(Object condition, Object set) throws Exception {
        String ask = "UPDATE %s SET ".formatted(schema.getTableName());
        ask+= getFullCondition(set, ",");
        ask+=" WHERE ";
        ask+= getFullCondition(condition, "and");

        return ask+";";
    }

    private String getFullCondition(Object object, String separator) throws Exception {
        List<String> rows = new ArrayList<>();
        List<Column> columns = schema.getColumns();

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            String cond = column.getConditionRow(object);
            if(cond.isEmpty())
                continue;
            rows.add(cond);
            rows.add(separator);
        }

        if (rows.size() == 0)
            return "";

        rows.remove(rows.size()-1);

        return Support.toString(rows);
    }

    public Schema getSchema() {
        return schema;
    }
}
