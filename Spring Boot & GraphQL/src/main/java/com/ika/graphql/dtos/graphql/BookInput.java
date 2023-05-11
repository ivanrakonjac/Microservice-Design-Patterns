package com.ika.graphql.dtos.graphql;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInput {
    private String name;
    private long authorId;
}
