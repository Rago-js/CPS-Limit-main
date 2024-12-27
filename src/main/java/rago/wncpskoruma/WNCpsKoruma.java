package rago.wncpskoruma;

import rago.wncpskoruma.events.AttackEventListener;
import org.bukkit.plugin.java.JavaPlugin;
import rago.wncpskoruma.commands.ReloadCommand;
import rago.wncpskoruma.events.PlayerAnimationListener;
import rago.wncpskoruma.events.PlayerInteractListener;
import rago.wncpskoruma.manager.PlayerCPSManager;
import rago.wncpskoruma.utils.resettask;
import rago.wncpskoruma.utils.util;

public class WNCpsKoruma extends JavaPlugin {
    private PlayerCPSManager cpsManager;
    private PlayerAnimationListener playerAnimationListener;
    private PlayerInteractListener playerInteractListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new util(this);

        cpsManager = new PlayerCPSManager(this);
        playerAnimationListener = new PlayerAnimationListener(cpsManager);
        playerInteractListener = new PlayerInteractListener(cpsManager);

        getServer().getPluginManager().registerEvents(playerAnimationListener, this);
        getServer().getPluginManager().registerEvents(playerInteractListener, this);
        getServer().getPluginManager().registerEvents(new AttackEventListener(this), this);

        new resettask(cpsManager).runTaskTimer(this, 0L, 20L); // 20 ticks = 1 second

        getCommand("wncpsreload").setExecutor(new ReloadCommand(this));
    }
}
