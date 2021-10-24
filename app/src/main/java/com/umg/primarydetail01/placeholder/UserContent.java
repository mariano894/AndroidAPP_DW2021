package com.umg.primarydetail01.placeholder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserContent
{
    public static List<UserItem> ITEMS = new ArrayList<UserItem>();

    public static Map<String, UserItem> ITEM_MAP = new HashMap<String, UserItem>();

    public static void startOneItem(int index, String name, String details)
    {
        addItem(createPlaceholderItem(index, name, details));
    }

    private static void addItem(UserItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static UserItem createPlaceholderItem(int position, String content, String details)
    {
        return new UserItem(String.valueOf(position), content, details);
    }

    public static class UserItem
    {
        public final String id;
        public final String content;
        public final String details;

        public UserItem(String id, String content, String details)
        {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}