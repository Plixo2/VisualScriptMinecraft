package net.plixo.paper.client.avs.newVersion;

public class getGround extends nFunction{

    @Override
    public void calculate() {
        pullInputs();
        output(0,mc.player.isOnGround());
    }

    @Override
    public void set() {
        set(0,1,0);
    }
}
