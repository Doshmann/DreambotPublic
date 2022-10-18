import org.dreambot.api.methods.container.impl.DropPattern;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.StandardDropPattern;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.WorldType;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.*;

public class MiningStageTwo {
    public static void mineOres() {
        String pickaxe = Inventory.getNameForSlot(0);
        Area miningArea = new Area(3033, 9826, 3033, 9826);
        Tile miningAreaTile = new Tile(3033, 9826);

        GameObject ore = GameObjects.closest(11365);

        if(!miningArea.contains(Players.getLocal()) && !Inventory.isFull()) {
            if(!miningArea.contains(Players.getLocal()) && !Players.getLocal().isMoving()) {
                if(miningAreaTile.distance() < 3 && !Players.getLocal().isMoving()) {
                    Walking.walkOnScreen(miningAreaTile);
                    sleep(5000);
                    sleepUntil(() -> !Players.getLocal().isMoving(), 10000);
                } else {
                    Main.botStatus = "Walking to mining area...";
                    Walking.walk(miningArea.getRandomTile());
                    sleep(1200);
                }
            }
        }
        if(!Inventory.isFull() && Inventory.contains(pickaxe) && miningArea.contains(Players.getLocal())) {
            if(Players.getLocal().getAnimation() == -1 && !Inventory.isFull() && ore != null) {
                Player player = Players.closest(p -> p != null && !p.equals(Players.getLocal()) && p.distance() < 3);

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
            Inventory.dropAllExcept(pickaxe, "Coins");
            sleep(4000);
            }
        }
    }

