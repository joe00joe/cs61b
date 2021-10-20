package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;;
        b = 231;
        energy = e;
    }


    public Color color() {

        return color(r, g, b);
    }

    public void attack(Creature c) {
       this.energy+=c.energy();
       //
    }

    public void move() {
        this.energy-=0.03;
    }

    public void stay() {
        this.energy-=0.01;

    }

    public Clorus replicate() {
        this.energy/=2;
        return new Clorus(this.energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        java.util.List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if(empties.size()==0){
            return new Action(Action.ActionType.STAY);
        }
        else if(plips.size()>0){
            Direction attackDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }
        else if(this.energy>=1){
            Direction repDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, repDir);
        }
        else{
            Direction moveDir=HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE,moveDir);

        }

    }






}
