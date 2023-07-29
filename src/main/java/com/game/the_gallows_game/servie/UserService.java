package com.game.the_gallows_game.servie;

import com.game.the_gallows_game.model.User;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final QuizService quizService;

	private final Map<Long, User> userMap = new HashMap<>();

	public User getOrCreateUserByChatId(long chatId) {
		User user = userMap.get(chatId);

		if (user == null) {
			user = User.builder()
					.chatId(chatId)
//					.quiz(quizService.generateQuizForUser())
					.build();
			userMap.put(chatId, user);
		}

		return user;
	}

	public User startNewQuiz(User user) {
		user.setQuiz(quizService.generateQuizForUser(user));
		user.setIsPlaying(true);
		user.getHistoryQuizQuestions().add(user.getQuiz().getQuestion());
		return user;
	}

	public User endGame(User user) {
		user.setIsPlaying(false);
		return user;
	}

}
