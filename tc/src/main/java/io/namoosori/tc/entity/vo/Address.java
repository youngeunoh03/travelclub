package io.namoosori.tc.entity.vo;

import lombok.Data;

@Data
public class Address {
    //
    private String zipCode;
    private String zipAddress;
    private String streetAddress;
    private String country;
    private AddressType addressType;

    public Address() {
        //
    }

    public Address(String zipCode, String zipAddress, String streetAddress) {
        //
        this.zipCode = zipCode;
        this.zipAddress = zipAddress;
        this.streetAddress = streetAddress;
        this.country = "South Korea";
        this.addressType = AddressType.Office;
    }
}
