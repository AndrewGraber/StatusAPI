package com.anzanama.statusapi.communication;

import com.anzanama.statusapi.StatusAPI;
import com.anzanama.statusapi.config.ConfigVals;

import java.util.ArrayList;

public class CommunicationFactory {
    public static ArrayList<ICommunication> buildCommuncation() {
        ArrayList<ICommunication> comm = new ArrayList<>();

        if(ConfigVals.use_http_server) {
            comm.add(new ComServer());
        }

        if(ConfigVals.use_http_client) {
            comm.add(new ComClient());
        }

        if(ConfigVals.use_database) {
            comm.add(new ComDatabase());
        }

        return comm;
    }
}
