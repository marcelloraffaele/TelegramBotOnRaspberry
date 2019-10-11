# TelegramBotOnRaspberry

This project want to show how to Manage your Raspberry pi3 using Telegram Bot, this Java project is the source code of the this video: [YOUTUBE video](https://www.youtube.com/watch?v=tat3laEkiBM).

The code is full and open, you can have fun with it, learn how to use these libraries and create your bot.

I used two libraries:
1. telegrambots library to start a telegram bot;
2. pi4j-core library to use your raspberry from java.
	
you can find these dependency inside the file pom.xml:
```
<dependencies>
        <dependency>
            <groupId>org.telegram</groupId>
            <artifactId>telegrambots</artifactId>
            <version>2.4.4.5</version>
        </dependency>
        <dependency>
            <groupId>com.pi4j</groupId>
                <artifactId>pi4j-core</artifactId>
            <version>1.2-SNAPSHOT</version>
        </dependency>
    </dependencies>
```	

The only thing that you need is only to setup the file BotMain.java the following variables:
```
public static final String TOKEN = "YOURBOTTOKEN";
public static final String BOT_USERNAME = "YOURBOTNAME";
```
the values for this two variables can be created using the BotFather, as explained here: [how-do-i-create-a-bot]( https://core.telegram.org/bots#3-how-do-i-create-a-bot)



Enjoy!!!


