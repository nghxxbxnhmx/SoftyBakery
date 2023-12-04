package com.poly.restcontrollers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.dto.address.City;
import com.poly.dto.address.District;
import com.poly.dto.address.Ward;
import com.poly.utils.JsonReaderUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/address")
public class AddressRestController {
    private String url = "src\\main\\resources\\static\\data\\vietnam_location\\";
    private String cityFilePath = url + "cities.json";
    private String districtFilePath = url + "districts.json";
    private String wardFilePath = url + "wards.json";
    @Autowired
    JsonReaderUtil jsonReader;

    @GetMapping("/cities")
    public List<City> cityIndex() {
        List<City> cityList = (ArrayList<City>) jsonReader.read(cityFilePath, City.class);
        if (cityList != null) {
            return cityList;
        }
        String [] citiesName = {};
        return null;
    }

    @GetMapping("/{cityCode}/districts")
    public List<District> districtIndex(@PathVariable("cityCode") String cityCode) {
        ArrayList<District> districtList = (ArrayList<District>) jsonReader.read(districtFilePath, District.class);
        if (districtList != null) {
            List<District> filteredDistricts = new ArrayList<>();
            for (District district : districtList) {
                if (district.getParentCode().equals(cityCode)) {
                    filteredDistricts.add(district);
                }
            }
            return filteredDistricts;
        }
        return null;
    }

    @GetMapping("/{districtCode}/wards")
    public ResponseEntity<List<Ward>> wardIndex(@PathVariable("districtCode") String districtCode) {
        List<Ward> wardList = jsonReader.read(wardFilePath, Ward.class);
        if (wardList != null) {
            List<Ward> filteredWards = new ArrayList<>();
            for (Ward ward : wardList) {
                if (ward.getParentCode().equals(districtCode)) {
                    filteredWards.add(ward);
                }
            }
            return ResponseEntity.ok(filteredWards);
        } else {
            return null;
        }
    }

}
