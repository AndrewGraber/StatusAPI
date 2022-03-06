package com.anzanama.statusapi;

import com.anzanama.statusapi.communication.CommunicationFactory;
import com.anzanama.statusapi.communication.ICommunication;
import com.anzanama.statusapi.config.ConfigHandler;
import com.anzanama.statusapi.data.StatusData;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(modid = StatusAPI.MODID, name = StatusAPI.MODNAME, version = StatusAPI.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
public class StatusAPI {
    public static final String MODID = "statusapi";
    public static final String MODNAME = "Status API";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static StatusAPI instance;

    public static Logger logger;

    public static Configuration config;

    public static ArrayList<ICommunication> com;

    public static StatusData data;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //Inititalize logger
        logger = event.getModLog();

        //Initialize config
        config = new Configuration(event.getSuggestedConfigurationFile());
        ConfigHandler.syncConfig(config);

        com = CommunicationFactory.buildCommuncation();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public static void serverStarted(FMLServerStartedEvent event) {
        data = new StatusData();
    }
}
