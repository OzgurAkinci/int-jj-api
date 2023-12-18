package com.app.integraljjapi.api;

abstract class Exp {
    public abstract Object accept(Visitor v);
}

class INT extends Exp {
    Exp exp;

    public INT(Exp e) {
        exp = e;
    }

    public String toString() {
        return "INT(" + exp + ")";
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }

}

class Num extends Exp {
    int number;

    public Num(int a) {
        number = a;
    }

    public String toString() {
        return "Num(" + number + ")";
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}

class RNum extends Exp {
    int number1, number2;

    public RNum(int a, int b) {
        number1 = a;
        number2 = b;
    }

    public String toString() {
        return "RNum(" + number1 + "/" + number2 + ")";
    }

    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
