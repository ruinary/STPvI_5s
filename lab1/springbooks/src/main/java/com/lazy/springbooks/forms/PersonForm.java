package com.lazy.springbooks.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
* -the features of @ToString , @EqualsAndHashCode ,
* @Getter / @Setter and @RequiredArgsConstructor
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonForm {
    private int id;
    private String name;
    private String lastname;
}
