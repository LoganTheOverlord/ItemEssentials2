package logaaan.itemessentials.tool;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Formatter {


    /**
     * Apply colour codes and line breaks
     * This is used for messages.yml, NOT the tool itself
     *
     * @param msg   Message to format
     * @return  Formatted message with colours
     */
    public static String format(String msg) {
        return translateHexColorCodes(ChatColor.translateAlternateColorCodes('&', msg));
    }

    /**
     * Apply hex colours to message
     * 
     * @param message   The message to format
     * @return  Formatted message with hex colours
     */
    public static String translateHexColorCodes(String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            String colour = message.substring(matcher.start(), matcher.end());
            message = message.replace(colour, ChatColor.of(colour.replace("&", "")) + "");
            matcher = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Adds the copy to clipboard and run command components
     * 
     * @param colouredText  Input text to use for the command or clipboard
     * @return  Message components to senc
     */



    /**
     * Add components for the presets list message
     * 
     * @param text  Text with colour codes to turn into components
     * @param preset    Name of the preset
     * @param label Label for the hover text
     * @return  Return a list of base components
     */
    public static BaseComponent[] presetComponents(String text, String preset, String label) {
        // Prepare component builder
        ComponentBuilder builder = new ComponentBuilder();
        // Create regex match for hex codes
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(text);

        // Split text into pieces which will be separate text components (thanks, bungee)
        String[] textList = text.split("&#([A-Fa-f0-9]{6})");

        // Start at SECOND string in list, the first will always be blank
        int counter = 1;

        while (matcher.find()) {
            // Get hex colour
            String colour = text.substring(matcher.start(), matcher.end()).replace("&", "");
            // Create component from text from list
            TextComponent component = new TextComponent(textList[counter]);
            // Set components colour
            component.setColor(ChatColor.of(colour));
            // Add event on hover
            component.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
                        format(String.format("&rUse the &7%s &rpreset", preset)))));
            // Add event on click
            component.setClickEvent(
                new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, 
                        String.format("/%1$s preset %2$s ", label, preset)));
            // Append component to builder
            builder.append(component);
            counter++;
        }
        // Return components
        return builder.create();
    }


}
