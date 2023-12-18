package com.app.integraljjapi.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FractionUtils extends Number implements Comparable<FractionUtils>, Serializable {
    public final static FractionUtils ZERO = new FractionUtils(BigInteger.ZERO, BigInteger.ONE);
    private final static long serialVersionUID = 1099377265582986378L;

    private final BigInteger numerator, denominator;

    private FractionUtils(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    private static FractionUtils canonical(BigInteger numerator, BigInteger denominator, boolean checkGcd) {
        if (denominator.signum() == 0) {
            throw new IllegalArgumentException("denominator is zero");
        }
        if (numerator.signum() == 0) {
            return ZERO;
        }
        if (denominator.signum() < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }
        if (checkGcd) {
            BigInteger gcd = numerator.gcd(denominator);
            if (!gcd.equals(BigInteger.ONE)) {
                numerator = numerator.divide(gcd);
                denominator = denominator.divide(gcd);
            }
        }
        return new FractionUtils(numerator, denominator);
    }

    public static FractionUtils getInstance(BigInteger numerator, BigInteger denominator) {
        return canonical(numerator, denominator, true);
    }

    public static FractionUtils getInstance(long numerator, long denominator) {
        return canonical(new BigInteger("" + numerator), new BigInteger("" + denominator), true);
    }

    public static FractionUtils getInstance(String numerator, String denominator) {
        return canonical(new BigInteger(numerator), new BigInteger(denominator), true);
    }

    public static FractionUtils valueOf(Double param) {
        String s = param.toString();
        Pattern p = Pattern.compile("(-?\\d+)(?:.(\\d+)?)?0*(?:e(-?\\d+))?");
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            throw new IllegalArgumentException("Unknown format '" + s + "'");
        }

        // this translates 23.123e5 to 25,123 / 1000 * 10^5 = 2,512,300 / 1 (GCD)
        String whole = m.group(1);
        String decimal = m.group(2);
        String exponent = m.group(3);
        String n = whole;

        // 23.123 => 23123
        if (decimal != null) {
            n += decimal;
        }
        BigInteger numerator = new BigInteger(n);

        // exponent is an int because BigInteger.pow() takes an int argument
        // it gets more difficult if exponent needs to be outside {-2 billion,2 billion}
        int exp = exponent == null ? 0 : Integer.valueOf(exponent);
        int decimalPlaces = decimal == null ? 0 : decimal.length();
        exp -= decimalPlaces;
        BigInteger denominator;
        if (exp < 0) {
            denominator = BigInteger.TEN.pow(-exp);
        } else {
            numerator = numerator.multiply(BigInteger.TEN.pow(exp));
            denominator = BigInteger.ONE;
        }

        // done
        return canonical(numerator, denominator, true);
    }

    // Comparable
    public int compareTo(FractionUtils o) {
        // note: this is a bit of cheat, relying on BigInteger.compareTo() returning
        // -1, 0 or 1.  For the more general contract of compareTo(), you'd need to do
        // more checking
        if (numerator.signum() != o.numerator.signum()) {
            return numerator.signum() - o.numerator.signum();
        } else {
            // oddly BigInteger has gcd() but no lcm()
            BigInteger i1 = numerator.multiply(o.denominator);
            BigInteger i2 = o.numerator.multiply(denominator);
            return i1.compareTo(i2); // expensive!
        }
    }

    public FractionUtils add(FractionUtils o) {
        if (o.numerator.signum() == 0) {
            return this;
        } else if (numerator.signum() == 0) {
            return o;
        } else if (denominator.equals(o.denominator)) {
            return new FractionUtils(numerator.add(o.numerator), denominator);
        } else {
            return canonical(numerator.multiply(o.denominator).add(o.numerator.multiply(denominator)), denominator.multiply(o.denominator), true);
        }
    }


    public FractionUtils multiply(FractionUtils o) {
        if (numerator.signum() == 0 || o.numerator.signum( )== 0) {
            return ZERO;
        } else if (numerator.equals(o.denominator)) {
            return canonical(o.numerator, denominator, true);
        } else if (o.numerator.equals(denominator)) {
            return canonical(numerator, o.denominator, true);
        } else if (numerator.negate().equals(o.denominator)) {
            return canonical(o.numerator.negate(), denominator, true);
        } else if (o.numerator.negate().equals(denominator)) {
            return canonical(numerator.negate(), o.denominator, true);
        } else {
            return canonical(numerator.multiply(o.numerator), denominator.multiply(o.denominator), true);
        }
    }

    public BigInteger getNumerator() { return numerator; }
    public BigInteger getDenominator() { return denominator; }
    public boolean isInteger() { return numerator.signum() == 0 || denominator.equals(BigInteger.ONE); }
    public FractionUtils negate() { return new FractionUtils(numerator.negate(), denominator); }
    public FractionUtils invert() { return canonical(denominator, numerator, false); }
    public FractionUtils abs() { return numerator.signum() < 0 ? negate() : this; }
    public FractionUtils pow(int exp) { return canonical(numerator.pow(exp), denominator.pow(exp), true); }
    public FractionUtils subtract(FractionUtils o) { return add(o.negate()); }
    public FractionUtils divide(FractionUtils o) { return multiply(o.invert()); }
    public FractionUtils min(FractionUtils o) { return compareTo(o) <= 0 ? this : o; }
    public FractionUtils max(FractionUtils o) { return compareTo(o) >= 0 ? this : o; }

    public BigDecimal toBigDecimal(int scale, RoundingMode roundingMode) {
        return isInteger() ? new BigDecimal(numerator) : new BigDecimal(numerator).divide(new BigDecimal(denominator), scale, roundingMode);
    }

    // Number
    public int intValue() { return isInteger() ? numerator.intValue() : numerator.divide(denominator).intValue(); }
    public long longValue() { return isInteger() ? numerator.longValue() : numerator.divide(denominator).longValue(); }
    public float floatValue() { return (float)doubleValue(); }
    public double doubleValue() { return isInteger() ? numerator.doubleValue() : numerator.doubleValue() / denominator.doubleValue(); }

    @Override
    public String toString() { return isInteger() ? String.format("%,d", numerator) : String.format("%,d / %,d", numerator, denominator); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FractionUtils that = (FractionUtils) o;

        if (denominator != null ? !denominator.equals(that.denominator) : that.denominator != null) return false;
        if (numerator != null ? !numerator.equals(that.numerator) : that.numerator != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = numerator != null ? numerator.hashCode() : 0;
        result = 31 * result + (denominator != null ? denominator.hashCode() : 0);
        return result;
    }
}
