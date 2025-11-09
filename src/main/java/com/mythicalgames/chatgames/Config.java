package com.mythicalgames.chatgames;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;

import java.util.Arrays;
import java.util.List;

@Header({
    "###############################################",
    "# Thank you for downloading ChatGames",
    "# You are now part of our Mythical Ecosystem",
    "###############################################"
})

public class Config extends OkaeriConfig {
    @Comment(" ")
    @Comment("###############################################")
    @Comment("# General ChatGames Settings")
    @Comment("###############################################")
    public boolean enabled = true;

    @Comment("Interval (in seconds) between games")
    public int intervalSeconds = 120;

    @Comment("Reward configuration")
    public Reward reward = new Reward();

    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Guess The Number Settings")
    @Comment("###############################################")
    public GuessNumber guessNumber = new GuessNumber();

    public static class GuessNumber extends OkaeriConfig {
        @Comment("Minimum possible number")
        public int min = 1;

        @Comment("Maximum possible number")
        public int max = 100;

        @Comment("Message format for question")
        public String question = "&eI’m thinking of a number between &b{min}&e and &b{max}&e!";
    }

    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Minecraft Trivia Settings")
    @Comment("###############################################")
    public Trivia trivia = new Trivia();

    public static class Trivia extends OkaeriConfig {
        @Comment("List of trivia questions and answers")
        public List<TriviaQuestion> questions = Arrays.asList(
            new TriviaQuestion("What mob drops blaze rods?", "Blaze"),
            new TriviaQuestion("What is the rarest ore in Minecraft?", "Emerald"),
            new TriviaQuestion("Which block do you need to make a Nether portal?", "Obsidian")
        );
    }

    public static class TriviaQuestion extends OkaeriConfig {
        public String question;
        public String answer;

        public TriviaQuestion(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public TriviaQuestion() {}
    }

    @Comment("###############################################")
    @Comment("# Sequence Guess Settings")
    @Comment("###############################################")
    public Sequence sequence = new Sequence();

    public static class Sequence extends OkaeriConfig {
        @Comment("Example patterns that can be asked")
        public List<String> patterns = Arrays.asList(
            "2, 4, 8, 16, ?",
            "1, 3, 6, 10, ?",
            "5, 10, 15, ?"
        );
    }

    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Quick Math Settings")
    @Comment("###############################################")
    public QuickMath quickMath = new QuickMath();

    public static class QuickMath extends OkaeriConfig {
        @Comment("Operators used in problems (+, -, *, /)")
        public List<String> operators = Arrays.asList("+", "-", "*", "/");

        @Comment("Maximum number used in math problems")
        public int maxNumber = 50;
    }

    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Guess The Word Settings")
    @Comment("###############################################")
    public GuessWord guessWord = new GuessWord();

    public static class GuessWord extends OkaeriConfig {
        @Comment("List of hints and corresponding words")
        public List<WordHint> words = Arrays.asList(
            new WordHint("You use it to mine stone", "Pickaxe"),
            new WordHint("You need this to sleep at night", "Bed"),
            new WordHint("Explodes when near players", "Creeper")
        );
    }

    public static class WordHint extends OkaeriConfig {
        public String hint;
        public String word;

        public WordHint(String hint, String word) {
            this.hint = hint;
            this.word = word;
        }

        public WordHint() {}
    }

    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Word Unscramble Settings")
    @Comment("###############################################")
    public Unscramble unscramble = new Unscramble();

    public static class Unscramble extends OkaeriConfig {
        @Comment("List of words to scramble")
        public List<String> words = Arrays.asList("diamond", "creeper", "sword", "pickaxe", "zombie");
    }

    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Fast Type Settings")
    @Comment("###############################################")
    public FastType fastType = new FastType();

    public static class FastType extends OkaeriConfig {
        @Comment("List of phrases to type")
        public List<String> phrases = Arrays.asList(
            "Creepers explode!",
            "The cake is a lie.",
            "Mining is life.",
            "Beware of Herobrine."
        );
    }
 
    @Comment(" ")
    @Comment("###############################################")
    @Comment("# Missing Letter Settings")
    @Comment("###############################################")
    public MissingLetter missingLetter = new MissingLetter();

    public static class MissingLetter extends OkaeriConfig {
        @Comment("List of words for missing-letter game")
        public List<String> words = Arrays.asList("minecraft", "villager", "skeleton", "enderdragon");
    }
  
    public static class Reward extends OkaeriConfig {
        @Comment(" ")
        @Comment("###############################################")
        @Comment("# Reward Settings")
        @Comment("###############################################")

        @Comment("Reward type: MONEY (via Mythical-Economy)")
        public String type = "MONEY";

        @Comment("Minimum random reward amount")
        public double min = 25.0;

        @Comment("Maximum random reward amount")
        public double max = 100.0;
    }
}
