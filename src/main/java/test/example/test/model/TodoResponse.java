package test.example.test.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {

    private String id;

    private String user_id;

    private String title;

    private String status;
}
