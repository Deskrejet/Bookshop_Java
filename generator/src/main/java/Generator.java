

import java.util.ArrayList;
import java.util.List;

public class Generator {
    private Integer count;

    public Generator(Integer count) {
        this.count = count;
    }
    public List<BookStore> gen(){
        List<BookStore> stores = new ArrayList<>();
        long tmp = 0;

        for (long i = 1; i <= count; i++) {
            Location location = new Location(
                    i,
                    "address_"+i,
                    i
            );

            List<Book> books = new ArrayList<>();
            for (long j = 1; j <= 2; j++) {
                tmp+=1;
                Author author = new Author(
                        tmp,
                        "autor_"+i*j,
                        tmp
                );
                Estimation estimation = new Estimation(
                        tmp,
                        4,
                        "text_",
                        tmp
                );

                books.add(
                        new Book(
                                tmp,
                                "book_"+tmp,
                                i,
                                author,
                                estimation
                        )
                );

            }
            stores.add(
                    new BookStore(
                            i,
                            "store_"+i,
                            location,
                            books
                    )
            );


        }
        return stores;
    }
}
