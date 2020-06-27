package com.trukhachev.twowayssl.client.alfabank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AlfaBankResponseDTO {

    private DataDTO data;

}
