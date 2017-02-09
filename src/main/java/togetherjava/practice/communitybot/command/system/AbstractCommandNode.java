package togetherjava.practice.communitybot.command.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import togetherjava.practice.communitybot.command.system.CommandExecuteResult.Type;

/**
 * A skeleton for the Command node
 */
public abstract class AbstractCommandNode {

    private String keyword, usage, description, name;

    private List<AbstractCommandNode> children = new ArrayList<>();

    /**
     * @param keyword The keyword
     * @param usage The usage message
     * @param description The description
     * @param name The name
     */
    public AbstractCommandNode(String keyword, String usage, String description, String name) {
        this.keyword = keyword;
        this.usage = usage;
        this.description = description;
        this.name = name;
    }

    /**
     * @param keyword The keyword to check
     *
     * @return True if that keyword belongs to you
     */
    public boolean isYourKeyword(String keyword) {
        return this.keyword.equalsIgnoreCase(keyword);
    }

    /**
     * @return A keyword that triggers this command
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @return The command usage
     */
    public String getUsage() {
        return usage;
    }

    /**
     * @return A short command description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return The name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * @param node The node to add
     */
    public void addChild(AbstractCommandNode node) {
        children.add(node);
    }

    /**
     * @return All children in an unmodifiable List
     */
    public List<AbstractCommandNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * @return A List with all child commands. Unmodifiable
     */
    public List<AbstractCommandNode> getAllChildren() {
        List<AbstractCommandNode> nodes = new ArrayList<>();
        nodes.addAll(children);
        for (AbstractCommandNode child : children) {
            nodes.addAll(child.getAllChildren());
        }
        return nodes;
    }

    /**
     * @return True if this node should be transparent.
     */
    protected boolean isTransparent() {
        return false;
    }

    /**
     * Executes a command
     *
     * @param query The user entered query. Everything after the command prefix
     * @param message The message that triggered it
     *
     * @return The {@link CommandExecuteResult}, if any. {@link Optional#empty()} if the command was not found
     */
    public CommandExecuteResult execute(String query, Message message) {
        Queue<String> parts = new LinkedList<>(Arrays.asList(query.split(" ")));
        CommandFindResult findResult = find(parts);

        if (!findResult.wasSuccessful()) {
            return new CommandExecuteResult(null, Type.NOT_FOUND);
        }

        Queue<String> leftArgs = findResult.getLeftArgs();
        String[] arguments = leftArgs.toArray(new String[leftArgs.size()]);
        Type executeResult = findResult.getCommandNode().execute(message.getChannel(), message, arguments);

        if (executeResult == null) {
            return new CommandExecuteResult(findResult.getCommandNode(), Type.ERROR);
        }
        return new CommandExecuteResult(findResult.getCommandNode(), executeResult);
    }

    /**
     * Attempts to find a command
     *
     * @param leftArgs The arguments left
     *
     * @return The result
     */
    protected CommandFindResult find(Queue<String> leftArgs) {
        if (leftArgs.isEmpty()) {
            // recursion anchor
            return new CommandFindResult(null, null);
        }

        // check yourself. Transparent nodes will just happily pass it along 
        if (!isTransparent()) {
            String keyword = leftArgs.poll();

            if (!isYourKeyword(keyword)) {
                return new CommandFindResult(null, null);
            }
        }

        for (AbstractCommandNode node : getChildren()) {
            CommandFindResult findResult = node.find(new LinkedList<>(leftArgs));
            if (findResult.wasSuccessful()) {
                return findResult;
            }
        }

        if (isTransparent()) {
            return new CommandFindResult(null, null);
        }
        else {
            return new CommandFindResult(this, leftArgs);
        }
    }

    /**
     * Executes the command
     *
     * @param channel The channel the message was sent in
     * @param message The message that was sent
     * @param arguments The arguments after the command name
     */
    protected abstract Type execute(MessageChannel channel, Message message, String[] arguments);

    public static class CommandFindResult {
        private AbstractCommandNode commandNode;
        private Queue<String> leftArgs;

        CommandFindResult(AbstractCommandNode commandNode, Queue<String> leftArgs) {
            this.commandNode = commandNode;
            this.leftArgs = leftArgs;
        }

        /**
         * @return The current arguments
         */
        Queue<String> getLeftArgs() {
            return leftArgs;
        }

        /**
         * @return The command node. Null if not found
         */
        AbstractCommandNode getCommandNode() {
            return commandNode;
        }

        /**
         * @return True if this method found a command node
         */
        boolean wasSuccessful() {
            return commandNode != null;
        }
    }
}
