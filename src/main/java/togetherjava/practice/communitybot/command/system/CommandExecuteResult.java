package togetherjava.practice.communitybot.command.system;

/**
 * The result of executing a command
 */
public class CommandExecuteResult {

    private AbstractCommandNode node;
    private Type type;

    /**
     * @param node The node
     * @param type The type of the result
     */
    public CommandExecuteResult(AbstractCommandNode node, Type type) {
        this.node = node;
        this.type = type;
    }

    /**
     * @return The type of the result
     */
    public Type getType() {
        return type;
    }

    /**
     * @return The CommandNode or null if none
     */
    public AbstractCommandNode getNode() {
        return node;
    }

    /**
     * The type of the result
     */
    public enum Type {
        /**
         * The command was successfully executed
         */
        SUCCESSFUL,
        /**
         * Sends the command usage
         */
        SEND_USAGE,
        /**
         * If a <em><strong>real</strong></em> error occurred executing the command. Nothing you should encounter, you
         * probably just have to deal with input verification.
         */
        ERROR,
        /**
         * If the command was not found
         */
        NOT_FOUND;

    }
}
