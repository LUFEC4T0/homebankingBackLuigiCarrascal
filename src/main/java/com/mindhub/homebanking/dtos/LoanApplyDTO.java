package com.mindhub.homebanking.dtos;

import java.net.Inet4Address;

public record LoanApplyDTO(String name, Double maxAmount, Integer payments, String numberAccount) {
}
