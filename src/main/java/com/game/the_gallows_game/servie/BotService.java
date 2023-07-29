package com.game.the_gallows_game.servie;

import com.game.the_gallows_game.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class BotService {

	private final UserService userService;

	private final QuizService quizService;
	private final SendMessageService sendMessageService;

	public SendMessage handle(Update update) {
		Long chatId = update.getMessage().getChatId();
		User user = userService.getOrCreateUserByChatId(chatId);

		Message message = update.getMessage();
		String userAnswer = message.getText().toLowerCase();

		return switch (userAnswer) {
			case SendMessageService.START -> offerNewGame(user);
			case SendMessageService.NEW_GAME -> startNewGame(user);
			case SendMessageService.SURRENDER -> surrender(user);
			default -> attempt(user, userAnswer);
		};
	}

	private SendMessage attempt(User user, String userMessage) {
		if (!user.getIsPlaying()) {
			return offerNewGame(user);
		}

		if (!quizService.validateAttempt(userMessage)) {
			return sendMessageService.wrongAttempt(user);
		}

		boolean attempt = quizService.attemptAndChangeQuiz(user.getQuiz(), userMessage);

		if (quizService.lose(user.getQuiz())) {
			userService.endGame(user);
			return sendMessageService.lose(user);
		}
		if (quizService.checkWin(user.getQuiz())) {
			userService.endGame(user);
			return sendMessageService.win(user);
		}

		return attempt ? sendMessageService.success(user) : sendMessageService.failed(user);
	}

	private SendMessage surrender(User user) {
		if (!user.getIsPlaying()) {
			return offerNewGame(user);
		}

		SendMessage sendMessage = sendMessageService.endGame(user);
		userService.endGame(user);
		return sendMessage;
	}

	private SendMessage startNewGame(User user) {
		user = userService.startNewQuiz(user);
		return sendMessageService.startNewGame(user);
	}

	private SendMessage offerNewGame(User user) {
		return sendMessageService.offerNewGame(user.getChatId());
	}

}
