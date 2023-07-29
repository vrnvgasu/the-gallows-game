package com.game.the_gallows_game.bot;

import com.game.the_gallows_game.servie.BotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotCommand extends TelegramLongPollingBot {

	private final BotService botService;

	public BotCommand(@Value("${bot.token}") String token, BotService botService) {
		super(token);
		this.botService = botService;
	}

	@Override
	public void onUpdateReceived(Update update) {
		SendMessage message = botService.handle(update);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getBotUsername() {
		return "TheGallowsGame26072023Bot";
	}

}
