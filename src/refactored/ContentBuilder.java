package refactored;

import java.time.LocalDateTime;

public class ContentBuilder
{
    private Content content;
    private ContentBuilder context;

    public ContentBuilder()
    {
        content = new Content("", null, LocalDateTime.now());
        context = this;
    }

    public ContentBuilder withText(String text)
    {
        content.setText(text);
        return context;
    }

    public ContentBuilder withPicture(Picture picture)
    {
        content.setPicture(picture);
        return context;
    }

    public ContentBuilder withDate(LocalDateTime dateTime)
    {
        content.setDateCreated(dateTime);
        return context;
    }

    public Content build()
    {
        return content;
    }
}