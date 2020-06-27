package com.trukhachev.twowayssl.client.alfabank.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CoordinateDTO {

    private String latitude;

    private String longitude;

}
