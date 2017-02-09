package togetherjava.practice.communitybot.command.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import togetherjava.practice.communitybot.command.system.AbstractCommandNode;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type;

/**
 * Says hello and greets you
 */
class CommandSayHelloAndGreet extends AbstractCommandNode {

    CommandSayHelloAndGreet() {
        super("greet", "hello greet", "Greets you too!", "Greet");
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
        if (arguments.length < 1) {
            return Type.SEND_USAGE;
        }
        channel.sendMessage("Hello " + message.getAuthor().getAsMention() + " and have a nice day!").queue();
        return Type.SUCCESSFUL;
    }
}
