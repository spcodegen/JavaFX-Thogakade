package model;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String id;
    private String name;
    private String address;
    private Double salary;

}
