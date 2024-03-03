package refactored.stored;

public class ContentBuilder
{
    private Content content;
    private ContentBuilder context;

    public ContentBuilder()
    {
        content = new Content("", null);
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

    public Content build()
    {
        return content;
    }
}