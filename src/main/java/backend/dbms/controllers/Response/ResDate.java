package backend.dbms.controllers.Response;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResDate {
    private Date date;
}
