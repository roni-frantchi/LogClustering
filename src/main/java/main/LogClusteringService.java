package main;

import com.google.common.io.Files;
import face.Cluster;
import impl.ClusterFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Or on 21/04/2017.
 */
public class LogClusteringService {

    public static void main(String[] args) throws IOException {
        List<Cluster> clusters = new ArrayList<>();
        File file = SampleFileGenerator.generateSampleFile("sample.txt").toFile();
        List<String> lines = Files.readLines(file, Charset.forName("utf-8"));
        lines.forEach(logMsg -> {
            if (clusters.size() == 0) {
                clusters.add(ClusterFactory.getCluster(logMsg));
            } else {
                boolean matched = false;
                for (Cluster cluster : clusters) {
                    if (cluster.match(logMsg)) {
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    clusters.add(ClusterFactory.getCluster(logMsg));
                }
            }


        });
        for (Cluster cluster : clusters) {
            System.out.println("====");
            List<String> logs = cluster.getLogs();
            for (String log : logs) {
                System.out.println(log);
            }
            System.out.println("The changing word was: " + cluster.getWords());
        }

    }

}
