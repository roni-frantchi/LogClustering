package impl;

import face.Cluster;

/**
 * Created by Or on 23/04/2017.
 */
public class ClusterFactory {

    public static Cluster getCluster(String str){
        return new WordsCluster(str);
    }
}
