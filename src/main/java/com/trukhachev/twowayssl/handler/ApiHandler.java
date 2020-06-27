package com.trukhachev.twowayssl.handler;

import com.trukhachev.twowayssl.client.alfabank.AlfaBankClient;
import com.trukhachev.twowayssl.client.alfabank.dto.AtmDTO;
import com.trukhachev.twowayssl.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiHandler {

    private final AlfaBankClient alfaBankClient;

    public ResponseDTO getAtm(final String deviceId)  {

        var result = alfaBankClient.getData();

        for (var atm : result.getData().getAtms()) {
            if (String.valueOf(atm.getDeviceId()).equals(deviceId)) {

                boolean payments;
                if (null == atm.getServices()) {
                    payments = false;
                }

                payments = "Y".equalsIgnoreCase(atm.getServices().getPayments());

                return convert(atm, payments);
            }
        }

        return null;
    }

    public ResponseDTO getNearest(final String latitude, final String longitude, final boolean payments) {

        var result = alfaBankClient.getData();

        double minDist = 0;
        AtmDTO nearestAtm = null;

        boolean paymentsSer =true;

        for (var atm : result.getData().getAtms()) {

            var latitude1 = atm.getCoordinates().getLatitude();
            var longitude1 = atm.getCoordinates().getLongitude();

            if (latitude1 == null || longitude1 == null) {
                continue;
            }


            if (null == atm.getServices()) {
                paymentsSer = false;
            }

            paymentsSer = "Y".equalsIgnoreCase(atm.getServices().getPayments());


            var diff = getMinDist(latitude, longitude, latitude1, longitude1);
            if (payments) {
                if (paymentsSer) {
                    if (nearestAtm == null) {
                        nearestAtm = atm;
                        minDist = diff;
                    } else {
                        if( diff < minDist) {
                            minDist = diff;
                            nearestAtm = atm;
                        }
                    }
                }
            } else {
                if (nearestAtm == null) {
                    nearestAtm = atm;
                    minDist = diff;
                } else {
                    if( diff < minDist) {
                        minDist = diff;
                        nearestAtm = atm;
                    }
                }
            }
        }

        if (nearestAtm == null) return null;


        return convert(nearestAtm, paymentsSer);

    }

    private double getMinDist(String x1, String y1, String x2, String y2) {

        double ax = Double.parseDouble(x1);
        double ay = Double.parseDouble(y1);
        double bx = Double.parseDouble(x2);
        double by = Double.parseDouble(y2);

        return Math.sqrt(Math.pow(Math.abs(bx - ax), 2) +  Math.pow(Math.abs(by - ay), 2));
    }

    private ResponseDTO convert(final AtmDTO atm, boolean payments) {
        return new ResponseDTO().
                setCity(atm.getAddress().getCity()).
                setDeviceId(atm.getDeviceId()).
                setLocation(atm.getAddress().getLocation()).
                setLatitude(atm.getCoordinates().getLatitude()).
                setLongitude(atm.getCoordinates().getLongitude()).
                setPayments(payments);
    }

}
