package rs.ac.singidunum.autoservis.error;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorObject {

    private String message;
    private String path;
    private String timestamp;
}
