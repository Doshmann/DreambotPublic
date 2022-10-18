package Mining;
import Main.Main;
import org.dreambot.api.methods.container.impl.DropPattern;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.StandardDropPattern;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.*;
import static org.dreambot.api.methods.MethodProvider.sleep;

public class MiningStageTwo {
    public static void mineOres() {
        String pickaxe = Inventory.getNameForSlot(0);
        Area miningArea = new Area(3032, 9825, 3032, 9825);
        GameObject ore = GameObjects.closest(11365);

        if(!miningArea.contains(getLocalPlayer()) && !Inventory.isFull()) {
            while(!miningArea.contains(getLocalPlayer()) && !getLocalPlayer().isMoving()) {
                Main.botStatus = "Walking to mining area...";
                Walking.walk(miningArea.getRandomTile());
                sleep(1200);
            }
        }
        if(!Inventory.isFull() && Inventory.contains(pickaxe) && miningArea.contains(getLocalPlayer())) {
            if(getLocalPlayer().getAnimation() == -1 && !Inventory.isFull() && Main.skillToStopAt > Skills.getRealLevel(Skill.MINING)) {
                Player player = Players.closest(p -> p != null && !p.equals(getLocalPlayer()) && p.distance() < 10);

                if (player != null) {
                    World world = Worlds.getRandomWorld(w -> w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0);
                    Main.botStatus = "Some nerd is trying to crash your rocks, silly fool - hopping worlds";
                    log(player.getName() + "Found at rocks, hopping");
                    WorldHopper.hopWorld(world);
                }
                int ores = Inventory.count("Iron ore");
                sleep(232, 341);
                Main.botStatus = "Mining ores...";
                ore.interact("Mine");
                sleep(890, 1231);
                sleepUntil(() -> Inventory.count("Iron ore") > ores || Dialogues.canContinue(), 21412);
            }
        }
        if(Inventory.isFull() && Inventory.contains("Iron ore")) {
            Main.botStatus = "Dropping ores....";
            DropPattern customDropPattern = new StandardDropPattern(0, 4, 1, 5, 2, 6, 3, 7, 8, 12, 9, 13, 10, 14, 11, 15, 16, 20, 17, 21, 18, 22, 19, 23, 24, 25, 26, 27);
            Inventory.setDropPattern(customDropPattern);
            Inventory.dropAllExcept(pickaxe);
            sleep(4000);
        }
    }
}
