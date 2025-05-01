package com.botfurioso;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import io.github.cdimascio.dotenv.Dotenv;

public class BotRunner {
    public static void main(String[] args){
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("API_KEY");


        try{
            TelegramBotsApi BotsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotsApi.registerBot(new Botfurioso());
            System.out.println("Bot iniciado com sucesso!");
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

}
