package aks.replies;

import org.telegram.telegrambots.meta.api.objects.Update;

import aks.Main;

public class R_Confirm extends Commands{
    
    public R_Confirm(){
        canSend = false;
        command = "/confirm";
        commandkey = "confirm";
        commandDesc = "/confirm to save your order";
    }

    @Override
    public void action(Update update) {
        sendConfirmationMessage(update);
    }
    public void sendConfirmationMessage(Update update){
        Main.bot.reply(update.getMessage().getChatId().toString(), "🔖 𝐘𝐨𝐮𝐫 𝐨𝐫𝐝𝐞𝐫 𝐡𝐚𝐬 𝐛𝐞𝐞𝐧 𝐬𝐚𝐯𝐞𝐝");
    }
}
