package togetherjava.practice.communitybot.command.system;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type;

import static togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type.ERROR;

/**
 * The root command node
 */
public class Root extends AbstractCommandNode {

    public Root() {
        super("","","","");
    }
    
    @Override
    protected boolean isTransparent() {
        return true;
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
        return ERROR;
    }
}
