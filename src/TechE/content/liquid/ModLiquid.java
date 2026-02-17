package TechE.content.liquid;

import arc.Core;
import arc.graphics.Color;
import mindustry.type.Liquid;

public class ModLiquid {
    public static Liquid neutronFluid;
    public static void load(){
        neutronFluid =new Liquid("neutron-fluid",Color.valueOf("3E84C9")){{
            heatCapacity=5f;
            uiIcon= Core.atlas.find("neutron-fluid");
            temperature=0.5f;
            lightColor= Color.valueOf("3E84C9");
            description="零度流体";
            coolant=true;
            alwaysUnlocked=true;
        }};
    }
}
