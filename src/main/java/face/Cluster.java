package face;

import java.util.List;
import java.util.Set;

/**
 * Created by Or on 23/04/2017.
 */
public interface Cluster {

    boolean match(String log);

    List<String> getLogs();

    Set<String> getWords();
}
