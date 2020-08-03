package org.poalim.html;

import org.poalim.model.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    private static final String HTML_TAG_PATTERN = "<([^>]+)>";

    public Tag getHtmlValidTags(String html) {
        Map<String, Integer> map = new HashMap<>();
        Pattern pattern = Pattern.compile(HTML_TAG_PATTERN);
        Matcher matcher = pattern.matcher(html);
        int counter = 0;
        while (matcher.find()) {
            String tag = matcher.group(1);
            if (tag.startsWith("/")) {
                String strippedTag = tag.substring(1);
                int tagCount = map.getOrDefault(strippedTag, 0);
                if (tagCount != 0) {
                    counter++;
                    map.put(strippedTag, tagCount - 1);
                }
            } else {
                map.put(tag, map.getOrDefault(tag, 0) + 1);
            }
        }

        return new Tag(counter);


    }
}
