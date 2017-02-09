package togetherjava.practice.communitybot.command.commands;

import java.util.stream.Collectors;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.MessageBuilder.Formatting;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import togetherjava.practice.communitybot.CommunityBot;
import togetherjava.practice.communitybot.command.system.AbstractCommandNode;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type;

/**
 * Lists all commands
 */
public class CommandList extends AbstractCommandNode {

    public CommandList() {
        super("list", "list", "Lists all commands.", "List");
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
        String commands = CommunityBot.getInstance().getCommandListener().getRoot().getChildren().stream()
                .map(AbstractCommandNode::getKeyword)
                .collect(Collectors.joining("\n"));
        channel.sendMessage(
                new MessageBuilder()
                        .append("I know the following commands:", Formatting.ITALICS, Formatting.BOLD)
                        .append("\n")
                        .append(commands)
                        .build()
        ).queue();
        return Type.SUCCESSFUL;
    }
}
