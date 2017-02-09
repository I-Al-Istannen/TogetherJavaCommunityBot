package togetherjava.practice.communitybot.listener;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.MessageBuilder.Formatting;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import togetherjava.practice.communitybot.command.commands.CommandList;
import togetherjava.practice.communitybot.command.commands.CommandSayHello;
import togetherjava.practice.communitybot.command.system.AbstractCommandNode;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type;
import togetherjava.practice.communitybot.command.system.Root;

/**
 * The {@link EventListener} for commands
 */
public class CommandEventListener extends ListenerAdapter {

    private Root root = new Root();
    private String prefix;

    /**
     * @param prefix The command prefix
     */
    public CommandEventListener(String prefix) {
        this.prefix = prefix;

        addCommand(new CommandSayHello());
        addCommand(new CommandList());
    }

    /**
     * @param node The {@link AbstractCommandNode} to add
     */
    public void addCommand(AbstractCommandNode node) {
        root.addChild(node);
    }

    /**
     * @return The root of the tree
     */
    public Root getRoot() {
        return root;
    }

    /**
     * @return The prefix
     */
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String strippedContent = event.getMessage().getStrippedContent();
        if (!strippedContent.startsWith(prefix)) {
            return;
        }
        String query = strippedContent.substring(prefix.length());

        CommandExecuteResult executionResult = root.execute(query, event.getMessage());

        if (executionResult.getType() == Type.NOT_FOUND) {
            Message message = new MessageBuilder()
                    .append("Couldn't find the command:", Formatting.ITALICS, Formatting.BOLD)
                    .append(" ")
                    .append(query, Formatting.BLOCK)
                    .build();
            event.getChannel().sendMessage(message).queue();
        }
        else if (executionResult.getType() == Type.SEND_USAGE) {
            event.getChannel().sendMessage(executionResult.getNode().getUsage());
        }
        else if (executionResult.getType() == Type.ERROR) {
            event.getChannel().sendMessage(
                    new MessageBuilder()
                            .append("An error occurred.", Formatting.BOLD, Formatting.ITALICS)
                            .build()
            ).queue();
        }
    }
}
