package aks.replies;

import org.telegram.telegrambots.meta.api.objects.Update;

import aks.Main;
import aks.Strings;

public class R_email extends Commands{

    public R_email(){
        canSend = false;
        command = "/email";
        commandDesc = "✉️ 𝐬𝐞𝐧𝐝 𝐲𝐨𝐮𝐫 𝐞𝐦𝐚𝐢𝐥 𝐚𝐝𝐝𝐫𝐞𝐬𝐬";
        commandkey = "email";
    }

    @Override
    public void action(Update update) {
        emailConfirmedMessage(update);
    }
    @Override
    public void notYet(Update update) {
        sendNotYetMessage(update);
    }
    public void emailConfirmedMessage(Update update){
        Main.bot.sendImageCaption(update.getMessage().getChatId().toString(), Strings.EMAIL_DONE, "✉️ 𝐄𝐦𝐚𝐢𝐥: ✅");
        Main.bot.commObjects.rCcp.canSend = true;
    }
    public void sendNotYetMessage(Update update){
        Main.bot.reply(update.getMessage().getChatId().toString(), "<𝘤𝘳𝘦𝘢𝘵𝘦 𝘢 𝘯𝘦𝘸 𝘰𝘳𝘥𝘦𝘳 𝘧𝘪𝘳𝘴𝘵> /order");
    }
}