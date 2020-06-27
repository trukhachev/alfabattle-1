package com.trukhachev.twowayssl.client.alfabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DataDTO {

    private List<AtmDTO> atms;

}
