package com.mindhub.homebanking.utils;
import java.util.Random;
public class NumberRamdom {
        public static String generateCardNumber() {
            String cardNumber16 = String.format("%04d-%04d-%04d-%04d",
                    1000 + new Random().nextInt(9000),
                    1000 + new Random().nextInt(9000),
                    1000 + new Random().nextInt(9000),
                    1000 + new Random().nextInt(9000));
            return cardNumber16;
        }
    }




