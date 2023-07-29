package com.game.the_gallows_game.config;

import com.game.the_gallows_game.bot.BotCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

	@Bean
	TelegramBotsApi telegramBotsApi(BotCommand botCommand) throws TelegramApiException {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(botCommand);
		return telegramBotsApi;
	}

}
