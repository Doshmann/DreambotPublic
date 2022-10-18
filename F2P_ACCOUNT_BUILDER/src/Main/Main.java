package Main;

import Mining.MiningStageOne;
import Mining.MiningStageTwo;

import org.dreambot.api.Client;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@ScriptManifest(author = "Mr.S", description = "Advanced account builder - F2P",
        name = "Account builder", category = Category.MONEYMAKING, version = 1)

public class Main extends AbstractScript{

    public static String botStatus = "Online";
    private boolean isRunning;
    public static int skillToStopAt;
    public static String skillToComplete;
    private final DrawMouseUtil drawMouseUtil = new DrawMouseUtil();


    @Override //Infinite loop
    public int onLoop() {
    if (isRunning) {
        if(skillToComplete == "Woodcutting") {
            // add this later
        }
        if(skillToComplete == "Mining") {
            if(Skills.getRealLevel(Skill.MINING) < 15) {
                MiningStageOne.mineOres();
            }
            if(Skills.getRealLevel(Skill.MINING) >= 15) {
                MiningStageTwo.mineOres();
            }
        }
        if(skillToComplete == "Fishing") {

        }
    }
        return 0;
    }

    //When script start load this.
    public void onStart() {
        drawMouseUtil.setRainbow(true);
        log("Bot Started, you have probably made a bad decision today");
        SwingUtilities.invokeLater(this::createGUI);
    }

    private void createGUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Mr.S F2P Account Builder");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(Client.getInstance().getCanvas());
        frame.setPreferredSize(new Dimension(300, 170));
        frame.getContentPane().setLayout(new BorderLayout());

        // initialize components here
        // start of upper gui
        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new GridLayout(0, 2));

        JLabel label = new JLabel();
        label.setText("Select skill:");
        settingPanel.add(label);

        JComboBox<String> skillComboBox = new JComboBox<>(new String[] {
            "Woodcutting", "Mining", "Fishing"});
        settingPanel.add(skillComboBox);

        label = new JLabel();
        label.setText("Level to stop at:");
        settingPanel.add(label);

        JComboBox<Integer> anotherComboBox = new JComboBox<>(new Integer[]{25, 30, 50});
        anotherComboBox.addActionListener(e -> skillToStopAt = (int) anotherComboBox.getSelectedItem());
        settingPanel.add(anotherComboBox);

        frame.getContentPane().add(settingPanel, BorderLayout.CENTER);
        // end of upper gui

        // start of lower gui
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton button = new JButton();
        button.setText("Start script");
        button.addActionListener( l -> {

            skillToComplete = Objects.requireNonNull(skillComboBox.getSelectedItem()).toString();

            isRunning = true;
            frame.dispose();
        });

        buttonPanel.add(button);

        button = new JButton();
        button.setText("Another button");
        button.addActionListener(l ->{
            log("This button was clicked");
        });
        buttonPanel.add(button);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    //When script ends do this.
    public void onExit() {
        log("Bot Ended, you've realised the mistake that you've made, congratulations; although it's probably too late");
    }

    private void drawShadowString(Graphics g, String s, int x, int y) {
        g.setFont(new Font("Ariel", 1, 15));
        g.setColor(Color.BLACK);
        g.drawString(s, x + 1, y + 1);
        g.setColor(Color.WHITE);
        g.drawString(s, x, y);
    }

    public void onPaint(Graphics g) {
        this.drawShadowString(g, "Bot Status: " + botStatus, 20, 330);
        this.drawShadowString(g, "Level to stop at: " + skillToStopAt, 20, 315);
        drawMouseUtil.drawRotatingCrossMouse(g);
        drawMouseUtil.drawTrail(g);
    }
}
