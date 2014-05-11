/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.io.Serializable;

/**
 *
 * @author sdist
 */
public class Task implements Serializable{
    protected String taskId;
    protected String requirementId;
    protected long length;
    protected String output;
    
    public Task(String t, String r, long l){
        taskId = t;
        requirementId = r;
        length = l;
        output = "Terminado " + t;
    }
    
    public long getLength(){
        return length;
    }
    
    public String getRequirementId(){
        return requirementId;
    }
    
    public String getOutput(){
        return output;
    }
    
//    public void setOutput(String o){
//        output = o + " " + taskId;
//    }
}
