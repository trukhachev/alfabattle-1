package com.trukhachev.twowayssl.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseDTO {

    private int deviceId;

    private String latitude;

    private String longitude;

    private String city;

    private String location;

    private boolean payments;

}
