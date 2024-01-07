import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estimation {
    private Long id;
    private Integer score;
    private String text;
    private Long book_id;


}
