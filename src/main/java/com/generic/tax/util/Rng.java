package com.generic.tax.util;

import java.util.Random;

public class Rng {

    private Random rand;

    public Rng() {
        rand = new Random();
    }

    public int getRandomNumber(int minValue, int maxValue) {

        int randomNumber = rand.nextInt(maxValue);

        while (randomNumber < minValue) {

            randomNumber = rand.nextInt(maxValue);

        }


        return randomNumber;
    }

}
