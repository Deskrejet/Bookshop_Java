import java.io.IOException;
import java.util.List;

public class JsonReadMain {
    public static void main(String[] args) throws IOException {
        List<BookStore> stores = new Dsrlzr("file.json").execute();

        for (BookStore store : stores) {
            System.out.println(store);
        }
    }
}
