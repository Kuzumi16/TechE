package TechE.content.units;

import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.world.blocks.units.UnitFactory;

import static mindustry.type.ItemStack.with;

public class ModUnit {
    public static UnitType workerZero, workerOne, workerTwo,transporter;
    public static void load(){
        workerZero =new TMiner("workerZero");
        workerOne =new TMiner("workerOne");
        workerTwo =new TMiner("workerTwo");
        transporter=new Transporter("transporter");
    }

    public static class TMiner extends UnitType {
        public TMiner(String name) {
            super(name);
            this.description = "跑得快，效率高";
            this.speed = 4f;
            this.flying = true;
            this.hitSize = 10f;
            this.engineOffset = 5f;
            this.engineSize = 1.5f;
            this.itemCapacity = 500;
            this.alwaysUnlocked = true;
            this.constructor= UnitEntity::create;
            // 采矿相关属性
            this.mineSpeed = 200f;
            this.mineTier = 10;
            this.buildSpeed = 1f;
            // 研究和生产相关
            // 可采矿类型
            this.mineItems=Seq.with(Items.thorium,Items.coal,Items.copper,Items.lead,
                    Items.sand, Items.titanium);
            //this.defaultCommand= UnitCommand.mineCommand;
            ((UnitFactory)Blocks.airFactory).plans.add(new UnitFactory.UnitPlan(this,60f*15f,with(
                    Items.silicon,50,Items.titanium,50,Items.graphite,50
            )));
            // 部件系统


        }
        @Override
        public boolean targetable(Unit unit, Team targeter) {
            return false;
        }
        @Override
        public boolean hittable(Unit unit) {
            return false;
        }
    }
    public static class Transporter extends UnitType{
        public Transporter(String name) {
            super(name);
            this.itemCapacity=1000;
            this.speed=10f;
            this.flying=true;
            this.hitSize = 10f;
            this.engineOffset = 5f;
            this.engineSize = 1.5f;
            this.alwaysUnlocked = true;
            this.constructor= UnitEntity::create;

            this.rotateSpeed=2f;
        }

        @Override
        public boolean targetable(Unit unit, Team targeter) {
            return false;
        }
    };
}