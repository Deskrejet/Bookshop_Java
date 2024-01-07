import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Long id;
    private String address;

    private Long store_id;

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Long getStore_id() {
        return store_id;
    }
}
