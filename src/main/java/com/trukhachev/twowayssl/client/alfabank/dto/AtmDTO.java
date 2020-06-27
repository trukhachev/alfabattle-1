package com.trukhachev.twowayssl.client.alfabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AtmDTO {

    private int deviceId;

    private ServiceDTO services;

    private AddressDTO address;

    private CoordinateDTO coordinates;

}
