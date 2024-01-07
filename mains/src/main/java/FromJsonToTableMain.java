import java.util.List;
import java.util.Map;

public class FromJsonToTableMain {
   public static void main(String[] args) throws Exception {

       ConnectionContainer con = new ConnectionContainer();

       Map<Class, Schema> schemaMap = Map.of(
               Author.class, new Schema(Author.class),
               Book.class, new Schema(Book.class),
               BookStore.class, new Schema(BookStore.class),
               Estimation.class, new Schema(Estimation.class),
               Location.class, new Schema(Location.class)


       );

       for (Schema schema: schemaMap.values()){
           con.getStatement().execute(
                   new QueryConstructor(schema).create()
           );
       }

       List<BookStore> stores = new Dsrlzr("file.json").execute();

       for (BookStore store : stores) {
           con.getStatement().execute(
                   new QueryConstructor(
                           schemaMap.get(store.getClass())
                   ).insert(store)
           );
           con.getStatement().execute(
                   new QueryConstructor(
                           schemaMap.get(Location.class)
                   ).insert(store.getLocation())
           );

           for (Book book: store.getBooks()){
               con.getStatement().execute(
                       new QueryConstructor(
                               schemaMap.get(book.getClass())
                       ).insert(book)
               );

               con.getStatement().execute(
                       new QueryConstructor(
                               schemaMap.get(Author.class)
                       ).insert(book.getAuthor())
               );

               con.getStatement().execute(
                       new QueryConstructor(
                               schemaMap.get(Estimation.class)
                       ).insert(book.getEstimation())
               );
           }


       }





   }

}
