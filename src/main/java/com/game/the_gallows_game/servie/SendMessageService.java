package com.game.the_gallows_game.servie;

import com.game.the_gallows_game.model.Quiz;
import com.game.the_gallows_game.model.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
@RequiredArgsConstructor
public class SendMessageService {

	public static final String START = "/start";

	public static final String NEW_GAME = "новая игра";

	public static final String SURRENDER = "сдаюсь:(";

	public SendMessage offerNewGame(long chatId) {
		return prepareSendMessage(chatId, "Поиграем в виселицу?", NEW_GAME);
	}

	public SendMessage startNewGame(User user) {
		Quiz quiz = user.getQuiz();
		return prepareSendMessage(user.getChatId(), quiz.getQuestion() + "\n" + quiz.getUserAnswer(), SURRENDER);
	}

	private SendMessage prepareSendMessage(long idChat, String text, String buttonText) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(String.valueOf(idChat));
		sendMessage.setText(text);

		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		List<KeyboardRow> keyboardRows = new ArrayList<>();

		KeyboardRow row = new KeyboardRow();
		row.add(buttonText);
		keyboardRows.add(row);

		keyboardMarkup.setKeyboard(keyboardRows);
		sendMessage.setReplyMarkup(keyboardMarkup);

		return sendMessage;
	}

	public SendMessage endGame(User user) {
		Quiz quiz = user.getQuiz();
		return prepareSendMessage(user.getChatId(), "Мне жаль, что у тебя такая слабая воля к победе:(\n"
				+ "Правильный ответ:\n"
				+ quiz.getRightAnswer(), NEW_GAME);
	}

	public SendMessage wrongAttempt(User user) {
		return prepareSendMessage(user.getChatId(), "Нужно пытаться угадать 1 (один) символ\n", SURRENDER);
	}

	public SendMessage failed(User user) {
		return prepareSendMessage(
				user.getChatId(),
				"Ошибка!\n" + user.getQuiz().getFailedProgress().getPicture(),
				SURRENDER
		);
	}

	public SendMessage success(User user) {
		return prepareSendMessage(user.getChatId(), "Угадал!\n" + user.getQuiz().getUserAnswer(), SURRENDER);
	}

	public SendMessage win(User user) {
		return prepareSendMessage(user.getChatId(), "ЛУЧШИЙ!\n" + user.getQuiz().getUserAnswer(), NEW_GAME);
	}

	public SendMessage lose(User user) {
		return prepareSendMessage(user.getChatId(),
				user.getQuiz().getFailedProgress().getPicture() + "\n"
				+ "Ты проиграл эту игру:(\n"
				+ "Правильный ответ:\n"
				+ user.getQuiz().getRightAnswer(), NEW_GAME);
	}

}
