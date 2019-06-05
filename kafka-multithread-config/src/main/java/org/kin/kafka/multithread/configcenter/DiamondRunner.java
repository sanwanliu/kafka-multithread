package org.kin.kafka.multithread.configcenter;

/**
 * Created by huangjianqin on 2017/9/11.
 */
public class DiamondRunner {
    public static void main(String[] args) {
        String configPath = "";
        if(args.length > 0){
            if(args[0] != null && !args[0].equals("")){
                configPath = args[0];
            }
        }

        Diamond diamond = configPath.equals("")? new Diamond() : new Diamond(configPath);

        try{
            diamond.start();
        }finally {
            diamond.close();
        }
    }
}
