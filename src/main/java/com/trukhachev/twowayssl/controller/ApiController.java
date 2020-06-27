package com.trukhachev.twowayssl.controller;

import com.trukhachev.twowayssl.dto.NotFoundDTO;
import com.trukhachev.twowayssl.handler.ApiHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atms")
@RequiredArgsConstructor
public class ApiController {

    private final ApiHandler apiHandler;

    @GetMapping(value = "{deviceId}")
    public ResponseEntity<?> getAtm(@PathVariable("deviceId") String deviceId) {
        var result = apiHandler.getAtm(deviceId);

        if (result == null) {
            return new ResponseEntity<>(new NotFoundDTO().setStatus("atm not found"),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping(value = "nearest")
    public ResponseEntity<?> getNearest(@RequestParam(value = "latitude") String latitude,
                                        @RequestParam(value = "longitude") String longitude,
                                        @Nullable @RequestParam(value = "payments") boolean payments) {
        var result = apiHandler.getNearest(latitude, longitude, payments);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}

