
import java.io.Console;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConsoleMain {
    public static void main(String[] args) throws Exception {
        ConnectionContainer con = new ConnectionContainer();

        Map<String, Schema> schemaMap = Map.of(
                "aut", new Schema(Author.class),
                "book", new Schema(Book.class),
                "store", new Schema(BookStore.class),
                "est", new Schema(Estimation.class),
                "loc", new Schema(Location.class)

        );

        for (Schema schema: schemaMap.values()){
            con.getStatement().execute(
                    new QueryConstructor(schema).create()
            );
        }

        ConsoleExecutor console = new ConsoleExecutor(con, schemaMap);
        console.start();


    }
}
