package togetherjava.practice.communitybot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import togetherjava.practice.communitybot.listener.CommandEventListener;

/**
 * The main class
 */
public class CommunityBot {

    private static CommunityBot instance;

    private JDA jda;
    private CommandEventListener commandListener;

    private CommunityBot(String token, String prefix) throws Exception {
        instance = this;

        commandListener = new CommandEventListener(prefix);

        jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addListener(commandListener)
                .buildBlocking();
    }

    /**
     * @return The command listener
     */
    public CommandEventListener getCommandListener() {
        return commandListener;
    }

    /**
     * @return The {@link JDA} instance
     */
    public JDA getJda() {
        return jda;
    }

    /**
     * @return The instance
     */
    public static CommunityBot getInstance() {
        return instance;
    }

    public static void main(String[] args) throws Exception {
        new CommunityBot(args[0], "!!");
    }
}
