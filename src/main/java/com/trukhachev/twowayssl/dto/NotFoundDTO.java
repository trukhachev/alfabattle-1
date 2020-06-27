package com.trukhachev.twowayssl.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NotFoundDTO {

    private String status;

}
