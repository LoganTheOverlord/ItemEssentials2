package logaaan.itemessentials.tool;

public class Pattern {

    /**
     * Make a pattern of colours stretch through the text
     * 
     * @param pattern   List of colours
     * @param text  Text to colour
     * @return  Text with colour codes injected
     */
    public static String stretchPattern(String[] pattern, String text) {
        // Get how long a colour segment should be
        double segment = (double) text.length() / pattern.length;
        StringBuilder builder = new StringBuilder();
        // Start at position 0 and colour 0
        double position = 0;
        int colour = 0;
        while (position < text.length()) {
            // Check if the next one will overshoot, if it does, stretch to the end
            if (position + segment * 2 > text.length()) {
                builder.append(Tool.setSolidColour(pattern[colour], text.substring((int) Math.ceil(position))));
                position = text.length();
            // Set colour from position rounded up to segment rounded up
            } else if (position + segment <= text.length()) {
                builder.append(Tool.setSolidColour(pattern[colour], text.substring((int) Math.ceil(position), (int) Math.ceil(position + segment))));
                position = position + segment;
            }
            // Advance to next colour
            colour++;
        }
        return builder.toString();
    }

    /**
     * Make a pattern of colours repeat through the text
     * 
     * @param pattern   List of colours
     * @param text  Text to colour
     * @return  Text with colour codes injected
     */
    public static String repeatPattern(String[] pattern, String text) {
        StringBuilder builder = new StringBuilder();
        int colour = 0;
        for (int i = 0; i < text.length(); i++) {
            // Check if the character is a space, don't apply colour code if it is
            if (text.charAt(i) == ' ') {
                builder.append(text.charAt(i));
            } else {
                // Apply colour code to character
                builder.append(Tool.setSolidColour(pattern[colour], text.charAt(i)));
            }
            
            // Check if the sequence should restart
            if (colour + 1 >= pattern.length) {
                colour = 0;
            } else {
                // Advance to next colour
                colour++;
            }
        }
        return builder.toString();
    }

    /**
     * Make a pattern of colours stretch through the text
     * 
     * @param pattern   List of colours
     * @param text  Text to colour
     * @return  Text with colour codes injected
     */
    public static String gradientPattern(String[] pattern, String text) {
        // Get how long a colour segment should be
        double segment = (double) text.length() / (pattern.length - 1);
        StringBuilder builder = new StringBuilder();
        // Start at position 0 and colour 0
        double position = 0;
        int colour = 0;
        while (position < text.length()) {
            // Check if the next one will overshoot, if it does, stretch to the end
            if (position + segment * 2 > text.length()) {
                builder.append(Tool.setGradientColour(pattern[colour], pattern[colour + 1], text.substring((int) Math.ceil(position))));
                position = text.length();
            // Set colour from position rounded up to segment rounded up
            } else if (position + segment <= text.length()) {
                builder.append(Tool.setGradientColour(pattern[colour], pattern[colour + 1], text.substring((int) Math.ceil(position), (int) Math.ceil(position + segment))));
                position = position + segment;
            }
            // Advance to next colour
            colour++;
        }
        return builder.toString();
    }


}
