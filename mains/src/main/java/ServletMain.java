import java.util.List;
import java.util.Map;

public class ServletMain {
   public static void main(String[] args) {
       ConnectionContainer con = new ConnectionContainer();

       Map<String, Schema> schemaMap = Map.of(
               "aut", new Schema(Author.class),
               "book", new Schema(Book.class),
               "store", new Schema(BookStore.class),
               "est", new Schema(Estimation.class),
               "loc", new Schema(Location.class)
       );

        CustomServletsContainer customServletsContainer = new CustomServletsContainer(con, schemaMap);
        customServletsContainer.start();

    }
}
