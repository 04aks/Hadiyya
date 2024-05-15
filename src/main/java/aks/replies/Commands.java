package aks.replies;

import org.telegram.telegrambots.meta.api.objects.Update;

public class Commands {

    public boolean canSend = false;
    public String command;
    public String commandkey;
    public String commandDesc;
    
    public Commands(){

    }

    // TO OVERRIDE SOMEWHERE
    public void action(Update update){}
    public void sendMessage(){}
    public void notYet(Update update){
        System.out.println("niggers");
    }
}
