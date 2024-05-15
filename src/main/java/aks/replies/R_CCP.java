package aks.replies;

import org.telegram.telegrambots.meta.api.objects.Update;

import aks.Main;
import aks.Strings;

public class R_CCP extends Commands{
 
    public R_CCP(){
        canSend = false;
        command = "/ccp";
        commandkey = "ccp";
        commandDesc = "💳 𝐬𝐞𝐧𝐝 𝐂𝐂𝐏 (𝐬𝐚𝐧𝐬 𝐜𝐥é)";    
    }
    
    @Override
    public void action(Update update) {
        sendCCPconfirmed(update);        
    }
    @Override
    public void notYet(Update update) {
        sendNotYetMessage(update);
    }
    public void sendCCPconfirmed(Update update){
        Main.bot.sendImageCaption(update.getMessage().getChatId().toString(), Strings.CCP_DONE, "💳 𝐂𝐂𝐏: ✅");
        Main.bot.commObjects.rConfirm.canSend = true;
        Main.bot.reply(update.getMessage().getChatId().toString(), "/confirm 𝐭𝐨 𝐜𝐨𝐧𝐟𝐢𝐫𝐦 𝐲𝐨𝐮𝐫 𝐨𝐫𝐝𝐞𝐫.");
    }
    public void sendNotYetMessage(Update update){
        Main.bot.reply(update.getMessage().getChatId().toString(), "<𝘢𝘥𝘥 𝘺𝘰𝘶𝘳 𝘦𝘮𝘢𝘪𝘭 𝘧𝘪𝘳𝘴𝘵> /email");
    }
}
