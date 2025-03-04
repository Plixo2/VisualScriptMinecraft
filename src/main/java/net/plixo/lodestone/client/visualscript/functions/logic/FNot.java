package net.plixo.lodestone.client.visualscript.functions.logic;

import net.plixo.lodestone.client.visualscript.Function;

public class FNot extends Function {

    @Override
    public void calculate() {
        pullInputs();
        if(hasInput(0)) {
            boolean obj1 = input(0,true);
            output(0, !obj1);
        }

    }

    @Override
    public void set() {
        setInputs("State");
        setOutputs("Opposite");
        setLinks();
    }
}
