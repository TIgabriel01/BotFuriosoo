package com.botfurioso;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotRunner {
    public static void main(String[] args){
        try{
            TelegramBotsApi BotsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotsApi.registerBot(new Botfurioso());
            System.out.println("Bot iniciado com sucesso!");
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

}
