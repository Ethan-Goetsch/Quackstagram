package Refactored;

public class Content
{
    private String text;
    private Picture picture;

    private Content(String text, Picture picture)
    {
        this.text = text;
        this.picture = picture;
    }

    public String getText() { return text; }
    public Picture getPicture() { return picture; }

    public void setText(String text) { this.text = text; }
    public void setPicture(Picture picture) { this.picture = picture; }

    public class Builder
    {
        private Content content;
        private Builder context;

        public Builder()
        {
            content = new Content("", null);
            context = this;
        }

        public Builder withText(String text)
        {
            content.setText(text);
            return context;
        }

        public Builder withPicture(Picture picture)
        {
            content.setPicture(picture);
            return context;
        }

        public Content build()
        {
            return content;
        }
    }
}