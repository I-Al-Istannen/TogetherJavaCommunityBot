package togetherjava.practice.communitybot.command.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import togetherjava.practice.communitybot.command.system.AbstractCommandNode;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type;

/**
 * A simple hello world command
 */
public class CommandSayHello extends AbstractCommandNode {

    {
        addChild(new CommandSayHelloAndGreet());
    }
    
    public CommandSayHello() {
        super("hello", "hello", "Says hello", "Hello");
    }

    /**
     * Executes the command
     *
     * @param channel The channel the message was sent in
     * @param message The message that was sent
     * @param arguments The arguments after the command name
     */
    @Override
    protected Type execute(MessageChannel channel, Message message, String[] arguments) {
        channel.sendMessage("Hello " + message.getAuthor().getAsMention()).queue();
        return Type.SEND_USAGE;
    }
}
