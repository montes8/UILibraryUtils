package com.gb.vale.uitaylibrary.utils.scan.detector;

public final class ModulusGF {

    public static final ModulusGF PDF417_GF = new ModulusGF(PDF417Common.NUMBER_OF_CODEWORDS, 3);

    private final int[] expTable;
    private final int[] logTable;
    private final ModulusPoly zero;
    private final ModulusPoly one;
    private final int modulus;

    private ModulusGF(int modulus, int generator) {
        this.modulus = modulus;
        expTable = new int[modulus];
        logTable = new int[modulus];
        int x = 1;
        for (int i = 0; i < modulus; i++) {
            expTable[i] = x;
            x = (x * generator) % modulus;
        }
        for (int i = 0; i < modulus - 1; i++) {
            logTable[expTable[i]] = i;
        }
        // logTable[0] == 0 but this should never be used
        zero = new ModulusPoly(this, new int[]{0});
        one = new ModulusPoly(this, new int[]{1});
    }


    public ModulusPoly getZero() {
        return zero;
    }

    public ModulusPoly getOne() {
        return one;
    }

    public ModulusPoly buildMonomial(int degree, int coefficient) {
        if (degree < 0) {
            throw new IllegalArgumentException();
        }
        if (coefficient == 0) {
            return zero;
        }
        int[] coefficients = new int[degree + 1];
        coefficients[0] = coefficient;
        return new ModulusPoly(this, coefficients);
    }

    int add(int a, int b) {
        return (a + b) % modulus;
    }

    public int subtract(int a, int b) {
        return (modulus + a - b) % modulus;
    }

    public int exp(int a) {
        return expTable[a];
    }

    public int log(int a) {
        if (a == 0) {
            throw new IllegalArgumentException();
        }
        return logTable[a];
    }

    public int inverse(int a) {
        if (a == 0) {
            throw new ArithmeticException();
        }
        return expTable[modulus - logTable[a] - 1];
    }

    public int multiply(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return expTable[(logTable[a] + logTable[b]) % (modulus - 1)];
    }

    public int getSize() {
        return modulus;
    }

}
