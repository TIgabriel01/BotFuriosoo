package com.botfurioso;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Botfurioso extends TelegramLongPollingBot {

    private final Dotenv dotenv = Dotenv.load(); // nao mostrar meu token publicamente no codigo

    // usado para pegar as informaçoes do bot
    @Override
    public String getBotUsername() {
        return "Baiit_Bot";
    }

    // criei uma pasta [.env] fora do git para que minha chave token nao fique acessivel publicamente a fim de segurança do projeto
    @Override
    public String getBotToken() {
        return dotenv.get("API_KEY");
    }

    public void mostrarMenuInicial(String chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Bem-vindo, FURIOSO(A)! Vem e interagi comigo :) Escolha:");

        // criaçao de listas com botoes e o que cada uma levara dentro dela (mensagem)
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        InlineKeyboardButton btnQuiz = new InlineKeyboardButton();
        btnQuiz.setText("Quiz");
        btnQuiz.setCallbackData("/quiz");

        InlineKeyboardButton btnTime = new InlineKeyboardButton();
        btnTime.setText("Conhecer o Time");
        btnTime.setCallbackData("/time");

        InlineKeyboardButton btnStats = new InlineKeyboardButton();
        btnStats.setText("Estatisticas");
        btnStats.setCallbackData("/stats");

        rows.add(Arrays.asList(btnQuiz, btnTime, btnStats));
        markup.setKeyboard(rows);
        message.setReplyMarkup(markup);

        try{
            execute(message);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = null;
        String text = null;

        //mensagem de texto normal
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId().toString();
            text = update.getMessage().getText();
        }
        //clique em inline button
        else if (update.hasCallbackQuery()) {
            CallbackQuery cq = update.getCallbackQuery();
            chatId = cq.getMessage().getChatId().toString();
            text = cq.getData(); // quiz | time | stats

            // callback tratado de forma correta e evitando repetiçao no meu bot
            AnswerCallbackQuery answer = new AnswerCallbackQuery();
            answer.setCallbackQueryId(cq.getId());

            try {
                execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
            if(text == null) return;

            // comandos realizados para que quando o usuario escolha um desses:
        if (text.startsWith("/start")) {
            mostrarMenuInicial(chatId);

        } else if (text.equals("/quiz")) {
            perguntaAtual = 0; // sempre começa da primeira pergunta
            String primeiraPergunta = pergunta[perguntaAtual] + "\n\n";
            for (String alt : alternativas[perguntaAtual]){
                primeiraPergunta += alt + "\n";
            }
            primeiraPergunta += "\nResponda com A, B ou C.";

            SendMessage perguntaMsg = new SendMessage(chatId, primeiraPergunta);
            try {
                execute(perguntaMsg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (text.equals("/time")) {
            String textoTime =
                    "*Conheça o time de CS da FURIA!*\n\n" +
                            "Um dos times mais agressivos do cenário mundial, a FURIA representa o Brasil com estilo único e ousadia.\n\n" +
                            "*Line-up atual:*\n" +
                            "• KSCERATO (capitão)\n" +
                            "• yuurih\n" +
                            "• chelo\n" +
                            "• saffee\n" +
                            "• FalleN (coach & player)\n\n" +
                            "Pronto pra torcer com a gente? Seja um Furioso você também :)\n\n" +
                            "Digite /quiz para testar seus conhecimentos ou /start para voltar ao menu.";

            SendMessage info = new SendMessage();
            info.setChatId(chatId);
            info.setText(textoTime);
            info.setParseMode("Markdown");
            try {
                execute(info);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (text.equals("/stats")) {
            String textoStats =
                    "* Estatísticas da FURIA CS:GO*\n\n" +
                            " *Títulos recentes:* ESL Challenger 2024, CBCS Elite League\n" +
                            " *Ranking HLTV:* Top 10 mundial (2025)\n" +
                            " *Estilo de jogo:* agressivo e ousado — marca registrada!\n\n" +
                            " *Destaques individuais:*\n" +
                            "• KSCERATO: rating 1.20+ nos últimos 6 meses\n" +
                            "• yuurih: mais de 200 kills em playoffs recentes\n\n" +
                            "Quer mais?\nDigite /time para saber o elenco ou /quiz para testar seu conhecimento. Mas se quiser retornar ao menu digite /start.";

            SendMessage stats = new SendMessage();
            stats.setChatId(chatId);
            stats.setText(textoStats);
            stats.setParseMode("Markdown");
            try {
                execute(stats);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (text.equals("/continuar")) {
            perguntaAtual++;
            if (perguntaAtual < pergunta.length) {
                String proxPergunta = pergunta[perguntaAtual] + "\n\n";
                for (String alt : alternativas[perguntaAtual]) {
                    proxPergunta += alt + "\n";
                }
                proxPergunta += "\nResponda com A, B ou C.";

                SendMessage proxMsg = new SendMessage(chatId, proxPergunta);
                try {
                    execute(proxMsg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage fim = new SendMessage(chatId, "Parabéns! Você concluiu o quiz da FURIA.\nDigite /quiz para jogar de novo ou /start para voltar ao menu.");
                try {
                    execute(fim);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                perguntaAtual = 0; // reseta se quiser jogar de novo
            }

        } else if (text.equals("/parar")) {
            SendMessage pararMsg = new SendMessage(chatId, "Quiz encerrado. Quando quiser jogar de novo, é só digitar /quiz! ou retorne ao menu com /start");
            try {
                execute(pararMsg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        if (text.equalsIgnoreCase("A") || text.equalsIgnoreCase("B") || text.equalsIgnoreCase("C")) {
            if (text.equalsIgnoreCase(respostasCorretas[perguntaAtual])) {
                SendMessage acertou = new SendMessage(chatId, "✅ Boa! Você acertou! Digite /continuar ou /parar.");
                try {
                    execute(acertou);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendMessage errou = new SendMessage(chatId, "❌ Errou! Mas tá valendo! Digite /continuar ou /parar.");
                try {
                    execute(errou);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //controle do Quiz - respostas
    private int perguntaAtual = 0;

    private final String[] pergunta = {
            "Pergunta 1 -> Qual jogador da FURIA fez o 5-man ACE contra a Liquid?",
            "Pergunta 2 -> Qual o nickname do capitão da FURIA?",
            "Pergunta 3 -> Em que ano a FURIA foi fundada?"
    };

    private final String[][] alternativas = {
            {"A) arT", "B) yuurih", "C) KSCERATO"},
            {"A) arT", "B) KSCERATO", "C) yuurih"},
            {"A) 2017", "B) 2018", "C) 2019"}
    };

    private final String[] respostasCorretas = {"A", "B", "A"};
}