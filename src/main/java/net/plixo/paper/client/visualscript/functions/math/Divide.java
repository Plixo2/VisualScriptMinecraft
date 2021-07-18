package net.plixo.paper.client.visualscript.functions.math;

import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.visualscript.Function;

public class Divide extends Function {

    @Override
    public void calculate() {
        pullInputs();
        if(hasInputs()) {
            Object obj1 = input(0);
            Object obj2 = input(1);
            if(obj1 instanceof Number && obj2 instanceof Number) {
                output(0,dNumbers((Number) obj1,(Number) obj2));
            }
            else {
                Util.print((obj2 instanceof Number ? "First" : "Second") + "Argument is not a Number");
            }
        }
    }
    public static Number dNumbers(Number a, Number b) {
        if(a instanceof Double || b instanceof Double) {
            return a.doubleValue() / b.doubleValue();
        } else if(a instanceof Float || b instanceof Float) {
            return a.floatValue() / b.floatValue();
        } else if(a instanceof Long || b instanceof Long) {
            return a.longValue() / b.longValue();
        } else {
            return a.intValue() / b.intValue();
        }
    }

    @Override
    public void set() {
        setInputs("Dividend","Divisor");
        setOutputs("Quotient");
        setLinks();
    }
}
