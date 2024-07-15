package com.practical_interview.project.config;

import jakarta.validation.constraints.NotBlank;

import java.util.Random;

public class Utils {
    public static @NotBlank String generateRandomPin() {
        Random random = new Random();
        char[] digits = new char[Constants.PIN_LENGTH];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < Constants.PIN_LENGTH; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }
}
