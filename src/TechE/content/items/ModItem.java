package TechE.content.items;

import arc.graphics.Color;

import mindustry.type.Item;




public class ModItem{
    public static Item AmultipleSteel,Mn;
    public static void load(){
        AmultipleSteel=new Item("Amultiple-steel",Color.valueOf("#C0C0C0")){{
            hardness=4;
            cost=2f;
        }};
        Mn = new Item("Mn",Color.valueOf("#0e030bff")){{
            hardness=3;
            cost=1.5f;
        }};
    }
}
