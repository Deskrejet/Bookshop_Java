import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStore {
    private Long id;
    private String name;
    private Location location;

    private List<Book> books;


}

