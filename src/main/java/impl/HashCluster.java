package impl;


import face.Cluster;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Or on 21/04/2017.
 */
public class HashCluster implements Cluster {

    //private int index;
    //private String[] pattern;
    final int PREFIX_LENGTH=20;
    private int key = 0;
    private int startDiffIndex = -1;
    private Set<String> words = new HashSet<>();
    private List<String> logs = new ArrayList<>();


    public HashCluster(String log) {
        //this.pattern = log.split(" ");
        this.logs.add(log);
    }

    public boolean match(String log){

        String logNoDate = log.substring(PREFIX_LENGTH, log.length());

        if(key != 0){
            int logWordOffset = logNoDate.indexOf(" ", startDiffIndex);
            if(logWordOffset==-1){
                return false;
            }
            String logNoDateNoWord = new StringBuffer(logNoDate).replace(startDiffIndex, logWordOffset, "").toString();
            if(key != logNoDateNoWord.hashCode()){
                return false;
            }else{
                String word = logNoDateNoWord.substring(startDiffIndex,logWordOffset);
                words.add(word);
                logs.add(log);
                return true;
            }
        }else{
            String originNoDate = logs.get(0).substring(PREFIX_LENGTH, logs.get(0).length());

            int indexOfFirstDiff = firstDiffIndex(originNoDate, logNoDate);
            int indexOfEndOfDiffWordInOrigin = originNoDate.indexOf(" ", indexOfFirstDiff);
            int indexOfEndOfDiffWordInLog = logNoDate.indexOf(" ", indexOfFirstDiff);
            String originNoDateNoWord = new StringBuilder().append(originNoDate.substring(0, indexOfFirstDiff)).
                    append(originNoDate.substring(indexOfEndOfDiffWordInOrigin)).toString();
            String logNoDateNoWord = new StringBuilder().append(logNoDate.substring(0, indexOfFirstDiff)).
                    append(logNoDate.substring(indexOfEndOfDiffWordInLog)).toString();
            if(originNoDateNoWord.equals(logNoDateNoWord)){
                key = originNoDateNoWord.hashCode();
                this.startDiffIndex = indexOfFirstDiff;
                logs.add(log);
                this.words.add(logNoDate.substring(indexOfFirstDiff,indexOfEndOfDiffWordInLog));
                return true;
            }else{
                return false;
            }
        }


    }

    private int firstDiffIndex(String originLog, String logNoDate) {
        char[] originLogCharArray = originLog.toCharArray();
        char[] logNoDateCharArray = logNoDate.toCharArray();
        int indexOfFirstDiff;
        for(int i = 0; i < originLogCharArray.length ; i++){
            if(originLogCharArray[i]!=logNoDateCharArray[i]){
                return i;
            }
        }
        return -1;
    }


    public Set<String> getWords() {
        return words;
    }

    public List<String> getLogs() {
        return logs;
    }
}
