package com.game.the_gallows_game.model;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private Long chatId;

	private Quiz quiz;

	@Builder.Default
	private Set<String> historyQuizQuestions = new HashSet<>();

	@Builder.Default
	private Boolean isPlaying = false;

}
